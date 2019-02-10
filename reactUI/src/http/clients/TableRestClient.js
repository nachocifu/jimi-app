import RestClient from "../RestClient";

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

  setName(id, name) {
    return this.instance.post(
      'api/tables/'+id+'/name',
      {name: name}
    );
  }

  delete(id) {
    return this.instance.delete(
      'api/tables/'+id
    );
  }

}
