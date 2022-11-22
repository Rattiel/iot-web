import React from 'react';

import Device from "./device/Device";
import Part from "./part/Part";
import History from "./history/History";

function Widget({widget}) {
    return (
        <>
            {(() => {
                switch (widget.type) {
                    case 'DEVICE':
                        return <Device widget={widget} />;
                    case 'PART':
                        return <Part widget={widget} />;
                    case 'HISTORY':
                        return <History widget={widget} />;
                }
            })()}
        </>
    );
}
export default Widget;