import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Badge, Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';

import tableData from './TableData'
import Button from "reactstrap/es/Button";

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

  render() {

    const tableList = tableData.filter((table) => table.id < 10);

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
                  {tableList.map((table, index) =>
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

export default Tables;
