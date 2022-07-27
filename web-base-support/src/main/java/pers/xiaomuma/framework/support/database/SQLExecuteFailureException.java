package pers.xiaomuma.framework.support.database;


import org.springframework.dao.DataAccessException;

public class SQLExecuteFailureException extends DataAccessException {

	public SQLExecuteFailureException(String msg) {
		super(msg);
	}

	public SQLExecuteFailureException(String msg, Throwable throwable) {
		super(msg, throwable);
	}


}
