package org.ciudadesAbiertas.rdfGeneratorZ;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Funciones {
	private static final Logger logger = LoggerFactory.getLogger(Funciones.class);
	
	public static  SimpleDateFormat formateadorFechaHora= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public static  SimpleDateFormat formateadorFechaHoraSinT= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static  SimpleDateFormat formateadorFecha= new SimpleDateFormat("yyyy-MM-dd");
	
	public static  SimpleDateFormat formateadorHora= new SimpleDateFormat("HH:mm:ss");
	
	public static String getGetterMethodName(String name) {
		return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	public static Object retrieveObjectValue(Object obj, String property) {
		if (property.contains(".")) {
			// we need to recurse down to final object
			String props[] = property.split("\\.");
			try {
				Object ivalue = null;
				if (Map.class.isAssignableFrom(obj.getClass())) {
					Map map = (Map) obj;
					ivalue = map.get(props[0]);
				} else {
					Method method = obj.getClass().getMethod(getGetterMethodName(props[0]));
					ivalue = method.invoke(obj);
				}
				
				if (ivalue == null) {
					return null;
				}
				return retrieveObjectValue(ivalue, property.substring(props[0].length() + 1));
			} catch (Exception e) {
				// throw new
				// InvalidImplementationException("Failed to retrieve value for "+property,
				// e);
				return null;
			}
		} else {
			// let's get the object value directly
			try {
				if (Map.class.isAssignableFrom(obj.getClass())) {
					Map map = (Map) obj;
					return HbUtils.deproxy(map.get(property));
				} else {
					Method method = obj.getClass().getMethod(getGetterMethodName(property));
					return HbUtils.deproxy(method.invoke(obj));
				}
			} catch (Exception e) {
				// throw new
				// InvalidImplementationException("Failed to retrieve value for "+property,
				// e);
				return null;
			}
		}
	}


	public static String generarHash(String cadena) {
		return "" + cadena.hashCode();
	}
	
	public static String urlify(String chain, int level) {
		
		if (chain==null)
			return null;
		
		if (level<=0)
		{
			chain = Normalizer.normalize(chain, Normalizer.Form.NFD);
			chain = chain.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			return chain;
		}
		if (level==1)
		{	
			chain = chain.replace(" de ", " ");
			chain = chain.replace(" ", "-");			
			return chain;
		}
		//CMG: Ya hay modelos utilizando este nivel 2 con esta salida
		if (level==2)
		{	
			chain = chain.trim().toLowerCase();
			
			chain = chain.replace(" de ", " ");
			
			while (chain.indexOf("  ") >= 0)
				chain = chain.replace("  ", " ");
			chain = chain.replace(" ", "-");
			
			chain = chain.replaceAll("\\s+", "_");
			
	
			chain = Normalizer.normalize(chain, Normalizer.Form.NFD);
			chain = chain.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			
			
			try {
				chain = URLEncoder.encode(chain, "UTF-8");
			} catch (UnsupportedEncodingException e) {			
				logger.error("Unsuported encoding",e);
			}
			
	
			while (chain.indexOf("--") >= 0)
				chain = chain.replace("--", "-");
	
			return chain;
		}
		//CMG Solo eliminamos acentos
		if (level==3)
		{	
			chain = chain.replace(" de ", " ");
			chain = chain.replace(" ", "-");	
			//Y los acentos se eliminan no la ñ
			chain = eliminaAcentos(chain);
			return chain;
		}
		//El resto es igual al 2 pero tenemos que fijar el 2 porque se esta usando.
		else 
		{
			chain = chain.trim().toLowerCase();
			
			chain = chain.replace(" de ", " ");
			
			while (chain.indexOf("  ") >= 0)
				chain = chain.replace("  ", " ");
			chain = chain.replace(" ", "-");
			
			chain = chain.replaceAll("\\s+", "_");
			
	
			chain = Normalizer.normalize(chain, Normalizer.Form.NFD);
			chain = chain.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			
			
			try {
				chain = URLEncoder.encode(chain, "UTF-8");
			} catch (UnsupportedEncodingException e) {			
				logger.error("Unsuported encoding",e);
			}
			
	
			while (chain.indexOf("--") >= 0)
				chain = chain.replace("--", "-");
	
			return chain;
		}

	}
	
	
	public static void replaceAll(StringBuilder builder, String from, String to)
	{
	    int index = builder.indexOf(from);
	    while (index != -1)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); 
	        index = builder.indexOf(from, index);
	    }
	}
	
	
	public static void main(String[] args)
	{
		//String[] urifyTest= {"España","Emiratos Árabes Unidos", "Bosnia y Herzegovina "};
		
		String[] urifyTest= {"Andorra",
				"Emiratos Árabes Unidos",
				"Afganistán",
				"Antigua y Barbuda",
				"Anguila",
				"Albania",
				"Armenia",
				"Antillas Neerlandesas",
				"Angola",
				"Antártida",
				"Argentina",
				"Samoa Americana",
				"Austria",
				"Australia",
				"Aruba",
				"Islas Áland",
				"Azerbaiyán",
				"Bosnia y Herzegovina",
				"Barbados",
				"Bangladesh",
				"Bélgica",
				"Burkina Faso",
				"Bulgaria",
				"Bahréin",
				"Burundi",
				"Benin",
				"San Bartolomé",
				"Bermudas",
				"Brunéi",
				"Bolivia",
				"Brasil",
				"Bahamas",
				"Bhután",
				"Isla Bouvet",
				"Botsuana",
				"Belarús",
				"Belice",
				"Canadá",
				"Islas Cocos",
				"República Centro-Africana",
				"Congo",
				"Suiza",
				"Costa de Marfil",
				"Islas Cook",
				"Chile",
				"Camerún",
				"China",
				"Colombia",
				"Costa Rica",
				"Cuba",
				"Cabo Verde",
				"Islas Christmas",
				"Chipre",
				"República Checa",
				"Alemania",
				"Yibuti",
				"Dinamarca",
				"Domínica",
				"República Dominicana",
				"Argel",
				"Ecuador",
				"Estonia",
				"Egipto",
				"Sahara Occidental",
				"Eritrea",
				"España",
				"Etiopía",
				"Finlandia",
				"Fiji",
				"Islas Malvinas",
				"Micronesia",
				"Islas Faroe",
				"Francia",
				"Gabón",
				"Reino Unido",
				"Granada",
				"Georgia",
				"Guayana Francesa",
				"Guernsey",
				"Ghana",
				"Gibraltar",
				"Groenlandia",
				"Gambia",
				"Guinea",
				"Guadalupe",
				"Guinea Ecuatorial",
				"Grecia",
				"Georgia del Sur e Islas Sandwich del Sur",
				"Guatemala",
				"Guam",
				"Guinea-Bissau",
				"Guayana",
				"Hong Kong",
				"Islas Heard y McDonald",
				"Honduras",
				"Croacia",
				"Haití",
				"Hungría",
				"Indonesia",
				"Irlanda",
				"Israel",
				"Isla de Man",
				"India",
				"Territorio Británico del Océano Índico",
				"Irak",
				"Irán",
				"Islandia",
				"Italia",
				"Jersey",
				"Jamaica",
				"Jordania",
				"Japón",
				"Kenia",
				"Kirguistán",
				"Camboya",
				"Kiribati",
				"Comoros",
				"San Cristóbal y Nieves",
				"Corea del Norte",
				"Corea del Sur",
				"Kuwait",
				"Islas Caimán",
				"Kazajstán",
				"Laos",
				"Líbano",
				"Santa Lucía",
				"Liechtenstein",
				"Sri Lanka",
				"Liberia",
				"Lesotho",
				"Lituania",
				"Luxemburgo",
				"Letonia",
				"Libia",
				"Marruecos",
				"Mónaco",
				"Moldova",
				"Montenegro",
				"Madagascar",
				"Islas Marshall",
				"Macedonia",
				"Mali",
				"Myanmar",
				"Mongolia",
				"Macao",
				"Martinica",
				"Mauritania",
				"Montserrat",
				"Malta",
				"Mauricio",
				"Maldivas",
				"Malawi",
				"México",
				"Malasia",
				"Mozambique",
				"Namibia",
				"Nueva Caledonia",
				"Níger",
				"Islas Norkfolk",
				"Nigeria",
				"Nicaragua",
				"Países Bajos",
				"Noruega",
				"Nepal",
				"Nauru",
				"Niue",
				"Nueva Zelanda",
				"Omán",
				"Panamá",
				"Perú",
				"Polinesia Francesa",
				"Papúa Nueva Guinea",
				"Filipinas",
				"Pakistán",
				"Polonia",
				"San Pedro y Miquelón",
				"Islas Pitcairn",
				"Puerto Rico",
				"Palestina",
				"Portugal",
				"Islas Palaos",
				"Paraguay",
				"Qatar",
				"Reunión",
				"Rumanía",
				"Serbia y Montenegro",
				"Rusia",
				"Ruanda",
				"Arabia Saudita",
				"Islas Solomón",
				"Seychelles",
				"Sudán",
				"Suecia",
				"Singapur",
				"Santa Elena",
				"Eslovenia",
				"Islas Svalbard y Jan Mayen",
				"Eslovaquia",
				"Sierra Leona",
				"San Marino",
				"Senegal",
				"Somalia",
				"Surinam",
				"Santo Tomé y Príncipe",
				"El Salvador",
				"Siria",
				"Suazilandia",
				"Islas Turcas y Caicos",
				"Cha",
				"Territorios Australes Franceses",
				"Togo",
				"Tailandia",
				"Tanzania",
				"Tayikistán",
				"Tokelau",
				"Timor-Leste",
				"Turkmenistán",
				"Túnez",
				"Tonga",
				"Turquía",
				"Trinidad y Tobago",
				"Tuvalu",
				"Taiwán",
				"Ucrania",
				"Uganda",
				"Estados Unidos de América",
				"Uruguay",
				"Uzbekistán",
				"Ciudad del Vaticano",
				"San Vicente y las Granadinas",
				"Venezuela",
				"Islas Vírgenes Británicas",
				"Islas Vírgenes de los Estados Unidos de América",
				"Vietnam",
				"Vanuatu",
				"Wallis y Futuna",
				"Samoa",
				"Yemen",
				"Mayotte",
				"Sudáfrica"};
		for (String t:urifyTest)
		{
//			System.out.println("-----------------urilevel 0-------------------");
//			System.out.println(t+" -> "+urlify(t,0));
//			System.out.println("-----------------urilevel 1-------------------");
//			System.out.println(t+" -> "+urlify(t,1));
//			System.out.println("-----------------urilevel 2-------------------");
			//System.out.println(t+" -> "+urlify(t,2));
			System.out.println( eliminaAcentos(urlify(t,3)).trim());
			//System.out.println("----------------------------------------------");
			
		}
	}
	
	public static String eliminaAcentos(String cadena)
	{
		Map<String,String> replace=new HashMap<String,String>();
		replace.put("á", "a");
		replace.put("é", "e");
		replace.put("í", "i");
		replace.put("ó", "o");
		replace.put("ú", "u");
		replace.put("Á", "A");
		replace.put("É", "E");
		replace.put("Í", "I");
		replace.put("Ó", "O");
		replace.put("Ú", "U");
		for (String c:replace.keySet())
		{
			cadena=cadena.replace(c, replace.get(c));	
		}
		return cadena;
	}
}

