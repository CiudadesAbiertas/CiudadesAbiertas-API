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

package org.ciudadesabiertas.service;




import org.ciudadesabiertas.dao.EstadisticaDao;
import org.ciudadesabiertas.model.Estadistica;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.StartVariables;
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
@Service("EstadisticaService")
public class EstadisticaService {

	private static final Logger log = LoggerFactory.getLogger(EstadisticaService.class);

	@Autowired
	private EstadisticaDao estadisticasDao;
	
	@Transactional
	public void add(Estadistica Estadistica) {
		estadisticasDao.add(Estadistica);
		log.info("estadistica added");
	}
	
	
	
	@Transactional
	public boolean controlRequesPerSecondUser(String user) {
		boolean result=true;
		long maximo = 0;
		String msg="";
		try 
		{
			if (user!=null && !"".equals(user) && !Constants.ANONYMOUS_USER.equals(user)) {
				maximo = StartVariables.MAX_NUMBER_REQUEST_PER_SECOND_INDENTIFIED;
			}else {
				maximo = StartVariables.MAX_NUMBER_REQUEST_PER_SECOND_ANONYMOUS;
			}
			long maxBBDD= estadisticasDao.maxRequestPerSecondUser_with_JPA_Criteria(user);
			msg="[User: "+ user + "] [max:"+maximo +"] << [petitions in last second:"+maxBBDD + "]";
			log.info("[controlRequesPerSecondUser] "+msg);
			result = maximo>maxBBDD;
			
		}catch (Exception e) {
			log.error("Error in the control de max request per day: "+ e.getMessage());
			result=false;
		}
		if (result==false)
		{
			log.info("\t"+msg);
			log.info("estadistica control request second [result:"+result+"]");
		}
				
		return result;
	}
	
	
	
}