import React from "react";
import axios from "axios";
import Button from "./type/Button";
import Slider from "./type/Slider";

import CO2Sensor from "./type/sensor/CO2Sensor";
import TemperatureSensor from "./type/sensor/TemperatureSensor";
import HumiditySensor from "./type/sensor/HumiditySensor";

let csrfToken = document.querySelector('meta[name="_csrf"]').content;
let csrfHeader =  document.querySelector('meta[name="_csrf_header"]').content;
let headers = {};
headers[csrfHeader] = csrfToken;

function PartFactory({widget, part}) {

    return (
        <>
            {(() => {
                switch (part.type) {
                    case 'BUTTON':
                        return <Button widget={widget} part={part} />;
                    case 'SLIDER':
                        return <Slider widget={widget} part={part} />;
                    case 'CO2_SENSOR':
                        return <CO2Sensor part={part} />;
                    case 'TEMPERATURE_SENSOR':
                        return <TemperatureSensor part={part} />;
                    case 'HUMIDITY_SENSOR':
                        return <HumiditySensor part={part} />;
                }
            })()}
        </>
    )
}

function Part({widget}) {
    const onClickDeleteButton = () => {
        axios.post(
            "/iot/widget/part/" + widget.id + "/delete",
            {},
            {
                headers : headers
            }
        ).catch(error => {
            window.location.reload();
        });
    }

    return (
        <div className="col mb-4">
            <div className="card">
                <div className="card-header d-flex justify-content-between align-items-center w-100">
                    <h4>{widget.part.label}</h4>
                    <div className="btn-group">
                        <button type="button" className="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-expanded="false">메뉴</button>
                        <div className="dropdown-menu dropdown-menu-right">
                            <button className="dropdown-item" onClick={onClickDeleteButton}>삭제</button>
                        </div>
                    </div>
                </div>
                <div className="card-body">
                    <PartFactory widget={widget} part={widget.part} />
                </div>
            </div>
        </div>
    );
}

export default Part;