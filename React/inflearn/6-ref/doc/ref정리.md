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
    	const divRef = useRef();
      useEffect(() => {
        // dom 랜더링 이후에 접근 가능해서 부수효과 함수 안에서 사용
          inputRef.current.focus();
  //       	divRef.current 다음 발표 과제~
      }, []);
  
      return (
          <div>
          		<div ref={divRef}></div>
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
              <InputAndSave inputRef={inputRef}/>
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

- 함수형 컴포넌트에서도 ref 프로퍼티를 사용하는것이 더 일관성을 높여줄 수 있음

- 다만 함수형 컴포넌트에서 ref를 사용하면 리엑트 내부에서 처리하기 때문에 이전 ref 프로퍼티처럼 사용할 수 없음

- 이럴때 forwardRef라는 함수를 사용할 수 있음

  ```react
  import React, { useRef, useEffect} from 'react';
  
  export default function App(){
      const inputRef = useRef();
      const buttonRef = useRef();
      useEffect(() => {
          inputRef.current.focus();
      }, []);
  
      return (
          <div>
              <InputAndSave inputRef={inputRef}/>
              <Button ref={buttonRef} onClick={() => console.log("!!!")}/>
              <button onClick={() => inputRef.current.focus()}>텍스트로 이동</button>
          </div>
      );
  }
  
  const Button = React.forwardRef(function ({onClick}, ref) {
      return (
          <button onClick={onClick} ref={ref}>
              저장
          </button>
      );
  })
  ```

  

## 1.4. ref 속성값에 함수 입력하기

* ref 속서에 함수가 들어오면 해당 요소가 생성되거나 사라질때 한번씩 실행됨

* 생성시에는 해당 요소의 레퍼런스가, 사라질대는 null 값이 넘어옴

  ```react
  import React, {useState} from 'react';
  
  export default function App(){
      const [text, setText] = useState(INITIAL_TEXT)
      const [showText, setShowText] = useState(true);
  
      return (
          <div>
              {showText && (
                  // 생성될때 ref의 레퍼런스가 넘어오고 초기 text로 셋팅해줌
                  // 다만 아래 예제에서는 새로운 text 입력시마다
                  // 랜더링되고, 새로운 함수가 입력되면서 계속해서 초기값으로 설정됨
                  <input
                      type="text"
                      ref={ref => ref && setText(INITIAL_TEXT)}
                      value={text}
                      onChange={e => setText(e.target.value)}
                  />
              )}
              {/* 버튼 클릭시 toggle 값에 따라 input 요소를 나타낸다. */}
              <button onClick={() => setShowText(!showText)}>보이기/가리기</button>
          </div>
          
      );
  }
  
  const INITIAL_TEXT = "안녕하세요";
  ```

  * 계속해서 새로운 함수가 입력되는 것을 막기 위해 useCallback 이라는 hook을 사용할 수 있음 (useCallback 의 메모이제이션 기능)

## 1.5 접근하고자 하는 돔 요소의 개수가 많을때

* hook 순서, 개수는 항상 같아야 하기 때문에 돔 요소 개수마다 useRef를 사용하는게 힘듬 

* 보통 ref 객체에 실제 돔 요소나 컴포넌트의 인스턴스를 참소하는 용도로 사용했지만 사실 특정 값들도 저장 가능하다.

* ref 에 배열로 실제 돔 요소들의 ref들을 저장하는 방식도 가능하다.

  ```react
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
  ```

  

## 1.6 ref 사용시 주의점

* 조건을 통해 랜더링을 할 경우 ref 객체가 없을 수도 있음

* 조건부 랜더링을 사용해 생성한 ref 객체에는 current 속성 검사 로직이 필요함

  ```react
  import React, {useRef, useState, useContext, createContext} from 'react';
  
  export default function App(){
      const inputRef = useRef();
      const [showText, setShowText] = useState(true);
  
      return (
          <div>
              {/* 조건부 랜더링을 통해 ref를 설정할 경우 주의 !  */}
              {showText && <input type="text" ref={inputRef}></input>}
              <button onClick={() => setShowText(!showText)}>
                  텍스트 보이기/가리기
              </button>
              {/* current 검사 로직 추가 */}
              <button onClick={() => input.current && inputRef.current.focus()}>텍스트로 이동</button>
          </div>
      );
  }
  ```

  