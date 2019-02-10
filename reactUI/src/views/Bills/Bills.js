import React, {Component} from 'react';
import {Badge, Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';
import Reactotron from "reactotron-react-js";
import {connect} from "react-redux";
import BillRestClient from "../../http/clients/BillRestClient";
import moment from "moment";


function BillRow(props) {
  const bill = props.bill;

  const getBadge = (status) => {
    return status === 'Active' ? 'primary' : 'secondary'
  };

  var openedAt = moment(bill.openedAt).format("h:m D/M/YY");
  var closedAt = moment(bill.closedAt).format("h:m D/M/YY");

  return (
    <tr key={bill.id.toString()}>
      {/*<td><Link to={billLink}>{bill.id}</Link></td>*/}
      <td>{bill.id}</td>
      <td><Badge color={getBadge(bill.status)}>{bill.status}</Badge></td>
      <td>{openedAt}</td>
      <td>{closedAt}</td>
      <td>{bill.diners}</td>
    </tr>
  );
}


class Bills extends Component {

  billClient;

  constructor(props) {
    super(props);
    this.billClient = new BillRestClient(props.token);
    this.state = {bills: [], loading: true};
  }

  updateList() {
    this.billClient.get(0, 100)
      .then((val) => {
        Reactotron.debug(val);
        this.setState({bills: val.data.orders, loading: false});
      }).catch((error) => {
      Reactotron.error("Failed to retrieve bills", error);
    });
  }

  componentDidMount() {
    this.updateList();
  }


  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col xl={12}>
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"/> Dishes
              </CardHeader>
              <CardBody>
                <Table responsive hover>
                  <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">status</th>
                    <th scope="col">opened</th>
                    <th scope="col">closed</th>
                    <th scope="col">diners</th>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.bills.map((bill, index) =>
                    <BillRow key={index} bill={bill} self={this}/>
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

export default connect(mapStateToProps)(Bills);
