import React from "react";
import {Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Tooltip} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Tooltip);

function Slider({values}) {
    const options = {
        animation: {
            duration: 0, // general animation time
        },
        responsive: true,
        plugins: { },
        scales: {
            y: {
                max: 1,
                min: -1,
                ticks: {
                    callback: function(value, index, ticks) {
                        if (value === -1) {
                            return "닫힘";
                        } else if (value === 0) {
                            return "정지";
                        } else if (value === 1) {
                            return "열림";
                        }
                    }
                }
            }
        }
    };

    const data = {
        labels: values,
        datasets: [
            {
                data: values,
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
            },
        ],
    };

    return (
        <Line options={options} data={data} />
    )
}

export default Slider;
