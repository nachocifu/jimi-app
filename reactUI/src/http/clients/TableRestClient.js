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
      'api/tables?page=' + page + '&pageSize=' + pagesize
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
      'api/tables/' + id
    );
  }

  /**
   *
   * @param name
   * @return Promise
   */
  create(name) {
    return this.instance.post(
      'api/tables',
      {name: name}
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
      'api/tables/' + id + '/name',
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
      'api/tables/' + id
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
      'api/tables/' + id + '/diners',
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
  addDish(id, dishId, amount) {
    return this.instance.post(
      'api/tables/' + id + '/undoneDishes',
      {dishId: dishId, amount: amount}
    );
  }

  setUndoneDishes(id, dishId, amount) {
    return this.instance.post(
      'api/tables/' + id + '/undoneDishes/' + dishId + '/amount',
      {amount: amount}
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
      'api/tables/' + id + '/status',
      {status: nextStatus}
    );
  }

  /**
   *
   * @param page
   * @param pageSize
   * @return Promise
   */
  getKitchen(page, pageSize) {
    return this.instance.get(
      'api/kitchen/busyTables'
    );
  }

  /**
   *
   * @param table
   * @param dish
   * @return Promise
   */
  setDoneDish(table, dish) {
    return this.instance.post(
      'api/tables/' + table + '/undoneDishes/' + dish
    );
  }

  deleteUnDoneDish(table, dish) {
    return this.instance.delete(
      '/api/tables/' + table + '/undoneDishes/' + dish
    );
  }

}
