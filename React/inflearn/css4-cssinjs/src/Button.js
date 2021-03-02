import React from 'react';
import styled from 'styled-components';

const ButtonCommon = styled.button`
    width: ${props => (props.isBig ? 100 :50)}px;
    height: 30px;
    background-color: red;
`

export default function Button({size}) {

    const isBig = size === 'big';
    const label = isBig ? '큰 버튼' : '작은 버튼';
    return <ButtonCommon isBig={isBig}>{label}</ButtonCommon>
} 