
# API CIUDADES ABIERTAS - MÓDULO TRÁFICO

Este módulo integra el vocabulario de contratos para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	TraficoDispositivoMedicion
-	TraficoEquipo
-	TraficoIncidencia
-	TraficoObservacion
-	TraficoObservacionDispostivo
-	TraficoProperInterval
-	TraficoPropiedadMedicion
- 	TraficoTramo
-	TraficoTramoVia

## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **trafico.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **trafico** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **trafico.properties**.

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


### Módulo TraficoDispositivoMedicion

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoDispositivo_LIST**: Listado de dispositivos de medición
-   **TraficoDispositivo_RECORD**: Ficha de un dispositivo de medición
-   **TraficoDispositivo_ADD**: Alta de un dispositivo de medición
-   **TraficoDispositivo_UPDATE**: Modificación de un dispositivo de medición
-   **TraficoDispositivo_DELETE**: Baja de un dispositivo de medición
-   **TraficoDispositivo_TRANSFORM**: Transformador de un dispositivo de medición externo (a través de JSON)

### Módulo TraficoEquipo

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoEquipo_LIST**: Listado de equipos
-   **TraficoEquipo_RECORD**: Ficha de un equipo
-   **TraficoEquipo_ADD**: Alta de un equipo
-   **TraficoEquipo_UPDATE**: Modificación de un equipo
-   **TraficoEquipo_DELETE**: Baja de un equipo
-   **TraficoEquipo_TRANSFORM**: Transformador de un equipo externo (a través de JSON)


### Módulo TraficoIncidencia

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoIncidencia_LIST**: Listado de incidencias
-   **TraficoIncidencia_RECORD**: Ficha de una incidencia
-   **TraficoIncidencia_ADD**: Alta de una incidencia
-   **TraficoIncidencia_UPDATE**: Modificación de una incidencia
-   **TraficoIncidencia_DELETE**: Baja de una incidencia
-   **TraficoIncidencia_TRANSFORM**: Transformador de una incidencia externo (a través de JSON)

### Módulo TraficoObservacion

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoObservacion_LIST**: Listado de observaciones
-   **TraficoObservacion_RECORD**: Ficha de una observación
-   **TraficoObservacion_ADD**: Alta de una observación
-   **TraficoObservacion_UPDATE**: Modificación de una observación
-   **TraficoObservacion_DELETE**: Baja de una observación
-   **TraficoObservacion_TRANSFORM**: Transformador de una observación externo (a través de JSON)

### Módulo TraficoObservacionDispostivo

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoObservacionDispostivo_LIST**: Listado de observaciones dispositivo
-   **TraficoObservacionDispostivo_RECORD**: Ficha de una observación dispositivo
-   **TraficoObservacionDispostivo_ADD**: Alta de una observación dispositivo
-   **TraficoObservacionDispostivo_UPDATE**: Modificación de una observación dispositivo
-   **TraficoObservacionDispostivo_DELETE**: Baja de una observación dispositivo
-   **TraficoObservacionDispostivo_TRANSFORM**: Transformador de una observación dispositivo externo (a través de JSON)

### Módulo TraficoProperInterval

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoProperInterval_LIST**: Listado de propiedades intervalo
-   **TraficoProperInterval_RECORD**: Ficha de una propiedad intervalo
-   **TraficoProperInterval_ADD**: Alta de una propiedad intervalo
-   **TraficoProperInterval_UPDATE**: Modificación de una propiedad intervalo
-   **TraficoProperInterval_DELETE**: Baja de una propiedad intervalo
-   **TraficoProperInterval_TRANSFORM**: Transformador de una propiedad intervalo externo (a través de JSON)

### Módulo TraficoPropiedadMedicion

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoPropiedadMedicion_LIST**: Listado de propiedades medición
-   **TraficoPropiedadMedicion_RECORD**: Ficha de una propiedad medición
-   **TraficoPropiedadMedicion_ADD**: Alta de una propiedad medición
-   **TraficoPropiedadMedicion_UPDATE**: Modificación de una propiedad medición
-   **TraficoPropiedadMedicion_DELETE**: Baja de una propiedad medición
-   **TraficoPropiedadMedicion_TRANSFORM**: Transformador de una propiedad medición externo (a través de JSON)

### Módulo TraficoTramo

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoTramo_LIST**: Listado de tramos
-   **TraficoTramo_RECORD**: Ficha de un tramo
-   **TraficoTramo_ADD**: Alta de un tramo
-   **TraficoTramo_UPDATE**: Modificación de un tramo
-   **TraficoTramo_DELETE**: Baja de un tramo
-   **TraficoTramo_TRANSFORM**: Transformador de un tramo externo (a través de JSON)

### Módulo TraficoTramoVia

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **TraficoTramoVia_LIST**: Listado de relaciones tramo vía
-   **TraficoTramoVia_RECORD**: Ficha de una relación tramo vía
-   **TraficoTramoVia_ADD**: Alta de una relación tramo vía
-   **TraficoTramoVia_UPDATE**: Modificación de una relación tramo vía
-   **TraficoTramoVia_DELETE**: Baja de una relación tramo vía
-   **TraficoTramoVia_TRANSFORM**: Transformador de una relación tramo vía externo (a través de JSON)



**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.



# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Hugo Lafuente Matesanz (Localidata)
Oscar Corcho (UPM, Localidata)