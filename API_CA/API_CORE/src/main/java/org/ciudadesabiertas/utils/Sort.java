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

import java.io.Serializable;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class Sort implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String property;
	protected boolean desc = false;
	protected boolean ignoreCase = false;

	public Sort() {

	}

	public Sort(String property, boolean desc, boolean ignoreCase) {
		this.property = property;
		this.desc = desc;
		this.ignoreCase = ignoreCase;
	}

	public Sort(String property, boolean desc) {
		this.property = property;
		this.desc = desc;
	}

	public Sort(String property) {
		this.property = property;
	}

	public static Sort asc(String property) {
		return new Sort(property);
	}

	public static Sort asc(String property, boolean ignoreCase) {
		return new Sort(property, ignoreCase);
	}

	public static Sort desc(String property) {
		return new Sort(property, true);
	}

	public static Sort desc(String property, boolean ignoreCase) {
		return new Sort(property, true, ignoreCase);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + (desc ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sort other = (Sort) obj;
		if (desc != other.desc)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (property == null) {
			sb.append("null");
		} else {
			sb.append("`");
			sb.append(property);
			sb.append("`");
		}
		sb.append(desc ? " desc" : " asc");
		if (ignoreCase) {
			sb.append(" (ignore case)");
		}
		return sb.toString();
	}
}
