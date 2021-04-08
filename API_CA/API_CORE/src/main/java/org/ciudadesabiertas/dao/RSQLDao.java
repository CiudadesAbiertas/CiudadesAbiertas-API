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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.tuple.Pair;
import org.ciudadesabiertas.config.multipe.MultipleSessionFactory;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.DifferentSQLforDatabases;
import org.ciudadesabiertas.utils.RegularExpressions;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.Sort;
import org.ciudadesabiertas.utils.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;




/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Repository
public class RSQLDao  {
	
	private static final Logger log = LoggerFactory.getLogger(RSQLDao.class);
	
	@Autowired
    public MultipleSessionFactory multipleSessionFactory;

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	protected Environment env;	
	
	private static Map<String,String>  arraysFieldExpression=new HashMap<String, String>();
	
	static
	{
		arraysFieldExpression.put("clasificacionEconomicaGastoSimple in","generatedAlias0.clasificacionEconomicaGastoSimple");
		arraysFieldExpression.put("clasificacionProgramaSimple in","generatedAlias0.clasificacionProgramaSimple");
	}

	/*
	public EntityManager getEntityManager(String key)
	{
		Session session = null;
		Session openedSession = null;
		EntityManager entityManager = null;
		if (Util.validValue(key)&&(multipleSessionFactory.getFactories().get(key)!=null))
		{
			openedSession=multipleSessionFactory.getFactories().get(key).openSession();
			entityManager = openedSession.getEntityManagerFactory().createEntityManager();
			openedSession.close();
		}else {
			session=sessionFactory.getCurrentSession();
			entityManager = session.getEntityManagerFactory().createEntityManager();
		}
		
		return entityManager;
	}
	*/
	
	/*
	 public <T> CriteriaQuery<T> getCriteriaQuery(String datasetKey, String queryString, RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor) throws BadRequestException {
  	   
	    	EntityManager entityManager=null;
	    	try
	    	{
	    		entityManager=getEntityManager(datasetKey);    	
	    		Node rootNode;
	    		CriteriaQuery<T> query;

	    		Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();    	
	    		
	    		rootNode = new RSQLParser(operators).parse(queryString);
	    		    		  		
	    		query = rootNode.accept(visitor, entityManager); 	    	       		
	    		
	    		return query;
	    	}
	    	catch (Exception e)
	    	{
	    		String msg="Error translating RSQL query to HQL query";
	    		log.error(msg,e);
	    		throw new BadRequestException(msg);
	    	}finally {
	    		entityManager.close();
	    	}
	    }
	  */ 
	

     
    

    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public <T>Result runQuery(String key, RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor, String queryString, int numPage, int numPageSize, List<Sort> orders) throws BadRequestException, DAOException
    {
    	Result result=new Result();
    	
    	List resultList = new ArrayList<T>();
    	
    	//Se necesita una sesion y un entity manager propio para ejecutar la select final  (persistenceQuery)
    	
		Session session = null;
		Session openedSession = null;
		Session persistenceSession = null;		
		EntityManager entityManager = null;
		EntityManager entityManagerPersistence = null;
		int total=0;
		
		if (Util.validValue(key)&&(multipleSessionFactory.getFactories().get(key)!=null))
		{
			openedSession=multipleSessionFactory.getFactories().get(key).openSession();
			entityManager = openedSession.getEntityManagerFactory().createEntityManager();			
			persistenceSession=multipleSessionFactory.getFactories().get(key).openSession();
			entityManagerPersistence = persistenceSession.getEntityManagerFactory().createEntityManager();
			openedSession.close();
		}else {
			session=sessionFactory.getCurrentSession();			
			persistenceSession=sessionFactory.openSession();
			entityManager = session.getEntityManagerFactory().createEntityManager();
			entityManagerPersistence = persistenceSession.getEntityManagerFactory().createEntityManager();
		}
    		
        	
    	try
    	{
    		Node rootNode;
    		CriteriaQuery<T> query;

    		Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();  
    		
    		rootNode = new RSQLParser(operators).parse(queryString);
    		    	
  		  	query = rootNode.accept(visitor, entityManager);
    		
    		 if (query.getRoots().size()>0)
	         {
	         	Root root=query.getRoots().iterator().next();         	
	         	//Tratamiento de Order
	 	        if (orders.size()>0)
	 	        {	        
	 		        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	 		        
	 		        List<javax.persistence.criteria.Order> orderList=new ArrayList<javax.persistence.criteria.Order>();
	 		        if (orders!=null && !orders.isEmpty()) {
	 					for (Sort sort: orders) {
	 						if (sort.isDesc()) {
	 							orderList.add( cb.desc(root.get(sort.getProperty())));
	 						}else {					
	 							orderList.add( cb.asc(root.get(sort.getProperty())));
	 						}
	 					}			
	 				}        
	 		        
	 		        query.orderBy(orderList);	        
	 	        }
	         }
    		

    		TypedQuery<T> typedQuery = entityManager.createQuery(query);    		
    		Query<T> unwrapQuery = typedQuery.unwrap(Query.class);
    		
    		String sqlString=unwrapQuery.getQueryString();
    		log.info("original SQL: "+sqlString);
    		
    		if(transfromQueryForDriver())
    		{
	    		if (sqlString.contains("lower("))
	    		{
	    			sqlString=DifferentSQLforDatabases.rsqlTransFormLower(sqlString, env.getProperty(Constants.DB_DRIVER), key);
	    			log.debug("translated SQL: "+sqlString);
	    		}
	    		if (sqlString.contains("BD"))
	    		{
	    			List<Pair<String, String>> removeSuffixBDInNumbers = RegularExpressions.removeSuffixBDInNumbers(sqlString);
	    			for (Pair<String,String> p:removeSuffixBDInNumbers)
	    			{
	    				sqlString=sqlString.replace(p.getLeft(), p.getRight());
	    			}
	    			log.info("translated SQL: "+sqlString);
	    		}
	    		//en el if pongo =in= pero lo que pasamos es el sqlString con ' in'  (cuidado con esto)
	    		if (queryString.contains("=in="))
	    		{
	    			sqlString=DifferentSQLforDatabases.rsqlTransFormInOut(sqlString, env.getProperty(Constants.DB_DRIVER)," in ", key);
	    			log.info("translated SQL: "+sqlString);
	    		}
	    		//en el if pongo =out= pero lo que pasamos es el sqlString con ' not in'  (cuidado con esto)
	    		if (queryString.contains("=out="))
	    		{
	    			sqlString=DifferentSQLforDatabases.rsqlTransFormInOut(sqlString, env.getProperty(Constants.DB_DRIVER)," not in ", key);
	    			log.info("translated SQL: "+sqlString);
	    		}
    		}
    		
    		Set<Parameter<?>> namedParameters = unwrapQuery.getParameters();
    		Map<String,Object> parameterMap=new HashMap<String,Object>();
    		
    		for (Parameter<?> p:namedParameters)
    		{
    			
    			Object parameterValue = unwrapQuery.getParameterValue(p.getName());
    			
    			if (transfromQueryForDriver())
				{
    				if (parameterValue instanceof String)
        			{
        				log.debug(parameterValue.toString());	
        				parameterValue=((String) parameterValue).toUpperCase();
        				parameterValue=DifferentSQLforDatabases.rsqlRemoveAccentsForParams(parameterValue.toString());
        			}	
				}
    			
    			log.info("param "+p.getName()+":"+parameterValue);
    			parameterMap.put(p.getName(), parameterValue);
    		}   
    		
    		//Tratamiento para campos que son arrays de String
    		for (String arrayFieldKey:arraysFieldExpression.keySet())
    		{
	    		if (sqlString.contains(arrayFieldKey))
	    		{
	    			String[] split = sqlString.split(arrayFieldKey);
	    			String columnName=arraysFieldExpression.get(arrayFieldKey);
	    			String queryTranslated="";
	    			for (int i=1;i<split.length;i++)
	    			{
	    				String condition=split[i];  
	    				String params=condition.substring(condition.indexOf("(")+1);
	    				params=params.substring(0,params.indexOf(")"));	
	    				String[] paramsSplitted =params.split(",");
	    				
	    				String toReplace=columnName+" in ("+params+")";
	    				for (String param:paramsSplitted)
	    				{    					
	    					param=param.replace(":", "");
	    					param=param.trim();
	    					String sqlRestriction="";
	    					String paramValue=(String) parameterMap.get(param);
	    					
	    					sqlRestriction="( "+columnName+" like '"+paramValue+"' or ";
							sqlRestriction+=columnName+" like '"+paramValue+",%' or ";
							sqlRestriction+=columnName+" like '%,"+paramValue+",%' or ";
							sqlRestriction+=columnName+" like '%,"+paramValue+"' )";		
							if (queryTranslated.equals(""))
							{
								queryTranslated+=sqlRestriction;
							}else {
								queryTranslated+=" and "+sqlRestriction;
							}
	    				}  				
	    				sqlString=sqlString.replace(toReplace, queryTranslated);
	    			}
	    		}
    		}
    		
    		
    		javax.persistence.Query createQuery = entityManagerPersistence.createQuery(sqlString);
    		
    		 
    	       		
    		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
    		    //System.out.println(entry.getKey() + "/" + entry.getValue());
    			
    			if (sqlString.contains(":"+entry.getKey()))
    			{
    				createQuery.setParameter(entry.getKey(), entry.getValue());
    			}
    		}
    		
    		total = createQuery.getResultList().size();
    		
    		//Restamos 1 a la pagina, porque la primera ahora es 1         
    		createQuery.setFirstResult((numPage-1) * numPageSize);
    		createQuery.setMaxResults(numPageSize);
    		
    		resultList = createQuery.getResultList();
    		
    	}
    	catch (Exception e)
    	{
    		String msg="Error translating RSQL query to HQL query";
    		log.error(msg,e);
    		throw new BadRequestException(msg);
    	}  
    	finally
    	{
    		if (openedSession != null)
			{
    			openedSession.close();
			}
    		persistenceSession.close();
    		entityManagerPersistence.close();
    	}
		 
        result.setRecords(resultList);
        result.setPage(numPage);
        result.setPageSize(numPageSize);
        result.setTotalRecords(total);
                  
         
         return result;
    }
	
    
    private boolean transfromQueryForDriver()
    {
    	return (env.getProperty(Constants.DB_DRIVER).contains(Constants.ORACLE))||env.getProperty(Constants.DB_DRIVER).contains(Constants.SQLSERVER);
    }
}