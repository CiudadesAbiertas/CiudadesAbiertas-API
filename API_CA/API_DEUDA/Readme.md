
# API CIUDADES ABIERTAS - MÓDULO DEUDA COMERCIAL

Este módulo integra el vocabulario de deuda comercial para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	DeudaComercialOrganization
-	DeudaComercialInformeMorosidadTrimestral
-	DeudaComercialInformePMPMensual
-	DeudaComercialInformePMPMensualGlobal
- 	DeudaComercialProperInterval




## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **deudaComercial.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **subvenciones** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **deudaComercial.properties**.


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

#Configuracion Properties Hibernates
#posibles valores true o false activa que se muestre en el log de salida todas las sentencias de hibernate que se ejecutan en la aplicación.
hibernate.show_sql=false
#posibles valores true o false cuando esta activo el log de hibernate las sentencias de SQL se les da formato para que puedan verse en mas de una unica linea de log.
hibernate.format_sql=true
#posibles valores true o false permite que se puedan añadir comentarios a las sentencias de SQL mediante programación
hibernate.use_sql_comments=true
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

### Módulo Deuda Comercial Organization

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Deuda Comercial:
- **DeudaComercialOrganization_LIST**: Listado de Organizations
- **DeudaComercialOrganization_RECORD**: Ficha de Organization
- **DeudaComercialOrganization_SEARCHGROUP**: Búsqueda de agrupadas para Organization
- **DeudaComercialOrganization_ADD**:  Alta de Organization
- **DeudaComercialOrganization_UPDATE**: Modificación de Organization
- **DeudaComercialOrganization_DELETE**: Baja de Organization
- **DeudaComercialOrganization_TRANSFORM**: Transformador de una Organization externa (a través de JSON)

### Módulo Deuda Comercial Informe Morosidad Trimestral

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Deuda Comercial:
- **DeudaComercialInformeMorosidadTrimestral_Beneficiario_LIST**: Listado de Deuda Comercial Informe Morosidad Trimestral Benficiarios
- **DeudaComercialInformeMorosidadTrimestral_Beneficiario_RECORD**: Ficha de Deuda Comercial Informe Morosidad Trimestral Benficiario
- **DeudaComercialInformeMorosidadTrimestral_Beneficiario_SEARCHGROUP**: Búsqueda de agrupadas para Deuda Comercial Informe Morosidad Trimestral
- **DeudaComercialInformeMorosidadTrimestral_Beneficiario_ADD**:  Alta de Deuda Comercial Informe Morosidad Trimestral
- **DeudaComercialInformeMorosidadTrimestral_Beneficiario_UPDATE**: Modificación de Deuda Comercial Informe Morosidad Trimestral
- **DeudaComercialInformeMorosidadTrimestral_Beneficiario_DELETE**: Baja de Deuda Comercial Informe Morosidad Trimestral
- **DeudaComercialInformeMorosidadTrimestral_Beneficiario_TRANSFORM**: Transformador de una Deuda Comercial Informe Morosidad Trimestral externa (a través de JSON)

### Módulo Deuda Comercial Informe PMP Mensual

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Deuda Comercial:
- **DeudaComercialInformePMPMensual_LIST**: Listado de Deuda Comercial Informe PMP Mensual 
- **DeudaComercialInformePMPMensual_RECORD**: Ficha de Deuda Comercial Informe PMP Mensual
- **DeudaComercialInformePMPMensual_SEARCHGROUP**: Búsqueda de agrupadas para Deuda Comercial Informe PMP Mensual
- **DeudaComercialInformePMPMensual_ADD**:  Alta de Deuda Comercial Informe PMP Mensual 
- **DeudaComercialInformePMPMensual_UPDATE**: Modificación de Deuda Comercial Informe PMP Mensual
- **DeudaComercialInformePMPMensual_DELETE**: Baja de Deuda Comercial Informe PMP Mensual 
- **DeudaComercialInformePMPMensual_TRANSFORM**: Transformador de una Deuda Comercial Informe PMP Mensual  externa (a través de JSON)

### Módulo Deuda Comercial Informe PMP Mensual Global

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Deuda Comercial:
- **DeudaComercialInformePMPMensualGlobal_LIST**: Listado de Deuda Comercial Informe PMP Mensual Global
- **DeudaComercialInformePMPMensualGlobal_RECORD**: Ficha de Deuda Comercial Informe PMP Mensual Global
- **DeudaComercialInformePMPMensualGlobal_SEARCHGROUP**: Búsqueda de agrupadas para Deuda Comercial Informe PMP Mensual Global
- **DeudaComercialInformePMPMensualGlobal_ADD**:  Alta de Deuda Comercial Informe PMP Mensual Global
- **DeudaComercialInformePMPMensualGlobal_UPDATE**: Modificación de Deuda Comercial Informe PMP Mensual Global
- **DeudaComercialInformePMPMensualGlobal_DELETE**: Baja de Deuda Comercial Informe PMP Mensual Global
- **DeudaComercialInformePMPMensualGlobal_TRANSFORM**: Transformador de una Deuda Comercial Informe PMP Mensual Global externa (a través de JSON)

### Módulo Deuda Comercial Proper Interval

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Deuda Comercial:
- **DeudaComercialProperInterval_LIST**: Listado de Deuda Comercial Proper Interval
- **DeudaComercialProperInterval_RECORD**: Ficha de Deuda Comercial Proper Interval
- **DeudaComercialProperInterval_SEARCHGROUP**: Búsqueda de agrupadas para Deuda Comercial Proper Interval
- **DeudaComercialProperInterval_ADD**:  Alta de Deuda Comercial Proper Interval
- **DeudaComercialProperInterval_UPDATE**: Modificación de Deuda Comercial Proper Interval
- **DeudaComercialProperInterval_DELETE**: Baja de Deuda Comercial Proper Interval
- **DeudaComercialProperInterval_TRANSFORM**: Transformador de una Deuda Comercial Proper Interval externa (a través de JSON)

Y los parámetros configurados quedarían de la siguiente manera en el fichero (**deudaComercial.properties**) Como Ejemplo:
- **peticiones.identificadas.public_auth=DeudaComercialInformePMPMensualGlobal_RECORD,DeudaComercialInformePMPMensualGlobal_LIST,DeudaComercialProperInterval_RECORD,DeudaComercialProperInterval_LIST,DeudaComercialOrganization_RECORD,DeudaComercialOrganization_LIST**
- **peticiones.identificadas.basic_auth=DeudaComercialInformePMPMensualGlobal_UPDATE,DeudaComercialInformePMPMensualGlobal_SEARCHGROUP,DeudaComercialInformePMPMensualGlobal_TRANSFORM,DeudaComercialProperInterval_UPDATE,DeudaComercialProperInterval_SEARCHGROUP,DeudaComercialProperInterval_TRANSFORM**
- **peticiones.identificadas.admin_auth= DeudaComercialProperInterval_DELETE,DeudaComercialInformePMPMensualGlobal_DELETE,DeudaComercialInformePMPMensual_DELETE**


```sh
peticiones.identificadas.public_auth=Subvencion_ADD,Subvencion_RECORD,Subvencion_LIST
peticiones.identificadas.basic_auth= Subvencion_UPDATE,Subvencion_SEARCHGROUP,Subvencion_TRANSFORM
peticiones.identificadas.admin_auth= Subvencion_DELETE
```

**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.



# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)
