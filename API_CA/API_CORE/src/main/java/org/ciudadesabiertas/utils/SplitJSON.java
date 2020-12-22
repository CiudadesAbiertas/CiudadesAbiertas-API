package org.ciudadesabiertas.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */


/*Clase para extraer elementos de una coleccion de GEOJSON y dividirlos en tantos ficheros como elementos posea*/
public class SplitJSON {

public static void main(String[] args) {

  String filePath = "D:\\git\\CiudadesAbiertas\\API_CA\\API_WEB\\src\\main\\webapp\\WEB-INF\\territorio\\seccionCensal\\25830\\alcobendas.geojson";
  //String filePath = "D:\\git\\CiudadesAbiertas\\API_CA\\API_WEB\\src\\main\\webapp\\WEB-INF\\territorio\\seccionCensal\\4326\\alcobendas.geojson";
  
  String fileContent = null;
  JSONParser parser = new JSONParser();
  JSONObject collection= null;
  JSONArray features= null;
  
  String fileStarts="{\r\n"
  	+ "\"type\": \"FeatureCollection\",\r\n"
  	+ "\"crs\": { \"type\": \"name\", \"properties\": { \"name\": \"urn:ogc:def:crs:EPSG::25830\" } },                                                                                \r\n"
  	+ "\"features\": [";
  
  String fileEnds="]\r\n"
  	+ "}";
  
  if (args.length == 1) {
	filePath = args[0];
  }

  

  File dataFile = new File(filePath);
  
  try {
	fileContent = FileUtils.readFileToString(dataFile,"utf8");
  } catch (IOException e) {
		e.printStackTrace();
		return;
  }

  try {
	 collection=(JSONObject) parser.parse(fileContent);
	 features=(JSONArray) collection.get("features");
  } catch (ParseException e) {
	e.printStackTrace();
	return;
  }  

  
  System.out.println(features.size()+" elements");
  
  for (int i=0;i<features.size();i++)
  {
	JSONObject tempObject = (JSONObject)features.get(i);
	
	JSONObject properties = (JSONObject) tempObject.get("properties");
	
	String fileName=(String) properties.get("CUSEC");
	
	fileName="territorio_seccion_"+fileName;
	
	String tempFileContent=fileStarts+tempObject.toJSONString()+fileEnds;
	
	File tempFile=new File(dataFile.getParent()+File.separator+fileName+".geojson");
	
	try {
	  FileUtils.writeStringToFile(tempFile, tempFileContent, "utf8");
	} catch (IOException e) {
	   e.printStackTrace();
	}
  }
  
}

}
