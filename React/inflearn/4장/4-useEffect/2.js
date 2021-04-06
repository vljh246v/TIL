function Profile ({userId}) {
    const [user, setUser] = useState();
    const [needDetail, setNeedDetail] = useState();
    useEffect(() => {
        fetchUser(userId, needDetail).then(data => setUser(data));
    }, [userId, needDetail]);
}