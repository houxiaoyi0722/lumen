package com.sang.domain.repo;

import io.ebean.Database;
import io.ebean.EbeanServer;
import io.ebean.Finder;

public abstract class BeanFinder<I,T> extends Finder<I,T> {

	protected final Database db;

	public BeanFinder(Class<T> type, Database db) {
		super(type);
		this.db = db;
	}

	@Override
	public Database db() {
		return db;
	}
}
