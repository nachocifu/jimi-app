import RestClient from "../RestClient";
import querystring from 'querystring'
import Reactotron from 'reactotron-react-js';

export default class TableRestClient extends RestClient {


  /**
   *  Perform http request to get tables
   *
   *  @returns {Promise} - The http promise.
   * @param page
   * @param pagesize
   */
  get(page, pagesize) {
    return this.instance.get(
      'api/tables'
    );
  }

  /**
   *  Perform http request to get table details
   *
   *  @returns {Promise} - The http promise.
   * @param id
   */
  getTable(id) {
    return this.instance.get(
      'api/tables/'+id
    );
  }

}
