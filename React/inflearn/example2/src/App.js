import React from 'react';

let color = 'red';

export default function App(){
  function onClick() {
    color = 'blue';
  }
  return (
    <button style={{
      backgroundColor: color
    }} onClick={onClick}>
      좋아요
    </button>
  )
}