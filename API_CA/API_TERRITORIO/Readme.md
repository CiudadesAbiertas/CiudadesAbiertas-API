
# API CIUDADES ABIERTAS - MÓDULO TERRITORIO

Este módulo no es opcional, ya que es un módulo maestro que siempre debe aparecer en cualquier instalación de la API. Este módulo permite la integración (a nivel semántico, RDF) con aquellos módulos que en su vocabulario tengan referencias a campos de Territorio.

Los conjuntos de datos asociados a este módulo son:
-	País
-	Autonomía
-	Provincia
-	Municipio
-	Distrito
-	Barrio
-	Sección Censal


## Configuración del Módulo

La Aplicación permite realizar configuraciones específicas asociadas a cada uno de los módulos que se integran.

Para realizar esto lo único que se necesita es crear un fichero **territorio.properties** e incluirlo en la carpeta de recursos del proyecto WEB (API_WEB/src/main/resources/) donde se distribuyen el resto de ficheros de configuración.

Esto permite que el módulo de **Territorio** utilice la información que contenga este fichero cuando se ejecutan peticiones sobre dicho módulo.

A continuación se detallan los parámetros que se pueden configurar en este fichero **territorio.properties**.


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

Cuando se implementa esta funcionalidad se configura a nivel de etiquetas de los servicios de cada uno de los controladores asociados al módulo de Territorio: (Como ejemplo mostramos los asociados a Autonomía)
- **AUTONOMIA_LIST**: Listado de territorios
- **AUTONOMIA_RECORD**: Ficha de territorio
- **AUTONOMIA_GEOMETRY**: Búsqueda para obtener las geometrias


Y los parámetros configurados quedarían de la siguiente manera en el fichero (**territorio.properties**) Como Ejemplo:
- **peticiones.identificadas.public_auth=AUTONOMIA_GEOMETRY,AUTONOMIA_RECORD,AUTONOMIA_LIST**

Para el resto de conjunto de datos se realizaria igual.

- **peticiones.identificadas.public_auth=Pais_GEOMETRY,Pais_RECORD,Pais_LIST,AUTONOMIA_GEOMETRY,AUTONOMIA_RECORD,AUTONOMIA_LIST**


```sh
peticiones.identificadas.public_auth=Pais_GEOMETRY,Pais_RECORD,Pais_LIST,Autonomia_GEOMETRY,Autonomia_RECORD,Autonomia_LIST
```
Listado de Operaciones:
- **PAIS_LIST**: Listado de territorios
- **PAIS_RECORD**: Ficha de territorio
- **PAIS_GEOMETRY**: Búsqueda para obtener las geometrias
- **AUTONOMIA_LIST**: Listado de territorios
- **AUTONOMIA_RECORD**: Ficha de territorio
- **AUTONOMIA_GEOMETRY**: Búsqueda para obtener las geometrias
- **PROVINCIA_LIST**: Listado de territorios
- **PROVINCIA_RECORD**: Ficha de territorio
- **PROVINCIA_GEOMETRY**: Búsqueda para obtener las geometrias
- **MUNICIPIO_LIST**: Listado de territorios
- **MUNICIPIO_RECORD**: Ficha de territorio
- **MUNICIPIO_GEOMETRY**: Búsqueda para obtener las geometrias
- **DISTRITO_LIST**: Listado de territorios
- **DISTRITO_RECORD**: Ficha de territorio
- **DISTRITO_GEOMETRY**: Búsqueda para obtener las geometrias
- **BARRIO_LIST**: Listado de territorios
- **BARRIO_RECORD**: Ficha de territorio
- **BARRIO_GEOMETRY**: Búsqueda para obtener las geometrias
- **SECCIONCENSAL_LIST**: Listado de territorios
- **SECCIONCENSAL_RECORD**: Ficha de territorio
- **SECCIONCENSAL_GEOMETRY**: Búsqueda para obtener las geometrias


**NOTA:** Si alguna etiqueta de identificación de servicio no se incluyera en los parametros se le aplicaria la configuración por defecto asociada al mismo.


## Configuración de Recursos Adicionales al módulo.
A parte de las configuraciones indicadas anteriormente, el módulo de territorio necesita configurar los recursos necesarios para que se generen correctamente las geometrías  (el campo **hasGeometry** de los modelos) asociadas a los módulos, estos recursos son ficheros geojson necesarios para el correcto funcionamiento del módulo de territorio.
Estos recursos se configuran en el módulo web directamente con la estructura de directorios que se indica a continuación:

![alt text](https://raw.githubusercontent.com/CiudadesAbiertas/CiudadesAbiertas-API/master/API_CA/Doc/territorioRecursos.PNG "Estructura de directorios para el módulo de Territorio en WEB")

Los directorios de recursos se corresponden a cada uno de los módulos definidos para Territorio:
-	País
-	Autonomía
-	Provincia
-	Municipio
-	Distrito
-	Barrio
-	Sección Censal

Cada directorio de los módulos consta a su vez de directorios con el valor de las proyecciones soportadas en la aplicación (EPSG:25830, EPSG:4258, EPSG:4326, ... etc)

Dentro de los directorios de proyecciones soportadas, se encuentra los recursos en formato geojson asociados a cada módulo.

**Ejemplo:** para el modulo de barrio adjuntamos el formato asociado al barrio:2800601 cuyo fichero de proyección para EPSG:25830 seria **2800601.geojson**

**Vista del fichero:**
```sh
{
"type": "FeatureCollection",
"name": "shapefile25830",
"crs": { "type": "name", "properties": { "name": "urn:ogc:def:crs:EPSG::25830" } },
"features": [
{ "type": "Feature", "properties": { "OBJECTID": 16792, "CBARRIO": "28006011","CUSEC": "2800601001", "CUMUN": "28006", "CSEC": "001", "CDIS": "01", "CMUN": "006", "CPRO": "28", "CCA": "13", "CUDIS": "2800601", "OBS": null, "CNUT0": "ES", "CNUT1": "3", "CNUT2": "0", "CNUT3": "0", "CLAU2": "28006", "NPRO": "Madrid", "NCA": "Comunidad de Madrid", "NMUN": "Alcobendas", "Shape_Leng": 828.67709005999995, "Shape_area": 36754.1645546, "Shape_len": 828.67709005999995, "OBJECTID_2": 16792, "CUSEC_2": "2800601001", "CUMUN_2": "28006", "CSEC_2": "001", "CDIS_2": "01", "CMUN_2": "006", "CPRO_2": "28", "CCA_2": "13", "CUDIS_2": "2800601", "OBS_2": null, "CNUT0_2": "ES", "CNUT1_2": "3", "CNUT2_2": "0", "CNUT3_2": "0", "CLAU2_2": "28006", "NPRO_2": "Madrid", "NCA_2": "Comunidad de Madrid", "NMUN_2": "Alcobendas", "Shape_Leng_2": 828.67709005999995, "Shape_area_2": 36754.1645546, "Shape_len_2": 828.67709005999995 }, "geometry": { "type": "MultiPolygon", "coordinates": [ [ [ [ 446242.97030000021914, 4484314.788100001402199 ], ... } }
]
}
```


# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)
