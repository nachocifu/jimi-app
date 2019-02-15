import RestClient from "../RestClient";

export default class BillRestClient extends RestClient {


  /**
   *  Perform http request to get bills
   *
   *  @returns {Promise} - The http promise.
   * @param page
   * @param pagesize
   */
  get(page, pagesize) {
    return this.instance.get(
      'api/admin/bills?page=' + page + '&pageSize=' + pagesize
    );
  }

  /**
   *
   * @param id
   * @return Promise
   */
  getBill(id) {
    return this.instance.get(
      'api/admin/bills/' + id
    );
  }

  addDish(id, dish, amount) {
    return this.instance.post(
      'api/admin/bills/' + id + '/dishes',
      {dishId: dish, amount: amount}
    );
  }

  setDishes(id, dish, amount) {
    return this.instance.post(
      'api/admin/bills/' + id + '/dishes/' + dish + '/amount',
      {amount: amount}
    );
  }

  deleteDish(bill, dish) {
    return this.setDishes(bill, dish, 0);
  }

}
