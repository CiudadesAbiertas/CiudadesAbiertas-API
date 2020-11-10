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

import com.opencsv.CSVWriter;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class Constants
{
	public static final String CONFIG_PROPERTIES = "config.properties";
	public static final String BUILD_NUMBER_PROPERTIES = "buildNumber.properties";
	public static final String PAQUETE_MODELO_CONJUNTOS_DATOS = "org.ciudadesabiertas.dataset.model";
	
	public static final String ENCODING_UTF8 = "UTF-8";
	
	public static final int defaultNumberValue = -1;
	
	public static final String API_JSON = "/api.json";
	public static final String SWAGGER="/swagger/";
	public static final String RESOURCES = "/resources/";
	public static final String API_DOCS = "/api-docs";
	
	public final static String NO_AUTH="NO_AUTH";
	public final static String BASIC_AUTH="BASIC";
	public final static String ADMIN_AUTH="ADMIN";
	
	public static final String ANONYMOUS_USER = "anonymous";
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String DATE_TIME_FORMAT_B = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_B = "dd-MM-yyyy";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String TIME_FORMAT_SHORT = "HH:mm";
	
	public static final String INTERNAL_ERROR = "Internal error";
	
	//Security Constants	
	
	public final static String ROLE_USER="ROLE_USER";	
	public final static String ROLE_ADMIN="ROLE_ADMIN";
	
	public final static String NAME_ADMIN="ADMIN";	
	
	//End of Security Constants
	
	//Ctes by Order Query
	public static final String SORT_SEPARATOR= ","; 
	public static final String SORT_ASC="+";
	public static final String SORT_DESC="-";
	
	
	//Metadata pagination
	public static final String SELF = "self";
	public static final String NEXT = "next";
	public static final String PREV = "prev";
	public static final String FIRST = "first";
	public static final String LAST = "last";
	public static final String STATUS = "status";
	public static final String FIELD = "field";
	public static final String PAGE = "page";	
	public static final String PAGESIZE = "pageSize";
	public static final String FIELDS =  "fields";
	public static final String RSQL_Q =  "q";
	public static final String SORT =  "sort";
	public static final String APIKEY = "apiKey";
	public static final String OBJETO = "obj";
	public static final String IDENTIFICADOR = "id";
	public static final String IDENTIFIER = "identifier";
	public static final String IKEY = "ikey";
	public static final String RECORD = "record";
	public static final String PAGERECORDS = "pageRecords";
	public static final String TOTALRECORDS = "totalRecords";
	public static final String RESPONSEDATE = "responseDate";
	public static final String CONTENT_MD5= "Content-MD5";
	public static final String IS_HOSTED_BY = "isHostedBy";
	public static final String OBSERVES_ID = "observesId";
	
	//METADATA Coordenadas
	public static final String STR_PROJECTEDCOORDINATES = "projectedCoordinates";
	public static final String STR_GEOGRAPHICCOORDINATES = "geographicCoordinates";
	
	public static final int defaultPage=1;
	public static final int defaultGroupByPageSize=100;
	public static final int maxGroupbyPageSize=200;
	
	public static final String SRID =  "srId";
	
	
	//Constante para el formato cadena al generar CSVs
	public static final String STRING_FORMAT = CSVWriter.DEFAULT_QUOTE_CHARACTER+"%s"+CSVWriter.DEFAULT_QUOTE_CHARACTER;
	
	//DRIVER CTE
	public static final String ORACLE = "oracle";
	public static final String MYSQL = "mysql";
	public static final String SQLSERVER = "sqlserver";
	//TYPE 
	public static final String TYPEDATE = "date";
	
	//Semilla para encriptar properties
	public static final String SEMILLA_PROPERTIES = "CIUDADES_ABIERTAS_2018";
	//Prefijo para propiedades encriptadas
	public static final String PREFIX_PROPERTY_ENCODED = "ENC(";
	public static final String SUFFIX_PROPERTY_ENCODED = ")";
	

	//peticiones.maximas.segundo.anonimas
	public static final String STR_NUMBER_REQUEST_PER_SECOND_ANONYMOUS="peticiones.maximas.segundo.anonimas";			

	
	//peticiones.maximas.minuto.identificadas
	public static final String STR_NUMBER_REQUEST_PER_SECOND_INDENTIFIED="peticiones.maximas.segundo.identificadas";	

	
	//numero de decimales para las conversiones de lat/long a x y
	public static final int NUM_DECIMALS_XY = 8;
	
	public static final String SUPPORTED_SRIDS = "EPSG:25830,EPSG:25829,EPSG:25828,EPSG:25831,EPSG:23030,EPSG:23029,EPSG:23028,EPSG:23031,EPSG:4258,EPSG:4230,EPSG:4326,EPSG:4082,EPSG:4083";
	public static final String SUPPORTED_XY_SRIDS="EPSG:25830,EPSG:25829,EPSG:25828,EPSG:25831,EPSG:23030,EPSG:23029,EPSG:23028,EPSG:23031,EPSG:4082,EPSG:4083";
	public static final String DEFAULT_XY_VALUE="EPSG:25830";
	public static final String SRID_DEFECTO= "";
	public static final String SRID_SWAGGER= "EPSG:4326";
	
	
	
	public static final String SUPPORTED_LAT_LON_SRIDS="EPSG:4258,EPSG:4230,EPSG:4326";
	public static final String DEFAULT_LAT_LON_VALUE="EPSG:4258";
	
	public static final String OPERACIONES_ESCRITURA="operaciones.escritura";
	public static final String OPERACIONES_HEAD="operaciones.head";
	public static final String OPERACIONES_AUTORIZACION="operaciones.autorization";
	
	
	
	//Propiedades conexion bbdd
	public static final String DB_DRIVER="db.driver";
	public static final String DB_JNDI_NAME="db.jndi.name";
	public static final String DB_URL="db.url";
	public static final String DB_SCHEMA="db.schema";
	public static final String DB_USER="db.user";
	public static final String DB_PASSWORD="db.password";
	public static final String DB_INITIAL_SIZE="db.initialSize";
	public static final String DB_MAX_ACTIVE="db.maxActive";
	public static final String DB_MAX_IDLE="db.maxIdle";
	public static final String DB_MIN_IDLE="db.minIdle";
	public static final String DB_VALIDATION_QUERY="db.validationQuery";
	public static final String DB_HIBERNATE_DIALECT="db.hibernate.dialect";	
	public static final String DB_HIBERNATE_SHOW_SQL="hibernate.show_sql";
	public static final String DB_HIBERNATE_FORMAT_SQL="hibernate.format_sql";
	public static final String DB_HIBERNATE_USE_SQL_COMMENTS="hibernate.use_sql_comments";
	public static final String DB_HIBERNATE_DEFAULT_SCHEMA="hibernate.default_schema";
	
	
	//Propiedad URIBase
	public static final String URI_BASE = "URIBase";
	public static final String APPNAME = "appname";
	public static final String APPSECRET = "appsecret";
	public static final String STR_NODO_PATTERN = "nodo.pattern";

	//Propiedad foreign key
	public static final String STR_ACTVE_FOREIGN_KEY="claves.foraneas";

	
	public static final String mimeHTML = "text/html";
	public static final String mimeJSON = "application/json";
	public static final String mimeXML = "application/xml";
	public static final String mimeCSV = "text/csv";
	public static final String mimeGEOJSON = "application/geo+json";
	public static final String mimeGEORSS = "application/atom+xml";
	
	
	public static final int maxSizeURL=2000;
	
	//Ctes para la Geoposición
	public static final String SRSWGS84 = "wgs84";
	public static final String SRSETRS89 = "utm30n_etrs89";
	public static final String SRSUTM30N = "utm30n";
	
	//Nuevas Ctes detectadas en PMD
	public static final String EXT_HTML = ".html";
	public static final String EXT_JSON = ".json";
	public static final String EXT_XML = ".xml";
	
	//Propiedades para la carga de peticiones por ficheros
	public static final String STR_REQUEST_PUBLIC_AUTH = "peticiones.identificadas.public_auth";
	public static final String STR_REQUEST_BASIC_AUTH  = "peticiones.identificadas.basic_auth";
	public static final String STR_REQUEST_ADMIN_AUTH  = "peticiones.identificadas.admin_auth";
	
	//Valor para los tipos de campos a filtrar en el search
	public static final String TYPE_STRING_CLASS = "java.lang.String";
	public static final String TYPE_CLASS_CLASS = "java.lang.Class";
	public static final String TYPE_DATE_CLASS = "java.util.Date";

	//Authorities
	public static final String AUTORITHY_CONSULTA = "consulta";
	
	//URL FIELD
	public static final String FIELD_URL = "url";
	
	//InitialContext by JNDI Conection
	public static final String STR_ENV_JNDI_CONTEXT = "db.jndi.env";
	public static final String ENV_JNDI_CONTEXT = "java:comp/env/";
	
	//BASIC OPPS
	public static final String BASIC_OPERATION_ADD = "add";
	public static final String BASIC_OPERATION_DELETE = "delete";
	public static final String BASIC_OPERATION_UPDATE = "update";
	
	public static final String STR_XY_ETRS89_EPSG = "xy.value.epsg";
	public static final String STR_LAT_LON_ETRS89_EPSG = "lat_lon.value.epsg";
	
	public static final String AJAX_PARAM = "_";
	
	public static final String X = "X";
	public static final String Y = "Y";
	public static final String FinX = "finX";
	public static final String FinY = "finY";
	public static final String XETRS89 = "xETRS89";
	public static final String YETRS89 = "yETRS89";
	public static final String XETRS89Fin = "xETRS89Fin";
	public static final String YETRS89Fin = "yETRS89Fin";
	public static final String METERS = "meters";
	public static final String DISTANCE = "distance";
	
	
	public static final String FORMATO_HTML = "html";
	public static final String FORMATO_JSON = "json";
	public static final String FORMATO_XML = "xml";
	public static final String FORMATO_CSV = "csv";
	public static final String FORMATO_RDF = "rdf";
	public static final String FORMATO_TTL = "ttl";
	public static final String FORMATO_JSONLD = "jsonld";
	public static final String FORMATO_N3 = "n3";
	public static final String FORMATO_GEOJSON = "geojson";
	public static final String FORMATO_GEORSS = "georss";
	public static final String FORMATO_ODATA = "odata";
	
	public static final String[] FORMATOS_EXTENSIONES= {FORMATO_HTML,FORMATO_JSON,FORMATO_XML,FORMATO_CSV,FORMATO_RDF,FORMATO_TTL,FORMATO_JSONLD,FORMATO_N3, FORMATO_GEOJSON,FORMATO_GEORSS,FORMATO_ODATA };
	
	public static final String CONTENT_TYPE_JSON_UTF8 = "application/json;charset=UTF-8";
	public static final String CONTENT_TYPE_XML_UTF8 = "application/xml;charset=UTF-8";
	public static final String CONTENT_TYPE_HTML_XML_UTF8 = "application/xhtml+xml;charset=UTF-8";
	public static final String CONTENT_TYPE_TEXT_UTF8 = "text/xml;charset=UTF-8";
	
	public static final String MEDIA_TYPE_TEXT = mimeHTML;
	public static final String MEDIA_TYPE_CSV = mimeCSV;
	public static final String MEDIA_TYPE_GEOJSON = mimeGEOJSON;
	public static final String MEDIA_TYPE_GEORSS = mimeGEORSS;
	
	
	
	public static final String [] FIELDS_GEO_NO_PROJECTION_INCLUDE = {"latitud","longitud","xETRS89","yETRS89"};
	
	public static final String STR_PATH_TEMPLATE="path.template.html";
	public static final String DEFAUL_PATH_TEMPLATE = "/WEB-INF/templates/";
	
	public static final String HEADDER_ACCEPT = "Accept";
	public static final String HEADER_LOCATION = "Location";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_REFERER = "Referer";
	
	public static final String STR_HTTPS = "https";
	public static final String STR_ACTIVE_ON = "on";
	public static final String STR_ACTIVE_TRUE = "true";
	public static final String STR_INACTIVE_FALSE = "false";
	
	public static final String STR_CONTEXTO = "contexto";
	public static final String STR_PAGE_MAX = "pagina.maximo";
	public static final String STR_PAGE_DEFAULT = "pagina.defecto";
	public static final String STR_HTML_TITLE = "htmlTitle"; 
	
	public static final String STR_RSQL_LOG_ACTIVE = "rsql.log.active";
	
	public static final String SWAGGER_V2_API_DOCS = "/v2/api-docs";
	public static final String SWAGGER_INDEX = "swagger/index.html";
	
	public static final int  NO_PAGINATION = 99999;
	public static final int  NO_PAGINATION_PAGE_1 = NO_PAGINATION-1;
	
	
	public static final String BASE_DE_DATOS_POR_DEFECTO = "Base de datos  por defecto";	
	public static final String DEFAULT_DATABASE = "default";
	
	public static final String EXCLUSION_BUILD_NUMBER="buildNumber";
}

