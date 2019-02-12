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

  /**
   *
   * @param name
   * @return Promise
   */
  create(name){
    return this.instance.post(
      'api/tables',
      {name: name, status: 'FREE'} //TODO remove status from create table POST
    );
  }

  /**
   *
   * @param id
   * @param name
   * @returns Promise
   */
  setName(id, name) {
    return this.instance.post(
      'api/tables/'+id+'/name',
      {name: name}
    );
  }

  /**
   *
   * @param id
   * @returns Promise
   */
  delete(id) {
    return this.instance.delete(
      'api/tables/'+id
    );
  }

  /**
   *
   * @param id
   * @param diners
   * @return Promise
   */
  setDiners(id, diners) {
    return this.instance.post(
      'api/tables/'+id+'/diners',
      {diners: diners}
    )
  }

  /**
   *
   * @param id
   * @param dishId
   * @param amount
   * @returns Promise
   */
  addDish(id, dishId, amount){
    return this.instance.post(
      'api/tables/'+id+'/dishes',
      {dishId: dishId, amount: amount}
    );
  }

  /**
   *
   * @param id
   * @param nextStatus
   * @return Promise
   */
  setStatus(id, nextStatus) {
    return this.instance.post(
      'api/tables/'+id+'/status',
      {status: nextStatus}
    );
  }

}
