# 1. ref 속성값으로 자식 요소에 접근하기

## 1.1. dom 요소에 접근하기

- 리엑트로 작업하다보면 실제 돔 요소에 접근해야 할 때가 있음(ex : Dom 요소에 포커스를 주거나 크기, 스크롤 위치등을 알기위해 등등)

- ref 속성값을 사용하면 자식 요소에 직접 접근할 수 있음

- useRef hook을 사용하면 됨

  ```react
  import React, { useRef, useEffect} from 'react';
  
  export default function App(){
      // useRef 훅을 사용
      const inputRef = useRef();
      useEffect(() => {
        // dom 랜더링 이후에 접근 가능해서 부수효과 함수 안에서 사용
          inputRef.current.focus();
      }, []);
  
      return (
          <div>
              {/* 반환하는 속성값에 useRef에서 return 값을 입력해주면 됨 */}
              <input type="text" ref={inputRef} /> 
              <button>저장</button>
          </div>
      )
  }
  ```

## 1.2 컴포넌트에 접근하기

* dom 요소가 아니라 일반적은 컴포넌트에도 사용할 수 있음

* 컴포넌트가 클래스형 컴포넌트라면 해당 클래스의 메소드를 호출 가능하게 함

* 함수형 컴포넌트에서도 useImperativeHandle 이라는 훅을 사용하면 클래스형 멤버 변수가 메서드에 접근하는 것처럼 함수형 컴포넌트 내부의 변수와 함수를 외부로 노출할 수 있음

  ```react
  import React, { useRef, useEffect} from 'react';
  
  export default function App(){
      const inputRef = useRef();
      useEffect(() => {
          inputRef.current.focus();
      }, []);
  
      return (
          <div>
              <input type="text" ref={inputRef} /> 
              <Box ref={inputRef}></Box>
              <button>저장</button>
          </div>
      )
  }
  ```



## 1.3. 함수형 컴포넌트에서 ref 사용하기

- 별다른 처리를 하지 않았다면 함수형 컴포넌트에 ref 속성값을 입력 할 수는 없음

- 속성으로 받아서 내부 자식에게 전달하는 방법이 있음

  ```react
  import React, { useRef, useEffect} from 'react';
  
  export default function App(){
      const inputRef = useRef();
      useEffect(() => {
          inputRef.current.focus();
      }, []);
  
      return (
          <div>
              <InputAndSave ref={inputRef}/>
              <button onClick={() => inputRef.current.focus()}>텍스트로 이동</button>
          </div>
      );
  }
  
  function InputAndSave({inputRef}) {
      return (
          <div>
              <input type="text" ref={inputRef}/>
              <button>저장</button>
          </div>
      )
  }
  ```

  

