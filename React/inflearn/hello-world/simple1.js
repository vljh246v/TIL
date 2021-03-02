function LikeButton() {
    const [liked, setLiked] = React.useState(false);
    const text = liked ? '좋아요 취소' : '좋아요';
    return React.createElement(
        'button',
        { onClick: () => setLiked(!liked)},
        text,
    );
}

function Container() {
    const [count, setCount] = React.useState(0);
    return React.createElement(
        'div',
        null,
        React.createElement(LikeButton),
        React.createElement(
            'div',
            {style: {marginTop: 20} },
            React.createElement('span', null, '현재 카운트 : '),
            React.createElement('span', {style: {marginRight: 10}}, count),
            React.createElement(
                'button',
                {onClick: () => setCount(count + 1)},
                '증가'
            ),
            React.createElement(
                'button',
                {onClick: () => setCount(count - 1)},
                '감소'
            ),
        ),
    );
}

const domContainer1 = document.getElementById('root1')
ReactDOM.render(React.createElement(Container),domContainer1);