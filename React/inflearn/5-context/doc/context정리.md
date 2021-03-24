# 1. context

## 1.1. context 기본 사용법

- 상위 -> 하위 데이터 전달시 속성값으로 전달

- 많은 수의 컴포넌트로 구성일경우 컴포넌트 전달 코드를 반복적으로 작성해야함

  ```react
  export default function App(){
      return (
          <div>
              <div>상단 메뉴</div>
              <Profile username="mike"></Profile>
              <div>하단 메뉴</div>
          </div>
      );
  }
  ...
  function Profile({username}) {
      return (
          <div>
              <Greeting username={username}></Greeting>
              {/* ... */}
          </div>
      );
  }
  ...
  function Greeting({username}) {
      return <p>{`${username}님 안녕하세요`}</p>
  }
  ```

- Context를 사용하면 간편하게 작성 가능

  ```react
  // context를 사용하기 위해 createContext를 불러와야함
  import React, {createContext} from 'react';
  
  // context 초기값 설정
  // 반환 받은 객체 안에는 Provider와 Consumer 컴포넌트가 들어있음
  const UserContext = createContext('unknown');
  
  export default function App() {
      return (
          <div>
              <UserContext.Provider value="mike">
                  <div>상단 메뉴</div>
                  <Profile></Profile>
                  <div>하단 메뉴</div>
              </UserContext.Provider>
          </div>
      );
  }
  
  function Profile() {
      return (
          <div>
              <Greeting></Greeting>
              {/* ... */}
          </div>
      );
  }
  
  function Greeting() {
      return (
        <!-- Provider 에서 value 값을 지정해주면 Consumer에서 사용가능 -->
          <UserContext.Consumer>
            <!--render prop 패턴,  children 함수를 호출함 -->
              {value => <p>{`${value}님 안녕하세요`}</p>}
          </UserContext.Consumer>
      );
  }
  ```

  > render prop : 반복되는 로직을 쉽게 재사용할 수 있게 해주고, 컴포넌트 코드를 작성하는 과정에서 컴포넌트를 함수로 감싸는 것이 아니라, JSX에서 랜더링 하는 방식으로 사용할 수 있음

* Consumer가 사용됐을때 필요한 값을 찾기 위해 부모로 올라가면서 가장 가까운 Provider를 찾음, 루트까지 가도 없으면 초기값을 사용

* Provider 의 value가 변경되면 하위 모든 Consumer 컴포넌트들은 다시 랜더링

*  중간에 위치한 컴포넌트는 다시 랜더링 X

  ```react
  ...
  const Profile = React.memo(function () {
      return (
          <div>
              <Greeting></Greeting>
              {/* ... */}
          </div>
      );
  });
  
  function Greeting() {
      return (
          <UserContext.Consumer>
              {username => <p>{`${username}님 안녕하세요`}</p>}
          </UserContext.Consumer>
      );
  }
  ...
  ```

* Consumer 컴포넌트 외부에서는 context 값을 사용할 수 없지만, hook 을 사용하면 context 값을 사용할 수 있음

  ```react
  import React, {createContext, useState, useContext} from 'react';
  
  ...
  
  function Greeting() {
      const username = useContext(UserContext)
      return <p>{`${username}님 안녕하세요`}</p>
  }
  ```

*  때에 따라서 컨텍스트를 여러개 사용할 수 있음
* 컨텍스트를 나누면 랜더링에 이점이 있음 -> 불필요한 랜더링을 줄일 수 있음

## 1.2. 하위 컴포넌트에서 컨텍스트 데이터를 수정하는 방법

* 데이터 수정 가능한 함수를 별도의 컨텍스트로 분리

* 데이터를 내려주는 컨텍스트 , 상태값 변경 함수 컨텍스트 분리

  ```react
  //edit.js
  import React, {createContext, useState} from 'react';
  
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
  
  // edit-child.js
  import React, {useContext} from 'react';
  
  function Greeting() {
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
  ```

  

## 1.3. 컨텍스트를 사용할때 주의점

### 1.3.1 불필요한 랜더링

* value 값에 객체를 할당한다면 랜더링 될때마다 새로운 객체가 생성되고, 새로운 값으로 인식함 -> 그렇기 때문에 Consumer는 불필요한 랜더링이 될 수 있음

  ```react
  import React, {createContext, useState, useContext} from 'react';
  
  const UserContext = createContext({username: 'unknown', helloCount: 0});
  
  
  export default function App(){
      const [username, setUsername] = useState('demo');
      const [age, setAge] = useState(0);
      const [count, setCount] = useState(0);
      console.log('App render')
      return (
          <div>
              <UserContext.Provider value={{username, age}}>
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
  ```

* 위 예제 에서도 count 값을 변경할때 App 컴포넌트가 랜더링 되고, UserContext.Provider value 값이 새로운 객체로 그려지면서 Consumer 또한 새롭개 그려짐

*  매번 새롭게 그려지는것을 방지하기 위해 하나의 state 로 관리 가능

  ```react
  ...
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
   ...
  ```

### 1.3.2 Consumer 의 초기값 사용

* Consumer 입장에서는 root level 까지 컨텍스트를 찾아가다가 없다면 초기값을 사용

* Consumer를 사용하는 입장에서는 Provider 컴포넌트 안에 랜더링 되도록 작성을 해야함

* 일반적으로는 Provider 컴포넌트를 전체 jsx를 감싸도록 하여 못찾는 일이 없더록 작성함

  ```react
  import React, {createContext} from 'react';
  
  const UserContext = createContext('unknown');
  
  export default function App(){
      return (
          <div>
              <UserContext.Provider value="demo">
                  {/* ... */}
              </UserContext.Provider>
              <Profile/>
          </div>
      );
  }
  ```

  