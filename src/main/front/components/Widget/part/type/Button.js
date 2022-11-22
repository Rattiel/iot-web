import React, { useContext } from "react";
import { WidgetDispatch } from '../../../../views/WidgetView';

function Button({widget, part}) {
    const dispatch = useContext(WidgetDispatch);

    const onAction = (e) => {
        dispatch({ type: "ACTION_PART", widget: widget, part: part, option: e.target.checked ? 1 : 0 });
        // dispatch({ type: 'UPDATE_PART', widget: widget, device: device, part : { ...part, status:  e.target.checked ? 1 : 0 } });
    }

    return (
        <div className="d-flex flex-row">
            <div className="custom-control custom-switch">
                <input id={"part-" + part.id} type="checkbox" className="custom-control-input" checked={part.status === 1} onChange={onAction}/>
                <label htmlFor={"part-" + part.id} className="custom-control-label"></label>
            </div>
        </div>
    )
}

export default Button;
