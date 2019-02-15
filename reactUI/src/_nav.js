import i18n from './i18n';

export default {
  items: [
    {
      name: i18n.t('navMenu.stats'),
      url: '/stats',
      icon: 'icon-speedometer',
      roles: ['ROLE_ADMIN']
    },
    {
      name: i18n.t('navMenu.bills'),
      class: '',
      url: '/bills',
      icon: 'icon-speedometer',
      roles: ['ROLE_ADMIN']
    },
    {
      name: i18n.t('navMenu.dishes'),
      class: '',
      url: '/dishes',
      icon: 'icon-speedometer',
      roles: ['ROLE_ADMIN']
    },
    {
      name: i18n.t('navMenu.users'),
      class: '',
      url: '/users',
      icon: 'icon-people',
      roles: ['ROLE_ADMIN']
    },
    {
      name: i18n.t('navMenu.kitchen'),
      class: '',
      url: '/kitchen',
      icon: 'icon-people',
      roles: ['ROLE_ADMIN', 'ROLE_USER']
    },
    {
      name: i18n.t('navMenu.tables'),
      class: '',
      url: '/tables',
      icon: 'icon-people',
      roles: ['ROLE_ADMIN', 'ROLE_USER']
    },
  ],
};
