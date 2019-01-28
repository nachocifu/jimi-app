import React, {Component} from 'react';
import {Nav, NavItem, NavLink} from 'reactstrap';
import PropTypes from 'prop-types';
import {AppSidebarToggler} from '@coreui/react';
import Button from "reactstrap/es/Button";

const propTypes = {
  children: PropTypes.node,
};

const defaultProps = {};

class DefaultHeader extends Component {
  render() {

    // eslint-disable-next-line
    const {children, ...attributes} = this.props;

    return (
      <React.Fragment>
        <AppSidebarToggler className="d-lg-none" display="md" mobile/>
        <AppSidebarToggler className="d-md-down-none" display="lg"/>

        <Nav className="d-md-down-none" navbar>
          <NavItem className="px-3">
            <NavLink href="#">Tables</NavLink>
          </NavItem>
          <NavItem className="px-3">
            <NavLink href="#">Kitchen</NavLink>
          </NavItem>
        </Nav>
        <Nav className="ml-auto" navbar>
          <NavItem className="px-3">
            <NavLink href="#">
              <Button onClick={e => this.props.onLogout(e)} size={'lg'} color={'danger'}>
                <i className="fa fa-lock"></i>
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

export default DefaultHeader;
