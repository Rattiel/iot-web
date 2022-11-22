import React from "react";

function CO2Censor({part}) {
    return (
        <div className="mb-1">
            <div className="card-text d-flex flex-row">
                <div className="mr-1">{part.label}</div>
                <div className="mr-1">:</div>
                <div className="mr-1">{part.status}</div>
                <div>ppm</div>
            </div>
        </div>
    )
}

export default CO2Censor;
