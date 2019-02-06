import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Badge, Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';

import tableData from './TableData'
import Button from "reactstrap/es/Button";
import {connect} from "react-redux";
import UserRestClient from "../../http/clients/UserRestClient";
import Reactotron from "reactotron-react-js";
import TableRestClient from "../../http/clients/TableRestClient";

function TableRow(props) {
  const table = props.table;
  const tableLink = `/tables/${table.id}`;

  const getBadge = (status) => {
    return status === 'Active' ? 'primary' : 'secondary'
  };

  return (
    <tr key={table.id.toString()}>
      <td><Link to={tableLink}>{table.name}</Link></td>
      <td><Badge color={getBadge(table.status)}>{table.status}</Badge></td>
      <td>valor</td>
    </tr>
  )
}

class Tables extends Component {

  tableClient;


  constructor(props) {
    super(props);
    this.tableClient = new TableRestClient(props.token);
    this.state ={tables: [], loading: true};
  }

  componentDidMount() {
    this.tableClient.get(0,10)
      .then((val) => {
        this.setState({tables: val.data.tables, loading: false});
      }).catch((error)=>{
      Reactotron.error("Failed to retrieve tables", error);
    });

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
                    <th scope="col">receipt</th>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.tables.map((table, index) =>
                    <TableRow key={index} table={table}/>
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

export default connect(mapStateToProps)(Tables);
