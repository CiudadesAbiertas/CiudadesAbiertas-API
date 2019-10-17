
# API CIUDADES ABIERTAS - MÓDULO PLANTILLA

Esta es la documentación asociada al módulo de Puntos Wifi.

Contenido:
   
     - Descripción
     - Creación de un módulo a partir de este

## Descripción

Este módulo se ha desarrollado para que sirva como punto de partida a la hora de desarrollar nuevos módulos.

Cuenta con los siguientes campos:

	- ikey
	- id 	
	- title	
	- x_etrs89
	- y_etrs89

Los dos primeros campos son obligatorios para todos los conjuntos de datos, y los siguientes suelen existir en la mayoría.

Este conjunto de datos no esta 'Activo' para que aparezca hay que descomentar las lineas donde aparece en los ficheros:

	- pom.xml del proyecto principal (todos los módulos cuelgan de él).
	- pom.xml del proyecto API_WEB


## Creación de un módulo a partir de este

Para seguir esta guia es necesario que se tenga desplegado en Eclipse el proyecto del API Ciudades Abiertas tal y como se indica en el readme del proyecto principal.

Estos son los pasos a seguir para crear un nuevo módulo a partir de este.

Se documentará el proceso pensando en un nuevo conjunto de datos llamado "bici", que contendrá estaciones de bicicletas públicas.

### 1) Creación de la información en base de datos
	
Los datos de este proyecto se encuentran en la tabla "plantilla", se recomienda hacer una copia de esta tabla, añadir los campos que sean necesarios y cargarla con la información que se desee mostrar en el API.

En el ejemplo presentado se creara la tabla "bici" con los siguientes sentencias (para MySQL):


```sql
CREATE TABLE `bici` (
  `ikey` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `title` varchar(400) NOT NULL,
  `x_etrs89` decimal(13,5) DEFAULT NULL,
  `y_etrs89` decimal(13,5) DEFAULT NULL,
  `street_address` varchar(200) NOT NULL,
  `num_plazas` int(11) DEFAULT NULL  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `bici` (`ikey`, `id`, `title`, `x_etrs89`, `y_etrs89`, `street_address`,`num_plazas`) VALUES
('0000000001', 'BICIPARK0000000001', 'Puerta del Sol A', '440407.15471', '4474267.12122', 'Puerta del Sol nº 1',30),
('0000000002', 'BICIPARK0000000002', 'Puerta del Sol B', '440407.65441', '4474278.75074', 'Puerta del Sol nº 2',30),
('0000000003', 'BICIPARK0000000003', 'Miguel Moya', '440120.61739', '4474679.29664', 'Calle Miguel Moya nº 1',20),
('0000000004', 'BICIPARK0000000004', 'Plaza Conde Suchil', '440037.98846', '4475757.28991', 'Plaza del Conde Suchil nº 2-4',20),
('0000000005', 'BICIPARK0000000005', 'Malasaña', '440403.69895', '4475561.07624', 'Calle Manuela Malasaña nº 5',25),
('0000000006', 'BICIPARK0000000006', 'Fuencarral', '440448.43094', '4475558.01207', 'Calle Fuencarral nº 108',15),
('0000000007', 'BICIPARK0000000007', 'Colegio Arquitectos', '440751.05080', '4475069.40409', 'Calle Hortaleza nº 63',20),
('0000000008', 'BICIPARK0000000008', 'Hortaleza', '440809.26759', '4475184.67963', 'Calle Hortaleza nº 75',20),
('0000000009', 'BICIPARK0000000009', 'Alonso Martínez', '441009.36023', '4475480.33338', 'Plaza de Alonso Martínez nº 5',30),
('0000000010', 'BICIPARK0000000010', 'Plaza de San Miguel', '439805.09594', '4474128.68796', 'Plaza de San Miguel nº 9',30);
```

	
### 2) Copiar, pegar el conjunto de datos plantilla y renombrar identificador.

SSe copiará el módulo "API_PLANTILLA" como "API_BICI".

Se editará el fichero "pom.xml" que contiene "API_BICI" y se cambiaran las siguientes lineas:

```
	...
	<artifactId>org.ciudadesabiertas.plantilla</artifactId>
  	<name>API_PLANTILLA</name>
  	<url>http://maven.apache.org</url>
  	<dependencies>
  	<!--  Dependencia CORE  -->
  	<dependency>
		<groupId>API_CA</groupId>
  		<artifactId>org.ciudadesabiertas.core</artifactId>
  		<version>0.0.1-SNAPSHOT</version>
	</dependency>
	</dependencies> 
  	<build>
		<finalName>API_PLANTILLA</finalName>
 	...
```

Por estas:

```
	...
	<artifactId>org.ciudadesabiertas.bici</artifactId>
  	<name>API_BICI</name>
  	<url>http://maven.apache.org</url>
  	<dependencies>
  	<!--  Dependencia CORE  -->
  	<dependency>
		<groupId>API_CA</groupId>
  		<artifactId>org.ciudadesabiertas.core</artifactId>
  		<version>0.0.1-SNAPSHOT</version>
	</dependency>
	</dependencies> 
  	<build>
		<finalName>API_BICI</finalName>
 	...
```

### 3) Importar y añadir dependencias

En Eclipse importaremos el proyecto que acabamos de crear dentro del Proyecto principal (API_CA) como "proyecto de maven ya existente".
A continuacion editaremos las dependencias de los proyectos API_WEB y API_CA para añadir nuestro nuevo módulo.


En el fichero API_CA/API_WEB/pom.xml añadimos las siguientes lineas en el bloque project>profiles>dependencies:

```
	<!-- Dependencia bici-->
	<dependency>
		<groupId>API_CA</groupId>
		<artifactId>org.ciudadesabiertas.bici</artifactId>
		<version>0.0.1-SNAPSHOT</version> 
	</dependency>
```

En el mismo fichero añadiemos las siguientes lineas en el bloque project>profiles

```
	<profile>
		<id>bici</id>
		<dependencies>			
			<dependency>
				<groupId>API_CA</groupId>
			  	<artifactId>org.ciudadesabiertas.bici</artifactId>
			  	<version>0.0.1-SNAPSHOT</version> 
			 </dependency>			  	
		</dependencies>
	</profile>
```
En el fichero API_CA/pom.xml añadimos la siguiente linea en el bloque project>profiles>profile>module:

```
	<module>API_BICI</module>
```

En el mismo fichero añadimos las siguientes lineas en el bloque project>profiles

```
	<profile>
		<id>bici</id>
		<modules>
			<module>rdfGeneratorZ</module>
			<module>API_CORE</module>
			<module>API_WEB</module>
			<module>API_BICI</module>
		</modules>
	</profile> 
```

Ahora deberiamos refrescar todo el proyecto para que los cambios que se han hecho se materialicen.

### 4) Renombrar  ficheros y ajustar URIs
Ahora se deben refactorizar todas las clases del proyecto "API_BICI"

Renombraemos las clases para adaptar su nombre a su contenido:

	- PlantillaController.java > BiciController.java
	- Plantilla.java > Bici.java
	...
		
Realizaremos los cambios que sean necesarios para ajustar metodos como constructores.

Volvemos a refrescar todo el proyecto para que los cambios que se han hecho se materialicen.


### 5) Cambiar tabla y añadir/modificar nuevos campos
En este punto ya se tiene un conjunto de datos totalmente funcional, pero que lee de la tabla "plantilla" en lugar de la tabla "bici".

Se editará el fichero del modelo "bici.java"

Se cambiará la linea: @Table(name = "plantilla") por @Table(name = "bici")

Ahora se añadirán los nuevos campos que existen en las tabla "bici" y que no existen en la tabla "plantilla".

Se añadiran los atributos:

```
	private String streetAddress;
	
	private Integer numPlazas;
```

Se modificarán los constructores y el metodo "toString()" para que cuenten con estos nuevos atributos.

Ahora se añadiran las anotaciones para generar CSV correctamente: 	

```
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="streetAddress")
	private String streetAddress;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="numPlazas")
	private Integer numPlazas;
```

En este punto sólo faltaría añadir las anotaciones para generar la nueva información en los formatos semánticos.

Para hacer esto nos basaremos en los vocabularios existentes, o en las propiedades que se han usado en otros conjuntos de datos.


```	
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="streetAddress")
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")
	private String streetAddress;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="numPlazas")
	@Rdf(contexto = Context.ESEQUIP, propiedad = "numPlazas", typeURI=Context.XSD_URI+"int")
	private Integer numPlazas;
```

#### 5.1) Paginas HTML (opcional)

Para generar el contenido HTML de cada modulo se deben generar las plantillas en formato thymeleaf en el directorio "API_CA\API_WEB\src\main\webapp\WEB-INF\templates"

En este directorio se encuentra el directorio "plantilla", si se quiere que se el proyecto "bici" disponga de contenido en html hay que crear dentro de templates el directorio "bici" y copiar los ficheros que se encuentran en "plantilla".


### 6) Generar tests

Por último (y no menos importante) recomendamos encarecidamente generar test del módulo que acabamos de crear.

En este módulo hay dos clases de Test que deben funcionar sin errores antes de liberar cualquier módulo.

Adicionalmente en el desarrollo de esta API en cada módulo se han implementados dos clases para testear Datos. Esto test (que en este módulo se han omitido) se utilizan para verificar que el funcionamiento de los modulos es identico en todas las bases de datos soportadas.	
	

# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)
