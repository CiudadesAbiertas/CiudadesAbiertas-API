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

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class MultipleDataSource
{

	private Map<String, BasicDataSource> dataSources;
	private Map<String, DataSource> jndis;
	private Map<String, String> dialects;
	private Map<String, String> userNames;
	private Map<String, String> drivers;
	private Map<String, String> show_sql;
	private Map<String, String> format_sql;
	private Map<String, String> defaultSchema;
	
	private static final Logger log = LoggerFactory.getLogger(MultipleDataSource.class);
	
	public Set<String> getKeys()
	{
		Set<String> setKey = new HashSet<String>();
		if (dataSources!=null || jndis!=null )
		{
			 if ( dataSources.keySet()!=null) {
				 setKey.addAll(dataSources.keySet());
			 }
			 if ( jndis.keySet()!=null) {
				 setKey.addAll(jndis.keySet());
			 }
		}
		
		return setKey;
		
	}

	public MultipleDataSource(MultipleConf multipleConf) 
	{
		dataSources = new HashMap<String, BasicDataSource>();
		jndis = new HashMap<String, DataSource>();
		dialects = new HashMap<String, String>();
		userNames = new HashMap<String, String>();
		drivers = new HashMap<String, String>();
		show_sql = new HashMap<String, String>();
		format_sql = new HashMap<String, String>();
		defaultSchema = new HashMap<String, String>();

		log.info("[MultipleDataSource] [multipleConf:"+multipleConf+"]");
		for (Map.Entry<String, Properties> entry : multipleConf.getDatabasesConfig().entrySet())
		{
			String key = entry.getKey();
			Properties p = entry.getValue();

			log.debug("[MultipleDataSource] [key:"+key+"] [p:"+p+"]");
			if ((Util.validValue(p.getProperty(Constants.DB_DRIVER))&&(Util.validValue(p.getProperty(Constants.DB_URL)))))
			{
				BasicDataSource ds = new BasicDataSource();
				ds.setDriverClassName(p.getProperty(Constants.DB_DRIVER));
				ds.setUrl(p.getProperty(Constants.DB_URL));
				ds.setUsername(p.getProperty(Constants.DB_USER));
				ds.setPassword(Util.checkAndSetEncodedProperty(p.getProperty(Constants.DB_PASSWORD)));
	
				ds.setInitialSize(Integer.parseInt(p.getProperty(Constants.DB_INITIAL_SIZE)));
				ds.setMaxActive(Integer.parseInt(p.getProperty(Constants.DB_MAX_ACTIVE)));
				ds.setMaxIdle(Integer.parseInt(p.getProperty(Constants.DB_MAX_IDLE)));
				ds.setMinIdle(Integer.parseInt(p.getProperty(Constants.DB_MIN_IDLE)));
				
				StartVariables.databaseTypes.put(key, Util.getDatabaseTypeFromDriver(p.getProperty(Constants.DB_DRIVER)));
			   	
				if (p.getProperty(Constants.DB_SCHEMA)!=null)
				{
					StartVariables.dbSQLServeSchemas.put(key, p.getProperty(Constants.DB_SCHEMA));
				}
				// Evitar cierre de conexiones después de horas sin uso
				ds.setTestOnBorrow(true);
				ds.setValidationQuery(p.getProperty(Constants.DB_VALIDATION_QUERY));
				
				dataSources.put(key, ds);
				dialects.put(key, p.getProperty(Constants.DB_HIBERNATE_DIALECT));
				userNames.put(key, p.getProperty(Constants.DB_USER));
				drivers.put(key, p.getProperty(Constants.DB_DRIVER));
				show_sql.put(key, p.getProperty(Constants.DB_HIBERNATE_SHOW_SQL));
				format_sql.put(key, p.getProperty(Constants.DB_HIBERNATE_FORMAT_SQL));
				
				if (p.getProperty(Constants.DB_SCHEMA)!=null)
				{
					defaultSchema.put(key, p.getProperty(Constants.DB_SCHEMA));
				}
				
				log.debug("[BasicDataSource] [ds:"+ds+"]");
			
			}
		//JNDI Configuration
			if (Util.validValue(p.getProperty(Constants.DB_JNDI_NAME))){
				String keyJNDI = p.getProperty(Constants.DB_JNDI_NAME);

				JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
				String envJndi = p.getProperty(Constants.STR_ENV_JNDI_CONTEXT);
				if (envJndi!=null && "".equals(envJndi)) {
					bean.setJndiName(envJndi + keyJNDI);
				}else {
					bean.setJndiName(Constants.ENV_JNDI_CONTEXT + keyJNDI);
				}
				bean.setProxyInterface(DataSource.class);
				//bean.setProxyInterface(BasicDataSource.class);
				bean.setLookupOnStartup(false);
				try {
					bean.afterPropertiesSet();
				} catch (IllegalArgumentException | NamingException e) {
					log.error("[MultipleDataSource] [Error:"+e.getMessage()+"] ");
				}
				jndis.put(key, (DataSource) bean.getObject());	
				dialects.put(key, p.getProperty(Constants.DB_HIBERNATE_DIALECT));
				drivers.put(key, p.getProperty(Constants.DB_DRIVER));
				show_sql.put(key, p.getProperty(Constants.DB_HIBERNATE_SHOW_SQL));
				format_sql.put(key, p.getProperty(Constants.DB_HIBERNATE_FORMAT_SQL));
				
				if (p.getProperty(Constants.DB_HIBERNATE_DEFAULT_SCHEMA)!=null)
				{
					defaultSchema.put(key, p.getProperty(Constants.DB_HIBERNATE_DEFAULT_SCHEMA));
				}
				
				if (p.getProperty(Constants.DB_SCHEMA)!=null)
				{
					StartVariables.dbSQLServeSchemas.put(key, p.getProperty(Constants.DB_SCHEMA));
				}
				
				log.debug("[MultipleDataSource] [JndiObjectFactoryBean:"+bean+"]");
			}

		}

	}
	
	

	public Map<String, BasicDataSource> getDataSources()
	{
		return dataSources;
	}

	public void setDataSources(Map<String, BasicDataSource> dataSources)
	{
		this.dataSources = dataSources;
	}

	public Map<String, String> getDialects()
	{
		return dialects;
	}

	public void setDialects(Map<String, String> dialects)
	{
		this.dialects = dialects;
	}

	public Map<String, String> getUserNames()
	{
		return userNames;
	}

	public void setUserNames(Map<String, String> userNames)
	{
		this.userNames = userNames;
	}

	public Map<String, String> getDrivers()
	{
		return drivers;
	}

	public void setDrivers(Map<String, String> drivers)
	{
		this.drivers = drivers;
	}

	public String getDatabaseType(String key)
	{
		String driver = drivers.get(key);

		return Util.getDatabaseTypeFromDriver(driver);

	}

	public Map<String, String> getShow_sql()
	{
		return show_sql;
	}

	public void setShow_sql(Map<String, String> show_sql)
	{
		this.show_sql = show_sql;
	}

	public Map<String, String> getFormat_sql()
	{
		return format_sql;
	}

	public void setFormat_sql(Map<String, String> format_sql)
	{
		this.format_sql = format_sql;
	}

	public Map<String, DataSource> getJndis() {
		return jndis;
	}

	public Map<String, String> getDefaultSchema() {
		return defaultSchema;
	}

	public void setDefaultSchema(Map<String, String> defaultSchema) {
		this.defaultSchema = defaultSchema;
	}
	
	

}
