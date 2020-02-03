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

package org.ciudadesabiertas.config.multipe;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.ciudadesabiertas.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class MultipleConf
{
	private static final Logger log = LoggerFactory.getLogger(MultipleConf.class);
	
	private Map<String,Properties> customConfig;
	
	public Set<String> getKeys()
	{
		if (customConfig!=null)
		{
			return customConfig.keySet();
		}else {
			return new HashSet<String>();
		}
	}

	public MultipleConf(ClassLoader classLoader) {

		customConfig=new HashMap<String,Properties>();
		
		File file = new File(classLoader.getResource(Constants.CONFIG_PROPERTIES).getFile());
				
		for (final File fileEntry : file.getParentFile().listFiles()) {
	        if (fileEntry.isFile() && !fileEntry.getName().equals(Constants.CONFIG_PROPERTIES) && fileEntry.getName().endsWith(".properties")) {	        	
	        	log.info(" *** [MultipleConf] [Custom file founded: "+fileEntry.getName() + "] ***");  
	        	Properties prop = new Properties();	           
		      	try {
					prop.load(new FileInputStream(fileEntry.getAbsolutePath()));
					customConfig.put(fileEntry.getName().replace(".properties", ""),prop);				
				} catch (Exception e) {
					log.error("Error reading custom configuration file: "+fileEntry.getName());
					e.printStackTrace();
				} 
	        } 
	    }
	}

	public Map<String, Properties> getDatabasesConfig() {
		return customConfig;
	}

	public void setDatabasesConfig(Map<String, Properties> customConfig) {
		this.customConfig = customConfig;
	}
	
}
