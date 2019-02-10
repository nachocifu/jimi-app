import React, {Component} from 'react';
import {
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
import {Redirect} from "react-router-dom";

class Table extends Component {

  tableClient;

  constructor(props) {
    super(props);
    this.tableClient = new TableRestClient(this.props.token);
    this.state = {
      table: {
        id: null,
        name: null,
        status: null,
        diners: null
      },
      form: {name: ''},
      loading: true,
      redirectToList: false,
    };
    this.toggle = this.toggle.bind(this);
    this.handleForm = this.handleForm.bind(this);
    this.loadTable = this.loadTable.bind(this);
    this.handleDelete = this.handleDelete.bind(this);
  }

  componentDidMount() {

    this.loadTable();

  }

  loadTable() {
    return this.tableClient.getTable(this.props.match.params.id)
      .then((val) => {
        Reactotron.debug("Retrieved table:" + val.data.name, val.data);
        this.setState({
          table: {
            id: val.data.id,
            name: val.data.name,
            status: val.data.status,
            diners: val.data.order.diners
          },
          loading: false,
          modal: false,
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

  render() {

    if (this.state.redirectToList === true) return (<Redirect to="/tables"/>);

    if (this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    // if( !this.state.table.id) return [['id', (<span><i className="text-muted icon-ban"></i> Not found</span>)]];

    Reactotron.debug(this.state.table);
    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"></i>{this.state.name}</strong>
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
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(Table);
