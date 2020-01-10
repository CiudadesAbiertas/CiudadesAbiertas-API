package org.ciudadesabiertas.dataset.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.rdfGeneratorZ.PathIdComplex;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Util;


@Rdf(contexto = Context.SF, propiedad = "MultiPolygon")
@PathId(value="/territorio/geometry")
public class Geometry implements RDFModel, PathIdComplex {
		
	
	private String ikey;
	
	private String id;
	
	private String typeForURI;
		
	@Rdf(contexto = Context.GEOSPARQL, propiedad = "asWKT", typeURI =Context.GEOSPARQL_URI+"wktLiteral" )
	private Object geometry;
	
	public Geometry() {		
		this.geometry = new Object();
	}	

	public Geometry(Object geometry) {
		super();
		this.geometry = geometry;
	}
	
	
	public Geometry(Geometry copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("geometry")) {
			this.geometry = copia.geometry;		
		}
		
		
	}
	

	public Object getGeometry() {
		return geometry;
	}

	public void setGeometry(Object geometry) {
		this.geometry = geometry;
	}


	@Override
	public String toString() {
		return "Geometry [geometry=" + geometry + "]";
	}

	@Override
	public String getIkey() {		
		return ikey;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setIkey(String ikey) {
		this.ikey=ikey;
		
	}

	@Override
	public void setId(String id) {
		this.id=id;
		
	}
	
	

	public String getTypeForURI() {
		return typeForURI;
	}

	public void setTypeForURI(String typeForURI) {
		this.typeForURI = typeForURI;
	}

	@Override
	public <T> T cloneModel(T copia, List<String> listado) {
		
		return (T) cloneClass((Geometry) copia, listado);
	}
	
	
	
	public Geometry cloneClass(Geometry copia, List<String> attributesToSet) {

		Geometry obj = new Geometry(copia,attributesToSet);		

		return obj;

	}
	

	@Override
	public List<String> validate() {

		List<String> result= new ArrayList<String>();
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getGeometry())) {
			result.add("Geometry is not valid [Geometry:"+this.getGeometry()+"]");
		}		
		
		
		return result;
	}

	@Override
	public Map<String, String> prefixes() {
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);	
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);		
		prefixes.put(Context.DUL, Context.DUL_URI);
		prefixes.put(Context.GEOSPARQL, Context.GEOSPARQL_URI);		
		return prefixes;
	}
	
	
	public String obtainURLPath()
	{
		String path="/territorio/"+typeForURI+"/"+id+"/geometry";
		return path;
	}

}
