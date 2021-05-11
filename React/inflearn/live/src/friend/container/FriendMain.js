import React, {useEffect, useReducer} from 'react';
import store from '../../common/store';
import { getNextFriend } from '../../common/mockData'
import { addFriend , setAgeLimit, setShowLimit} from '../state';
import { getAgeLimit , getShowLimit, getFriendsWithAgeLimit, getFriendsWithAgeShowLimit} from '../state/selector';
import FriendList from '../component/FriendList';
import {useSelector, shallowEqual , useDispatch} from 'react-redux';
import NumberSelect from '../component/NumberSelect';
import { MAX_AGE_LIMIT, MAX_SHOW_LIMIT } from '../common';


export default function FriendMain() {
    // const [, forceUpdate] = useReducer(v => v + 1, 0);
    // useEffect(() => {
    //     let prevFriends = store.getState().friend.friends;
    //     const unsubscribe = store.subscribe(() => {
    //         const friends = store.getState().friend.friends;
    //         if(prevFriends !== friends){
    //             forceUpdate();
    //         }
    //         prevFriends = friends;
    //     });
    //     return () => unsubscribe();
    // }, []);

    // const [
    //     ageLimit,
    //     showLimit,
    //     friendsWithAgeLimit,
    //     friendsWithAgeShowLimit
    // ] = useSelector(state => {
    //     const {ageLimit, showLimit, friends} = state.friend;
    //     const friendsWithAgeLimit = friends.filter(itme => itme.age <= ageLimit);
    //     // friends.filter(itme => itme.age <= ageLimit); 해당 연산은 반복적으로 실행
    //     // 효율적으로 처리하기 위해 reslect를 사용
    //     // 선택자 함수를 컴포넌트에서 분리 가능
    //     return [
    //         ageLimit,
    //         showLimit,
    //         friendsWithAgeLimit,
    //         friendsWithAgeLimit.slice(0, showLimit),
    //     ];
    // }, shallowEqual);

    const [
        ageLimit,
        showLimit,
        friendsWithAgeLimit,
        friendsWithAgeShowLimit
    ] = useSelector(state => [
        getAgeLimit(state),
        getShowLimit(state),
        getFriendsWithAgeLimit(state),
        getFriendsWithAgeShowLimit(state)
    ], shallowEqual,);
    const dispatch = useDispatch();
    // const friends = useSelector(state => state.friend.friends) //리덕스의 상태값이 메개변수로 넘어옴

    // useSelector훅은 리덕스에서 액션이 처리가 되면 여기서 반환하는 값의 이전값을 기억했다가 이 값이 변경되었을대 컴포넌트를 다시 랜더링해준다.
    // 여러개 상태값을 가지고 오고 싶다면 const friends2 = useSelector(state => state.friend.friends2) 와같이 가지고 오는 방법이 있음
    // 또 다른 방법은 useSelector(state => [state.friend.friends, state.friend.friends2])
    // 다만 위 방법은  위 배열이 매번 새롭게 생성되어서 두 값이 변경되지 않아도 리덕스에서 액션이 처리가 될 때 마다 불필요하게 컴포넌트가 랜더링 될 수 있음
    // 이때 사용할 수있는게 두번째 매게변수로 함수를 입력해 랜더링 할지 말지 결정한다.
    // 보통은 리엑트 리덕스에서 사용하는 shallowEqual 을 사용
    // useSelector(state => [state.friend.friends, state.friend.friends2], shallowEqual)
    function onAdd() {
        const friend = getNextFriend();
        // store.dispatch(addFriend(friend));
        dispatch(addFriend(friend));
    }

    console.log('FriendMain render');

    // const friends = store.getState().friend.friends;
    return (
        <div>
            <button onClick={onAdd}>친구 추가</button>
            {/* <FriendList friends={friends} /> */}

            <NumberSelect
                onChange={v => dispatch(setAgeLimit(v))}
                value={ageLimit}
                options={AGE_LIMIT_OPTIONS}
                postfix="세 이하만 보기"
            />
            <FriendList friends={friendsWithAgeLimit}/>

            <NumberSelect
                onChange={v => dispatch(setShowLimit(v))}
                value={showLimit}
                options={SHOW_LIMIT_OPTIONS}
                postfix="명 이하만 보기 (연령 제한 적용)"
            />
            <FriendList friends={friendsWithAgeShowLimit}/>
        </div>
    )
}

const AGE_LIMIT_OPTIONS = [15, 20, 25, MAX_AGE_LIMIT];
const SHOW_LIMIT_OPTIONS = [2, 4, 6, MAX_SHOW_LIMIT];