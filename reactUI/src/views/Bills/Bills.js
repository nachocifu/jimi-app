import React, {Component} from 'react';
import {Badge, Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';
import Reactotron from "reactotron-react-js";
import {connect} from "react-redux";
import BillRestClient from "../../http/clients/BillRestClient";
import moment from "moment";
import Button from "reactstrap/es/Button";
import {Link} from "react-router-dom";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import CardFooter from "reactstrap/es/CardFooter";


function BillRow(props) {
  const bill = props.bill;

  const getBadge = (status) => {
    return status === 'Active' ? 'primary' : 'secondary'
  };

  let openedAt = moment(bill.openedAt).format("h:m D/M/YY");
  let closedAt = moment(bill.closedAt).format("h:m D/M/YY");

  return (
    <tr>
      <td>{bill.id}</td>
      <td><Badge color={getBadge(bill.status)}>{bill.status}</Badge></td>
      <td>{openedAt}</td>
      <td>{closedAt}</td>
      <td>${bill.total}</td>
      <td>
        <Link to={`/bills/${bill.id}`}>
          <Button color={'warning'} block><i className="fa fa-edit"/></Button>
        </Link>
      </td>
    </tr>
  );
}


class Bills extends Component {

  billClient;

  pageSize = 6;

  constructor(props) {
    super(props);
    this.billClient = new BillRestClient(props);
    this.state = {
      bills: [], loading: true, links: {next: null, last: null, prev: null, first: null, page: null}
    };
    this.updateList = this.updateList.bind(this);
    this.getPaginationLinks = this.getPaginationLinks.bind(this);
  }

  updateList(page) {
    page = page ? page : 1;
    return this.billClient.get(page, this.pageSize)
      .then((val) => {
        let links = {...this.state.links};
        links.next = val.data.links.next;
        links.last = val.data.links.last;
        links.prev = val.data.links.prev;
        links.first = val.data.links.first;
        links.page = val.data.links.page;
        this.setState({bills: val.data.orders, loading: false, links: links});
      }).catch((error) => {
        Reactotron.error("Failed to retrieve bills", error);
      });
  }

  componentDidMount() {
    this.updateList().finally(() => this.setState({loading: false}));
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

    return (
      <div className="animated fadeIn">
        <Row>
          <Col xl={12}>
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"/> Bills
              </CardHeader>
              <CardBody>
                <Table responsive hover>
                  <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">status</th>
                    <th scope="col">opened</th>
                    <th scope="col">closed</th>
                    <th scope="col">Total</th>
                    <th scope="col"/>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.bills.map((bill, index) =>
                    <BillRow key={index} bill={bill} self={this}/>
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
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(Bills);
