# Configuracion de Tomcat
#school/PAW

* Primero en Run Configuration crear una config nueva del tipo Tomcat Local Server.
* Despues de `Aplication server` tocar `configure`.
* Ahi pasarle el directorio de tomcat unzipeado. Ponelo en el root de tu user (_Users_vos/) asi no lo tocas **nunca**.
* En la carpeta de apache poner `chmod 777 bin/catalina.sh`.
* Destildar `After launch`.
* Tildar `Deploy application configured...`
* En before launch:
	* +
	* Maven run
	* En commando pones `package`
* OK.