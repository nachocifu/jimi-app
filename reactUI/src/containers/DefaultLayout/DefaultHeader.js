import React, {Component} from 'react';
import {Nav, NavItem, NavLink} from 'reactstrap';
import PropTypes from 'prop-types';
import {AppSidebarToggler} from '@coreui/react';
import Button from "reactstrap/es/Button";
import {connect} from "react-redux";
import {LOGOUT} from "../../redux/actions/actionTypes";
import {Redirect} from "react-router-dom";
import { withRouter } from 'react-router'
const propTypes = {
  children: PropTypes.node,
};

const defaultProps = {};

class DefaultHeader extends Component {

  constructor(props) {
    super(props);
    this.state = {
      redirectLogOut: false,
    };
    this.test = this.test.bind(this);
  }

  test = () => {
    this.props.dispatch({type: LOGOUT});
    this.props.history.push('login');
  };

  render() {
    if (this.state.redirectLogOut) {
      // this.props.history.push('/login');
      return <Redirect to={'/login'}/>;
    }

    // eslint-disable-next-line
    const {children, ...attributes} = this.props;

    return (
      <React.Fragment>
        <AppSidebarToggler className="d-lg-none" display="md" mobile/>
        <AppSidebarToggler className="d-md-down-none" display="lg"/>
        <Nav className="ml-auto" navbar>
          <NavItem className="px-3">
            <NavLink href="#">
              <Button onClick={e => this.test()} size={'lg'} color={'danger'}>
                <i className="fa fa-lock"/>
              </Button>
            </NavLink>
          </NavItem>
        </Nav>
      </React.Fragment>
    );
  }
}

DefaultHeader.propTypes = propTypes;
DefaultHeader.defaultProps = defaultProps;

export default withRouter(connect()(DefaultHeader));
