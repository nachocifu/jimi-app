import {LOGIN_REQUESTED, LOGIN_SUCCESSFULL} from "./actionTypes";


let nextTodoId = 0;

export const loginRequested = content => ({
  type: LOGIN_REQUESTED,
  payload: {}
});

export const loginSuccessfull = content => ({
  type: LOGIN_SUCCESSFULL,
  payload: {token}
});
