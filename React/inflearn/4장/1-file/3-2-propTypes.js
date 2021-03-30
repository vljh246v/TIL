import PropTypes from 'prop-types';

User.propTypes = {
    male : PropTypes.bool.isRequired,
    age: PropTypes.number,
    type: PropTypes.oneOf(['gold', 'silver', 'bronze']),
    onChangeName: PropTypes.func, // 주석으로 함수 시그니쳐를 정리해주는게 좋음 ex ) (name: string) => void
    onChangeTitle: PropTypes.func,isRequired
};

export default function User({type, age, male, onChangeName, onChangeTitle}) {
    function onClick1() {
        const msg = `type: ${type}, age: ${age ? age : '알수 없음'}`;
        log(msg, {color: type === 'gold' ? 'red' : 'black'});
        // ...
    }

    function onClick2(name, title) {
        if(onChangeName){
            onChangeName(name);
        }
        onChangeTitle(title);
        male ? goMalePage() : goFemalePage();
        // ...
    }
    return null;
}

function goMalePage(){}
function goFemalePage(){}