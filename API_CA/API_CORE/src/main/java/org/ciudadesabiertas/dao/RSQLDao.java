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
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ciudadesabiertas.config.multipe.MultipleSessionFactory;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.Sort;
import org.ciudadesabiertas.utils.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    

    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public <T>Result runQuery(String datasetKey, RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor, String queryString, int numPage, int numPageSize, List<Sort> orders) throws BadRequestException, DAOException
    {
    	Result result=new Result();
    	
    	CriteriaQuery<T> query;
    	
    	 EntityManager entityManager = null;
		
    	try
		{
    		query = getCriteriaQuery(datasetKey, queryString, visitor);
		} 
    	catch (BadRequestException e)
		{   
    		throw new BadRequestException("Wrong query");
		}
    	 
    	
    	try
    	{
    	   entityManager=getEntityManager(datasetKey);
         
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
         
         
         //Extraemos el total
         log.debug("Before RSQL rowcount");
         int total = entityManager.createQuery(query).getResultList().size();        
         log.debug("After RSQL rowcount");
         
         TypedQuery typedQuery = entityManager.createQuery(query);
         //Restamos 1 a la pagina, porque la primera ahora es 1         
         typedQuery.setFirstResult((numPage-1) * numPageSize);
         typedQuery.setMaxResults(numPageSize);
         
         List resultList = typedQuery.getResultList();
         
         result.setRecords(resultList);
         result.setPage(numPage);
         result.setPageSize(numPageSize);
         result.setTotalRecords(total);
         
         
         
    	}
    	catch (Exception e)
    	{
    		String msg="Error in RSQL query";
    		log.error(msg,e);
    		throw new DAOException(msg);
    	}finally {
    		entityManager.close();
    	}
                  
                  
         
         return result;
    }
	
}