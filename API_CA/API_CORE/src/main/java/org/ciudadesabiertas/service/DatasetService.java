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

package org.ciudadesabiertas.service;




import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;


import org.ciudadesabiertas.dao.DatasetDao;
import org.ciudadesabiertas.dao.DatasetDistinctDao;
import org.ciudadesabiertas.dao.DatasetGroupDao;
import org.ciudadesabiertas.dao.RSQLDao;
import org.ciudadesabiertas.exception.BadRequestException;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.GroupBySearch;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.SearchBasic;
import org.ciudadesabiertas.utils.DatasetSearch;
import org.ciudadesabiertas.utils.DistinctSearch;
import org.ciudadesabiertas.utils.Sort;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.jirutka.rsql.parser.ast.RSQLVisitor;
 

 
/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 */
@Service("datasetService")
@Transactional
public class DatasetService<T>  {
	
	private static final Logger log = LoggerFactory.getLogger(DatasetService.class);
     
	
	@Autowired
	private DatasetDao<T> datasetDao;
	
	@Autowired
	private DatasetGroupDao<T> datasetGroupDao;
	
	@Autowired
	private DatasetDistinctDao<T> datasetDistinctDao;
	

	
	@Autowired
	private RSQLDao rsqlDao;
 
	@Transactional(readOnly = true)
	public List<T> query(String key, Class<T> type, DatasetSearch<T> search, int page, int pageSize,List<String> fieldsQuery,List<Sort> orders) throws DAOException {
		log.debug("[query][page:"+page+"][pageSize:"+pageSize+"][order:"+orders+"]");  
    	//Restamos 1 a la pagina, porque la primera ahora es 1
    	page--;
    	return datasetDao.query(key, type,search, page, pageSize, fieldsQuery, orders);
	}

	@Transactional(readOnly = true)
	public List<T> query(Class<T> type, DatasetSearch<T> search, int page, int pageSize,List<String> fieldsQuery,List<Sort> orders) throws DAOException {
    	log.debug("[query][page:"+page+"][pageSize:"+pageSize+"][order:"+orders+"]");  
    	//Restamos 1 a la pagina, porque la primera ahora es 1
    	page--;
    	return datasetDao.query( type,search, page, pageSize, fieldsQuery, orders);
    }
	
	
    
	@Transactional(readOnly = true)
    public T findById(String key, Class<T> type, String id) {
    	log.debug("[findById][key:"+key+"][id:"+id+"]");
        return datasetDao.findByIdObject(key, type, id);
    }
     
         
    public void save(String key, T obj) throws DAOException {  
    	log.debug("[save][key:"+key+"][equipamiento:"+obj+"]");
    	
    	datasetDao.add(key, setIkey(key,obj) );
    }
      
    public void update(String key,T obj) throws DAOException {
    	log.debug("[update][key:"+key+"][equipamiento:"+obj+"]");
    	datasetDao.update(key, obj);
    }
    
    
    public void delete(String key,T obj) throws DAOException {
    	log.debug("[delete][key:"+key+"][equipamiento:"+obj+"]");
    	datasetDao.remove(key, obj);
    }
 
    @Transactional(readOnly = true)  
    public long rowcount(String key,Class<T> type,DatasetSearch<T> search) throws DAOException {
    	log.debug("[rowcount][key:"+key+"][type:"+type+"][search:"+search+"]");
    	return datasetDao.rowCount(key,type, search);
    }    
        
    
    @Transactional(readOnly = true)
    public long rowCountGroupBy(String key,Class<T> type,GroupBySearch search) throws DAOException {
    	log.debug("[rowCountGroupBy][key:"+key+"][type:"+type+"][search:"+search+"]");
    	return datasetGroupDao.rowCountGroupBy(key,type, search);
    }
    
    @Transactional(readOnly = true)
    public long rowCountDistinct(String key,Class<T> type, DistinctSearch search) throws DAOException {
    	log.debug("[rowCountGroupBy][key:"+key+"][type:"+type+"][search:"+search+"]");
    	return datasetDistinctDao.rowCountDistinct(key,type, search);
    }
 
   
    
    @Transactional(readOnly = true)
    public List<Object> groupBySearch(String key,Class<T> type,GroupBySearch groupBySearch, int page, int pageSize) throws DAOException, BadRequestException {
    	log.debug("[search][key:"+key+"][SearchAgrupada:"+groupBySearch+"]");
    	//Restamos 1 a la pagina, porque la primera ahora es 1
    	page--;
    	return datasetGroupDao.groupBy(key,type, groupBySearch, page, pageSize);
    }
    
    @Transactional(readOnly = true)
    public List<Object> distinctSearch(String key,Class<T> type,DistinctSearch search, int page, int pageSize) throws DAOException, BadRequestException {
    	log.debug("[search][key:"+key+"][search:"+search+"]");
    	//Restamos 1 a la pagina, porque la primera ahora es 1
    	page--;
    	return datasetDistinctDao.distinctSearch(key,type, search, page, pageSize);
    }
    
    /**
     * Metodos exclusivos para los Test del Service
     * @param type
     * @return
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
    public List<T> basicQuery(Class<T> type) throws DAOException
	{
    	return query(type,(DatasetSearch<T>) (new SearchBasic()), Constants.defaultPage, StartVariables.defaultPageSize, null, null);
	}
    
    /**
     * Metodos exclusivos para los Test del Service
     * @param key
     * @param type
     * @return
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<T> basicQuery(String key,Class<T> type) throws DAOException
	{
		return query(key,type,(DatasetSearch<T>)(new SearchBasic()), Constants.defaultPage, StartVariables.defaultPageSize, null, null);
	}
    
	
	


	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Result<T> searchByRSQLQuery(RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor, String datasetKey, String queryString, int numPage, int numPageSize, List<Sort> orders) throws BadRequestException, DAOException {
		
    	log.debug("[searchByQuery][queryString:"+queryString+"][numPage:"+numPage+"][numPageSize:"+numPageSize+"][orders:"+orders+"]");	
        //RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor = new JpaCriteriaQueryVisitor<T>();     
        Result<Object> result= rsqlDao.runQuery(datasetKey,visitor, queryString, numPage, numPageSize, orders);
        
        return (Result<T>) result;
    }
	
	
        
    @SuppressWarnings("unchecked")
   	private T setIkey(String key,T obj) {
       	String id =Long.toString(datasetDao.countMax(key,(Class<T>) obj.getClass())+1);
       	String idGenerated=Util.generateID(id,StartVariables.NODO_PATTERN ,true);
       	obj = (T) Util.setIkey(obj, idGenerated);
       	
       	return obj;
       }


    @Transactional(readOnly = true)
    public List<T> geoQuery(String key, Class<T> type, long meters, DatasetSearch<T> search, int page, int pageSize,List<String> fieldsQuery,List<Sort> orders) throws DAOException {
    	log.debug("[query][page:"+page+"][pageSize:"+pageSize+"][order:"+orders+"]");  
    	//Restamos 1 a la pagina, porque la primera ahora es 1
    	page--;
    	return datasetDao.geoQuery(key, type, meters,search, page, pageSize, fieldsQuery, orders);
	}
    
    @Transactional(readOnly = true)
	public long geoRowcount(String key,Class<T> type, long meters, DatasetSearch<T> search) throws DAOException
	{
		log.debug("[rowcount][key:"+key+"][type:"+type+"][search:"+search+"]");
    	return datasetDao.geoRowCount(key, type, meters, search);
	}

	 
   
 
 
	 
}