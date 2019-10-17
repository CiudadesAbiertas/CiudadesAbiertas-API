
# API CIUDADES ABIERTAS

Esta es la documentación de la API Ciudades Abiertas.

Contenido:
   
     - Requerimientos 
     - Primeros pasos 
     - Inicialización de Base de datos
     - Despliegue en desarrollo
     - Despliegue en producción
     - Utilizando la API
     - Parámetros en las operaciones
     - Configurar Autenticación de Peticiones 
     - Encriptación de contraseña de BBDD
     - Datos Generales asociados a la API
     - Activación JNDI
     - Personalización


## Requerimientos

  - Java 1.8 (compatible con OpenJDK 11)
  - Git
  - Maven
  - Sistema de relacional de BBDD: Oracle, SQLServer y MySQL

## Primeros pasos

Para empezar debemos descargar el repositorio a través de la herramienta git.

El repositorio se encuentra en la URL: 

[https://github.com/CiudadesAbiertas/CiudadesAbiertas-API](https://github.com/CiudadesAbiertas/CiudadesAbiertas-API)

Para descargarlo se utilizaran los siguientes comandos: 

```sh
$ mkdir ciudadesAbiertas
$ cd ciudadesAbiertas
$ git clone https://github.com/CiudadesAbiertas/CiudadesAbiertas-API
```


## Inicialización de Base de datos

Dentro de este repositorio se encuentra el directorio "liquibase_CA" que contiene un proyecto para realizar la inicialización de la base de datos y mantener un versionado de la misma.

Es necesario que ya se tenga un usuario de base de datos creado y con privilegios de escritura y generación de tablas.

Antes de continuar se debe configurar correctamente la conexión con la base de datos.

Para esto hay que editar el fichero que se encuentra en la ruta: "liquibase_CA\src\main\resources\liquibase"

Este fichero tiene la siguiente estructura:

```sh
driver=
url=
username=
password=
```

Para configurarlo para una conexión contra una BBDD Oracle se podría configurar así:

```sh
driver=oracle.jdbc.OracleDriver
url=jdbc:oracle:thin:@//localhost:1521/xe
username=ciudadesAbiertas
password=ciudadesAbiertas
```

Una vez configurado se debe lanzar el proceso para la generación de las tablas:

```sh
cd ciudadesAbiertas\liquibase_CA
mvn install
mvn liquibase:update
```

Nota: si se quiere aumentar el nivel de log, se puede cambiar el último comando por:

```sh
mvn -X liquibase:update
```


En unos minutos se deben haber creado un serie de tablas en el esquema que hemos configurado


## Despliegue en desarrollo

El IDE utilizado es Eclipse (Photon Release 4.8.0) configurado con Oracle Java 8 y usando Tomcat 8.5 como servidor.

En el árbol de proyectos se pulsa botón derecho con el ratón y se pulsa "import", y nuevamente "import":

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/import1.PNG "Importando el proyecto paso 1")

A continuación se selecciona "Existing Maven Projects":

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/import2.PNG "Importando el proyecto paso 2")

Se pulsa en la esquina superior derecha en el botón "browse", se selecciona la ruta donde esta el proyecto.

En unos segundos aparecerán todos los módulos desarrollados dentro del repositorio, se debe marcar todo los que aparecen dentro de API_CA: 

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/import3.PNG "Importando el proyecto paso 3")

Se pulsa el botón "Finish" y en unos segundos se debe ver el siguiente arbol de proyectos:

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/imported.PNG "solución importada")

Se le debe dar unos minutos para que Eclipse termine de descargarse todas las librerias que necesite.

### Configuración

Ahora se debe configurar el proyecto indicando los diversos parámetros para que se inicie correctamente.

Esto se realizará a través del fichero: \API_CA\API_WEB\src\main\resources\config.properties

Este fichero tiene la siguiente estructura:

```sh
URIBase=

contexto=

pagina.maximo=
pagina.defecto=

peticiones.maximas.segundo.anonimas=
peticiones.maximas.segundo.identificadas=

nodo.pattern=

https=

tokenTime=
appname=
appsecret=

html.title=

xy.value.epsg=
lat_lon.value.epsg=

path.template.html=

rsql.log.active=

db.driver=
db.url=
db.user=
db.password=
db.initialSize=
db.maxActive=
db.maxIdle=
db.minIdle=
db.hibernate.dialect=
db.validationQuery=

hibernate.show_sql=
hibernate.format_sql=
hibernate.use_sql_comments=
```

A continuación se muestra una configuración básica utilizando Oracle comentando cada parámetro:


```sh
#URIBase para generar RDF, no debe acabar en /
URIBase=https://ciudadesabiertas.es

#Contexto para generar RDF, si esta vacio se detectara automáticamente. En caso contrario debe empezar por /
#contexto=/OpenCitiesAPI
contexto=

#valores por defecto de la paginación
pagina.maximo=500
pagina.defecto=100

#Peticiones maximas por segundo
peticiones.maximas.segundo.anonimas=50
peticiones.maximas.segundo.identificadas=25

#Nodo Patron para Id Estadisticas
nodo.pattern=A

#Https mode (on/off) (en producción SIEMPRE on con https!!!!) 
https=off

#tokenTime (segundos duración del token)
tokenTime=3600

#API Configuración de la seguridad
appname=api
appsecret=000

#Cabecera html
html.title=Ciudades Abiertas

#vble para la configuracion de las coordenadas geograficas SOPORTAMOS (EPSG:25830,EPSG:25829,EPSG:25828,EPSG:25831,EPSG:23030,EPSG:23029,EPSG:23028,EPSG:23031)
xy.value.epsg=EPSG:25830
#vble para la configuracion de las coordenadas geograficas  SOPORTAMOS (EPSG:4258, EPSG:4230, EPSG:4326)
lat_lon.value.epsg=EPSG:4258

#vble configuracion path template pueden ser rutas:
#- absolutas (D:\\git\\CiudadesAbiertas\\API_CA\\API_WEB\\src\\main\\webapp\\WEB-INF\\templates\\) 
#- relativas a la app (/WEB-INF/templates/)
#- si no existe el parametro path.template.html por defecto apunta a la ruta relativa /WEB-INF/templates/
path.template.html=/WEB-INF/templates/

#RSQL ON / OFF LOG para el paquete de RSQL
rsql.log.active=off

#Configuración BBDD 

#Configuracion Oracle
db.driver=oracle.jdbc.OracleDriver
db.url=jdbc:oracle:thin:@127.0.0.1:1521:xe
db.user=ciudadesAbiertas
db.password=ciudadesAbiertas
db.initialSize=5
db.maxActive=10
db.maxIdle=10
db.minIdle=0
db.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
db.validationQuery=Select 1 from dual


#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=false
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=true
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=true

#Control para el listado de peticiones a requerir identificación, este control debe realizares por 
#modulo y no se incluye en el generico (config.properties)
#A continuación comentado se muestra un ejemplo de la funcionalidad para el módulo de subveciones que
#debería incluirse en su fichero de carga(subvecion.properties) las etiquetas asociadas estan
#documentadas y serian los id concretos que se establecen en el Controlador del Módulo en cuestión.
#peticiones.identificadas.public_auth=SUB_ADD
#peticiones.identificadas.basic_auth=SUB_LIST,SUB_UPDATE
#peticiones.identificadas.admin_auth= SUB_DELETE
#Nota: No es necesarios incluir todas las peticiones disponibles solo aquellas que se quieran 
#configurar con una Autenticación distinta a la de por defecto.

```


El siguiente paso es arrancar la solución.

### Otras BBDD

Esta aplicación también esta preparada para trabajar con MySQL y SQLServer. A continuación se muestra como sería la configuración con estas BBDD:

- SQL Server

```sh
#Configuracion SQLServer
db.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
db.url=jdbc:sqlserver://localhost:1433;databaseName=ciudadesAbiertas
db.user=ciudadesAbiertas
db.password=ciudadesAbiertas
db.initialSize=5
db.maxActive=10
db.maxIdle=10
db.minIdle=0
db.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
db.validationQuery=Select 1
```


- MySQL

```sh
#Configuracion Mysql
db.driver=com.mysql.jdbc.Driver
db.url=jdbc:mysql://127.0.0.1:3306/ciudadesAbiertas
db.user=ciudadesAbiertas
db.password=ciudadesAbiertas
db.initialSize=5
db.maxActive=10
db.maxIdle=10
db.minIdle=0
db.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
db.validationQuery=Select 1
```





### Arrancando

Para esto se debe ir a la pestaña "Servers" y pulsar botón derecho de ratón encima del servidor, se selecciona "Add and Remove":

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/running1.PNG "Arrancando la solución paso 1")

Debe aparecer a la izquieda el proyecto "org.ciudadesabiertas.web":

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/running2.PNG "Arrancando la solución paso 2")

Ahora se debe pasar a la derecha seleccionandolo y pulsando el botón "Add"

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/running3.PNG "Arrancando la solución paso 3")

Para acabar se pulsa el botón "Finish".

Y ahora solo se debe iniciar el servidor para arrancar el proyecto.

Para verificar que todo funciona correctamente podemos realizar acceder a la documentación de la API:

http://localhost:8080/OpenCitiesAPI


## Despliegue en producción

Para realizar un despliegue en producción se generará un war a partir del código del repositorio y se procedera a desplegarlo en un contenedor de servlets como Tomcat (8.5)

###Modularización de la API

Esta API soporta  diferentes conjuntos de datos. Cada conjunto de datos es un módulo independiente. Gracias a esto se puede generar la API con todos los conjuntos de datos, o sólo con los que interesen.

Estos son los conjuntos de datos que la API soporta actualmente:

Nombre del conjunto de datos | cadena a especificar en la generación del WAR
-- | --
Agenda cultural  | agenda
Alojamientos | alojamiento
Aparcamientos | aparcamiento
Avisos, quejas y sugerencias | avisoQuejaSugerencia
Búsqueda indexada | busquedaIndexada
Calidad del aire | calidadAire
Callejero | callejero
Equipamientos | equipamiento
Instalaciones deportivas | instalacionDeportiva
Puntos de interés turístico | interesTuristico
Locales comerciales | localComercial
Monumentos | monumento
Organigrama | organigrama
Puntos wifi | puntoWifi
Subvenciones | subvencion
Tramites | tramite


Para realizar el proceso de generación del WAR se deben lanzar los siguientes comandos:

- Si se quieren todos los conjuntos de datos:

```sh
cd ciudadesAbiertas\API_CA
mvn package
```

- Si se quiere un solo conjunto de datos:

```sh
cd ciudadesAbiertas\API_CA
mvn package -P subvencion
```

- Si se quieren varios conjuntos de datos:

```sh
cd ciudadesAbiertas\API_CA
mvn package -P subvencion,equipamiento
```

- Si no se quiere que se lancen los tests debemos añadir al final del comando: " -DskipTests"

```sh
cd ciudadesAbiertas\API_CA
mvn package -P subvencion,equipamiento -DskipTests
```


### Tests de integración
Durante la generación del desplegable, se lanzan diversos test para validar el correcto funcionamiento de la aplicación. Para que estos test sean satisfactorios es necesario que la configuración de la aplicación sea correcta. Para ello lo mejor es configurar el fichero: \API_CA\API_WEB\src\main\resources\config.properties tal y como se ha visto en la sección "Despliegue en desarrollo"

### Diferentes conexiones por cada conjunto de datos

En ocasiones se puede necesitar que ciertos conjuntos de datos no se encuentren en el mismo esquema de base de datos que los demás. Para conseguir esta funcionalidad se debe crear un fichero con el mismo nombre que el conjunto de datos con la extensión ".properties" en el mismo directorio donde se encuentra el fichero "config.properties", dentro de ese fichero sólo deben existir los parametros relacionados con la configuración de la BBDD:

Ejemplo: subvencion.properties

```sh
#Configuracion Oracle
db.driver=oracle.jdbc.OracleDriver
db.url=jdbc:oracle:thin:@127.0.0.1:1521:xe
db.user=subvencion
db.password=subvencion
db.initialSize=5
db.maxActive=10
db.maxIdle=10
db.minIdle=0
db.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
db.validationQuery=Select 1 from dual
´´´ 

###Generación del War

Si se quiere generar un desplegable (war) con la solución completa se lanzarán los siguientes comandos:

```sh
cd ciudadesAbiertas\API_CA
mvn clean compile package
```

En caso de no querer todos los conjuntos de datos, se pueden especificar cuales son los que se desean. Si se quisiera crear una aplicación que sólo tuviera el conjunto de datos "subvencion" estos son los comandos que se deben lanzar.

```sh
cd ciudadesAbiertas\API_CA
mvn clean compile package -P subvencionn
```

Se podrá acceder al WAR generado en el directorio: \API_CA\API_WEB\target


## Utilizando la API

En esta API hay dos tipos de operaciones: 

- las que se pueden llamar de manera anónima. Ej. listado de subvenciones
- las que requieren estar identificado para ser utilizada. Ej. alta de una subvención

### Operaciones anónimas

Para utilizar este tipo de operaciones sólo se tiene que saber la URL de dicha operación, configurar la URL utilizando sus parametros e invocarla.

Ejemplo: listado de subvenicones

Su URL es: <http://localhost:8080/OpenCitiesAPI/subvencion>

Se puede parametrizar para que nos devuelva todas las subvenciones con importe igual 1000 € de la siguiente forma:

<http://localhost:8080/OpenCitiesAPI/subvencion?importe=1000>

Podemos obtener la información en diferentes formatos:

- json: <http://localhost:8080/OpenCitiesAPI/subvencion.json?importe=1000>
- xml: <http://localhost:8080/OpenCitiesAPI/subvencion.xml?importe=1000>
- csv: <http://localhost:8080/OpenCitiesAPI/subvencion.csv?importe=1000>
- rdf: <http://localhost:8080/OpenCitiesAPI/subvencion.rdf?importe=1000>
- jsonld: <http://localhost:8080/OpenCitiesAPI/subvencion.jsonld?importe=1000>
- ttl: <http://localhost:8080/OpenCitiesAPI/subvencion.ttl?importe=1000>
- n3: <http://localhost:8080/OpenCitiesAPI/subvencion.n3?importe=1000>

Ahora para utilizarla se puede escribir en el navegador.

También se puede solicitar con el comando curl (y especificar su formato a través de la cabecera 'accept')

```sh
curl -X GET "http://localhost:8080/OpenCitiesAPI/subvencion?importe=1000" -H "accept: application/json"
```
 
### Operaciones que requieren identificación

Todas las operaciones de la API pueden ser llamadas de manera identificada.

La identificación se hace a través de un token. Para conseguir este token es necesario que identificarse a través de una URL utilizando las credenciales de un usuario y los datos de la aplicación.

Es necesario tener un usuario dentro de la API para poder realizar esta identificación.

También es necesario conocer los parametros "appname" y "appscret" de la API.

La URL para lo solicitar el token es la siguiente: <http://localhost:8080/OpenCitiesAPI/oauth/token>

Las peticiones deben ser via POST, en caso contrario devolverá un error 404.

Además se necesitan una serie de parámetros y cabeceras.

Los parámetros son los siguientes:

    - username: tu nombre de usuario. Ejemplo: neo.
    - password: la constraseña de tu usuario: whiteRabbit.
    - grant_type: la cadena 'password'.

Las cabeceras son las siguientes:

    - Accept: es el formato en el que quieres recibir el token: 'application/json' o 'application/xml'.
    - Authorization: aquí debes enviar la palabra 'Basic 'y codificado en base 64 la cadena de texto compuesta por el nombre de la aplicación, el caracter ':' y el secreto de la aplicación. Se pueden consultar estos datos más abajo, en la sección Datos de la Aplicación. 

Por ejemplo:

Si el nombre de la aplicación es 'api' y el secreto de la aplicación es '000', la cadena de texto que hay que codificar a base 64 es 'api:000'. El resultado de codificar 'api:000' es 'YXBpOjAwMA=='.

Un ejemplo de url sería el siguiente:

<http://localhost:8080/OpenCitiesAPI/oauth/token?username=neo&password=whiteRabbit&grant_type=password>

Con las cabeceras 'Accept: application/json' y 'Authorization: Basic YXBpOjAwMA=='

"curl" nos ahorra el tener que encodear a base 64 y añadir la cadena autorization si utilizamos -u appname:appsecret

Si se quisiera hacer una llamada utilizando curl este sería el comando:

```sh
curl -X POST -u api:000 --data "grant_type=password&username=neo&password=whiterabbit" http://localhost:8080/OpenCitiesAPI/oauth/token
```

Nota: esta llamada sólo es un ejemplo y no funciona.

El resultado obtenido es esta estructura: 

```json
{
  "access_token" : "b9581394-c09c-42c5-b7d7-3f5c60e045ee",
  "token_type" : "bearer",  
  "expires_in" : 3406,
  "scope" : "read write"
}
```

En ella se puede ver el token (propiedad access_token), así como el tiempo que nos queda hasta su caducidad en segundos (propiedad expires_in).

Cuando se tiene el token ya se puede llamar a cualquiera de las llamadas de la API. Solo hay que enviar el token a traves de la cabecera Authorization, añadiendole la cadena'Bearer ' al principio.

Ejemplo: 'Authorization: Bearer b9581394-c09c-42c5-b7d7-3f5c60e045ee'

Ahora que ya se tiene el token, se va a llamar a la misma URL del ejemplo anterior, pero esta vez de manera identificada: <http://localhost:8080/OpenCitiesAPI/subvencion?importe=1000> 


```sh
curl -X GET "http://localhost:8080/OpenCitiesAPI/subvencion?importe=1000 " -H "accept: application/json" -H  "Authorization: Bearer b9581394-c09c-42c5-b7d7-3f5c60e045ee"
```

## Documentación de la API

La documentación de la API esta disponible en la URL <http://localhost:8080/OpenCitiesAPI/swagger-ui.html>

Para realizar la autenticación y realizar pruebas debemos pulsar en el botón "Autorize"

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/swaggerAuth1.PNG "Botón autorizar en swagger")

Ahora solicita introducir el token:

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/swaggerAuth2.PNG "Solicitud de token").

Es el momento de generar un token (a través de 'curl', por ejemplo). Ahora que ya se tiene un token, debemos escribir "Bearer " y a continuación su valor:

 
![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/swaggerAuth3.PNG "Escribimos el token").

Se pulsa en el boton "autorize", y a continuación veremos la siguiente pantalla:

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/swaggerAuth4.PNG "Token configurado").

Se pulsa el botón close, y ya se puede probar cualquier llamada de la API.

Al pulsar "execute" se puede ver en la llamada realizada que la  autenticación se incluye:

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/swaggerAuth5.PNG "Llamada con token bearer").


## Parámetros en las operaciones

A continuación se detallan los parámetros que son posibles utilizar en cada  una de las operaciones de la API 

### Parámetros opcionales relativos a paginación y orden

- **page**: Es la página que se quiere consultar. La primera página se solicita con el número 1, o sin especificar. 	
	

- **pageSize**: El tamaño elementos por página
	
	
- **sort**: se utilizará cuando queramos ordenar por uno varios campos del conjunto de datos que se está utilizando. Podremos especificar orden descendente con el símbolo “-“, si sólo especificamos el nombre del campo, el orden será ascendente.
	Ejemplos:
	Orden por importe ascendente:  **sort=importe**
	Orden por importe descendente:  **sort=-importe**
	Orden por nombre ascendente e importe descendente  **sort=nombre,-importe**

### Parámetros opcionales para hacer búsquedas
Si se necesita realizar búsquedas, se podrá utilizar cualquier de los campos que tenga el conjunto de datos deseado.

Para subvenciones, estos son campos que hay disponibles:

**adjudicatarioId**
**areaNombre**
**idSubvencion**
**municipioNombre**
**adjudicatarioNombre**
**entidadFinanciadoraId**
**importe**
**nombre**
**aplicacionPresupuestaria**
**entidadFinanciadoraNombre**
**lineaFinanciacion**
**tipoInstrumento**
**areaId**
**fechaAdjudicacion**
**municipioId**
**url**

Utilizando cualquiera de estos campos se puede buscar fijando cualquier valor de estos campos.

Ejemplo: importe=11700

Se pueden concatenarlos mediante el símbolo “&”.

Ejemplo: importe=11700&areaId=A05003340

### Parámetro de Consultas RSQL

Para utilizar este tipo de consultas se tiene que indicar a través del parámetro **q**

En caso de que este parámetro tenga información se omitirán los datos de los campos del apartado anterior

A continuación se muestrán una serie de consultas a través de este parámetro:

q=areaNombre=="Salud" and importe>10000

q=importe<10000 and adjudicatarioId=in=(08029713e,21406767t)

### Parámetro para consultas agrupadas

 - **Parámetros obligatorios**:  deben de ir en cada petición.
		 
	 - [ ] **fields**: campo/s que se quiere mostrar con la operación de agregación seleccionada.

	- [ ] **group**: campo/s por los que agrupar. Es importante tener en cuenta que se debe agrupar por alguno de los campos que tenemos fijados en el campo anterior.
 
 - **Parámetros no obligatorios**:  deben de ir en cada petición.
 
	- [ ] **where**: condición que se quiere añadir a los campos que se incluyen en el parámetro fields.
	- [ ] **sort**: orden a aplicar.
	- [ ] **having**: condición global del group by.

## Configurar Autenticación de Peticiones
A continuación se describe brevemente el funcionamiento de estos parámetros, en la configuración principal no deben incluirse estos campos ya que solo afectan a la configuración particular de cada módulo:
**peticiones.identificadas.public_auth=**
**peticiones.identificadas.basic_auth=**
**peticiones.identificadas.admin_auth=**
Solo deben distribuirse en los ficheros de propiedades de cada módulo (**subvencion.properties, equipamiento.properties**, ...etc), y solo si se requiere dicha funcionalidad, si no se incluyen se aplica la configuración básica por defecto que seria la que se describe a continuación:
Auntentificación **Public** para peticiones de: Listado, Ficha, búsquedas Agrupadas y transformación de un recurso externo.
Auntentificación **Basic** para peticiones de: Alta, Baja y Modificación.

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores:
- **SUB_LIST**: Listado de Subvenciones
- **SUB_RECORD**: Ficha de Subvenciones
- **SUB_SEARCHGROUP**: Búsqueda de  agrupadas para Subvenciones
- **SUB_ADD**:  Alta de Subvenciones
- **SUB_UPDATE**: Baja de Subvenciones
- **SUB_DELETE**: Modificación de Subvenciones
- **SUB_TRANSFORM**: Transformador de una Subvención externa (a través de JSON)

Y los parámetros configurados quedarían de la siguiente manera en el fichero (**subvencion.properties**) Como Ejemplo:
- **peticiones.identificadas.public_auth=SUB_ADD,SUB_RECORD,SUB_LIST**
- **peticiones.identificadas.basic_auth= SUB_UPDATE,SUB_SEARCHGROUP**
- **peticiones.identificadas.admin_auth= SUB_DELETE**


**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.

## Encriptación de contraseña de BBDD.

Si se desea, se puede encriptar la contraseña de la BBDD  que se introduce en el config.properties

En el módulo de **API_CORE** se permite invocar a una clase que permite la encriptación de contraseñas asociadas a la aplicación, la forma correcta de utilizarla seria:

Dentro del directorio API_CORE (Y previa compilación del mismo: **mvn install**) ejecutar

```sh
mvn exec:java -Dexec.mainClass="org.ciudadesabiertas.utils.EncriptStringProperties"  -Dexec.args="TEXTOAENCRIPTAR"
```

El texto que aparece en consola será el siguiente:

```sh
This will encript all arguments
Now we have 1 arguments

Encripted:              B7KzbeM7EvFSULpAAT35bcujA+y8gSXC
Desencripted:   TEXTOAENCRIPTAR
For use in properties: ENC(B7KzbeM7EvFSULpAAT35bcujA+y8gSXC)
```

La forma correcta de usarlo será como se indica en el siguiente ejemplo para una contraseña del fichero de config.properties:

**db.password=ENC(B7KzbeM7EvFSULpAAT35bcujA+y8gSXC)**

## Datos Generales asociados a la API

- Se usa como comdin el ** * ** para las busquedas por contenido entre los campos de los conjuntos de datos.

## Activación JNDI

La Aplicación permite configurar la conexion a BBDD de la aplicación, y de los módulos a traves de DataSources. A continuación describimos los pasos para realizar esta configuración:

- Fichero de aplicación: **config.properties**

```sh
#JNDI SERVER
#Nombre definido para el datasource.
db.jndi.name=jdbc/API_CA

#BBDD Configuration
#Nombre del driver de la BBDD.
db.driver=oracle.jdbc.OracleDriver
#Nombre del dialect de la BBDD.
db.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
#Nombre del env para establecer la conexión con el DataSource
db.jndi.env=java:comp/env/

#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=false
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=true
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=true
```

- Ejemplo de configuración de un modulo de la aplicación (Agenda): **agenda.properties**

```sh
#JNDI SERVER
#Nombre definido para el datasource.
db.jndi.name=jdbc/agenda

#BBDD Configuration
#Nombre del driver de la BBDD.
db.driver=oracle.jdbc.OracleDriver
#Nombre del dialect de la BBDD.
db.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
#Nombre del env para establecer la conexión con el DataSource
db.jndi.env=java:comp/env/

#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=false
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=true
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=true
```

- En el modulo web de la aplicación dentro de la carpeta resources se ha incluido el fichero context.xml (Carpeta META-INF): 

Este fichero tiene comentado el código para crear un acceso via JNDI en un entorno TOMCAT, ejemplo:

```sh
<Resource name="jdbc/agenda"
   	   factory="org.ciudadesabiertas.factory.tomcat.EncryptedDataSourceFactory"
       auth="Container"
	   type="javax.sql.DataSource"
	   driverClassName="oracle.jdbc.OracleDriver"
       url="jdbc:oracle:thin:@127.0.0.1:1521:xe"
	   username="ENC(1wiazHWG67Uq8ObFSNBXGTUaIK5MgZKNBGzCSmN8FK0=)"
	   password="ENC(1wiazHWG67Uq8ObFSNBXGTUaIK5MgZKNBGzCSmN8FK0=)"
	   initialSize="5"
	   maxActive="20"
	   minIdle="0"
	   maxIdle="5"
	   maxWait="30000"
	   validationQuery="SELECT 1 FROM DUAL"
	   testWhileIdle="true"
	   timeBetweenEvictionRunsMillis="60000"
	   numTestsPerEvictionRun="5"
	   minEvictableIdleTimeMillis="90000"
	   testOnBorrow="false"
	   testOnReturn="false"
	/>
	<Resource name="jdbc/API_CA"
   	   factory="org.ciudadesabiertas.factory.tomcat.EncryptedDataSourceFactory"
       auth="Container"
	   type="javax.sql.DataSource"
	   driverClassName="oracle.jdbc.OracleDriver"
       url="jdbc:oracle:thin:@127.0.0.1:1521:xe"
	   username="ENC(1wiazHWG67Uq8ObFSNBXGTUaIK5MgZKNBGzCSmN8FK0=)"
	   password="ENC(1wiazHWG67Uq8ObFSNBXGTUaIK5MgZKNBGzCSmN8FK0=)"
	   initialSize="5"
	   maxActive="20"
	   minIdle="0"
	   maxIdle="5"
	   maxWait="30000"
	   validationQuery="SELECT 1 FROM DUAL"
	   testWhileIdle="true"
	   timeBetweenEvictionRunsMillis="60000"
	   numTestsPerEvictionRun="5"
	   minEvictableIdleTimeMillis="90000"
	   testOnBorrow="false"
	   testOnReturn="false"
	/>
```

- Tambien se ha desarrollado un factory: **org.ciudadesabiertas.factory.tomcat.EncryptedDataSourceFactory.java**. Que permite encriptar las contraseñas asociadas a estos servicios.


## Personalización

A continuación se describe los componentes de la API REST Ciudades Abiertas que son factible de personalizar por cada instalación.

**- Documentación Swagger**

La documentación de swagger se ha externalizado dentro del directorio **..\webapp\swagger\** haciendo posible la personalización de esta, realizando modificaciones en los ficheros asociados a esta carpeta.

**- Pantalla de error para las plantillas html**

La aplicación dispone de una plantilla básica para mostrar errores en formato html, que se puede personalizar por cada instalación. Esta plantilla se encuentra disponible en la siguiente ruta: **\webapp\WEB-INF\templates**
nombre del fichero: **error.html**

**- Plantillas html**

La aplicación dispone de unas plantillas básicas que se utilizan para cada uno de los módulos que integra la solución y que hace que se puedan mostrar información en formato HTML, cuando así se requiera en la petición asociada a un servicio del módulo que corresponda y siempre que este servicio disponga de respuesta en este formato. Estas plantillas se encuentran disponibles en la siguiente ruta: **\webapp\WEB-INF\templates**

**Nota**: Como anteriormente se describió en los parámetros de configuración la API REST Ciudades Abiertas,  esta permite la externalización de estas plantillas mediante la configuración del parámetro **path.template.html** 



# Autores
 - Juan Carlos Ballesteros (Localidata)
 - Carlos Martínez de la Casa (Localidata)
 - Oscar Corcho (UPM, Localidata)


# Licencia
Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es

This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).

Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
You may not use this work except in compliance with the Licence.
You may obtain a copy of the Licence at:

https://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the Licence for the specific language governing permissions and limitations under the Licence.