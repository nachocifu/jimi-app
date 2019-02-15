import React, {Component} from 'react';
import {Line} from 'react-chartjs-2';
import {Card, CardBody, CardFooter, CardTitle, Col, Progress, Row,} from 'reactstrap';
import {connect} from "react-redux";
import {CustomTooltips} from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {getStyle, hexToRgba} from '@coreui/coreui/dist/js/coreui-utilities'
import Spinner from "reactstrap/es/Spinner";
import StatRestClient from "../../http/clients/StatRestClient";
import Reactotron from "reactotron-react-js";
import i18n from '../../i18n';

const brandInfo = getStyle('--info');

class Dashboard extends Component {

  statClient;

  stockLimit = 10;

  constructor(props) {
    super(props);
    this.statClient = new StatRestClient(props);

    this.state = {
      loading: true,
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

  buildChart = () => {
    Reactotron.debug(this.state);
    // generate data
    let labels = [];
    let dt1 = [];
    this.state.monthOrderTotals.forEach((val) => {
      labels.push(val.key);
      dt1.push(val.value);
    });
    Reactotron.display({preview: "Data", display: "Data", value: {labels: labels, dt1: dt1}});

    // Reactotron.debug();
    // gen options
    let opts = {
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
            scaleLabel: {
              display: true,
              labelString: i18n.t('dashboard.yearMonth')
            },
            gridLines: {
              drawOnChartArea: true,
            },
          }],
        yAxes: [
          {
            scaleLabel: {
              display: true,
              labelString: i18n.t('dashboard.value')
            },
            ticks: {
              beginAtZero: true,
              maxTicksLimit: 5,
              stepSize: Math.ceil(Math.max(...dt1) / 10),
              max: Math.ceil(Math.max(...dt1) + (Math.max(...dt1) * .1)),
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

    return {
      chart: {
        labels: labels,
        datasets: [
          {
            label: i18n.t('dashboard.revenue'),
            backgroundColor: hexToRgba(brandInfo, 10),
            borderColor: brandInfo,
            pointHoverBackgroundColor: '#fff',
            borderWidth: 2,
            data: dt1,
          },
        ],
      },
      options: opts,
    }
  };

  updateStats = () => {
    return this.statClient.getAll(this.stockLimit)
      .then((val) => {
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
          monthOrderTotals: val.data.monthOrderTotals.entry,
          monthlyOrdersCancelled: val.data.monthlyOrdersCancelled.entry,
        });
      });
  };

  componentDidMount() {
    this.updateStats()
      .then(this.setState({loading: false}));
  }


  render() {

    if (this.state.loading === true) return <Spinner style={{width: '3rem', height: '3rem'}}/>;

    return (
      <div className="animated fadeIn">
        <Row>
          <Col xs="12" sm="6" lg="3">
            <Card className="text-white bg-success">
              <CardBody className="pb-0">
                <div className="text-value">{this.state.totalAmountOfFreeTables}</div>
                <div>{i18n.t('dashboard.freeTables')}</div>
              </CardBody>
              <div className="chart-wrapper mx-3" style={{height: '30px'}}/>
            </Card>
          </Col>
          <Col xs="12" sm="6" lg="3">
            <Card className="text-white bg-warning">
              <CardBody className="pb-0">
                <div className="text-value">{this.state.totalAmountOfBusyTables}</div>
                <div>{i18n.t('dashboard.occupiedTables')}</div>
              </CardBody>
              <div className="chart-wrapper mx-3" style={{height: '30px'}}/>
            </Card>
          </Col>
          <Col xs="12" sm="6" lg="3">
            <Card className="text-white bg-danger">
              <CardBody className="pb-0">
                <div className="text-value">{this.state.totalAmountOfPayingTables}</div>
                <div>{i18n.t('dashboard.payingTables')}</div>
              </CardBody>
              <div className="chart-wrapper mx-3" style={{height: '30px'}}/>
            </Card>
          </Col>
          <Col xs="12" sm="6" lg="3">
            <Card className="text-white bg-info">
              <CardBody className="pb-0">
                <div className="text-value">{this.state.totalAmountOfTables}</div>
                <div>{i18n.t('dashboard.totalTables')}</div>
              </CardBody>
              <div className="chart-wrapper mx-3" style={{height: '30px'}}/>
            </Card>
          </Col>
        </Row>
        <Row>
          <Col>
            <Card>
              <CardBody>
                <Row>
                  <Col sm="5">
                    <CardTitle className="mb-0">{i18n.t('dashboard.report')}</CardTitle>
                  </Col>
                </Row>
                <div className="chart-wrapper" style={{height: 300 + 'px', marginTop: 40 + 'px'}}>
                  <Line data={this.buildChart().chart} options={this.buildChart().options} height={300}/>
                </div>
              </CardBody>
              <CardFooter>
                <Row className="text-center">
                  <Col sm={12} md className="mb-sm-2 mb-0">
                    <div className="text-muted">{i18n.t('dashboard.tables')}</div>
                    <strong>{this.state.freeTablesPercentage}% {i18n.t('dashboard.currentlyFree')}</strong>
                    <Progress className="progress-xs mt-2" color="success" value={this.state.freeTablesPercentage}/>
                  </Col>
                  <Col sm={12} md className="mb-sm-2 mb-0 d-md-down-none">
                    <div className="text-muted">{i18n.t('dashboard.dishes')}</div>
                    <strong>{this.state.stockStatePercentage}% {i18n.t('dashboard.currentlyUnderStock')} {this.stockLimit}</strong>
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
