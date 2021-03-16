import { useState, useEffect} from 'react'

export default function useMounted() {
    const [mounted, setMouted] = useState(false);
    useEffect(() => {
        setMouted(true);
    }, [])
    return mounted;
}