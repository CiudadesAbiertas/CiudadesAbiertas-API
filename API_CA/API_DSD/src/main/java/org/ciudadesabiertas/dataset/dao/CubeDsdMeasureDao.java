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
import org.ciudadesabiertas.dataset.model.CubeDsd;
import org.ciudadesabiertas.dataset.model.CubeDsdMeasure;
import org.ciudadesabiertas.dataset.model.CubeDsdRelMeasure;
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
public class CubeDsdMeasureDao  {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	public MultipleSessionFactory multipleSessionFactory;

	@Autowired
	public MultipleDataSource multipleDataSource;
	
	private static final Logger log = LoggerFactory.getLogger(CubeDsdMeasureDao.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CubeDsdMeasure> findByCubeDsd(String key, CubeDsd cubeDsd) {

		List<CubeDsdMeasure> result = new ArrayList<CubeDsdMeasure>();
		
		List<CubeDsdRelMeasure> firstResult = new ArrayList<CubeDsdRelMeasure>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdRelMeasure where cubeDsd = :cDsd";
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery(queryText);
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery(queryText);
		}
				
		query.setParameter("cDsd", cubeDsd);		
		
		firstResult = query.list();
		
		for (CubeDsdRelMeasure rel:firstResult)
		{
			result.add(	rel.getCubeDsdMeasure());
		}
		
		
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		return result;
		
		
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public long rowCountByCubeDsd(String key, CubeDsd cubeDsd) {
		List<CubeDsdMeasure> result = new ArrayList<CubeDsdMeasure>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdMeasure where cubeDsd = :cDsd";
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery(queryText);
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery(queryText);
		}
				
		query.setParameter("cDsd", cubeDsd);		
		
		
		long count=query.list().size();
		
		
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		return count;
		
	}

	public CubeDsdMeasure findByCubeDsdAndMeasureId(String key, CubeDsd cubeDsd, String measureId) {
		
		List<CubeDsdMeasure> result = findByCubeDsd(key,cubeDsd);		
		
		for (CubeDsdMeasure dimension:result)
		{
			if (dimension.getId().equals(measureId))
			{
				return dimension;
			}
		}
		return new CubeDsdMeasure();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CubeDsd> findCubeDsdByMeasure(String key, CubeDsdMeasure measure) {

		List<CubeDsd> result = new ArrayList<CubeDsd>();
		List<CubeDsdRelMeasure> firstResult = new ArrayList<CubeDsdRelMeasure>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdRelMeasure where cubeDsdMeasure = :measure";
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery(queryText);
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery(queryText);
		}
				
		query.setParameter("measure", measure);		
		
		firstResult = query.list();
		
		for (CubeDsdRelMeasure rel:firstResult)
		{
			result.add(rel.getCubeDsd());
		}
			
		
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long rowCountCubeDsdByMeasure(String key, CubeDsdMeasure measure) {
		
		List<CubeDsd> result = new ArrayList<CubeDsd>();
		List<CubeDsdRelMeasure> firstResult = new ArrayList<CubeDsdRelMeasure>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdRelMeasure where cubeDsdMeasure = :measure";
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery(queryText);
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery(queryText);
		}
				
		query.setParameter("measure", measure);		
		
		firstResult = query.list();
		
		for (CubeDsdRelMeasure rel:firstResult)
		{
			result.add(rel.getCubeDsd());
		}
			
		
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		return result.size();
	}

	
	
}