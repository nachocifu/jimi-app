import RestClient from "../RestClient";

export default class StatRestClient extends RestClient {


  /**
   *  Perform http request to get stats
   *
   *  @returns {Promise} - The http promise.
   */
  getAll(stockLimit) {
    return this.instance.get(
      'api/admin/stats?stockLimit=' + stockLimit
    );
  }

}
