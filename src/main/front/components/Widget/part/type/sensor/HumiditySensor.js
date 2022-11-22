import React from "react";

function HumiditySensor({part}) {
    return (
        <div className="card-text d-flex flex-row">
            <div className="mr-1">{part.status}</div>
            <div>%</div>
        </div>
    )
}

export default HumiditySensor;
