import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';

import dishData from './DishData'
import UserRestClient from "../../http/clients/UserRestClient";
import Reactotron from "reactotron-react-js";
import DishRestClient from "../../http/clients/DishRestClient";
import {connect} from "react-redux";

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
        Reactotron.debug(val);
        this.setState({dish: val.data, loading: false});
      }).catch((error)=>{
      this.setState({loading: false});
      Reactotron.error("Failed to retrieve dish", error);
    });

  }

  render() {

    const dishDetails = this.state.dish ? Object.entries(this.state.dish) : [['id', (
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
                <Table responsive striped hover>
                  <tbody>
                  {
                    dishDetails.map(([key, value]) => {
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
