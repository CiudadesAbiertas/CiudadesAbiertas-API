
# API CIUDADES ABIERTAS - MÓDULO EMPLEO

Este módulo integra el vocabulario de empleo para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	BoletinOficial
-	ConvocatoriaEmpleoPublico
-	OfertaEmpleoPublico
-	PlazaPorTurno
-	RelBoletinConvoca
-	RelOfertaConvoca


## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **empelo.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **empleo** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **empleo.properties**.

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

### Módulo OfertaPublica

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **OfertaPublica_LIST**: Listado de Oferta Publica
-   **OfertaPublica_RECORD**: Ficha de una Oferta Publica
-   **OfertaPublica_ADD**: Alta de una Oferta Publica
-   **OfertaPublica_UPDATE**: Modificación de una Oferta Publica
-   **OfertaPublica_DELETE**: Baja de una Oferta Publica
-   **OfertaPublica_TRANSFORM**: Transformador de una Oferta Publica externo (a través de JSON)


### Módulo ConvocatoriaPublica

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ConvocatoriaPublica_LIST**: Listado de Convocatoria Publica
-   **ConvocatoriaPublica_RECORD**: Ficha de una Convocatoria Publica
-   **ConvocatoriaPublica_ADD**: Alta de una Convocatoria Publica
-   **ConvocatoriaPublica_UPDATE**: Modificación de una Convocatoria Publica
-   **ConvocatoriaPublica_DELETE**: Baja de una Convocatoria Publica
-   **ConvocatoriaPublica_TRANSFORM**: Transformador de una Convocatoria Publica externo (a través de JSON)


### Módulo BoletinOficial

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **BoletinOficial_LIST**: Listado de Boletin Oficial
-   **BoletinOficial_RECORD**: Ficha de una Boletin Oficial
-   **BoletinOficial_ADD**: Alta de una Boletin Oficial
-   **BoletinOficial_UPDATE**: Modificación de una Boletin Oficial
-   **BoletinOficial_DELETE**: Baja de una Boletin Oficial
-   **BoletinOficial_TRANSFORM**: Transformador de una Boletin Oficial externo (a través de JSON)

### Módulo PlazaPorTurno

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **PlazaPorTurno_LIST**: Listado de intervalos de Plaza por Turno
-   **PlazaPorTurno_RECORD**: Ficha de un intervalo de Plaza por Turno
-   **PlazaPorTurno_ADD**: Alta de un intervalo de Plaza por Turno
-   **PlazaPorTurno_UPDATE**: Modificación de un intervalo de Plaza por Turno
-   **PlazaPorTurno_DELETE**: Baja de un intervalo de Plaza por Turno
-   **PlazaPorTurno_TRANSFORM**: Transformador de un intervalo de Plaza por Turno externo (a través de JSON)

### Módulo RelBoletinConvoca

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **RelBoletinConvoca_LIST**: Listado de grupos de viajes en Relación Boletin Convocatoria
-   **RelBoletinConvoca_RECORD**: Ficha de un grupo de viajes en Relación Boletin Convocatoria
-   **RelBoletinConvoca_ADD**: Alta de un grupo de viajes en Relación Boletin Convocatoria
-   **RelBoletinConvoca_UPDATE**: Modificación de un grupo de viajes en Relación Boletin Convocatoria
-   **RelBoletinConvoca_DELETE**: Baja de un grupo de viajes en Relación Boletin Convocatoria
-   **RelBoletinConvoca_TRANSFORM**: Transformador de un grupo de viajes en Relación Boletin Convocatoria externo (a través de JSON)





# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)
