import {createSelector} from 'reselect'

const getFriends = state => state.friend.friends;

export const getAgeLimit = state => state.friend.ageLimit;
export const getShowLimit  = state => state.friend.showLimit;

// 선택자 함수에서 사용할 값을 만들어야함
export const getFriendsWithAgeLimit = createSelector(
    [getFriends, getAgeLimit],
    (friends, ageLimit) => friends.filter(item => item.age <= ageLimit),
);

export const getFriendsWithAgeShowLimit = createSelector(
    [getFriendsWithAgeLimit, getShowLimit],
    (friendsWithAgeLimit, showLimit) => friendsWithAgeLimit.slice(0, showLimit),
);