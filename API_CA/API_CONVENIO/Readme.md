

# API CIUDADES ABIERTAS - MÓDULO CONVENIOS

Este módulo integra el vocabulario de contratos para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	Convenio
- 	ConvenioDocumento
-	ConvenioOrganization
-	ConvenioSuscEntidad
-	ConvRelFirmanteAyto
-	ConvRelFirmanteEntidad



## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **convenio.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **convenios** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **convenio.properties**.

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

### Módulo Convenio

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Convenio_LIST**: Listado de Convenios
-   **Convenio_RECORD**: Ficha de un Convenio
-   **Convenio_ADD**: Alta de un local Convenio
-   **Convenio_UPDATE**: Modificación de un Convenio
-   **Convenio_DELETE**: Baja de un Convenio
-   **Convenio_TRANSFORM**: Transformador de un Convenio externo (a través de JSON)

### Módulo ConvenioDocumento

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ConvenioDocumento_LIST**: Listado de ConvenioDocumento
-   **ConvenioDocumento_RECORD**: Ficha de un ConvenioDocumento
-   **ConvenioDocumento_ADD**: Alta de un local ConvenioDocumento
-   **ConvenioDocumento_UPDATE**: Modificación de un ConvenioDocumento
-   **ConvenioDocumento_DELETE**: Baja de un ConvenioDocumento
-   **ConvenioDocumento_TRANSFORM**: Transformador de un ConvenioDocumento externo (a través de JSON)

### Módulo ConvenioOrganization

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Convenio_Organization_LIST**: Listado de ConvenioOrganization
-   **Convenio_Organization_RECORD**: Ficha de un ConvenioOrganization
-   **Convenio_Organization_ADD**: Alta de un local ConvenioOrganization
-   **Convenio_Organization_UPDATE**: Modificación de un ConvenioOrganization
-   **Convenio_Organization_DELETE**: Baja de un ConvenioOrganization
-   **Convenio_Organization_TRANSFORM**: Transformador de un ConvenioOrganization externo (a través de JSON)

### Módulo ConvenioSuscEntidad

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ConvenioSuscEntidad_LIST**: Listado de ConvenioSuscEntidad
-   **ConvenioSuscEntidad_RECORD**: Ficha de un ConvenioSuscEntidad
-   **ConvenioSuscEntidad_ADD**: Alta de un local ConvenioSuscEntidad
-   **ConvenioSuscEntidad_UPDATE**: Modificación de un ConvenioSuscEntidad
-   **ConvenioSuscEntidad_DELETE**: Baja de un ConvenioSuscEntidad
-   **ConvenioSuscEntidad_TRANSFORM**: Transformador de un ConvenioSuscEntidad externo (a través de JSON)


### Módulo ConvRelFirmanteAyuntamiento

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ConvRelFirmanteAyuntamiento_LIST**: Listado de ConvRelFirmanteAyuntamiento
-   **ConvRelFirmanteAyuntamiento_RECORD**: Ficha de un ConvRelFirmanteAyuntamiento
-   **ConvRelFirmanteAyuntamiento_ADD**: Alta de un local ConvRelFirmanteAyuntamiento
-   **ConvRelFirmanteAyuntamiento_UPDATE**: Modificación de un ConvRelFirmanteAyuntamiento
-   **ConvRelFirmanteAyuntamiento_DELETE**: Baja de un ConvRelFirmanteAyuntamiento
-   **ConvRelFirmanteAyuntamiento_TRANSFORM**: Transformador de un ConvRelFirmanteAyuntamiento externo (a través de JSON)


### Módulo ConvRelFirmanteEntidad

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ConvRelFirmanteEntidad_LIST**: Listado de ConvRelFirmanteEntidad
-   **ConvRelFirmanteEntidad_RECORD**: Ficha de un ConvRelFirmanteEntidad
-   **ConvRelFirmanteEntidad_ADD**: Alta de un local ConvRelFirmanteEntidad
-   **ConvRelFirmanteEntidad_UPDATE**: Modificación de un ConvRelFirmanteEntidad
-   **ConvRelFirmanteEntidad_DELETE**: Baja de un ConvRelFirmanteEntidad
-   **ConvRelFirmanteEntidad_TRANSFORM**: Transformador de un ConvRelFirmanteEntidad externo (a través de JSON)



```sh
peticiones.identificadas.public_auth=Convenio_LIST,Convenio_RECORD,ConvenioDocumento_LIST,ConvenioDocumento_RECORD,ConvenioSuscEntidad_LIST,ConvenioSuscEntidad_RECORD,ConvenioSuscEntidad_LIST,ConvenioSuscEntidad_RECORD,Convenio_Organization_LIST,Convenio_Organization_RECORD,ConvRelFirmanteAyuntamiento_LIST,ConvRelFirmanteEntidad_RECORD,ConvRelFirmanteAyuntamiento_LIST,ConvRelFirmanteAyuntamiento_RECORD,ConvRelFirmanteAyuntamiento_LIST,ConvRelFirmanteAyuntamiento_RECORD,ConvRelFirmanteAyuntamiento_ADD,ConvRelFirmanteAyuntamiento_ADD,ConvRelFirmanteEntidad_ADD,Convenio_Organization_ADD,ConvenioSuscEntidad_ADD,ConvenioSuscEntidad_ADD,ConvenioDocumento_ADD,Convenio_ADD
peticiones.identificadas.basic_auth= Convenio_UPDATE,Convenio_TRANSFORM,ConvenioDocumento_UPDATE,ConvenioDocumento_TRANSFORM,ConvenioSuscEntidad_UPDATE,ConvenioSuscEntidad_TRANSFORM,ConvenioSuscEntidad_UPDATE,ConvenioSuscEntidadRelItet_TRANSFORM,Convenio_Organization_UPDATE,Convenio_Organization_TRANSFORM,ConvRelFirmanteEntidad_UPDATE,ConvRelFirmanteEntidad_TRANSFORM,ConvRelFirmanteAyuntamiento_UPDATE,ConvRelFirmanteAyuntamiento_TRANSFORM,ConvRelFirmanteAyuntamiento_UPDATE,ConvRelFirmanteAyuntamientoRelIter_TRANSFORM
peticiones.identificadas.admin_auth= Convenio_DELETE,ConvenioDocumento_DELETE,s.public_auth=Convenio_LIST,Convenio_RECORD,ConvenioDocumento_LIST,ConvenioSuscEntidad_DELETE,ConvenioSuscEntidad_DELETE,Convenio_Organization_DELETE,ConvRelFirmanteEntidad_DELETE,ConvRelFirmanteAyuntamiento_DELETEItem_DELETE
```

**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.



# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)