
# API CIUDADES ABIERTAS - MÓDULO AGENDA MUNICIPAL

Este módulo integra el vocabulario de Agenda Municipal para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	AgendaMDocumento
-	AgendaMEvento
-	AgendaMRolEvento


## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **agendaMunicipal.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **Agenda Municipal** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **agendaMunicipal.properties**.
	
```sh
#Configuracion BBDD
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

#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=

#Control para el listado de peticiones 
peticiones.identificadas.public_auth=
peticiones.identificadas.basic_auth=
peticiones.identificadas.admin_auth= 

```

### Configuración BBDD

- Ejemplo configuración BBDD SQLServer

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

#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=false
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=true
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=true
```


- Ejemplo configuración BBDD MySQL

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

#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=false
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=true
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=true
```


- Ejemplo configuración BBDD Oracle

```sh
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
```


### Configurar Seguridad de las Operaciones (Servicios disponibles de la API)
A continuación describimos brevemente el funcionamiento de estos parámetros
- **peticiones.identificadas.public_auth=**  Parametro para incluir las peticiones Públicas sin identificación
- **peticiones.identificadas.basic_auth=** Parámetro para incluir las peticiones con identificación de usuario con rol básico
- **peticiones.identificadas.admin_auth=**
Parámetro para incluir las peticiones con identificación de usuario con rol administrador. 

Si no se incluyen todos o algunos de estos parámetros se aplica la configuración básica por defecto que seria la que se describe a continuación:
- Auntentificación **Public** para peticiones de: Listado, Ficha, búsquedas Agrupadas y transformación de un recurso externo.
- Auntentificación **Basic** para peticiones de: Alta, Baja y Modificación.


Readme

### Módulo Agenda Municipal Documento

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **AgendaMDocumento_LIST**: Listado de Agenda Municipal Documento
-   **AgendaMDocumento_RECORD**: Ficha de un Agenda Municipal Documento
-   **AgendaMDocumento_ADD**: Alta de un local Agenda Municipal Documento
-   **AgendaMDocumento_UPDATE**: Modificación de un Agenda Municipal Documento
-   **AgendaMDocumento_DELETE**: Baja de un Agenda Municipal Documento
-   **AgendaMDocumento_TRANSFORM**: Transformador de un Agenda Municipal Documento externo (a través de JSON)

### Módulo Agenda Municipal Evento

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **AgendaMEvento_LIST**: Listado de Agenda Municipal Evento
-   **AgendaMEvento_RECORD**: Ficha de un Agenda Municipal Evento
-   **AgendaMEvento_ADD**: Alta de un local Agenda Municipal Evento
-   **AgendaMEvento_UPDATE**: Modificación de un Agenda Municipal Evento
-   **AgendaMEvento_DELETE**: Baja de un Agenda Municipal Evento
-   **AgendaMEvento_TRANSFORM**: Transformador de un Agenda Municipal Evento externo (a través de JSON)

### Módulo Agenda Municipal Rol Evento

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **AgendaMRolEvento_LIST**: Listado de Agenda Municipal Rol Evento
-   **AgendaMRolEvento_RECORD**: Ficha de un Agenda Municipal Rol Evento
-   **AgendaMRolEvento_ADD**: Alta de un local Agenda Municipal Rol Evento
-   **AgendaMRolEvento_UPDATE**: Modificación de un Agenda Municipal Rol Evento
-   **AgendaMRolEvento_DELETE**: Baja de un Agenda Municipal Rol Evento
-   **AgendaMRolEvento_TRANSFORM**: Transformador de un Agenda Municipal Rol Evento externo (a través de JSON)

```sh
peticiones.identificadas.public_auth=AgendaMDocumento_LIST,AgendaMEvento_LIST,AgendaMRolEvento_LIST,AgendaMDocumento_RECORD,AgendaMEvento_RECORD,AgendaMRolEvento_RECORD,AgendaMDocumento_ADD,AgendaMEvento_ADD,AgendaMRolEvento_ADD
peticiones.identificadas.basic_auth=AgendaMDocumento_UPDATE,AgendaMEvento_UPDATE,AgendaMRolEvento_UPDATE,AgendaMDocumento_TRANSFORM,AgendaMEvento_TRANSFORM,AgendaMRolEvento_TRANSFORM
peticiones.identificadas.admin_auth=AgendaMDocumento_DELETE,AgendaMEvento_DELETE,AgendaMRolEvento_DELETE
```

# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)
