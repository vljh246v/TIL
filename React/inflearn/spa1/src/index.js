import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';


function App() {
  const [pageName, setPageName] = useState('');
  useEffect(() => {
    window.onpopstate = function(event) {
      setPageName(event.state);
    };
  }, []);
  function onClick1() {
    const pageName = 'page1';
    window.history.pushState(pageName, '', '/page1');
    setPageName(pageName);
  }

  function onClick2() {
    const pageName = 'page2';
    window.history.pushState(pageName, '', '/page2');
    setPageName(pageName);
  }
  return (
    <div>
      <button onClick={onClick1}>
        page1
      </button>
      <button onClick={onClick2}>
        page2
      </button>
      {!pageName && <Home />}
      {pageName === 'page1' && <Page1/>}
      {pageName === 'page2' && <Page2/>}
    </div>
  );
}

function Home(){
  return <h2>여기는 홈페이지 입니다. 원하는 페이지 버튼을 클릭하세요</h2>;
}

function Page1() {
  return <h2>여기는 page1 입니다. </h2>;
}

function Page2() {
  return <h2>여기는 page2 입니다. </h2>;
}

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root'),
)