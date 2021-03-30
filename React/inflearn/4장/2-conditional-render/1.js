function GreetingA({isLogin, name}) {
    if(isLogin) {
        return <p>{`${name}님 안녕하세요`}</p>;
    } else {
        return <p>권한이 없습니다.</p>
    }
}

function GreetingB({isLogin, name}){
    return <p>{isLogin ? `${name}님 안녕하세요.` : `권한이 없습니다.`}</p>;
}

