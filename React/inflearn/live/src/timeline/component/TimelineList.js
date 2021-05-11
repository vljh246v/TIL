import React from 'react';

export default function Timelinelist({ timelines }) {
    return (
        <ul>
            {timelines.map(timeline => (
                <li key={timeline.id}>{timeline.desc}</li>
            ))}
        </ul>
    );
}