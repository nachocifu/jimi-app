import React, {Component} from 'react';
import {
  Card,
  CardBody,
  CardHeader,
  Col,
  Row,
  Table
} from 'reactstrap';

import Reactotron from "reactotron-react-js";
import {connect} from "react-redux";
import Spinner from "reactstrap/es/Spinner";
import BillRestClient from "../../http/clients/BillRestClient";

class Bill extends Component {

  dishClient;
  constructor(props) {
    super(props);
    this.billClient = new BillRestClient(props);
    this.state = {
      bill: null,
      loading: true,
    };

    this.loadBill = this.loadBill.bind(this);
  }

  loadBill() {
    return this.billClient.getBill(this.props.match.params.id)
      .then((val) => {
        Reactotron.display({
          preview: 'Updated bill from endpoint',
          name: val.data.name,
          value: val.data,
        });
        this.setState({
          bill: val.data,
          loading: false,
          form: {name: val.data.name, price: val.data.price, stock: val.data.stock, minStock: val.data.minStock}
        });
      }).catch((error)=>{
        this.setState({loading: false});
        Reactotron.error({
          preview: 'Failded to retrieve bill',
          name: 'Failed to retrieve bill',
          value: error,
        });
      });
  }

  componentDidMount(){
    this.loadBill();
  }


  render() {

    if(this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    if(this.state.bill === null) return [['id', (<span><i className="text-muted icon-ban"/> Not found</span>)]];

    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"/>{this.state.bill.name}</strong>
              </CardHeader>
              <CardBody>
                <Table responsive striped hover>
                  <tbody>
                    <tr key={'id'}>
                      <td>ID</td>
                      <td><strong>{this.state.bill.id}</strong></td>
                    </tr>
                    <tr key={'diners'}>
                      <td>Diners</td>
                      <td>{this.state.bill.diners}</td>
                    </tr>
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

export default connect(mapStateToProps)(Bill);
