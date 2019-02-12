import React, {Component} from 'react';
import {
  Badge,
  Button,
  Card,
  CardBody,
  CardHeader,
  Col,
  Input,
  InputGroup,
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
import Form from "reactstrap/es/Form";
import CardFooter from "reactstrap/es/CardFooter";
import {Link, Redirect} from "react-router-dom";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import DishRestClient from "../../http/clients/DishRestClient";

function DishListItem(props) {
  var dish = props.dish;
  var amount = props.amount;

  Reactotron.debug(dish);
  Reactotron.debug(amount);
  return (
    <tr key={dish.id}>
      <td>{dish.name}</td>
      <td>{amount}</td>
      <td>
        {props.delete? <Button onClick={() => props.self.deleteUnDoneDish(dish.id)} color={'warning'} block><i className="fa fa-remove"/></Button> : ''}
      </td>
    </tr>
  )
}

class Table extends Component {

  tableClient;
  dishClient;

  constructor(props) {
    super(props);
    this.tableClient = new TableRestClient(this.props.token);
    this.dishClient = new DishRestClient(this.props.token);
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
      form: {name: ''},
      loading: true,
      redirectToList: false,
      modal: false,
      addDishModal: false,
      addDishModalNested: false,
      addDishCloseAll: false
    };
    this.toggle = this.toggle.bind(this);
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
  }

  deleteUnDoneDish(dish) {
    this.setState({loading: true});
    return this.tableClient.deleteUnDoneDish(this.state.table.id, dish)
      .then(()=> this.loadTable());
  }

  componentDidMount() {

    this.loadTable();

  }

  loadTable() {
    return this.tableClient.getTable(this.props.match.params.id)
      .then((val) => {
        Reactotron.display({
          preview: "Retrieved Table "+val.data.id,
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
        this.setState({loading: false});
        Reactotron.error("Failed to retrieve table");
      });
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal,
      form: {name: ''},
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
    this.dishClient.get(0, 100)
      .then((val) => {
        Reactotron.display({
          name: 'Table Dishes to add SUCCESS',
          preview: 'Table Dishes to add SUCCESS',
          value: val.data
        });
        this.setState({loading: false, dishes: val.data.dishes});
      })
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
      name: 'Table Form submited',
      preview: 'Table Form submited',
      value: this.state.form
    });

    this.setState({loading: true});
    this.tableClient.setName(this.state.table.id, this.state.form.name)
      .then((val) => this.loadTable())
      .catch(() => this.setState({loading: false}));
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
      .then(() => this.loadTable())
      .catch(() => Reactotron.display({
        name: 'Table add dish Fail',
        preview: 'Table add dish Fail',
        value: this.state.table
      }));
  }

  handleStatusChange(nextStatus) {
    this.setState({loading: true});
    this.tableClient.setStatus(this.state.table.id, nextStatus)
      .then(() => this.loadTable())
      .catch(() => Reactotron.display({
        name: 'Table change status Fail',
        preview: 'Table change status Fail',
        value: this.state.table
      }));
  }

  getAvailableOperations() {
    switch (this.state.table.status) {
      case 'FREE':
        return <Button color={'success'} onClick={()=> this.handleStatusChange("BUSY")} block>SET OCCUPIED</Button>;
      case 'BUSY':
        return (
          <div>
            <Button onClick={this.preToggleAddDish} color={"success"} block>ADD DISH</Button>
            <ButtonGroup style={{'width': '100%', 'margin-top': '5px'}}>
              <Button onClick={() => this.setDiners(this.state.table.diners - 1)} color={"warning"} block><i
                className="fa fa-minus"/> Dinner</Button>
              <Button onClick={() => this.setDiners(this.state.table.diners + 1)} color={"warning"} block><i
                className="fa fa-plus"/> Dinner</Button>
            </ButtonGroup>
            <Button onClick={()=> this.handleStatusChange("PAYING")} color={"danger"} block style={{'margin-top': '5px'}}>CHARGE</Button>
          </div>
        );
      case 'PAYING':
        return (
          <div>
            <Button  color={"success"} block>PRINT</Button>
            <Button
              onClick={()=> this.handleStatusChange("FREE")}
              color={"success"} block style={{'margin-top': '5px'}}>CHARGED</Button>
          </div>
        );
      default:
        return this.state.table.status;
    }
  }

  render() {

    if (this.state.redirectToList === true) return (<Redirect to="/tables"/>);

    if (this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    if (!this.state.table.id) return [['id', (<span><i className="text-muted icon-ban"/> Not found</span>)]];

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
              <CardFooter>
                <Button color="secondary" onClick={this.toggle}>Edit</Button>
                <Button color="danger" onClick={this.handleDelete}
                        disabled={this.state.table.status !== 'FREE'}>Delete</Button>
              </CardFooter>
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
                      <DishListItem dish={entry.key} amount={entry.value.amount} self={this} delete={true}/>
                    )}
                    {this.state.table.doneDishes.map((entry, index) =>
                      <DishListItem dish={entry.key} amount={entry.value} self={this} delete={false}/>
                    )}
                  </tbody>
                </TableHtml>
              </CardBody>
            </Card>
          </Col>
        </Row>
        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          <ModalHeader toggle={this.toggle}>
            Edit Table
          </ModalHeader>
          <Form onSubmit={this.handleForm}>
            <ModalBody>
              <InputGroup className="mb-3">
                <InputGroupAddon addonType="prepend">
                  <InputGroupText>
                    Name
                  </InputGroupText>
                </InputGroupAddon>
                <Input type="text" placeholder={this.state.table.name} value={this.state.form.name}
                       onChange={e => this.setState({form: {name: e.target.value}})}/>
              </InputGroup>
            </ModalBody>
            < ModalFooter>
              <Button color="primary" className="px-4" block>Save</Button>
              <Button color="secondary" onClick={this.toggle}>Cancel</Button>
            </ModalFooter>
          </Form>
        </Modal>


        <Modal isOpen={this.state.modalAddDish} toggle={this.toggleAddDish} size={'xl'}>
          <ModalHeader toggle={this.toggleAddDish}>Select Dish</ModalHeader>
          <ModalBody>
            <Row>
              {this.state.dishes.map((dish) =>
                <Col lg={3}>
                  <Button size={'lg'} color={'info'} lg={3} block style={{margin: '2.5px'}}
                          onClick={() => {
                            this.setState({dishSelection: dish.id});
                            this.toggleAddDishNested();
                          }}
                  >
                    {dish.name} ({dish.price})
                  </Button>
                </Col>
              )}
            </Row>

            <Modal isOpen={this.state.addDishModalNested} toggle={this.toggleAddDishNested}
                   onClosed={this.state.addDishCloseAll ? this.toggleAddDish : undefined}>
              <ModalHeader>How Many?</ModalHeader>
              <Form>
                <ModalBody>
                  <InputGroup className="mb-3">
                    <InputGroup>
                      <InputGroupAddon addonType="prepend">#</InputGroupAddon>
                      <Input placeholder="Amount" type="number" step="1"
                             onChange={e => this.setState({dishSelectionNum: e.target.value})}/>
                    </InputGroup>
                  </InputGroup>
                </ModalBody>
                <ModalFooter>
                  <Button color="primary" onClick={this.toggleAddDishNested}>Back</Button>
                  <Button color="secondary" onClick={this.toggleAddDishAll}>Cancel</Button>
                  <Button color="success" onClick={this.addDishes}>Submit</Button>
                </ModalFooter>
              </Form>
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

export default connect(mapStateToProps)(Table);
