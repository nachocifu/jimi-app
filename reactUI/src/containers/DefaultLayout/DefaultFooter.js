import React, {Component} from 'react';
import PropTypes from 'prop-types';
import Button from "reactstrap/es/Button";

const propTypes = {
  children: PropTypes.node,
};

const defaultProps = {};

class DefaultFooter extends Component {
  render() {

    // eslint-disable-next-line
    const {children, ...attributes} = this.props;

    return (
      <React.Fragment>
        <span>JIMI &copy; 2019</span>
        <span className="ml-auto">
          <Button className="btn-github btn-brand ml-1 mr-1"><i
            className="fa fa-github"></i><span>nachocifu</span></Button>
          <Button className="btn-github btn-brand ml-1 mr-1"><i className="fa fa-github"></i><span>j1nma</span></Button>
        </span>
      </React.Fragment>
    );
  }
}

DefaultFooter.propTypes = propTypes;
DefaultFooter.defaultProps = defaultProps;

export default DefaultFooter;
