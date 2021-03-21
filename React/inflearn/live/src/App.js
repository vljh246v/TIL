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

import React, {createContext, useState, useContext} from 'react';

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

const UserContext = createContext({username: 'unknown', helloCount: 0});


export default function App(){
    // const [username, setUsername] = useState('demo');
    const [user, setUser] = useState({username: 'demo', age: 31});
    const [count, setCount] = useState(0);
    console.log('App render')
    return (
        <div>
            <UserContext.Provider value={user}>
                <Profile/>
                <button onClick={() => setCount(count + 1)}>증가</button>
            </UserContext.Provider>
        </div>
    );
}

const Profile = React.memo(function () {
    return (
        <div>
            <Greeting></Greeting>
            {/* ... */}
        </div>
    );
});

function Greeting() {
    console.log('Greeting render')
    const {username} = useContext(UserContext)
    return <p>{`${username}님 안녕하세요`}</p>
}