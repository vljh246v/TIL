import React from 'react';
import Style from './Box.module.css'

export default function Box({size}) {
    if(size === 'big'){
        return <div className={`${Style.box} ${Style.big}`}>큰 버튼</div>
    } else {
        return <div className={`${Style.box} ${Style.small}`}>작은 버튼</div>
    }
}