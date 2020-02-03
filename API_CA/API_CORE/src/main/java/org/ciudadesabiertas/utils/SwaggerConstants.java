/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package org.ciudadesabiertas.utils;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class SwaggerConstants
{
	public static final String DESCRIPCION_INSERCION = "Permite realizar la operación de insercion a partir de una cadena en formato JSON";
	public static final String INSERCION = "Inserción de un nuevo registro";
	public static final String FORMATOS_ADD_RESPONSE = "application/json, application/xml";
	public static final String FORMATOS_ADD_REQUEST = "application/json";
	public static final String ERROR_INTERNO = "Error interno";
	public static final String NO_AUTORIZADO = "No autorizado";
	public static final String PETICION_INCORRECTA = "Petición incorrecta";
	public static final String RESULTADO_DE_BUSQUEDA_O_LISTADO = "Resultado de búsqueda o listado";	
	public static final String RESULTADO_DE_INSERCION = "Resultado de inserción";
	public static final String FORMATOS_CONSULTA_RESPONSE_GROUPBY = "application/json, application/xml, text/csv";
	public static final String FORMATOS_CONSULTA_RESPONSE_NO_HTML = "application/json, application/xml, text/csv, application/ld+json, text/turtle, application/rdf+xml, text/n3";
	public static final String FORMATOS_CONSULTA_RESPONSE_FULL = FORMATOS_CONSULTA_RESPONSE_NO_HTML+ ", text/html";
	public static final String FORMATOS_CONSULTA_INDEXADA_RESPONSE = "application/json, application/xml";
	public static final String DESCRIPCION_BUSQUEDA = "Permite realizar la operación de listado y búsquedas a través de diferentes parámetros";
	public static final String DESCRIPCION_BUSQUEDA_GEOGRAFICA = "Permite realizar la operación de búsqueda geográfica a través de las coordenadas de un punto y su radio en metros, se puden utilizar los demás atributos para una búsqueda más específica";
	public static final String LISTADO_Y_BUSQUEDA = "Listado y búsqueda";
	public static final String BUSQUEDA_GEOGRAFICA = "Búsqueda geográfica a traves de un punto y radio en metros";
	public static final String EL_RECURSO_YA_EXISTE = "El recurso ya existe";
	public static final String LISTADO_Y_BUSQUEDA_HTML="Listado y búsqueda en formato HTML";
	public static final String FORMATOS_CONSULTA_RESPONSE_HTML = "text/htm";
	public static final String FICHA_HTML="Ficha de un Registro en formato HTML";
	public static final String DESCRIPCION_FICHA = "Permite ver un registro en concreto del módulo correspondiente";
	public static final String RESULTADO_DE_FICHA = "El resultado es un registro que coincide con el identificador utilizado";	
	public static final String MODIFICACION = "Modificación de un registro";
	public static final String DESCRIPCION_MODIFICACION = "Permite realizar la operación de modificación a partir de una cadena en formato JSON";
	public static final String RESULTADO_DE_MODIFICACION = "Resultado de la modificación";
	public static final String DELETE = "Eliminación de un registro";
	public static final String DESCRIPCION_DELETE = "Permite realizar la operación de eliminación a partir de un id";
	public static final String RESULTADO_DE_DELETE = "Resultado de la eliminación";
	public static final String DESCRIPCION_BUSQUEDA_AGRUPADA_HTML = "Permite realizar la operación de busqueda agrupada a través de diferentes parámetros";
	public static final String LISTADO_Y_BUSQUEDA_AGRUPADA_HTML = "Búsqueda Agrupada en formato HTML";
	public static final String RESULTADO_DE_BUSQUEDA_O_LISTADO_AGRUPADA = "Resultado de la búsqueda agrupada";	
	public static final String DESCRIPCION_BUSQUEDA_AGRUPADA = "Permite realizar la operación de busqueda agrupada a través de diferentes parámetros, las operaciones permitidas son: SUM,COUNT,AVG,MAX y MIN";
	public static final String LISTADO_Y_BUSQUEDA_AGRUPADA = "Búsqueda Agrupada";
	public static final String BUSQUEDA_DISTINCT = "Búsqueda de los valores de un atributo";
	public static final String DESCRIPCION_BUSQUEDA_DISTINCT = "Permite realizar la operación de busqueda los distintos posibles valores de un atributo";
	public static final String RESULTADO_DE_BUSQUEDA_DISTINCT = "Resultado de la búsqueda por los posibles valores de un atributo";
	public static final String FICHA="Ficha de un registro";
	public static final String DESCRIPCION_BUSQUEDA_HEAD = "Permite realizar la operación de listado y búsquedas a través de diferentes parámetros pero solo obtiene los valores de las cabeceras, no se devuelve un cuerpo de mensaje.";
	public static final String DESCRIPCION_FICHA_HEAD = DESCRIPCION_FICHA + "  pero solo obtiene los valores de las cabeceras, no se devuelve un cuerpo de mensaje.";	
	public static final String FORMATOS_CONSULTA_HEAD = "Solo valores de la cabeceras de la petición (Header)";
	public static final String TRANSFORMACION="Transformación de un registro externo";
	public static final String DESCRIPCION_TRANSFORMACION = "Permite generar un recurso externo en los diferentes formatos de la API";
	public static final String LISTADO_CUBO_POR_DIMENSION = "Búsqueda de cubos de datos que tienen una derminada dimensión";
	public static final String CONSULTA_CUBO = "Consulta a un cubo de datos";
	public static final String DESCRIPCION_CONSULTA_CUBO = "Permite obtener los datos de un cubo fijando una dimensión, una medida y aplicando una operación de agregación";
	public static final String LISTADO_CUBO_POR_MEDIDA = "Búsqueda de cubos de datos que tienen una derminada medida";
	public static final String GEOMETRIA="Geometría de un registro";
	public static final String GEOMETRIA_FICHA = "Permite ver la geometria de un registro en concreto del módulo correspondiente";
	public static final String BUSQUEDA_DATASET_NAME_SOLR = "Listado DatasetName SOLR";
	public static final String DESCRIPCION_BUSQUEDA_DATASET_NAME_SOLR = "Búsqueda de todos los datasetName cargados en SOLR";
	
	
	//Parametros a Documentar
	public static final String PARAM_ID_TEXT= "id del Objeto necesario para ejecutar la operación";
	public static final String PARAM_SUBVENCION_TEXT= "Objeto (Subvención) en formato json necesario para poder realizar la operación";
	public static final String PARAM_EQUIPAMIENTO_TEXT= "Objeto (Equipamiento) en formato json necesario para poder realizar la operación";
	public static final String PARAM_LOCALCOMERCIAL_TEXT= "Objeto (Local Comercial) en formato json necesario para poder realizar la operación";
	public static final String PARAM_AGENDA_TEXT= "Objeto (Agenda) en formato json necesario para poder realizar la operación";
	public static final String PARAM_ALOJAMIENTO_TEXT= "Objeto (Alojamiento) en formato json necesario para poder realizar la operación";
	public static final String PARAM_AVISOQUEJASUG_TEXT= "Objeto (AvisoQuejaSug) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CALIDADAIREESTACION_TEXT= "Objeto (CalidadAireEstacion) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CALIDADAIREOBSERVACION_TEXT= "Objeto (CalidadAireObservacion) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CALIDADAIRESENSOR_TEXT= "Objeto (CalidadAireSensor) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CALLEJEROPORTAL_TEXT= "Objeto (CallejeroPortal) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CALLEJEROTRAMO_TEXT= "Objeto (CallejeroTramoVia) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CALLEJEROVIA_TEXT= "Objeto (CallejeroVia) en formato json necesario para poder realizar la operación";
	public static final String PARAM_INTERESTURISTICO_TEXT= "Objeto (PuntoInteresTuristico) en formato json necesario para poder realizar la operación";
	public static final String PARAM_AGRUPACIONCOMERCIAL_TEXT= "Objeto (AgrupacionComercial) en formato json necesario para poder realizar la operación";
	public static final String PARAM_LICENCIAACTIVIDAD_TEXT= "Objeto (LicenciaActividad) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TERRAZA_TEXT= "Objeto (Terraza) en formato json necesario para poder realizar la operación";
	public static final String PARAM_ORGANIGRAMA_TEXT= "Objeto (Organigrama) en formato json necesario para poder realizar la operación";
	public static final String PARAM_PLANTILLA_TEXT= "Objeto (Plantilla) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CUBO_PADRON_DISTRITO_EDAD_TEXT= "Objeto (Observación del Cubo de Datos de Padron: Distrito y Edad) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CONTRATO_PROCESS_TEXT= "Licitación (Contratos) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICABICICLETA_TEXT= "Objeto (BicicletaPublicaBicicleta) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICAESTACION_TEXT= "Objeto (BicicletaPublicaEstacion) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICAANCLAJE_TEXT= "Objeto (BicicletaPublicaAnclaje) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICAUSUARIO_TEXT= "Objeto (BicicletaPublicaUsuario) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICATRAYECTO_TEXT= "Objeto (BicicletaPublicaTrayecto) en formato json necesario para poder realizar la operación";
	
	public static final String PARAM_OBSERVES_ID_TEXT= "Código del elemento que se está obervando";
	public static final String PARAM_IS_HOSTED_BY_TEXT= "Código de la estación donde se encuentra el sensor";
	
	
	public static final String PARAM_SRID= "Sistema de referencia para generar las coordenadas proyectadas (X e Y)";
	
	
	//Información Superior
	public static final String EMAIL_CONTACTO = "contacto@ciudadesabiertas.es";
	public static final String NOMBRE_CONTACTO = "Ciudades Abiertas";
	public static final String WEB_CONTACTO = "https://ciudadesabiertas.es/contacto/index.html";	
	public static final String URL_WEB = "https://ciudadesabiertas.es";
	public static final String TITULO_API = "Ciudades Abiertas API";
	public static final String DESCRIPTION_API = "API sobre los conjuntos de datos del proyecto Ciudades Abiertas";
	public static final String VERSION_API = "1.0";
	public static final String URL_LICENCIA = "https://joinup.ec.europa.eu/software/page/eupl";
	public static final String TEXTO_LICENCIA = "Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es."; 
	
	//Subvencion agrupadas
	public static final String AGRUPADAS_OPER_PERMITIDAS= "SUM,COUNT,AVG,MAX,MIN";
}
