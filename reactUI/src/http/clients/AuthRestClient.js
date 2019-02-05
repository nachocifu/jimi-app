import RestClient from "../RestClient";

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
  login(username: string, password: string) {
    return this.instance.post(
      'api/login',
      {
        username: username,
        password: password,
      }
    );
  }

}
