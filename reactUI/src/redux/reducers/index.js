import {LOGIN_ERRORED, LOGIN_PENDING, LOGIN_REQUESTED, LOGIN_SUCCESSFULL} from "../actions/actionTypes";
import Reactotron from "reactotron-react-js";
import jwt_decode from 'jwt-decode'

// TODO for when spliting reducers
// export default combineReducers({ });


const initialState = {
  authentication: {status: LOGIN_PENDING, token: null, info: null, roles: []}
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
      let content = jwt_decode(token);
      Reactotron.display({
        preview: 'Login',
        name: 'Succesful Login',
        value: content
      });
      return {
        ...state,
        authentication: {
          status: LOGIN_SUCCESSFULL,
          token: token,
          roles: content.roles,
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
