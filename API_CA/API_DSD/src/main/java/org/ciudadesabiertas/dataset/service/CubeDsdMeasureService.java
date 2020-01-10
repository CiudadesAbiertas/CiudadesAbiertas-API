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

package org.ciudadesabiertas.dataset.service;





import java.util.List;

import org.ciudadesabiertas.dataset.dao.CubeDsdMeasureDao;
import org.ciudadesabiertas.dataset.model.CubeDsd;
import org.ciudadesabiertas.dataset.model.CubeDsdMeasure;
import org.ciudadesabiertas.service.DatasetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 

 
	
/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Service("cubeDsdMeasureService")
@Transactional
public class CubeDsdMeasureService extends DatasetService<CubeDsdMeasure> {

	@Autowired
	private CubeDsdMeasureDao cubeDsdMeasureDao;	
	
	private static final Logger log = LoggerFactory.getLogger(CubeDsdMeasureService.class);
	
	
	public List<CubeDsdMeasure> findByCubeDsd(String key, CubeDsd cubeDsd) {
    	log.debug("[findById][cubeDsd:"+cubeDsd+"]");
        return cubeDsdMeasureDao.findByCubeDsd(key, cubeDsd);
    }
	
	public long rowCountByCubeDsd(String key, CubeDsd cubeDsd) {
    	log.debug("[rowCountByCubeDsd][cubeDsd:"+cubeDsd+"]");
        return cubeDsdMeasureDao.rowCountByCubeDsd(key, cubeDsd);
    }

	public CubeDsdMeasure findByCubeDsdAndMeasureId(String key, CubeDsd cubeDsd, String id) {
		log.debug("[findByCubeDsdAndDimensionId][cubeDsd:"+cubeDsd+"][measureId:"+id+"]");
        return cubeDsdMeasureDao.findByCubeDsdAndMeasureId(key, cubeDsd,id);
	}

	public List<CubeDsd> findCubeDsdByMeasure(String key, CubeDsdMeasure measure) {
		log.debug("[findCubeDsdByMeasure][measureId:"+measure+"]");
        return cubeDsdMeasureDao.findCubeDsdByMeasure(key, measure);
	}

	public long rowCountCubeDsdByMeasure(String key, CubeDsdMeasure measure) {
		log.debug("[rowCountCubeDsdByMeasure][measureId:"+measure+"]");
		return cubeDsdMeasureDao.rowCountCubeDsdByMeasure(key, measure);
	}
	
	

}