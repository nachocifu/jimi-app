import React, {Component} from 'react';
import {
  Button,
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

import Reactotron from "reactotron-react-js";
import DishRestClient from "../../http/clients/DishRestClient";
import {connect} from "react-redux";
import Spinner from "reactstrap/es/Spinner";
import Form from "reactstrap/es/Form";
import CardFooter from "reactstrap/es/CardFooter";

class Dish extends Component {

  dishClient;
  constructor(props) {
    super(props);
    this.dishClient = new DishRestClient(this.props.token);
    this.state = {
      dish: null,
      loading: true,
      form: {name: 'init', price: 'init', stock: 'init', minStock: 'init'}
    };

    this.loadDish = this.loadDish.bind(this);
    this.toggle = this.toggle.bind(this);
    this.handleForm = this.handleForm.bind(this);
    this.handleDiscontinue = this.handleDiscontinue.bind(this);
    // this.resetForm = this.resetForm.bind(this);
  }

  loadDish() {
    return this.dishClient.getDish(this.props.match.params.id)
      .then((val) => {
        Reactotron.display({
          preview: 'Updated dish from endpoint',
          name: val.data.name,
          value: val.data,
        });
        this.setState({
          dish: val.data,
          loading: false,
          form: {name: val.data.name, price: val.data.price, stock: val.data.stock, minStock: val.data.minStock}
        });
      }).catch((error)=>{
        this.setState({loading: false});
        Reactotron.error({
          preview: 'Failded to retrieve dish',
          name: 'Failed to retrieve dish',
          value: error,
        });
      });
  }

  componentDidMount(){
    this.loadDish();
  }

  handleDiscontinue() {
    this.setState({loading: true});
    this.dishClient.discontinue(this.state.dish.id)
      .then(() => this.loadDish())
      .catch(() => this.setState({loading: false}));
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal,
    }));
    Reactotron.display({
      name: 'Dish Form TOGGLED',
      preview: 'Dish Form TOGGLED',
      value: this.state.form
    });
  }

  handleForm() {
    Reactotron.display({
      name: 'Dish Form submitted',
      preview: 'Dish Form submitted',
      value: this.state.form
    });

    this.setState({loading: true});
    this.dishClient.update(
      this.state.dish.id, this.state.form.name, this.state.form.price, this.state.form.stock, this.state.form.minStock
    )
      .then(() => this.toggle())
      .then((val) => this.loadDish())
      .catch(() => {
        Reactotron.error({
          name: 'Dish Form submit failed',
          preview: 'Dish Form submit failed',
          value: this.state.form
        });
        this.setState({loading: false});
      });
  }

  // resetForm() {
  //   this.setState({form: {name: this.state.dish.name, price: this.state.dish.price, stock: this.state.dish.stock, minStock: this.state.dish.minStock}});
  // }

  render() {

    if(this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    if(this.state.dish === null) return [['id', (<span><i className="text-muted icon-ban"/> Not found</span>)]];

    // const dishDetails = this.state.dish ? Object.entries(this.state.dish) : [['id', (
    //   {/*<span><i className="text-muted icon-ban"></i> Not found</span>)]];*/}

    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"/>{this.state.dish.name}</strong>
              </CardHeader>
              <CardBody>
                <Table responsive striped hover>
                  <tbody>
                    <tr key={this.state.dish.id}>
                      <td>ID</td>
                      <td><strong>{this.state.dish.id}</strong></td>
                    </tr>
                    <tr key={this.state.dish.name}>
                      <td>Name</td>
                      <td><strong>{this.state.dish.name}</strong></td>
                    </tr>
                    <tr key={this.state.dish.price}>
                      <td>Price</td>
                      <td><strong>{this.state.dish.price}</strong></td>
                    </tr>
                    <tr key={this.state.dish.stock}>
                      <td>Stock</td>
                      <td><strong>{this.state.dish.stock}</strong></td>
                    </tr>
                    <tr key={this.state.dish.minStock}>
                      <td>Minimum Stock</td>
                      <td><strong>{this.state.dish.minStock}</strong></td>
                    </tr>
                    <tr key={this.state.dish.discontinued}>
                      <td>Discontinued</td>
                      <td><strong>{this.state.dish.discontinued? 'YES':'NO'}</strong></td>
                    </tr>
                  </tbody>
                </Table>
              </CardBody>
              <CardFooter>
                <Button color="secondary" onClick={this.toggle}>Edit</Button>
                <Button color="danger" onClick={this.handleDiscontinue}>{this.state.dish.discontinued? 'SET IN PRODUCTION': 'SET DISCONTINUED'}</Button>
              </CardFooter>
            </Card>
          </Col>
        </Row>
        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          <ModalHeader toggle={this.toggle}>
            Edit Dish
          </ModalHeader>
          <Form onSubmit={this.handleForm}>
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
              <Button type="submit" color="primary" className="px-4" block>Save</Button>
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

export default connect(mapStateToProps)(Dish);
