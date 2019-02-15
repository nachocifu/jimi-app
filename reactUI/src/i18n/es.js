export default {
  global: {
    save: 'Guardar',
    cancel: 'Cancelar',
    back: 'Atrás',
    delete: 'Borrar',
    edit: 'Editar',
    confirm: 'Confirmar',
    home: 'Inicio',
    yes: 'Sí',
    no: 'No',
  },
  login: {
    title: 'Inicio',
    message: 'Ingrese sus credenciales',
    error: 'La información no es válida, ¿quiere intentar de nuevo?',
    username: 'Usuario',
    password: 'Contraseña',
  },
  navMenu: {
    tables: "Mesas",
    kitchen: 'Cocina',
    bills: 'Facturas',
    dishes: 'Platos',
    users: 'Usuarios',
    stats: 'Estadísticas',
  },
  tables: {
    plural: "Mesas",
    single: "Mesa",
    name: 'Nombre',
    details: 'Detalle de Mesa',
    status: 'Estado',
    operations: 'Operaciones',
    newTitle: 'Nueva Mesa',
    editTitle: 'Modificar Mesa',
    diners: 'Comensales',
    validation: {
      minLength: 'Mínimo 4 caracteres',
      maxLength: 'Máximo 20 caracteres',
      pattern: 'Solamente letras y números',
      required: 'Ingrese su nombre',
      maxStock: 'No alcanza el stock',
      minStock: 'Mínimo 1',
      amount: 'Cantidad',
      repeatedName: 'Nombre ya existe',
      inputName: 'Ingrese el nombre',
    },
    charged: 'Cobrado',
    print: 'Print',
    charge: 'Cobrar',
    diner: 'Comensal',
    addDish: 'Agregar Plato...',
  },
  dishes: {
    plural: 'Platos',
    single: 'Plato',
    details: 'Detalle de Plato',
    name: 'Nombre',
    price: 'Precio',
    status: 'Status',
    stock: 'Stock',
    minStock: 'Stock Mínimo',
    precio: 'Price',
    newTitle: 'Nuevo Plato',
    discontinued: 'Discontinuado',
    inProduction: 'En producción',
    setDiscontinued: 'Discontinuar',
    edit: 'Editar Plato',
    validation: {
      minLength: 'Mínimo 1 caracter',
      maxLength: 'Máximo 25 caracteres',
      pattern: 'Solamente letras y números',
      required: 'Ingrese su nombre',
      maxStock: 'No alcanza el stock',
      maxValue: 'Máximo de 10000',
      minStock: 'Mínimo de 0',
      min1: 'Mínimo de 1',
      requiredMinStock: 'Ingrese stock mínimo',
      requiredStock: 'Ingrese stock',
      requiredPrice: 'Ingrese precio',
      requiredName: 'Ingrese nombre',
      namePattern: 'Sólo letras y números',
      step1: 'Incremento de a 1',
      step001: 'Incremento de a 0.01',
      amount: 'Cantidad',
      repeatedName: 'Nombre ya existe',
      inputName: 'Ingrese el nombre',
      pricePattern: '5 dígitos enteros, 2 decimales como máximo',
    }
  },
  dashboard: {},
  users: {
    details: 'Detalle de usuario',
    name: 'Nombre',
    single: 'Usuario',
    new: 'Usuario Nuevo',
    password: 'Contraseña',
    confirmPassword: 'Confirmar Contraseña',
    confirmDelete: '¿Seguro desea eliminar el usuario?',
    validation: {
      requiredName: 'Ingrese nombre',
      requiredPassword: 'Ingrese contraseña',
      pattern: 'Solamente letras y números',
      minLength: 'Mínimo 6 caracteres',
      maxLength: 'Máximo 40 caracteres',
      passwordMatch: 'Deben coincidir las contraseñas',
      existentUsername: 'Ya existe un usuario con ese nombre',
    }
  },
  kitchen: {},
  bills: {
    details: 'Detalle de Factura'
  }
}
