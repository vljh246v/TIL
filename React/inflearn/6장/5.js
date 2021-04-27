import {createStore, applyMiddleware} from 'redux';

const middleware1 = store => next => action => {
    console.log('middleware1 start');
    const result = next(action);
    console.log('middleware1 end')
    return result;
}

const middleware2 = store => next => action => {
    console.log('middleware2 start');
    const result = next(action);
    console.log('middleware2 end')
    return result;
}

const myReducer = (state, action) => {
    console.log('myReducer');
    return state;
}

const store = createStore(myReducer, applyMiddleware(middleware1, middleware2));
store.dispatch({type: 'someAction'});