
# API CIUDADES ABIERTAS - MÓDULO CONTAMINACIÓN ACUSTICA

Esta es la documentación asociada al módulo de Contaminación acústica.

Contenido:
   
     - Descripción
     - Creación de un módulo a partir de este

## Descripción

Este módulo integra el vocabulario de contratos para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	Estación
-	Observación
-   Propiedad

## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **contaminacionacustica.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **contaminacionacustica** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **contaminacionacustica.properties**.

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


### Módulo Estación

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ContaminacionAcusticaEstacion_LIST**: Listado de estaciones
-   **ContaminacionAcusticaEstacion_RECORD**: Ficha de una estación
-   **ContaminacionAcusticaEstacion_ADD**: Alta de una estación
-   **ContaminacionAcusticaEstacion_UPDATE**: Modificación de una estación
-   **ContaminacionAcusticaEstacion_DELETE**: Baja de una estación
-   **ContaminacionAcusticaEstacion_TRANSFORM**: Transformador de una estación externo (a través de JSON)

### Módulo Observación

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ContAcusticaObservacionController_LIST**: Listado de observaciones
-   **ContAcusticaObservacionController_RECORD**: Ficha de una observación
-   **ContAcusticaObservacionController_ADD**: Alta de una observación
-   **ContAcusticaObservacionController_UPDATE**: Modificación de una observación
-   **ContAcusticaObservacionController_DELETE**: Baja de una observación
-   **ContAcusticaObservacionController_TRANSFORM**: Transformador de una observación externa (a través de JSON)

### Módulo Propiedad

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ContaminacionAcusticaPropiedad_LIST**: Listado de propiedades
-   **ContaminacionAcusticaPropiedad_RECORD**: Ficha de una propiedad
-   **ContaminacionAcusticaPropiedad_ADD**: Alta de una propiedad
-   **ContaminacionAcusticaPropiedad_UPDATE**: Modificación de una propiedad
-   **ContaminacionAcusticaPropiedad_DELETE**: Baja de una propiedad
-   **ContaminacionAcusticaPropiedad_TRANSFORM**: Transformador de una propiedad externa (a través de JSON)

```sh
peticiones.identificadas.public_auth=ContaminacionAcusticaEstacion_LIST,ContaminacionAcusticaEstacion_ADD,ContAcusticaObservacionController_LIST,ContAcusticaObservacionController_ADD,ContaminacionAcusticaPropiedad_LIST,ContaminacionAcusticaPropiedad_ADD
peticiones.identificadas.basic_auth=ContaminacionAcusticaEstacion_UPDATE,ContaminacionAcusticaEstacion_TRANSFORM,ContAcusticaObservacionController_UPDATE,ContAcusticaObservacionController_TRANSFORM,ContaminacionAcusticaPropiedad_UPDATE,ContaminacionAcusticaPropiedad_TRANSFORM
peticiones.identificadas.admin_auth=ContaminacionAcusticaEstacion_DELETE,ContAcusticaObservacionController_DELETE,ContaminacionAcusticaPropiedad_DELETE
```

**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.



# Autores
- Juan Carlos Ballesteros (Localidata)
- Carlos Martínez de la Casa (Localidata)
- Hugo Lafuente (Localidata)
- Oscar Corcho (UPM, Localidata)