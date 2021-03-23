import React, {useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';

export default function App(){
    const [v1, setV1] = useState(0);
    const [v2, setV2] = useState(0);
    const [v3, setV3] = useState(0);
    // 첫번째 매개변수로 함수를 입력 , 함수 실행값을 기억함, 뒤 배열 매개변수가 변경되면 다시 실행함
    const value = useMemo(() => runExpensiveJob(v1, v2), [v1, v2]);

    return (
        <>
            <p>{`value is ${value}`}</p>
            <button
                onClick={() => {
                    setV1(Math.random());
                    setV2(Math.random());
                }}
            >
                v1/v2 수정
            </button>
            <p>{`v3 is ${v3}`}</p>
            <button onClick={() => setV3(Math.random())}>v3 수정</button>
        </>
    );
}

function runExpensiveJob(v1, v2) {
    console.log('runExpensiveJob is called');
    return v1 + v2;
}