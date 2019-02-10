import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';

import dishData from './DishData'
import UserRestClient from "../../http/clients/UserRestClient";
import Reactotron from "reactotron-react-js";
import DishRestClient from "../../http/clients/DishRestClient";
import {connect} from "react-redux";
import Spinner from "reactstrap/es/Spinner";

class Dish extends Component {

  dishClient;
  constructor(props) {
    super(props);
    this.dishClient = new DishRestClient(this.props.token);
    this.state = {
      dish: null,
      loading: true,
    };
  }

  componentDidMount(){

    this.dishClient.getDish(this.props.match.params.id)
      .then((val) => {
        Reactotron.display({
          preview: 'Updated dish from endpoint',
          name: val.data.name,
          value: val.data,
        });
        this.setState({dish: val.data, loading: false});
      }).catch((error)=>{
        this.setState({loading: false});
        Reactotron.error({
          preview: 'Failded to retrieve dish',
          name: 'Failed to retrieve dish',
          value: error,
        });
    });

  }

  render() {

    if(this.state.loading === true) return (<Spinner style={{width: '3rem', height: '3rem'}}/>);

    if(this.state.dish === null) return [['id', (<span><i className="text-muted icon-ban"></i> Not found</span>)]];

    // const dishDetails = this.state.dish ? Object.entries(this.state.dish) : [['id', (
    //   {/*<span><i className="text-muted icon-ban"></i> Not found</span>)]];*/}

    return (
      <div className="animated fadeIn">
        <Row>
          <Col lg={6}>
            <Card>
              <CardHeader>
                <strong><i className="icon-info pr-1"></i>{this.state.dish.name}</strong>
              </CardHeader>
              <CardBody>
                <Table responsive striped hover>
                  <tbody>
                    <tr key={this.state.dish.id}>
                      <td>ID</td>
                      <td><strong>{this.state.dish.id}</strong></td>
                    </tr>
                    <tr key={this.state.dish.name}>
                      <td>Name</td>
                      <td><strong>{this.state.dish.name}</strong></td>
                    </tr>
                    <tr key={this.state.dish.price}>
                      <td>Price</td>
                      <td><strong>{this.state.dish.price}</strong></td>
                    </tr>
                    <tr key={this.state.dish.stock}>
                      <td>Stock</td>
                      <td><strong>{this.state.dish.stock}</strong></td>
                    </tr>
                    <tr key={this.state.dish.minStock}>
                      <td>Minimum Stock</td>
                      <td><strong>{this.state.dish.minStock}</strong></td>
                    </tr>
                    <tr key={this.state.dish.discontinued}>
                      <td>Discontinued</td>
                      <td><strong>{this.state.dish.discontinued? 'YES':'NO'}</strong></td>
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

export default connect(mapStateToProps)(Dish);
