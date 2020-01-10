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

import org.ciudadesabiertas.dataset.dao.CubeDsdDimensionValueDao;
import org.ciudadesabiertas.dataset.model.CubeDsdDimension;
import org.ciudadesabiertas.dataset.model.CubeDsdDimensionValue;
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
@Service("cubeDsdDimensionValueService")
@Transactional
public class CubeDsdDimensionValueService extends DatasetService<CubeDsdDimensionValue> {

	@Autowired
	private CubeDsdDimensionValueDao cubeDsdDimensionValueDao;	
	
	private static final Logger log = LoggerFactory.getLogger(CubeDsdDimensionValueService.class);
	
	
	public List<CubeDsdDimensionValue> findByDimension(String key, CubeDsdDimension cubeDsdDimension) {
    	log.debug("[findByDimension][cubeDsdDimension:"+cubeDsdDimension+"]");
        return cubeDsdDimensionValueDao.findByDimension(key, cubeDsdDimension);
    }


	public long rowCountByDimension(String key, CubeDsdDimension cubeDsdDimension) {
		log.debug("[rowCountByDimension][cubeDsdDimension:"+cubeDsdDimension+"]");
        return cubeDsdDimensionValueDao.rowCountByDimension(key, cubeDsdDimension);
	}
	
	
	public List<CubeDsdDimensionValue>  findByDimensionAndId(String key, CubeDsdDimension cubeDsdDimension, String id) {
    	log.debug("[findByDimension][cubeDsdDimension:"+cubeDsdDimension+"]");
        return cubeDsdDimensionValueDao.findByDimensionAndId(key, cubeDsdDimension, id);
    }

	
	
	

}