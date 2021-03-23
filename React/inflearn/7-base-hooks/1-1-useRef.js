import React, {useRef, useState, useContext, createContext} from 'react';

export default function App(){
    const timerIdRef = useRef(-1);
    useEffect(() => {
        // setTimeout 반환값을 저장
        timerIdRef.current = setTimeout(() => {}, 1000);
    });

    useEffect(() => {
        if(timerIdRef.current >= 0) {
            clearTimeout(timerIdRef.current);
        }
    });
}