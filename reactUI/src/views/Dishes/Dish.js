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
  Table
} from 'reactstrap';

import Reactotron from "reactotron-react-js";
import DishRestClient from "../../http/clients/DishRestClient";
import {connect} from "react-redux";
import Spinner from "reactstrap/es/Spinner";
import CardFooter from "reactstrap/es/CardFooter";
import {AvField, AvForm} from 'availity-reactstrap-validation';
import i18n from '../../i18n';

class Dish extends Component {

  dishClient;

  constructor(props) {
    super(props);
    this.dishClient = new DishRestClient(props);
    this.state = {
      dish: null,
      loading: true,
      form: {name: '', price: 0, stock: 0, minStock: 0, error: false},
      modalLoading: false,
      confirmationModal: false,
    };

    this.loadDish = this.loadDish.bind(this);
    this.toggle = this.toggle.bind(this);
    this.handleForm = this.handleForm.bind(this);
    this.handleDiscontinue = this.handleDiscontinue.bind(this);
    this.handleValidSubmit = this.handleValidSubmit.bind(this);
    this.handleInvalidSubmit = this.handleInvalidSubmit.bind(this);
  }

  toggleConfirmationModal = () => {
    this.setState(prevState => ({
      confirmationModal: !prevState.confirmationModal,
    }));
  };

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
      }).catch((error) => {
        this.setState({loading: false});
        Reactotron.error({
          preview: 'Failded to retrieve dish',
          name: 'Failed to retrieve dish',
          value: error,
        });
      });
  }

  componentDidMount() {
    this.loadDish();
  }

  handleDiscontinue() {
    this.setState({loading: true});
    this.dishClient.discontinue(this.state.dish.id)
      .then(() => this.loadDish())
      .then(this.toggleConfirmationModal)
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

    this.setState({modalLoading: true});
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
        let form = {...this.state.form};
        form.error = true;
        this.setState({modalLoading: false, form: form});
      });
  }

  handleValidSubmit(event, values) {
    let form = {...this.state.form};
    form.error = false;
    form.name = values.name;
    form.price = values.price;
    form.stock = values.stock;
    form.minStock = values.minStock;
    this.setState({form: form});
    this.handleForm()
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

  render() {

    if (this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    if (!this.state.dish) {
      this.props.history.push('/404');
      return '';
    }

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
                  <tr>
                    <td>ID</td>
                    <td><strong>{this.state.dish.id}</strong></td>
                  </tr>
                  <tr>
                    <td>{i18n.t('dishes.name')}</td>
                    <td><strong>{this.state.dish.name}</strong></td>
                  </tr>
                  <tr>
                    <td>{i18n.t('dishes.price')}</td>
                    <td><strong>{this.state.dish.price}</strong></td>
                  </tr>
                  <tr>
                    <td>{i18n.t('dishes.stock')}</td>
                    <td><strong>{this.state.dish.stock}</strong></td>
                  </tr>
                  <tr>
                    <td>{i18n.t('dishes.minStock')}</td>
                    <td><strong>{this.state.dish.minStock}</strong></td>
                  </tr>
                  <tr>
                    <td>{i18n.t('dishes.discontinued')}</td>
                    <td>
                      <strong>{this.state.dish.discontinued ? i18n.t('global.yes').toUpperCase() : i18n.t('global.no').toUpperCase()}</strong>
                    </td>
                  </tr>
                  </tbody>
                </Table>
              </CardBody>
              <CardFooter>
                <Button color="secondary" onClick={this.toggle}>{i18n.t('global.edit')}</Button>
                {!this.state.dish.discontinued ? (
                  <Button color="danger" style={{'marginLeft': '5px'}} onClick={this.toggleConfirmationModal}>{i18n.t('dishes.setDiscontinued')}</Button>
                ) : ''
                }
              </CardFooter>
            </Card>
          </Col>
        </Row>
        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          {this.state.form.modalLoading ?
            (<Spinner style={{width: '3rem', height: '3rem'}}/>) :
            (<Card>
              <ModalHeader toggle={this.toggle}>
                {i18n.t('dishes.edit')}
              </ModalHeader>
              <AvForm onValidSubmit={this.handleValidSubmit} onInvalidSubmit={this.handleInvalidSubmit}
                      model={this.state.form}>
                <ModalBody>
                  <AvField name="name" label={i18n.t('dishes.single')} type="text"
                           validate={{
                             required: {value: true, errorMessage: i18n.t('dishes.validation.requiredName')},
                             pattern: {
                               value: '^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$',
                               errorMessage: i18n.t('dishes.validation.namePattern')
                             },
                             minLength: {value: 1, errorMessage: i18n.t('dishes.validation.minLength')},
                             maxLength: {value: 25, errorMessage: i18n.t('dishes.validation.maxLength')}
                           }}/>
                  <AvField name="price" label={i18n.t('dishes.price')} type="number"
                           validate={{
                             required: {value: true, errorMessage: i18n.t('dishes.validation.requiredPrice')},
                             step: {value: 0.01, errorMessage: i18n.t('dishes.validation.step001')},
                             pattern: {
                               value: '^\\d{1,5}[.]?\\d{0,2}$',
                               errorMessage: i18n.t('dishes.validation.pricePattern')
                             },
                             min: {value: 1, errorMessage: i18n.t('dishes.validation.min1')},
                             max: {value: 10000, errorMessage: 'Max of 10000'}
                           }}/>
                  <AvField name="stock" label={i18n.t('dishes.stock')} type="number"
                           validate={{
                             required: {value: true, errorMessage: i18n.t('dishes.validation.requiredStock')},
                             step: {value: 1, errorMessage: i18n.t('dishes.validation.step1')},
                             min: {value: 1, errorMessage: i18n.t('dishes.validation.min1')},
                             max: {value: 10000, errorMessage: i18n.t('dishes.validation.maxValue')}
                           }}/>
                  <AvField name="minStock" label={i18n.t('dishes.minStock')} type="number"
                           validate={{
                             required: {value: true, errorMessage: i18n.t('dishes.validation.requiredMinStock')},
                             step: {value: 1, errorMessage: i18n.t('dishes.validation.step1')},
                             min: {value: 0, errorMessage: i18n.t('dishes.validation.minStock')},
                             max: {value: 10000, errorMessage: i18n.t('dishes.validation.maxValue')}
                           }}/>
                </ModalBody>
                <ModalFooter>
                  <Button type="submit" color="primary" className="px-4" block>{i18n.t('global.save')}</Button>
                  <Button color="secondary" onClick={this.toggle}>{i18n.t('global.cancel')}</Button>
                </ModalFooter>
              </AvForm>
            </Card>)}
        </Modal>
        <Modal isOpen={this.state.confirmationModal} toggle={this.toggleConfirmationModal}>
          <div>
            <ModalHeader>Confirm dish is discontinued</ModalHeader>
            <ModalBody>Ones discontinued dish becomes permanently unavailable.</ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.toggleConfirmationModal}>Cancel</Button>
              <Button color="success" onClick={this.handleDiscontinue}>Confirm</Button>
            </ModalFooter>
          </div>
        </Modal>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(Dish);
