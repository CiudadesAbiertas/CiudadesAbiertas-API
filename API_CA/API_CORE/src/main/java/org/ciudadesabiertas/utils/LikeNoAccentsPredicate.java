package org.ciudadesabiertas.utils;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;

/*
 * Con esta clase conseguimos ignorar acentos, y mayusculas y minusculas cuando lanzamos
 * busquedas a traves de criteria en hibernate
 * */
public class LikeNoAccentsPredicate implements Predicate {
    private static final long serialVersionUID = 1L;

    private final String propertyName;
    private final Object value;
    private final String database;

    public LikeNoAccentsPredicate(String propertyName, Object value, String databaseType)
	{
        this.propertyName = propertyName;
        this.value = value;
    	this.database = databaseType;
	}

	@Override
	public Predicate isNull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate isNotNull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Expression<?>... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Collection<?> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate in(Expression<Collection<?>> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X> Expression<X> as(Class<X> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Selection<Boolean> alias(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCompoundSelection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Selection<?>> getCompoundSelectionItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Boolean> getJavaType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanOperator getOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNegated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Expression<Boolean>> getExpressions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Predicate not() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
//        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
//
//        String theSQLString="";
//        if(columns.length != 1) {
//            throw new HibernateException("ilike may only be used with single-column properties");
//        }
//
//        if (database.equals(Constants.ORACLE)||(database.equals(Constants.SQLSERVER)))
//        {
//        	theSQLString= "TRANSLATE(UPPER("+ columns[0] +"),'ÂÁÀÄÃÊÉÈËÎÍÌÏÔÓÒÖÕÛÚÙÜ', 'AAAAAEEEEIIIIOOOOOUUUU') like ?";	
//        }
//        else if (database.equals(Constants.MYSQL))
//        {
//        	theSQLString= columns[0] +" like ?";
//        }
//        
//        
//        
//        return theSQLString;
//    }
//
//    @Override
//    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
//        return new TypedValue[] { criteriaQuery.getTypedValue( criteria, propertyName, Util.stripAccents(value.toString().toUpperCase())) };
//    }

}