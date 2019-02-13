import RestClient from "../RestClient";

export default class DishRestClient extends RestClient {


  /**
   *  Perform http request to get dishes
   *
   *  @returns {Promise} - The http promise.
   * @param page
   * @param pagesize
   * @param filterAvailable filter available dishes
   */
  get(page, pagesize, filterAvailable = false) {
    return this.instance.get(
      'api/dishes?page=' + page + '&pageSize=' + pagesize + '&filterAvailable=' + filterAvailable
    );
  }

  /**
   *  Perform http request to get available dishes
   *
   *  @returns {Promise} - The http promise.
   * @param page
   * @param pagesize
   */
  getAvailable(page, pagesize) {
    return this.get(page, pagesize, true);
  }

  /**
   *  Perform http request to get dish details
   *
   *  @returns {Promise} - The http promise.
   * @param id
   */
  getDish(id) {
    return this.instance.get(
      'api/dishes/' + id
    );
  }

  putStock(id, newStock) {
    return this.instance.post(
      'api/dishes/' + id + '/stock',
      {newStock: newStock}
      // querystring.stringify({oldStock: oldStock, newStock: newStock})
    );
  }

  update(id, name, price, stock, minStock) {
    return this.instance.put(
      'api/dishes/' + id,
      {name: name, price: price, stock: stock, minStock: minStock}
    );
  }

  discontinue(id) {
    return this.instance.post(
      'api/dishes/' + id + "/discontinued"
    );
  }


  getCSV() {
    return this.instance.post(
      'api/dishes/downloadCSV',
      {responseType: 'blob'}
    );
  }

  /**
   *
   * @param name
   * @param price
   * @param stock
   * @param minStock
   * @return Promise
   */
  create(name, price, stock, minStock) {
    return this.instance.post(
      'api/dishes',
      {name: name, price: price, stock: stock, minStock: minStock}
    );
  }

}
