import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Col, Row, Table} from 'reactstrap';
import Reactotron from "reactotron-react-js";
import Button from "reactstrap/es/Button";
import TableRestClient from "../../http/clients/TableRestClient";
import connect from "react-redux/es/connect/connect";

function KitchenItem(props) {
  const table = props.table;
  const dishes = table.order.unDoneDishes.entry;


  return (
    <Col xs="12" md="6" lg="4">
      <Card>
        <CardHeader>
          <i className="fa fa-align-justify"/> {table.name}
        </CardHeader>
        <CardBody>
          <Table>
            <tbody>
            {dishes.map((dish) => <KitchenDishRow table={table} dish={dish} self={props.self}/>)}
            </tbody>
          </Table>
        </CardBody>
      </Card>
    </Col>
  );
}

function KitchenDishRow(props) {
  const dish = props.dish.key;
  Reactotron.debug(props.dish);
  return (
    <tr>
      <td>{(dish.name.length > 20) ? dish.name.substring(0, 20).concat("...") : dish.name}</td>
      <td>{props.dish.value.amount}</td>
      <td>
        <Button onClick={() => props.self.confirmDish(props.table.id, dish.id)}><i class="fa fa-check"/></Button>
      </td>
    </tr>
  );
}


class Kitchen extends Component {

  confirmDish(tableId, dishId) {
    this.setState({loading: true});
    this.tableClient.setDoneDish(tableId,dishId)
      .then(() => this.updateKitchen())
      .then(() => this.setState({loading: false}));
  }

  constructor(props) {
    super(props);
    this.tableClient = new TableRestClient(props);
    this.state ={tables: [], loading: true, refresh: 5000};
    this.confirmDish = this.confirmDish.bind(this);
    this.timer = this.timer.bind(this);
  }

  updateKitchen() {
    return this.tableClient.getKitchen(0,10)
      .then((val) => {
        this.setState({tables: val.data.tables, loading: false});
        Reactotron.display({
          preview: "Retrieved tables",
          value: val.data.tables
        })
      }).catch((error)=>{
      Reactotron.error("Failed to retrieve tables", error);
    });
  }

  componentWillUnmount(){
    // use intervalId from the state to clear the interval
    clearInterval(this.state.intervalId);
  }

  componentDidMount() {
    this.updateKitchen()
      .then(()=>{
        this.setState({intervalId: setInterval(this.timer, this.state.refresh)})
      });
  }

  timer() {
    var newCount = this.state.currentCount - 1;
    if(newCount >= 0) {
      this.setState({ currentCount: newCount });
    } else {
      clearInterval(this.state.intervalId);
      this.updateKitchen()
        .then(()=>{
          this.setState({intervalId: setInterval(this.timer, this.state.refresh)})
        });
    }
  }

  render() {


    return (
      <div className="animated fadeIn">
        <Row>
          {this.state.tables.map((table, index) =>
            <KitchenItem key={index} table={table} self={this}/>
          )}
        </Row>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {token: state.authentication.token};
};

export default connect(mapStateToProps)(Kitchen);
