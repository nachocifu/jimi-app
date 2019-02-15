import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';
import {connect} from "react-redux";
import UserRestClient from "../../http/clients/UserRestClient";
import Reactotron from "reactotron-react-js";
import Spinner from "reactstrap/es/Spinner";
import CardFooter from "reactstrap/es/CardFooter";
import Button from "reactstrap/es/Button";
import Redirect from "react-router-dom/es/Redirect";
import Modal from "reactstrap/es/Modal";
import ModalHeader from "reactstrap/es/ModalHeader";
import ModalBody from "reactstrap/es/ModalBody";
import ModalFooter from "reactstrap/es/ModalFooter";

class User extends Component {

  usersClient;

  constructor(props) {
    super(props);
    this.usersClient = new UserRestClient(props);
    this.state = {
      user: {
        id: null,
        name: null,
      },
      loading: true,
      redirectToList: false,
      confirmationModal: false
    };
    this.loadUser = this.loadUser.bind(this);
    this.handleDelete = this.handleDelete.bind(this);
    this.toggleConfirmationModal = this.toggleConfirmationModal.bind(this);
    this.getConfirmationModalContent = this.getConfirmationModalContent.bind(this);
  }

  componentDidMount() {
    this.loadUser();
  }

  loadUser() {
    return this.usersClient.getUser(this.props.match.params.id)
      .then((val) => {
        Reactotron.debug({
          preview: "Retrieved User " + val.data.id,
          name: val.data.name,
          value: val.data
        });
        this.setState({
          user: {
            id: val.data.id,
            name: val.data.username,
          },
          loading: false,
          modal: false,
        });
      })
      .catch((error) => {
        this.setState({loading: false, table: null});
        Reactotron.error("Failed to retrieve user");
      });
  }

  handleDelete() {
    this.setState({loading: true});
    this.usersClient.delete(this.state.user.id)
      .then(() => this.setState({redirectToList: true}))
      .catch(() => this.loadUser());
  }

  toggleConfirmationModal() {
    this.setState(prevState => ({
      confirmationModal: !prevState.confirmationModal,
    }));
  }

  getConfirmationModalContent() {
    return (<div>
        <ModalHeader>Confirmation</ModalHeader>
        <ModalBody>Are you sure you want to delete the user?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.toggleConfirmationModal}>Cancel</Button>
          <Button color="success" onClick={() => this.handleDelete()}>Confirm</Button>
        </ModalFooter>
      </div>
    );
  }

  render() {
    if (this.state.redirectToList === true) return (<Redirect to="/users"/>);

    if (this.state.loading === true) return <Spinner style={{width: '3rem', height: '3rem'}}/>;

    if (this.state.user === null) {
      this.props.history.push('/404');
      return '';
    }

    const userDetails = this.state.user ? Object.entries(this.state.user) : [['id', (
      <span><i className="text-muted icon-ban"/> Not found</span>)]];

    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"/>User id: {this.props.match.params.id}</strong>
              </CardHeader>
              <CardBody>
                <Table responsive striped hover>
                  <tbody>
                  {
                    userDetails.map(([key, value]) => {
                      return (
                        <tr key={key}>
                          <td>{`${key}:`}</td>
                          <td><strong>{value}</strong></td>
                        </tr>
                      )
                    })
                  }
                  </tbody>
                </Table>
              </CardBody>
              <CardFooter>
                <Button color="secondary" onClick={this.toggle}>Edit</Button>
                <Button color="danger" onClick={this.toggleConfirmationModal}>Delete</Button>
              </CardFooter>
            </Card>
          </Col>
        </Row>

        <Modal isOpen={this.state.confirmationModal} toggle={this.toggleConfirmationModal}>
          {this.getConfirmationModalContent()}
        </Modal>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(User);
