import React, {Component} from 'react';
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Col,
  InputGroupAddon,
  InputGroupText,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
  Row,
  Table as TableHtml
} from 'reactstrap';
import {connect} from "react-redux";
import Reactotron from "reactotron-react-js";
import TableRestClient from "../../http/clients/TableRestClient";
import Spinner from "reactstrap/es/Spinner";
import CardFooter from "reactstrap/es/CardFooter";
import {Redirect} from "react-router-dom";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import DishRestClient from "../../http/clients/DishRestClient";
import {AvField, AvForm} from 'availity-reactstrap-validation';
import Select from "react-select";
import i18n from '../../i18n';
import moment from "moment";

function DishListItem(props) {
  let dish = props.dish;
  let amount = props.amount;

  Reactotron.debug(dish);
  Reactotron.debug(amount);
  return (
    <tr>
      <td>{dish.name}</td>
      {props.status === 'PAYING'?
        <td>${dish.price}</td>:''
      }
      <td>{amount}</td>
      {props.status === 'PAYING'?
        <td>${dish.price * amount}</td>:''
      }
      {props.status !== 'PAYING'?
        <td>
          {props.options && amount > 0 ?
            <Button onClick={() => props.self.setDishes(dish.id, amount - 1)} color={'danger'} block><i
              className="fa fa-minus"/></Button>
            : ''}
        </td>
        :''}
      {props.status !== 'PAYING' ?
        <td>
          {props.options && dish.stock > 0 ?
            <Button onClick={() => props.self.setDishes(dish.id, amount + 1)} color={'success'} block><i
              className="fa fa-plus"/></Button>
            : ''}
        </td>
      :''}
      {props.status !== 'PAYING'?
        <td>
          {props.options ?
            <Button onClick={() => props.self.deleteUnDoneDish(dish.id)} color={'warning'} block><i
              className="fa fa-remove"/></Button>
            : ''}
        </td>
        :''}
    </tr>
  )
}

class Table extends Component {

  tableClient;
  dishClient;

  constructor(props) {
    super(props);
    this.tableClient = new TableRestClient(props);
    this.dishClient = new DishRestClient(props);
    this.state = {
      table: {
        id: null,
        name: null,
        status: null,
        diners: null,
        doneDishes: null,
        unDoneDishes: null,
        total: 0,
      },
      dishSelection: null,
      dishSelectionNum: 1,
      dishes: [],
      form: {name: '', error: false, nameError: false},
      loading: true,
      modalLoading: false,
      redirectToList: false,
      modal: false,
      confirmationModal: false,
      addDishModal: false,
      addDishModalNested: false,
      addDishCloseAll: false
    };
    this.toggle = this.toggle.bind(this);
    this.toggleConfirmationModal = this.toggleConfirmationModal.bind(this);
    this.preToggleAddDish = this.preToggleAddDish.bind(this);
    this.toggleAddDish = this.toggleAddDish.bind(this);
    this.toggleAddDishNested = this.toggleAddDishNested.bind(this);
    this.toggleAddDishAll = this.toggleAddDishAll.bind(this);
    this.handleForm = this.handleForm.bind(this);
    this.loadTable = this.loadTable.bind(this);
    this.loadDishes = this.loadDishes.bind(this);
    this.handleDelete = this.handleDelete.bind(this);
    this.setDiners = this.setDiners.bind(this);
    this.addDishes = this.addDishes.bind(this);
    this.getAvailableOperations = this.getAvailableOperations.bind(this);
    this.deleteUnDoneDish = this.deleteUnDoneDish.bind(this);
    this.setDishes = this.setDishes.bind(this);
    this.handleValidSubmit = this.handleValidSubmit.bind(this);
    this.handleDishAmountValidSubmit = this.handleDishAmountValidSubmit.bind(this);
    this.handleInvalidSubmit = this.handleInvalidSubmit.bind(this);
    this.getConfirmationModalContent = this.getConfirmationModalContent.bind(this);
    this.handleSelect = this.handleSelect.bind(this);
  }

  deleteUnDoneDish(dish) {
    this.setState({loading: true});
    return this.tableClient.deleteUnDoneDish(this.state.table.id, dish)
      .then(() => this.loadTable());
  }

  componentDidMount() {
    this.loadTable();
  }

  loadTable() {
    return this.tableClient.getTable(this.props.match.params.id)
      .then((val) => {
        Reactotron.display({
          preview: "Retrieved Table " + val.data.id,
          name: val.data.name,
          value: val.data
        });
        this.setState({
          table: {
            id: val.data.id,
            name: val.data.name,
            status: val.data.status,
            diners: val.data.order.diners,
            doneDishes: val.data.order.doneDishes.entry,
            unDoneDishes: val.data.order.unDoneDishes.entry,
            total: val.data.order.total,
          },
          loading: false,
          modal: false,
          addDishModal: false,
          addDishModalNested: false,
          addDishCloseAll: false,
          modalAddDish: false
        });
      })
      .catch((error) => {
        this.setState({loading: false, table: null});
        Reactotron.error("Failed to retrieve table");
      });
  }

  loadDishes() {
    return this.dishClient.getAvailable(1, 100)
      .then((val) => {
        Reactotron.display({
          name: 'Table Dishes to add SUCCESS',
          preview: 'Table Dishes to add SUCCESS',
          value: val.data
        });
        this.setState({loading: false, dishes: val.data.dishes});
      })
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal,
      form: {name: ''},
    }));
  }

  toggleConfirmationModal() {
    this.setState(prevState => ({
      confirmationModal: !prevState.confirmationModal,
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
    this.loadDishes()
      .then(() => this.toggleAddDish())
      .catch(() => {
        Reactotron.display({
          name: 'Table Dishes to add FAIL',
          preview: 'Table Dishes to add FAIL',
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
      addDishCloseAll: true,
      dishSelection: null
    });
  }

  handleForm() {
    Reactotron.display({
      name: 'Table Form submitted',
      preview: 'Table Form submitted',
      value: this.state.form
    });

    this.setState({modalLoading: true});
    this.tableClient.setName(this.state.table.id, this.state.form.name)
      .then((val) => this.loadTable())
      .catch(() => {
        Reactotron.error("Failed to update table name");

        let form = {...this.state.form};
        form.nameError = true;
        form.error = true;
        this.setState({modalLoading: false, form: form});
      });
  }

  handleDelete() {
    this.setState({loading: true});
    this.tableClient.delete(this.state.table.id)
      .then(() => this.setState({redirectToList: true}))
      .catch(() => this.loadTable());
  }

  setDiners(diners) {
    Reactotron.display({
      name: 'Table Set Diners Requesting',
      preview: 'Table Set Diners Requesting',
      value: this.state.table
    });
    this.tableClient.setDiners(this.state.table.id, diners)
      .then(() => {
        let table = {...this.state.table};
        table.diners = diners;
        this.setState({table});
        Reactotron.display({
          name: 'Table Set Diners Success',
          preview: 'Table Set Diners Success',
          value: this.state.table
        });
      })
      .catch((error) =>
        Reactotron.display({name: 'Table Set Diners Fail', preview: 'Table Set Diners Fail', value: this.state.table})
      );
  }

  addDishes() {
    this.setState({loading: true});
    Reactotron.display({preview: "addDishes num", name: "addDishes num", value: this.state.dishSelectionNum})
    this.tableClient.addDish(this.state.table.id, this.state.dishSelection.value, this.state.dishSelectionNum)
      .then(this.toggleAddDishAll())
      .then(() => Promise.all([this.loadTable(), this.loadDishes()]))
      .catch(() => Reactotron.display({
        name: 'Table add dish Fail',
        preview: 'Table add dish Fail',
        value: this.state.table
      }));
  }

  setDishes(dish, amount) {
    this.tableClient.setUndoneDishes(this.state.table.id, dish, amount)
      .then(() => this.loadTable())
      .catch(() => Reactotron.display({
        name: 'Table add dish Fail',
        preview: 'Table add dish Fail',
        value: this.state.table
      }));
  }

  handleStatusChange = (status) => {
    this.setState({nextStatus: status});
    this.toggleConfirmationModal();
  };

  changeTableStatus(nextStatus) {
    this.setState({loading: true, confirmationModal: false});
    this.tableClient.setStatus(this.state.table.id, nextStatus)
      .then(() => this.loadTable())
      .catch(() => Reactotron.display({
        name: 'Table change status Fail',
        preview: 'Table change status Fail',
        value: this.state.table
      }));
  }

  getConfirmationModalContent() {
    switch (this.state.nextStatus) {
      case 'FREE':
        return (
          <div>
            <ModalHeader>{i18n.t('global.confirm')}</ModalHeader>
            <ModalBody>{i18n.t('tables.hasPayed')}</ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.toggleConfirmationModal}>{i18n.t('global.cancel')}</Button>
              <Button color="success" onClick={() => this.changeTableStatus("FREE")}>{i18n.t('global.confirm')}</Button>
            </ModalFooter>
          </div>
        );
      case 'BUSY':
        return (
          <div>
            <ModalHeader>{i18n.t('global.confirm')}</ModalHeader>
            <ModalBody>{i18n.t('tables.isOccupied')}</ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.toggleConfirmationModal}>{i18n.t('global.cancel')}</Button>
              <Button color="success" onClick={() => this.changeTableStatus("BUSY")}>{i18n.t('global.confirm')}</Button>
            </ModalFooter>
          </div>);
      case 'PAYING':
        return (
          <div>
            <ModalHeader>{i18n.t('global.confirm')}</ModalHeader>
            <ModalBody>{i18n.t('tables.willPay')}</ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.toggleConfirmationModal}>{i18n.t('global.cancel')}</Button>
              <Button color="success"
                      onClick={() => this.changeTableStatus("PAYING")}>{i18n.t('global.confirm')}</Button>
            </ModalFooter>
          </div>);
      case 'CANCELLED':
        return (
          <div>
            <ModalHeader>{i18n.t('global.confirm')}</ModalHeader>
            <ModalBody>{i18n.t('tables.cancel')}</ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.toggleConfirmationModal}>{i18n.t('global.cancel')}</Button>
              <Button color="success" onClick={() => this.changeTableStatus("FREE")}>{i18n.t('global.confirm')}</Button>
            </ModalFooter>
          </div>);
      default:
        return this.state.nextStatus;
    }
  }

  getAvailableOperations() {
    switch (this.state.table.status) {
      case 'FREE':
        return <Button color={'success'} onClick={() => this.handleStatusChange('BUSY')}
                       block>{i18n.t('tables.occupy').toUpperCase()}</Button>;
      case 'BUSY':
        if (this.state.dishes.length <= 0) {
          this.loadDishes();
        }
        return (
          <div>
            <Select placeholder={i18n.t('tables.addDish')} value={this.state.dishSelection}
                    options={this.state.dishes.map((dish) =>
                      ({value: dish.id, label: dish.name})
                    )} onChange={this.handleSelect}/>
            <ButtonGroup style={{'width': '100%', 'marginTop': '5px'}}>
              <Button disabled={this.state.table.diners === 0}
                      onClick={() => this.setDiners(this.state.table.diners - 1)} color={"warning"}
                      style={{'marginRight': '5px'}}><i
                className="fa fa-minus"/> {i18n.t('tables.diner')}</Button>
              <Button onClick={() => this.setDiners(this.state.table.diners + 1)} color={"warning"}><i
                className="fa fa-plus"/> {i18n.t('tables.diner')}</Button>
            </ButtonGroup>
            {this.state.table.doneDishes.length || this.state.table.unDoneDishes.length ?
              <Button onClick={() => this.handleStatusChange('PAYING')} color={"danger"} block
                      style={{'marginTop': '5px'}}>{i18n.t('tables.charge').toUpperCase()}</Button> : ''
            }
            <Button onClick={() => this.handleStatusChange('CANCELLED')} color={"danger"} block
                    style={{'marginTop': '5px'}}>{i18n.t('global.cancel').toUpperCase()}</Button>
          </div>
        );
      case 'PAYING':
        return (
          <div>
            <Button color={"success"} block>{i18n.t('tables.print').toUpperCase()}</Button>
            <Button
              onClick={() => this.handleStatusChange('FREE')}
              color={"success"} block style={{'marginTop': '5px'}}>{i18n.t('tables.charged').toUpperCase()}</Button>
          </div>
        );
      default:
        return this.state.table.status;
    }
  }

  handleValidSubmit(event, values) {
    let form = {...this.state.form};
    form.error = false;
    form.nameError = false;
    form.name = values.name;
    form.dishSelectionNum = values.dishSelectionNum;
    this.setState({form: form});
    this.handleForm()
  }

  handleDishAmountValidSubmit(event, values) {
    let form = {...this.state.form};
    form.error = false;
    form.nameError = false;
    this.setState({form: form, dishSelectionNum: values.dishSelectionNum});
    Reactotron.display({preview: "Selected num", name: "Selected num", value: values.dishSelectionNum});
    this.addDishes();
  }

  handleInvalidSubmit(event, errors, values) {
    let form = {...this.state.form};
    form.error = true;
    form.nameError = false;
    form.name = values.name;
    this.setState({form: form, dishSelectionNum: values.dishSelectionNum});
  }

  handleSelect = (dishSelection) => {
    this.setState({dishSelection: dishSelection});
    this.toggleAddDishNested();
  };

  render() {

    if (this.state.redirectToList === true) return (<Redirect to="/tables"/>);

    if (this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    if (this.state.table === null) {
      this.props.history.push('/404');
      return '';
    }

    Reactotron.debug(this.state.table);
    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"/>{this.state.name}</strong>
              </CardHeader>
              <CardBody>
                <TableHtml responsive striped hover>
                  <tbody>
                  <tr key={'id'}>
                    <td>ID</td>
                    <td><strong>{this.state.table.id}</strong></td>
                  </tr>
                  <tr key={'name'}>
                    <td>{i18n.t('tables.name').toUpperCase()}</td>
                    <td><strong>{this.state.table.name}</strong></td>
                  </tr>
                  <tr key={'status'}>
                    <td>{i18n.t('tables.status').toUpperCase()}</td>
                    <td><strong>{this.state.table.status}</strong></td>
                  </tr>
                  <tr key={'diners'}>
                    <td>{i18n.t('tables.diners').toUpperCase()}</td>
                    <td><strong>{this.state.table.diners}</strong></td>
                  </tr>
                  <tr key={'total'}>
                    <td>TOTAL</td>
                    <td><strong>${this.state.table.total}</strong></td>
                  </tr>
                  {this.state.table.status === 'PAYING'?
                    <tr key={'date'}>
                      <td>DATE</td>
                      <td><strong>{moment(this.state.table.closedAt).format("h:m D/M/YY")}</strong></td>
                    </tr>
                    :''
                  }
                  </tbody>
                </TableHtml>
              </CardBody>
              {this.props.roles.filter(value => value === 'ROLE_ADMIN').length > 0 ? (
                <CardFooter>
                  <Button color="secondary" style={{'marginRight': '5px'}}
                          onClick={this.toggle}>{i18n.t('global.edit')}</Button>
                  <Button color="danger" onClick={this.handleDelete}
                          disabled={this.state.table.status !== 'FREE'}>{i18n.t('global.delete')}</Button>
                </CardFooter>
              ) : ''
              }
            </Card>
          </Col>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"/>{i18n.t('tables.operations')}</strong>
              </CardHeader>
              <CardBody>
                {this.getAvailableOperations()}
              </CardBody>
            </Card>
          </Col>
          {(this.state.table.unDoneDishes.length > 0 ||
            this.state.table.doneDishes.length > 0) ?
            (<Col lg={12}>
            <Card>
              <CardHeader>
                <strong><i className="icon-list pr-1"/>{i18n.t('dishes.plural')}</strong>
              </CardHeader>
              <CardBody>
                <TableHtml>
                  <thead>
                    <th>Name</th>
                    {this.state.table.status==='PAYING'?<th>Price</th>:''}
                    <th>Amount</th>
                    {this.state.table.status==='PAYING'?<th>Total</th>:''}
                  </thead>
                  <tbody>
                  {this.state.table.unDoneDishes.length > 0 ?
                      (this.state.table.unDoneDishes.sort((a, b) => (a.key.name.localeCompare(b.key.name))).map((entry, index) =>
                    <DishListItem key={entry.key.id} dish={entry.key} amount={entry.value.amount} self={this}
                                  options={this.state.table.status!=='PAYING'} status={this.state.table.status}/>))
                  : ''}
                  {this.state.table.doneDishes.length > 0 ?
                      (this.state.table.doneDishes.map((entry, index) =>
                    <DishListItem key={entry.key.id} dish={entry.key} amount={entry.value} self={this} options={false}status={this.state.table.status}/>))
                  : ''}
                  </tbody>
                </TableHtml>
              </CardBody>
            </Card>
          </Col>) : ''}
        </Row>

        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          {this.state.form.modalLoading ?
            (<Spinner style={{width: '3rem', height: '3rem'}}/>) :
            (
              <Card>
                <ModalHeader toggle={this.toggle}>
                  {i18n.t('tables.editTitle')}
                </ModalHeader>
                <AvForm onValidSubmit={this.handleValidSubmit} onInvalidSubmit={this.handleInvalidSubmit}>
                  <ModalBody>
                    <AvField name="name" label={i18n.t('tables.name')} type="text" placeholder={this.state.table.name}
                             validate={{
                               required: {value: true, errorMessage: i18n.t('tables.validation.inputName')},
                               pattern: {
                                 value: '^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$',
                                 errorMessage: i18n.t('tables.validation.pattern')
                               },
                               minLength: {value: 4, errorMessage: i18n.t('tables.validation.minLength')},
                               maxLength: {value: 20, errorMessage: i18n.t('tables.validation.maxLength')}
                             }}/>
                    {this.state.form.nameError ? (
                      <InputGroupAddon addonType="append">
                        <InputGroupText>
                          {i18n.t('tables.validation.repeatedName')}
                        </InputGroupText>
                      </InputGroupAddon>) : ''}
                  </ModalBody>
                  <ModalFooter>
                    <Button color="primary" className="px-4" block>{i18n.t('global.save')}</Button>
                    <Button color="secondary" onClick={this.toggle}>{i18n.t('global.cancel')}</Button>
                  </ModalFooter>
                </AvForm>
              </Card>)}
        </Modal>

        <Modal isOpen={this.state.addDishModalNested} toggle={this.toggleAddDishNested}
               onClosed={this.state.addDishCloseAll ? this.toggleAddDish : undefined}>
          <ModalHeader>How Many?</ModalHeader>
          <AvForm onValidSubmit={this.handleDishAmountValidSubmit} onInvalidSubmit={this.handleInvalidSubmit}>
            <ModalBody>
              <AvField name="dishSelectionNum" label={i18n.t('tables.validation.amount')} type="number"
                       validate={{
                         required: {value: true, errorMessage: i18n.t('tables.validation.minStock')},
                         step: {value: 1},
                         min: {value: 1, errorMessage: i18n.t('tables.validation.minStock')},
                         max: {
                           value:
                             this.state.dishSelection ?
                               (this.state.dishes.find(dish => dish.id === this.state.dishSelection.value) ?
                                 this.state.dishes.find(dish => dish.id === this.state.dishSelection.value).stock :
                                 0) : 0,
                           errorMessage: i18n.t('tables.validation.maxStock')
                         }
                       }}/>
            </ModalBody>
            <ModalFooter>
              <Button color="primary" onClick={this.toggleAddDishNested}>{i18n.t('global.cancel')}</Button>
              <Button color="secondary" onClick={this.toggleAddDishAll}>{i18n.t('global.cancel')}</Button>
              <Button color="success">{i18n.t('global.save')}</Button>
            </ModalFooter>
          </AvForm>
        </Modal>

        <Modal isOpen={this.state.confirmationModal} toggle={this.toggleConfirmationModal}>
          {this.getConfirmationModalContent()}
        </Modal>

      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    token: state.authentication.token,
    roles: state.authentication.roles,
  };
};

export default connect(mapStateToProps)(Table);
