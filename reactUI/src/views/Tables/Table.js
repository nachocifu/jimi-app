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


function DishListItem(props) {
  let dish = props.dish;
  let amount = props.amount;

  Reactotron.debug(dish);
  Reactotron.debug(amount);
  return (
    <tr>
      <td>{dish.name}</td>
      <td>{amount}</td>
      <td>
        {props.options && amount > 0 ?
          <Button onClick={() => props.self.setDishes(dish.id, amount - 1)} color={'danger'} block><i
            className="fa fa-minus"/></Button>
          : ''}
      </td>
      <td>
        {props.options && dish.stock > 0 ?
          <Button onClick={() => props.self.setDishes(dish.id, amount + 1)} color={'success'} block><i
            className="fa fa-plus"/></Button>
          : ''}
      </td>
      <td>
        {props.options ?
          <Button onClick={() => props.self.deleteUnDoneDish(dish.id)} color={'warning'} block><i
            className="fa fa-remove"/></Button>
          : ''}
      </td>
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
        unDoneDishes: null
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
            unDoneDishes: val.data.order.unDoneDishes.entry
          },
          loading: false,
          modal: false,
          addDishModal: false,
          addDishModalNested: false,
          addDishCloseAll: false
        });
      })
      .catch((error) => {
        this.setState({loading: false, table: null});
        Reactotron.error("Failed to retrieve table");
      });
  }

  loadDishes() {
    return this.dishClient.getAvailable(0, 100)
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
      addDishCloseAll: true
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
    this.tableClient.addDish(this.state.table.id, this.state.dishSelection, this.state.dishSelectionNum)
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

  handleStatusChange() {
    this.toggleConfirmationModal();
  }

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
    switch (this.state.table.status) {
      case 'FREE':
        return (
          <div>
            <ModalHeader>Confirmation</ModalHeader>
            <ModalBody>Table is being occupied?</ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.toggleConfirmationModal}>Cancel</Button>
              <Button color="success" onClick={() => this.changeTableStatus("BUSY")}>Confirm</Button>
            </ModalFooter>
          </div>
        );
      case 'BUSY':
        return (
          <div>
            <ModalHeader>Confirmation</ModalHeader>
            <ModalBody>Table is going to pay?</ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.toggleConfirmationModal}>Cancel</Button>
              <Button color="success" onClick={() => this.changeTableStatus("PAYING")}>Confirm</Button>
            </ModalFooter>
          </div>);
      case 'PAYING':
        return (
          <div>
            <ModalHeader>Confirmation</ModalHeader>
            <ModalBody>Table payed?</ModalBody>
            <ModalFooter>
              <Button color="secondary" onClick={this.toggleConfirmationModal}>Cancel</Button>
              <Button color="success" onClick={() => this.changeTableStatus("FREE")}>Confirm</Button>
            </ModalFooter>
          </div>);
      default:
        return this.state.table.status;
    }
  }

  getAvailableOperations() {
    switch (this.state.table.status) {
      case 'FREE':
        return <Button color={'success'} onClick={() => this.handleStatusChange()} block>SET OCCUPIED</Button>;
      case 'BUSY':
        return (
          <div>
            <Button onClick={this.preToggleAddDish} color={"success"} block>ADD DISH</Button>
            <ButtonGroup style={{'width': '100%', 'marginTop': '5px'}}>
              <Button onClick={() => this.setDiners(this.state.table.diners - 1)} color={"warning"} block><i
                className="fa fa-minus"/> Diner</Button>
              <Button onClick={() => this.setDiners(this.state.table.diners + 1)} color={"warning"} block><i
                className="fa fa-plus"/> Diner</Button>
            </ButtonGroup>
            <Button onClick={() => this.handleStatusChange()} color={"danger"} block
                    style={{'marginTop': '5px'}}>CHARGE</Button>
          </div>
        );
      case 'PAYING':
        return (
          <div>
            <Button color={"success"} block>PRINT</Button>
            <Button
              onClick={() => this.handleStatusChange()}
              color={"success"} block style={{'marginTop': '5px'}}>CHARGED</Button>
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
                  <tr key={this.state.table.id}>
                    <td>ID</td>
                    <td><strong>{this.state.table.id}</strong></td>
                  </tr>
                  <tr key={this.state.table.name}>
                    <td>NAME</td>
                    <td><strong>{this.state.table.name}</strong></td>
                  </tr>
                  <tr key={this.state.table.status}>
                    <td>STATUS</td>
                    <td><strong>{this.state.table.status}</strong></td>
                  </tr>
                  <tr key={this.state.table.diners}>
                    <td>DINERS</td>
                    <td><strong>{this.state.table.diners}</strong></td>
                  </tr>
                  </tbody>
                </TableHtml>
              </CardBody>
              {this.props.roles.filter(value => value === 'ROLE_ADMIN').length > 0 ? (

                <CardFooter>
                  <Button color="secondary" onClick={this.toggle}>Edit</Button>
                  <Button color="danger" onClick={this.handleDelete}
                          disabled={this.state.table.status !== 'FREE'}>Delete</Button>
                </CardFooter>
              ) : ''
              }
            </Card>
          </Col>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"/>Operations</strong>
              </CardHeader>
              <CardBody>
                {this.getAvailableOperations()}
              </CardBody>
            </Card>
          </Col>
          <Col lg={12}>
            <Card>
              <CardHeader>
                <strong><i className="icon-list pr-1"/>Dishes</strong>
              </CardHeader>
              <CardBody>
                <TableHtml>
                  <tbody>
                  {this.state.table.unDoneDishes.map((entry, index) =>
                    <DishListItem key={index} dish={entry.key} amount={entry.value.amount} self={this}
                                  options={true}/>
                  )}
                  {this.state.table.doneDishes.map((entry, index) =>
                    <DishListItem key={index} dish={entry.key} amount={entry.value} self={this} options={false}/>
                  )}
                  </tbody>
                </TableHtml>
              </CardBody>
            </Card>
          </Col>
        </Row>

        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          {this.state.form.modalLoading ?
            (<Spinner style={{width: '3rem', height: '3rem'}}/>) :
            (
              <Card>
                <ModalHeader toggle={this.toggle}>
                  Edit Table
                </ModalHeader>
                <AvForm onValidSubmit={this.handleValidSubmit} onInvalidSubmit={this.handleInvalidSubmit}>
                  <ModalBody>
                    <AvField name="name" label="Name" type="text" placeholder={this.state.table.name}
                             validate={{
                               required: {value: true, errorMessage: 'Please enter a name'},
                               pattern: {
                                 value: '^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$',
                                 errorMessage: 'Your name must be composed only with letter and numbers'
                               },
                               minLength: {value: 4, errorMessage: 'Your name must be between 4 and 20 characters'},
                               maxLength: {value: 20, errorMessage: 'Your name must be between 4 and 20 characters'}
                             }}/>
                    {this.state.form.nameError ? (
                      <InputGroupAddon addonType="append">
                        <InputGroupText>
                          Table already exists with this name
                        </InputGroupText>
                      </InputGroupAddon>) : ''}
                  </ModalBody>
                  <ModalFooter>
                    <Button color="primary" className="px-4" block>Save</Button>
                    <Button color="secondary" onClick={this.toggle}>Cancel</Button>
                  </ModalFooter>
                </AvForm>
              </Card>)}
        </Modal>

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
