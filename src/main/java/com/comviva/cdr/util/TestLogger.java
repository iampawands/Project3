
package com.comviva.cdr.util;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * This class is used for logging operations.
 *
 * @author siddavarapu.surekha
 * @version 1.0
 * @date Nov 30, 2010
 */

public class TestLogger {

	/** The logger. */
	private Logger logger;

	/** The Constant SPACE. */

	private static final String SPACE = " ";
	private static String[] testMsisdn;

	/**
	 * Constructor to get the Logger instance.
	 * 
	 * @param className
	 *            the name of the logger to retrieve. If the named logger
	 *            already exists, then the existing instance will be returned.
	 */
	public TestLogger(final Class<?> className) {
		logger = LoggerFactory.getLogger(className);

	}

	/**
	 * Instantiates a new kokila logger.
	 *
	 * @param aStrVal
	 *            the a str val
	 */
	public TestLogger(String aStrVal) {
		logger = LoggerFactory.getLogger(aStrVal);
	}

	/**
	 * This method is used to reload the logger properties.
	 */
	public void reloadLogBackupConfiguration() throws JoranException {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		ContextInitializer ci = new ContextInitializer(loggerContext);
		URL url = ci.findURLOfDefaultConfigurationFile(true);
		loggerContext.reset();
		ci.configureByResource(url);
	}

	/**
	 * Method to load test Msisdn
	 */
	public void loadTestMsisdn(String[] msisdn) {
		testMsisdn = msisdn;
	}

	/**
	 * Method to check msisdn belongs to test MSISDN
	 */

	private boolean checkMsisdn(String msisdn) {
		for (int i = 0; i < testMsisdn.length; i++)
			if (msisdn.equals(testMsisdn[i]))
				return true;
		return false;
	}

	/**
	 * Checks if is debug enabled.
	 *
	 * @return If Debug is enabled
	 */
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/**
	 * Checks if is info enabled.
	 *
	 * @return If Info is enabled
	 */
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/**
	 * This method logs the statement with the DEBUG level (It designates
	 * fine-grained informational events that are most useful to debug an
	 * application). If this category is DEBUG enabled, then it proceeds to call
	 * all the registered appenders in this category and also higher in the
	 * hierarchy depending on the value of the additivity flag.
	 * 
	 * @param statement
	 *            the statement to be logged
	 */
	public void debug(final String statement) {

		if (!isDebugEnabled() && MDC.get("msisdn") != null && checkMsisdn(MDC.get("msisdn")))
			logger.info(statement);
		else
			logger.debug(statement);

	}

	/**
	 * Debug.
	 *
	 * @param statement
	 *            the statement
	 * @param e
	 *            the e
	 */
	public void debug(final String statement, final Exception e) {
		if (!isDebugEnabled() && MDC.get("msisdn") != null && checkMsisdn(MDC.get("msisdn")))
			logger.info(append(statement, e));
		else
			logger.debug(append(statement, e));
	}

	/**
	 * This method logs the statement with the INFO level (It designates
	 * informational messages that highlight the progress of the application at
	 * coarse-grained level). If this category is INFO enabled, then it proceeds
	 * to call all the registered appenders in this category and also higher in
	 * the hierarchy depending on the value of the additivity flag.
	 * 
	 * @param statement
	 *            the statement to be logged
	 */
	public void info(final String statement) {
		logger.info(statement);
	}

	/**
	 * Info.
	 *
	 * @param statement
	 *            the statement
	 * @param e
	 *            the e
	 */
	public void info(final String statement, final Exception e) {
		logger.info(append(statement, e));
	}

	/**
	 * This method logs the statement with the WARN level (It designates
	 * potentially harmful situations).
	 * 
	 * @param statement
	 *            the statement to be logged
	 */
	public void warn(final String statement) {
		logger.warn(statement);
	}

	/**
	 * Warn.
	 *
	 * @param statement
	 *            the statement
	 * @param e
	 *            the e
	 */
	public void warn(final String statement, final Exception e) {
		logger.warn(append(statement, e));
	}

	/**
	 * This method logs the statement with the ERROR level (It designates error
	 * events that might still allow the application to continue running).
	 * 
	 * @param statement
	 *            the statement to be logged
	 */
	public void error(final String statement) {
		logger.error(statement);
	}

	/**
	 * Error.
	 *
	 * @param statement
	 *            the statement
	 * @param e
	 *            the e
	 */
	public void error(final String statement, final Exception e) {
		logger.error(append(statement, e));
	}

	/**
	 * This method logs the statement with the FATAL level (It designates very
	 * severe error events that will presumably lead the application to abort).
	 * 
	 * @param statement
	 *            the statement to be logged
	 */
	public void fatal(final String statement) {
		logger.error(statement);
	}

	/**
	 * Fatal.
	 *
	 * @param statement
	 *            the statement
	 * @param e
	 *            the e
	 */
	public void fatal(final String statement, final Exception e) {
		logger.error(append(statement, e));
	}

	/**
	 * Append.
	 *
	 * @param statement
	 *            the statement
	 * @param e
	 *            the e
	 * @return the string
	 */
	private String append(final String statement, final Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(statement);
		sb.append(SPACE);
		sb.append(getExceptionDetails(e));
		return sb.toString();
	}

	/**
	 * Gets the exception details.
	 *
	 * @param e
	 *            the e
	 * @return the exception details
	 */
	private String getExceptionDetails(Exception e) {
		StringBuilder st = new StringBuilder();
		if (e != null) {
			StackTraceElement[] traces = e.getStackTrace();
			int len = traces.length;
			if (traces != null && len > 0) {
				if (len > 3 && logger.isInfoEnabled()) {
					len = 3;
				}
				st.append("EXP: " + e + ": " + e.getMessage());
				for (int i = 0; i < len; i++) {
					StackTraceElement ste = traces[i];
					if (ste != null) {
						String s = ste.getClassName();
						if (s != null) {
							st.append("[Class: " + s + "]");
						}

						s = ste.getMethodName();
						if (s != null) {
							st.append("[Method: " + s + "]");
						}
						st.append("[Line: " + ste.getLineNumber() + "]");
					}
				}
			}
		}
		return st.toString();
	}

	/**
	 * Returns the query string given a prepared statement when c3p0 is used.
	 *
	 * @param prepStmt
	 *            DB prepared statement
	 * @return DB query string
	 */
/*	public String getQuery(java.sql.PreparedStatement prepStmt) {
		try {
			if (!isDebugEnabled() && MDC.get("msisdn") != null && checkMsisdn(MDC.get("msisdn")))
				logger.info("Getting query for the prepStmt");
			else
				logger.debug("Getting query for the prepStmt");
			C3P0ProxyStatement c3p0Stmt = (C3P0ProxyStatement) prepStmt;
			Method toStringMethod = Object.class.getMethod(KokilaConstants.toString, new Class[] {});
			Object toStr = c3p0Stmt.rawStatementOperation(toStringMethod, C3P0ProxyStatement.RAW_STATEMENT,
					new Object[] {});
			if (toStr instanceof String) {
				String sql = (String) toStr;
				sql = sql.substring(sql.indexOf(KokilaConstants.HYPHEN) + 1).trim() + ";";
				return sql;
			}
			return null;
		} catch (Exception e) {
			logger.error("Failed to get Query logs for prepStmt " + e);
			return null;
		}
	}   */
}
