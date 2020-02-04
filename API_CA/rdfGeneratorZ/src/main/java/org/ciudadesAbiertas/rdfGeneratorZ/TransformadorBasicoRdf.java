package org.ciudadesAbiertas.rdfGeneratorZ;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.CustomId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.HtmlContent;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Interno;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsPropiedadSemanticaCentro;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RangeSKOS;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfDinamico;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfList;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfMultiple;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfTripleExtenal;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfType;
import org.ciudadesAbiertas.rdfGeneratorZ.geo.Geometria;
import org.ciudadesAbiertas.rdfGeneratorZ.geo.LineString;
import org.ciudadesAbiertas.rdfGeneratorZ.geo.Punto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;





public class TransformadorBasicoRdf {
	
	private static final Logger logger = LoggerFactory.getLogger(TransformadorBasicoRdf.class);
	private Html2MarkDown transformadorMarkDown = new Html2MarkDown();

	private Model model = ModelFactory.createDefaultModel();
	private String uriBase = "http://ciudadesAbiertas.es";
	private String context = "/OpenCitiesAPI";
	private Resource initRes = null;
	private boolean genericId = true;
	
	private Map<String,String> prefixes=new LinkedHashMap<String,String>();
	
	
	public TransformadorBasicoRdf(String uriBase, String context)
	{
		super();		
		if ((uriBase!=null)&&(!uriBase.equals("")))
		{
			this.uriBase = uriBase;
		}
		
		if ((context!=null)&&(!context.equals("")))
		{
			this.context = context;
		}
		
		
		
	}

	public void transformarObjeto(StringBuilder respuesta, Object retorno,
			Peticion peticion, boolean primero, String prefijo)
			throws Exception {
		
		if (primero){
			
			
			
		    model.setNsPrefixes(prefixes);
		    
		    
			String id = UUID.randomUUID().toString();
			String path = uriBase+context ;
			genericId = true;
			for (Field field : retorno.getClass().getDeclaredFields()) {
				if (field.getName().equals("id") || (field.isAnnotationPresent(Id.class)) ){				
					Object valor = Funciones.retrieveObjectValue(retorno, field.getName());
					id = valor.toString();
					genericId = false;
				}
			}
			if (retorno.getClass().isAnnotationPresent(PathId.class)){
				path = path + retorno.getClass().getAnnotation(PathId.class).value() + "/";
			} else {
				path = path + "/result/";
			}
			if (!genericId){
				initRes = model.createResource(path + id);
				if (retorno.getClass().getAnnotation(Rdf.class)!=null){
					String rdf = obtenerValorAnotacionRDF(retorno.getClass().getAnnotation(Rdf.class)).replaceAll("\"", "");;
					if (rdf.endsWith("/"))
						rdf = rdf.substring(0,rdf.lastIndexOf("/"));
	
					Property initResProp;
					if (startsWithHttp(rdf))
						initResProp = model.createProperty(rdf);
					else
						initResProp = model.createProperty(uriBase+ rdf);
					model.add(initRes, RDF.type, initResProp);
				} else if (retorno.getClass().getAnnotation(RdfMultiple.class)!=null){
					for (Rdf anot:retorno.getClass().getAnnotation(RdfMultiple.class).value()){
						String rdf = obtenerValorAnotacionRDF(anot).replaceAll("\"", "");;
						if (rdf.endsWith("/"))
							rdf = rdf.substring(0,rdf.lastIndexOf("/"));
		
						Property initResProp;
						if (startsWithHttp(rdf))
							initResProp = model.createProperty(rdf);
						else
							initResProp = model.createProperty(uriBase+ rdf);
						model.add(initRes, RDF.type, initResProp);
					}
				}
			}
			else
				initRes = model.createResource();
			
			for (Field field : retorno.getClass().getDeclaredFields()) {
				if ((field.isAnnotationPresent(RdfType.class)) ){				
					Object valor = Funciones.retrieveObjectValue(retorno, field.getName());
					if (valor instanceof Object[]) {
						Object[] listado = (Object[]) valor;
						for (Object element:listado){
							model.add(initRes, RDF.type, model.createProperty(element.toString()));
						}
					}
					else
						model.add(initRes, RDF.type, model.createProperty(valor.toString()));
				}
			}
			

		}		
		
		peticion.establecerLastModified(retorno);
		int index=0;
		for (Field field : retorno.getClass().getDeclaredFields()) {
			boolean transformarCampo = transformarCampo(retorno, peticion, prefijo, field);
			if (transformarCampo) {

				Object valor = Funciones.retrieveObjectValue(retorno, field
						.getName());
				
				
				if (field.isAnnotationPresent(HtmlContent.class) && (valor instanceof String) && CheckeoParametros.RESPUESTAMARKDOWN.equals(peticion.getTipoEtiquetado())) {
					valor = transformadorMarkDown.transformar((String)valor);
				}

				
				
			 if (valor instanceof List) {
					List<?> listado = (List<?>) valor;

					if (listado.size()>0){
						for (Object object : listado) {													
							Resource newRes = model.createResource(getPathResource(object,initRes.getURI()));													
							boolean output = transformarObjeto(respuesta, object, peticion, prefijo, newRes);
							
							if (output){
								if (object.getClass().getAnnotation(Rdf.class)!=null){
									String rdf = obtenerValorAnotacionRDF(object.getClass().getAnnotation(Rdf.class)).replaceAll("\"", "");;
									if (rdf.endsWith("/"))
										rdf = rdf.substring(0,rdf.lastIndexOf("/"));
					
									Property initResProp;
									if (startsWithHttp(rdf))
										initResProp = model.createProperty(rdf);
									else
										initResProp = model.createProperty(uriBase+ rdf);
									model.add(newRes, RDF.type, initResProp);
								} else if (object.getClass().getAnnotation(RdfMultiple.class)!=null){
									for (Rdf anot:object.getClass().getAnnotation(RdfMultiple.class).value()){
										String rdf = obtenerValorAnotacionRDF(anot).replaceAll("\"", "");;
										if (rdf.endsWith("/"))
											rdf = rdf.substring(0,rdf.lastIndexOf("/"));
						
										Property initResProp;
										if (startsWithHttp(rdf))
											initResProp = model.createProperty(rdf);
										else
											initResProp = model.createProperty(uriBase+ rdf);
										model.add(newRes, RDF.type, initResProp);
									}
								}
								
								if (containsRdfsType(object)) {				
									for (Field f : object.getClass().getDeclaredFields()) {
										if ((f.isAnnotationPresent(RdfType.class)) ){				
											Object val = Funciones.retrieveObjectValue(object, f.getName());
											if (val instanceof Object[]) {
												Object[] values = (Object[]) val;
												for (Object element:values){
													model.add(newRes, RDF.type, model.createProperty(element.toString()));
												}
											}
											else if (val!=null)
												model.add(newRes, RDF.type, model.createProperty(val.toString()));
										}
									
									}	
								}
								addResource(initRes, newRes, field, object);
							}
							
						}
					}
				} else if (valor instanceof Map<?, ?>) {
					transformarMap(respuesta, peticion, peticion.getFormato().getTransformador(), true, field, valor, initRes);
				} else if (valor instanceof Set) {
					Set<?> listado = (Set<?>) valor;
					if (listado.size()>0){
						for (Object object : listado) {
							Resource newRes = model.createResource(getPathResource(object,initRes.getURI()));
							boolean output = transformarObjeto(respuesta, object, peticion, prefijo, newRes);
							if (output){
								addResource(initRes, newRes, field, object);
							}
							
						}
					}
				} else if (valor instanceof Object[]) {
					Object[] listado = (Object[]) valor;
					if (listado.length>0){
						for (Object object : listado) {
							Resource newRes = model.createResource(getPathResource(object));
							boolean output = transformarObjeto(respuesta, object, peticion, prefijo, newRes);
							if (output){
								addResource(initRes, newRes, field, object);
							}
							
						}
					}
				} else if (valor instanceof Geometria) {
					if (field.isAnnotationPresent(Rdf.class)) {
						Geometria geo = (Geometria) valor;
						String srsName = peticion.getSrsName();
						boolean aux = false;
						addGeometry(aux, field, srsName, geo, initRes, peticion.isCargandoEnVirtuoso());
					}

				} else if (field.getType().isAnnotationPresent(XmlRootElement.class)){
					if (!field.isAnnotationPresent(Interno.class) && (field.isAnnotationPresent(Rdf.class) || field.isAnnotationPresent(RdfMultiple.class))){
	//					transformarObjeto(respuesta, valor, peticion, false, prefijo);
						Resource newRes = model.createResource(getPathResource(valor,initRes.getURI()));													
						boolean output = transformarObjeto(respuesta, valor, peticion, prefijo, newRes);
						
						if (output){
							addResource(initRes, newRes, field, valor);
						}
					}
					
				} else {
				
					//ESTA PARTE ES DONDE DEBO AÑADIR LA PRUEBA DEL CAMPO CONTEXTO A VER QUE PUEDO HACER
						if (field.isAnnotationPresent(Rdf.class) && !field.isAnnotationPresent(Interno.class)) {
							String anot = obtenerValorAnotacionRDF(field.getAnnotation(Rdf.class)).replaceAll("\"", "");
							
							Property entityProp = model.createProperty(anot);
							if (field.isAnnotationPresent(IsUri.class))
								model.add(initRes, entityProp, model.createResource(valor.toString()));
							else
								model.add(initRes, entityProp, valor.toString());

						} else if (field.isAnnotationPresent(RdfMultiple.class) && !field.isAnnotationPresent(Interno.class)) {
							Rdf[] valores = field.getAnnotation(RdfMultiple.class).value();

							for (int i = 0; i < valores.length; i++) {							
								Property entityProp = model.createProperty(obtenerValorAnotacionRDF(valores[i]).replaceAll("\"", ""));					        
								if (field.isAnnotationPresent(IsUri.class))
									model.add(initRes, entityProp, model.createResource(valor.toString()));
								else
									model.add(initRes, entityProp, model.createTypedLiteral(valor.toString()));
							}

						}

				}
			}
		}
	
		if (primero){
			String format = "RDF/XML";
			if (peticion.getFormato().getExtension().equals(MimeTypes.TURTLE_EXTENSION))
				format = "TURTLE";
			else if (peticion.getFormato().getExtension().equals(MimeTypes.RDF_N3_EXTENSION))
				format = "N-Triples";
			else if (peticion.getFormato().getExtension().equals(MimeTypes.JSONLD_EXTENSION))
				format = "JSON-LD";
								
			ByteArrayOutputStream obj = new ByteArrayOutputStream();		
			try
			{
				model.write( obj, format);	
			}
			catch (Exception e)
			{
				throw e;
			}
						
			
			try {
		        respuesta.append(obj.toString("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				//e.printStackTrace();
				respuesta.append(obj);
			}
			 
			Funciones.replaceAll(respuesta, Metavariables.uriBase, uriBase);
			Funciones.replaceAll(respuesta, Metavariables.contexto, context);		
						
		}
	}

	private boolean startsWithHttp(String rdf) {
		boolean check=false;
		if (rdf!=null)
		{
			check=rdf.startsWith("http://")||(rdf.startsWith("https://"));
		}
		return check;
	}
	
	public boolean transformarObjeto(StringBuilder respuesta, Object retorno,
			Peticion peticion, String prefijo, Resource resource)
			throws Exception {

		boolean output = false;
		
		peticion.establecerLastModified(retorno);
		//Pre acciones JCBH
		HashMap<String, String> mapaValores=new HashMap<String,String>();
		HashMap<String, Resource> mapaNodosEnBlanco=new HashMap<String,Resource>();
		for (Field field : retorno.getClass().getDeclaredFields()) {
			Object valor = Funciones.retrieveObjectValue(retorno, field.getName());
			if (valor!=null && !"".equals(valor)) {
				mapaValores.put(field.getName(), valor.toString());
				
				if (field.isAnnotationPresent(RdfBlankNode.class))
				{
					String nodo = obtenerIdNodoBlanco(field.getAnnotation(RdfBlankNode.class));
				
					if (mapaNodosEnBlanco.get(nodo)==null)
					{
						mapaNodosEnBlanco.put(nodo, model.createResource());
					}				
					
				}
			}
		}
		
		for (Field field : retorno.getClass().getDeclaredFields()) {
			boolean transformarCampo = transformarCampo(retorno, peticion, prefijo, field);

			if (transformarCampo
					&& (field.isAnnotationPresent(Rdf.class) || 
							field.isAnnotationPresent(IsPropiedadSemanticaCentro.class) ||
							field.isAnnotationPresent(RdfMultiple.class)) && !field.isAnnotationPresent(Interno.class)) {
				
				
				Object valor = Funciones.retrieveObjectValue(retorno, field
						.getName());

				if (valor instanceof List) {
					List<?> listado = (List<?>) valor;
					if (listado.size()>0){
						for (Object object : listado) {
							Resource newRes = model.createResource(getPathResource(object,resource.getURI()));
							
							boolean auxOutput = transformarObjeto(respuesta, object, peticion, prefijo, newRes);
							if (auxOutput && !output)
								output = true;
							if (auxOutput){
								if (object.getClass().getAnnotation(Rdf.class)!=null){
									String rdf = obtenerValorAnotacionRDF(object.getClass().getAnnotation(Rdf.class)).replaceAll("\"", "");;
									if (rdf.endsWith("/"))
										rdf = rdf.substring(0,rdf.lastIndexOf("/"));
					
									Property initResProp;
									if (startsWithHttp(rdf))
										initResProp = model.createProperty(rdf);
									else
										initResProp = model.createProperty(uriBase+ rdf);
									model.add(newRes, RDF.type, initResProp);
								} else if (object.getClass().getAnnotation(RdfMultiple.class)!=null){
									for (Rdf anot:object.getClass().getAnnotation(RdfMultiple.class).value()){
										String rdf = obtenerValorAnotacionRDF(anot).replaceAll("\"", "");
										if (rdf.endsWith("/"))
											rdf = rdf.substring(0,rdf.lastIndexOf("/"));
						
										Property initResProp;
										if (startsWithHttp(rdf))
											initResProp = model.createProperty(rdf);
										else
											initResProp = model.createProperty(uriBase+ rdf);
										model.add(newRes, RDF.type, initResProp);
									}
								}
								addResource(resource, newRes, field, object);
							}
						}
					}
				} else if (valor instanceof Map<?, ?>) {
					Map<?,?> listado = (Map<?,?>) valor;
				} else if (valor instanceof Set) {
					Set<?> listado = (Set<?>) valor;
					if (listado.size()>0){
						for (Object object : listado) {

							Resource newRes = model.createResource(getPathResource(object,resource.getURI()));							
							boolean auxOutput = transformarObjeto(respuesta, object, peticion, prefijo, newRes);
							if (auxOutput && !output)
								output = true;
							if (auxOutput){
								addResource(resource, newRes, field, object);
							}
						}
					}
				} else if (valor instanceof Object[]) {
					Object[] listado = (Object[]) valor;

					if (listado.length>0){
						for (Object object : listado) {

							Resource newRes = model.createResource(getPathResource(object));
							boolean auxOutput = transformarObjeto(respuesta, object, peticion, prefijo, newRes);
							if (auxOutput && !output)
								output = true;
							if (auxOutput){
								addResource(resource, newRes, field, object);
							}
						}
					}
				} else if (valor instanceof Geometria) {
					
					if (field.isAnnotationPresent(Rdf.class)) {
						Geometria geo = (Geometria) valor;
						String srsName = peticion.getSrsName();
						output = addGeometry(output, field, srsName, geo, resource, peticion.isCargandoEnVirtuoso());
					}
					
				} else if (field.getType().isAnnotationPresent(XmlRootElement.class)){
					if (!field.isAnnotationPresent(Interno.class) && (field.isAnnotationPresent(Rdf.class) || field.isAnnotationPresent(RdfMultiple.class))){					
						Resource newRes = model.createResource(getPathResource(valor,resource.getURI()));							
						boolean auxOutput = transformarObjeto(respuesta, valor, peticion, prefijo, newRes);
						if (auxOutput && !output)
							output = true;
						if (auxOutput){

							addResource(resource, newRes, field, valor);
						}
						if (auxOutput && !output)
							output = true;
					}
				} else {
					if (field.isAnnotationPresent(RdfTripleExtenal.class)) {						
							
							//Vamos a componer una tripleta de tres URLs
							String sujetoURI="";
							String predicadoURI="";
							String objetoURI="";
							//URL del Sujeto							
							String sujetoInicioURI=field.getAnnotation(RdfTripleExtenal.class).sujetoInicioURI();
							String sujetoFinURI=field.getAnnotation(RdfTripleExtenal.class).sujetoFinURI();
							if (sujetoInicioURI.startsWith("/"))
							{
								sujetoURI=uriBase+context+sujetoInicioURI+mapaValores.get(sujetoFinURI);
							} else {
								sujetoURI=sujetoInicioURI+mapaValores.get(sujetoFinURI);
							}
							//URL del Predicado
							predicadoURI=field.getAnnotation(RdfTripleExtenal.class).propiedadURI();
							String objetoInicioURI=field.getAnnotation(RdfTripleExtenal.class).objetoInicioURI();
							String objectoFinURI=field.getAnnotation(RdfTripleExtenal.class).objetoFinURI();
							if (objetoInicioURI.startsWith("/"))
							{
								objetoURI=uriBase+context+objetoInicioURI+mapaValores.get(objectoFinURI);
							} else {
								objetoURI=objetoInicioURI+mapaValores.get(objectoFinURI);
							}						
							
							
							model.add(model.createResource(sujetoURI), model.createProperty(predicadoURI), model.createResource(objetoURI));	
								
							}
					
							if (field.isAnnotationPresent(Rdf.class) && !field.isAnnotationPresent(Interno.class)) {
								String anot = obtenerValorAnotacionRDF(field.getAnnotation(Rdf.class)).replaceAll("\"", "");
								String typeURI=obtenerTipoLiteralAnotacionRDF(field.getAnnotation(Rdf.class));							
								Property entityProp = model.createProperty(anot);
								//Modificación JCBH
								if (field.isAnnotationPresent(RdfExternalURI.class)) 
								{
									boolean isValorURI=false;
									
									if ((valor.toString().startsWith("/"))||(valor.toString().startsWith("http")))
									{
										isValorURI=true;
									}
									
									String separador=obtenerSeparadorAnotacionExternalRDF(field.getAnnotation(RdfExternalURI.class));
									ArrayList<String> theURIs=new ArrayList<String>();
									
									String inicioURI=obtenerInicioURIAnotacionExternalRDF(field.getAnnotation(RdfExternalURI.class));
									if (inicioURI.startsWith("/"))
									{
										inicioURI=uriBase+context+inicioURI;
									}
									String finURI=obtenerFinURIKeyAnotacionExternalRDF(field.getAnnotation(RdfExternalURI.class));
									String propiedad=obtenerPropiedadAnotacionExternalRDF(field.getAnnotation(RdfExternalURI.class));																		
									String tipo=obtenerTipoAnotacionExternalRDF(field.getAnnotation(RdfExternalURI.class));
									int urifyLevel=obtenerUrifyLevelAnotacionExternalRDF(field.getAnnotation(RdfExternalURI.class));
									boolean capitalize=obtenerCapitalizeAnotacionExternalRDF(field.getAnnotation(RdfExternalURI.class));
										
									
									
									if (!inicioURI.equals(""))
									{
										String theURI=inicioURI;
										
										if (!finURI.equals(""))
										{	
											if (separador.equals(""))
											{
												String valorURI=mapaValores.get(finURI);
												if (capitalize)
												{
													valorURI=StringUtils.capitalize(valorURI.toLowerCase());
												}
												if (valorURI!=null)
												{
													theURI+=Funciones.urlify(valorURI,urifyLevel);
												}else {
													theURI=null;
												}
											}
											else {
												String valoresURI=mapaValores.get(finURI);
												String[] splitedValues = valoresURI.split(separador);
												for (String valorURI:splitedValues)
												{				
													valorURI=valorURI.trim();
													if (capitalize)
													{
														valorURI=StringUtils.capitalize(valorURI.toLowerCase());
													}
													if (valorURI!=null)
													{
														theURI+=Funciones.urlify(valorURI,urifyLevel);
													}else {
														theURI=null;
													}
													theURIs.add(theURI);
												}
												theURI=null;
											}
										}
											
										//Reemplazamos la metavariables en las URLs externas
										//theURI=theURI.replace(Metavariables.uriBase, uriBase);
										//theURI=theURI.replace(Metavariables.contexto, context);
										
										if (theURI!=null)
										{
											Resource externalResourceURI =model.createResource(theURI);									
										
											if (propiedad.equals(""))
											{
												model.add(resource, entityProp, externalResourceURI);	
											}
											else 
											{
												model.add(resource, model.createProperty(propiedad), externalResourceURI);
												if (!tipo.equals(""))
												{
													model.add(externalResourceURI, RDF.type, model.createResource(tipo));
												}
												
												if (isValorURI)
												{
													String valorURI="";
													if (valor.toString().startsWith("/"))
													{
														valorURI=uriBase+context+valor.toString();
													}else {
														valorURI=valor.toString();
													}
													
													model.add(externalResourceURI, entityProp, model.createResource(valorURI));
												}
												else 
												{											
													model.add(externalResourceURI, entityProp, valor.toString());
												}
											}									
										}
										
										if (theURIs.size()>0)
										{
											for (String oneURI:theURIs)
											{
												Resource externalResourceURI =model.createResource(oneURI);		
												
												if (propiedad.equals(""))
												{
													model.add(resource, entityProp, externalResourceURI);	
												}
												else 
												{
													model.add(resource, model.createProperty(propiedad), externalResourceURI);
													if (!tipo.equals(""))
													{
														model.add(externalResourceURI, RDF.type, model.createResource(tipo));
													}
													model.add(externalResourceURI, entityProp, valor.toString());
												}	
											}
										}
										
										
									}
							}
							else if (field.isAnnotationPresent(RdfBlankNode.class))
							{
								String nodo = obtenerIdNodoBlanco(field.getAnnotation(RdfBlankNode.class));
								String tipo = obtenerTipoNodoBlanco(field.getAnnotation(RdfBlankNode.class));
								String propiedad = obtenerPropiedadNodoBlanco(field.getAnnotation(RdfBlankNode.class));
								
								Resource blankNodeResource=mapaNodosEnBlanco.get(nodo);
								
								model.add(resource, model.createProperty(propiedad), blankNodeResource);
								if (!tipo.equals(""))
								{
									model.add(blankNodeResource, RDF.type, model.createResource(tipo));
								}
								if (typeURI.equals(""))
								{
									model.add(blankNodeResource, entityProp, valor.toString());
								}
								else 
								{									
									checkTypeURIandFormat(model, blankNodeResource, valor, typeURI, entityProp);
								}
							}							
							else {
							
								if (field.isAnnotationPresent(IsUri.class)) {
									if ((valor!=null)&&(valor.toString().startsWith("www")))
									{
										model.add(resource, entityProp, model.createResource("http://"+valor.toString()));
									}
									else {
										model.add(resource, entityProp, model.createResource(valor.toString()));
									}
								}
								else
								{	
									checkTypeURIandFormat(model, resource, valor, typeURI, entityProp);									
								}
							}
							output = true;
						} else if (field.isAnnotationPresent(RdfMultiple.class) && !field.isAnnotationPresent(Interno.class)) {
							Rdf[] rdfAnotation = field.getAnnotation(RdfMultiple.class).value();
							
							
							for (int i = 0; i < rdfAnotation.length; i++) {							
								Property entityProp = model.createProperty(obtenerValorAnotacionRDF(rdfAnotation[i]).replaceAll("\"", ""));					        								
								if (field.isAnnotationPresent(IsUri.class))
								{
									model.add(resource, entityProp, model.createResource(valor.toString()));
								}
								else
								{
									String typeURI=rdfAnotation[i].typeURI();
									//model.add(resource, entityProp, model.createTypedLiteral(valor.toString()));
									if (typeURI.equals(""))
									{
										model.add(resource, entityProp, valor.toString());
									}else {
										checkTypeURIandFormat(model, resource, valor, typeURI, entityProp);
									}
									
								}
							}
							output = true;
						} 

				}
			}else if (transformarCampo && field.isAnnotationPresent(RdfDinamico.class)) {							
								
				String inicioURI=obtenerInicioURIAnotacionDinamicRDF(field.getAnnotation(RdfDinamico.class));
				
				if (inicioURI.startsWith("/"))
				{
					inicioURI=uriBase+context+inicioURI;
				}
				String finURI=obtenerFinURIKeyAnotacionDinamicRDF(field.getAnnotation(RdfDinamico.class));
				String propiedad=obtenerPropiedadAnotacionDinamicRDF(field.getAnnotation(RdfDinamico.class));
				String rdfType=obtenerRdfTypeAnotacionDinamicRDF(field.getAnnotation(RdfDinamico.class));
				String valorURI=mapaValores.get(finURI);
				if (valorURI==null)
				{
					valorURI="";
				}
			
				model.add(resource, model.createProperty(propiedad), model.createResource(inicioURI+valorURI));
				
				if ((rdfType!=null)&&(!rdfType.equals("")))
				{
					Object valor = Funciones.retrieveObjectValue(retorno, field.getName());
					model.add(model.createResource(valor.toString()),RDF.type,model.createResource(rdfType));
				}
				
				output = true;
			}else if (transformarCampo && field.isAnnotationPresent(RangeSKOS.class)) {
				
				Object valor = Funciones.retrieveObjectValue(retorno, field.getName());
				
				if (valor!=null)
				{
					Resource blankNodeRestriction=model.createResource();				
					blankNodeRestriction.addProperty(RDF.type,model.createResource(Context.OWL_URI+"Restriction"));
					blankNodeRestriction.addProperty(model.createProperty(Context.OWL_URI+"onProperty"),model.createResource(Context.SKOS_URI+"inScheme"));
					blankNodeRestriction.addProperty(model.createProperty(Context.OWL_URI+"hasValue"),model.createResource(valor.toString()));				
								
					model.add(resource, model.createProperty(Context.RDFS_URI+"range"), blankNodeRestriction);
				}
				model.add(resource, model.createProperty(Context.RDFS_URI+"range"), model.createResource(Context.SKOS_URI+"Concept"));
				
				
			}else if (transformarCampo && field.isAnnotationPresent(RdfNode.class)) {							
				
				RdfNode annotation = field.getAnnotation(RdfNode.class);
				
				String inicioURI=annotation.inicioURI();
				String valorURI=annotation.valorURI();
				String finURI=annotation.finURI();
				
				String propiedad=annotation.propiedad();
				
				String nodoType=annotation.nodoType();
				String nodoPropiedad=annotation.nodoPropiedad();
				String nodoPropiedadTipo=annotation.nodoPropiedadTipo();
				
				Object valor = Funciones.retrieveObjectValue(retorno, field.getName());
				
				if (valorURI==null)
				{
					valorURI="";
				}
				
				if (finURI==null)
				{
					finURI="";
				}
				
				if (inicioURI.startsWith("/"))
				{
					inicioURI=uriBase+context+inicioURI;
				}
				
				valorURI=mapaValores.get(valorURI);
				
				String theURI=inicioURI+valorURI+finURI;
								
				model.add(resource, model.createProperty(propiedad), model.createResource(theURI));
				
				if (nodoType!=null)
				{					
					model.add(model.createResource(theURI),RDF.type,model.createResource(nodoType));
				}
				
				if ((nodoPropiedadTipo!=null)&&(!nodoPropiedadTipo.equals("")))
				{				
					model.add(model.createResource(theURI),model.createProperty(nodoPropiedad),valor.toString());
				}else {
					model.add(model.createResource(theURI),model.createProperty(nodoPropiedad), model.createTypedLiteral(valor.toString(),nodoPropiedadTipo));
				}
				
				
				output = true;
			}else if (transformarCampo && field.isAnnotationPresent(RdfList.class)) {
				
				Object valor = Funciones.retrieveObjectValue(retorno, field.getName());
				String propiedad=obtenerPropiedadAnotacionRdfList(field.getAnnotation(RdfList.class));	
				
				
				List<String> valueList = (List)valor;
				
				for (String actualValue:valueList)
				{
					boolean actualValueURI=false;
					
					actualValue=actualValue.trim();
					
					if (actualValue.startsWith("/"))
					{
						actualValue=uriBase+context+actualValue;
						actualValueURI=true;
					}else if (actualValue.startsWith("http"))
					{
						actualValueURI=true;
					}
				
					if( field.isAnnotationPresent(RdfBlankNode.class) )
					{
						String propiedadNodoEnBlanco=obtenerPropiedadNodoBlanco(field.getAnnotation(RdfBlankNode.class));	
						
						Resource blankNodeResource=model.createResource();
						
						model.add(resource, model.createProperty(propiedad), blankNodeResource);
						
						if (actualValueURI)
						{
							model.add(blankNodeResource, model.createProperty(propiedadNodoEnBlanco),  model.createResource(actualValue));
						}
						else
						{						
							model.add(blankNodeResource, model.createProperty(propiedadNodoEnBlanco), actualValue);
						}
					}
					else {
						if (actualValueURI)
						{
							model.add(resource, model.createProperty(propiedad), model.createResource(actualValue));
						}	
						else 
						{
							model.add(resource, model.createProperty(propiedad), actualValue);
						}
					}
					
				}
				
				
			}
		}
		
		
		
		
		return output;
	}

	private void checkTypeURIandFormat(Model model, Resource resource, Object valor, String typeURI, Property entityProp)
	{
		if ((typeURI == null)||(typeURI.equals("")))
		{
			model.add(resource, entityProp, valor.toString());
		}
		else if (typeURI.endsWith("date"))
		{
			String fechaFormateada=Funciones.formateadorFecha.format(valor);
			model.add(resource, entityProp, model.createTypedLiteral(fechaFormateada,typeURI));
		}
		else if (typeURI.endsWith("datetime"))
		{
			String fechaFormateada=Funciones.formateadorFechaHora.format(valor);
			model.add(resource, entityProp, model.createTypedLiteral(fechaFormateada,typeURI));
		}										
		else if (typeURI.endsWith("time"))
		{
			String fechaFormateada=Funciones.formateadorHora.format(valor);
			model.add(resource, entityProp, model.createTypedLiteral(fechaFormateada,typeURI));
		}
		else {
			model.add(resource, entityProp, model.createTypedLiteral(valor.toString(),typeURI));
		}
	}
	
	public static boolean transformarMap(StringBuilder respuesta, Peticion peticion, TransformadorGenerico transformador, boolean primerCampo, Field field, Object valor, Resource resource)
			throws Exception {
		Map<?,?> listado = (Map<?,?>) valor;
		if (listado.size() > 0) {
			Iterator<?> it = listado.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
			    Object object = (Object) listado.get(key);
				if (object instanceof Map) {
					
					transformarMap(respuesta, peticion, transformador, true, field, object, resource);
					
				} else if (object instanceof Object[]) {
					
					Object[] listadoInt =(Object[]) object;
					
					if (listadoInt.length > 0) {
					
						boolean primerInterno = true;
						for (int i = 0; i < listadoInt.length; i++) {
							Object objectInt = listadoInt[i];
							if (objectInt instanceof Double) {
								if (i > 0) {
									respuesta.append(",");
								}

							} else if (objectInt instanceof String) {
								if (i > 0) {
									respuesta.append(",");
								}

							} else {
								transformador.transformarObjeto(respuesta, objectInt, peticion, primerInterno, null);
							}
							primerInterno = false;
						}
					}
				} else {
					if (peticion.quiereVerCampo("", key, peticion.getSelectedFields())) {

						if (object.getClass().isAnnotationPresent(XmlRootElement.class)) {
							StringBuilder datosObj = new StringBuilder();
							transformador.transformarObjeto(datosObj, object, peticion, true, "");

						} else if (object.getClass().isAnnotationPresent(Rdf.class)){
							transformador.escribirValorCampo(key, object, field.isAnnotationPresent(HtmlContent.class), peticion.getTipoEtiquetado());
						}
					}
				}
			}
		}
		return primerCampo;
	}
	
	private boolean addGeometry(boolean output, Field field, String srsName, Geometria geo, Resource resource, boolean cargandoEnVirtuoso){
		
		if (geo instanceof Punto) {
			Punto p = (Punto) geo;
			Resource geoRes;
			String anot = obtenerValorAnotacionRDF(field.getAnnotation(Rdf.class)).replaceAll("\"", "");
			Property entityProp = model.createProperty(anot);
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(6);
			df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.UK));
			if (srsName.equals(CheckeoParametros.SRSWGS84) || cargandoEnVirtuoso) {
				
				String term = Punto.pasarAWgs84(p
						.getCoordinates()[0], p
						.getCoordinates()[1]);
		
				Property latProp = model
						.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
				Property lngProp = model
						.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#long");
				StringTokenizer st = new StringTokenizer(
						term, ",");
				String lngSt = df.format(Float.valueOf(st.nextToken())).toString();				
				String latSt = df.format(Float.valueOf(st.nextToken())).toString();
				Literal litLat = model.createTypedLiteral(latSt,XSDDatatype.XSDdouble);
				Literal litLng = model.createTypedLiteral(lngSt,XSDDatatype.XSDdouble);
				
				geoRes = model.createResource("http://www.zaragoza.es/sede/servicio/geometry/WGS84/"+ latSt + "_" + lngSt);
				model.add(geoRes,RDF.type,model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos#Point"));
				model.add(geoRes,RDF.type,model.createProperty("http://www.opengis.net/ont/sf#Point"));
				geoRes.addLiteral(latProp, litLat);
				geoRes.addLiteral(lngProp, litLng);
								
				Property crsProperty = model.createProperty("http://www.opengis.net/ont/geosparql#crs");
				Resource crsRes = model.createResource("http://www.opengis.net/def/crs/OGC/1.3/CRS84");
				geoRes.addProperty(crsProperty, crsRes);
				
				Property asWKTProperty = model.createProperty("http://www.opengis.net/ont/geosparql#asWKT");
				String wktText = "POINT("
					+ lngSt
					+ " "
					+ latSt + ")";
				Literal literal = model.createTypedLiteral(wktText,"http://www.opengis.net/ont/geosparql#wktLiteral");
				geoRes.addLiteral(asWKTProperty, literal);
				
				model.add(resource, entityProp, geoRes);
				output = true;
			} 
			if (srsName.equals(CheckeoParametros.SRSETRS89) || cargandoEnVirtuoso) {
				
				double[] term = Geometria.pasarAETRS89Double(p.getCoordinates()[0], p.getCoordinates()[1]);
				
				geoRes = model.createResource("http://www.zaragoza.es/sede/servicio/geometry/EPSG25830/"+ term[0] + "_" + term[1]);
				model.add(geoRes,RDF.type,model.createProperty("http://www.opengis.net/ont/sf#Point"));
				
				Property crsProperty = model.createProperty("http://www.opengis.net/ont/geosparql#crs");
				Resource crsRes = model.createResource("http://www.opengis.net/def/crs/EPSG/0/25830");
				geoRes.addProperty(crsProperty, crsRes);
				
				Property asWKTProperty = model.createProperty("http://www.opengis.net/ont/geosparql#asWKT");
				String wktText = "POINT("
						+ term[0]
						+ " "
						+ term[1] + ")";
				Literal literal = model.createTypedLiteral(wktText,"http://www.opengis.net/ont/geosparql#wktLiteral");
				geoRes.addLiteral(asWKTProperty, literal);
				model.add(resource, entityProp, geoRes);
				output = true;
			}
			if (srsName.equals(CheckeoParametros.SRSUTM30N) || cargandoEnVirtuoso) {
				geoRes = model.createResource("http://www.zaragoza.es/sede/servicio/geometry/EPSG23030/"+ p.getCoordinates()[0] + "_" + p.getCoordinates()[1]);
				model.add(geoRes,RDF.type,model.createProperty("http://www.opengis.net/ont/sf#Point"));
				
				Property crsProperty = model.createProperty("http://www.opengis.net/ont/geosparql#crs");
				Resource crsRes = model.createResource("http://www.opengis.net/def/crs/EPSG/0/23030");
				geoRes.addProperty(crsProperty, crsRes);
				
				Property asWKTProperty = model.createProperty("http://www.opengis.net/ont/geosparql#asWKT");
				String wktText = "POINT("
						+ p.getCoordinates()[0]
						+ " "
						+ p.getCoordinates()[1] + ")";
				Literal literal = model.createTypedLiteral(wktText,"http://www.opengis.net/ont/geosparql#wktLiteral");
				geoRes.addLiteral(asWKTProperty, literal);
				model.add(resource, entityProp, geoRes);
				output = true;
			}
			
		} else if (geo instanceof LineString) {
			LineString ls = (LineString) geo;		
			Double[][] coordinates = ls.getCoordinates();
			
			
			String anot = obtenerValorAnotacionRDF(field.getAnnotation(Rdf.class)).replaceAll("\"", "");
			Property entityProp = model.createProperty(anot);
			if (srsName.equals(CheckeoParametros.SRSWGS84)) {
				
				String wktText = "<![CDATA[<gml:LineString srsName=\"EPSG:4258\" xmlns:gml=\"http://www.opengis.net/gml\"><gml:coordinates decimal=\".\" cs=\",\" ts=\" \">";
				int ind = 0;
				while (ind<coordinates.length){
					Double lat = coordinates[ind][0];
					Double lng = coordinates[ind][1];
					wktText = wktText + lat + ","+lng+" ";
					ind++;
				}
				wktText = wktText + "</gml:coordinates></gml:LineString>]]>";
				Resource geoRes = model.createResource("http://www.zaragoza.es/sede/servicio/geometry/"+ getHash(wktText));
				model.add(geoRes,RDF.type,model.createProperty("http://www.opengis.net/ont/gml#LineString"));
				Literal literal = model.createTypedLiteral(wktText,"http://www.opengis.net/ont/geosparql#gmlLiteral");
				Property asGMLroperty = model.createProperty("http://www.opengis.net/ont/geosparql#asGML");
				geoRes.addLiteral(asGMLroperty, literal);
				model.add(resource, entityProp, geoRes);
			} else {
				
				String wktText = "<![CDATA[<http://www.opengis.net/def/crs/OGC/1.3/CRS84>LineString(";
				int ind = 0;
				while (ind<coordinates.length){
					Double lat = coordinates[ind][0];
					Double lng = coordinates[ind][1];
					if (ind!=0)
						wktText = wktText + ",";
					wktText = wktText + lat + " "+lng;
					ind++;
				}
				wktText = wktText + ")]]>";
				Resource geoRes = model.createResource("http://www.zaragoza.es/sede/servicio/geometry/"+ getHash(wktText));
				model.add(geoRes,RDF.type,model.createProperty("http://www.opengis.net/ont/sf#LineString"));
				Literal literal = model.createTypedLiteral(wktText,"http://www.opengis.net/ont/geosparql#wktLiteral");
				Property asGMLroperty = model.createProperty("http://www.opengis.net/ont/geosparql#asWKT");
				geoRes.addLiteral(asGMLroperty, literal);
				model.add(resource, entityProp, geoRes);
			}

		}		
		
		return output;
		
	}
	
	private void addResource(Resource parentRes, Resource newRes, Field field, Object object){
		
		if (field.isAnnotationPresent(Rdf.class)){
			String anot = obtenerValorAnotacionRDF(field.getAnnotation(Rdf.class)).replaceAll("\"", "");
			Property entityProp = model.createProperty(anot);
			
			model.add(parentRes, entityProp, newRes);
			//A�ADIMOS EL TIPO DEL RECURSO
			addResourceType(newRes, object);

		} else if (field.isAnnotationPresent(RdfMultiple.class)){
			for(Rdf annotation:field.getAnnotation(RdfMultiple.class).value()){
				String anot = obtenerValorAnotacionRDF(annotation).replaceAll("\"", "");
				
				Property entityProp = model.createProperty(anot);
				model.add(parentRes, entityProp, newRes);
								
				//A�ADIMOS EL TIPO DEL RECURSO
				addResourceType(newRes, object);

			}
		}
		
	}
	
	private void addResourceType(Resource newRes, Object object){
		
		if (object.getClass().isAnnotationPresent(Rdf.class)){
			String rdf = obtenerValorAnotacionRDF(object.getClass().getAnnotation(Rdf.class)).replaceAll("\"", "");
			if (rdf.endsWith("/"))
				rdf = rdf.substring(0,rdf.lastIndexOf("/"));

			Property initResProp;
			if (startsWithHttp(rdf))
				initResProp = model.createProperty(rdf);
			else
				initResProp = model.createProperty(uriBase+ rdf);
			model.add(newRes, RDF.type, initResProp);
		} else if (object.getClass().isAnnotationPresent(RdfMultiple.class)){
			for(Rdf annotation:object.getClass().getAnnotation(RdfMultiple.class).value()){
				String rdf = obtenerValorAnotacionRDF(annotation).replaceAll("\"", "");
				if (rdf.endsWith("/"))
					rdf = rdf.substring(0,rdf.lastIndexOf("/"));

				Property initResProp;
				if (startsWithHttp(rdf))
					initResProp = model.createProperty(rdf);
				else
					initResProp = model.createProperty(uriBase+ rdf);
				model.add(newRes, RDF.type, initResProp);
			}
		}
		
	}
	
	private String getPathResource(Object retorno){		
		String id = UUID.randomUUID().toString();
		String path = uriBase+context ;
		for (Field fld : retorno.getClass().getDeclaredFields()) {
			if (fld.getName().equals("id") || (fld.isAnnotationPresent(Id.class))){
				
				Object val = Funciones.retrieveObjectValue(retorno, fld.getName());
				id = val.toString();
				if (id.contains(" ") || id.contains(",") || (id.contains ("[") || id.contains ("]"))){
					id = getComplexId(id);					
				}
			}
		}
		if (retorno.getClass().isAnnotationPresent(PathId.class)){
			path = path + retorno.getClass().getAnnotation(PathId.class).value() + "/";
		} else {
			path = path + "/recurso/";
		}
		return path + id;
	}
	
	private String getPathResource(Object retorno, String raiz){		
		String id = UUID.randomUUID().toString();
		String path = uriBase+context ;
		boolean complexId = false;
		for (Field fld : retorno.getClass().getDeclaredFields()) {
			if (fld.getName().equals("id") || (fld.isAnnotationPresent(Id.class))){
				
			    if (fld.isAnnotationPresent(CustomId.class))
			    {
			    	String realId=obtenerPropiedadID(fld.getAnnotation(CustomId.class));
			    	Object val = Funciones.retrieveObjectValue(retorno, realId);
			    	id=val.toString();
			    	id=Funciones.urlify(id, 3);			    	
			    }
			    else 
			    {
					Object val = Funciones.retrieveObjectValue(retorno, fld.getName());
					if (val != null) {
						id = val.toString();
						
						if (id.contains(" ") || id.contains(",") || (id.contains ("[") || id.contains ("]"))){
							String prefijo = retorno.getClass().getAnnotation(Rdf.class).prefijo();
							id = getComplexId(id,raiz,prefijo);	
							complexId = true;
						}
					} else {
						id = Funciones.generarHash(retorno.toString());
					}
			    }
			}
		}
		if (complexId){
			path = raiz;
		}
		else if (retorno.getClass().isAnnotationPresent(PathId.class)){
				
			String pathIdFromObject="";
				
			//JCBH Cambio para control por interfaz para PathIdComplex			
			if (retorno instanceof PathIdComplex ) {
				pathIdFromObject=((PathIdComplex) retorno).obtainURLPath();
				if (pathIdFromObject.startsWith("/"))
				{
					path = path + pathIdFromObject;
				}else {
					path = pathIdFromObject;
				}
				return path;
			}
			//Fin PathIdComplex
			
			//CMG Cambio para control por interfaz para MultiURI
			if (retorno instanceof MultiURI ) {				
				pathIdFromObject=((MultiURI) retorno).obtainURLPathFromType();
			} 
			//Fin MultiURI
				
			if (pathIdFromObject.equals(""))
			{
				path = path + retorno.getClass().getAnnotation(PathId.class).value() + "/";
			}
			else 
			{
				path = path + pathIdFromObject + "/";
			}
		} else {
			path = path + "/recurso/";
		}
		return path + id;
	}
	
	private String getComplexId(String id){
		String output = "";
		String subString = id.substring(id.indexOf("[")+1, id.indexOf("]"));
		StringTokenizer st = new StringTokenizer(subString, ",");
		String token = st.nextToken().trim();
		if (token.contains("=")){
			token = token.substring(token.indexOf("=")+1);
		}
		output = token;
		
		while (st.hasMoreElements()){
			token = st.nextToken().trim();
			if (token.contains("=")){
				token = token.substring(token.indexOf("=")+1);
			}
			output = output + "_" + token;
		}
		try {
			output = URLEncoder.encode(output,"utf-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		
		return output;
	}
	
	private String getComplexId(String id, String raiz, String prefijo){
		String output = "";
		String subString = id.substring(id.indexOf("[")+1, id.indexOf("]"));
		StringTokenizer st = new StringTokenizer(subString, ",");
		String token = st.nextToken().trim();
		if (token.contains("=")){
			token = token.substring(token.indexOf("=")+1);
		}
		output = token;
		if (raiz.endsWith("/"+output)){
			token = st.nextToken().trim();
			if (token.contains("=")){
				token = token.substring(token.indexOf("=")+1);
			}
			output = "/" + prefijo + "/" +token;
		}
		while (st.hasMoreElements()){
			token = st.nextToken().trim();
			if (token.contains("=")){
				token = token.substring(token.indexOf("=")+1);
			}
			output = output + "-" + token;
		}
		return output;
	}
	
	private String obtenerValorAnotacionRDF(Rdf ann) {
		if ("".equals(ann.uri())) {
			String textToReturn="\"" + Context.listado.get(ann.contexto()).getUri()
					+ ann.propiedad()  + "\"";			
			return textToReturn;
		} else {
			return "\"" + ann.uri() + "\"";
		}
	}
	
	private String obtenerTipoNodoBlanco(RdfBlankNode ann)
	{
		return ann.tipo();
	}
	

	private String obtenerIdNodoBlanco(RdfBlankNode ann)
	{
		return ann.nodoId();
	}
	
	private String obtenerPropiedadNodoBlanco(RdfBlankNode ann)
	{
		return ann.propiedad();
	}
	
	private String obtenerTipoLiteralAnotacionRDF(Rdf ann) {
		return ann.typeURI();
	}
	
	private String obtenerInicioURIAnotacionExternalRDF(RdfExternalURI ann) {
		
		return ann.inicioURI();
	}
	
	private String obtenerInicioURIAnotacionDinamicRDF(RdfDinamico ann) {
		
		return ann.inicioURI();
	}
	
	private String obtenerSeparadorAnotacionExternalRDF(RdfExternalURI ann) {
		
		return ann.separador();
	}
	
	private String obtenerFinURIKeyAnotacionExternalRDF(RdfExternalURI ann) {
		return ann.finURI();
	}
	
	private String obtenerFinURIKeyAnotacionDinamicRDF(RdfDinamico ann) {
		return ann.finURI();
	}
	
	private int obtenerUrifyLevelAnotacionExternalRDF(RdfExternalURI ann) {
		return ann.urifyLevel();
	}
	
	private boolean obtenerCapitalizeAnotacionExternalRDF(RdfExternalURI ann) {
		return ann.capitalize();
	}
	
	
	
	private String obtenerPropiedadAnotacionExternalRDF(RdfExternalURI ann) {
		return ann.propiedad();
	}
	
	private String obtenerPropiedadAnotacionDinamicRDF(RdfDinamico ann) {
		return ann.propiedad();
	}
	
	private String obtenerRdfTypeAnotacionDinamicRDF(RdfDinamico ann) {
		return ann.rdfType();
	}
	
	private String obtenerPropiedadAnotacionRdfList(RdfList ann) {
		return ann.propiedad();
	}
	
	private String obtenerPropiedadID(CustomId ann) {
		return ann.id();
	}
		
	private String obtenerTipoAnotacionExternalRDF(RdfExternalURI ann) {
		return ann.tipo();
	}

	public static boolean transformarCampo(Object retorno, Peticion peticion,
			String prefijo, Field field) {

		return peticion.puedeVerCampoEnSeccion(field, peticion
				.getPermisosEnSeccion(), peticion.getMetodo())
				&& peticion.quiereVerCampo(prefijo, field.getName(), peticion
						.getSelectedFields())
				&& Funciones.retrieveObjectValue(retorno, field.getName()) != null;
	}
	
	public static String getHash(String message) {
        MessageDigest md;
        byte[] buffer, digest;
        String hash = "";

        try {
            buffer = message.getBytes("UTF-8");
            md = MessageDigest.getInstance("SHA1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        md.update(buffer);
        digest = md.digest();
        for (byte aux : digest) {
            int b = aux & 0xff;
            String s = Integer.toHexString(b);
            if (s.length() == 1) {
                hash += "0";
            }
            hash += s;
        }
        return hash;
    }
	
	public static boolean containsRdfsType(Object object){
		for (Field f : object.getClass().getDeclaredFields()) {
			if ((f.isAnnotationPresent(RdfType.class)) )
				return true;
		}
		return false;
	}
	
	
	
	
	public Map<String, String> getPrefixes()
	{
		return prefixes;
	}

	public void setPrefixes(Map<String, String> prefixes)
	{
		this.prefixes=prefixes;
	}

	
}
