import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {
  Badge,
  Card,
  CardBody,
  CardHeader,
  Col, Input,
  InputGroup,
  InputGroupAddon, InputGroupText, Modal,
  ModalBody, ModalFooter,
  ModalHeader,
  Row,
  Table
} from 'reactstrap';
import DishRestClient from '../../http/clients/DishRestClient'
import Button from "reactstrap/es/Button";
import Reactotron from "reactotron-react-js";
import {connect} from "react-redux";
import Form from "reactstrap/es/Form";
import Spinner from "reactstrap/es/Spinner";
import CardFooter from "reactstrap/es/CardFooter";


function DishRow(props) {
  const dish = props.dish;
  const dishLink = `/dishes/${dish.id}`;

  const getBadge = (status) => {
    return status ? 'primary' : 'success'
  };

  return (
    <tr key={dish.id.toString()}>
      <td><Link to={dishLink}>{dish.name}</Link></td>
      <td>${dish.price}</td>
      <td><Badge color={getBadge(dish.discontinued)}>{dish.discontinued?'Discontinued':'In Production'}</Badge></td>
      <td>{dish.stock}</td>
      <td><Button onClick={() => addStock(props.self, dish.id, dish.stock + 1)} color={'success'}><i className="fa fa-plus-circle"/></Button></td>
      <td><Button onClick={() => addStock(props.self, dish.id, dish.stock - 1)} color={'danger'}><i className="fa fa-minus-circle"/></Button></td>
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
    this.dishClient = new DishRestClient(props.token);
    this.downloadCsv = this.downloadCsv.bind(this);
    this.state = {dishes: [], loading: true, form: {name: '',price:0,stock:0,minStock:0}};
    this.newDish = this.newDish.bind(this);
    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal,
      form: {name: '',price:0,stock:0,minStock:0},
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
      .then(() => this.updateList());
  }

  updateList() {
    this.dishClient.get(0, 100)
      .then((val) => {
        this.setState({dishes: val.data.dishes, loading: false});
      }).catch((error) => {
      Reactotron.error("Failed to retrieve dishes", error);
    });
  }

  componentDidMount() {
    this.updateList();
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
                <i className="fa fa-align-justify"/> Dishes  <Button onClick={this.downloadCsv} style={{'float': 'right'}} color={'primary'}><i className="fa fa-print"/></Button>
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
          <Form onSubmit={this.newDish}>
            <ModalBody>
              <InputGroup className="mb-3">
                <InputGroupAddon addonType="prepend">
                  <InputGroupText>
                    Name
                  </InputGroupText>
                </InputGroupAddon>
                <Input type="text" value={this.state.form.name}
                       onChange={e => {
                         let form = {...this.state.form};
                         form.name = e.target.value;
                         this.setState({form})
                       }}/>
              </InputGroup>
              <InputGroup className="mb-3">
                <InputGroupAddon addonType="prepend">
                  <InputGroupText>
                    Price
                  </InputGroupText>
                </InputGroupAddon>
                <Input type="text" value={this.state.form.price}
                       onChange={e => {
                         let form = {...this.state.form};
                         form.price = e.target.value;
                         this.setState({form})
                       }}/>
              </InputGroup>
              <InputGroup className="mb-3">
                <InputGroupAddon addonType="prepend">
                  <InputGroupText>
                    Stock
                  </InputGroupText>
                </InputGroupAddon>
                <Input type="text" value={this.state.form.stock}
                       onChange={e => {
                         let form = {...this.state.form};
                         form.stock = e.target.value;
                         this.setState({form})
                       }}/>
              </InputGroup>
              <InputGroup className="mb-3">
                <InputGroupAddon addonType="prepend">
                  <InputGroupText>
                    Minimum Stock
                  </InputGroupText>
                </InputGroupAddon>
                <Input type="text" value={this.state.form.minStock}
                       onChange={e => {
                         let form = {...this.state.form};
                         form.minStock = e.target.value;
                         this.setState({form})
                       }}/>
              </InputGroup>
            </ModalBody>
            < ModalFooter>
              <Button color="primary" className="px-4" block>Save</Button>
              <Button color="secondary" onClick={this.toggle}>Cancel</Button>
            </ModalFooter>
          </Form>
        </Modal>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(Dishes);
