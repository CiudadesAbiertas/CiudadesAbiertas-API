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

package org.ciudadesabiertas.factory.tomcat;



import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.ciudadesabiertas.utils.Util;
 


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class EncryptedDataSourceFactory extends BasicDataSourceFactory {

	
	@SuppressWarnings("rawtypes")
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
		if (obj instanceof Reference) {
			setUsername((Reference) obj);
			setPassword((Reference) obj);
		}
		return super.getObjectInstance(obj, name, nameCtx, environment);
	}
	
	private void setUsername(Reference ref) throws Exception {
		findDecryptAndReplace("username", ref);
	}

	private void setPassword(Reference ref) throws Exception {
		findDecryptAndReplace("password", ref);
	}

	private void findDecryptAndReplace(String refType, Reference ref) throws Exception {
		int idx = find(refType, ref);
		String decrypted = decrypt(idx, ref);
		replace(idx, refType, decrypted, ref);
	}
	
	private void replace(int idx, String refType, String newValue, Reference ref) throws Exception {
		ref.remove(idx);
		ref.add(idx, new StringRefAddr(refType, newValue));
	}

	private String decrypt(int idx, Reference ref) throws Exception {
		return Util.checkAndSetEncodedProperty(ref.get(idx).getContent().toString());
	}

	@SuppressWarnings("rawtypes")
	private int find(String addrType, Reference ref) throws Exception {
		Enumeration enu = ref.getAll();
		for (int i = 0; enu.hasMoreElements(); i++) {
			RefAddr addr = (RefAddr) enu.nextElement();
			if (addr.getType().compareTo(addrType) == 0)
				return i;
		}

		throw new Exception("The \"" + addrType + "\" name/value pair was not found"
				+ " in the Reference object.  The reference Object is" + " " + ref.toString());
	}
	
}

