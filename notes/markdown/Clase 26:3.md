# Clase 26/3
#PAW

Para el tp: **JAVA 8**
- - - -

Siempre que devuelvas colecciones nunca devuelvas NULL, devolve lista vacía -> _least surprise_

NULL es mala palabra desde Java 8 -> **Optional**User [igual que _Maybe_!!! en Haskell :))))))] 

userList.stream().findFirst() retorna un Optional<User> -> null safe

orElseThrow método que devuelve Optional y te pide excepción (factory).
- - - -

Definir nueva dependencia: 
```
<groupId>org.postgresql</groupId>
<artifactId?postgresql</artifactId>
<version>${postgresql.version}</version> .. 


# agrego property en pom.xml y persistence/pom.xml
<postgre .. 
```

y después mvn eclipse:eclipse
- - - -
WebConfig

```
@Bean
 ... viewResolver() {
}

@Bean
public DataSource dataSource() {
	final SimpleDriverDataSource ds = new SimpleDriverDataSource();

ds.setDriverClass(org.postgresql.Driver.class)
ds.setUrl("jdbc:postgresql://localhost/paw")
ds.setUsername()
ds.setPassword()

```

agrego dependencia de postgresql a webapp pom.

- - - -
@Autowired: spring se mete y te completa todo
- - - -
### Crear database

psql paw -U root -W

Evitar escribir query a mano:

```
@Autowired
public UserJdbcDao(final DataSource ds) 	{
jdbcTemplate = new JdbcTemplate(ds);
jdbcTemplate.execute(“CREATE TABLE IF NOT EXISTS user (“ +
	“id SERIAL PRIMARY KEY,” +
	“username varchar(100) UNIQUE” +
	“)”);
} 
```

en UserJdbcDao:

```
@Override
public User create(String username) {
	final Map<String, Object> = args
	args.put(“username”, username);
	final Number userId = jdbcInsert.executeAndReturnKey(args);
	return new User(userId.longValue(), username);
}
```

Me garantiza pleno control en un solo lugar que las entidades de modelo se reflejan en persistencia (no creo instancias del modelo que no existan).

user tiene el id auto generado  y _username_.

- - - -

En HelloWorldController

No hay ningún flujo normal donde mi aplicación no pueda retornar un user.

@RequestMapping(“/create”)
public ModelAndView create(@RequestParam(value = “name”, required = true) f….
	final User u = us.create(username);
	return new ModelAndView(“redirect:_user_“ + u.getId());
}