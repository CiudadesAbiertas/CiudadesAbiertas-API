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

package org.ciudadesabiertas.dataset.utils;

import java.io.File;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class TerritorioConstants
{
	public static final String KEY="territorio";
	
	public static final String seccionCensalFilePath = ".."+File.separator+"territorio"+File.separator+"seccionCensal";
	public static final String paisFilePath = ".."+File.separator+"territorio"+File.separator+"pais";
	public static final String autonomiaFilePath = ".."+File.separator+"territorio"+File.separator+"autonomia";
	public static final String provinciaFilePath = ".."+File.separator+"territorio"+File.separator+"provincia";
	public static final String municipioFilePath = ".."+File.separator+"territorio"+File.separator+"municipio";
	public static final String distritoFilePath = ".."+File.separator+"territorio"+File.separator+"distrito";
	public static final String barrioFilePath = ".."+File.separator+"territorio"+File.separator+"barrio";
	
	
	//CTES PARA MAPEAR LAS CONSULTAS RSQL
	public static final String FIELD_PAIS="pais";
	public static final String FIELD_PAIS_RSQL="paisObject.identifier";
	public static final String FIELD_AUTONOMIA="autonomia";
	public static final String FIELD_AUTONOMIA_RSQL="autonomiaObject.identifier";
	public static final String FIELD_PROVINCIA="provincia";
	public static final String FIELD_PROVINCIA_RSQL="provinciaObject.identifier";
	public static final String FIELD_MUNICIPIO="municipioId";
	public static final String FIELD_MUNICIPIO_RSQL="municipioObject.id";
	public static final String FIELD_DISTRITO="distritoId";
	public static final String FIELD_DISTRITO_RSQL="distritoObject.id";
	
	public static final String seccionCensalVocabURL="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#SeccionCensal";
	public static final String barrioVocabURL="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#Barrio";
	public static final String distritoVocabURL="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#Distrito";
	public static final String municipioVocabURL="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#Municipio";
	public static final String provinciaVocabURL="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#Provincia";
	public static final String autonomiaVocabURL="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#Autonomia";
	public static final String paisVocabURL="http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#Pais";
	
}
