import React, { useState, useEffect} from 'react';
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
import Profile from './Profile'

export default function App(){
    const [userId, setUserId] = useState(0)
    return (
        <>
            <Profile userId={userId}></Profile>
            <button onClick={() => setUserId(userId + 1)}>User Id 변경</button>
        </>
    )
}

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