import React from 'react';
import DefaultLayout from './containers/DefaultLayout';
import i18n from './i18n';

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
  {path: '/', exact: true, name: i18n.t('global.home'), component: DefaultLayout, roles: ['ROLE_ADMIN']},
  {path: '/stats', name: i18n.t('navMenu.stats'), component: Dashboard, roles: ['ROLE_ADMIN']},
  {path: '/users', exact: true, name: i18n.t('navMenu.users'), component: Users, roles: ['ROLE_ADMIN']},
  {path: '/users/:id', exact: true, name: i18n.t('users.details'), component: User, roles: ['ROLE_ADMIN']},
  {path: '/dishes', exact: true, name: i18n.t('dishes.plural'), component: Dishes, roles: ['ROLE_ADMIN']},
  {path: '/dishes/:id', exact: true, name: i18n.t('dishes.details'), component: Dish, roles: ['ROLE_ADMIN']},
  {path: '/tables', exact: true, name: i18n.t('tables.plural'), component: Tables, roles: ['ROLE_ADMIN', 'ROLE_USER']},
  {path: '/tables/:id', exact: true, name: i18n.t('tables.details'), component: Table, roles: ['ROLE_ADMIN', 'ROLE_USER']},
  {path: '/kitchen', exact: true, name: i18n.t('navMenu.kitchen'), component: Kitchen, roles: ['ROLE_ADMIN', 'ROLE_USER']},
  {path: '/bills', exact: true, name: i18n.t('navMenu.bills'), component: Bills, roles: ['ROLE_ADMIN']},
  {path: '/bills/:id', exact: true, name: i18n.t('bills.details'), component: Bill, roles: ['ROLE_ADMIN']},
];

export default routes;
