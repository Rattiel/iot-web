import React from "react";

function CO2Sensor({part}) {
    return (
        <div className="card-text d-flex flex-row">
            <div className="mr-1">{part.status}</div>
            <div>ppm</div>
        </div>
    )
}

export default CO2Sensor;
