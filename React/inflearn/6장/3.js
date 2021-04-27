
export const ADD = 'todo/ADD';

function addToDo({title, priority}) {
    return {type : ADD, title: priority};
}
