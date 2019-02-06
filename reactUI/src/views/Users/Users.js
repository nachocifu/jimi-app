import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Badge, Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';
import UserRestClient from '../../http/clients/UserRestClient';
import Reactotron from 'reactotron-react-js';
import {connect} from "react-redux";

import usersData from './UsersData'
import Spinner from "reactstrap/es/Spinner";

function UserRow(props) {
  const user = props.user
  const userLink = `/users/${user.id}`

  const getBadge = (status) => {
    return status === 'Active' ? 'success' :
      status === 'Inactive' ? 'secondary' :
        status === 'Pending' ? 'warning' :
          status === 'Banned' ? 'danger' :
            'primary'
  }

  return (
    <tr key={user.id.toString()}>
      <th scope="row"><Link to={userLink}>{user.id}</Link></th>
      <td><Link to={userLink}>{user.username}</Link></td>
      <td>{user.registered}</td>
      <td>{user.role}</td>
      <td><Link to={userLink}><Badge color={getBadge(user.status)}>{user.status}</Badge></Link></td>
    </tr>
  )
}

class Users extends Component {

  usersClient;


  constructor(props) {
    super(props);
    this.usersClient = new UserRestClient(props.token);
    this.state ={users: [], loading: true};
  }

  componentDidMount() {
    this.usersClient.get(0,10).then((val) => {
      this.setState({users: val.data.users, loading: false});
    }).catch((error)=>{
      Reactotron.error("Failed to retrieve users", error);
    });

  }

  render() {
    if(this.state.loading === true) return <Spinner style={{ width: '3rem', height: '3rem' }} />;

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
                    <th scope="col">registered</th>
                    <th scope="col">role</th>
                    <th scope="col">status</th>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.users.map((user, index) =>
                    <UserRow key={index} user={user}/>
                  )}
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

export default connect(mapStateToProps)(Users);
