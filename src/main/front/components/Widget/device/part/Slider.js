import React, { useContext } from "react";
import { WidgetDispatch } from '../../../../views/WidgetView';

function Slider({widget, part}) {
    const dispatch = useContext(WidgetDispatch);

    const onAction = (e) => {
        dispatch({ type: "ACTION_PART", widget: widget, part: part, option: e.target.value });
    }

    return (
        <div className="mb-1">
            <div className="d-flex flex-row">
                <label style={{position: "relative", marginBottom: "0", verticalAlign: "0"}} className="mr-1" htmlFor={"part-" + part.id}>{part.label}</label>
                <div className="mr-1">:</div>
                    <div style={{paddingLeft: "0", paddingRight: "0"}} className="custom-control">
                        <input id={"part-" + part.id} type="range" min="-1" max="1" step={1} value={part.status} className="form-control-range" onChange={onAction}/>
                    </div>
            </div>
        </div>
    )
}

export default Slider;
