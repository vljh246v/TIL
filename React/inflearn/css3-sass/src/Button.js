import React from 'react';
import Style from './Button.module.scss'
import cn from 'classnames'

export default function Button({size}) {
    const isBig = size === 'big';
    return <button className={cn(Style.button, {
        [Style.big]: isBig,
        [Style.small]: !isBig
    })}>{isBig ? '큰 버튼' : '작은 버튼'}</button>
    // if(size === 'big'){
    //     return <div className={cn(Style.button, Style.big)}>큰 버튼</div>
    // } else {
    //     return <div className={cn(Style.button, Style.small)}>작은 버튼</div>
    // }
} 