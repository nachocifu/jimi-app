import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Badge, Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';
import DishRestClient from '../../http/clients/DishRestClient'
import Button from "reactstrap/es/Button";
import Reactotron from "reactotron-react-js";
import {connect} from "react-redux";


function DishRow(props) {
  const dish = props.dish;
  const dishLink = `/dishes/${dish.id}`;

  const getBadge = (status) => {
    return status === 'Active' ? 'primary' : 'secondary'
  };

  return (
    <tr key={dish.id.toString()}>
      <td><Link to={dishLink}>{dish.name}</Link></td>
      <td>${dish.price}</td>
      <td><Badge color={getBadge(dish.status)}>{dish.status}</Badge></td>
      <td>{dish.stock}</td>
      <td><Button onClick={() => addStock(props.self, dish.id, dish.stock + 1)} color={'success'}><i className="fa fa-plus-circle"/></Button></td>
      <td><Button onClick={() => addStock(props.self, dish.id, dish.stock - 1)} color={'danger'}><i className="fa fa-minus-circle"/></Button></td>
    </tr>
  );
}

function addStock(self, id, stock) {
  Reactotron.debug([this, id, stock]);
  self.dishClient.putStock(id, stock)
    .then((val) => {
      Reactotron.debug(val);
      self.updateList();
    })
    .catch((err) => {
      Reactotron.error(err);
    });
}

class Dishes extends Component {

  dishClient;

  constructor(props) {
    super(props);
    this.dishClient = new DishRestClient(props.token);
    this.state = {dishes: [], loading: true};
  }

  updateList() {
    this.dishClient.get(0, 100)
      .then((val) => {
        this.setState({dishes: val.data.dishes, loading: false});
      }).catch((error) => {
      Reactotron.error("Failed to retrieve dishes", error);
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
                    <th scope="col">name</th>
                    <th scope="col">price</th>
                    <th scope="col">status</th>
                    <th scope="col">stock</th>
                    <th scope="col">+</th>
                    <th scope="col">-</th>
                  </tr>
                  </thead>
                  <tbody>
                  {this.state.dishes.map((dish, index) =>
                    <DishRow key={index} dish={dish} self={this}/>
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

export default connect(mapStateToProps)(Dishes);
