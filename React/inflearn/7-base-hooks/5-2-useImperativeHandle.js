import React, {useRef} from 'react';

import Profile from './5-1-useImperativeHandle'

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