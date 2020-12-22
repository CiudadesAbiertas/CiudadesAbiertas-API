package org.ciudadesabiertas.dataset.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel
@Table(name = "empleo_convocatoria_publica", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "ConvocatoriaEmpleoPublico")
@PathId(value = "/empleo/convocatoria-empleo-publico")
public class ConvocatoriaEmpleoPublico implements java.io.Serializable, RDFModel {

@JsonIgnore
private static final long serialVersionUID = 8064356584427652694L;

@ApiModelProperty(hidden = true)
@JsonIgnore
private String ikey;

@ApiModelProperty(value = "Identificador la Convocatoria. Ejemplo: CONEM0001")
@CsvBindByPosition(position=1)
@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
private String id;

@ApiModelProperty(value = "Nombre de la Convocatoria. Ejemplo: Convocatoria Informática Noviembre 2020")
@CsvBindByPosition(position=2)	
@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.DCT, propiedad = "title")
private String title;

@ApiModelProperty(value = "Descripción de la convocatoria. Ejemplo: Convocatoria para cubrir 5 plazas para el despartamento de Informática")
@CsvBindByPosition(position=3)	
@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.SCHEMA, propiedad = "description")
private String description;

@ApiModelProperty(value = "Fecha de publicación de la convocatoria. Ejemplo: 2020-11-02T14:00:00")
@CsvBindByPosition(position=4)
@CsvBindByName(column="datePublished", format=Constants.STRING_FORMAT)
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.SCHEMA, propiedad = "datePublished", typeURI=Context.XSD_URI+"date")
private Date datePublished;

@ApiModelProperty(value = "Fecha de aprobación de la convocatoria. Ejemplo: 2020-10-28T10:00:00")
@CsvBindByPosition(position=5)
@CsvBindByName(column="fechaAprobacion", format=Constants.STRING_FORMAT)
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "fechaAprobacion", typeURI=Context.XSD_URI+"date")
private Date fechaAprobacion;

@ApiModelProperty(value = "Fecha de aprobación de la resolución. Ejemplo: 2020-10-20T14:00:00")
@CsvBindByPosition(position=6)
@CsvBindByName(column="fechaResolucion", format=Constants.STRING_FORMAT)
@CsvDate(Constants.DATE_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "fechaResolucion", typeURI=Context.XSD_URI+"date")
private Date fechaResolucion;

@ApiModelProperty(value = "Propiedad que indica si esta abierto del plazo de la convocatoria. Ejemplo: true")
@CsvBindByPosition(position=7)
@CsvBindByName(column="fechaResolucion", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "estadoPlazo", typeURI=Context.XSD_URI+"boolean")
private Boolean estadoPlazo;

@ApiModelProperty(value = "Plazos de la convocatoria. Ejemplo: Hasta el 03/12/2021")
@CsvBindByPosition(position=8)
@CsvBindByName(column="plazos", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "plazos")
private String plazos;

@ApiModelProperty(value = "Número de plazas de la convocatoria. Ejemplo: 5")
@CsvBindByPosition(position=9)
@CsvBindByName(column="numeroPlazasConvocadas")
@Rdf(contexto = Context.ESEMPLEO, propiedad = "numeroPlazasConvocadas", typeURI=Context.XSD_URI+"integer")
private Integer numeroPlazasConvocadas;

@ApiModelProperty(value = "Propiedad que indica si hay lista de espera de la convocatoria. Ejemplo: true")
@CsvBindByPosition(position=10)
@CsvBindByName(column="listaEsperaExtraordinaria", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "listaEsperaExtraordinaria", typeURI=Context.XSD_URI+"boolean")
private Boolean listaEsperaEx;

@ApiModelProperty(value = "Observaciones de la convocatoria. Ejemplo: El plazo de presentación de solicitudes*")
@CsvBindByPosition(position=11)	
@CsvBindByName(column="observaciones", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "observaciones")
private String observaciones;


@ApiModelProperty(value = "Disposiciones de la convocatoria. Ejemplo: BOLETÍN OFICIAL DEL ESTADO, Dispuesto el 17/09/2020, Publicado el 12/10/2020")
@CsvBindByPosition(position=12)	
@CsvBindByName(column="disposiciones", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "disposiciones")
private String disposiciones;


@ApiModelProperty(value = "Requisitos de la convocatoria. Ejemplo: Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática")
@CsvBindByPosition(position=13)	
@CsvBindByName(column="requisitos", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "requisitos")
private String requisitos;


@ApiModelProperty(value = "Bases de la convocatoria. Ejemplo: Cumplir  los  requisitos*")
@CsvBindByPosition(position=14)	
@CsvBindByName(column="bases", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "bases")
private String bases; 


@ApiModelProperty(value = "URL de las bases de la convocatoria. Ejemplo: http://www.aytoMadrid.org/basesConvocatoria/informatica/2020")
@CsvBindByPosition(position=15)	
@CsvBindByName(column="basesUrl", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "basesURL", typeURI=Context.XSD_URI+"anyURI")
private String basesUrl;

@ApiModelProperty(value = "URL del formulario de inscripción de la convocatoria. Ejemplo: http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario")
@CsvBindByPosition(position=16)	
@CsvBindByName(column="formularioInscripcion", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "formularioInscripcion", typeURI=Context.XSD_URI+"anyURI")
private String formularioInscripcion;

@ApiModelProperty(value = "Pruebas de la convocatoria. Ejemplo: Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse")
@CsvBindByPosition(position=17)	
@CsvBindByName(column="pruebas", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "pruebas")
private String pruebas;

@ApiModelProperty(value = "Grupo profesional de la convocatoria. Ejemplo: A1")
@CsvBindByPosition(position=18)	
@CsvBindByName(column="grupoProfesional", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "grupoProfesional")
@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/empleo/grupo-profesional/", finURI="grupoProfesional", urifyLevel=2)
private String grupoProfesional;

@ApiModelProperty(value = "Tipo de empleado público para la convocatoria. Ejemplo: funcionario")
@CsvBindByPosition(position=19)	
@CsvBindByName(column="empleadoPublico", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "empleadoPublico")
@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/empleo/empleado-publico/", finURI="empleadoPublico", urifyLevel=2)
private String empleadoPublico;


@ApiModelProperty(value = "Cuerpo o escala de la administración pública para la convocatoria. Ejemplo: administracion-general")
@CsvBindByPosition(position=20)	
@CsvBindByName(column="cuerpo", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "cuerpo")
@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/page/datosabiertos/kos/sector-publico/empleo/cuerpo/", finURI="cuerpo", urifyLevel=2)
private String cuerpo;


@ApiModelProperty(value = "Modalidad de la convocatoria. Ejemplo: oposición")
@CsvBindByPosition(position=21)	
@CsvBindByName(column="modalidad", format=Constants.STRING_FORMAT)
@Rdf(contexto = Context.ESEMPLEO, propiedad = "modalidad")
@RdfExternalURI(inicioURI="http://vocab.linkeddata.es/datosabiertos/kos/sector-publico/empleo/modalidad/", finURI="modalidad", urifyLevel=2)
private String modalidad;

public ConvocatoriaEmpleoPublico() {
}


public ConvocatoriaEmpleoPublico(ConvocatoriaEmpleoPublico copia) {
  super();
  this.ikey = copia.ikey;
  this.id = copia.id;
  this.title = copia.title;
  this.description = copia.description;
  this.datePublished = copia.datePublished;
  this.fechaAprobacion = copia.fechaAprobacion;
  this.fechaResolucion = copia.fechaResolucion;
  this.estadoPlazo = copia.estadoPlazo;
  this.plazos = copia.plazos;
  this.numeroPlazasConvocadas = copia.numeroPlazasConvocadas;
  this.listaEsperaEx = copia.listaEsperaEx;
  this.observaciones = copia.observaciones;
  this.disposiciones = copia.disposiciones;
  this.requisitos = copia.requisitos;
  this.bases = copia.bases;
  this.basesUrl = copia.basesUrl;
  this.formularioInscripcion = copia.formularioInscripcion;
  this.pruebas = copia.pruebas;
  this.grupoProfesional = copia.grupoProfesional;
  this.empleadoPublico = copia.empleadoPublico;
  this.cuerpo = copia.cuerpo;
  this.modalidad = copia.modalidad;
}

public ConvocatoriaEmpleoPublico(ConvocatoriaEmpleoPublico copia, List<String> attributesToSet) {

  if (attributesToSet.contains(Constants.IKEY)) {
	this.ikey = copia.ikey;
  }
  if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
	this.id = copia.id;
  }
  if (attributesToSet.contains("title")) {
	this.title = copia.title;
  }  
  if (attributesToSet.contains("description")) {
	this.description = copia.description;
  }
  if (attributesToSet.contains("datePublished")) {
	 this.datePublished = copia.datePublished;
  }if (attributesToSet.contains("fechaAprobacion")) {
	  this.fechaAprobacion = copia.fechaAprobacion;
  }if (attributesToSet.contains("fechaResolucion")) {
	  this.fechaResolucion = copia.fechaResolucion;
  }if (attributesToSet.contains("estadoPlazo")) {
	  this.estadoPlazo = copia.estadoPlazo;
  }if (attributesToSet.contains("plazos")) {
	  this.plazos = copia.plazos;
  }if (attributesToSet.contains("numeroPlazasConvocadas")) {
	  this.numeroPlazasConvocadas = copia.numeroPlazasConvocadas;
  }if (attributesToSet.contains("listaEsperaEx")) {
	  this.listaEsperaEx = copia.listaEsperaEx;
  }if (attributesToSet.contains("observaciones")) {
	  this.observaciones = copia.observaciones;
  }if (attributesToSet.contains("disposiciones")) {
	  this.disposiciones = copia.disposiciones;
  }if (attributesToSet.contains("requisitos")) {
	  this.requisitos = copia.requisitos;
  }if (attributesToSet.contains("bases")) {
	  this.bases = copia.bases;
  }if (attributesToSet.contains("basesUrl")) {
	  this.basesUrl = copia.basesUrl;
  }if (attributesToSet.contains("formularioInscripcion")) {
	  this.formularioInscripcion = copia.formularioInscripcion;
  }if (attributesToSet.contains("pruebas")) {
	  this.pruebas = copia.pruebas;
  }if (attributesToSet.contains("grupoProfesional")) {
	  this.grupoProfesional = copia.grupoProfesional;
  }if (attributesToSet.contains("empleadoPublico")) {
	  this.empleadoPublico = copia.empleadoPublico;
  }if (attributesToSet.contains("cuerpo")) {
	  this.cuerpo = copia.cuerpo;
  }if (attributesToSet.contains("modalidad")) {
	  this.modalidad = copia.modalidad;
  }
  

}


@Id
@Column(name = "ikey", unique = true, nullable = false, length = 50)
public String getIkey() {
  return this.ikey;
}

public void setIkey(String ikey) {
  this.ikey = ikey;
}

@Column(name = "id", unique = true, length = 50)
public String getId() {
  return this.id;
}

public void setId(String id) {
  this.id = id;
}

@Column(name = "title", length = 400)
public String getTitle() {
  return this.title;
}

public void setTitle(String title) {
  this.title = title;
}

@Column(name = "description", length = 2000)
public String getDescription() {
  return this.description;
}

public void setDescription(String description) {
  this.description = description;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "date_published", length = 26)
public Date getDatePublished() {
  return this.datePublished;
}

public void setDatePublished(Date datePublished) {
  this.datePublished = datePublished;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "fecha_aprobacion", length = 26)
public Date getFechaAprobacion() {
  return this.fechaAprobacion;
}

public void setFechaAprobacion(Date fechaAprobacion) {
  this.fechaAprobacion = fechaAprobacion;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "fecha_resolucion", length = 26)
public Date getFechaResolucion() {
  return this.fechaResolucion;
}

public void setFechaResolucion(Date fechaResolucion) {
  this.fechaResolucion = fechaResolucion;
}

@Column(name = "estado_plazo")
public Boolean getEstadoPlazo() {
  return this.estadoPlazo;
}

public void setEstadoPlazo(Boolean estadoPlazo) {
  this.estadoPlazo = estadoPlazo;
}

@Column(name = "plazos", length = 200)
public String getPlazos() {
  return this.plazos;
}

public void setPlazos(String plazos) {
  this.plazos = plazos;
}

@Column(name = "num_plazas_convo")
public Integer getNumeroPlazasConvocadas() {
  return this.numeroPlazasConvocadas;
}

public void setNumeroPlazasConvocadas(Integer numeroPlazasConvocadas) {
  this.numeroPlazasConvocadas = numeroPlazasConvocadas;
}

@Column(name = "lista_espera_ex")
public Boolean getListaEsperaEx() {
  return this.listaEsperaEx;
}

public void setListaEsperaEx(Boolean listaEsperaEx) {
  this.listaEsperaEx = listaEsperaEx;
}

@Column(name = "observaciones", length = 2000)
public String getObservaciones() {
  return this.observaciones;
}

public void setObservaciones(String observaciones) {
  this.observaciones = observaciones;
}

@Column(name = "disposiciones", length = 2000)
public String getDisposiciones() {
  return this.disposiciones;
}

public void setDisposiciones(String disposiciones) {
  this.disposiciones = disposiciones;
}

@Column(name = "requisitos", length = 2000)
public String getRequisitos() {
  return this.requisitos;
}

public void setRequisitos(String requisitos) {
  this.requisitos = requisitos;
}

@Column(name = "bases", length = 2000)
public String getBases() {
  return this.bases;
}

public void setBases(String bases) {
  this.bases = bases;
}

@Column(name = "bases_url", length = 200)
public String getBasesUrl() {
  return this.basesUrl;
}

public void setBasesUrl(String basesUrl) {
  this.basesUrl = basesUrl;
}

@Column(name = "formulario_inscripcion", length = 200)
public String getFormularioInscripcion() {
  return this.formularioInscripcion;
}

public void setFormularioInscripcion(String formularioInscripcion) {
  this.formularioInscripcion = formularioInscripcion;
}

@Column(name = "pruebas", length = 2000)
public String getPruebas() {
  return this.pruebas;
}

public void setPruebas(String pruebas) {
  this.pruebas = pruebas;
}

@Column(name = "grupo_profesional", length = 200)
public String getGrupoProfesional() {
  return this.grupoProfesional;
}

public void setGrupoProfesional(String grupoProfesional) {
  this.grupoProfesional = grupoProfesional;
}

@Column(name = "empleado_publico", length = 200)
public String getEmpleadoPublico() {
  return this.empleadoPublico;
}

public void setEmpleadoPublico(String empleadoPublico) {
  this.empleadoPublico = empleadoPublico;
}

@Column(name = "cuerpo", length = 200)
public String getCuerpo() {
  return this.cuerpo;
}

public void setCuerpo(String cuerpo) {
  this.cuerpo = cuerpo;
}

@Column(name = "modalidad", length = 200)
public String getModalidad() {
  return this.modalidad;
}

public void setModalidad(String modalidad) {
  this.modalidad = modalidad;
}



@Override
public String toString() {
  return "ConvocatoriaPublica [ikey=" + ikey + ", id=" + id + ", title=" + title + ", description=" + description + ", datePublished=" + datePublished + ", fechaAprobacion=" + fechaAprobacion + ", fechaResolucion=" + fechaResolucion
	  + ", estadoPlazo=" + estadoPlazo + ", plazos=" + plazos + ", numeroPlazasConvocadas=" + numeroPlazasConvocadas + ", listaEsperaEx=" + listaEsperaEx + ", observaciones=" + observaciones + ", disposiciones=" + disposiciones + ", requisitos="
	  + requisitos + ", bases=" + bases + ", basesUrl=" + basesUrl + ", formularioInscripcion=" + formularioInscripcion + ", pruebas=" + pruebas + ", grupoProfesional=" + grupoProfesional + ", empleadoPublico=" + empleadoPublico
	  + ", cuerpo=" + cuerpo + ", modalidad=" + modalidad + "]";
}


@Override
public Map<String, String> prefixes() {
  Map<String, String> prefixes = new HashMap<String, String>();
  prefixes.put(Context.XSD, Context.XSD_URI);
  prefixes.put(Context.DCT, Context.DCT_URI);
  prefixes.put(Context.ESADM, Context.ESADM_URI);
  prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);  
  prefixes.put(Context.ELI, Context.ELI_URI);
  prefixes.put(Context.ESEMPLEO, Context.ESEMPLEO_URI);
  return prefixes;
}

@SuppressWarnings("unchecked")
@Override
public <T> T cloneModel(T copia, List<String> listado) {

  return (T) cloneClass((ConvocatoriaEmpleoPublico) copia, listado);
}

// Constructor copia con lista de attributos a settear
public ConvocatoriaEmpleoPublico cloneClass(ConvocatoriaEmpleoPublico lc, List<String> attributesToSet) {

  ConvocatoriaEmpleoPublico obj = new ConvocatoriaEmpleoPublico(lc, attributesToSet);

  return obj;

}

// TODO valiaciones de equipamientos ver si es posible realizar mediante
// anotaciones en model el saber los campos obligatorios
public List<String> validate() {
  List<String> result = new ArrayList<String>();

  // Validamos campos Obligatorios ver si es posible obtener esta información
  // mediante anotaciones del modelo
  if (!Util.validValue(this.getId())) {
	result.add("id is not valid [Id:" + this.getId() + "]");
  }

  if (!Util.validValue(this.getTitle())) {
	result.add("title is not valid [Title:" + this.getTitle() + "]");
  }

  

  return result;
}


}
