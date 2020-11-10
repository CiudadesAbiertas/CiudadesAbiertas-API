
# API CIUDADES ABIERTAS - MÓDULO AUTOBÚS

Este módulo integra el vocabulario de contratos para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	Authority
-	DayType
-	DayTypeAssignment
-	HeadwayInterval
-	HeadwayJourneyGroup
-	Incidencia
-	JourneyPattern
- 	Linea
-	Operator
-	Parada
-	PointOnRoute
-	RealTimePassingTime
-	RelLineaIncidencia
-	Route
-	ScheduledStopPoint
-	ServiceCalendar
-	StopPointInJourneyPattern
-	VehicleJourney

## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **autobus.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **autobus** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **autobus.properties**.

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

### Módulo Authority

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Authority_LIST**: Listado de autoridades
-   **Authority_RECORD**: Ficha de una autoridad
-   **Authority_ADD**: Alta de una autoridad
-   **Authority_UPDATE**: Modificación de una autoridad
-   **Authority_DELETE**: Baja de una autoridad
-   **Authority_TRANSFORM**: Transformador de una autoridad externo (a través de JSON)

### Módulo DayTypeAssignment

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **DayTypeAssignment_LIST**: Listado de asignaciones por día tipo
-   **DayTypeAssignment_RECORD**: Ficha de una asignación por día tipo
-   **DayTypeAssignment_ADD**: Alta de una asignación por día tipo
-   **DayTypeAssignment_UPDATE**: Modificación de una asignación por día tipo
-   **DayTypeAssignment_DELETE**: Baja de una asignación por día tipo
-   **DayTypeAssignment_TRANSFORM**: Transformador de una asignación por día tipo externo (a través de JSON)

### Módulo DayType

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **DayType_LIST**: Listado de días tipo
-   **DayType_RECORD**: Ficha de un día tipo
-   **DayType_ADD**: Alta de un día tipo
-   **DayType_UPDATE**: Modificación de un día tipo
-   **DayType_DELETE**: Baja de un día tipo
-   **DayType_TRANSFORM**: Transformador de un día tipo externo (a través de JSON)

### Módulo HeadwayInterval

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **HeadwayInterval_LIST**: Listado de intervalos de cabecera
-   **HeadwayInterval_RECORD**: Ficha de un intervalo de cabecera
-   **HeadwayInterval_ADD**: Alta de un intervalo de cabecera
-   **HeadwayInterval_UPDATE**: Modificación de un intervalo de cabecera
-   **HeadwayInterval_DELETE**: Baja de un intervalo de cabecera
-   **HeadwayInterval_TRANSFORM**: Transformador de un intervalo de cabecera externo (a través de JSON)

### Módulo HeadwayJourneyGroup

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **HeadwayJourneyGroup_LIST**: Listado de grupos de viajes en cabecera
-   **HeadwayJourneyGroup_RECORD**: Ficha de un grupo de viajes en cabecera
-   **HeadwayJourneyGroup_ADD**: Alta de un grupo de viajes en cabecera
-   **HeadwayJourneyGroup_UPDATE**: Modificación de un grupo de viajes en cabecera
-   **HeadwayJourneyGroup_DELETE**: Baja de un grupo de viajes en cabecera
-   **HeadwayJourneyGroup_TRANSFORM**: Transformador de un grupo de viajes en cabecera externo (a través de JSON)

### Módulo Incidencia

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Incidencia_LIST**: Listado de incidencias
-   **Incidencia_RECORD**: Ficha de una incidencia
-   **Incidencia_ADD**: Alta de una incidencia
-   **Incidencia_UPDATE**: Modificación de una incidencia
-   **Incidencia_DELETE**: Baja de una incidencia
-   **Incidencia_TRANSFORM**: Transformador de una incidencia externo (a través de JSON)

### Módulo Journeypattern

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Journeypattern_LIST**: Listado de patrones de viaje
-   **Journeypattern_RECORD**: Ficha de un patrón de viaje
-   **Journeypattern_ADD**: Alta de un patrón de viaje
-   **Journeypattern_UPDATE**: Modificación de un patrón de viaje
-   **Journeypattern_DELETE**: Baja de un patrón de viaje
-   **Journeypattern_TRANSFORM**: Transformador de un patrón de viaje externo (a través de JSON)

### Módulo Linea

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Linea_LIST**: Listado de lineas
-   **Linea_RECORD**: Ficha de una linea
-   **Linea_ADD**: Alta de una linea
-   **Linea_UPDATE**: Modificación de una linea
-   **Linea_DELETE**: Baja de una linea de viaje
-   **Linea_TRANSFORM**: Transformador de una linea externo (a través de JSON)

### Módulo Operator

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Operator_LIST**: Listado de operadores
-   **Operator_RECORD**: Ficha de un operador
-   **Operator_ADD**: Alta de un operador
-   **Operator_UPDATE**: Modificación de un operador
-   **Operator_DELETE**: Baja de un operador
-   **Operator_TRANSFORM**: Transformador de un operador externo (a través de JSON)

### Módulo Parada

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Parada_LIST**: Listado de paradas
-   **Parada_RECORD**: Ficha de una parada
-   **Parada_ADD**: Alta de una parada
-   **Parada_UPDATE**: Modificación de una parada
-   **Parada_DELETE**: Baja de una parada
-   **Parada_TRANSFORM**: Transformador de una parada externo (a través de JSON)

### Módulo PointOnRoute

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **PointOnRoute_LIST**: Listado de puntos de ruta
-   **PointOnRoute_RECORD**: Ficha de un punto de ruta
-   **PointOnRoute_ADD**: Alta de una parada
-   **PointOnRoute_UPDATE**: Modificación de una parada
-   **PointOnRoute_DELETE**: Baja de una parada
-   **PointOnRoute_TRANSFORM**: Transformador de una parada externo (a través de JSON)

### Módulo RealTimePassingTime

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **RealtimeBUS_LIST**: Listado de tiempos de llegada previsto
-   **RealtimeBUS_RECORD**: Ficha de un tiempo de llegada previsto
-   **RealtimeBUS_ADD**: Alta de un tiempo de llegada previsto
-   **RealtimeBUS_UPDATE**: Modificación de un tiempo de llegada previsto
-   **RealtimeBUS_DELETE**: Baja de un tiempo de llegada previsto
-   **RealtimeBUS_TRANSFORM**: Transformador de un tiempo de llegada previsto externo (a través de JSON)

### Módulo RelLineaIncidencia

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **RelLineaIncidencia_LIST**: Listado de relaciones Linea e Incidencia
-   **RelLineaIncidencia_RECORD**: Ficha de una relación Linea e Incidencia
-   **RelLineaIncidencia_ADD**: Alta de una relación Linea e Incidencia
-   **RelLineaIncidencia_UPDATE**: Modificación de una relación Linea e Incidencia
-   **RelLineaIncidencia_DELETE**: Baja de una relación Linea e Incidencia
-   **RelLineaIncidencia_TRANSFORM**: Transformador de una relación Linea e Incidencia externo (a través de JSON)

### Módulo Route

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **Route_LIST**: Listado de rutas
-   **Route_RECORD**: Ficha de una ruta
-   **Route_ADD**: Alta de una ruta
-   **Route_UPDATE**: Modificación de una ruta
-   **Route_DELETE**: Baja de una ruta
-   **Route_TRANSFORM**: Transformador de una ruta externo (a través de JSON)

### Módulo ScheduledStopPoint

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ScheduledPoint_LIST**: Listado de parada planificadas
-   **ScheduledPoint_RECORD**: Ficha de una parada planificada
-   **ScheduledPoint_ADD**: Alta de una parada planificada
-   **ScheduledPoint_UPDATE**: Modificación de una parada planificada
-   **ScheduledPoint_DELETE**: Baja de una parada planificada
-   **ScheduledPoint_TRANSFORM**: Transformador de una parada planificada externo (a través de JSON)

### Módulo ServiceCalendar

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **ServiceCalendar_LIST**: Listado de servicios de calendario
-   **ServiceCalendar_RECORD**: Ficha de un servicio de calendario
-   **ServiceCalendar_ADD**: Alta de un servicio de calendario
-   **ServiceCalendar_UPDATE**: Modificación de un servicio de calendario
-   **ServiceCalendar_DELETE**: Baja de un servicio de calendario
-   **ServiceCalendar_TRANSFORM**: Transformador de un servicio de calendario externo (a través de JSON)

### Módulo StopPointInJourneyPattern

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **StopPointInJourneyPattern_LIST**: Listado de puntos de Parada en Patrón de Viaje
-   **StopPointInJourneyPattern_RECORD**: Ficha de un punto de Parada en Patrón de Viaje
-   **StopPointInJourneyPattern_ADD**: Alta de un punto de Parada en Patrón de Viaje
-   **StopPointInJourneyPattern_UPDATE**: Modificación de un punto de Parada en Patrón de Viaje
-   **StopPointInJourneyPattern_DELETE**: Baja de un punto de Parada en Patrón de Viaje
-   **StopPointInJourneyPattern_TRANSFORM**: Transformador de un punto de Parada en Patrón de Viaje externo (a través de JSON)

### Módulo VehicleJourney

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **VehicleJourney_LIST**: Listado de planificación del vehículo
-   **VehicleJourney_RECORD**: Ficha de una planificación del vehículo
-   **VehicleJourney_ADD**: Alta de una planificación del vehículo
-   **VehicleJourney_UPDATE**: Modificación de una planificación del vehículo
-   **VehicleJourney_DELETE**: Baja de una planificación del vehículo
-   **VehicleJourney_TRANSFORM**: Transformador de una planificación del vehículo externo (a través de JSON)

# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)
