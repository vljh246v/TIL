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
import React, {useRef, useState, useContext, createContext} from 'react';

export default function App(){
    const boxListRef = useRef({});

    function onClick(){
        let maxRight = 0;
        let maxId = '';
        for(const box of BOX_LIST){
            const ref = boxListRef.current[box.id];
            if(ref){
                const rect = ref.getBoundingClientRect();
                if(maxRight < rect.right){
                    maxRight = rect.right;
                    maxId = box.id;
                }
            }
        }
        alert(`오른쪽 끝 요소는 ${maxId} 입니다.`)
    }

    return (
        <div>
            <div
                style={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    width: '100vw',
                    height: '100%'
                }}
            >
                {BOX_LIST.map(item => (
                    <div
                        key={item.id}
                        ref={ref => boxListRef.current[item.id] = ref}
                        style={{ 
                            flex: '0 0 auto',
                            width: item.width,
                            height: 100,
                            backgroundColor: 'yellow',
                            border: 'solid 1px red'
                        }}
                    >{`box_${item.id}`}</div>
                ))}
            </div>
            <button onClick={onClick}>오른쪽 끝 요소는?</button>
        </div>
    );
}

const BOX_LIST = [
    {id: 1, width: 70},
    {id: 2, width: 100},
    {id: 3, width: 80},
    {id: 4, width: 100},
    {id: 5, width: 90},
    {id: 6, width: 60},
    {id: 7, width: 120}
]