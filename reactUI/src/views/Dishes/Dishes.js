import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Badge, Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';

import dishData from './DishData'
import Button from "reactstrap/es/Button";

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
      <td><Button color={'success'}><i className="fa fa-plus-circle"/></Button></td>
      <td><Button color={'danger'}><i className="fa fa-minus-circle"/></Button></td>
    </tr>
  )
}

class Dishes extends Component {

  render() {

    const dishList = dishData.filter((dish) => dish.id < 10);

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
                  {dishList.map((dish, index) =>
                    <DishRow key={index} dish={dish}/>
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

export default Dishes;
