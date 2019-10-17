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

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.internal.CriteriaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Con esta clase conseguimos ignorar acentos, y mayusculas y minusculas cuando lanzamos
 * busquedas a traves de criteria en hibernate
 * */
/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class LikeNoAccents implements Criterion {
    private static final long serialVersionUID = 1L;
    
    private static final Logger log = LoggerFactory.getLogger(LikeNoAccents.class);

    private final String propertyName;
    private final Object value;
    private final String database;

    public LikeNoAccents(String propertyName, Object value, String databaseType)
	{
        this.propertyName = propertyName;
        this.value = value;
    	this.database = databaseType;
	}

	@Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        //String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
        
		String className=((CriteriaImpl) criteria).getEntityOrClassName();
		
		String columnName=Util.javaNameToTableField(propertyName);
		if ((StartVariables.mapaClasesColumnas.get(className))!=null)
		{
			String tempColumnName=StartVariables.mapaClasesColumnas.get(className).get(propertyName);
			if (Util.validValue(tempColumnName))
				columnName=tempColumnName;
		}       
        
        String theSQLString="";
        /*
        if(columns.length != 1) {
            throw new HibernateException("ilike may only be used with single-column properties");
        }*/

        if (database.equals(Constants.ORACLE))
        {
        	//theSQLString= "TRANSLATE(UPPER("+ columns[0] +"),'ÂÁÀÄÃÊÉÈËÎÍÌÏÔÓÒÖÕÛÚÙÜÑ', 'AAAAAEEEEIIIIOOOOOUUUUN') like ?";
        	theSQLString= DifferentSQLforDatabases.TRANSLATE_ORACLE + columnName + DifferentSQLforDatabases.TRANSLATE_END+" like ?";
        		
        }
        else if (database.equals(Constants.SQLSERVER))
        {	//TEXT Description
        	//theSQLString= "dbo.TRANSLATE(UPPER(convert(varchar(5000),"+ columns[0] +")),'ÂÁÀÄÃÊÉÈËÎÍÌÏÔÓÒÖÕÛÚÙÜÑ', 'AAAAAEEEEIIIIOOOOOUUUUN') like ?";
        	//VARCHAR DESCRIPTION
        	theSQLString= DifferentSQLforDatabases.TRANSLATE_SQLSERVER+ columnName + DifferentSQLforDatabases.TRANSLATE_END+" like ?";
        }
        else if (database.equals(Constants.MYSQL))
        {
        	theSQLString= columnName +" like ?";
        }
        
        
        
        return theSQLString;
    }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return new TypedValue[] { criteriaQuery.getTypedValue( criteria, propertyName, Util.stripAccents(value.toString().toUpperCase())) };
    }

}