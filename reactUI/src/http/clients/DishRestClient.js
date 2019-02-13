import RestClient from "../RestClient";

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
      'api/dishes?page='+page+'&pageSize='+pagesize
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

  putStock(id, newStock) {
    return this.instance.post(
      'api/dishes/'+id+'/stock',
      {newStock: newStock}
      // querystring.stringify({oldStock: oldStock, newStock: newStock})
    );
  }

  update(id, name, price, stock, minStock) {
    return this.instance.put(
      'api/dishes/'+id,
      {name: name, price: price, stock: stock, minStock: minStock}
    );
  }

  discontinue(id) {
    return this.instance.post(
      'api/dishes/'+id+"/discontinued"
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
  create(name, price, stock, minStock){
    return this.instance.post(
      'api/dishes',
      {name: name, price: price, stock: stock, minStock: minStock}
    );
  }

}
