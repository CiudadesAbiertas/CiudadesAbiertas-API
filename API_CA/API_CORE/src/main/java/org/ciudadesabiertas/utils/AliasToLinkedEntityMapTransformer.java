package org.ciudadesabiertas.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
/* Con esta clase conseguimos que los campos de las consultas se devuelvan en el 
 * orden que se ha especificado en la Query
 */
public class AliasToLinkedEntityMapTransformer extends AliasedTupleSubsetResultTransformer {

	private static final long serialVersionUID = -6612407859165667135L;

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String, Object> result = new LinkedHashMap<>(tuple.length);

		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i];

			if (alias != null) {
				result.put(alias, tuple[i]);
			}else {
			  result.put(i+"", tuple[i]);
			}
		}
		return result;
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {

		return false;
	}
}
