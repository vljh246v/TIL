import React, {useCallback, useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';

export default function App(){
    const [name, setName] = useState('');
    const [age, setAge] = useState(0);
    const [v1, setV1] = useState(0);
    const onSave = useCallback(() => saveToServer(name, age), [name, age]);

    return (
        <div>
            <p>{`name is ${name}`}</p>
            <p>{`age is ${age}`}</p>
            <UserEdit
            // 함수 속성값 전달시 랜더링시 마다 새로운 함수를  전달함
            // 그럼 함수 랜더링시 마다 속성값이 변경
            // 자식 컴포넌트 입장에서는 새로운 속성값을 입력받게 됨, 불필요한 랜더링
                onSave={onSave}
                setName={setName}
                setAge={setAge}
            ></UserEdit>
            <p>{`v1 : ${v1}`}</p>
            <button onClick={() => setV1(Math.random())}>
                v1수정
            </button>
        </div>
    );
}

const UserEdit = React.memo(function({onSave, setName, setAge}){
    console.log('UserEdit render');
    return null;
});

function saveToServer(name, age){}