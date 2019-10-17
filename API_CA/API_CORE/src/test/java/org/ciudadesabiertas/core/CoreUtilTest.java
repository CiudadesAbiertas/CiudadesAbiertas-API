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

package org.ciudadesabiertas.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.utils.CoordinateTransformer;
import org.ciudadesabiertas.utils.EncriptStringProperties;
import org.ciudadesabiertas.utils.Util;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoreUtilTest {
		

    
	private static final Logger log = LoggerFactory.getLogger(CoreUtilTest.class);
        
    @Test
    public void test01_util_encoder() throws DAOException {
	
    	String texto = "TEXTOAENCRIPTAR";
    	log.debug("[test01_util_encoder] [Texto Original:"+texto+"]");
    	String encriptado=EncriptStringProperties.textEncryptor(texto);
		log.debug("[test01_util_encoder] [Texto codificado:"+encriptado+"]");
		String textoDesecriptado=EncriptStringProperties.textDesEncryptor(encriptado);
		assertTrue(texto.equals(textoDesecriptado));

    }
    
    
    @Test
    public void test02_util_hash() throws DAOException {
	
    	String texto = "TEXTODEPRUEBATEXTODEPRUEBATEXTODEPRUEBA";
    	log.debug("[test02_util_hash] [Texto Original:"+texto+"]");
    	String hash = Util.generateHash(texto);
		log.debug("[test02_util_hash] [hash:"+hash+"]");
		String hash2 = Util.generateHash(texto);
		log.debug("[test02_util_hash] [hash:"+hash2+"]");
		assertTrue(hash.equals(hash2));

    }
   
    @Test
    public void test03_util_generateID_BBDD() throws DAOException {
	
    	String id = "IDPRUEBAS";
    	String nodoPruebas = "A";    	
    	String idBBDD = Util.generateID(id, nodoPruebas,true);
    	log.debug("[test02_util_generateID_BBDD] [id Generado:"+idBBDD+"]");
		assertTrue(idBBDD!=null && !"".equals(idBBDD));

    }
    
    @Test
    public void test04_util_properties_Driver() throws DAOException {
	
    	List<String> listDriver = new ArrayList<String>();
    	//MYSQL
    	listDriver.add("com.mysql.jdbc.Driver");
    	//ORACLE
    	listDriver.add("oracle.jdbc.OracleDriver");
    	//SQLSERVER
    	listDriver.add("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	
    	for (String obj:listDriver) {
    		String databaseType = Util.getDatabaseTypeFromDriver(obj);
    		log.debug("[test02_util_properties_Driver] [databaseType:"+databaseType+"]");
    		assertFalse("ERROR".equals(databaseType));
    	}
    }
    
    @Test
    public void test05_CoordinateTransformer() throws DAOException {
	
    	Double[] punto1 = { 40.53538651, -3.63554906 };
		Double[] punto2 = { 40.54751704, -3.64160098 };
		Double[] punto3 = { 40.55037604, -3.64221795 };
		Double[] punto4 = { 40.53861489, -3.63204832 };

		List<Double[]> listadoPuntos = new ArrayList<Double[]>();
		listadoPuntos.add(punto1);
		listadoPuntos.add(punto2);
		listadoPuntos.add(punto3);
		listadoPuntos.add(punto4);

		CoordinateTransformer ct = new CoordinateTransformer("EPSG:32630");
		
		double[] error = { 0, 0 };

		for (Double[] point : listadoPuntos)
		{
			Double lat = Double.valueOf(point[0]);
			Double lon = Double.valueOf(point[1]);
			
			double[] transformCoordinates = ct.transformCoordinates(lat, lon);

			log.info(lat + "," + lon);
			log.info("x:" + transformCoordinates[0] + " y:" + transformCoordinates[1]);
			assertFalse(error.equals(transformCoordinates));
		}

    }
    
    @Test
    public void test06_util_urlify() throws DAOException {
	
    	String cadena_acentuada = "áéíóúçàèìòùñ";
    	String resultado_esperado ="aeioucaeioun";
    	String result = Util.urlify(cadena_acentuada);
    	assertTrue(resultado_esperado.equals(result));
    }
    
}
