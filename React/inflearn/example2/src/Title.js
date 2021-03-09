import React, {useState} from 'react';

function Title({title}){
    console.log('Title render')
    return <p>{title}</p>
}

export default React.memo(Title)