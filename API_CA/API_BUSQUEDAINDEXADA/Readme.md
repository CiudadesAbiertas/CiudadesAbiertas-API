
# API CIUDADES ABIERTAS - Búsqueda indexada

Esta es la documentación asociada a la búsqueda indexada

Contenido:
   
     - Configuración del módulo
     - Configuracion en SOLR: creacion de core y configuracion adicional
     - Carga del core


## Configuración del Módulo

Para configurar este módulo es necesario añadir y configurar las siguientes lineas en el fichero "config.properties" del módulo org.ciudades.abiertas.web

Los parámetros a modificar son:

**solr.coreURL:** URL donde se encuentra el core donde se van a lanzar las peticiones de datos

**solr.timeout:** timeout en milisegundos para cada peticion a SOLR

###Ejemplos de configuración:

solr.coreURL=http://localhost:8983/solr/ciudadesAbiertas

solr.timeout=10000

## Configuracion en SOLR: creacion de core y configuracion adicional

Requisitos: 

	- La versión de SOLR es 7.X (no se ha probado en versiones superiores)
	- SOLR no tiene la seguridad activada.
	- Se lanzan con un usuario que tiene privilegios para ejecutar el comando "sudo"	

Creación de un core para uso exclusivo del módulo "búsqueda indexada":

```sh
sudo su - solr -c "/opt/solr/bin/solr create -c ciudadesAbiertas -n data_driven_schema_configs"
```


Se debe tener un campo "_text_" donde se concatenan toda la información de cada elemento. Este campo se crea con este comando:


```sh
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-copy-field" : {"source":"*","dest":"_text_"}}' http://localhost:8983/solr/ciudadesAbiertas/schema
```

Se especifica que los siguientes campos son de tipo texto, ya que por defecto SOLR los detectará como números:

* postalCode

```sh
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field": {"name":"postalCode", "type":"text_general", "multiValued":false, "stored":true}}' http://localhost:8983/solr/ciudadesAbiertas/schema
```

* telephone

```sh
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field": {"name":"telephone", "type":"text_general", "multiValued":false, "stored":true}}' http://localhost:8983/solr/ciudadesAbiertas/schema
```


## Carga del core

Para que este módulo funcione es necesario tener un core de SOLR cargado con toda la información de la API en formato JSON elemento a elemento.

Antes de procesar esta carga es necesario saber que hay que añadir dos atributos a cada elemento:

- datasetName: nombre del dataset al que pertenece el elemento
- objectURI: URI del elemento, es la dirección donde está disponible dicho elemento. Ejemplo: https://api.ciudadesAbiertas.com/OpenCitiesAPI/subvencion/subvencion/S0001

###Ejemplo: subvenciones

Un subvencion estandar tiene la siguiente estructura en formato JSON:

```
{
    "id": "S0001",
    "title": "Convocatoria de beca",
    "areaId": "A05003362",
    "areaTitle": "SALUD",
    "municipioId": "28079",
    "municipioTitle": "MADRID",
    "adjudicatarioId": "02287827V",
    "adjudicatarioTitle": "JAVIER GOMEZ PLATERO",
    "entidadFinanciadoraId": "A05003362",
    "entidadFinanciadoraTitle": "SALUD",
    "importe": 11700,
    "fechaAdjudicacion": "2016-03-29T00:00:00",
    "lineaFinanciacion": "Medio ambiente y sostenibilidad",
    "basesReguladoras": "https://sede.madrid.es/portal/site/tramites/776",
    "tipoInstrumento": "OTROS INSTRUMENTOS DE AYUDA",
    "aplicacionPresupuestaria": "2016-G/31101/48201"
}
```

Cuando se envíe esta información a SOLR para que la indexe, se deben añadir estos dos atributos:

- datasetName: "subvencion"
- objectURI: "https://api.ciudadesAbiertas.com/OpenCitiesAPI/subvencion/subvencion/S0001"

La subvención que se enviaría a SOLR contendría la siguiente información:


```
{
    "id": "S0001",
    "title": "Convocatoria de beca",
    "areaId": "A05003362",
    "areaTitle": "SALUD",
    "municipioId": "28079",
    "municipioTitle": "MADRID",
    "adjudicatarioId": "02287827V",
    "adjudicatarioTitle": "JAVIER GOMEZ PLATERO",
    "entidadFinanciadoraId": "A05003362",
    "entidadFinanciadoraTitle": "SALUD",
    "importe": 11700,
    "fechaAdjudicacion": "2016-03-29T00:00:00",
    "lineaFinanciacion": "Medio ambiente y sostenibilidad",
    "basesReguladoras": "https://sede.madrid.es/portal/site/tramites/776",
    "tipoInstrumento": "OTROS INSTRUMENTOS DE AYUDA",
    "aplicacionPresupuestaria": "2016-G/31101/48201",
    "datasetName": "subvencion",
	 "objectURI": "https://api.ciudadesAbiertas.com/OpenCitiesAPI/subvencion/subvencion/S0001"
}
```


# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)