

# API CIUDADES ABIERTAS - MÓDULO CONTRATOS

Este módulo integra el vocabulario de contratos para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	Award
-	Item
-	Lot
-	LotRelItem
-	Organization
- 	Process
- 	Tender
- 	TenderRelItem



## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **contratos.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **contratos** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **contratos.properties**.

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

### Módulo Award

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Contract_Award_LIST**: Listado de award
-   **Contract_Award_RECORD**: Ficha de un award
-   **Contract_Award_ADD**: Alta de un local award
-   **Contract_Award_UPDATE**: Modificación de un award
-   **Contract_Award_DELETE**: Baja de un award
-   **Contract_Award_TRANSFORM**: Transformador de un award externo (a través de JSON)

### Módulo Item

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Contract_Item_LIST**: Listado de Item
-   **Contract_Item_RECORD**: Ficha de un Item
-   **Contract_Item_ADD**: Alta de un local Item
-   **Contract_Item_UPDATE**: Modificación de un Item
-   **Contract_Item_DELETE**: Baja de un Item
-   **Contract_Item_TRANSFORM**: Transformador de un Item externo (a través de JSON)

### Módulo Lot

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Contract_Lot_LIST**: Listado de Lot
-   **Contract_Lot_RECORD**: Ficha de un Lot
-   **Contract_Lot_ADD**: Alta de un local Lot
-   **Contract_Lot_UPDATE**: Modificación de un Lot
-   **Contract_Lot_DELETE**: Baja de un Lot
-   **Contract_Lot_TRANSFORM**: Transformador de un Lot externo (a través de JSON)

### Módulo LotRelItem

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Contract_LotRelItem_LIST**: Listado de LotRelItem
-   **Contract_LotRelItem_RECORD**: Ficha de un LotRelItem
-   **Contract_LotRelItem_ADD**: Alta de un local LotRelItem
-   **Contract_LotRelItem_UPDATE**: Modificación de un LotRelItem
-   **Contract_LotRelItem_DELETE**: Baja de un LotRelItem
-   **Contract_LotRelItem_TRANSFORM**: Transformador de un LotRelItem externo (a través de JSON)

### Módulo Organization

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Contract_Organization_LIST**: Listado de Organization
-   **Contract_Organization_RECORD**: Ficha de un Organization
-   **Contract_Organization_ADD**: Alta de un local Organization
-   **Contract_Organization_UPDATE**: Modificación de un Organization
-   **Contract_Organization_DELETE**: Baja de un Organization
-   **Contract_Organization_TRANSFORM**: Transformador de un Organization externo (a través de JSON)

### Módulo Process

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Contract_Process_LIST**: Listado de Process
-   **Contract_Process_RECORD**: Ficha de un Process
-   **Contract_Process_ADD**: Alta de un local Process
-   **Contract_Process_UPDATE**: Modificación de un Process
-   **Contract_Process_DELETE**: Baja de un Process
-   **Contract_Process_TRANSFORM**: Transformador de un Process externo (a través de JSON)


### Módulo Tender

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Contract_Tender_LIST**: Listado de Tender
-   **Contract_Tender_RECORD**: Ficha de un Tender
-   **Contract_Tender_ADD**: Alta de un local Tender
-   **Contract_Tender_UPDATE**: Modificación de un Tender
-   **Contract_Tender_DELETE**: Baja de un Tender
-   **Contract_Tender_TRANSFORM**: Transformador de un Tender externo (a través de JSON)


### Módulo TenderRelItem

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Contract_TenderRelItem_LIST**: Listado de TenderRelItem
-   **Contract_TenderRelItem_RECORD**: Ficha de un TenderRelItem
-   **Contract_TenderRelItem_ADD**: Alta de un local TenderRelItem
-   **Contract_TenderRelItem_UPDATE**: Modificación de un TenderRelItem
-   **Contract_TenderRelItem_DELETE**: Baja de un TenderRelItem
-   **Contract_TenderRelItem_TRANSFORM**: Transformador de un TenderRelItem externo (a través de JSON)

```sh
peticiones.identificadas.public_auth=Contract_Award_LIST,Contract_Award_RECORD,Contract_Item_LIST,Contract_Item_RECORD,Contract_Lot_LIST,Contract_Lot_RECORD,Contract_LotRelItem_LIST,Contract_LotRelItem_RECORD,Contract_Organization_LIST,Contract_Organization_RECORD,Contract_Process_LIST,Contract_Process_RECORD,Contract_Tender_LIST,Contract_Tender_RECORD,Contract_TenderRelItem_LIST,Contract_TenderRelItem_RECORD,Contract_TenderRelItem_ADD,Contract_Tender_ADD,Contract_Process_ADD,Contract_Organization_ADD,Contract_LotRelItem_ADD,Contract_Lot_ADD,Contract_Item_ADD,Contract_Award_ADD
peticiones.identificadas.basic_auth= Contract_Award_UPDATE,Contract_Award_TRANSFORM,Contract_Item_UPDATE,Contract_Item_TRANSFORM,Contract_Lot_UPDATE,Contract_Lot_TRANSFORM,Contract_LotRelItem_UPDATE,Contract_LotRelItet_TRANSFORM,Contract_Organization_UPDATE,Contract_Organization_TRANSFORM,Contract_Process_UPDATE,Contract_Process_TRANSFORM,Contract_Tender_UPDATE,Contract_Tender_TRANSFORM,Contract_TenderRelItem_UPDATE,Contract_TenderRelIter_TRANSFORM
peticiones.identificadas.admin_auth= Contract_Award_DELETE,Contract_Item_DELETE,s.public_auth=Contract_Award_LIST,Contract_Award_RECORD,Contract_Item_LIST,Contract_Lot_DELETE,Contract_LotRelItem_DELETE,Contract_Organization_DELETE,Contract_Process_DELETE,Contract_Tender_DELETE,Contract_TenderRelItem_DELETE
```

**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.



# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)