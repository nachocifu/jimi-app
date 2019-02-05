import React, {Component} from 'react';
import {HashRouter, Redirect, Route, Switch} from 'react-router-dom';
// import { renderRoutes } from 'react-router-config';
import Loadable from 'react-loadable';
import './App.scss';
import Reactotron from 'reactotron-react-js';
import {Provider} from "react-redux";
import rootReducer from './redux/reducers'
import {reactotronRedux} from 'reactotron-redux'
import {INIT, LOGIN_SUCCESSFULL} from "./redux/actions/actionTypes";

const loading = () => <div className="animated fadeIn pt-3 text-center">Loading...</div>;

// Containers
const DefaultLayout = Loadable({
  loader: () => import('./containers/DefaultLayout'),
  loading
});

// Pages
const Login = Loadable({
  loader: () => import('./views/Pages/Login'),
  loading
});

const Register = Loadable({
  loader: () => import('./views/Pages/Register'),
  loading
});

const Page404 = Loadable({
  loader: () => import('./views/Pages/Page404'),
  loading
});

const Page500 = Loadable({
  loader: () => import('./views/Pages/Page500'),
  loading
});

class App extends Component {

  store = null;

  constructor(props) {
    super(props);
    Reactotron.configure().use(reactotronRedux());
    // eslint-disable-next-line
    if (process.env.NODE_ENV != 'production') Reactotron.connect();

    this.store = Reactotron.createStore(rootReducer);
  }

  render() {
    return (
      <Provider store={this.store}>
        <HashRouter>
          <Switch>
            <Route exact path="/login" name="Login Page" component={Login}/>
            <Route exact path="/register" name="Register Page" component={Register}/>
            <Route exact path="/404" name="Page 404" component={Page404}/>
            <Route exact path="/500" name="Page 500" component={Page500}/>
            {/*<Route path="/" name="Stadistics" component={DefaultLayout}/>*/}
            <Route path="/" name="Stadisticts" render={(val) => (
              this.store.getState().authentication.status !== LOGIN_SUCCESSFULL ? (
                <Redirect to="/login"/>
              ) : (
                <DefaultLayout/>
              )

            )}/>
          </Switch>
        </HashRouter>
      </Provider>
    );
  }
}

export default App;
