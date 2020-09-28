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

package org.ciudadesabiertas.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.LogManager;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.persistence.Column;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.jena.ext.com.google.common.base.Charsets;
import org.ciudadesAbiertas.rdfGeneratorZ.TransformadorBasicoRdf;
import org.ciudadesabiertas.config.multipe.MultipleConf;
import org.ciudadesabiertas.config.multipe.MultipleDataSource;
import org.ciudadesabiertas.config.multipe.MultipleSessionFactory;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.StringToDateConverter;
import org.ciudadesabiertas.utils.StringToNumberConverter;
import org.ciudadesabiertas.utils.SwaggerConstants;
import org.ciudadesabiertas.utils.Util;
import org.ciudadesabiertas.utils.converters.CSVConverter;
import org.ciudadesabiertas.utils.converters.GEOJSONConverter;
import org.ciudadesabiertas.utils.converters.GEORSSConverter;
import org.ciudadesabiertas.utils.converters.RDFConverter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.ISpringTemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Configuration
@EnableSwagger2
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan(basePackages =  {"org.ciudadesabiertas.*"})
@Import({ SecurityConfig.class })
public class WebConfig extends WebMvcConfigurerAdapter
{
	private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

	@Autowired
	private Environment env;

	@Autowired
	private ApplicationContext applicationContext;
	
	// para poder servir recursos
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler(Constants.SWAGGER + "**").addResourceLocations(Constants.SWAGGER);
		
		registry.addResourceHandler(Constants.RESOURCES + "**").addResourceLocations(Constants.RESOURCES);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
	{
		// JSON como contenido por defecto
		configurer.defaultContentType(MediaType.APPLICATION_JSON).		
		// le decimos que la extesion jsonld se corresponde con su mimetype
		mediaType(Constants.FORMATO_HTML, MediaType.valueOf(Constants.MEDIA_TYPE_TEXT)).
		mediaType(Constants.FORMATO_CSV, MediaType.valueOf(Constants.MEDIA_TYPE_CSV)).
		mediaType(Constants.FORMATO_JSONLD, MediaType.valueOf(RDFConverter.JSONLD)).
		mediaType(Constants.FORMATO_TTL, MediaType.valueOf(RDFConverter.TURTLE)).
		mediaType(Constants.FORMATO_N3, MediaType.valueOf(RDFConverter.N3)).
		mediaType(Constants.FORMATO_RDF, MediaType.valueOf(RDFConverter.RDF_XML)).
		mediaType(Constants.FORMATO_GEOJSON, MediaType.valueOf(Constants.MEDIA_TYPE_GEOJSON+";charset=UTF-8")).
		mediaType(Constants.FORMATO_GEORSS, MediaType.valueOf(Constants.MEDIA_TYPE_GEORSS)).
		mediaType(Constants.FORMATO_ODATA, MediaType.valueOf(Constants.MEDIA_TYPE_GEORSS));
		

	}

	// Funcion formatear el JSON que devolvemos
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		for (HttpMessageConverter<?> converter : converters)
		{
			// System.out.println(("Converter: "+converter.getClass().getName()));
			if (converter instanceof MappingJackson2HttpMessageConverter)
			{
				MappingJackson2HttpMessageConverter jsonMessageConverter = (MappingJackson2HttpMessageConverter) converter;
				
				// eliminamos de formatos soportados por el JSON el *+json para que el xml no se
				// apodere de ld+json
				MediaType JSON_FORMAT[] = { MediaType.valueOf(Constants.CONTENT_TYPE_JSON_UTF8) };
				jsonMessageConverter.setSupportedMediaTypes(Arrays.asList(JSON_FORMAT));

				ObjectMapper objectMapper = jsonMessageConverter.getObjectMapper();
				// Fechas sin timestamps
				objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				// Y con este formato
				objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
				objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
				jsonMessageConverter.setPrettyPrint(true);
				
				StartVariables.jsonConverter=jsonMessageConverter;

			} else if (converter instanceof MappingJackson2XmlHttpMessageConverter)
			{
				MappingJackson2XmlHttpMessageConverter xmlMessageConverter = (MappingJackson2XmlHttpMessageConverter) converter;

				// eliminamos de formatos soportados por el XML el *+xml para que el xml no se
				// apodere de rdf+xml
				MediaType XMLs[] = { MediaType.valueOf(Constants.CONTENT_TYPE_XML_UTF8), MediaType.valueOf(Constants.CONTENT_TYPE_TEXT_UTF8) };
				xmlMessageConverter.setSupportedMediaTypes(Arrays.asList(XMLs));

				ObjectMapper objectMapper = xmlMessageConverter.getObjectMapper();
				// Fechas sin timestamps
				objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				// Y con este formato
				objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
				objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
				xmlMessageConverter.setPrettyPrint(true);
			}
		}

		StartVariables.context = applicationContext.getApplicationName();
		if (Util.validValue(env.getProperty(Constants.STR_CONTEXTO).trim()))
		{
			StartVariables.context = env.getProperty(Constants.STR_CONTEXTO).trim();
		}

		// Añadimos el CSV
		converters.add(new CSVConverter<>());

		// Añadimos el RDF/XML
		converters.add(new RDFConverter<>(RDFConverter.RDF_XML, env.getProperty(Constants.URI_BASE), StartVariables.context));

		// Añadimos el ttl
		converters.add(new RDFConverter<>(RDFConverter.TURTLE, env.getProperty(Constants.URI_BASE), StartVariables.context));

		// Añadimos N3
		converters.add(new RDFConverter<>(RDFConverter.N3, env.getProperty(Constants.URI_BASE), StartVariables.context));

		// Añadimos JSONLD
		converters.add(new RDFConverter<>(RDFConverter.JSONLD, env.getProperty(Constants.URI_BASE), StartVariables.context));

		// Añadimos geojson		
		converters.add(new GEOJSONConverter());		
		
		// Añadimos georss		
		converters.add(new GEORSSConverter());

		
		
	}

	private Properties getHibernateProperties()
	{
		Properties prop = new Properties();
		prop.put(Constants.DB_HIBERNATE_FORMAT_SQL, env.getProperty(Constants.DB_HIBERNATE_FORMAT_SQL));
		prop.put(Constants.DB_HIBERNATE_SHOW_SQL, env.getProperty(Constants.DB_HIBERNATE_SHOW_SQL));
		prop.put(Constants.DB_HIBERNATE_DIALECT, env.getProperty(Constants.DB_HIBERNATE_DIALECT));
		prop.put(Constants.DB_HIBERNATE_USE_SQL_COMMENTS, env.getProperty(Constants.DB_HIBERNATE_USE_SQL_COMMENTS));
		return prop;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource()
	{

		BasicDataSource ds = new BasicDataSource();
		
		StartVariables.db_schema=env.getProperty(Constants.DB_SCHEMA);
		
		//Basic
		if ((Util.validValue(env.getProperty(Constants.DB_DRIVER))&&(Util.validValue(env.getProperty(Constants.DB_URL)))))
		{
			ds.setDriverClassName(env.getProperty(Constants.DB_DRIVER));
			ds.setUrl(env.getProperty(Constants.DB_URL));
			ds.setUsername(env.getProperty(Constants.DB_USER));
			ds.setPassword(Util.checkAndSetEncodedProperty(env.getProperty(Constants.DB_PASSWORD)));
	
			
			
			ds.setInitialSize(Integer.parseInt(env.getProperty(Constants.DB_INITIAL_SIZE)));
			ds.setMaxActive(Integer.parseInt(env.getProperty(Constants.DB_MAX_ACTIVE)));
			ds.setMaxIdle(Integer.parseInt(env.getProperty(Constants.DB_MAX_IDLE)));
			ds.setMinIdle(Integer.parseInt(env.getProperty(Constants.DB_MIN_IDLE)));
	
			// Evitar cierre de conexiones después de horas sin uso
			ds.setTestOnBorrow(true);
			ds.setValidationQuery(env.getProperty(Constants.DB_VALIDATION_QUERY));
	
			log.debug("[dataSource] [BasicDataSource:"+ds+"]");
			return (DataSource) ds;
		}
		//JNDI
		if (Util.validValue(env.getProperty(Constants.DB_JNDI_NAME))){
			String keyJNDI = env.getProperty(Constants.DB_JNDI_NAME);

			JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
			
			String envJndi = env.getProperty(Constants.STR_ENV_JNDI_CONTEXT);
			if (envJndi!=null && "".equals(envJndi)) {
				bean.setJndiName(envJndi + keyJNDI);
			}else {
				bean.setJndiName(Constants.ENV_JNDI_CONTEXT + keyJNDI);
			}
			bean.setProxyInterface(DataSource.class);
			//bean.setProxyInterface(BasicDataSource.class);
			bean.setLookupOnStartup(false);
			try {
				bean.afterPropertiesSet();
			} catch (IllegalArgumentException | NamingException e) {
				log.error("[dataSource] [Error:"+e.getMessage()+"] ");
			}
		
			
			return (DataSource) bean.getObject();
		}
		log.error("[dataSource] [Error:NO  BasicDataSource created ");
		return null;
	}

	@Bean
	public SessionFactory sessionFactory()
	{

		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
		
		Properties hibernateProperties = getHibernateProperties();
		
		if (Util.validValue(StartVariables.db_schema))
		{
			hibernateProperties.put(Constants.DB_HIBERNATE_DEFAULT_SCHEMA, StartVariables.db_schema);
		}
		
		
		builder
			.scanPackages("org.ciudadesabiertas.users.model")
			.scanPackages("org.ciudadesabiertas.model")
			.scanPackages(Constants.PAQUETE_MODELO_CONJUNTOS_DATOS)
			.addProperties(hibernateProperties);

		return builder.buildSessionFactory();
	}

	@Bean(name = "txManager")
	@Primary
	public HibernateTransactionManager txManager()
	{
		log.info("txManager");
		return new HibernateTransactionManager(sessionFactory());

	}

	// Configuración CORS
	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/**").allowedOrigins("*");
	}

	// Formateadores para Spring
	@Override
	public void addFormatters(FormatterRegistry registry)
	{
		registry.addConverter(new StringToDateConverter());
		registry.addConverterFactory( new StringToNumberConverter());
	}

	// Beans thymeleaf

	// Carga las plantillas a nivel de fichero
	
	
	@Bean
	public ITemplateResolver templateResolver()
	{
		if (Util.isPathAbsolute(StartVariables.PATH_TEMPLATE)) {
			FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();		
			//fileTemplateResolver.setApplicationContext(this.applicationContext);
			fileTemplateResolver.setPrefix(StartVariables.PATH_TEMPLATE);
			
			fileTemplateResolver.setCharacterEncoding(Charsets.UTF_8.name());
			fileTemplateResolver.setTemplateMode(TemplateMode.HTML);
			
			fileTemplateResolver.setSuffix(Constants.EXT_HTML);		
			// TODO cuidado con esto
			fileTemplateResolver.setCacheable(false);
			return fileTemplateResolver;
		}else {
			SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
			templateResolver.setApplicationContext(this.applicationContext);
			templateResolver.setPrefix(StartVariables.PATH_TEMPLATE);
			
			templateResolver.setCharacterEncoding(Charsets.UTF_8.name());
		    templateResolver.setTemplateMode(TemplateMode.HTML);
		    
			templateResolver.setSuffix(Constants.EXT_HTML);
			// TODO cuidado con esto
			templateResolver.setCacheable(false);
			return  templateResolver;
		}
		
	}
	
	/*
	@Bean
	public FileTemplateResolver templateResolver()
	{
		FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();		
		//templateResolver.setApplicationContext(this.applicationContext);
		fileTemplateResolver.setPrefix("D:\\git\\CiudadesAbiertas\\API_CA\\API_WEB\\src\\main\\webapp\\WEB-INF\\templates\\");
		fileTemplateResolver.setSuffix(Constants.EXT_HTML);		
		// TODO cuidado con esto
		fileTemplateResolver.setCacheable(false);
		return fileTemplateResolver;
	}
	*/
	

	@Bean
	public ISpringTemplateEngine templateEngine()
	{
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver()
	{
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	// Beans para controlar multiples ficheros de configuración ->multiples bbdd

	@Bean
	public MultipleConf multipleConf()
	{

		ClassLoader classLoader = getClass().getClassLoader();
		MultipleConf multipleConf = new MultipleConf(classLoader);
		return multipleConf;
	}

	@Bean
	public MultipleDataSource multipleDataSource()
	{

		MultipleDataSource multipleDataSource = new MultipleDataSource(multipleConf());
		return multipleDataSource;
	}

	@Bean(name = "MultipleSessionFactory")
	public MultipleSessionFactory multipleSessionFactory()
	{

		MultipleSessionFactory multipleSessionFactory = new MultipleSessionFactory(multipleDataSource());
		return multipleSessionFactory;

	}

	// Fin Beans Multiples conexiones

	// Metodo para realizar tareas depués de cargar
	@PostConstruct
	public void postConstruct() throws MalformedURLException
	{
		String maxSize = env.getProperty(Constants.STR_PAGE_MAX);
		String defaultSize = env.getProperty(Constants.STR_PAGE_DEFAULT);
		String maxPetitionsPerSecondAnonymous = env.getProperty(Constants.STR_NUMBER_REQUEST_PER_SECOND_ANONYMOUS);
		String maxPetitionsPerSecondIdentified = env.getProperty(Constants.STR_NUMBER_REQUEST_PER_SECOND_INDENTIFIED);
		String nodoPattern = env.getProperty(Constants.STR_NODO_PATTERN);
		String srId_XY_App = env.getProperty(Constants.STR_XY_ETRS89_EPSG);
		String srId_LATLON_App = env.getProperty(Constants.STR_LAT_LON_ETRS89_EPSG);
		String str_pathTemplate = env.getProperty(Constants.STR_PATH_TEMPLATE);
		String str_rsql_log_active = env.getProperty(Constants.STR_RSQL_LOG_ACTIVE);
		String str_active_fk = env.getProperty(Constants.STR_ACTVE_FOREIGN_KEY);
		
		if (Util.validValue(env.getProperty(Constants.URI_BASE)))
		{
			if (Util.validateURL(env.getProperty(Constants.URI_BASE)))
			{
				StartVariables.uriBase=env.getProperty(Constants.URI_BASE);
				StartVariables.serverPort=StartVariables.uriBase.substring(StartVariables.uriBase.indexOf("//")+2);
				StartVariables.schema=StartVariables.uriBase.substring(0,StartVariables.uriBase.indexOf("://")+3);
			}else{
				throw new MalformedURLException(Constants.URI_BASE);
			}
			
		}
		
		
		if (Util.validValue(env.getProperty(Constants.STR_HTML_TITLE)))
		{
			StartVariables.HTML_TITLE=env.getProperty(Constants.STR_HTML_TITLE);
		}
		
		if (Util.validValue(str_pathTemplate))
		{
			StartVariables.PATH_TEMPLATE=str_pathTemplate;
			log.info("PATH TEMPLATE: " + StartVariables.PATH_TEMPLATE);
		}else {
			log.error("PATH TEMPLATE (DEFAULT): " + StartVariables.PATH_TEMPLATE);
		}


		try
		{
			StartVariables.maxPageSize = Integer.parseInt(maxSize);
			log.info("max. size set to " + maxSize);
		} catch (Exception e)
		{
			log.error("Wrong maxSize in properties file: " + maxSize);
		}
		try
		{
			StartVariables.defaultPageSize = Integer.parseInt(defaultSize);
			log.info("default page size set to " + defaultSize);
		} catch (Exception e)
		{
			log.error("Wrong default page size in properties file: " + defaultSize);
		}
		try
		{
			StartVariables.MAX_NUMBER_REQUEST_PER_SECOND_ANONYMOUS = Integer.parseInt(maxPetitionsPerSecondAnonymous);
			log.info("max petitions per second by users anonymous se to " + maxPetitionsPerSecondAnonymous);
		} catch (Exception e)
		{
			log.error("Wrong max petitions per second by users anonymous in properties file: " + maxPetitionsPerSecondAnonymous);
		}
		try
		{
			StartVariables.MAX_NUMBER_REQUEST_PER_SECOND_INDENTIFIED = Integer.parseInt(maxPetitionsPerSecondIdentified);
			log.info("max petitions per second by users indentified se to " + maxPetitionsPerSecondIdentified);
		} catch (Exception e)
		{
			log.error("Wrong max petitions per second by users indentified in properties file: " + maxPetitionsPerSecondIdentified);
		}

		if (Util.validValue(nodoPattern))
		{
			StartVariables.NODO_PATTERN = nodoPattern;
			log.info("Nodo Configuration Character: " + StartVariables.NODO_PATTERN);
		} else
		{
			log.error("Nodo No Configuration Character (Default): " + StartVariables.NODO_PATTERN);
		}
		
		if (Util.validValue(srId_XY_App))
		{
			StartVariables.SRID_XY_APP = srId_XY_App;
			log.info("APP Configuration " + Constants.STR_PROJECTEDCOORDINATES + " " + StartVariables.SRID_XY_APP);
		} else
		{
			log.error("APP NO Configuration " + Constants.STR_PROJECTEDCOORDINATES + " (Default) " + StartVariables.SRID_XY_APP);
		}
		
		if (Util.validValue(srId_LATLON_App))
		{
			StartVariables.SRID_LAT_LON_APP = srId_LATLON_App;
			log.info("APP Configuration " + Constants.STR_GEOGRAPHICCOORDINATES + " " + StartVariables.SRID_LAT_LON_APP);
		} else
		{
			log.error("APP NO Configuration " + Constants.STR_GEOGRAPHICCOORDINATES + " (Default) " + StartVariables.SRID_LAT_LON_APP);
		}

		
		StartVariables.mapaClasesColumnas=generarMapaTablasColumnas();		
		
		
		if (Util.validValue(str_rsql_log_active))
		{
			StartVariables.RSQL_LOG_ACTIVE = Constants.STR_ACTIVE_ON.equals(str_rsql_log_active.toLowerCase());
			log.info("RSQL LOG ACTIVE: " + StartVariables.RSQL_LOG_ACTIVE);
		} else
		{
			log.error("RSQL LOG ACTIVE (Default): " + StartVariables.RSQL_LOG_ACTIVE);
		}
		
		//CMG: DESACTIVAMOS EL LOG PARA LA LIBRERIA RSQL : com.github.tennaito.rsql.jpa
		if (!StartVariables.RSQL_LOG_ACTIVE) {
			LogManager.getLogManager().reset();
			java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
			globalLogger.setLevel(java.util.logging.Level.OFF);
		}
		//FIN DESACTIVACION
		
		//Control de fk
		if (Util.validValue(str_active_fk))
		{
			StartVariables.activeFK = !Constants.STR_INACTIVE_FALSE.equals(str_active_fk.toLowerCase());
			
		} else
		{	//POR DEFECTO SI NO SE CONFIGURA ES UN TRUE
			StartVariables.activeFK = Boolean.TRUE;
		}
		log.info("ACTIVE FOREIGN KEY: " + StartVariables.activeFK);
		
		if (env.getProperty("presupuestos.urlSkos.clasificacionEconomicaGasto")!=null)
		{
			StartVariables.presupuestosUrlSkosClasificacionEconomicaGasto=env.getProperty("presupuestos.urlSkos.clasificacionEconomicaGasto");
		}
		if (env.getProperty("presupuestos.urlSkos.clasificacionPrograma")!=null)
		{
			StartVariables.presupuestosUrlSkosClasificacionPrograma=env.getProperty("presupuestos.urlSkos.clasificacionPrograma");
		}
		if (env.getProperty("presupuestos.urlSkos.clasificacionOrganica")!=null)
		{
			StartVariables.presupuestosUrlSkosClasificacionOrganica=env.getProperty("presupuestos.urlSkos.clasificacionOrganica");			
		}
		TransformadorBasicoRdf.variables.put("clasificacionEconomicaIngreso",StartVariables.presupuestosUrlSkosClasificacionEconomicaIngreso);
		TransformadorBasicoRdf.variables.put("clasificacionEconomicaGasto",StartVariables.presupuestosUrlSkosClasificacionEconomicaGasto);
		TransformadorBasicoRdf.variables.put("clasificacionPrograma",StartVariables.presupuestosUrlSkosClasificacionPrograma);
		TransformadorBasicoRdf.variables.put("clasificacionOrganica",StartVariables.presupuestosUrlSkosClasificacionOrganica);
	
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	

	// Bean para la documentación con swagger accesible desde:
	// http://localhost:8080/API/swagger-ui.html
	// http://localhost:8080/API/v2/api-docs
	@Bean
	public Docket api()
	{

		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("Authorization").description("Access Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		List<Parameter> aParameters = new ArrayList<Parameter>();
		aParameters.add(aParameterBuilder.build());

		//expresion regular para ignorar todas las URLs que empiezen por v1
		String ignoreURLsV1="(?!/v1).+";
		
		return new Docket(DocumentationType.SWAGGER_2)
				.ignoredParameterTypes(ModelAndView.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.ciudadesabiertas.dataset"))
				//.paths(PathSelectors.any())
				.paths(PathSelectors.regex(ignoreURLsV1))
				.build()
				.securitySchemes(Arrays.asList(apiKey()))
				.apiInfo(apiInfo())
				.globalOperationParameters(aParameters);
	}
	
	


	private ApiKey apiKey()
	{
		return new ApiKey("apiKey", "Authorization", "header");
	}

	private ApiInfo apiInfo()
	{

		springfox.documentation.service.Contact contact = new springfox.documentation.service.Contact(SwaggerConstants.NOMBRE_CONTACTO, SwaggerConstants.WEB_CONTACTO, SwaggerConstants.EMAIL_CONTACTO);

		return new ApiInfo(SwaggerConstants.TITULO_API, SwaggerConstants.DESCRIPTION_API, SwaggerConstants.VERSION_API, SwaggerConstants.URL_WEB, contact, SwaggerConstants.TEXTO_LICENCIA, SwaggerConstants.URL_LICENCIA, Collections.emptyList());

	}
	
	/*Funcion para extraer las columnas de cada tabla y guardar su nombre en java y su nombre en bbdd*/
	private HashMap<String,Map<String,String>> generarMapaTablasColumnas()
	{
		HashMap<String,Map<String,String>> mapaClasesColumnas = new HashMap<String,Map<String,String>>();
		
		try
		{

			final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

			provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
	
			// get matching classes defined in the package
			final Set<BeanDefinition> classes = provider.findCandidateComponents("org.ciudadesabiertas.dataset.model");
	
			boolean ikeyError=false;
			for (BeanDefinition bean: classes) 
			{
				Map<String,String> mapaJavaNameColumna=new HashMap<String,String>();
			    Class<?> clazz = Class.forName(bean.getBeanClassName());
			    Field[] fields=Class.forName(clazz.getName()).getDeclaredFields();			    
			    for (Field field : fields) 
			    {
	                if (field.getName().equals("ikey")) {
	                	boolean fail=false;
	                	ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
	                    if (annotation!=null)
	                    {
	                    	if ((annotation.hidden()==false))
	                    	{
	                    		fail=true;
	                    	}
	                    }
	                    else 
	                    {
	                    	fail=true;
	                    }
	                    if (fail)
	                    {
	                    	ikeyError=true;
	                    	log.error("ikeyError: se esta exponiendo el ikey in the class: "+bean.getBeanClassName());
	                    }
	                }	                
	            }
			    
			    
			    log.debug(clazz.getName());
			    Method[] methods=Class.forName(clazz.getName()).getDeclaredMethods();
			    for (Method method : methods) 
			    {
	                if (method.getAnnotation(Column.class) != null) {	                    
	                    Column annotation = method.getAnnotation(Column.class);
	                    String columna=annotation.name();
	                    String metodo=method.getName();
	                    //borramos el 'get' inicial
	                    metodo=metodo.substring(3);
	                    //pasamos a minuscula la primera letra
	                    metodo=metodo.substring(0, 1).toLowerCase()+metodo.substring(1);
	                    log.debug(metodo+" "+columna);
	                    mapaJavaNameColumna.put(metodo, columna);	                    
	                }	                
	            }
			    mapaClasesColumnas.put(clazz.getName(), mapaJavaNameColumna);
			}			
			if (ikeyError==false)
			{
			    	log.info("Campos ikey sin exponer en todos los modelos");
			}
			
		}
		catch (Exception e)
		{
			log.error("Error generating mapaClasesColumnas",e);
		}
		return mapaClasesColumnas;
	}
	

}
