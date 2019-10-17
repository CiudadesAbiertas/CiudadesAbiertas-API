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

package org.ciudadesabiertas.dataset.dao;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesabiertas.config.multipe.MultipleDataSource;
import org.ciudadesabiertas.config.multipe.MultipleSessionFactory;
import org.ciudadesabiertas.dataset.model.CalidadAireSensor;
import org.ciudadesabiertas.utils.LiteralConstants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Repository
public class CalidadAireSensorDao  {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	public MultipleSessionFactory multipleSessionFactory;

	@Autowired
	public MultipleDataSource multipleDataSource;
	
	private static final Logger log = LoggerFactory.getLogger(CalidadAireSensorDao.class);
	
	public CalidadAireSensor findById(String key, String estation, String observes) {

		List<CalidadAireSensor> result = new ArrayList<CalidadAireSensor>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CalidadAireSensor where isHostedBy = :p1 and observesId = :p2";
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery(queryText);
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery(queryText);
		}
				
		query.setParameter("p1", estation);
		query.setParameter("p2", observes);
		
		result = query.list();
		
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		if (result.size() > 0)
		{
			return result.get(0);
		} else
		{
			return null;
		}
	}
	
}