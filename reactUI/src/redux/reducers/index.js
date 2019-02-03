import {combineReducers} from "redux";
import {LOGIN_ERRORED, LOGIN_PENDING, LOGIN_REQUESTED, LOGIN_SUCCESSFULL} from "../actions/actionTypes";


// TODO for when spliting reducers
// export default combineReducers({ });


const initialState = {
  authentication: {status: LOGIN_PENDING, token: null, info: null}
};

export default function (state = initialState, action) {
  switch (action.type) {
    case LOGIN_REQUESTED: {
      return {
        ...state,
        authentication: {
          status: LOGIN_REQUESTED,
          token: null
        }
      };
    }
    case LOGIN_SUCCESSFULL: {
      const {token} = action.payload;
      return {
        ...state,
        authentication: {
          status: LOGIN_SUCCESSFULL,
          token: token
        }
      };
    }
    case LOGIN_ERRORED: {
      const {info} = action.payload;
      return {
        ...state,
        authentication: {
          status: LOGIN_ERRORED,
          token: '',
          info: info
        }
      };
    }
    default:
      return state;
  }
}
