import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {
  Badge,
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
  Table
} from 'reactstrap';
import {connect} from "react-redux";
import Reactotron from "reactotron-react-js";
import TableRestClient from "../../http/clients/TableRestClient";
import Button from "reactstrap/es/Button";
import CardFooter from "reactstrap/es/CardFooter";
import {AvField, AvForm} from 'availity-reactstrap-validation';
import Spinner from "reactstrap/es/Spinner";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import i18n from '../../i18n';

function TableRow(props) {
  const table = props.table;
  const getBadge = (status) => {
    return status === 'Active' ? 'primary' : 'secondary'
  };

  return (
    <tr>
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

  pageSize = 5;

  constructor(props) {
    super(props);
    this.tableClient = new TableRestClient(props);
    this.state = {
      tables: [],
      loading: true,
      modal: false,
      form: {name: '', error: false, nameError: false},
      links: {next: null, last: null, prev: null, first: null, page: null}
    };
    this.toggle = this.toggle.bind(this);
    this.newTable = this.newTable.bind(this);
    this.updateList = this.updateList.bind(this);
    this.handleValidSubmit = this.handleValidSubmit.bind(this);
    this.handleInvalidSubmit = this.handleInvalidSubmit.bind(this);
    this.getPaginationLinks = this.getPaginationLinks.bind(this);
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal,
      form: {name: ''},
    }));
  }

  newTable() {
    this.setState({loading: true});
    this.tableClient.create(this.state.form.name)
      .then(() => this.updateList())
      .then(() => this.toggle())
      .then(() => this.setState({loading: false}))
      .catch((error) => {
        Reactotron.error("Failed to create table");

        let form = {...this.state.form};
        form.nameError = true;
        form.error = true;
        this.setState({loading: false, form: form});
      });
  }

  updateList(page) {
    page = page ? page : 1;
    return this.tableClient.get(page, this.pageSize)
      .then((val) => {
        let links = {...this.state.links};
        links.next = val.data.links.next;
        links.last = val.data.links.last;
        links.prev = val.data.links.prev;
        links.first = val.data.links.first;
        links.page = val.data.links.page;
        this.setState({tables: val.data.tables, links: links});
      }).catch((error) => {
        Reactotron.error("Failed to retrieve tables", error);
      });
  }

  componentDidMount() {
    this.updateList().finally(() => this.setState({loading: false}));
  }

  handleValidSubmit(event, values) {
    let form = {...this.state.form};
    form.error = false;
    form.nameError = false;
    form.name = values.name;
    this.setState({form: form});
    this.newTable()
  }

  handleInvalidSubmit(event, errors, values) {
    let form = {...this.state.form};
    form.error = true;
    form.nameError = false;
    form.name = values.name;
    this.setState({form: form});
  }

  getPaginationLinks() {
    return this.state.links.first !== this.state.links.last ? (
      <ButtonGroup style={{'width': '100%', marginBottom: '10px'}}>
        {this.state.links.prev ?
          <Button onClick={() => this.updateList(this.state.links.prev)}>
            <i className="fa fa-chevron-left"/></Button> : ''}
        {this.state.links.next ?
          <Button onClick={() => this.updateList(this.state.links.next)}>
            <i className="fa fa-chevron-right"/></Button> : ''}
      </ButtonGroup>) : '';
  }

  render() {

    if (this.state.loading === true) return <Spinner style={{width: '3rem', height: '3rem'}}/>;

    return (
      <div className="animated fadeIn">
        <Row>
          <Col xl={12}>
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"/> {i18n.t('tables.plural')}

                {this.props.roles.filter(value => value === 'ROLE_ADMIN').length > 0 ? (
                  <Button onClick={this.toggle} style={{'float': 'right'}} color="primary" className="px-4">
                    <i className="fa fa-plus-circle"/> {i18n.t('tables.single')}
                  </Button>
                ) : ''
                }
              </CardHeader>
              <CardBody>
                <Table responsive hover>
                  <thead>
                  <tr>
                    <th scope="col">{i18n.t('tables.name')}</th>
                    <th scope="col">{i18n.t('tables.status')}</th>
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
                {this.getPaginationLinks()}
              </CardFooter>
            </Card>
          </Col>
        </Row>

        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          <ModalHeader toggle={this.toggle}>
            {i18n.t('tables.newTitle')}
          </ModalHeader>
          <AvForm onValidSubmit={this.handleValidSubmit} onInvalidSubmit={this.handleInvalidSubmit}>
            <ModalBody>
              <AvField name="name" label={i18n.t('tables.name')} type="text" validate={{
                required: {value: true, errorMessage: i18n.t('tables.validation.required')},
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
                    Table already exists with this name
                  </InputGroupText>
                </InputGroupAddon>) : ''}
            </ModalBody>
            <ModalFooter>
              <Button color="primary" className="px-4" block>{i18n.t('global.save')}</Button>
              <Button color="secondary" onClick={this.toggle}>{i18n.t('global.cancel')}</Button>
            </ModalFooter>
          </AvForm>
        </Modal>

      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    token: state.authentication.token,
    roles: state.authentication.roles
  };
};

export default connect(mapStateToProps)(Tables);
