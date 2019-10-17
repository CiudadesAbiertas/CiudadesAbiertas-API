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

package org.ciudadesabiertas.utils;


import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class EncriptStringProperties {

	private static final Logger log = LoggerFactory.getLogger(Util.class);
	
	public static String textEncryptor(String texto) {
		String result = "";
		try {			
			
			BasicTextEncryptor textEncryptor = new BasicTextEncryptor();												
			textEncryptor.setPassword(Constants.SEMILLA_PROPERTIES);
			
			result = textEncryptor.encrypt(texto);			
		} catch (Exception e) {
			System.out.println("Error encripting argument");
			e.printStackTrace();		
		}
		return result;
	}
	
	public static String textDesEncryptor(String textoEncriptado) {
		String result = "";
		try {			
			
			BasicTextEncryptor textEncryptor = new BasicTextEncryptor();												
			textEncryptor.setPassword(Constants.SEMILLA_PROPERTIES);
			
			result = textEncryptor.decrypt(textoEncriptado);			
		} catch (Exception e) {
			System.out.println("Error encripting argument");
			e.printStackTrace();		
		}
		return result;
	}

	public static void main(String[] args) {

		
		
		System.out.println("This will encript all arguments");
		System.out.println("Now we have "+args.length+ " arguments");
		
		
		try {			
		
			BasicTextEncryptor textEncryptor = new BasicTextEncryptor();												
			textEncryptor.setPassword(Constants.SEMILLA_PROPERTIES);
			
			for (String a:args)
			{
				System.out.println("");
				String myEncryptedText = textEncryptor.encrypt(a);			
				String plainText = textEncryptor.decrypt(myEncryptedText);
				
				System.out.println("Encripted:\t\t"+myEncryptedText);
				System.out.println("Desencripted:\t"+plainText);
				System.out.println("For use in properties: "+Constants.PREFIX_PROPERTY_ENCODED+myEncryptedText+Constants.SUFFIX_PROPERTY_ENCODED);
				
				
				
			}
							
			
			
			
		} catch (Exception e) {
			System.out.println("Error encripting argument");
			e.printStackTrace();
		
		}

	}

}
