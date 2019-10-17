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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cts.CRSFactory;
import org.cts.IllegalCoordinateException;
import org.cts.crs.CRSException;
import org.cts.crs.CoordinateReferenceSystem;
import org.cts.crs.GeodeticCRS;
import org.cts.op.CoordinateOperation;
import org.cts.op.CoordinateOperationFactory;
import org.cts.registry.EPSGRegistry;
import org.cts.registry.RegistryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class CoordinateTransformer
{
	private static final Logger log = LoggerFactory.getLogger(CoordinateTransformer.class);

	private String sourceSRID= "EPSG:25830"; //"EPSG:4326";
	
	private String targetSRID;

	public static List<String> suportedTargetXY;
	
	public static List<String> suportedTargetLATLON;

	private Set<CoordinateOperation> coordinatesOpps;
	
	private boolean transformXY = true;

	
	static
	{
		suportedTargetXY = new ArrayList<String>();
		String[] srIdsXY=Constants.SUPPORTED_XY_SRIDS.split(",");	
		for (String srId:srIdsXY)
		{		
			suportedTargetXY.add(srId);
		}
		suportedTargetLATLON = new ArrayList<String>();
		String[] srIdsLATLON=Constants.SUPPORTED_LAT_LON_SRIDS.split(",");
		for (String srId:srIdsLATLON)
		{		
			suportedTargetLATLON.add(srId);
		}
		
	}
	
	public boolean isTransformXY() {
		return transformXY;
	}

	public void setTransformXY(boolean transformXY) {
		this.transformXY = transformXY;
	}
	
	

	/**
	 * Metodo para comprobar previamente a llamar al constructor si es una transformacion X e y o para LAT y LON
	 * @return boolean
	 */
	public static boolean comprobarSrIdXY(String srId) {
		boolean result=false;
		if (suportedTargetXY.contains(srId)) {
			result=true;
		}
		return result;
	}
	

	/**
	 * Metodo para comprobar previamente a llamar al constructor si es una transformacion X e y o para LAT y LON
	 * @return boolean
	 */
	public static boolean comprobarSrIdLatLon(String srId) {
		boolean result=false;
		if (suportedTargetLATLON.contains(srId)) {
			result=true;
		}
		return result;
	}
	
	public static boolean isValidSrId(String srId) {
		boolean result=false;
		if (suportedTargetLATLON.contains(srId)) {
			return true;
		}
		if (suportedTargetXY.contains(srId)) {
			return true;
		}
		return result;
	}

	public CoordinateTransformer(String target) {
		
		new CoordinateTransformer(sourceSRID, target);
	}
		

	public CoordinateTransformer(String source,String target)
	{		
		super();
		if (source!=null && !"".equals(source)) {
			this.sourceSRID = source;
		}
		
		if (suportedTargetLATLON.contains(target)) {
			transformXY=false;
		}
		
		if (suportedTargetXY.contains(target) || (suportedTargetLATLON.contains(target)) )
		{
			this.targetSRID = target;
			try
			{
				CRSFactory cRSFactory = new CRSFactory();
				RegistryManager registryManager = cRSFactory.getRegistryManager();
				registryManager.addRegistry(new EPSGRegistry());
				CoordinateReferenceSystem crs1 = cRSFactory.getCRS(this.sourceSRID);
				CoordinateReferenceSystem crs2 = cRSFactory.getCRS(this.targetSRID);
				GeodeticCRS sourceGCRS = (GeodeticCRS) crs1;
				GeodeticCRS targetGCRS = (GeodeticCRS) crs2;
				coordinatesOpps = CoordinateOperationFactory.createCoordinateOperations(sourceGCRS, targetGCRS);

			}

			catch (Exception e)
			{
				log.error("Error creating coordinate transformator", e);
			}

		}else {
			log.error("Error creating coordinate transformator [suportedTarget] [sourceSRID:"+sourceSRID+"] [targetSRID:"+targetSRID+"] NOT is Defined");
		}
	}

	public double[] transformCoordinates(double lat, double lon)
	{
		double[] dd = { 0, 0 };
		try
		{
			if ((coordinatesOpps != null) && (coordinatesOpps.size() > 0))
			{
				double[] coord = new double[2];
				coord[0] = lon;
				coord[1] = lat;
				CoordinateOperation op = coordinatesOpps.iterator().next();
				dd = op.transform(coord);
			}
		} catch (Exception e)
		{
			log.error("Error converting coordinates", e);
		}

		return dd;
	}
	
	
	public static void main(String args[])
	{

		exampleTransformLatLongtoXY();
		exampleTransformXYToLatLong();

	}
	
	private static void exampleTransformLatLongtoXY() {
		log.info("[exampleTransformLatLongtoXY] start...");
		Double[] punto1 = { 40.53538651, -3.63554906 };
		Double[] punto2 = { 40.54751704, -3.64160098 };
		Double[] punto3 = { 40.55037604, -3.64221795 };
		Double[] punto4 = { 40.53861489, -3.63204832 };

		List<Double[]> listadoPuntos = new ArrayList<Double[]>();
		listadoPuntos.add(punto1);
		listadoPuntos.add(punto2);
		listadoPuntos.add(punto3);
		listadoPuntos.add(punto4);

		CoordinateTransformer ct = new CoordinateTransformer("EPSG:4326","EPSG:23030");

		for (Double[] point : listadoPuntos)
		{
			Double lat = Double.valueOf(point[0]);
			Double lon = Double.valueOf(point[1]);
			
			double[] transformCoordinates = ct.transformCoordinates(lat, lon);

			log.info(lat + "," + lon);
			log.info("x:" + transformCoordinates[0] + " y:" + transformCoordinates[1]);
		}
		log.info("[exampleTransformLatLongtoXY] end.");
	}
	
	private static void exampleTransformXYToLatLong() {
		log.info("[exampleTransformXYToLatLong] start...");
		//FIJAMOS sourceGCRS		
		
		Double[] puntoxy1 = { 446277.3850317879,  4487583.259033542 };
		Double[] puntoxy2 = { 445774.64290306787, 4488933.496882837 };
		Double[] puntoxy3 = { 445724.71314195887, 4489251.235334008 };
		Double[] puntoxy4 = { 446576.4359870558,  4487939.486829181 };
		
//		Double[] puntoxy1 = { 446175.61012356204, 4487376.659440295 };
//		Double[] puntoxy2 = { 445672.87340489257, 4488726.881990712 };
//		Double[] puntoxy3 = { 445622.944149136, 4489044.616854279 };
//		Double[] puntoxy4 = { 446474.65766156896, 4487732.88327275 };

		List<Double[]> listadoPuntos = new ArrayList<Double[]>();
		listadoPuntos.add(puntoxy1);
		listadoPuntos.add(puntoxy2);
		listadoPuntos.add(puntoxy3);
		listadoPuntos.add(puntoxy4);

		CoordinateTransformer ct = new CoordinateTransformer("EPSG:23030","EPSG:4326");

		for (Double[] point : listadoPuntos)
		{
			Double x = Double.valueOf(point[0]);
			Double y = Double.valueOf(point[1]);
			
			//Alternamos coordenadas
			double[] transformCoordinates = ct.transformCoordinates(y, x);

			log.info(x + "," + y);
			//Alternamos Respuesta
			log.info("lat:" + transformCoordinates[1] + " lon:" + transformCoordinates[0]);
		}
		log.info("[exampleTransformXYToLatLong] end.");
	}

	

}
