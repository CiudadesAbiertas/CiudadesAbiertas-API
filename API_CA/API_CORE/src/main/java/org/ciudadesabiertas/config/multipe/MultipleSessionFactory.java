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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.ciudadesabiertas.utils.Constants;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class MultipleSessionFactory {
	
	
	private Map<String,SessionFactory> factories;
	private Map<String,LocalSessionFactoryBuilder> builders;
	
	public Set<String> getKeys()
	{
		if (factories!=null)
		{
			return factories.keySet();
		}else {
			return new HashSet<String>();
		}
	}

	public Map<String,SessionFactory> getFactories() {
		return factories;
	}



	public void setFactories(Map<String,SessionFactory> factories) {
		this.factories = factories;
	}

	


	public Map<String, LocalSessionFactoryBuilder> getBuilders()
	{
		return builders;
	}

	public void setBuilders(Map<String, LocalSessionFactoryBuilder> builders)
	{
		this.builders = builders;
	}

	public MultipleSessionFactory(MultipleDataSource multipleDataSource) {
		
		factories=new HashMap<String,SessionFactory>();
		builders=new HashMap<String,LocalSessionFactoryBuilder>();
		
		for (String key:multipleDataSource.getKeys())
		{
			
			LocalSessionFactoryBuilder builder = null;
			Properties prop = new Properties();
			prop.put(Constants.DB_HIBERNATE_FORMAT_SQL, multipleDataSource.getFormat_sql().get(key));
			prop.put(Constants.DB_HIBERNATE_SHOW_SQL, multipleDataSource.getShow_sql().get(key));
			prop.put(Constants.DB_HIBERNATE_DIALECT, multipleDataSource.getDialects().get(key));					
			
			if (multipleDataSource.getDataSources().get(key)!=null) {
				
				
				builder = new LocalSessionFactoryBuilder(multipleDataSource.getDataSources().get(key));
				builder        	
				.scanPackages("org.ciudadesabiertas.users.model")
	        	.scanPackages("org.ciudadesabiertas.model")
	        	.scanPackages(Constants.PAQUETE_MODELO_CONJUNTOS_DATOS)
	            .addProperties(prop);
			}
			
			if (multipleDataSource.getJndis().get(key)!=null){
				builder = new LocalSessionFactoryBuilder(multipleDataSource.getJndis().get(key));
				builder        	
				.scanPackages("org.ciudadesabiertas.users.model")
	        	.scanPackages("org.ciudadesabiertas.model")
	        	.scanPackages(Constants.PAQUETE_MODELO_CONJUNTOS_DATOS)
	            .addProperties(prop);
			}
			
			
			builders.put(key,builder);	 
		}
		
		
		
	}
	
	
	
	
	
	

}
