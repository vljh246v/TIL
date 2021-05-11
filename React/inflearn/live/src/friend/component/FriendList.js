import React from 'react';

export default function Friendlist({ friends }) {
    return (
        <ul>
            {friends.map(friend => (
                <li key={friend.id}>{friend.name} ({friend.age})</li>
            ))}
        </ul>
    );
}