const printLog = store => next => action => {
    console.log(`prev state = ${JSON.stringify(store.getState())}`)
    const result = next(action);
    console.log(`next state = ${JSON.stringify(store.getState())}`)
    return result;
}