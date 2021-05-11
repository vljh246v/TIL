// import React from 'react';
// import {
//     addTimeline,
//     removeTimeline,
//     editTimeline,
//     increaseNextPage,
// } from './timeline/state';
// import {
//     addFriend,
//     removeFriend,
//     editFriend
// } from './friend/state';
// import store from './common/store';

// store.dispatch(addTimeline({id: 1, desc: '코딩은 즐거워'}));
// store.dispatch(addTimeline({id: 2, desc: '리덕스 좋아'}));
// store.dispatch(increaseNextPage());
// store.dispatch(editTimeline({id: 2, desc: '리덕스 너무 좋아'}));
// store.dispatch(removeTimeline({id: 1, desc: '코딩은 즐거워'}));

// store.dispatch(addFriend({id: 1, name: '아이유'}));
// store.dispatch(addFriend({id: 2, name: '손나은'}));
// store.dispatch(editFriend({id: 2, name: '수지'}));
// store.dispatch(removeFriend({id: 1, name: '아이유'}));

// export default function App() {
//     return <div>실전 리엑트</div>;
// }

import React from 'react';
import TimelineMain from './timeline/container/TimelineMain';
import FriendMain from './friend/container/FriendMain';

import { Provider } from 'react-redux'; 
import store from './common/store';

export default function App() {
    return (
        // <div>
        //     <FriendMain/>
        //     <TimelineMain/>
        // </div>

        <Provider store={store}>
            <div>
                <FriendMain/>
                <TimelineMain/>
            </div>
        </Provider>
    )
}
// provider 컴포넌트에서는 리액트에서 액션이 처리 되었을때 이벤트를 받아서 하위에 있는 다른 컴포넌트가 다시 랜더링 될 수 있도록 도아줌