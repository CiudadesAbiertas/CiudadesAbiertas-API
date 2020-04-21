
# API CIUDADES ABIERTAS - MÓDULO BICICLETA PÚBLICA

Esta es la documentación asociada al módulo de Bicicleta pública.

Contenido:
   
     - Descripción
     - Creación de un módulo a partir de este

## Descripción

Este módulo integra el vocabulario de contratos para las consultas semánticas (RDF)  y contine los siguiente (sub)módulos:
-	Anclaje
-	Bicicleta
-	Estacion
-	Observación
-	Punto de paso
- 	Trayecto
- 	Usuario

## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **bicicletaPublica.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **bicicletaPublica** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **bicicletaPublica.properties**.

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


### Módulo Anclaje

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **BICICLETA_ANCLAJE_LIST**: Listado de anclajes
-   **BICICLETA_ANCLAJE_RECORD**: Ficha de un anclaje
-   **BICICLETA_ANCLAJE_ADD**: Alta de un anclaje
-   **BICICLETA_ANCLAJE_UPDATE**: Modificación de un anclaje
-   **BICICLETA_ANCLAJE_DELETE**: Baja de un anclaje
-   **BICICLETA_ANCLAJE_TRANSFORM**: Transformador de un anclaje externo (a través de JSON)

### Módulo Bicicleta

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **BICICLETA_LIST**: Listado de bicicletas
-   **BICICLETA_RECORD**: Ficha de una bicicleta
-   **BICICLETA_ADD**: Alta de una bicicleta
-   **BICICLETA_UPDATE**: Modificación de una bicicleta
-   **BICICLETA_DELETE**: Baja de una bicicleta
-   **BICICLETA_TRANSFORM**: Transformador de una bicicleta externa (a través de JSON)

### Módulo Estación

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **BICICLETA_ESTACION_LIST**: Listado de estaciones
-   **BICICLETA_ESTACION_RECORD**: Ficha de una estación
-   **BICICLETA_ESTACION_ADD**: Alta de una estación
-   **BICICLETA_ESTACION_UPDATE**: Modificación de una estación
-   **BICICLETA_ESTACION_DELETE**: Baja de una estación
-   **BICICLETA_ESTACION_TRANSFORM**: Transformador de una estación externa (a través de JSON)

### Módulo Observación

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **BICICLETA_OBSERVACION_LIST**: Listado de observaciones
-   **BICICLETA_OBSERVACION_RECORD**: Ficha de una observación
-   **BICICLETA_OBSERVACION_ADD**: Alta de una observación
-   **BICICLETA_OBSERVACION_UPDATE**: Modificación de una observación
-   **BICICLETA_OBSERVACION_DELETE**: Baja de una observación
-   **BICICLETA_OBSERVACION_TRANSFORM**: Transformador de una observación externa (a través de JSON)

### Módulo Punto de paso

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **BICICLETA_PUNTO_PASO_LIST**: Listado de puntos de paso
-   **BICICLETA_PUNTO_PASO_RECORD**: Ficha de un punto de paso
-   **BICICLETA_PUNTO_PASO_ADD**: Alta de un punto de paso
-   **BICICLETA_PUNTO_PASO_UPDATE**: Modificación de un punto de paso
-   **BICICLETA_PUNTO_PASO_DELETE**: Baja de un punto de paso
-   **BICICLETA_PUNTO_PASO_TRANSFORM**: Transformador de un punto de paso externa (a través de JSON)

### Módulo Trayecto

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **BICICLETA_TRAYECTO_LIST**: Listado de trayectos
-   **BICICLETA_TRAYECTO_RECORD**: Ficha de un trayecto
-   **BICICLETA_TRAYECTO_ADD**: Alta de un trayecto
-   **BICICLETA_TRAYECTO_UPDATE**: Modificación de un trayecto
-   **BICICLETA_TRAYECTO_DELETE**: Baja de un trayecto
-   **BICICLETA_TRAYECTO_TRANSFORM**: Transformador de un trayecto externa (a través de JSON)

### Módulo Usuario

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados.

-   **BICICLETA_USUARIO_LIST**: Listado de usuarios
-   **BICICLETA_USUARIO_RECORD**: Ficha de un usuario
-   **BICICLETA_USUARIO_ADD**: Alta de un usuario
-   **BICICLETA_USUARIO_UPDATE**: Modificación de un usuario
-   **BICICLETA_USUARIO_DELETE**: Baja de un usuario
-   **BICICLETA_USUARIO_TRANSFORM**: Transformador de un usuario externa (a través de JSON)

```sh
peticiones.identificadas.public_auth=BICICLETA_USUARIO_LIST,BICICLETA_USUARIO_ADD,BICICLETA_TRAYECTO_LIST,BICICLETA_TRAYECTO_ADD,BICICLETA_PUNTO_PASO_LIST,BICICLETA_PUNTO_PASO_ADD,BICICLETA_OBSERVACION_LIST,BICICLETA_OBSERVACION_ADD,BICICLETA_ESTACION_LIST,BICICLETA_ESTACION_ADD,BICICLETA_LIST,BICICLETA_ADD,BICICLETA_ANCLAJE_LIST,BICICLETA_ANCLAJE_ADD,
peticiones.identificadas.basic_auth=BICICLETA_USUARIO_UPDATE,BICICLETA_USUARIO_TRANSFORM,BICICLETA_TRAYECTO_UPDATE,BICICLETA_TRAYECTO_TRANSFORM,BICICLETA_PUNTO_PASO_UPDATE,BICICLETA_PUNTO_PASO_TRANSFORM,BICICLETA_OBSERVACION_UPDATE,BICICLETA_OBSERVACION_TRANSFORM,BICICLETA_ESTACION_UPDATE,BICICLETA_ESTACION_TRANSFORM,BICICLETA_UPDATE,BICICLETA_TRANSFORM,BICICLETA_ANCLAJE_UPDATE,BICICLETA_ANCLAJE_TRANSFORM
peticiones.identificadas.admin_auth=BICICLETA_USUARIO_DELETE,BICICLETA_TRAYECTO_DELETE,BICICLETA_PUNTO_PASO_DELETE,BICICLETA_OBSERVACION_DELETE,BICICLETA_ESTACION_DELETE,BICICLETA_DELETE,BICICLETA_ANCLAJE_DELETE
```

**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.



# Autores
- Juan Carlos Ballesteros (Localidata)
- Carlos Martínez de la Casa (Localidata)
- Hugo Lafuente (Localidata)
- Oscar Corcho (UPM, Localidata)