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

package org.ciudadesabiertas.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ciudadesabiertas.config.multipe.MultipleDataSource;
import org.ciudadesabiertas.config.multipe.MultipleSessionFactory;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.DatasetSearch;
import org.ciudadesabiertas.utils.LiteralConstants;
import org.ciudadesabiertas.utils.OrderBySqlFormula;
import org.ciudadesabiertas.utils.Sort;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.DoubleType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 */
@Repository
public class DatasetDao<T>
{
	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	public MultipleSessionFactory multipleSessionFactory;

	@Autowired
	public MultipleDataSource multipleDataSource;

	@Autowired
	protected Environment env;

	private static final Logger log = LoggerFactory.getLogger(DatasetDao.class);
	
	public List<T> query(Class<T> type, DatasetSearch<T> search, int page, int pageSize, List<String> fieldsQuery, List<Sort> orders) throws DAOException
	{
		return query(null, type, search, page, pageSize, fieldsQuery, orders);
	}

	@SuppressWarnings("unchecked")
	public List<T> query(String key, Class<T> type, DatasetSearch<T> search, int page, int pageSize, List<String> fieldsQuery, List<Sort> orders) throws DAOException
	{

		List<T> result = new ArrayList<T>();
		
		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		Session opennedSession = null;
		try
		{
			List<Criterion> criterios = new ArrayList<Criterion>();
			Criteria criteria = null;
			

			if (multipleSessionFactory.getKeys().contains(key))
			{
				log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
				criterios = search.obtenerCriterios(multipleDataSource.getDrivers().get(key),key);
				opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
				criteria = opennedSession.createCriteria(type);
			} else
			{
				criterios = search.obtenerCriterios(env.getProperty(Constants.DB_DRIVER),key);
				criteria = sessionFactory.getCurrentSession().createCriteria(type);
			}

			for (Criterion criterioTemp : criterios)
			{
				criteria.add(criterioTemp);
			}

			if (page >= 0)
			{
				if (page != Constants.NO_PAGINATION_PAGE_1)
				{
					criteria.setFirstResult(page * pageSize);
					criteria.setMaxResults(pageSize);
				}

				// Tratamiento de Fields
				if (fieldsQuery != null && !fieldsQuery.isEmpty())
				{
					ProjectionList projectionList = Projections.projectionList();
					for (String field : fieldsQuery)
					{
						projectionList.add(Projections.property(field).as(field), field);
					}
					criteria.setProjection(projectionList);
					criteria.setResultTransformer(new AliasToBeanResultTransformer(type));
				}

				// Tratamiento del order
				if (orders != null && !orders.isEmpty())
				{
					for (Sort sort : orders)
					{
						if (sort.isDesc())
						{
							criteria.addOrder(Order.desc(sort.getProperty()));
						} else
						{
							criteria.addOrder(Order.asc(sort.getProperty()));
						}
					}
					// Esto es para prevenir el bug de paginación en Oracle
					if (Util.getDatabaseTypeFromDriver(env.getProperty(Constants.DB_DRIVER)).equals(Constants.ORACLE))
					{
						if (orders.toString().indexOf("id") < 0)
						{
							criteria.addOrder(Order.asc("id"));
						}
					}

				}

				result = criteria.list();
				
			}
		} catch (Exception e)
		{
			String msg = "executeSelect(query) [Hibernate Exception]:" + e.getMessage();
			log.error(msg, e);
			throw new DAOException(Constants.INTERNAL_ERROR);
		}finally {
			if (opennedSession != null)
			{
				opennedSession.close();
			}
		}
		return result;
	}
	

	@SuppressWarnings("unchecked")
	public T findByIdObject(String key, Class<T> type, String id)
	{

		List<T> result = new ArrayList<T>();

		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		
		String nameEntity=Util.extractNameFromModelClass(type);

		// Criteria criteria = null;
		Session opennedSession = null;

		Query<?> query = null;
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery("FROM "+nameEntity+" where id = :code ");
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery("FROM "+nameEntity+" where id = :code ");
		}

		query.setParameter("code", id);
		result = (List<T>) query.list();

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

	
	public void add(String key, T obj) throws DAOException
	{
		Session opennedSession = null;
		Transaction tx = null;

		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(obj.getClass());
		}
		

		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			try
			{
				opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
				tx = opennedSession.beginTransaction();
				opennedSession.save(obj);
				tx.commit();
			} catch (Exception e)
			{
				tx.rollback();
				throw new DAOException("Error adding Equipamiento");
			} finally
			{
				opennedSession.close();
			}

		} else
		{
			sessionFactory.getCurrentSession().save(obj);
		}

	}
	

	public void update(String key, T obj) throws DAOException
	{

		Session opennedSession = null;
		Transaction tx = null;

		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(obj.getClass());
		}

		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			opennedSession.update(obj);

			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			try
			{
				opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
				tx = opennedSession.beginTransaction();
				opennedSession.update(obj);
				tx.commit();
			} catch (Exception e)
			{
				tx.rollback();
				throw new DAOException("Error update Equipamiento");
			} finally
			{
				opennedSession.close();
			}

		} else
		{
			sessionFactory.getCurrentSession().update(obj);
		}

	}

	
	public void remove(String key, T obj) throws DAOException
	{
		Session opennedSession = null;
		Transaction tx = null;

		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(obj.getClass());
		}

		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			try
			{
				opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
				tx = opennedSession.beginTransaction();
				opennedSession.delete(obj);
				tx.commit();
			} catch (Exception e)
			{
				tx.rollback();
				throw new DAOException("Error removing Equipamiento");
			} finally
			{
				opennedSession.close();
			}
		} else
		{
			sessionFactory.getCurrentSession().delete(obj);
		}

	}
	
	
	@SuppressWarnings("unused")
	@Deprecated
	private long rowCount_with_APICriteria (Class<T> type, Session opennedSession) {
		
		Criteria criteria = opennedSession.createCriteria(type).setProjection(Projections.rowCount());
				
		Long idCount = (Long)criteria.uniqueResult();
		if(idCount==null){
			idCount= Long.parseLong("0");
		}
		
			
		return idCount.longValue();			
	}
	
	
	@SuppressWarnings("unused")
	public long rowCount(String key,Class<T> type) {

		Session opennedSession = null;
		Long rowCount = Long.parseLong("0");

		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}

		// Criteria criteria = null;
		if (multipleSessionFactory.getKeys().contains(key)) {
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			rowCount = rowCountMax_with_JPACriteria(type, opennedSession);

		} else {

			rowCount = rowCountMax_with_JPACriteria(type, sessionFactory.getCurrentSession());
		}

		if (rowCount == null) {
			rowCount = Long.parseLong("0");
		}

		if (opennedSession != null) {
			opennedSession.close();
		}

		return rowCount.longValue();
	}
	
	
	public long rowCount(String key,Class<T> type, DatasetSearch<T> search) throws DAOException {		
				
		Criteria criteria = null;
		Session opennedSession = null;
		
		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		
		List<Criterion> criterios = new ArrayList<Criterion>();
		Long rowCount = null;
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			criterios = search.obtenerCriterios(multipleDataSource.getDrivers().get(key),key);
			criteria = opennedSession.createCriteria(type);
		}else {
			criterios = search.obtenerCriterios(env.getProperty(Constants.DB_DRIVER),key);
			criteria = sessionFactory.getCurrentSession().createCriteria(type);			
		}
		
		
		try
		{
			for (Criterion criterio: criterios) {
				criteria.add(criterio);
			}
			
			criteria.setProjection(Projections.rowCount());
	        
			
			
			rowCount = (Long)criteria.uniqueResult();
			
			if(rowCount==null){
				rowCount= Long.parseLong("0");
			}
			
			
			
		}
		catch (Exception e)
		{
			String msg="Row count error";
			log.error(msg,e);
			throw new DAOException(msg);
		}finally {
			if (opennedSession!=null)
			{
				opennedSession.close();
			}
		}
		
		return rowCount.longValue();
	}
	
	
	public long countMax (String key,Class<T> type) {
		Session opennedSession = null;		
		Long idCount = null;
		
		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();	
			idCount =rowCountMax_with_JPACriteria (type, opennedSession);
		}
		else 
		{			
			idCount =rowCountMax_with_JPACriteria (type, sessionFactory.getCurrentSession());
		}
		
		
		if (opennedSession!=null)
		{
			opennedSession.close();
		}
			
		return idCount.longValue();			
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private long countMax_with_APICriteria (Class<T> type, Session opennedSession) {
		
		Criteria criteria = opennedSession.createCriteria(type).setProjection(Projections.max("ikey"));
				
		Long idCount = (Long)criteria.uniqueResult();
		if(idCount==null){
			idCount= Long.parseLong("0");
		}
		
			
		return idCount.longValue();			
	}
	
	
	

	
	private long rowCountMax_with_JPACriteria (Class<T> type, Session opennedSession) {
				
		CriteriaBuilder builder = opennedSession.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery( Long.class );
		Root<T> root = criteria.from(type);
		criteria.multiselect(builder.count(root.get("ikey")));
		
		Long idCount = opennedSession.createQuery(criteria).getResultList().get(0);
				
		if(idCount==null){
			idCount= Long.parseLong("0");
		}
		
			
		return idCount.longValue();			
	}


	
	@SuppressWarnings("unchecked")
	public List<T> geoQuery(String key, Class<T> type, long meters, DatasetSearch<T> search, int page, int pageSize, List<String> fieldsQuery, List<Sort> orders) throws DAOException
	{		
		List<T> result = new ArrayList<T>();
		
		String xColumnName="";
		String yColumnName="";
		String className=type.getName();
		
		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		
		if ((StartVariables.mapaClasesColumnas.get(className))!=null)
		{
			xColumnName=StartVariables.mapaClasesColumnas.get(className).get(Constants.X.toLowerCase());
			yColumnName=StartVariables.mapaClasesColumnas.get(className).get(Constants.Y.toLowerCase());
		}  
		
		//VBLE para crear el alias de la distancia
		String formulaGeo="";
		try
		{
			List<Criterion> criterios = new ArrayList<Criterion>();
			Criteria criteria = null;
			Session opennedSession = null;

			if (multipleSessionFactory.getKeys().contains(key))
			{
				log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
				criterios = search.obtenerCriterios(multipleDataSource.getDrivers().get(key),key);
				//Los criterios geograficos aquí no se utilizan para buscar, son el punto inicial de la búsqueda
				eliminaCriteriosGeograficos(criterios);
				
				opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
				criteria = opennedSession.createCriteria(type);
				String geoSQL=search.obtenerCondicionGeografica(xColumnName, yColumnName,  meters);
				criteria.add(Restrictions.sqlRestriction(geoSQL));
				formulaGeo=geoSQL;
			} 
			else
			{
				criterios = search.obtenerCriterios(env.getProperty(Constants.DB_DRIVER),key);
				//Los criterios geograficos aquí no se utilizan para buscar, son el punto inicial de la búsqueda
				eliminaCriteriosGeograficos(criterios);				
				
				criteria = sessionFactory.getCurrentSession().createCriteria(type);
				String geoSQL=search.obtenerCondicionGeografica(xColumnName, yColumnName, meters);
				criteria.add(Restrictions.sqlRestriction(geoSQL));
				formulaGeo=geoSQL;
			}

			for (Criterion criterioTemp : criterios)
			{
				criteria.add(criterioTemp);
			}
			
			//Obtenemos la query para modificar o añadir un nuevo campo
			formulaGeo = formulaGeo.substring(0,formulaGeo.indexOf("<="));
			

			if (page >= 0)
			{
				criteria.setFirstResult(page * pageSize);
				criteria.setMaxResults(pageSize);
				ProjectionList projectionList = Projections.projectionList();
				// Tratamiento de Fields
				if (fieldsQuery != null && !fieldsQuery.isEmpty())
				{
					
					for (String field : fieldsQuery)
					{
						if (!Constants.DISTANCE.equals(field)) {//Mientras no sea distance se genera proyección, distance se incluye al final.
							projectionList.add(Projections.property(field).as(field), field);
						}
					}
					
					//CMG: INCLUIMOS LA DISTANCIA EN LAS PROYECCIONES;
					projectionList.add( Projections.sqlProjection( 
							formulaGeo+" as "+Constants.DISTANCE, 
		                    new String[] { Constants.DISTANCE }, 
		                    new Type[]{ new DoubleType()}
		                  ) 
		            ) ;
					
					criteria.setProjection(projectionList);
					
					criteria.setResultTransformer(new AliasToBeanResultTransformer(type));
				}else {
					//PARA PODER INCLUIR LA DISTANCIA NECESITAMOS CREAR PROYECCIONES DE TODOS LOS CAMPOS
					
					List <String> fields = Util.extractPropertiesFromBean(type);
 					
					for (String field : fields)
					{
						//CORREGIR LOS CAMPOS
						if  (!Constants.TYPE_CLASS_CLASS.toLowerCase().contains(field)) {
							if (!Constants.DISTANCE.equals(field) 
								&& !Util.exitString(Constants.FIELDS_GEO_NO_PROJECTION_INCLUDE, field))
							{//Mientras no sea distance y los campos geo que no deben incluirse en proyecciones se genera proyección, distance se incluye al final.
								projectionList.add(Projections.property(field).as(field), field);
							}
						}
					}
					//CMG: INCLUIMOS LA DISTANCIA EN LAS PROYECCIONES;
					projectionList.add( Projections.sqlProjection( 
							formulaGeo+" as "+Constants.DISTANCE, 
		                    new String[] { Constants.DISTANCE }, 
		                    new Type[]{ new DoubleType()}
		                  ) 
		            ) ;
					
					criteria.setProjection(projectionList);
					
					criteria.setResultTransformer(new AliasToBeanResultTransformer(type));
				}

				
				
				//CMG: Tratamiento del order //Actualmente no puede ser nulo ya que forzamos a que como minimo llegue id
				if (orders != null && !orders.isEmpty())
				{
					
					for (Sort sort : orders)
					{
						if (sort.isDesc())
						{
							//CMG: Controlamos el caso especifico de distance
							if (Constants.DISTANCE.equals(sort.getProperty())) {
								criteria.addOrder(OrderBySqlFormula.sqlFormula(formulaGeo + " desc"));
							}else {
								criteria.addOrder(Order.desc(sort.getProperty()));
							}
							
						} else
						{
							//CMG: Controlamos el caso especifico de distance
							if (Constants.DISTANCE.equals(sort.getProperty())) {
								criteria.addOrder(OrderBySqlFormula.sqlFormula(formulaGeo + " asc"));
							}else {
								criteria.addOrder(Order.asc(sort.getProperty()));
							}
							
						}
						
					}
					// Esto es para prevenir el bug de paginación en Oracle
					if (Util.getDatabaseTypeFromDriver(env.getProperty(Constants.DB_DRIVER)).equals(Constants.ORACLE))
					{
						if (orders.toString().indexOf("id") < 0)
						{
							criteria.addOrder(Order.asc("id"));
						}
					}										

				}				
								
				result = criteria.list();
				//List list = criteria.list();
				
				if (opennedSession != null)
				{
					opennedSession.close();
				}
			}
		} catch (Exception e)
		{
			String msg = "executeSelect(query) [Hibernate Exception]:" + e.getMessage();
			log.error(msg, e);
			throw new DAOException(Constants.INTERNAL_ERROR);
		}
		return result;
	}

	private void eliminaCriteriosGeograficos(List<Criterion> criterios)
	{
		List<Criterion> borrarCriterios = new ArrayList<Criterion>();
		for (Criterion c:criterios)
		{
			if ((c.toString().toLowerCase().indexOf(Constants.X.toLowerCase())==0)||(c.toString().toLowerCase().indexOf(Constants.Y.toLowerCase())==0))
			{
				borrarCriterios.add(c);
			}
		}
		for (Criterion c:borrarCriterios)
		{
			criterios.remove(c);
		}
	}

	public long geoRowCount(String key, Class<T> type, long meters, DatasetSearch<T> search) throws DAOException
	{
		Criteria criteria = null;
		Session opennedSession = null;
		
		String xColumnName="";
		String yColumnName="";
		String className=type.getName();
		
		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		
		if ((StartVariables.mapaClasesColumnas.get(className))!=null)
		{
			xColumnName=StartVariables.mapaClasesColumnas.get(className).get(Constants.X.toLowerCase());
			yColumnName=StartVariables.mapaClasesColumnas.get(className).get(Constants.Y.toLowerCase());
		}  
		
		
		List<Criterion> criterios = new ArrayList<Criterion>();
		Long rowCount = null;
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();			
			criterios = search.obtenerCriterios(multipleDataSource.getDrivers().get(key),key);	
			//Los criterios geograficos aquí no se utilizan para buscar, son el punto inicial de la búsqueda
			eliminaCriteriosGeograficos(criterios);			
			
			criteria = opennedSession.createCriteria(type);			
			String geoSQL=search.obtenerCondicionGeografica( xColumnName, yColumnName, meters);
			criteria.add(Restrictions.sqlRestriction(geoSQL));
		}else {
			criterios = search.obtenerCriterios(env.getProperty(Constants.DB_DRIVER),key);		
			//Los criterios geograficos aquí no se utilizan para buscar, son el punto inicial de la búsqueda
			eliminaCriteriosGeograficos(criterios);
			
			criteria = sessionFactory.getCurrentSession().createCriteria(type);
			String geoSQL=search.obtenerCondicionGeografica(xColumnName, yColumnName, meters);
			criteria.add(Restrictions.sqlRestriction(geoSQL));
		}
		
		
		try
		{
			for (Criterion criterio: criterios) {
				criteria.add(criterio);
			}
			
			criteria.setProjection(Projections.rowCount());
	        
			
			
			rowCount = (Long)criteria.uniqueResult();
			
			if(rowCount==null){
				rowCount= Long.parseLong("0");
			}
			
			
			if (opennedSession!=null)
			{
				opennedSession.close();
			}
		}
		catch (Exception e)
		{
			String msg="Row count error";
			log.error(msg,e);
			throw new DAOException(msg);
		}
		
		return rowCount.longValue();
	}

	@SuppressWarnings("unchecked")
	public T findByIdentifierObject(String key, Class<T> type, String identifier)
	{

		List<T> result = new ArrayList<T>();

		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		
		String nameEntity=Util.extractNameFromModelClass(type);

		// Criteria criteria = null;
		Session opennedSession = null;

		Query<?> query = null;
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
			query = opennedSession.createQuery("FROM "+nameEntity+" where identifier = :code ");
		} else
		{
			query = sessionFactory.getCurrentSession().createQuery("FROM "+nameEntity+" where identifier = :code ");
		}

		query.setParameter("code", identifier);
		result = (List<T>) query.list();

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
