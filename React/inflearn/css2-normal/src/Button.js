import React from 'react';
import Style from './Button.module.css'

export default function Button({size}) {
    if(size === 'big'){
        return <div className={`${Style.button} ${Style.big}`}>큰 버튼</div>
    } else {
        return <div className={`${Style.button} ${Style.small}`}>작은 버튼</div>
    }
}