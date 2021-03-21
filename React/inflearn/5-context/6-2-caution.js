import React, {createContext} from 'react';

const UserContext = createContext('unknown');

export default function App(){
    return (
        <div>
            <UserContext.Provider value="demo">
                {/* ... */}
            </UserContext.Provider>
            <Profile/>
        </div>
    );
}