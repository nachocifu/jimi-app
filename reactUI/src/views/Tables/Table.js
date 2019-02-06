import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table as TableHtml} from 'reactstrap';

import tableData from './TableData'
import {connect} from "react-redux";
import UserRestClient from "../../http/clients/UserRestClient";
import Reactotron from "reactotron-react-js";
import TableRestClient from "../../http/clients/TableRestClient";
import Spinner from "reactstrap/es/Spinner";

class Table extends Component {

  tableClient;
  constructor(props) {
    super(props);
    this.tableClient = new TableRestClient(this.props.token);
    this.state = {
      table: null,
      loading: true,
    };
  }

  componentDidMount(){

    this.tableClient.getTable(this.props.match.params.id)
      .then((val) => {
        Reactotron.debug(val);
        this.setState({table: val.data, loading: false});
      }).catch((error)=>{
      this.setState({loading: false});
      Reactotron.error("Failed to retrieve table", error);
    });

  }

  render() {

    if(this.state.loading === true) return <Spinner style={{ width: '3rem', height: '3rem' }} />;

    const tableDetails = this.state.table ? Object.entries(this.state.table) : [['id', (
      <span><i className="text-muted icon-ban"></i> Not found</span>)]];

    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"></i>{this.state.name}</strong>
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

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(Table);
