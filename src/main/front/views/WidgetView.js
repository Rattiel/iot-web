import React, { useReducer, useState, useEffect } from 'react';
import axios from 'axios';

const SockJS = require('sockjs-client');
require('stompjs');

import Widget from "../components/Widget/Widget";
import {act} from "react-dom/test-utils";

let sock = new SockJS('/iot/api');
let client = Stomp.over(sock);

let csrfToken = document.querySelector('meta[name="_csrf"]').content;
let csrfHeader =  document.querySelector('meta[name="_csrf_header"]').content;
let headers = {};
headers[csrfHeader] = csrfToken;

function WidgetList({widgets}) {
    return (
        <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 mb-3">
            {
                widgets != null && widgets.map(widget => (<Widget widget={widget} key={widget.id}/>))
            }
        </div>
    );
}

export const WidgetDispatch = React.createContext(null);

function WidgetView() {
    const initialState = {
        widgets: []
    };

    function init() {
        return { };
    }

    const [isLoading, setLoading] = useState(true);
    const [state, dispatch] = useReducer(reducer, initialState, init);
    const { widgets } = state;

    const onCreatedWidget = (response) => {
        dispatch({ type: 'CREATE_WIDGET', widget: JSON.parse(response.body) });
    }

    const onDeletedWidget = (response) => {
        dispatch({ type: 'DELETE_WIDGET', widget: JSON.parse(response.body) });
    }

    const onUpdatedDevice = (response) => {
        dispatch({ type: 'UPDATE_DEVICE', device: JSON.parse(response.body) });
    }

    const onDeletedDevice = (response) => {
        dispatch({ type: 'DELETE_DEVICE', device: JSON.parse(response.body) });
    }

    const onUpdatePart = (response) => {
        dispatch({ type: 'UPDATE_PART', device: JSON.parse(response.body).device, part: JSON.parse(response.body).part });
    }

    const onConnected = () => {
        client.subscribe("/user/iot/widget/create", onCreatedWidget);
        client.subscribe("/user/iot/widget/delete", onDeletedWidget);
        client.subscribe("/user/iot/device/update", onUpdatedDevice);
        client.subscribe("/user/iot/device/delete", onDeletedDevice);
        client.subscribe("/user/iot/part/update", onUpdatePart);
    }

    function reducer(state, action) {
        if (isLoading) {
            return state;
        }

        switch (action.type) {
            case 'INIT' :
                return {
                    widgets: action.widgets,
                }
            case 'CREATE_WIDGET':
                if (action.widget.type === "HISTORY") {
                    let value = [];

                    for (let j = 0; j < 15; j++) {
                        value[j] = action.widget.part.status;
                    }

                    return {
                        ...state,
                        widgets: [...state.widgets, {...action.widget, values: value}]
                    };
                } else {
                    return {
                        ...state,
                        widgets: [...state.widgets, {...action.widget}]
                    };
                }
            case 'DELETE_WIDGET':
                return {
                    ...state,
                    widgets: [...state.widgets].filter(widget => widget.id !== action.widget.id)
                };
            case 'UPDATE_DEVICE':
                return {
                    ...state,
                    widgets: [...state.widgets].map(widget => {
                        if (widget.device.id === action.device.id) {
                            if (widget.type === "DEVICE") {
                                return { ...widget, device : action.device };
                            } else if (widget.type === "PART") {
                                for (const idx in action.device.parts) {
                                    const updatedPart = action.device.parts[idx];
                                    if (widget.part.id === updatedPart.id) {
                                        return { ...widget, part : updatedPart };
                                    }
                                }
                            } else if (widget.type === "HISTORY") {
                                for (const idx in action.device.parts) {
                                    const updatedPart = action.device.parts[idx];
                                    if (widget.part.id === updatedPart.id) {
                                        return { ...widget, part : updatedPart };
                                    }
                                }
                            }
                        } else {
                            return widget;
                        }
                    })
                };
            case 'DELETE_DEVICE':
                return {
                    ...state,
                    widgets: [...state.widgets].filter(widget => widget.device.id !== action.device.id)
                };
            case 'UPDATE_PART':
                return {
                    ...state,
                    widgets: [...state.widgets].map(widget => {
                        if (widget.device.id === action.device.id) {
                            if (widget.type === "PART") {
                                if (widget.part.id === action.part.id) {
                                    return { ...widget, part : action.part };
                                }
                            } else if (widget.type === "DEVICE") {
                                const updatedDevice = { ...widget.device };

                                for (let i = 0; i < updatedDevice.parts.length; i++) {
                                    if (JSON.parse(action.part.id) === JSON.parse(updatedDevice.parts[i].id)) {
                                        updatedDevice.parts[i] = action.part;
                                    }
                                }

                                return { ...widget, device: updatedDevice };
                            } else if (widget.type === "HISTORY") {
                                if (widget.part.id === action.part.id) {
                                    let copy = [...widget.values, action.part.status];
                                    if (copy.length > 15) {
                                        return { ...widget, part : action.part, values: [...copy.slice(-15)] };
                                    } else {
                                        return { ...widget, part : action.part, values: copy };
                                    }
                                }
                            }
                        }

                        return widget;
                    })
                };
            case 'ACTION_PART':
                const params = new URLSearchParams();
                params.append('option', action.option);

                axios.post(
                    "/iot/part/" + action.part.id + "/action",
                    params,
                    {
                        headers : headers
                    }
                ).catch(error => {
                    console.log(error);
                });

                return {
                    ...state,
                    widgets: [...state.widgets].map(widget => {
                        if (widget.device.id === action.widget.device.id) {
                            if (widget.type === "PART") {
                                if (widget.part.id === action.part.id) {
                                    return { ...widget, part : {...action.part, status: action.option } };
                                }
                            } else if (widget.type === "DEVICE") {
                                const updatedDevice = { ...widget.device };

                                for (let i = 0; i < updatedDevice.parts.length; i++) {
                                    if (JSON.parse(action.part.id) === JSON.parse(updatedDevice.parts[i].id)) {
                                        updatedDevice.parts[i] = {...action.part, status: action.option };
                                    }
                                }

                                return { ...widget, device: updatedDevice };
                            }
                        }

                        return widget;
                    })
                };

            default:
                return state;
        }
    }

    useEffect(() => {
        client.connect([], onConnected);

        axios.post(
            '/iot/widget',
            { },
            {
                headers : headers
            }
        ).then(response => {
                for (let i in response.data) {
                    if (response.data[i].type === "HISTORY") {
                        let value = new Array();

                        for (let j = 0; j < 15; j++) {
                            value[j] = response.data[i].part.status;
                        }

                        response.data[i] = {...response.data[i], values: value};
                    }
                }
                dispatch({ type: 'INIT', widgets: response.data });
                setLoading(false);
        });
    }, [])

    return (
        <WidgetDispatch.Provider value={dispatch}>
            { isLoading ? <h2>로딩중</h2> : <WidgetList widgets={widgets} /> }
        </WidgetDispatch.Provider>
    );
}
export default WidgetView;
