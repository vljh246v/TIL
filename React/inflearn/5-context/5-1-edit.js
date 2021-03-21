import React, {createContext, useState} from 'react';

const UserContext = createContext({username: 'unknown', helloCount: 0});
const SetUserContext = createContext(() => {});

export default function App(){
    const [user, setUser] = useState({username: 'mike', helloCount: 0})
    return (
        <div>
            <SetUserContext.Provider value={setUser}>
                <UserContext.Provider value={user}>
                    <Profile/>
                </UserContext.Provider>
            </SetUserContext.Provider>
        </div>
    );
}