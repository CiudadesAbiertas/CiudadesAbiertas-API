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

package org.ciudadesabiertas.dataset.busquedaIndexada.service;





import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.ciudadesabiertas.dataset.busquedaIndexada.util.BusquedaIndexadaResult;
import org.ciudadesabiertas.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 

 
/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Service("solrService")
@Transactional
public class BusquedaIndexadaService {
	
	private static final Logger log = LoggerFactory.getLogger(BusquedaIndexadaService.class);
    
	private HttpSolrClient solrClient;
	
	private String username="";
	private String password="";
	
	@Autowired
	private Environment env;
	
    public BusquedaIndexadaResult query(String dataset, String query,  int page, int pageSize) throws Exception {
    	
    	log.debug("[query][dataset:"+dataset+"][query:"+query+"]");  
    	
    	if (solrClient==null)
    	{
    		if (Util.validValue(env.getProperty("solr.coreURL")))
    		{    		
    			if (env.getProperty("solr.username")!=null)
    			{
    				username=env.getProperty("solr.username").trim();
    			}
    			if (env.getProperty("solr.password")!=null)
    			{
    				password=env.getProperty("solr.password").trim();
    			}
    			 
    			generateSolrClient();   			
    			
    			try 
    			{
    				SolrPingResponse ping = null;
    		    	if (Util.validValue(username)||Util.validValue(password))
    				{
    		    		SolrPing solrPing = new SolrPing();
    		    		solrPing.setBasicAuthCredentials(username,password);    		    		
    		    		ping = solrPing.process(solrClient, null);
    				}
    		    	else 
    		    	{
    		    		ping = solrClient.ping(); 
    		    	}
    		    	if (ping.getStatus() == 0) {
		    			log.info("Pinged Solr in {}", ping.getQTime());
		    		}    		    	
    		    	
    	        } catch (SolrServerException | IOException e) {
    	            log.error("Cannot connect to solr server", e);    	       
    	            solrClient=null;
    	            throw new Exception("The index server is not available",e);
    	        } 
    		}
    	}
    	
    	//Restamos 1 a la pagina, porque la primera es 1
    	page--;
    	
    	String filterQuery="";
    	String realQuery="*:*";    	
    	if (Util.validValue(dataset))
    	{
    		filterQuery="datasetName:"+dataset.trim();
    	}
    	if (Util.validValue(query))
    	{
    		realQuery=query.trim();
    	}
    	
    	//asusoldjc:8983/solr/ciudadesAbiertas/select?fq=datasetName:agenda&q="pintura"
    	
    	SolrQuery solrQuery = new SolrQuery();
    	solrQuery.setQuery(realQuery);
    	
    	if (!filterQuery.equals(""))
    	{
    		solrQuery.setFilterQueries(filterQuery);
    	}
    	solrQuery.setStart(page*pageSize);
    	solrQuery.setRows(pageSize);
    	 
    	QueryResponse response = null;
    	try
    	{
	    	if (Util.validValue(username)||Util.validValue(password))
			{
	    		  SolrRequest<QueryResponse> req = new QueryRequest(solrQuery);
				  req.setBasicAuthCredentials(username, password);
				  response=req.process(solrClient);	
			}
	    	else 
	    	{
	    		response = solrClient.query(solrQuery);
	    	}
    	}    	
    	catch (Exception e)
    	{
    		log.error("Error quering solr",e);
    	}
    	catch (Throwable e)
    	{
    		log.error("Error quering solr",e);
    	}
    	
    	BusquedaIndexadaResult solrResult=new BusquedaIndexadaResult();
    	solrResult.setPage(page+1);
    	solrResult.setPageSize(pageSize);
    	
    	if (response!=null)
    	{
    		List<Map<String, Object>> docs=new ArrayList<Map<String,Object>>();
    		SolrDocumentList results = response.getResults();
    		solrResult.setTotalRecords(results.getNumFound());
    		for (SolrDocument doc:results)
    		{	
    			HashMap<String, Object> actualDoc=new HashMap<String,Object>();
    			Collection<String> fieldNames = doc.getFieldNames();
    			for (String field:fieldNames)
    			{
    				if (doc.get(field) instanceof String[])
    				{
    					if ( ((String[])doc.get(field)).length==1)
						{
							actualDoc.put(field, ((String[])doc.get(field))[0]);
						}
    				}
    				else 
    				{
    					actualDoc.put(field, doc.get(field));
    				}
    			}
    			docs.add(actualDoc);
    		}
    		solrResult.setRecords(docs);
    		solrResult.setPageRecords(docs.size());
    		
        	
        	    	
    	}
    	
    	
    	return solrResult;

    }
    
    public BusquedaIndexadaResult searchDataset() throws Exception {
    	
    	log.debug("[searchDataset]");  
    	
    	if (solrClient==null)
    	{
    		if (Util.validValue(env.getProperty("solr.coreURL")))
    		{    		
    			if (env.getProperty("solr.username")!=null)
    			{
    				username=env.getProperty("solr.username").trim();
    			}
    			if (env.getProperty("solr.password")!=null)
    			{
    				password=env.getProperty("solr.password").trim();
    			}
    			 
    			generateSolrClient();   			
    			
    			try 
    			{
    				SolrPingResponse ping = null;
    		    	if (Util.validValue(username)||Util.validValue(password))
    				{
    		    		SolrPing solrPing = new SolrPing();
    		    		solrPing.setBasicAuthCredentials(username,password);    		    		
    		    		ping = solrPing.process(solrClient, null);
    				}
    		    	else 
    		    	{
    		    		ping = solrClient.ping(); 
    		    	}
    		    	if (ping.getStatus() == 0) {
		    			log.info("Pinged Solr in {}", ping.getQTime());
		    		}    		    	
    		    	
    	        } catch (SolrServerException | IOException e) {
    	            log.error("Cannot connect to solr server", e);    	       
    	            solrClient=null;
    	            throw new Exception("The index server is not available",e);
    	        } 
    		}
    	}
    	
    	
    	
    	    	
    	//asusoldjc:8983/solr/ciudadesAbiertas/select?facet.field=datasetName&facet.query=datasetName&facet=on&fl=datasetName&q=*%3A*
    	//Lógica para obtener la busqued facetada de los datasetname cargados en solr
    	SolrQuery solrQuery = new SolrQuery();
    	solrQuery.setQuery("*:*");
    	solrQuery.setFacet(true);
    	solrQuery.add("facet.field","datasetName");
    	solrQuery.add("facet.query","datasetName");    	
    	solrQuery.add("fl","datasetName");     	
    	
   
    	 
    	QueryResponse response = null;
    	try
    	{
	    	if (Util.validValue(username)||Util.validValue(password))
			{
	    		  SolrRequest<QueryResponse> req = new QueryRequest(solrQuery);
				  req.setBasicAuthCredentials(username, password);
				  response=req.process(solrClient);	
			}
	    	else 
	    	{
	    		response = solrClient.query(solrQuery);
	    	}
    	}    	
    	catch (Exception e)
    	{
    		log.error("Error quering solr",e);
    	}
    	catch (Throwable e)
    	{
    		log.error("Error quering solr",e);
    	}
    	
    	BusquedaIndexadaResult solrResult=new BusquedaIndexadaResult();
    
    	
    	if (response!=null)
    	{
    		List<Map<String, Object>> docs=new ArrayList<Map<String,Object>>();
    		//Lógica para obtener la busqued facetada de los datasetname cargados en solr
    		final List<FacetField> facetFields = response.getFacetFields();
    		if (facetFields!=null && !facetFields.isEmpty()) {
	    		solrResult.setTotalRecords(facetFields.size());
	    		for (FacetField facet:facetFields)
	    		{	
	    			
	    			String fieldName = facet.getName();       			
	    			List<Count> listado = facet.getValues();
	    			for (Count id:listado) {
	    				String value =id.getName();
	    				HashMap<String, Object> actualDoc=new HashMap<String,Object>();
	    				actualDoc.put(fieldName, value);
	    				docs.add(actualDoc);
	    			}
	    			    			
	    			
	    			
	    		}
    		}
    		solrResult.setRecords(docs);
    		solrResult.setPageRecords(docs.size());
    		        	
        	    	
    	}
    	
    	
    	return solrResult;



    }

	@SuppressWarnings("deprecation")
	private void generateSolrClient()
	{
		log.info("Generating solrClient");
		solrClient = new HttpSolrClient.Builder(env.getProperty("solr.coreURL")).build();
		
		int timeout=10000;
		if (Util.validValue(env.getProperty("solr.timeout")))
		{
			try
			{
				timeout=Integer.parseInt(env.getProperty("solr.timeout"));
			}
			catch (NumberFormatException e)
			{
				log.error("Error parsing solr timeout",e);
			}
		}    			
		solrClient.setSoTimeout(timeout);
		solrClient.setConnectionTimeout(timeout);
	}
    
    
	 
}