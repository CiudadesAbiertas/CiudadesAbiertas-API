package org.ciudadesabiertas.dataset.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.ciudadesabiertas.dataset.model.Linea;
import org.ciudadesabiertas.utils.Territorio;
import org.ciudadesabiertas.utils.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class LineStringUtil {

	
	private static final Logger log = LoggerFactory.getLogger(LineStringUtil.class);
	
	public static Map<String,JSONObject> readGeoJSON(String filePath, String propertyIdentifier)
	{
		Map<String,JSONObject> geojsonMap=new HashMap<String,JSONObject>();
				
		
		File resource;
		try {
			resource = new ClassPathResource(filePath).getFile();			
			File[] listFilesEPSGs = resource.listFiles();
			for (File directory:listFilesEPSGs)
			{
				if (directory.isDirectory())
				{
					File[] listFilesGEOJSON = directory.listFiles();
					for (File actualGeojson:listFilesGEOJSON)
					{
						String fileContent=FileUtils.readFileToString(actualGeojson, "utf-8");
						JSONObject geometryObject=Util.stringToJSONObject(fileContent);
						
						JSONArray geometryList=(JSONArray) geometryObject.get("features");
						
						for (int i=0;i<geometryList.size();i++)
						{
							JSONObject actualGeometry=(JSONObject) geometryList.get(i);
							JSONObject properties=(JSONObject) actualGeometry.get("properties");
							
							String id=(String)properties.get(propertyIdentifier);
							
							geojsonMap.put("geometry", actualGeometry);
						}
						
					}
				}
			}
			
			
			
			
		} catch (IOException e) {			
			log.error("Error reading territorio files");			
		}
		
		
		return geojsonMap;
		
	}

	public static String geojsonToWKT(String jsonString) {
		String wkt=jsonString;
		wkt=wkt.replace(" ", "");
		wkt=wkt.replace("],[", ").(");
		wkt=wkt.replace(",", " ");
		wkt=wkt.replace(").(",",");
		wkt=wkt.replace("]",")");
		wkt=wkt.replace("[","(");
		wkt=wkt.replace("((((","(((");
		wkt=wkt.replace("))))",")))");
		wkt="MULTIPOLYGON "+wkt;
		
		return wkt;
	}
	
			

	
	/*Metodo para añadir las los poligonos a un pais*/
	public static void addPolygon(boolean isSemantic, String srId, Linea linea) {
		
		if ((linea.getGeometry()!=null) )
		{
			JSONObject jsonObject = Util.stringToJSONObject(linea.getGeometry());
		
			if (jsonObject!=null)
			{
				if (isSemantic)
				{	
					JSONObject geometry=(JSONObject) jsonObject.get("geometry");
					JSONArray coordinates=(JSONArray) geometry.get("coordinates");
					String WKT=LineStringUtil.geojsonToWKT(coordinates.toString());
					linea.setHasGeometry(WKT);
				}
				else 
				{
					JSONObject properties=new JSONObject();
					properties.put("id", linea.getId());
					properties.put("title", linea.getTitle());
					jsonObject.remove("properties");	
					jsonObject.put("properties", properties);
					linea.setHasGeometry(jsonObject);
				}
				linea.setGeometry(null);
			}
		
		}
		else 
		{
			log.info("geoJson Object not initialized: "+linea.toString());
			linea.setHasGeometry(new JSONObject());
		}
	}
	

}
