import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {Badge, Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';

import kitchenData from './KitchenData'
import Button from "reactstrap/es/Button";

function KitchenItem(props) {
  const table = props.table;
  const dishes = table.dishes;


  return (
    <Col sm={4}>
      <Card>
        <CardHeader>
          <i className="fa fa-align-justify"/> {table.name}
        </CardHeader>
        <CardBody>
          <Table>
            <tbody>
            {dishes.map((dish) => <KitchenDishRow dish={dish}/>)}
            </tbody>
          </Table>
        </CardBody>
      </Card>
    </Col>
  )
}

function KitchenDishRow(props) {
  const dish = props.dish;
  return (
    <tr>
      <td>{(dish.name.length > 20) ? dish.name.substring(0, 20).concat("...") : dish.name}</td>
      <td>{dish.amount}</td>
      <td>
        <Button onClick={confirmDish}><i class="fa fa-check"/></Button>
      </td>
    </tr>
  );
}

function confirmDish() {
  alert("dish confirmed");
}

class Kitchen extends Component {

  render() {

    const kitchenList = kitchenData.filter((dish) => dish.id < 10);

    return (
      <div className="animated fadeIn">
        <Row>
          {kitchenList.map((table, index) =>
            <KitchenItem key={index} table={table}/>
          )}
        </Row>
      </div>
    )
  }
}

export default Kitchen;
