import React, {Component} from 'react';
import {
  Button,
  Card,
  CardBody,
  CardGroup,
  Col,
  Container,
  Form,
  Input,
  InputGroup,
  InputGroupAddon,
  InputGroupText,
  Row
} from 'reactstrap';
import Reactotron from 'reactotron-react-js';
import {connect} from "react-redux";
import {LOGIN_ERRORED, LOGIN_PENDING, LOGIN_REQUESTED, LOGIN_SUCCESSFULL} from "../../../redux/actions/actionTypes";
import AuthRestClient from "../../../http/clients/AuthRestClient";
import i18n from '../../../i18n';

class Login extends Component {

  constructor(props) {
    super(props);
    this.handleLogin = this.handleLogin.bind(this);
    this.state = {
      username: '',
      password: ''
    };
  }

  handleLogin() {

    const authCli = new AuthRestClient();
    this.props.dispatch({type: LOGIN_REQUESTED});
    authCli.login(this.state.username, this.state.password)
      .then(value => {
        this.props.dispatch({type: LOGIN_SUCCESSFULL, payload: {token: value.headers['x-auth-token']}});
        // window.location.replace("/stats");
        this.props.history.push("/tables");
        // return <Redirect to="/#/stats"/>;
      }).catch(error => {
      if (!error.response) error.response = "ERROR";
      this.props.dispatch({type: LOGIN_ERRORED, payload: {info: error.response}});
      Reactotron.log(error);
    });
  };

  getTitle() {
    switch (this.props.status) {
      case LOGIN_PENDING:
        return <p className="text-muted">{i18n.t('login.message')}</p>;
      case LOGIN_ERRORED:
      default:
        return <p className="text-muted">{i18n.t('login.error')}</p>;
    }
  }

  render() {
    return (
      <div className="app flex-row align-items-center">
        <Container>
          <Row className="justify-content-center">
            <Col md="8">
              <CardGroup>
                <Card className="p-4">
                  <CardBody>
                    <Form onSubmit={this.handleLogin}>
                      <h1>{i18n.t('login.title')}</h1>
                      {this.getTitle()}
                      <InputGroup className="mb-3">
                        <InputGroupAddon addonType="prepend">
                          <InputGroupText>
                            <i className="icon-user"/>
                          </InputGroupText>
                        </InputGroupAddon>
                        <Input type="text" placeholder={i18n.t('login.username')} autoComplete={i18n.t('login.username')} value={this.state.username}
                               onChange={e => this.setState({username: e.target.value})}/>
                      </InputGroup>
                      <InputGroup className="mb-4">
                        <InputGroupAddon addonType="prepend">
                          <InputGroupText>
                            <i className="icon-lock"/>
                          </InputGroupText>
                        </InputGroupAddon>
                        <Input type="password" placeholder={i18n.t('login.password')} autoComplete={i18n.t('login.password')}
                               value={this.state.password}
                               onChange={e => this.setState({password: e.target.value})}/>
                      </InputGroup>
                      <Row>
                        <Col xs="12">
                          <Button color="primary" className="px-4" block>{i18n.t('login.title')}</Button>
                        </Col>
                      </Row>
                    </Form>
                  </CardBody>
                </Card>
              </CardGroup>
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {status: state.authentication.status};
};

export default connect(mapStateToProps)(Login);
