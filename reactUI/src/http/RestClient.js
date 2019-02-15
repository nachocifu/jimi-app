import axios from 'axios';
import conf from '../conf';
import Reactotron from "reactotron-react-js";
import {LOGOUT} from "../redux/actions/actionTypes";

class RestClient {
  protocol;
  domain;
  port;
  instance;

  constructor(props) {
    Reactotron.debug(props);
    let token;
    let dispatch;
    if (props) {
      token = props.token;
      dispatch = props.dispatch;
    }
    // Get Values From environment
    this.protocol = conf.API_PROTOCOL;
    this.domain = conf.API_DOMAIN;
    this.port = conf.API_PORT;

    // Handle Headers
    const headers = {
      // 'Content-Type': 'application/x-www-form-urlencoded',
      'X-AUTH-TOKEN': token
    };

    // Create axios instance
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}`,
      timeout: 60000,
      headers: headers,
    });

    // Add a response interceptor for 401
    this.instance.interceptors.response.use(
      (response) => {
        return response;
      }, (error) => {
        let errorResponse = error.response;
        if (errorResponse.status === 401) {
          dispatch({type: LOGOUT});
          props.history.push('login');
        }
        if (errorResponse.status === 403) {
          dispatch({type: LOGOUT});
          props.history.push('login');
        }
        if (errorResponse.status === 500) {
          dispatch({type: LOGOUT});
          props.history.push('500');
        }
        return Promise.reject(error);
      });
  }
}

export default RestClient;
