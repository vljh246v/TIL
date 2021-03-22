import React, {useState} from 'react';

export default function App(){
    const [text, setText] = useState(INITIAL_TEXT)
    const [showText, setShowText] = useState(true);

    const setInitialText = useCallback(ref => ref && setText(INITIAL_TEXT), []);

    return (
        <div>
            {showText && (
                // 생성될때 ref의 레퍼런스가 넘어오고 초기 text로 셋팅해줌
                // 다만 아래 예제에서는 새로운 text 입력시마다
                // 랜더링되고, 새로운 함수가 입력되면서 계속해서 초기값으로 설정됨
                <input
                    type="text"
                    ref={setInitialText}
                    value={text}
                    onChange={e => setText(e.target.value)}
                />
            )}
            {/* 버튼 클릭시 toggle 값에 따라 input 요소를 나타낸다. */}
            <button onClick={() => setShowText(!showText)}>보이기/가리기</button>
        </div>
        
    );
}

const INITIAL_TEXT = "안녕하세요";
