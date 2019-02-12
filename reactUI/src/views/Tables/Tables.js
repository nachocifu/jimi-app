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
import {connect} from "react-redux";
import Reactotron from "reactotron-react-js";
import TableRestClient from "../../http/clients/TableRestClient";
import Button from "reactstrap/es/Button";
import CardFooter from "reactstrap/es/CardFooter";
import Form from "reactstrap/es/Form";

function TableRow(props) {
  const table = props.table;

  const getBadge = (status) => {
    return status === 'Active' ? 'primary' : 'secondary'
  };

  return (
    <tr key={table.id.toString()}>
      <td>{table.name}</td>
      <td><Badge color={getBadge(table.status)}>{table.status}</Badge></td>
      <td>
        <Link to={`/tables/${table.id}`}>
          <Button color={'warning'} block><i className="fa fa-edit"/></Button>
          </Link>
      </td>
    </tr>
  )
}

class Tables extends Component {

  tableClient;


  constructor(props) {
    super(props);
    this.tableClient = new TableRestClient(props.token);
    this.state ={tables: [], loading: true, modal: false, form: {name: ''}};
    this.toggle = this.toggle.bind(this);
    this.newTable = this.newTable.bind(this);
    this.updateList = this.updateList.bind(this);
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal,
      form: {name: ''},
    }));
  }

  newTable(){
    this.toggle();
    this.setState({loading: true});
    this.tableClient.create(this.state.form.name)
      .then(() => this.updateList())
      .then(() => this.setState({loading:false}));
  }

  updateList() {
    return this.tableClient.get(0,10)
      .then((val) => {
        this.setState({tables: val.data.tables});
      }).catch((error)=>{
      Reactotron.error("Failed to retrieve tables", error);
    });
  }

  componentDidMount() {
    this.updateList().finally(()=> this.setState({loading:false}));
  }

  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col xl={12}>
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"/> Tables
              </CardHeader>
              <CardBody>
                <Table responsive hover>
                  <thead>
                  <tr>
                    <th scope="col">name</th>
                    <th scope="col">status</th>
                    <th scope="col"/>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.tables.map((table, index) =>
                    <TableRow key={index} table={table}/>
                  )}
                  </tbody>
                </Table>
              </CardBody>
              <CardFooter>
                <Button onClick={this.toggle} color="primary" className="px-4" block><i className="fa fa-plus-circle"/>Table</Button>
              </CardFooter>
            </Card>
          </Col>
        </Row>

        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          <ModalHeader toggle={this.toggle}>
            Edit Table
          </ModalHeader>
          <Form onSubmit={this.newTable}>
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

export default connect(mapStateToProps)(Tables);
