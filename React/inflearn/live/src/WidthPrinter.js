 
import useWindowWithd from './useWindowWidht'

export default function WidthPrinter(){
    const width = useWindowWithd();
    return <div>{`width is ${width}`}</div>
}