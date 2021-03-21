import React, { useRef, useEffect} from 'react';

export default function App(){
    // useRef 훅을 사용
    const inputRef = useRef();
    useEffect(() => {
        inputRef.current.focus();
    }, []);

    return (
        <div>
            {/* 반환하는 속성값에 useRef에서 return 값을 입력해주면 됨 */}
            <input type="text" ref={inputRef} /> 
            <Box ref={inputRef}></Box>
            <button>저장</button>
        </div>
    )
}