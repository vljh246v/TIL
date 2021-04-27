function reducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case ADD:
            return {
                ...state,
                todos: [
                    ...state.todos,
                    {id: getNewId(), title: action:title, priority: action.priority},
                ],
            };
    }
}



const INITIAL_STATE = {todos: []}