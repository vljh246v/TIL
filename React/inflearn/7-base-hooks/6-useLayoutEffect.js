import React, {useState, useEffect, useLayoutEffect} from 'react';

export default function App(){
    const [width, setWidth] = useState(200);
    // useEffect(() => {
    useLayoutEffect(() => {
        if(width > 500){
            setWidth(500);
        }
    }, [width]);
    return (
        <div>
            <div
                style={{
                    width, heigth: 100, backgroundColor: 'green'
                }}
            >test</div>
            <button
                onClick={() => {
                    const value = Math.floor(Math.random() * 499 + 1);
                    setWidth(value);
                }}
            >500 이하</button>
            <button
                onClick={() => {
                    const value = Math.floor(Math.random() * 500 + 501);
                    setWidth(value);
                }}
            >500 이상</button>
        </div>
    );
}