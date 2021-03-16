import React, { useState} from 'react'

export default function App() {
    const[conut1, setCount1] = useState(0);
    const[conut2, setCount2] = useState(0);
    function onClick() {
        setCount1(count1 + 1);
        setCount1(count2 + 1);
    }

    const result = count1 >= count2;
}