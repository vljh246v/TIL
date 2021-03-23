import React, {useReducer, useCallback, useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';

export default function App(){
    // state는 현재 상태, dispatch는 액션을 발생시키는 함수
    const [state, dispatch] = useReducer(reducer, INITIAL_STATE);
    return (
        <div>
            <p>{`name is ${state.name}`}</p>
            <p>{`age is ${state.age}`}</p>
            <input
                type="text"
                value={state.name}
                // dispatch에 action 값을 전달
                onChange={e => 
                    dispatch({type: 'setName', name: e.currentTarget.value})
                }
            />
            <input
                type="number"
                value={state.age}
                onChange={e => dispatch({type: 'setAge', age: e.currentTarget.value})}
            />
        </div>
    );
}

const INITIAL_STATE = {name: 'empty', age: 0};
const MAX_AGE = 50;
function reducer(state, action) {
    // 상태값과 action이 입력이 되고, action 을 보고 상태값을 어떻게 변경할지
    switch (action.type) {
        case 'setName':
            return {...state, name: action.name};
        case 'setAge':
            if(action.age > MAX_AGE){
                return {...state, age:MAX_AGE};
            } else {
                return {...state, age: action.age};
            }
        default:
            return state;
    }
}
