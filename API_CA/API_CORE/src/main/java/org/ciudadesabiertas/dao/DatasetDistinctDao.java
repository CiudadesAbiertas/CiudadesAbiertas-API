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

import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.DistinctSearch;
import org.ciudadesabiertas.utils.LiteralConstants;
import org.ciudadesabiertas.utils.Util;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 */
@Repository
public class DatasetDistinctDao<T> extends DatasetDao<T>
{
	
	private static final Logger log = LoggerFactory.getLogger(DatasetDistinctDao.class);
	
	
	
	
	@SuppressWarnings("unchecked")
	public  List<Object> distinctSearch(String key,Class<T> type, DistinctSearch search, int page, int pageSize) throws DAOException, BadRequestException {

		List<Object> result = new ArrayList<Object>();
		Session opennedSession = null;
		
		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		String nameClass=Util.extractNameFromModelClass(type);
		
		String query ="";
				
		
		if (page>=0)
		{
			
			try 
			{
				Query<?> queryObj = null;
				if (multipleSessionFactory.getKeys().contains(key))
				{
					log.debug("[distinctSearch]" +LiteralConstants.TXT_CUSTOM_CONECT);				
					opennedSession = multipleSessionFactory.getFactories().get(key).openSession();
					
					query = search.createQuery(nameClass);
					
					log.info("[distinctSearch] [Distinct query: "+query+"]");
											
					queryObj = opennedSession.createQuery(query);						
				}else {
					
					query = search.createQuery(nameClass);				
					
					log.info("[distinctSearch] [Distinct query: "+query+"]");
					
					queryObj = sessionFactory.getCurrentSession().createQuery(query);	
				}
					
				queryObj.setFirstResult(page * pageSize);
				queryObj.setMaxResults(pageSize);		
							
				result =   (List<Object>) queryObj.list();
								
								
					
			}catch ( SQLGrammarException |QuerySyntaxException | IllegalArgumentException |  NullPointerException e1)
			{
				String msg="executeSelect(query) [Hibernate Exception]:" + e1.getMessage() + " [query Fail:"+query+"]";
				log.error(msg,e1);
				throw new BadRequestException("Wrong parameters in the petition");			
			}
			catch (Exception e1)
			{			
				if (e1.getCause().toString().contains("SQLGrammarException"))
				{
					throw new BadRequestException("Wrong parameters in the query");
				}
				String msg="executeSelect(query) [Hibernate Exception]:" + e1.getMessage() + " [query Fail:"+query+"]";
				log.error(msg,e1);
				throw new DAOException(Constants.INTERNAL_ERROR);			
			}finally {
				if (opennedSession!=null)
				{
					opennedSession.close();
				}	
			}
		
		}
		return result;		

	}
	
	
	
	
	public long rowCountDistinct(String key,Class<T> type, DistinctSearch search) throws DAOException {
		
		long size=0;	
		Session opennedSession = null;
		
		if (key==null || "".equals(key)) {
			key = Util.extractKeyFromModelClass(type);
		}
		
		String nameClass=Util.extractNameFromModelClass(type);
						
		
		Query<?> queryObj = null;
		if (multipleSessionFactory.getKeys().contains(key))
		{
			log.debug(LiteralConstants.TXT_CUSTOM_CONECT);
			
			String query =search.createRowCountQuery(nameClass);
			
			log.info("Distinct Count Query: "+query);			
			
			opennedSession = multipleSessionFactory.getFactories().get(key).openSession();				
			queryObj  = opennedSession.createQuery(query);
		}else {
			
			
			String query =search.createRowCountQuery(nameClass);
			
			log.info("Distinct Count Query: "+query);
			
			queryObj  = sessionFactory.getCurrentSession().createQuery(query);
		}
		
		if (queryObj.getResultList().size()>0)
		{
			size=(Long)queryObj.getResultList().get(0);
		}
		
		if (opennedSession!=null)
		{							
			opennedSession.close();
		}	
		
		return size;
	}


	

}


