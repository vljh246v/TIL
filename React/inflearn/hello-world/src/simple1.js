function LikeButton() {
    const [liked, setLiked] = React.useState(false);
    const text = liked ? '좋아요 취소' : '좋아요';

    return <button onClick={() => setLiked(!liked)}>{text}</button>
}

function Container() {
    const [count, setCount] = React.useState(0);
    return (
        <div>
            <LikeButton />
            <div>
                <span style={{marginTop: 20}}>
                    현재 카운트 : 
                </span>
                <span style={{marginRight: 10}}>
                    {count}
                </span>
                <button onClick={() => setCount(count + 1) }>
                    현재 카운트:
                </button>
                <button onClick={() => setCount(count - 1) }>
                    감소
                </button>
            </div>
        </div>
    );
}

const domContainer1 = document.getElementById('root1')
ReactDOM.render(React.createElement(Container),domContainer1);