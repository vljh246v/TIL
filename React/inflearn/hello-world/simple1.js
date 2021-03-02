function LikeButton() {
  const [liked, setLiked] = React.useState(false);
  const text = liked ? '좋아요 취소' : '좋아요';
  return /*#__PURE__*/React.createElement("button", {
    onClick: () => setLiked(!liked)
  }, text);
}

function Container() {
  const [count, setCount] = React.useState(0);
  return /*#__PURE__*/React.createElement("div", null, /*#__PURE__*/React.createElement(LikeButton, null), /*#__PURE__*/React.createElement("div", null, /*#__PURE__*/React.createElement("span", {
    style: {
      marginTop: 20
    }
  }, "\uD604\uC7AC \uCE74\uC6B4\uD2B8 :"), /*#__PURE__*/React.createElement("span", {
    style: {
      marginRight: 10
    }
  }, count), /*#__PURE__*/React.createElement("button", {
    onClick: () => setCount(count + 1)
  }, "\uD604\uC7AC \uCE74\uC6B4\uD2B8:"), /*#__PURE__*/React.createElement("button", {
    onClick: () => setCount(count - 1)
  }, "\uAC10\uC18C")));
}

const domContainer1 = document.getElementById('root1');
ReactDOM.render(React.createElement(Container), domContainer1);