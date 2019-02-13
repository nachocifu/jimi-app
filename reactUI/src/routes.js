import React from 'react';
import DefaultLayout from './containers/DefaultLayout';

const Breadcrumbs = React.lazy(() => import('./views/Base/Breadcrumbs'));
const Cards = React.lazy(() => import('./views/Base/Cards'));
const Carousels = React.lazy(() => import('./views/Base/Carousels'));
const Collapses = React.lazy(() => import('./views/Base/Collapses'));
const Dropdowns = React.lazy(() => import('./views/Base/Dropdowns'));
const Forms = React.lazy(() => import('./views/Base/Forms'));
const Jumbotrons = React.lazy(() => import('./views/Base/Jumbotrons'));
const ListGroups = React.lazy(() => import('./views/Base/ListGroups'));
const Navbars = React.lazy(() => import('./views/Base/Navbars'));
const Navs = React.lazy(() => import('./views/Base/Navs'));
const Paginations = React.lazy(() => import('./views/Base/Paginations'));
const Popovers = React.lazy(() => import('./views/Base/Popovers'));
const ProgressBar = React.lazy(() => import('./views/Base/ProgressBar'));
const Switches = React.lazy(() => import('./views/Base/Switches'));
const TablesLegacy = React.lazy(() => import('./views/Base/Tables'));
const Tabs = React.lazy(() => import('./views/Base/Tabs'));
const Tooltips = React.lazy(() => import('./views/Base/Tooltips'));
const BrandButtons = React.lazy(() => import('./views/Buttons/BrandButtons'));
const ButtonDropdowns = React.lazy(() => import('./views/Buttons/ButtonDropdowns'));
const ButtonGroups = React.lazy(() => import('./views/Buttons/ButtonGroups'));
const Buttons = React.lazy(() => import('./views/Buttons/Buttons'));
const Charts = React.lazy(() => import('./views/Charts'));
const Dashboard = React.lazy(() => import('./views/Dashboard'));
const CoreUIIcons = React.lazy(() => import('./views/Icons/CoreUIIcons'));
const Flags = React.lazy(() => import('./views/Icons/Flags'));
const FontAwesome = React.lazy(() => import('./views/Icons/FontAwesome'));
const SimpleLineIcons = React.lazy(() => import('./views/Icons/SimpleLineIcons'));
const Alerts = React.lazy(() => import('./views/Notifications/Alerts'));
const Badges = React.lazy(() => import('./views/Notifications/Badges'));
const Modals = React.lazy(() => import('./views/Notifications/Modals'));
const Colors = React.lazy(() => import('./views/Theme/Colors'));
const Typography = React.lazy(() => import('./views/Theme/Typography'));
const Widgets = React.lazy(() => import('./views/Widgets/Widgets'));
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
  {path: '/theme', exact: true, name: 'Theme', component: Colors, roles: ['ROLE_ADMIN']},
  {path: '/theme/colors', name: 'Colors', component: Colors, roles: ['ROLE_ADMIN']},
  {path: '/theme/typography', name: 'Typography', component: Typography, roles: ['ROLE_ADMIN']},
  {path: '/base', exact: true, name: 'Base', component: Cards, roles: ['ROLE_ADMIN']},
  {path: '/base/cards', name: 'Cards', component: Cards, roles: ['ROLE_ADMIN']},
  {path: '/base/forms', name: 'Forms', component: Forms, roles: ['ROLE_ADMIN']},
  {path: '/base/switches', name: 'Switches', component: Switches, roles: ['ROLE_ADMIN']},
  {path: '/base/tables', name: 'Tables', component: TablesLegacy, roles: ['ROLE_ADMIN']},
  {path: '/base/tabs', name: 'Tabs', component: Tabs, roles: ['ROLE_ADMIN']},
  {path: '/base/breadcrumbs', name: 'Breadcrumbs', component: Breadcrumbs, roles: ['ROLE_ADMIN']},
  {path: '/base/carousels', name: 'Carousel', component: Carousels, roles: ['ROLE_ADMIN']},
  {path: '/base/collapses', name: 'Collapse', component: Collapses, roles: ['ROLE_ADMIN']},
  {path: '/base/dropdowns', name: 'Dropdowns', component: Dropdowns, roles: ['ROLE_ADMIN']},
  {path: '/base/jumbotrons', name: 'Jumbotrons', component: Jumbotrons, roles: ['ROLE_ADMIN']},
  {path: '/base/list-groups', name: 'List Groups', component: ListGroups, roles: ['ROLE_ADMIN']},
  {path: '/base/navbars', name: 'Navbars', component: Navbars, roles: ['ROLE_ADMIN']},
  {path: '/base/navs', name: 'Navs', component: Navs, roles: ['ROLE_ADMIN']},
  {path: '/base/paginations', name: 'Paginations', component: Paginations, roles: ['ROLE_ADMIN']},
  {path: '/base/popovers', name: 'Popovers', component: Popovers, roles: ['ROLE_ADMIN']},
  {path: '/base/progress-bar', name: 'Progress Bar', component: ProgressBar, roles: ['ROLE_ADMIN']},
  {path: '/base/tooltips', name: 'Tooltips', component: Tooltips, roles: ['ROLE_ADMIN']},
  {path: '/buttons', exact: true, name: 'Buttons', component: Buttons, roles: ['ROLE_ADMIN']},
  {path: '/buttons/buttons', name: 'Buttons', component: Buttons, roles: ['ROLE_ADMIN']},
  {path: '/buttons/button-dropdowns', name: 'Button Dropdowns', component: ButtonDropdowns, roles: ['ROLE_ADMIN']},
  {path: '/buttons/button-groups', name: 'Button Groups', component: ButtonGroups, roles: ['ROLE_ADMIN']},
  {path: '/buttons/brand-buttons', name: 'Brand Buttons', component: BrandButtons, roles: ['ROLE_ADMIN']},
  {path: '/icons', exact: true, name: 'Icons', component: CoreUIIcons, roles: ['ROLE_ADMIN']},
  {path: '/icons/coreui-icons', name: 'CoreUI Icons', component: CoreUIIcons, roles: ['ROLE_ADMIN']},
  {path: '/icons/flags', name: 'Flags', component: Flags, roles: ['ROLE_ADMIN']},
  {path: '/icons/font-awesome', name: 'Font Awesome', component: FontAwesome, roles: ['ROLE_ADMIN']},
  {path: '/icons/simple-line-icons', name: 'Simple Line Icons', component: SimpleLineIcons, roles: ['ROLE_ADMIN']},
  {path: '/notifications', exact: true, name: 'Notifications', component: Alerts, roles: ['ROLE_ADMIN']},
  {path: '/notifications/alerts', name: 'Alerts', component: Alerts, roles: ['ROLE_ADMIN']},
  {path: '/notifications/badges', name: 'Badges', component: Badges, roles: ['ROLE_ADMIN']},
  {path: '/notifications/modals', name: 'Modals', component: Modals, roles: ['ROLE_ADMIN']},
  {path: '/widgets', name: 'Widgets', component: Widgets, roles: ['ROLE_ADMIN']},
  {path: '/charts', name: 'Charts', component: Charts, roles: ['ROLE_ADMIN']},
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
