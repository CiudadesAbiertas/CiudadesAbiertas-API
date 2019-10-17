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
import org.cts.op.CoordinateOperationException;
import org.cts.op.CoordinateOperationFactory;
import org.cts.registry.EPSGRegistry;
import org.cts.registry.RegistryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.BasicConfigurator;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class CheckCoordinates
{
	
	private static final Logger log = LoggerFactory.getLogger(CheckCoordinates.class);

	Set<CoordinateOperation> coordinatesOpps;
	
	public CheckCoordinates(String source, String target)
	{
		try
		{
			CRSFactory cRSFactory = new CRSFactory();
			RegistryManager registryManager = cRSFactory.getRegistryManager();
			registryManager.addRegistry(new EPSGRegistry());
			CoordinateReferenceSystem crs1 = cRSFactory.getCRS(source);
			CoordinateReferenceSystem crs2 = cRSFactory.getCRS(target);
			GeodeticCRS sourceGCRS = (GeodeticCRS) crs1;
			GeodeticCRS targetGCRS = (GeodeticCRS) crs2;
			coordinatesOpps = CoordinateOperationFactory.createCoordinateOperations(sourceGCRS, targetGCRS);
		}
		catch (Exception e)
		{
			log.error("Error creating coordinate transformator", e);
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
				CoordinateOperation op = coordinatesOpps.iterator().next();
				coord[0] = lon;
				coord[1] = lat;
				dd = op.transform(coord);
			}
		} catch (Exception e)
		{
			log.error("Error converting coordinates", e);
		}

		return dd;
	}
	
	public static void main(String[] args)
	{
	
		

		CheckCoordinates ct = new CheckCoordinates("EPSG:4326","EPSG:32630");
		
		double[] transformCoordinates = ct.transformCoordinates(40.53538651, -3.63554906);
		
		log.info(transformCoordinates[0]+","+transformCoordinates[1]);
		
		
		CheckCoordinates ct2 = new CheckCoordinates("EPSG:32630","EPSG:4326");
		
		double[] transformCoordinates2 = ct2.transformCoordinates(4487376.659440295,446175.61012356204);
		
		log.info(transformCoordinates2[0]+","+transformCoordinates2[1]);
		
		
		CheckCoordinates ct3 = new CheckCoordinates("EPSG:25830","EPSG:4326");
		double[] transformCoordinates3 = ct3.transformCoordinates(4474393.97,447703.02);
		
		log.info(transformCoordinates3[0]+","+transformCoordinates3[1]);
		
		CheckCoordinates ct4 = new CheckCoordinates("EPSG:23030","EPSG:4326");
		double[] transformCoordinates4= ct4.transformCoordinates(4351891.4,447209.738);
		transformCoordinates4= ct3.transformCoordinates(4393996.4,448145.507);
		
		
		CheckCoordinates ct5 = new CheckCoordinates("EPSG:23030","EPSG:4326");
		double[] transformCoordinates5= ct5.transformCoordinates(4414739.0,446795.174);		
		transformCoordinates5= ct5.transformCoordinates(4467951.74,441473.90);
			

		
		log.info(transformCoordinates5[0]+","+transformCoordinates5[1]);
		
		
		
		
		
	}
}
