import React, {useRef, useState, useContext, createContext} from 'react';

export default function App(){
    const inputRef = useRef();
    const [showText, setShowText] = useState(true);

    return (
        <div>
            {/* 조건부 랜더링을 통해 ref를 설정할 경우 주의 !  */}
            {showText && <input type="text" ref={inputRef}></input>}
            <button onClick={() => setShowText(!showText)}>
                텍스트 보이기/가리기
            </button>
            {/* current 검사 로직 추가 */}
            <button onClick={() => input.current && inputRef.current.focus()}>텍스트로 이동</button>
        </div>
    );
}