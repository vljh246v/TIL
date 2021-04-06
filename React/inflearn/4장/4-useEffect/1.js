function Profile ({userId}) {
    const [user, setUser] = useState();
    useEffect(() => {
        fetchUser(userid).then(data => setUser(data));
    }, []);
}