import React, {Component} from 'react';
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Col,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
  Row,
  Table as TableHtml,
  Table
} from 'reactstrap';

import Reactotron from "reactotron-react-js";
import {connect} from "react-redux";
import Spinner from "reactstrap/es/Spinner";
import BillRestClient from "../../http/clients/BillRestClient";
import CardFooter from "reactstrap/es/CardFooter";
import DishRestClient from "../../http/clients/DishRestClient";
import {AvField, AvForm} from 'availity-reactstrap-validation';

function DishListItem(props) {
  let dish = props.dish;
  let amount = props.amount;

  Reactotron.debug(dish);
  Reactotron.debug(amount);
  return (
    <tr key={dish.id}>
      <td>{dish.name}</td>
      <td>{amount}</td>
      <td>{dish.price}</td>
      <td>{dish.price * amount}</td>
      <td>
        {props.delete ? <Button onClick={() => props.self.deleteDish(dish.id)} color={'warning'} block><i
          className="fa fa-remove"/></Button> : ''}
      </td>
    </tr>
  )
}

class Bill extends Component {

  dishClient;
  billClient;

  constructor(props) {
    super(props);
    this.billClient = new BillRestClient(props);
    this.dishClient = new DishRestClient(props);
    this.state = {
      dishes: [],
      bill: null,
      loading: true,
      addDishModal: false,
      addDishModalNested: false,
      addDishCloseAll: false,
      dishSelection: null,
      dishSelectionNum: 1,
    };

    this.loadBill = this.loadBill.bind(this);
    this.preToggleAddDish = this.preToggleAddDish.bind(this);
    this.toggleAddDish = this.toggleAddDish.bind(this);
    this.toggleAddDishNested = this.toggleAddDishNested.bind(this);
    this.toggleAddDishAll = this.toggleAddDishAll.bind(this);
    this.addDishes = this.addDishes.bind(this);
    this.handleDishAmountValidSubmit = this.handleDishAmountValidSubmit.bind(this);
    this.handleInvalidSubmit = this.handleInvalidSubmit.bind(this);
  }

  deleteDish(dish) {
    this.setState({loading: true});
    return this.billClient.deleteDish(this.state.bill.id, dish)
      .then(() => this.loadBill());
  }

  loadBill() {
    return this.billClient.getBill(this.props.match.params.id)
      .then((val) => {
        Reactotron.display({
          preview: 'Updated bill from endpoint',
          name: val.data.name,
          value: val.data,
        });
        this.setState({
          bill: val.data,
          loading: false,
          form: {name: val.data.name, price: val.data.price, stock: val.data.stock, minStock: val.data.minStock}
        });
      }).catch((error) => {
        this.setState({loading: false});
        Reactotron.error({
          preview: 'Failded to retrieve bill',
          name: 'Failed to retrieve bill',
          value: error,
        });
      });
  }

  componentDidMount() {
    this.loadBill();
  }

  addDishes() {
    this.setState({loading: true});
    this.billClient.addDish(this.state.bill.id, this.state.dishSelection, this.state.dishSelectionNum)
      .then(this.toggleAddDishAll())
      .then(() => Promise.all([this.loadBill(), this.loadDishes()]))
      .catch(() => Reactotron.display({
        name: 'Bill add dish Fail',
        preview: 'Bill add dish Fail',
        value: this.state.bill
      }));
  }

  toggleAddDish() {
    this.setState(prevState => ({
      modalAddDish: !prevState.modalAddDish,
    }));
  }

  preToggleAddDish() {
    // Reactotron.display({name: 'Table Dishes to add Requesting', preview: 'Table Dishes to add Requesting', value: this.state.dishes});
    this.setState({loading: true});
    this.dishClient.get(0, 100) //TODO change to getAvailable
      .then((val) => {
        Reactotron.display({
          name: 'Bill Dishes to add SUCCESS',
          preview: 'Bill Dishes to add SUCCESS',
          value: val.data
        });
        this.setState({loading: false, dishes: val.data.dishes});
      })
      .then(() => this.toggleAddDish())
      .catch(() => {
        Reactotron.display({
          name: 'Bill Dishes to add FAIL',
          preview: 'Bill Dishes to add FAIL',
          value: this.state.dishes
        });
        this.setState({loading: false});
      })
  }

  toggleAddDishNested() {
    this.setState({
      addDishModalNested: !this.state.addDishModalNested,
      addDishCloseAll: false
    });
  }

  toggleAddDishAll() {
    this.setState({
      addDishModalNested: !this.state.addDishModalNested,
      addDishCloseAll: true
    });
  }

  handleDishAmountValidSubmit(event, values) {
    let form = {...this.state.form};
    form.error = false;
    form.nameError = false;
    form.dishSelectionNum = values.dishSelectionNum;
    this.setState({form: form});
    this.addDishes()
  }

  handleInvalidSubmit(event, errors, values) {
    let form = {...this.state.form};
    form.error = true;
    form.nameError = false;
    form.name = values.name;
    form.dishSelectionNum = values.dishSelectionNum;
    this.setState({form: form});
  }

  render() {

    if (this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    if (this.state.bill === null) return [['id', (<span><i className="text-muted icon-ban"/> Not found</span>)]];

    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"/>{this.state.bill.name}</strong>
              </CardHeader>
              <CardBody>
                <Table responsive striped hover>
                  <tbody>
                  <tr key={'id'}>
                    <td>ID</td>
                    <td><strong>{this.state.bill.id}</strong></td>
                  </tr>
                  <tr key={'diners'}>
                    <td>Diners</td>
                    <td>{this.state.bill.diners}</td>
                  </tr>
                  </tbody>
                </Table>
              </CardBody>
              <CardFooter>
                <Button onClick={this.preToggleAddDish} color={"success"} block>ADD DISH</Button>
              </CardFooter>
            </Card>
          </Col>

          <Col lg={12}>
            <Card>
              <CardHeader>
                <strong><i className="icon-list pr-1"/>Dishes</strong>
              </CardHeader>
              <CardBody>
                <TableHtml>
                  <thead>
                  <tr>
                    <th>Name</th>
                    <th>Amount</th>
                    <th>Price</th>
                    <th>Total</th>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.bill.doneDishes.entry.map((entry, index) =>
                    <DishListItem key={entry.key.id} dish={entry.key} amount={entry.value} self={this} delete={true}/>
                  )}
                  </tbody>
                </TableHtml>
              </CardBody>
            </Card>
          </Col>
        </Row>

        <Modal isOpen={this.state.modalAddDish} toggle={this.toggleAddDish} size={'xl'}>
          <ModalHeader toggle={this.toggleAddDish}>Select Dish</ModalHeader>
          <ModalBody>
            <Row>
              {this.state.dishes.map((dish) =>
                <Col lg={3} key={dish.id}>
                  <Button size={'lg'} color={'info'} lg={3} block style={{margin: '2.5px'}}
                          onClick={() => {
                            this.setState({dishSelection: dish.id});
                            this.toggleAddDishNested();
                          }}>
                    {dish.name}
                  </Button>
                </Col>
              )}
            </Row>

            <Modal isOpen={this.state.addDishModalNested} toggle={this.toggleAddDishNested}
                   onClosed={this.state.addDishCloseAll ? this.toggleAddDish : undefined}>
              <ModalHeader>How Many?</ModalHeader>
              <AvForm onValidSubmit={this.handleDishAmountValidSubmit} onInvalidSubmit={this.handleInvalidSubmit}>
                <ModalBody>
                  <AvField name="dishSelectionNum" label="Amount" type="number"
                           onChange={e => this.setState({dishSelectionNum: e.target.value})}
                           validate={{
                             required: {value: true, errorMessage: 'Please enter an amount'},
                             step: {value: 1},
                             min: {value: 1, errorMessage: 'Minimum of 1'},
                             max: {
                               value:
                                 this.state.dishes.find(dish => dish.id === this.state.dishSelection) ?
                                   this.state.dishes.find(dish => dish.id === this.state.dishSelection).stock :
                                   0,
                               errorMessage: 'Not enough stock'
                             }
                           }}/>
                </ModalBody>
                <ModalFooter>
                  <Button color="primary" onClick={this.toggleAddDishNested}>Back</Button>
                  <Button color="secondary" onClick={this.toggleAddDishAll}>Cancel</Button>
                  <Button color="success">Submit</Button>
                </ModalFooter>
              </AvForm>
            </Modal>

          </ModalBody>
          <ModalFooter>
            <Button color="secondary" onClick={this.toggleAddDish} block>Cancel</Button>
          </ModalFooter>
        </Modal>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(Bill);
