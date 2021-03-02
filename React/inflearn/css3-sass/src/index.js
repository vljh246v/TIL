import React from 'react';
import ReactDOM from 'react-dom';
import Button from './Button';
import Box from './Box';

ReactDOM.render(
  <div>
    <Button size="big"></Button>
    <Button size="small"></Button>
    <Box size="big"></Box>
    <Box size="small"></Box>
  </div>,
  document.getElementById('root'),
);