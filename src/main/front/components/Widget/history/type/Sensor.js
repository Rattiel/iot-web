import React from "react";
import {Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Tooltip} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Tooltip);

function Sensor({values}) {
    const options = {
        animation: {
            duration: 0, // general animation time
        },
        responsive: true,
        plugins: { },
        scales: {
            y: {
                ticks: {
                    //stepSize: 1
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

export default Sensor;
