# 1. React 의 내장 훅

* ~~useState~~
* ~~useEffect~~
* ~~useContext~~
* useRef
* useMemo : 메모이제이션 기능 제공 
* useCallback : 메모이제이션 기능 제공 
* useReducer : 여러개 상태값을 하나의 훅으로 관리 가능
* useImperativeHandle
* useLayoutEffect
* useDebugValue

## 1.1. useRef

* useRef 객체는 꼭 돔 요소를 참조할 때만 사용할 수 있는건 아님

* 랜더링과 관련없는 값을 저장할때 유용

* useState를 활용해서 관리하면, 대상 데이터가 변경이 일어나면 다시 랜더링이 일어남

  ```react
  import React, {useRef} from 'react';
  
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
  ```

* 이전값을 저장한 예제

  ```react
  import React, {useRef, useState, useContext, createContext, useEffect} from 'react';
  
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
  ```

## 1.2. useMemo

* 계산량이 많은 함수의 반환값을 재활용 하는 용도

  ```react
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
      // 계산량이 많음 함수라고 가정
      return v1 + v2;
  }
  ```

  

## 1.3. useCallback

* 함수 메모이제이션에 특화된 훅

* 잘못된 예

  ```react
  import React, {useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';
  
  export default function App(){
      const [name, setName] = useState('');
      const [age, setAge] = useState(0);
      const [v1, setV1] = useState(0);
  
      return (
          <div>
              <p>{`name is ${name}`}</p>
              <p>{`age is ${age}`}</p>
              <UserEdit
              // 함수 속성값 전달시 랜더링시 마다 새로운 함수를  전달함
              // 그럼 함수 랜더링시 마다 속성값이 변경
              // 자식 컴포넌트 입장에서는 새로운 속성값을 입력받게 됨, 불필요한 랜더링
                  onSave={() => saveToServer(name, age)}
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
  React.memo를 사용해도 새로운 값이 전달되는 것이기 때문에 호출
  const UserEdit = React.memo(function({onSave, setName, setAge}){
      console.log('UserEdit render');
      return null;
  });
  
  function saveToServer(name, age){}
  ```

* 위 예제를 아래와 같이 수정 가능

  ```react
  import React, {useCallback, useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';
  
  export default function App(){
      const [name, setName] = useState('');
      const [age, setAge] = useState(0);
      const [v1, setV1] = useState(0);
    // useCallback 사용, 동일하게 의존성 배열을 사용함
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
  ```

  

## 1.4. useReducer

* 여러개의 상태값 관리에는 useReducer 훅을 사용

* 리듀서는 현재 상태, 그리고 업데이를 위해 필요한 정보를 담은 액션 값을 전달받아 새로운 상태를 반환하는 함수

* 컴포넌트 업데이트 로직을 외부로 뺄 수 있음

  ```react
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
  ```



* 상위 컴포넌트의 상태값을 자식 컴포넌트에서 변경해야 하는 경우가 있음

* 이를 위해 트리의 깊은 곳까지 이벤트  처리 함수를 전달하기도함 -> 코드 가독성이 떨어짐

* useReducer 훅과 Context api를 같이 이용하면 상위 컴포넌트에서 트리 깊은 곳으로 이벤트 전달함수를 쉽게 전달 가능

  ```react
  import React, {useReducer, useCallback, useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';
  
  export const ProfileDispatch = React.createContext(null);
  
  export default function App(){
      // state는 현재 상태, dispatch는 액션을 발생시키는 함수
      const [state, dispatch] = useReducer(reducer, INITIAL_STATE);
      return (
          <div>
              <p>{`name is ${state.name}`}</p>
              <p>{`age is ${state.age}`}</p>
              {/* 필요한 하위 컴포넌트에서는  dispatch함수를 이용해서 상태값 변경 */}
              <ProfileDispatch.provider value={dispatch}>
                  <SomeComponent></SomeComponent>
              </ProfileDispatch.provider>
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
  ```



## 1.5. useImperativeHandle 

* 클래스형 컴포넌트의 부모 컴포넌트는 ref 객체를 통해서 자식 컴포넌트의 메서드를 호출할 수 있다.(자식 컴포넌트에 대한 의존성이 생김)

* useImperativeHandle를 사용하면 함수형 컴포넌트에도 멤버 변수나 멤버 함수가 있는 것처럼 만들 수 있음

* 부모 컴포넌트에서 자식 컴포넌트에 함수를 호출 가능

  ```react
  // Profile.js
  import React, {forwardRef, useState, useImperativeHandle} from 'react';
  
  // 속성값은 사용하지 않고 ref 속성값만 사용하고 있음
  function Profile(_, ref){
      const [name, setName] = useState('mike');
      const [age, setAge] = useState(0);
  
      // ref 값을 useImperativeHandle 매개변수로 입력
      // 함수를 반환하고 있는데 부모의 ref 객체가 참조하는 값
      useImperativeHandle(ref,() => ({
          addAge: value => setAge(age + value),
          getNameLength: () => name.length,
      }));
  
      return (
          <div>
              <p>{`name is ${name}`}</p>
              <p>{`age is ${age}`}</p>
              {/*  */}
          </div>
      )
  }
  
  export default forwardRef(Profile);
  // https://ko.reactjs.org/docs/react-api.html#reactforwardref
  
  // parent
  import React, {useRef} from 'react';
  
  import Profile from './Profile'
  
  export default function App(){
      const profileRef = useRef();
      const onClick = () => {
          if(profileRef.current){
              console.log('current name length: ', profileRef.current.getNameLength());
              profileRef.current.addAge(5);
          }
      };
      return (
          <div>
              {/* useRef 를 사용해서 Profile 컴포넌트의 ref 속성값으로 사용하고 있음 */}
              <Profile ref={profileRef}/>
              <button onClick={onClick}>add age 5</button>
          </div>
      )
  }
  ```

  

## 1.6. useLayoutEffect

* useEffect 부수함수는 렌더링 결과가 돔에 반영된 후에 비동기로 호출이 됨

* useLayoutEffect 훅은 useEffect 훅과 유사하게 동작하지만 부수효과 함수를 동기로 호출한다는 점이 다름

* useLayoutEffect 훅의 부수 효과 함수는 렌더링 결과가 돔에 반영된 직후에 바로 호출이 됨

* 그렇기 때문에 많은 연산일 경우 브라우저가 멈출 수도 있음

* 대부분 경우 useEffect를 사용하면 됨

* 렌더링 직후 돔 요소 값을 읽거나 조건에 따라 다시 렌더링 하고 싶을 경우 사용하기도함

  ```react
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
  ```

  

## 1.6. useDebugValue.js

* 커스텀 hook 내부에서 useDebugValue를 사용하면 디버깅할때 더 다양한 정보를 표시해줌