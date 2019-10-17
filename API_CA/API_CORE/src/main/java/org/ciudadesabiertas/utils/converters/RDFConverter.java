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

package org.ciudadesabiertas.utils.converters;


import org.ciudadesAbiertas.rdfGeneratorZ.Formato;
import org.ciudadesAbiertas.rdfGeneratorZ.Peticion;
import org.ciudadesAbiertas.rdfGeneratorZ.TransformadorBasicoRdf;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;



/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 * @param <T>
 * @param <L>
 */
@Component
public class RDFConverter <T, L extends Result<T>> extends AbstractHttpMessageConverter<L> {

	private static final Logger log = LoggerFactory.getLogger(RDFConverter.class);

	private String URIBase;
	private String context;
	private String format;
	
	public static final String TURTLE = "text/turtle";
	public static final String RDF_XML = "application/rdf+xml";
	public static final String N3 = "text/n3";
	public static final String JSONLD = "application/ld+json";
    
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	

    public RDFConverter() {    	
      	 super(MediaType.valueOf(TURTLE));
      	 format=TURTLE;
    }
    
    
    public RDFConverter(String format, String URIBase, String context) {    	
   	 super(MediaType.valueOf(format));
   	 this.format=format;
   	 this.URIBase=URIBase;
   	 this.context=context;   	 
   }

    @Override
    protected boolean supports (Class<?> clazz) {
        return Result.class.isAssignableFrom(clazz);
    }
    
	@Override
	protected L readInternal(Class<? extends L> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Charset getCharset(HttpHeaders headers) {
		if (headers == null || headers.getContentType() == null || headers.getContentType().getCharSet() == null) {
			return DEFAULT_CHARSET;
		}
		return headers.getContentType().getCharSet();
	}
    
    @SuppressWarnings("unchecked")
    @Override
    protected void writeInternal (L l, HttpOutputMessage outputMessage)
              throws IOException, HttpMessageNotWritableException {
        
        
        try {
        	
        	Charset charset = getCharset(outputMessage.getHeaders());
        	
        	TransformadorBasicoRdf rdfT=new TransformadorBasicoRdf(URIBase,context);
    		   		
    		if (l.getRecords().size()>0)
    		{
    			RDFModel firstObject=(RDFModel) l.getRecords().get(0);
    			Map<String, String> prefixesRDF = firstObject.prefixes();
    			if ((prefixesRDF!=null)&&(prefixesRDF.size()>0))
    			{
    				rdfT.setPrefixes(prefixesRDF);
    			}
    		}
    		
    		StringBuilder respuesta=new StringBuilder();
    		
    		Result<T> result=new Result<T>(); 
    		
    		result.setRecords(l.getRecords());

    		Peticion peticion=new Peticion();
    		Formato f=new Formato();
    		f.setExtension("ttl");
    		if (format.equals(RDF_XML))
    		{
    			f.setExtension("RDF/XML");
    		}else if (format.equals(N3))
    		{
    			f.setExtension("n3");
    		}else if (format.equals(JSONLD))
    		{
    			f.setExtension("jsonld");
    		} 
    		
    		peticion.setFormato(f);
    		
    		boolean primero=true;
    		String prefijo="";
    		
    			
    		try
    		{
    			rdfT.transformarObjeto(respuesta,result, peticion, primero, prefijo);
    			   
    			OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), charset);    			
    			writer.append(respuesta);
    			writer.close();
    		}
    		catch (Exception e)
    		{
    			log.error("Error generating RDF",e);
    			throw e;
    		}
    			
    			
    	
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private Class<T> toBeanType (Type type) {
        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }



}