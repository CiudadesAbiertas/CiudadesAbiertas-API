
# API CIUDADES ABIERTAS - MÓDULO SUBVENCIÓN

Este módulo integra el vocabulario de subvención para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	Subvencion
-	SubvencionBeneficiario
-	SubvencionOrganization




## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **subvencion.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **subvenciones** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **subvecion.properties**.


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

### Módulo Subvención

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Subvención:
- **Subvencion_LIST**: Listado de subvenciones
- **Subvencion_RECORD**: Ficha de subvención
- **Subvencion_SEARCHGROUP**: Búsqueda de agrupadas para subvenciones
- **Subvencion_ADD**:  Alta de subvención
- **Subvencion_UPDATE**: Modificación de subvención
- **Subvencion_DELETE**: Baja de subvención
- **Subvencion_TRANSFORM**: Transformador de una subvención externa (a través de JSON)

### Módulo Subvención Beneficiario

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Subvención:
- **Subvencion_Beneficiario_LIST**: Listado de subvenciones Benficiarios
- **Subvencion_Beneficiario_RECORD**: Ficha de subvención Benficiario
- **Subvencion_Beneficiario_SEARCHGROUP**: Búsqueda de agrupadas para subvenciones Benficiarios
- **Subvencion_Beneficiario_ADD**:  Alta de subvención Benficiario
- **Subvencion_Beneficiario_UPDATE**: Modificación de subvención Benficiario
- **Subvencion_Beneficiario_DELETE**: Baja de subvención Benficiario
- **Subvencion_Beneficiario_TRANSFORM**: Transformador de una subvención Benficiario externa (a través de JSON)

### Módulo Subvención Organization

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Subvención:
- **Subvencion_Organization_LIST**: Listado de Organinzaciones de las subvenciones 
- **Subvencion_Organization_RECORD**: Ficha de Organinzacion de las subvención
- **Subvencion_Organization_SEARCHGROUP**: Búsqueda de agrupadas para Organinzaciones de las subvenciones 
- **Subvencion_Organization_ADD**:  Alta de Organinzacion de la subvención 
- **Subvencion_Organization_UPDATE**: Modificación de Organinzacion de la subvención 
- **Subvencion_Organization_DELETE**: Baja de Organinzacion de la subvención 
- **Subvencion_Organization_TRANSFORM**: Transformador de una Organinzacion de la subvención  externa (a través de JSON)

Y los parámetros configurados quedarían de la siguiente manera en el fichero (**subvencion.properties**) Como Ejemplo:
- **peticiones.identificadas.public_auth=Subvencion_ADD,Subvencion_RECORD,Subvencion_LIST**
- **peticiones.identificadas.basic_auth= Subvencion_UPDATE,Subvencion_SEARCHGROUP,Subvencion_TRANSFORM**
- **peticiones.identificadas.admin_auth= Subvencion_DELETE**


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
