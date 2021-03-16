import React from 'react'
import useUser from './useUser';

export default function Profile({userId}){
    const user = useUser(userId);
    return (
        <div>
            {!user && <p>사용자 정보를 가져오는 중...</p>}
            {user && (
                <>
                    <p>{`name is ${user.name}`}</p>
                    <p>{`age is ${user.age}`}</p>
                </>
            )}
        </div>
    );
}