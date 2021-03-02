import React from 'react';
import './Button.css'

export default function Button({size}) {
    if(size === 'big'){
        return <div className="button big">큰 버튼</div>
    } else {
        return <div className="button small">작은 버튼</div>
    }
}