package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import java.util.HashMap;
import java.util.Map;

public class Context {
	public static final String ESEQUIP_URI = "http://vocab.linkeddata.es/datosabiertos/def/urbanismo-infraestructuras/equipamiento-municipal#";
	public static final String GEO_URI = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static final String XSD_URI = "http://www.w3.org/2001/XMLSchema#";
	public static final String ORG_URI = "http://www.w3.org/ns/org#";
	public static final String ESSUBV_URI = "http://vocab.linkeddata.es/datosabiertos/def/sector-publico/subvencion#";
	public static final String ESADM_URI ="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#";
	public static final String OWL_URI ="http://www.w3.org/2002/07/owl#";
	public static final String RDF_URI ="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static final String RDFS_URI ="http://www.w3.org/2000/01/rdf-schema#";
	public static final String ESPRESUP_URI ="http://vocab.linkeddata.es/datosabiertos/def/hacienda/presupuesto#";
	public static final String DCT_URI ="http://purl.org/dc/terms/";	
	public static final String SCHEMA_URI ="http://schema.org/";
	public static final String ESAGENDA_URI = "http://vocab.linkeddata.es/datosabiertos/def/cultura-ocio/agenda#";
	public static final String ESTRN_URI = "http://vocab.linkeddata.es/datosabiertos/def/transporte/transporte-publico#";
	public static final String ESCOM_URI = "http://vocab.ciudadesabiertas.es/def/comercio/tejido-comercial#";
	public static final String SOSA_URI = "http://www.w3.org/ns/sosa/";
	public static final String OPEN311_URI = "http://ontology.eil.utoronto.ca/open311#";
	public static final String TIME_URI = "http://www.w3.org/2006/time#";
	public static final String DUL_URI = "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#";
	public static final String ESAIR_URI="http://vocab.linkeddata.es/datosabiertos/def/medio-ambiente/calidad-aire#";
	public static final String ORGES_URI="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/organizacion#";
	public static final String FOAF_URI="http://xmlns.com/foaf/0.1/";
	public static final String ESTURISMO_URI="http://vocab.linkeddata.es/datosabiertos/def/turismo/lugar#";
	public static final String ESALOJ_URI="http://vocab.linkeddata.es/datosabiertos/def/turismo/alojamiento#";
	public static final String ESCJR_URI="http://vocab.linkeddata.es/datosabiertos/def/urbanismo-infraestructuras/callejero#";
	public static final String GEOCORE_URI="http://datos.ign.es/def/geo_core#";
	public static final String ESSRVC_URI = "http://vocab.linkeddata.es/datosabiertos/def/sector-publico/servicio#";
	public static final String QB_URI = "http://purl.org/linked-data/cube#";
	public static final String SKOS_URI = "http://www.w3.org/2004/02/skos/core#";
	public static final String ESPAD_URI = "http://vocab.ciudadesabiertas.es/def/demografia/padron-municipal#";
	public static final String ESPADMEDIDA_URI = "http://vocab.linkeddata.es/def/demografia/padron-municipal/medida#";	                                              
	public static final String IAESTDIMENSION_URI = "https://opendata.aragon.es/def/iaest/dimension#";
	public static final String SDMXDIMENSION_URI = "http://purl.org/linked-data/sdmx/2009/dimension#";
	public static final String SF_URI = "http://www.opengis.net/ont/sf#";
	public static final String GEOSPARQL_URI = "http://www.opengis.net/ont/geosparql#";
	public static final String ESAGM_URI = "http://vocab.ciudadesabiertas.es/def/sector-publico/agenda-municipal#";
	public static final String OCDS_URI = "http://data.tbfy.eu/ontology/ocds#";		
	public static final String ESBICI_URI = "http://vocab.ciudadesabiertas.es/def/transporte/bicicleta-publica#";
	public static final String ESCONV_URI = "http://vocab.ciudadesabiertas.es/def/sector-publico/convenio#";
	public static final String ESTRAF_URI = "http://vocab.ciudadesabiertas.es/def/transporte/trafico#";
	
	public static final String RDF = "rdf";
	public static final String RDFS = "rdfs";
	public static final String VCARD = "vcard";
	public static final String DCT = "dcterms";
	public static final String GR = "gr";
	public static final String GEO = "geo";
	public static final String GEONAMES = "geonames";
	public static final String FOAF = "foaf";
	public static final String ZAR = "zar";
	public static final String XSD= "xsd";
	public static final String ORG= "org";
	public static final String DCAT = "dcat";
	public static final String OWL = "owl";
	public static final String LOCN ="locn";
	public static final String SSN = "ssn";
	public static final String CPSV = "cpsv";

	public static final String ESCJR= "escjr";
	public static final String ESADM= "esadm";
	public static final String ESSUBV= "essubv";
	public static final String ESSRVC= "essrvc";
	public static final String ESPRESUP= "espresup";
	public static final String ESAGENDA= "esagenda";
	public static final String ESTURISMO= "esturismo";
	public static final String ESTUR = "estur";
	public static final String ESALOJ = "esaloj";
	public static final String ESMETEO = "esmeteo";
	public static final String ESCALIDADAIRE = "escalidadaire";
	public static final String ESACCID = "esaccid";
	public static final String ESEQUIP = "esequip";
	public static final String ESNORM = "esnorm";
	public static final String ESCOM = "escom";
	public static final String SOSA = "sosa";
	public static final String CRUZAR = "cruzar";
	public static final String TURZAR = "turzar";
	public static final String MAPEOSEM = "mapeosem";
	public static final String OPEN311 = "open311";
	public static final String ESTRN = "estrn";
	public static final String EVENT = "event";
	public static final String MY= "my";	
	public static final String ORGES= "orges";	
	public static final String GEOSPARQL= "geosparql";	
	public static final String GRAPH= "graph";	
	public static final String SCHEMA= "schema";	
	public static final String SKOS = "skos";	
	public static final String EMP = "emp";	
	public static final String TIME = "time";
	public static final String DUL = "dul";
	public static final String ESAIR = "esair";
	public static final String GEOCORE = "geo_core";
	public static final String QB = "qb";
	public static final String ESPADMEDIDA = "espad-medida";
	public static final String ESPAD = "espad";
	public static final String IAESTDIMENSION = "iaest-dimension";
	public static final String SDMXTDIMENSION = "sdmx-dimension";
	public static final String SF = "sf";
	public static final String ESAGM = "esagm";
	public static final String OCDS = "ocds";
	public static final String ESBICI = "esbici";
	public static final String ESCONV = "esconv";
	public static final String ESTRAF = "estraf";
	
	
	
	
	public static final Map<String, Context> listado = new HashMap<String, Context>();
	
    static { 
    	listado.put(RDF, new Context(RDF_URI, RDF));
        listado.put(RDFS, new Context(RDFS_URI, RDFS));        
        listado.put(VCARD, new Context("http://www.w3.org/2006/vcard/ns#", VCARD));
        listado.put(DCT, new Context(DCT_URI, DCT));
        listado.put(GR, new Context("http://purl.org/goodrelations/v1#", GR));
        listado.put(GEO, new Context(GEO_URI, GEO));
        listado.put(GEONAMES, new Context("http://www.geonames.org/ontology#", GEONAMES));
        listado.put(FOAF, new Context(FOAF_URI, FOAF));
        listado.put(ZAR, new Context("http://www.zaragoza.es/sede/portal/skos/vocab/", ZAR));        
        listado.put(XSD, new Context(XSD_URI, XSD));
        listado.put(ORG, new Context(ORG_URI, ORG));
        listado.put(MY, new Context("http://www.zaragoza.es/example/my#", MY));
        listado.put(GEOSPARQL, new Context(GEOSPARQL_URI, GEOSPARQL));
        listado.put(GRAPH, new Context("http://www.zaragoza.es/graph/default/", GRAPH));
        listado.put(ESCJR, new Context(ESCJR_URI,ESCJR));
        listado.put(ESADM, new Context(ESADM_URI,ESADM));
        listado.put(ESSUBV, new Context(ESSUBV_URI,ESSUBV));
        listado.put(ESSRVC, new Context(ESSRVC_URI,ESSRVC));
        listado.put(ESPRESUP, new Context(ESPRESUP_URI,ESPRESUP));        
		listado.put(ESAGENDA, new Context(ESAGENDA_URI,ESAGENDA));        
        listado.put(ESTURISMO, new Context(ESTURISMO_URI,ESTURISMO));
        listado.put(CRUZAR, new Context("http://idi.fundacionctic.org/cruzar/turismo#",CRUZAR));
        listado.put(TURZAR, new Context("https://www.zaragoza.es/sede/portal/def/turismo/lugar#",TURZAR));
        listado.put(ESTUR, new Context("http://vocab.linkeddata.es/datosabiertos/def/turismo/lugar#",ESTUR));
        listado.put(ESALOJ,new Context(ESALOJ_URI, ESALOJ));
        listado.put(ESMETEO,new Context("http://vocab.linkeddata.es/datosabiertos/def/medio-ambiente/meteorologia#", ESMETEO));
        listado.put(ESCALIDADAIRE,new Context("http://vocab.linkeddata.es/datosabiertos/def/medio-ambiente/calidad-aire#", ESCALIDADAIRE));
        listado.put(ESACCID,new Context("http://vocab.linkeddata.es/datosabiertos/def/transporte/accidentalidad-trafico#", ESACCID));
        listado.put(MAPEOSEM,new Context("https://www.zaragoza.es/sede/portal/def/urbanismo-infraestructuras/mapeo-semantico-calle#", MAPEOSEM));
        listado.put(ORGES, new Context(ORGES_URI,ORGES));
        listado.put(SCHEMA, new Context(SCHEMA_URI,SCHEMA));        
        listado.put(SKOS, new Context(SKOS_URI,SKOS));
        listado.put(EVENT, new Context("http://purl.org/NET/c4dm/event.owl#",EVENT));        
        listado.put(LOCN, new Context("http://www.w3.org/ns/locn#",LOCN));
        listado.put(OWL, new Context(OWL_URI,OWL));
        listado.put(EMP, new Context("http://purl.org/ctic/empleo/oferta#",EMP));
        listado.put(SSN, new Context("http://purl.oclc.org/NET/ssnx/ssn#",SSN));
        listado.put(DCAT, new Context("http://www.w3.org/ns/dcat#",DCAT));
        listado.put(ESEQUIP, new Context(ESEQUIP_URI,ESEQUIP));
        listado.put(ESCOM, new Context(ESCOM_URI,ESCOM));
        listado.put(ESNORM, new Context("http://vocab.linkeddata.es/datosabiertos/def/sector-publico/normativa#",ESNORM));
        listado.put(OPEN311, new Context(OPEN311_URI,OPEN311));
        listado.put(CPSV, new Context("http://purl.org/vocab/cpsv#",CPSV));
        listado.put(SOSA, new Context(SOSA_URI,SOSA));
        listado.put(TIME, new Context(TIME_URI,TIME));        
        listado.put(ESTRN, new Context(ESTRN_URI,ESTRN));
        listado.put(DUL, new Context(DUL_URI,DUL));
        listado.put(ESAIR, new Context(ESAIR_URI,ESAIR));
        listado.put(GEOCORE, new Context(GEOCORE_URI,GEOCORE));
        listado.put(QB, new Context(QB_URI,QB));
        listado.put(ESPAD, new Context(ESPAD_URI,ESPAD));
        listado.put(ESPADMEDIDA, new Context(ESPADMEDIDA_URI,ESPADMEDIDA));
        listado.put(IAESTDIMENSION, new Context(IAESTDIMENSION_URI,IAESTDIMENSION));
        listado.put(SDMXTDIMENSION, new Context(SDMXDIMENSION_URI,SDMXTDIMENSION));
        listado.put(SF, new Context(SF_URI,SF));
        listado.put(ESAGM, new Context(ESAGM_URI,ESAGM));
        listado.put(OCDS, new Context(OCDS_URI,OCDS));
        listado.put(ESBICI, new Context(ESBICI_URI,ESBICI));
        listado.put(ESCONV, new Context(ESCONV_URI,ESCONV));
        listado.put(ESTRAF, new Context(ESTRAF_URI,ESTRAF));
        
    }
	
	private String uri;
	private String prefijo;
	
	public Context(String uri, String prefijo) {
		super();
		this.uri = uri;
		this.prefijo = prefijo;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
}
