useEffect(async () => {
    const data = await fetchUser(userId);
    setUser(data);
}, [input])