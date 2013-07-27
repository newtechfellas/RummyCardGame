package com.webi.ent.util;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

/**
 * Utility class to provide access to session factory and datasource
 * @author Suman Jakkula
 *
 */
public class DAOHelper {

    private SessionFactory sessionFactory;
	
	private DataSource datasource;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
}
