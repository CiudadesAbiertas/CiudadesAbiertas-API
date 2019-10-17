
# API CIUDADES ABIERTAS - MÓDULO CALLEJERO

Esta es la documentación asociada al módulo **callejero**.

Contenido:
   
     - Configuración del módulo 



## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **callejero.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **callejero** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **callejero.properties**.


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

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Vía:
- **VIA_LIST**: Listado de vías
- **VIA_RECORD**: Ficha de vía
- **VIA_ADD**:  Alta de vía
- **VIA_UPDATE**: Modificación de vía
- **VIA_DELETE**: Baja de vía
- **VIA_TRANSFORM**: Transformador de una vía externa (a través de JSON)

Y los parámetros configurados quedarían de la siguiente manera en el fichero (**callejero.properties**) Como Ejemplo:
- **peticiones.identificadas.public_auth=VIA_ADD,VIA_RECORD,VIA_LIST**
- **peticiones.identificadas.basic_auth= VIA_UPDATE,VIA_TRANSFORM**
- **peticiones.identificadas.admin_auth= VIA_DELETE**

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Tramo:
- **TRAMO_LIST**: Listado de Tramos
- **TRAMO_RECORD**: Ficha de Tramo
- **TRAMO_ADD**:  Alta de Tramo
- **TRAMO_UPDATE**: Modificación de Tramo
- **TRAMO_DELETE**: Baja de Tramo
- **TRAMO_TRANSFORM**: Transformador de una Tramo externa (a través de JSON)

Y los parámetros configurados quedarían de la siguiente manera en el fichero (**callejero.properties**) Como Ejemplo:
- **peticiones.identificadas.public_auth=TRAMO_ADD,TRAMO_RECORD,TRAMO_LIST**
- **peticiones.identificadas.basic_auth=TRAMO_UPDATE,TRAMO_TRANSFORM**
- **peticiones.identificadas.admin_auth=TRAMO_DELETE**

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Portal:
- **PORTAL_LIST**: Listado de Portales
- **PORTAL_RECORD**: Ficha de Portal
- **PORTAL_ADD**:  Alta de Portal
- **PORTAL_UPDATE**: Modificación de Portal
- **PORTAL_DELETE**: Baja de Portal
- **PORTAL_TRANSFORM**: Transformador de un Portal externo (a través de JSON)

Y los parámetros configurados quedarían de la siguiente manera en el fichero (**callejero.properties**) Como Ejemplo:
- **peticiones.identificadas.public_auth=PORTAL_ADD,PORTAL_RECORD,PORTAL_LIST**
- **peticiones.identificadas.basic_auth=PORTAL_UPDATE,PORTAL_TRANSFORM**
- **peticiones.identificadas.admin_auth=PORTAL_DELETE**





```sh
peticiones.identificadas.public_auth=VIA_ADD,VIA_RECORD,VIA_LIST,TRAMO_ADD,TRAMO_RECORD,TRAMO_LIST,PORTAL_ADD,PORTAL_RECORD,PORTAL_LIST
peticiones.identificadas.basic_auth=VIA_UPDATE,VIA_TRANSFORM,TRAMO_UPDATE,TRAMO_TRANSFORM,PORTAL_UPDATE,PORTAL_TRANSFORM
peticiones.identificadas.admin_auth=VIA_DELETE,TRAMO_DELETE,PORTAL_DELETE
```

**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.



# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)