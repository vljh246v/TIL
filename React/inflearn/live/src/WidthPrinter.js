import React, { useState, useEffect} from 'react'

export default function WidthPrinter(){
    const [width, setWidth] = useState(window.innerWidth);
    useEffect(() => {
        const onResize = () => setWidth(window.innerWidth)
        window.addEventListener('resize', onResize);
        console.log('useEffect 1')
        return () => {
            console.log('useEffect 2')
            window.removeEventListener('resize', onResize);
        }
    }, []);

    return <div>{`width is ${width}`}</div>
}