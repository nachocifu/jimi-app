import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';
import {connect} from "react-redux";
import UserRestClient from "../../http/clients/UserRestClient";
import Reactotron from "reactotron-react-js";
import Spinner from "reactstrap/es/Spinner";

class User extends Component {

  usersClient;
  constructor(props) {
    super(props);
    this.usersClient = new UserRestClient(this.props.token);
    this.state = {
      user: null,
      loading: true,
    };
  }

  componentDidMount(){

    this.usersClient.getUser(this.props.match.params.id)
      .then((val) => {
        Reactotron.debug(val);
        this.setState({user: val.data, loading: false});
      }).catch((error)=>{
        this.setState({loading: false});
      Reactotron.error("Failed to retrieve user", error);
    });

  }

  render() {
    if(this.state.loading === true) return <Spinner style={{ width: '3rem', height: '3rem' }} />;

    const userDetails = this.state.user ? Object.entries(this.state.user) : [['id', (
      <span><i className="text-muted icon-ban"></i> Not found</span>)]]

    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"></i>User id: {this.props.match.params.id}</strong>
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
            </Card>
          </Col>
        </Row>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(User);
