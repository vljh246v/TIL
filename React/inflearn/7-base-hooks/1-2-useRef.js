import React, {useRef, useState, useContext, createContext} from 'react';

export default function App(){
    const [age, setAge] = useState(20);
    const prevAgeRef = useRef(20);
    useEffect(() => {
        // 랜더링 후에 현재 age를 저장
        prevAgeRef.current = age;
    }, [age]);
    // 랜더링 도중 이전 값을 저장
    const prevAge = prevAgeRef.current;
    const text = age == prevAge ? 'same' : age > prevAge ? 'older' : 'younger';

    return (
        <div>
            <p>{`age ${age} is ${text} then age ${prevAge}`}</p>
            <button
                onClick={() => {
                    const age = Math.floor(Math.random() * 50 + 1);
                    setAge(age);
                }}
            >
                나이변경
            </button>
        </div>
    )
}