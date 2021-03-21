import React from 'react';

export default function App(){
    return (
        <div>
            <div>상단 메뉴</div>
            <Profile username="mike"></Profile>
            <div>하단 메뉴</div>
        </div>
    );
}

function Profile({username}) {
    return (
        <div>
            <Greeting username={username}></Greeting>
            {/* ... */}
        </div>
    );
}

function Greeting({username}) {
    return <p>{`${username}님 안녕하세요`}</p>
}