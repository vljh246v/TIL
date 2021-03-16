import React, { useState, useEffect} from 'react'
import ReactDOM from 'react-dom';

export default function App(){
    const [count, setCount] = useState(0);
    function onClick() {
      ReactDOM.unstable_batchedUpdates(() => {
        setCount(v => v + 1);
        setCount(v => v + 1);
      });
    }
    useEffect(() => {
      window.addEventListener('click', onClick);
      return () => {
        console.log('!!')
        window.removeEventListener('click', onClick)
      };
    })
    console.log('render called');
    return (
        <div>
            <h2>{count}</h2>
            <button onClick={onClick}>증가</button>
        </div>
    )
}