function MyComponent() {
    const [value1, setValue1] = useState(0);
    const [value2, setValue2] = useState(0);

    useEffect(() => {
        const id = setInterval(() => console.log(value1, value2), 1000);
        return () => clearInterval(id);
    }, [value1]);
    return (
        <div>
            <button onClick={() => setValue1(value1 + 1)}>value1 증가</button>
            <button onClick={() => setValue2(value2 + 1)}>value2 증가</button>
        </div>
    )
}