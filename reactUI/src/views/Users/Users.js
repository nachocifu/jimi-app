import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';
import UserRestClient from '../../http/clients/UserRestClient';
import Reactotron from 'reactotron-react-js';
import {connect} from "react-redux";
import Spinner from "reactstrap/es/Spinner";
import Button from "reactstrap/es/Button";
import CardFooter from "reactstrap/es/CardFooter";
import Modal from "reactstrap/es/Modal";
import ModalHeader from "reactstrap/es/ModalHeader";
import {AvField, AvForm} from "availity-reactstrap-validation";
import ModalBody from "reactstrap/es/ModalBody";
import InputGroupAddon from "reactstrap/es/InputGroupAddon";
import InputGroupText from "reactstrap/es/InputGroupText";
import ModalFooter from "reactstrap/es/ModalFooter";

function UserRow(props) {
  const user = props.user;
  const userLink = `/users/${user.id}`;

  return (
    <tr>
      <th scope="row">{user.id}</th>
      <td><Link to={userLink}>{user.username}</Link></td>
    </tr>
  )
}

class Users extends Component {

  usersClient;

  constructor(props) {
    super(props);
    this.usersClient = new UserRestClient(props);
    this.state = {
      users: [],
      loading: true,
      modal: false,
      form: {username: '', password: '', confirmPassword: '', error: false, nameError: false}
    };
    this.toggle = this.toggle.bind(this);
    this.newUser = this.newUser.bind(this);
    this.updateList = this.updateList.bind(this);
    this.handleValidSubmit = this.handleValidSubmit.bind(this);
    this.handleInvalidSubmit = this.handleInvalidSubmit.bind(this);
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal,
      form: {username: '', password: '', confirmPassword: ''},
    }));
  }

  newUser() {
    this.setState({loading: true});
    this.usersClient.create(this.state.form.username, this.state.form.password, this.state.form.confirmPassword)
      .then(() => this.updateList())
      .then(() => this.toggle())
      .then(() => this.setState({loading: false}))
      .catch((error) => {
        Reactotron.error("Failed to create user");

        let form = {...this.state.form};
        form.error = true;
        if (error.response.status === 409) form.nameError = true;
        this.setState({loading: false, form: form});
      });
  }

  updateList() {
    return this.usersClient.get(0, 10)
      .then((val) => {
        this.setState({users: val.data.users});
      }).catch((error) => {
        Reactotron.error("Failed to retrieve users", error);
      });
  }

  componentDidMount() {
    this.usersClient.get(0, 10)
      .then((val) => {
        this.setState({users: val.data.users, loading: false});
      }).catch((error) => {
      Reactotron.error("Failed to retrieve users", error);
    });
  }

  handleValidSubmit(event, values) {
    let form = {...this.state.form};
    form.error = false;
    form.nameError = false;
    form.username = values.username;
    form.password = values.password;
    form.confirmPassword = values.confirmPassword;
    this.setState({form: form});
    this.newUser()
  }

  handleInvalidSubmit(event, errors, values) {
    let form = {...this.state.form};
    form.error = true;
    form.nameError = false;
    form.username = values.username;
    form.password = values.password;
    form.confirmPassword = values.confirmPassword;
    this.setState({form: form});
  }

  render() {
    if (this.state.loading === true) return <Spinner style={{width: '3rem', height: '3rem'}}/>;
    return (
      <div className="animated fadeIn">
        <Row>
          <Col xl={6}>
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"/> Users <small className="text-muted"/>
              </CardHeader>
              <CardBody>
                <Table responsive hover>
                  <thead>
                  <tr>
                    <th scope="col">id</th>
                    <th scope="col">name</th>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.users.map((user, index) =>
                    <UserRow key={index} user={user}/>
                  )}
                  </tbody>
                </Table>
              </CardBody>
              <CardFooter>
                <Button onClick={this.toggle} color="primary" className="px-4" block><i
                  className="fa fa-plus-circle"/> User</Button>
              </CardFooter>
            </Card>
          </Col>
        </Row>

        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
          <ModalHeader toggle={this.toggle}>
            New User
          </ModalHeader>
          <AvForm onValidSubmit={this.handleValidSubmit} onInvalidSubmit={this.handleInvalidSubmit}>
            <ModalBody>
              <AvField name="username" label="Name" type="text" validate={{
                required: {value: true, errorMessage: 'Please enter a name'},
                pattern: {
                  value: '[a-zA-Z0-9]+',
                  errorMessage: 'Your name must be composed only with letter and numbers'
                },
                minLength: {value: 6, errorMessage: 'Your name must be between 6 and 40 characters'},
                maxLength: {value: 40, errorMessage: 'Your name must be between 6 and 40 characters'}
              }}/>
              {this.state.form.nameError ? (
                <InputGroupAddon addonType="append">
                  <InputGroupText>
                    User already exists with this name
                  </InputGroupText>
                </InputGroupAddon>) : ''}
              <AvField name="password" label="Password" type="password" validate={{
                required: {value: true, errorMessage: 'Please enter a password'},
                minLength: {value: 6, errorMessage: 'Your name must be between 6 and 40 characters'},
                maxLength: {value: 40, errorMessage: 'Your name must be between 6 and 40 characters'}
              }}/>
              <AvField name="confirmPassword" label="Confirm password" type="password" validate={{
                required: {value: true, errorMessage: 'Please confirm password'},
                minLength: {value: 6, errorMessage: 'Your name must be between 6 and 40 characters'},
                maxLength: {value: 40, errorMessage: 'Your name must be between 6 and 40 characters'},
                match: {value: 'password', errorMessage: 'Passwords must match'}
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
  return {
    token: state.authentication.token
  };
};

export default connect(mapStateToProps)(Users);
