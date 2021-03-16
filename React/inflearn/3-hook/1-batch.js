import React, { useState, useEffect} from 'react'

export default function App(){
    const [count, setCount] = useState(0);
    function onClick() {
        setCount(count + 1);
        setCount(count + 1);
    }
    console.log('render called');
    return (
        <div>
            <h2>{count}</h2>
            <button onClick={onClick}>증가</button>
        </div>
    )
}