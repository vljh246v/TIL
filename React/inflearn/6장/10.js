const delayAction = store => next => action => {
    const delay = action.meta?.delay;
    if(!delay) {
        return next(action);
    }
    const timeoutId = setTimeout(() => next(action), delay);
    return function cancel() {
        clearTimeout(timeoutId);
    }
}