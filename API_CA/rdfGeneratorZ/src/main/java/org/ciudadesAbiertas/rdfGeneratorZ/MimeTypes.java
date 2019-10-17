package org.ciudadesAbiertas.rdfGeneratorZ;

import java.util.HashMap;
import java.util.Map;


import org.springframework.http.MediaType;

public class MimeTypes {

	public static final String JSONP_EXTENSION = "jsonp";
	public static final String JSON_EXTENSION = "json";
	public static final String JSONLD_EXTENSION = "jsonld";
	public static final String GEOJSON_EXTENSION = "geojson";
	public static final String XML_EXTENSION = "xml";
	public static final String XHTML_EXTENSION = "xhtml";
	public static final String HTML_EXTENSION = "html";
	public static final String RDF_EXTENSION = "rdf";
	public static final String TURTLE_EXTENSION = "ttl";
	public static final String RDF_N3_EXTENSION = "n3";
	public static final String SPARQL_XML_EXTENSION = "sparqlxml";
	public static final String SPARQL_JSON_EXTENSION = "sparqljson";
	
	public static final String SOLR_XML_EXTENSION = "solrxml";
	public static final String SOLR_JSON_EXTENSION = "solrjson";
	public static final String ICS_EXTENSION = "ics";
	
	public static final String GEORSS_EXTENSION = "georss";
	public static final String RSS_EXTENSION = "rss";
	
	public static final String CSV_EXTENSION = "csv";
	
	public static final String PDF_EXTENSION = "pdf";
	public static final String DOC_EXTENSION = "doc";
	public static final String XLS_EXTENSION = "xls";
	
	public static final String JSONP = "text/x+javascript";
	public static final String JSON = "application/json";
	public static final String JSONLD = "application/ld+json";
	public static final String GEOJSON = "application/geo+json";
	public static final MediaType GEOJSON_MEDIA = new MediaType("application", "geo+json");
	public static final String XML = "application/xml";
	public static final String XHTML = "application/xhtml+xml";
	public static final String HTML = "text/html";
	public static final String RDF = "application/rdf+xml";
	public static final String TURTLE = "application/x-turtle";
	public static final String RDF_N3 = "text/rdf+n3";
	public static final MediaType RDF_N3_MEDIA = new MediaType("text", "rdf+n3");
	public static final String SPARQL_XML = "application/sparql-results+xml";
	public static final String SPARQL_JSON = "application/sparql-results+json";
	public static final String ICS = "text/calendar";
	public static final MediaType ICS_MEDIA = new MediaType("text", "calendar");
	
	
	public static final String SOLR_XML = "application/solr-results+xml";
	public static final String SOLR_JSON = "application/solr-results+json";
	public static final MediaType SOLR_JSON_MEDIA = new MediaType("application", "solr-results+json");
	public static final String GEORSS = "text/xml+georss";
	public static final String RSS = "application/rss+xml";

	public static final MediaType RSS_MEDIA = new MediaType("application", "rss+xml");
	public static final String CSV = "text/csv";
	public static final MediaType CSV_MEDIA = new MediaType("text", "csv");
	
	public static final String PDF = "application/pdf";
	public static final MediaType PDF_MEDIA = new MediaType("application", "pdf");
	public static final String XLS = "application/vnd.ms-excel";
	public static final MediaType XLS_MEDIA = new MediaType("application", "vnd.ms-excel");
	
	public static final String DOC = "application/msword";
	public static final MediaType DOC_MEDIA = new MediaType("application", "msword");
	
	public static final MediaType APPLICATION_JSONLD = new MediaType("application", "ld+json");
	
	
	
	
	

    public static final Map<String, Formato> extensiones = new HashMap<String, Formato>();
	
   
  
}
