import React, {createContext, useState, useContext} from 'react';

const UserContext = createContext({username: 'unknown', helloCount: 0});
const SetUserContext = createContext(() => {});

export default function App(){
    const [user, setUser] = useState({username: 'mike', helloCount: 0})
    return (
        <div>
            <SetUserContext.Provider value={setUser}>
                <UserContext.Provider value={user}>
                    <Profile/>
                </UserContext.Provider>
            </SetUserContext.Provider>
        </div>
    );
}


function Profile() {
    const setUser = useContext(SetUserContext);
    const {username, helloCount} = useContext(UserContext);

    return (
        <React.Fragment>
            <p>{`${username}님 안녕하세요`}</p>
            <p>{`인사 횟수 : ${helloCount}`}</p>
            <button onClick={() => setUser({username, helloCount: helloCount + 1})}>
                인사하기
            </button>
        </React.Fragment>
    )
}


// import React, { useState, useEffect} from 'react';
// import ReactDOM from 'react-dom';

// export default function App(){
//     const [count, setCount] = useState(0);
//     function onClick() {
//       ReactDOM.unstable_batchedUpdates(() => {
//         setCount(v => v + 1);
//         setCount(v => v + 1);
//       });
//     }
//     useEffect(() => {
//       window.addEventListener('click', onClick);
//       return () => {
//         console.log('!!')
//         window.removeEventListener('click', onClick)
//       };
//     })
//     console.log('render called');
//     return (
//         <div>
//             <h2>{count}</h2>
//             <button onClick={onClick}>증가</button>
//         </div>
//     )
// }

// import React, { useState, useEffect} from 'react'

// export default function App(){
//     const [count, setCount] = useState(0);
//     useEffect(() => {
//         document.title = `업데이트 횟수: ${count}`
//     })
//     console.log('render called');
//     return (
//         <button onClick={() => setCount(count + 1)}>
//             increase
//         </button>
//     )
// }

// import React, { useState} from 'react'
// import Profile from './Profile'

// export default function App(){
//     const [userId, setUserId] = useState(0)
//     return (
//         <>
//             <Profile userId={userId}></Profile>
//             <button onClick={() => setUserId(userId + 1)}>User Id 변경</button>
//         </>
//     )
// }

// import WidthPrinter from './WidthPrinter';

// export default function App() {
//     const [count, setCount] = useState(0);
//     return (
//         <>
//             {count === 0 && <WidthPrinter/>}
//             <button onClick = {() => setCount(count + 1)}>증가</button>
//         </>
//     );
// }


// 5 - context

// import React, {createContext, useState, useContext} from 'react';

// const UserContext = createContext('unknown');

// export default function App() {
//     const [name, setName] = useState('mike');
//     return (
//         <div>
//             <UserContext.Provider value={name}>
//                 <div>상단 메뉴</div>
//                 <Profile></Profile>
//                 <div>하단 메뉴</div>
//                 <input type="text" value={name} onChange={e => setName(e.target.value)}/>
//             </UserContext.Provider>
//         </div>
//     );
// }

// const UserContext = createContext({username: 'unknown', helloCount: 0});


// export default function App(){
//     // const [username, setUsername] = useState('demo');
//     const [user, setUser] = useState({username: 'demo', age: 31});
//     const [count, setCount] = useState(0);
//     console.log('App render')
//     return (
//         <div>
//             <UserContext.Provider value={user}>
//                 <Profile/>
//                 <button onClick={() => setCount(count + 1)}>증가</button>
//             </UserContext.Provider>
//         </div>
//     );
// }

// const Profile = React.memo(function () {
//     return (
//         <div>
//             <Greeting></Greeting>
//             {/* ... */}
//         </div>
//     );
// });

// function Greeting() {
//     console.log('Greeting render')
//     const {username} = useContext(UserContext)
//     return <p>{`${username}님 안녕하세요`}</p>
// }



// 6 - ref
// import React, { useRef, useEffect} from 'react';

// export default function App(){
//     // useRef 훅을 사용
//     const inputRef = useRef();
//     useEffect(() => {
//         inputRef.current.focus();
//     }, []);

//     return (
//         <div>
//             {/* 반환하는 속성값에 useRef에서 return 값을 입력해주면 됨 */}
//             <input type="text" ref={inputRef} /> 
//             <button>저장</button>
//         </div>
//     )
// }

// import React, { useRef, useEffect} from 'react';

// export default function App(){
//     const inputRef = useRef();
//     const buttonRef = useRef();
//     useEffect(() => {
//         inputRef.current.focus();
//     }, []);

//     return (
//         <div>
//             <InputAndSave inputRef={inputRef}/>
//             <Button ref={buttonRef} onClick={() => console.log("!!!")}/>
//             <button onClick={() => inputRef.current.focus()}>텍스트로 이동</button>
//         </div>
//     );
// }

// const Button = React.forwardRef(function ({onClick}, ref) {
//     return (
//         <button onClick={onClick} ref={ref}>
//             저장
//         </button>
//     );
// })


// function InputAndSave({inputRef}) {
//     return (
//         <div>
//             <input type="text" ref={inputRef}/>
//             <button>저장</button>
//         </div>
//     )
// }

// import React, {useState, useCallback} from 'react';

// export default function App(){
//     const [text, setText] = useState(INITIAL_TEXT)
//     const [showText, setShowText] = useState(true);

//     const setInitialText = useCallback(ref => ref && setText(INITIAL_TEXT), []);

//     return (
//         <div>
//             {showText && (
//                 // 생성될때 ref의 레퍼런스가 넘어오고 초기 text로 셋팅해줌
//                 // 다만 아래 예제에서는 새로운 text 입력시마다
//                 // 랜더링되고, 새로운 함수가 입력되면서 계속해서 초기값으로 설정됨
//                 <input
//                     type="text"
//                     ref={setInitialText}
//                     value={text}
//                     onChange={e => setText(e.target.value)}
//                 />
//             )}
//             {/* 버튼 클릭시 toggle 값에 따라 input 요소를 나타낸다. */}
//             <button onClick={() => setShowText(!showText)}>보이기/가리기</button>
//         </div>
        
//     );
// }

// const INITIAL_TEXT = "안녕하세요";


// import React, {useRef, useState, useContext, createContext} from 'react';

// export default function App(){
//     const boxListRef = useRef({});

//     return (
//         <div>
//             <div
//                 style={{
//                     display: 'flex',
//                     flexWrap: 'wrap',
//                     width: '100vw',
//                     height: '100%'
//                 }}
//             >
//                 {BOX_LIST.map(item => (
//                     <div
//                         key={item.id}
//                         ref={ref => boxListRef.current[item.id] = ref}
//                         style={{ 
//                             flex: '0 0 auto',
//                             width: item.width,
//                             height: 100,
//                             backgroundColor: 'yellow',
//                             border: 'solid 1px red'
//                         }}
//                     >{`box_${item.id}`}</div>
//                 ))}
//             </div>
//             <button onClick={() => {}}>오른쪽 끝 요소는?</button>
//         </div>
//     );
// }

// const BOX_LIST = [
//     {id: 1, width: 70},
//     {id: 2, width: 100},
//     {id: 3, width: 80},
//     {id: 4, width: 100},
//     {id: 5, width: 90},
//     {id: 6, width: 60},
//     {id: 7, width: 120}
// ]
// import React, {useRef, useState, useContext, createContext} from 'react';

// export default function App(){
//     const boxListRef = useRef({});

//     function onClick(){
//         let maxRight = 0;
//         let maxId = '';
//         for(const box of BOX_LIST){
//             const ref = boxListRef.current[box.id];
//             if(ref){
//                 const rect = ref.getBoundingClientRect();
//                 if(maxRight < rect.right){
//                     maxRight = rect.right;
//                     maxId = box.id;
//                 }
//             }
//         }
//         alert(`오른쪽 끝 요소는 ${maxId} 입니다.`)
//     }

//     return (
//         <div>
//             <div
//                 style={{
//                     display: 'flex',
//                     flexWrap: 'wrap',
//                     width: '100vw',
//                     height: '100%'
//                 }}
//             >
//                 {BOX_LIST.map(item => (
//                     <div
//                         key={item.id}
//                         ref={ref => boxListRef.current[item.id] = ref}
//                         style={{ 
//                             flex: '0 0 auto',
//                             width: item.width,
//                             height: 100,
//                             backgroundColor: 'yellow',
//                             border: 'solid 1px red'
//                         }}
//                     >{`box_${item.id}`}</div>
//                 ))}
//             </div>
//             <button onClick={onClick}>오른쪽 끝 요소는?</button>
//         </div>
//     );
// }

// const BOX_LIST = [
//     {id: 1, width: 70},
//     {id: 2, width: 100},
//     {id: 3, width: 80},
//     {id: 4, width: 100},
//     {id: 5, width: 90},
//     {id: 6, width: 60},
//     {id: 7, width: 120}
// ]

// ch.07

// import React, {useRef, useState, useContext, createContext, useEffect} from 'react';

// export default function App(){
//     const [age, setAge] = useState(20);
//     const prevAgeRef = useRef(20);
//     useEffect(() => {
//         // 랜더링 후에 현재 age를 저장
//         prevAgeRef.current = age;
//     }, [age]);
//     // 랜더링 도중 이전 값을 저장
//     const prevAge = prevAgeRef.current;
//     const text = age == prevAge ? 'same' : age > prevAge ? 'older' : 'younger';

//     return (
//         <div>
//             <p>{`age ${age} is ${text} then age ${prevAge}`}</p>
//             <button
//                 onClick={() => {
//                     const age = Math.floor(Math.random() * 50 + 1);
//                     setAge(age);
//                 }}
//             >
//                 나이변경
//             </button>
//         </div>
//     )
// }

// import React, {useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';

// export default function App(){
//     const [v1, setV1] = useState(0);
//     const [v2, setV2] = useState(0);
//     const [v3, setV3] = useState(0);
//     // 첫번째 매개변수로 함수를 입력 , 함수 실행값을 기억함, 뒤 배열 매개변수가 변경되면 다시 실행함
//     const value = useMemo(() => runExpensiveJob(v1, v2), [v1, v2]);

//     return (
//         <>
//             <p>{`value is ${value}`}</p>
//             <button
//                 onClick={() => {
//                     setV1(Math.random());
//                     setV2(Math.random());
//                 }}
//             >
//                 v1/v2 수정
//             </button>
//             <p>{`v3 is ${v3}`}</p>
//             <button onClick={() => setV3(Math.random())}>v3 수정</button>
//         </>
//     );
// }

// function runExpensiveJob(v1, v2) {
//     console.log('runExpensiveJob is called');
//     // 계산량이 많음 함수라고 가정
//     return v1 + v2;
// }

// import React, {useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';

// export default function App(){
//     const [name, setName] = useState('');
//     const [age, setAge] = useState(0);
//     const [v1, setV1] = useState(0);

//     return (
//         <div>
//             <p>{`name is ${name}`}</p>
//             <p>{`age is ${age}`}</p>
//             <UserEdit
//             // 함수 속성값 전달시 랜더링시 마다 새로운 함수를  전달함
//             // 그럼 함수 랜더링시 마다 속성값이 변경
//             // 자식 컴포넌트 입장에서는 새로운 속성값을 입력받게 됨, 불필요한 랜더링
//                 onSave={() => saveToServer(name, age)}
//                 setName={setName}
//                 setAge={setAge}
//             ></UserEdit>
//             <p>{`v1 : ${v1}`}</p>
//             <button onClick={() => setV1(Math.random())}>
//                 v1수정
//             </button>
//         </div>
//     );
// }

// const UserEdit = React.memo(function({onSave, setName, setAge}){
//     console.log('UserEdit render');
//     return null;
// });

// function saveToServer(name, age){}


// import React, {useCallback, useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';

// export default function App(){
//     const [name, setName] = useState('');
//     const [age, setAge] = useState(0);
//     const [v1, setV1] = useState(0);
//     const onSave = useCallback(() => saveToServer(name, age), [name, age]);

//     return (
//         <div>
//             <p>{`name is ${name}`}</p>
//             <p>{`age is ${age}`}</p>
//             <UserEdit
//             // 함수 속성값 전달시 랜더링시 마다 새로운 함수를  전달함
//             // 그럼 함수 랜더링시 마다 속성값이 변경
//             // 자식 컴포넌트 입장에서는 새로운 속성값을 입력받게 됨, 불필요한 랜더링
//                 onSave={onSave}
//                 setName={setName}
//                 setAge={setAge}
//             ></UserEdit>
//             <p>{`v1 : ${v1}`}</p>
//             <button onClick={() => setV1(Math.random())}>
//                 v1수정
//             </button>
//         </div>
//     );
// }

// const UserEdit = React.memo(function({onSave, setName, setAge}){
//     console.log('UserEdit render');
//     return null;
// });

// function saveToServer(name, age){}


// import React, {useReducer, useCallback, useRef, useMemo, useState, useContext, createContext, useEffect} from 'react';

// export default function App(){
//     // state는 현재 상태, dispatch는 액션을 발생시키는 함수
//     const [state, dispatch] = useReducer(reducer, INITIAL_STATE);
//     return (
//         <div>
//             <p>{`name is ${state.name}`}</p>
//             <p>{`age is ${state.age}`}</p>
//             <input
//                 type="text"
//                 value={state.name}
//                 // dispatch에 action 값을 전달
//                 onChange={e => 
//                     dispatch({type: 'setName', name: e.currentTarget.value})
//                 }
//             />
//             <input
//                 type="number"
//                 value={state.age}
//                 onChange={e => dispatch({type: 'setAge', age: e.currentTarget.value})}
//             />
//         </div>
//     );
// }

// const INITIAL_STATE = {name: 'empty', age: 0};
// const MAX_AGE = 50;
// function reducer(state, action) {
//     // 상태값과 action이 입력이 되고, action 을 보고 상태값을 어떻게 변경할지
//     switch (action.type) {
//         case 'setName':
//             return {...state, name: action.name};
//         case 'setAge':
//             if(action.age > MAX_AGE){
//                 return {...state, age:MAX_AGE};
//             } else {
//                 return {...state, age: action.age};
//             }
//         default:
//             return state;
//     }
// }

// import React, {useRef} from 'react';

// import Profile from './Profile'

// export default function App(){
//     const profileRef = useRef();
//     const onClick = () => {
//         if(profileRef.current){
//             console.log('current name length: ', profileRef.current.getNameLength());
//             profileRef.current.addAge(5);
//         }
//     };
//     return (
//         <div>
//             {/* useRef 를 사용해서 Profile 컴포넌트의 ref 속성값으로 사용하고 있음 */}
//             <Profile ref={profileRef}/>
//             <button onClick={onClick}>add age 5</button>
//         </div>
//     )
// }


// import React from 'react';
// import useChangeAppState, {
//     STATE_START,
//     STATE_RUNNING,
// } from './useChangeAppState'

// export default function App() {
//      const [state, next] = useChangeAppState(true);
//      const msg = 
//         state === STATE_START
//         ? '앱 시작'
//         : state === STATE_RUNNING
//         ? '앱 실행 중'
//         : '앱 종료';

//     return (
//         <div>
//             <p>{msg}</p>
//             <button onClick={next}>next</button>
//         </div>
//     )

// }