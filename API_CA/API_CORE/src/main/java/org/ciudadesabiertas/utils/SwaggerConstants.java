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
	public static final String FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO = "application/json, application/xml, text/csv, application/ld+json, text/turtle, application/rdf+xml, text/n3";
	public static final String FORMATOS_CONSULTA_RESPONSE_NO_HTML = FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO + ", application/geo+json, application/atom+xml";
	public static final String FORMATOS_CONSULTA_RESPONSE_FULL_WITHOUT_GEO = FORMATOS_CONSULTA_RESPONSE_NO_HTML_WITHOUT_GEO+ ", text/html";
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
	public static final String PARAM_Q= "Parámetro para especificar una consulta de tipo RSQL. <a href='https://github.com/CiudadesAbiertas/CiudadesAbiertas-API/tree/master/API_CA#par%C3%A1metro-de-consultas-rsql' target='rsqlQuery'>Ejemplos de consultas RSQL</a>";
	public static final String PARAM_FIELDS= "Parámetro para especificar que atributos se quieren solicitar. Ejemplo: id,title";
	public static final String PARAM_FIELDS_GROUP_BY= "Parámetro para especificar que atributos se quieren solicitar. Ejemplo: title,count(id)";
	public static final String PARAM_FIELD = "Parámetro para especificar que atributo se quiere solicitar.  Ejemplo: title";
	public static final String PARAM_PAGE = "Parámetro para especificar la página que se quiere solicitar";
	public static final String PARAM_PAGESIZE = "Parámetro para especificar el tamaño de página";
	public static final String PARAM_SORT = "Parámetro para espeficicar el orden de la consulta";
	public static final String PARAM_METERS = "Parámetro para espeficicar los metros del radio de búsqueda";
	public static final String PARAM_CONDICION_ADICCIONAL = "Parámetro para agregar una condición adicional a la petición. Ejemplo: title = 'Texto de prueba 1'";
	public static final String PARAM_WHERE = "Parámetro para especificar un filtro sobre los campos";
	public static final String PARAM_GROUP = "Parámetro para espeficicar el campo o los campos por lo que se agrupará la búsqueda";
	public static final String PARAM_HAVING = "Parámetro para especificar el filtro sobre las operaciones de agrupación. Ejemplo COUNT(id) > 5";
	public static final String PARAM_ID= "Parámetro para especificar el identificador del registro";
	public static final String PARAM_ID_TEXT= "id del Objeto necesario para ejecutar la operación";
	//Subvencion
	public static final String PARAM_SUBVENCION_TEXT= "Objeto (Subvención) en formato json necesario para poder realizar la operación";
	public static final String PARAM_SUBVENCION_ORGANIZATION_TEXT= "Objeto (Organización Subvención) en formato json necesario para poder realizar la operación";
	
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
	public static final String PARAM_REL_LINEA_INCIDENCIA= "Objeto (RelLineaIncidencia) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CUBO_PADRON_DISTRITO_EDAD_TEXT= "Objeto (Observación del Cubo de Datos de Padron: Distrito y Edad) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CONTRATO_PROCESS_TEXT= "Licitación (Contratos) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICABICICLETA_TEXT= "Objeto (BicicletaPublicaBicicleta) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICAESTACION_TEXT= "Objeto (BicicletaPublicaEstacion) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICAANCLAJE_TEXT= "Objeto (BicicletaPublicaAnclaje) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICAUSUARIO_TEXT= "Objeto (BicicletaPublicaUsuario) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BICICLETAPUBLICATRAYECTO_TEXT= "Objeto (BicicletaPublicaTrayecto) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TRAFICODISPMEDI_TEXT= "Objeto (TraficoDispositivoMedicion) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TRAFICOEQUIPO_TEXT= "Objeto (TraficoEquipo) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TRAFICOINCIDENCIA_TEXT= "Objeto (TraficoIncidencia) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TRAFICOOBSERVACION_TEXT= "Objeto (TraficoObservacion) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TRAFICOOBSEDISP_TEXT= "Objeto (TraficoObservacionDispostivo) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TRAFICOPORPINTE_TEXT= "Objeto (TraficoProperInterval) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TRAFICOTRAMO_TEXT= "Objeto (TraficoTramo) en formato json necesario para poder realizar la operación";
	public static final String PARAM_TRAFICOTRAMOVIA_TEXT= "Objeto (TraficoTramoVia) en formato json necesario para poder realizar la operación";
	public static final String PARAM_INCIDENCIA_TEXT= "Objeto (Incidencia) en formato json necesario para poder realizar la operación";
	public static final String PARAM_OBSERVES_ID_TEXT= "Código del elemento que se está obervando";
	public static final String PARAM_IS_HOSTED_BY_TEXT= "Código de la estación donde se encuentra el sensor";
	
	//Convenio
	public static final String PARAM_CONVENIO_TEXT= "Objeto (Convenio) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CONVENIODOCUMENTO_TEXT= "Objeto (Documento Convenio) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CONVENIOORGANIZATION_TEXT= "Objeto (Organización Convenio) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CONVENIOSUSCENTIDAD_TEXT= "Objeto (Suscripción Entidad Convenio) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CONVENIOSRELFIRAYTO_TEXT= "Objeto (Firmante Ayuntamiento Convenio) en formato json necesario para poder realizar la operación";
	public static final String PARAM_CONVENIOSRELFIRENTIDAD_TEXT= "Objeto (Firmante Entidad Convenio) en formato json necesario para poder realizar la operación";
	
	public static final String PARAM_SRID= "Sistema de referencia para generar las coordenadas";
	
	public static final String PARAM_BUS_AUTHORITY_TEXT= "Objeto (Authority) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BUS_PARADA_TEXT= "Objeto (Parada) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BUS_LINEA_TEXT= "Objeto (Línea) en formato json necesario para poder realizar la operación";
	public static final String PARAM_BUS_JOURNEYPATTERN_TEXT = "Objeto (Journeypattern) en formato json necesario para poder realizar la operación";
	
	
	public static final String PARAM_BUS_STOP_POINT_IN_JOURNEY_PATTERN_TEXT = "Objeto (StopPointInJourneyPattern) en formato json necesario para poder realizar la operación";
	

	//Ejemplos id
	public static final String PARAM_ID_SECCION_CENSAL = " Ejemplo: 2800601001";
	public static final String PARAM_ID_BARRIO = " Ejemplo: 28006011";
	public static final String PARAM_ID_DISTRITO = " Ejemplo: 2800601";
	public static final String PARAM_ID_MUNICIPIO = " Ejemplo: 28006";
	public static final String PARAM_ID_PROVINCIA = " Ejemplo: Madrid";
	public static final String PARAM_ID_AUTONOMIA = " Ejemplo: Comunidad-Madrid";
	public static final String PARAM_ID_PAIS = " Ejemplo: España";
	public static final String PARAM_ID_SUBVENCION = " Ejemplo: S0001";	
	public static final String PARAM_ID_LOCAL_COMERCIAL = " Ejemplo: 270002391";
	public static final String PARAM_ID_AGRUPACION_COMERCIAL = " Ejemplo: 99000214";
	public static final String PARAM_ID_LICENCIA = " Ejemplo: 270002391-106-2003-01539";
	public static final String PARAM_ID_TERRAZA = " Ejemplo: 1355";
	public static final String PARAM_ID_AGENDA_CULTURAL = " Ejemplo: AG0001";
	public static final String PARAM_ID_EQUIPAMIENTO = " Ejemplo: EQ0001";
	public static final String PARAM_ID_APARCAMIENTO = " Ejemplo: EQAP0001";
	public static final String PARAM_ID_INSTALACION_DEPORTIVA = " Ejemplo: EQID0001";
	public static final String PARAM_ID_PUNTOS_WIFI = " Ejemplo: EQPW0001";
	public static final String PARAM_ID_AVI_QUE_SUG = " Ejemplo: AQSA0002";
	public static final String PARAM_ID_CALIDAD_AIRE_ESTACION = " Ejemplo: STAT04";
	public static final String PARAM_ID_CALIDAD_AIRE_OBSERVACION = " Ejemplo: OBS0001";
	public static final String PARAM_ID_CALIDAD_AIRE_SENSOR = " Ejemplo: SENSOR0001";
	public static final String PARAM_ID_CALIDAD_AIRE_SENSOR_OBS = " Ejemplo: dioxidoDeAzufre";
	public static final String PARAM_ID_ORGANIGRAMA = " Ejemplo: 10100";
	public static final String PARAM_ID_CALLEJERO_PORTAL = " Ejemplo: PORTAL000001";
	public static final String PARAM_ID_CALLEJERO_TRAMO = " Ejemplo: TRA000001";
	public static final String PARAM_ID_CALLEJERO_VIA = " Ejemplo: 114600";
	public static final String PARAM_ID_PUNTO_INTERES_TURISTICO = " Ejemplo: PIT0001";
	public static final String PARAM_ID_ALOJAMIENTO = " Ejemplo: ALJ0001";
	public static final String PARAM_ID_MONUMENTO = " Ejemplo: PITM0001";
	public static final String PARAM_ID_TRAMITE = " Ejemplo: TR0001";
	public static final String PARAM_ID_CONTRATOS_AWARD = " Ejemplo: AW1";
	public static final String PARAM_ID_CONTRATOS_ITEM = " Ejemplo: IT1";
	public static final String PARAM_ID_CONTRATOS_LOT = " Ejemplo: LT1";
	public static final String PARAM_ID_CONTRATOS_ITEM_LOT = " Ejemplo: 00000000000001";
	public static final String PARAM_ID_CONTRATOS_ORGANIZATION = " Ejemplo: A28021350";
	public static final String PARAM_ID_CONTRATOS_TENDER = " Ejemplo: TN1";
	public static final String PARAM_ID_CONTRATOS_ITEM_TENDER = " Ejemplo: 00000000000001";
	public static final String PARAM_ID_CONTRATOS_PROCESS = " Ejemplo: 300-2018-00524";
	public static final String PARAM_ID_AGENDA_EVENTO = " Ejemplo: AGM0001";
	public static final String PARAM_ID_AGENDA_ROL = " Ejemplo: AGMROL0001";
	public static final String PARAM_ID_AGENDA_DOCUMENTO = " Ejemplo: AGMDOC0001";
	public static final String PARAM_ID_CUBE_DSD = " Ejemplo: poblacionPorEdadGruposQuinquenales";
	public static final String PARAM_ID_CUBE_DIMENSION_ID = " Ejemplo: edadGruposQuinquenales";
	public static final String PARAM_ID_CUBE_DIMENSION_VALUE = " Ejemplo: 05-a-09";
	public static final String PARAM_ID_CUBE_MEASURE_ID = " Ejemplo: tasaNatalidad";
	public static final String PARAM_ID_PADRON_EDAD_QUINQUENAL = " Ejemplo: obs1";
	public static final String PARAM_ID_PADRON_EDAD = " Ejemplo: obs1";
	public static final String PARAM_ID_PADRON_ESTUDIOS = " Ejemplo: obs12";
	public static final String PARAM_ID_PADRON_INDICADORES = " Ejemplo: obs11";
	public static final String PARAM_ID_PADRON_NACIONALIDAD = " Ejemplo: obs11";
	public static final String PARAM_ID_PADRON_PAIS_NACIMIENTO = " Ejemplo: obs1";
	public static final String PARAM_ID_PADRON_PROCEDENCIA = " Ejemplo: obs1";
	public static final String PARAM_ID_CONVENIO = " Ejemplo: CONV001";
	public static final String PARAM_ID_CONVENIODOCUMENTO = " Ejemplo: CONVDOC001";
	public static final String PARAM_ID_CONVENIOORGANIZATION = " Ejemplo: ORG0001";
	public static final String PARAM_ID_CONVENIOSUSENTIDAD = " Ejemplo: CONVSUSENT001";
	public static final String PARAM_ID_CONVENIOSRELFIRAYTO = " Ejemplo: CONVRELFIRMAYTO001";
	public static final String PARAM_ID_CONVENIOSRELFIRENTIDAD = " Ejemplo: CONVRELFIRMENTIDAD001";
	public static final String PARAM_ID_TRAFICO_DISP_MEDI = " Ejemplo: TRAFDISMED01";
	public static final String PARAM_ID_TRAFICO_EQUIPO = " Ejemplo: TRAFEQUI01";
	public static final String PARAM_ID_TRAFICO_INCIDENCIA = " Ejemplo: TRAFINCI01";
	public static final String PARAM_ID_TRAFICO_OBSERACION = " Ejemplo: TRAFOBS01";
	public static final String PARAM_ID_TRAFICO_OBSEDISP = " Ejemplo: TRAFOBSDIPS01";
	public static final String PARAM_ID_TRAFICO_PROP_INTE = " Ejemplo: 468a5a615f32d0dbee5937f86acf58b3";
	public static final String PARAM_ID_TRAFICO_PROP_MEDI = " Ejemplo: carga";
	public static final String PARAM_ID_TRAFICO_TRAMO = " Ejemplo: TRAFTRAM01";
	public static final String PARAM_ID_TRAFICO_TRAM_VIA = " Ejemplo: TRAFTRAVIA01";
	public static final String PARAM_ID_BUS_AUTHORITY = " Ejemplo: TRAFTRAVIA01";
	public static final String PARAM_ID_BUS_LINEA = "Ejemplo: 138";
	public static final String PARAM_ID_BUS_INCIDENCIA = "Ejemplo: BUSINCI01";
	public static final String PARAM_ID_BUS_REL_LINEA_INCIDENCIA = "Ejemplo: RELINCI01";
	public static final String PARAM_ID_BUS_JOURNEYPATTERN = "Ejemplo: JPAT01"; 	
	public static final String PARAM_ID_BUS_STOP_POINT_IN_JOURNEY_PATTERN = "Ejemplo: 6a2-1918";
	public static final String PARAM_ID_SUBVENCION_ORGANIZATION = " Ejemplo: ORG0001";
	
	
	//Ejemplos condicionAdicional
	public static final String PARAM_COND_ADI_BARRIO = " Ejemplo: distrito = 2800601";
	
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
	
	
	public static final String VOCABULARIO_A_HREF_END = "' class='enlaceVocabulario' target='vocabulario'>Vocabulario</a>";
	public static final String VOCABULARIO_A_HREF = " <a href='";
	
	
	

}
