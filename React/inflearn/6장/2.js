
function addToDo({title, priority}) {
    return {type : 'todo/ADD', title: priority};
}

store.dispatch(addToDo({title: '영화 보기', priority: 'high'}));
