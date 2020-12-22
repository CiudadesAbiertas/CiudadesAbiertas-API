package org.ciudadesabiertas.dataset.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.ciudadesabiertas.model.IGeoModelGeometry;
import org.ciudadesabiertas.utils.Territorio;
import org.ciudadesabiertas.utils.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class TerritorioUtil {

	
	private static final Logger log = LoggerFactory.getLogger(TerritorioUtil.class);
	
	

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
					if (geometry!=null)
					{
					  JSONArray coordinates=(JSONArray) geometry.get("coordinates");
					  String WKT=TerritorioUtil.geojsonToWKT(coordinates.toString());
					  territorio.setHasGeometry(WKT);
					}else {
					  System.out.println(geometry.toJSONString());
					}
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
	
	
	
	/*Metodo para añadir las los poligonos a un pais*/
	public static void addPolygon(boolean isSemantic, IGeoModelGeometry territorio) {
		
		if ((territorio.getGeometry()!=null) )
		{
			JSONObject jsonObject = Util.stringToJSONObject(territorio.getGeometry());
		
			if (jsonObject!=null)
			{
				if (isSemantic)
				{	
					JSONObject geometry=(JSONObject) jsonObject.get("geometry");
					if (geometry==null)
					{
					  if (jsonObject.get("features")!=null)
					  {
						JSONArray features=(JSONArray) jsonObject.get("features");
						JSONObject feature=(JSONObject) features.get(0);
						geometry=(JSONObject) feature.get("geometry");
					  }
					}					  
					if (geometry!=null)
					{
					  JSONArray coordinates=(JSONArray) geometry.get("coordinates");
					  String WKT=geojsonToWKT(coordinates.toString());
					  territorio.setHasGeometry(WKT);
					}
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
				territorio.setGeometry(null);
			}
		
		}
		else 
		{
			log.debug("geoJson Object not initialized: "+territorio.toString());
			territorio.setHasGeometry(new JSONObject());
		}
		
		territorio.showFieldTerritorio();
	}
	
	
}
