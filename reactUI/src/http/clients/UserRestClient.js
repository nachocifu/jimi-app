import RestClient from "../RestClient";
import querystring from 'querystring'
import Reactotron from 'reactotron-react-js';

export default class UserRestClient extends RestClient {


  /**
   *  Perform http request to signin
   *  the user.
   *
   *  @returns {Promise} - The http promise.
   * @param page
   * @param pagesize
   */
  get(page, pagesize) {
    return this.instance.get(
      'api/users'
    );
  }

}
