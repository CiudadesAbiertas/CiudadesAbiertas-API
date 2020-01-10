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
import org.ciudadesabiertas.dataset.model.CubeDsdDimension;
import org.ciudadesabiertas.dataset.model.CubeDsdRelDimension;
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
public class CubeDsdDimensionDao  {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	public MultipleSessionFactory multipleSessionFactory;

	@Autowired
	public MultipleDataSource multipleDataSource;
	
	private static final Logger log = LoggerFactory.getLogger(CubeDsdDimensionDao.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CubeDsdDimension> findByCubeDsd(String key, CubeDsd cubeDsd) {

		List<CubeDsdDimension> result = new ArrayList<CubeDsdDimension>();
		
		List<CubeDsdRelDimension> firstResult = new ArrayList<CubeDsdRelDimension>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdRelDimension where cubeDsd = :cDsd";
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
		
		for (CubeDsdRelDimension rel:firstResult)
		{
			result.add(	rel.getCubeDsdDimension());
		}
		
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		return result;
		
		
	}

	@SuppressWarnings("rawtypes")
	public long rowCountByCubeDsd(String key, CubeDsd cubeDsd) {
		
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdRelDimension where cubeDsd = :cDsd";
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

	public CubeDsdDimension findByCubeDsdAndDimensionId(String key, CubeDsd cubeDsd, String dimensionId) {
		
		List<CubeDsdDimension> result = findByCubeDsd(key,cubeDsd);		
		
		for (CubeDsdDimension dimension:result)
		{
			if (dimension.getId().equals(dimensionId))
			{
				return dimension;
			}
		}
		return new CubeDsdDimension();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CubeDsd> findByCubeDsdByDimension(String key, CubeDsdDimension cubeDsdDimension) {

		List<CubeDsd> result = new ArrayList<CubeDsd>();
		
		List<CubeDsdRelDimension> firstResult = new ArrayList<CubeDsdRelDimension>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdRelDimension where cubeDsdDimension = :cDsdDimension";
		
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery(queryText);
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery(queryText);
		}
				
		query.setParameter("cDsdDimension", cubeDsdDimension);		
				
		firstResult = query.list();
		
		for (CubeDsdRelDimension rel:firstResult)
		{
			result.add(	rel.getCubeDsd());
		}
		
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		return result;
		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long rowCountByCubeDsdByDimension(String key, CubeDsdDimension cubeDsdDimension) {
		
		
		List<CubeDsdRelDimension> firstResult = new ArrayList<CubeDsdRelDimension>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdRelDimension where cubeDsdDimension = :cDsdDimension";
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery(queryText);
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery(queryText);
		}
				
		query.setParameter("cDsdDimension", cubeDsdDimension);		
		
		firstResult = query.list();
				
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		return firstResult.size();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CubeDsdDimension> findDimensionWithMultiplesValues(String key) {

		List<CubeDsdDimension> result = new ArrayList<CubeDsdDimension>();
		
		Session opennedSession = null;
		
		Query query = null;
		String queryText="FROM CubeDsdDimension where multiple_field != 'NULL' and multiple_field != ''";
		
		
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery(queryText);
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery(queryText);
		}
		
		result = query.list();
		
		
		if (opennedSession != null)
		{
			opennedSession.close();
		}
		
		return result;
		
		
	}

	
}