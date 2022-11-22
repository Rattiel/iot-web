import React from "react";
import {Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Tooltip} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Tooltip);

function Button({values}) {
    const options = {
        responsive: true,
        plugins: {},
        scales: {
            y: {
                max: 1,
                min: 0,
                ticks: {
                    callback: function (value, index, ticks) {
                        if (value === 0) {
                            return "닫힘";
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

export default Button;
