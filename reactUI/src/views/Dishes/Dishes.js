import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {
  Badge,
  Card,
  CardBody,
  CardHeader,
  Col,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
  Row,
  Table
} from 'reactstrap';
import DishRestClient from '../../http/clients/DishRestClient'
import Button from "reactstrap/es/Button";
import Reactotron from "reactotron-react-js";
import {connect} from "react-redux";
import Spinner from "reactstrap/es/Spinner";
import CardFooter from "reactstrap/es/CardFooter";
import {AvField, AvForm} from 'availity-reactstrap-validation';

function DishRow(props) {
  const dish = props.dish;
  const dishLink = `/dishes/${dish.id}`;

  const getBadge = (status) => {
    return status ? 'primary' : 'success'
  };

  return (
    <tr key={dish.id.toString()}>
      {dish.discontinued?<td>{dish.name}</td>:<td><Link to={dishLink}>{dish.name}</Link></td>}
      <td>${dish.price}</td>
      <td><Badge color={getBadge(dish.discontinued)}>{dish.discontinued ? 'Discontinued' : 'In Production'}</Badge></td>
      <td>{dish.stock}</td>
      <td><Button disabled={dish.discontinued} onClick={() => addStock(props.self, dish.id, dish.stock + 1)} color={'success'}><i
        className="fa fa-plus-circle"/></Button></td>
      <td><Button disabled={dish.discontinued} onClick={() => addStock(props.self, dish.id, dish.stock - 1)} color={'danger'}><i
        className="fa fa-minus-circle"/></Button></td>
    </tr>
  );
}

function addStock(self, id, stock) {
  Reactotron.debug([this, id, stock]);
  self.dishClient.putStock(id, stock)
    .then((val) => {
      Reactotron.debug(val);
      self.updateList();
    })
    .catch((err) => {
      Reactotron.error(err);
    });
}

class Dishes extends Component {

  dishClient;

  constructor(props) {
    super(props);
    this.dishClient = new DishRestClient(props);
    this.downloadCsv = this.downloadCsv.bind(this);
    this.state = {
      dishes: [],
      loading: true,
      modal: false,
      form: {name: '', price: 0, stock: 0, minStock: 0, error: false}
    };
    this.newDish = this.newDish.bind(this);
    this.toggle = this.toggle.bind(this);
    this.handleValidSubmit = this.handleValidSubmit.bind(this);
    this.handleInvalidSubmit = this.handleInvalidSubmit.bind(this);
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal,
      form: {name: '', price: 0, stock: 0, minStock: 0},
    }));
  }

  newDish() {
    this.setState({loading: true});
    this.dishClient.create(
      this.state.form.name,
      this.state.form.stock,
      this.state.form.price,
      this.state.form.minStock)
      .then(() => this.toggle())
      .then(() => this.updateList())
      .then(() => this.setState({loading: false}))
      .catch((error) => {
        Reactotron.error("Failed to create dish");

        let form = {...this.state.form};
        form.error = true;
        this.setState({loading: false, form: form});
      });
  }

  updateList() {
    this.dishClient.get(0, 100)
      .then((val) => {
        this.setState({dishes: val.data.dishes, loading: false});
      }).catch((error) => {
      Reactotron.error("Failed to retrieve dishes");
    });
  }

  componentDidMount() {
    this.updateList();
  }

  handleValidSubmit(event, values) {
    let form = {...this.state.form};
    form.error = false;
    form.name = values.name;
    form.price = values.price;
    form.stock = values.stock;
    form.minStock = values.minStock;
    this.setState({form: form});
    this.newDish()
  }

  handleInvalidSubmit(event, errors, values) {
    let form = {...this.state.form};
    form.error = true;
    form.name = values.name;
    form.price = values.price;
    form.stock = values.stock;
    form.minStock = values.minStock;
    this.setState({form: form});
  }

  downloadCsv() {
    this.dishClient.getCSV()
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'dishes.csv');
        document.body.appendChild(link);
        link.click();
      });
  }

  render() {

    if (this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    return (
      <div className="animated fadeIn">
        <Row>
          <Col xl={12}>
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"/> Dishes <Button onClick={this.downloadCsv}
                                                                    style={{'float': 'right'}} color={'primary'}><i
                className="fa fa-print"/></Button>
              </CardHeader>
              <CardBody>
                <Table responsive hover>
                  <thead>
                  <tr>
                    <th scope="col">name</th>
                    <th scope="col">price</th>
                    <th scope="col">status</th>
                    <th scope="col">stock</th>
                    <th scope="col">+</th>
                    <th scope="col">-</th>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.dishes.map((dish, index) =>
                    <DishRow key={index} dish={dish} self={this}/>
                  )}
                  </tbody>
                </Table>
              </CardBody>
              <CardFooter>
                <Button onClick={this.toggle} color="primary" className="px-4" block><i className="fa fa-plus-circle"/>Dish</Button>
              </CardFooter>
            </Card>
          </Col>
        </Row>

        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          <ModalHeader toggle={this.toggle}>
            New Dish
          </ModalHeader>
          <AvForm onValidSubmit={this.handleValidSubmit} onInvalidSubmit={this.handleInvalidSubmit}>
            <ModalBody>
              <AvField name="name" label="Name" type="text"
                       validate={{
                         required: {value: true, errorMessage: 'Please enter a name'},
                         pattern: {
                           value: '^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$',
                           errorMessage: 'Your name must be composed only with letter and numbers'
                         },
                         minLength: {value: 1, errorMessage: 'Your name must be between 1 and 25 characters'},
                         maxLength: {value: 25, errorMessage: 'Your name must be between 1 and 25 characters'}
                       }}/>
              <AvField name="price" label="Price" type="number"
                       validate={{
                         required: {value: true, errorMessage: 'Please enter a price'},
                         step: {value: 0.01},
                         maxLength: {value: 10},
                         min: {value: 1}
                       }}/>
              <AvField name="stock" label="Stock" type="number"
                       validate={{
                         required: {value: true, errorMessage: 'Please enter a stock'},
                         step: {value: 1},
                         min: {value: 1},
                         max: {value: 10000}
                       }}/>
              <AvField name="minStock" label="Minimum Stock" type="number"
                       validate={{
                         required: {value: true, errorMessage: 'Please enter a minimum stock'},
                         step: {value: 1},
                         min: {value: 0},
                         max: {value: 10000}
                       }}/>
            </ModalBody>
            <ModalFooter>
              <Button color="primary" className="px-4" block>Save</Button>
              <Button color="secondary" onClick={this.toggle}>Cancel</Button>
            </ModalFooter>
          </AvForm>
        </Modal>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(Dishes);
