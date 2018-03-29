package ru.fx.develop.renovation.dao.impl;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

//@Repository
public class GenericRealDao extends GenericDao {

    public Query getQuery(String sql){
        return getSession().createQuery(sql);
    }

    public NativeQuery createSQLQuery(String stringQuery) {
        return getSession().createNativeQuery(stringQuery);
    }

    public Query createQuery(CriteriaQuery criteriaQuery) {
        return getSession().createQuery(criteriaQuery);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return getSession().getCriteriaBuilder();
    }
}
