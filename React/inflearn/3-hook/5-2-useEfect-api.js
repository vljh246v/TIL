import React, { useState} from 'react'
import Profile from './Profile'

export default App(){
    const [userId, setUserId] = useState(0)
    return (
        <>
            <Profile userId={userId}></Profile>
            <button onClick={() => setUserId(userId + 1)}>User Id 변경</button>
        </>
    )
}