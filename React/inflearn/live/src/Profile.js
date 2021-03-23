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


// import React, {useContext, useState, useImperativeHandle} from 'react';
// import SetUserContext from './App'
// import UserContext from './App'

// export default function Profile() {
//     const setUser = useContext(SetUserContext);
//     const {username, helloCount} = useContext(UserContext);

//     return (
//         <React.Fragment>
//             <p>{`${username}님 안녕하세요`}</p>
//             <p>{`인사 횟수 : ${helloCount}`}</p>
//             <button onClick={() => setUser({username, helloCount: helloCount + 1})}>
//                 인사하기
//             </button>
//         </React.Fragment>
//     )
// }
