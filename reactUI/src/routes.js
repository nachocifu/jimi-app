import React from 'react';
import DefaultLayout from './containers/DefaultLayout';
const Dashboard = React.lazy(() => import('./views/Dashboard'));
const Users = React.lazy(() => import('./views/Users/Users'));
const User = React.lazy(() => import('./views/Users/User'));
const Dishes = React.lazy(() => import('./views/Dishes/Dishes'));
const Dish = React.lazy(() => import('./views/Dishes/Dish'));
const Tables = React.lazy(() => import('./views/Tables/Tables'));
const Table = React.lazy(() => import('./views/Tables/Table'));
const Kitchen = React.lazy(() => import('./views/Kitchen/Kitchen'));
const Bills = React.lazy(() => import('./views/Bills/Bills'));
const Bill = React.lazy(() => import('./views/Bills/Bill'));

// https://github.com/ReactTraining/react-router/tree/master/packages/react-router-config
const routes = [
  {path: '/', exact: true, name: 'Home', component: DefaultLayout, roles: ['ROLE_ADMIN']},
  {path: '/stats', name: 'Stadistics', component: Dashboard, roles: ['ROLE_ADMIN']},
  {path: '/users', exact: true, name: 'Users', component: Users, roles: ['ROLE_ADMIN']},
  {path: '/users/:id', exact: true, name: 'User Details', component: User, roles: ['ROLE_ADMIN']},
  {path: '/dishes', exact: true, name: 'Dishes', component: Dishes, roles: ['ROLE_ADMIN']},
  {path: '/dishes/:id', exact: true, name: 'Dish Details', component: Dish, roles: ['ROLE_ADMIN']},
  {path: '/tables', exact: true, name: 'Tables', component: Tables, roles: ['ROLE_ADMIN', 'ROLE_USER']},
  {path: '/tables/:id', exact: true, name: 'Table Details', component: Table, roles: ['ROLE_ADMIN', 'ROLE_USER']},
  {path: '/kitchen', exact: true, name: 'Kitchen', component: Kitchen, roles: ['ROLE_ADMIN', 'ROLE_USER']},
  {path: '/bills', exact: true, name: 'Bills', component: Bills, roles: ['ROLE_ADMIN']},
  {path: '/bills/:id', exact: true, name: 'Bill Details', component: Bill, roles: ['ROLE_ADMIN']},
];

export default routes;
