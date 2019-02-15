import RestClient from "../RestClient";
import querystring from 'querystring'

export default class AuthRestClient extends RestClient {

  /**
   *  Perform http request to signin
   *  the user.
   *
   *  @param {string} username - The username.
   *  @param {string} password - The password.
   *  @param {string} application - The client.
   *  @returns {Promise} - The http promise.
   */
  login(username, password) {
    return this.instance.post(
      'api/login',
      querystring.stringify({username: username, password: password})
    );
  }

}
