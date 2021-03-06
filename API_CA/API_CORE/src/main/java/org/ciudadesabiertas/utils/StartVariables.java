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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class StartVariables
{
		public static int defaultPageSize=50;
		public static int maxPageSize=500;	
		public static int MAX_NUMBER_REQUEST_PER_SECOND_ANONYMOUS=10;
		public static int MAX_NUMBER_REQUEST_PER_SECOND_INDENTIFIED=15;
		public static String NODO_PATTERN = "A";
		public static String HTML_TITLE = "Ciudades Abiertas";
		//Mapa donde se encuentran todas las clases con sus columnas en java y columnas en tabla
		public static Map<String, Map<String,String>> mapaClasesColumnas;
		
		//parametro para la configuración de las coordenadas geograficas valor por defecto (25830)
		public static String SRID_XY_APP="EPSG:25830";
		public static String SRID_LAT_LON_APP="EPSG:4326";
		
		//Lista con todas las URLs de Listado disponibles
		public static List<String> listURIs;
		
		//PATH TEMPLATE (Constants.STR_PATH_MODULE_TEMPLATE,valor)
		public static String PATH_TEMPLATE = Constants.DEFAUL_PATH_TEMPLATE;
		
		public static boolean RSQL_LOG_ACTIVE = false;
		
		public static String context = "";
		public static String uriBase = "";
		public static String serverPort = "";
		public static String schema = "";
		
		//Lista con todos los controllers cargados de en la aplicación
		public static List<String> listControllers;
		
		//Conversor de JSON utilizado en el sistema
		public static MappingJackson2HttpMessageConverter jsonConverter;
		
		//VBLE para el control de las FK
		public static boolean activeFK = true;
		
		//db.schema		
		public static String db_schema = "";
		
		//Variables SKOS Presupuestos
		public static String presupuestosUrlSkosClasificacionEconomicaIngreso="http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/economica-ingreso";
		public static String presupuestosUrlSkosClasificacionEconomicaGasto="http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/economica-gasto/madrid/";
		public static String presupuestosUrlSkosClasificacionPrograma="http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/programas-gasto/madrid/";
		public static String presupuestosUrlSkosClasificacionOrganica="http://vocab.linkeddata.es/datosabiertos/kos/hacienda/presupuesto/organica/madrid/";

		public static Map<String,String> databaseTypes = new HashMap<String,String>();
		
		public static Map<String,String> dbSQLServeSchemas = new HashMap<String,String>();
		
		public static Map<String,String> errorDatabaseTypes = new HashMap<String,String>();
}
