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

package org.ciudadesabiertas.dataset.util;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class CalidadAireConstants
{
	public static final String KEY="calidadAire";
	
	public static final String sensorIdPath="/calidad-aire/estacion/{isHostedBy}/sensor/{observesId}";
	
	public static final String airQualityStationVocabURL="http://vocab.linkeddata.es/datosabiertos/def/medio-ambiente/calidad-aire/index-en.html#AirQualityStation";
	
	public static final String airQualityObservationVocabURL="http://vocab.linkeddata.es/datosabiertos/def/medio-ambiente/calidad-aire/index-en.html#AirQualityObservation";
	
	public static final String airQualitySensorVocabURL="http://vocab.linkeddata.es/datosabiertos/def/medio-ambiente/calidad-aire/index-en.html#AirQualitySensor";
	
}
