import RestClient from "../RestClient";

export default class UserRestClient extends RestClient {


  /**
   *  Perform http request to get users
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

  /**
   *  Perform http request to get user details
   *
   *  @returns {Promise} - The http promise.
   * @param id
   */
  getUser(id) {
    return this.instance.get(
      'api/users/' + id
    );
  }

  /**
   *  Perform http request to get user details
   *
   *  @returns {Promise} - The http promise.
   * @param username
   * @param password
   * @param confirmPassword
   */
  create(username, password, confirmPassword) {
    return this.instance.post(
      'api/users/',
      {username: username, password: password, repeatPassword: confirmPassword}
    );
  }

}
