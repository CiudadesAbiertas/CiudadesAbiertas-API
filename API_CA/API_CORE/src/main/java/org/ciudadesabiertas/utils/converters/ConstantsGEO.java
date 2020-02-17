/**
 * 
 */
package org.ciudadesabiertas.utils.converters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class ConstantsGEO {
	
	public static List <String> ignoreFields=new ArrayList<String>();
	
	static
	{
		ignoreFields.add("ikey");
		ignoreFields.add("class");
		ignoreFields.add("distance");
		ignoreFields.add("x");
		ignoreFields.add("y");
		ignoreFields.add("latitud");
		ignoreFields.add("longitud");
		ignoreFields.add("hasgeometry");		
	}

}
