
# Inicializador y gestión de versiones de la base de datos para la aplicación API REST de Ciudades Abiertas
Esta aplicación sirve para realizar la carga de la base de datos. Con esta aplicación se consigue independizarse del sistema general de base de datos. 
Es necesario tener el usuario <**ciudadesAbiertas**> ya creado en la BBDD.

# Pasos previos a la ejecución

## Requisitos 
Se debe de tener instalado:
* Java 1.8 (compatible con OPENJDK11)
* maven >= 3.5.0
* El usuario debe existir en la BBDD.

## Configuración liquibase.properties
Este es el fichero de configuración necesario para la correcta ejecución de la carga. Aquí se especificarán los parámetros para conectar con la BBDD.

El fichero se encuentra en ../liquibase_CA/src/main/resources/liquibase/liquibase.properties

Actualmente se soportan tres sistemas de BBDD: Oracle, SQLServer y MySQL.

Sólo se requiere activa una configuración, por lo que la que no se use se debería borrar o comentar, antes de ejecutar la aplicación.

### Parámetros del fichero de configuración

**driver:** Paquete donde se encuentra el driver para la ejecución del programa. Este campo es obligatorio.

**url:** Dirección de la base de datos para la ejecución del programa. Este campo es obligatorio.

**username**: Usuario de la base de datos para la ejecución del programa. Este campo es obligatorio.

**password**: Contraseña de la base de datos para la ejecución del programa. Este campo es obligatorio.

**outputChangeLogFile**: Sólo se configura en el caso de ejecutar una exportación de datos, no es obligatorio configurar en la carga o importación de los datos.

### Ejemplo de configuración para una base de Datos en MySQL
driver=com.mysql.jdbc.Driver

url=jdbc:mysql://localhost:3306/ciudadesAbiertas

username=ciudadesAbiertas

password=xxxxxx

### Ejemplo de configuración para una base de Datos en Oracle
driver=oracle.jdbc.OracleDriver

url=jdbc:oracle:thin:@//localhost:1521/xe

username=ciudadesAbiertas

password=ciudadesAbiertas

### Nota
En todos los ejemplos se usa <**ciudadesAbiertas**> no es necesario que se replique. Puede ser cualquier otro usuario y contraseña pero se debe configurar en los parámetros de acceso y existir en la BBDD.

### Ejemplo de configuración del parámetro adicional para realizar la extracción de datos
**outputChangeLogFile**=D:\\borrar\\liquibase\\output.xml

Este fichero se utiliza para extraer la información de una BBDD ya operativa, es decir, sólo se configura cuando se requiera extraer información del usuario <**ciudadesAbiertas**> para generar un nuevo backup de scripts de carga o ver alguna modificación de la BBDD que no se ha contemplado en los scripts de carga.

# Pasos de ejecución 

1. Se clona el proyecto Ciudades Abiertas, y se accede al directorio "liquibase_CA"

2. Se configura el fichero liquibase.properties según se ha explicado en el punto anterior.

3. Se ejecutan los siguientes comandos:

```sh
mvn install
mvn liquibase:update
```

Nota: si se desea aumentar el nivel de log, se puede hacer con el comando:

```sh
mvn -X liquibase:update
```


# Documentación adicional
## Ficheros de importación

A continuación se encuentran descritos los ficheros que se utilizan para la carga de información y estructuras de datos de la aplicación API REST Ciudades Abiertas.

Todos los ficheros que se van a mencionar se encuentran en la ruta 
El fichero se encuentra en ../liquibase_CA/src/main/resources/liquibase/  

**Maestro de carga**

db-changelog-master.xml

**Otros ficheros**

| Fichero                                      | Versión | Descripción                                                                         |
|----------------------------------------------|---------|-------------------------------------------------------------------------------------|
| db-changelog-script-SQLSERVER-FUNCIONES-1.0.1.xml | 0.1        | Funciones necesarias para SQLServer
| db-changelog-script-CORE-table-1.0.xml       | 1.0       | Tablas necesarias para el funcionamiento básico del API                           |
| db-changelog-script-CORE-data-1.0.xml        | 1.0        | Datos para las tablas necesarias para el funcionamiento básico del API           |
| db-changelog-script-SUBVENCION-table-1.1.xml |  1.1        |Tablas necesarias para el conjunto de datos **subvenciones** |
| db-changelog-script-SUBVENCION-data-1.1.xml |  1.1        | Datos de prueba para el conjunto de datos **subvenciones** |
| db-changelog-script-EQUIPAMIENTO-table-1.2.xml |  1.2        |Tablas necesarias para los conjuntos de datos **equipamientos municipales**, **aparcamientos**, **instalaciones deportivas** y **puntos wifi**  |
| db-changelog-script-EQUIPAMIENTO-data-1.2.xml |  1.2        | Datos de prueba para el conjunto de datos **equipamientos municipales** |
| db-changelog-script-AGENDA-table-1.3.xml |  1.3        |Tablas necesarias para el conjunto de datos **agenda** |
| db-changelog-script-AGENDA-data-1.3.xml |  1.3        | Datos de prueba para el conjunto de datos **agenda** |
| db-changelog-script-LOCAL-COMERCIAL-table-1.4.xml | 1.4        |Tablas necesarias para el conjunto de datos **locales comerciales** |
| db-changelog-script-LOCAL-COMERCIAL-data-1.4.xml |  1.4        | Datos de prueba para el conjunto de datos  **locales comerciales** | 
| db-changelog-script-EQUIPAMIENTO-PUNTOWIFI-data-1.5.xml | 1.5        |  Datos de prueba para el conjunto de datos **puntos wifi** |
| db-changelog-script-EQUIPAMIENTO-APARCAMIENTOS-data-1.6.xml |  1.6        | Datos de prueba para el conjunto de datos **aparcamientos** |
| db-changelog-script-EQUIPAMIENTO-INSTAL-DEPORTIVAS-data-1.7.xml |  1.7        | Datos de prueba para el conjunto de datos **instalaciones deportivas** |
| db-changelog-script-AVISO_QUEJA_SUG-table-1.8.xml |  1.8        |Tablas necesarias para el conjunto de datos **avisos, quejas y sugerencias** |
| db-changelog-script-AVISO_QUEJA_SUG-data-1.8.xml |  1.8        | Datos de prueba para el conjunto de datos **avisos, quejas y sugerencias** |
| db-changelog-script-CALIDAD_AIRE-table-1.9.xml |  1.9        |Tablas necesarias para el conjunto de datos **calidad del aire** |
| db-changelog-script-CALIDAD_AIRE-data-1.9.xml |  1.9        | Datos de prueba para el conjunto de datos **calidad del aire** |
| db-changelog-script-ORGANIGRAMA-table-1.10.xml |  1.10        |Tablas necesarias para el conjunto de datos **organigrama** |
| db-changelog-script-ORGANIGRAMA-data-1.10.xml | 1.10        | Datos de prueba para el conjunto de datos **organigrama** |
| db-changelog-script-PUNTO-INTERES-TURISTICO-table-1.11.xml | 1.11        | Tablas necesarias para el conjunto de datos **puntos de interés turístico** y **monumentos** |
| db-changelog-script-PUNTO-INTERES-TURISTICO-data-1.11.xml |  1.11        | Datos de prueba para el conjunto de datos **puntos de interés turístico** |
| db-changelog-script-MONUMENTOS-data-1.12.xml |  1.12        | Datos de prueba para el conjunto de datos **monumentos** |
| db-changelog-script-ALOJAMIENTO-table-1.13.xml |  1.13        |Tablas necesarias para el conjunto de datos **alojamientos** |
| db-changelog-script-ALOJAMIENTO-data-1.13.xml |  1.13       | Datos de prueba para el conjunto de datos **alojamientos** |
| db-changelog-script-CALLEJERO-table-1.14.xml |  1.14        |Tablas necesarias para el conjunto de datos **callejero** |
| db-changelog-script-CALLEJERO-data-1.14.xml |  1.14        | Datos de prueba para el conjunto de datos **callejero** |
| db-changelog-script-TRAMITE-table-1.15.xml |  1.15        |Tablas necesarias para el conjunto de datos **tramites** |
| db-changelog-script-TRAMITE-data-1.15.xml |  1.15        | Datos de prueba para el conjunto de datos **tramites** |
| db-changelog-script-PLANTILLA-table-1.16.xml |  1.16        |Tablas necesarias para el conjunto de datos **plantilla** |
| db-changelog-script-PLANTILLA-data-1.16.xml |  1.16       | Datos de prueba para el conjunto de datos **plantilla** |


  
# Autores
 - Juan Carlos Ballesteros (Localidata)
 - Carlos Martínez de la Casa (Localidata)
 - Oscar Corcho (UPM, Localidata)

# Licencia
Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es

This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).

Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
You may not use this work except in compliance with the Licence.
You may obtain a copy of the Licence at:

https://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the Licence for the specific language governing permissions and limitations under the Licence.