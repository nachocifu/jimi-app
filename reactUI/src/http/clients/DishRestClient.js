import RestClient from "../RestClient";
import querystring from 'querystring'
import Reactotron from 'reactotron-react-js';

export default class DishRestClient extends RestClient {


  /**
   *  Perform http request to get dishes
   *
   *  @returns {Promise} - The http promise.
   * @param page
   * @param pagesize
   */
  get(page, pagesize) {
    return this.instance.get(
      'api/dishes'
    );
  }

  /**
   *  Perform http request to get dish details
   *
   *  @returns {Promise} - The http promise.
   * @param id
   */
  getDish(id) {
    return this.instance.get(
      'api/dishes/'+id
    );
  }

}
