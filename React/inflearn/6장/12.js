const saveToLocalStorage = store => next => action => {
    if(action.meta?.localStorageKey) {
        localStorage.setItem(action.meta?.localStorageKey, JSON.stringify(action));
    }
    return next(action);
}