import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table as TableHtml} from 'reactstrap';

import tableData from './TableData'

class Table extends Component {

  render() {

    const table = tableData.find(table => table.id.toString() === this.props.match.params.id);

    const tableDetails = table ? Object.entries(table) : [['id', (
      <span><i className="text-muted icon-ban"></i> Not found</span>)]];

    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"></i>{table.name}</strong>
              </CardHeader>
              <CardBody>
                <TableHtml responsive striped hover>
                  <tbody>
                  {
                    tableDetails.map(([key, value]) => {
                      if (key === 'id') return '';
                      return (
                        <tr key={key}>
                          <td>{`${key}:`}</td>
                          <td><strong>{value}</strong></td>
                        </tr>
                      )
                    })
                  }
                  </tbody>
                </TableHtml>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    )
  }
}

export default Table;
