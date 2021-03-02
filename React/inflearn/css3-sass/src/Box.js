import React from 'react';
import style from './Box.module.scss'
import cn from 'classnames';

export default function Box({ size }) {
  const isBig = size === 'big';
  const label = isBig ? '큰 박스' : '작은 박스';
  return (
    <div
      className={cn(style.box, { [style.big]: isBig, [style.small]: !isBig })}
    >
      {label}
    </div>
  );
}