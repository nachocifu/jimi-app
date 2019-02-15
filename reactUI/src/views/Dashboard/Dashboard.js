import React, {Component, lazy, Suspense} from 'react';
import {Bar, Line} from 'react-chartjs-2';
import {
  Badge,
  Button,
  ButtonDropdown,
  ButtonGroup,
  ButtonToolbar,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  CardTitle,
  Col,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  Progress,
  Row,
  Table,
} from 'reactstrap';
import {connect} from "react-redux";
import {CustomTooltips} from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {getStyle, hexToRgba} from '@coreui/coreui/dist/js/coreui-utilities'
import Spinner from "reactstrap/es/Spinner";
import StatRestClient from "../../http/clients/StatRestClient";
import Reactotron from "reactotron-react-js";
const Widget03 = lazy(() => import('../../views/Widgets/Widget03'));

const brandPrimary = getStyle('--primary');
const brandSuccess = getStyle('--success');
const brandInfo = getStyle('--info');
const brandWarning = getStyle('--warning');
const brandDanger = getStyle('--danger');

const data1 = [];
const data2 = [];
const data3 = [];


const mainChart = {
  labels: ['Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'],
  datasets: [
    {
      label: 'My First dataset',
      backgroundColor: hexToRgba(brandInfo, 10),
      borderColor: brandInfo,
      pointHoverBackgroundColor: '#fff',
      borderWidth: 2,
      data: data1,
    },
    {
      label: 'My Second dataset',
      backgroundColor: 'transparent',
      borderColor: brandSuccess,
      pointHoverBackgroundColor: '#fff',
      borderWidth: 2,
      data: data2,
    },
    {
      label: 'My Third dataset',
      backgroundColor: 'transparent',
      borderColor: brandDanger,
      pointHoverBackgroundColor: '#fff',
      borderWidth: 1,
      borderDash: [8, 5],
      data: data3,
    },
  ],
};

const mainChartOpts = {
  tooltips: {
    enabled: false,
    custom: CustomTooltips,
    intersect: true,
    mode: 'index',
    position: 'nearest',
    callbacks: {
      labelColor: function (tooltipItem, chart) {
        return {backgroundColor: chart.data.datasets[tooltipItem.datasetIndex].borderColor}
      }
    }
  },
  maintainAspectRatio: false,
  legend: {
    display: false,
  },
  scales: {
    xAxes: [
      {
        gridLines: {
          drawOnChartArea: false,
        },
      }],
    yAxes: [
      {
        ticks: {
          beginAtZero: true,
          maxTicksLimit: 5,
          stepSize: Math.ceil(250 / 5),
          max: 250,
        },
      }],
  },
  elements: {
    point: {
      radius: 0,
      hitRadius: 10,
      hoverRadius: 4,
      hoverBorderWidth: 3,
    },
  },
};

class Dashboard extends Component {

  statClient;

  constructor(props) {
    super(props);
  this.statClient = new StatRestClient(props);

    this.state = {
      loading: false,
      totalAmountOfFreeTables: 0,
      totalAmountOfBusyTables: 0,
      totalAmountOfPayingTables: 0,
      totalAmountOfTables: 0,
      freeTablesPercentage: 0,
      stockStatePercentage: 0,
      monthOrderTotals: [],
      monthlyOrdersCancelled: [],
    };
  }

  updateStats = () => {
    return this.statClient.getAll()
      .then( (val) => {
        Reactotron.display({
          name: 'Get Stats',
          preview: 'Get Stats',
          value: val.data
        });
        this.setState({
          totalAmountOfFreeTables: val.data.totalAmountOfFreeTables,
          totalAmountOfBusyTables: val.data.totalAmountOfBusyTables,
          totalAmountOfPayingTables: val.data.totalAmountOfPayingTables,
          totalAmountOfTables: val.data.totalAmountOfTables,
          freeTablesPercentage: val.data.freeTablesPercentage,
          stockStatePercentage: val.data.stockStatePercentage,
          monthOrderTotals: [],
          monthlyOrdersCancelled: [],
        });
      });
  };

  componentDidMount() {
    this.updateStats();
  }


  render() {

    if(this.state.loading === true) return <Spinner style={{ width: '3rem', height: '3rem' }} />;

    return (
      <div className="animated fadeIn">
        <Row>
          <Col xs="12" sm="6" lg="3">
            <Card className="text-white bg-success">
              <CardBody className="pb-0">
                <div className="text-value">{this.state.totalAmountOfFreeTables}</div>
                <div>Tables FREE</div>
              </CardBody>
              <div className="chart-wrapper mx-3" style={{height: '30px'}}></div>
            </Card>
          </Col>
          <Col xs="12" sm="6" lg="3">
            <Card className="text-white bg-warning">
              <CardBody className="pb-0">
                <div className="text-value">{this.state.totalAmountOfBusyTables}</div>
                <div>Tables BUSY</div>
              </CardBody>
              <div className="chart-wrapper mx-3" style={{height: '30px'}}></div>
            </Card>
          </Col>
          <Col xs="12" sm="6" lg="3">
            <Card className="text-white bg-danger">
              <CardBody className="pb-0">
                <div className="text-value">{this.state.totalAmountOfPayingTables}</div>
                <div>Tables PAYING</div>
              </CardBody>
              <div className="chart-wrapper mx-3" style={{height: '30px'}}></div>
            </Card>
          </Col>
          <Col xs="12" sm="6" lg="3">
            <Card className="text-white bg-info">
              <CardBody className="pb-0">
                <div className="text-value">{this.state.totalAmountOfTables}</div>
                <div>Total Tables</div>
              </CardBody>
              <div className="chart-wrapper mx-3" style={{height: '30px'}}></div>
            </Card>
          </Col>
        </Row>
        <Row>
          <Col>
            <Card>
              <CardBody>
                <Row>
                  <Col sm="5">
                    <CardTitle className="mb-0">Traffic</CardTitle>
                    <div className="small text-muted">November 2015</div>
                  </Col>
                  <Col sm="7" className="d-none d-sm-inline-block">
                    <Button color="primary" className="float-right"><i className="icon-cloud-download"/></Button>
                    <ButtonToolbar className="float-right" aria-label="Toolbar with button groups">
                      <ButtonGroup className="mr-3" aria-label="First group">
                        <Button color="outline-secondary" onClick={() => this.onRadioBtnClick(1)}
                                active={this.state.radioSelected === 1}>Day</Button>
                        <Button color="outline-secondary" onClick={() => this.onRadioBtnClick(2)}
                                active={this.state.radioSelected === 2}>Month</Button>
                        <Button color="outline-secondary" onClick={() => this.onRadioBtnClick(3)}
                                active={this.state.radioSelected === 3}>Year</Button>
                      </ButtonGroup>
                    </ButtonToolbar>
                  </Col>
                </Row>
                <div className="chart-wrapper" style={{height: 300 + 'px', marginTop: 40 + 'px'}}>
                  <Line data={mainChart} options={mainChartOpts} height={300}/>
                </div>
              </CardBody>
              <CardFooter>
                <Row className="text-center">
                  <Col sm={12} md className="mb-sm-2 mb-0">
                    <div className="text-muted">Tables</div>
                    <strong>{this.state.freeTablesPercentage}% Currently Free</strong>
                    <Progress className="progress-xs mt-2" color="success" value={this.state.freeTablesPercentage}/>
                  </Col>
                  <Col sm={12} md className="mb-sm-2 mb-0 d-md-down-none">
                    <div className="text-muted">Dishes</div>
                    <strong>{this.state.stockStatePercentage}% Current Global Stock </strong>
                    <Progress className="progress-xs mt-2" color="info" value={this.state.stockStatePercentage}/>
                  </Col>
                </Row>
              </CardFooter>
            </Card>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    token: state.authentication.token,
    roles: state.authentication.roles
  };
};

export default connect(mapStateToProps)(Dashboard);
