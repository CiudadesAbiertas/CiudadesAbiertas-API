package org.ciudadesabiertas.dataset.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.ciudadesabiertas.utils.Territorio;
import org.ciudadesabiertas.utils.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class TerritorioUtil {

	
	private static final Logger log = LoggerFactory.getLogger(TerritorioUtil.class);
	
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
							
							PairIdEPSG pair=new PairIdEPSG();
							pair.setEPSG(directory.getName());
							pair.setId(id.toLowerCase());
							
							log.debug(pair.toString());
							
							geojsonMap.put(pair.toString(), actualGeometry);
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
	
			
	public static void addPolygon(boolean isSemantic, String srId, Territorio territorio, Map<String,JSONObject> geojson) {
		
		addPolygon(isSemantic, srId, territorio, geojson, false);
	}
	
	/*Metodo para añadir las los poligonos a un pais*/
	public static void addPolygon(boolean isSemantic, String srId, Territorio territorio, Map<String,JSONObject> geojson, boolean byTittle) {
		
		
		PairIdEPSG pair=new PairIdEPSG();
		if (byTittle) {
			pair.setId(territorio.getTitle().toLowerCase());
		}else {
			pair.setId(territorio.getId().toLowerCase());
		}
		pair.setEPSG(srId.replace("EPSG:", ""));
		
		if ((geojson!=null) && (geojson.size()>0))
		{
			JSONObject jsonObject = geojson.get(pair.toString());
		
			if (jsonObject!=null)
			{
				if (isSemantic)
				{	
					JSONObject geometry=(JSONObject) jsonObject.get("geometry");
					JSONArray coordinates=(JSONArray) geometry.get("coordinates");
					String WKT=TerritorioUtil.geojsonToWKT(coordinates.toString());
					territorio.setHasGeometry(WKT);
				}
				else 
				{
					JSONObject properties=new JSONObject();
					properties.put("id", territorio.getId());
					properties.put("title", territorio.getTitle());
					jsonObject.remove("properties");	
					jsonObject.put("properties", properties);
					territorio.setHasGeometry(jsonObject);
				}
			}
		
			//Incluimos el cargar los atributos de Territorio
			territorio.showFieldTerritorio();
		}
		else 
		{
			log.info("geoJson Object not initialized: "+territorio.toString());
			territorio.setHasGeometry(new JSONObject());
		}
	}
	
	public static String rsqlTerritorio(String rsqlQ) {
		if (rsqlQ.contains(TerritorioConstants.FIELD_PAIS)) {										
			rsqlQ = rsqlQ.replace(TerritorioConstants.FIELD_PAIS, TerritorioConstants.FIELD_PAIS_RSQL);
		}
		if (rsqlQ.contains(TerritorioConstants.FIELD_AUTONOMIA)) {										
			rsqlQ = rsqlQ.replace(TerritorioConstants.FIELD_AUTONOMIA, TerritorioConstants.FIELD_AUTONOMIA_RSQL);
		}
		if (rsqlQ.contains(TerritorioConstants.FIELD_PROVINCIA)) {										
			rsqlQ = rsqlQ.replace(TerritorioConstants.FIELD_PROVINCIA, TerritorioConstants.FIELD_PROVINCIA_RSQL);
		}
		if (rsqlQ.contains(TerritorioConstants.FIELD_MUNICIPIO)) {										
			rsqlQ = rsqlQ.replace(TerritorioConstants.FIELD_MUNICIPIO, TerritorioConstants.FIELD_MUNICIPIO_RSQL);
		}
		if (rsqlQ.contains(TerritorioConstants.FIELD_DISTRITO)) {										
			rsqlQ = rsqlQ.replace(TerritorioConstants.FIELD_DISTRITO, TerritorioConstants.FIELD_DISTRITO_RSQL);
		}
		return rsqlQ;
	}
}
