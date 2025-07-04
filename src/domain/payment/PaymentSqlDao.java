/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.payment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import util.DateUtil;

/**
 * DB SQL implementation of Payment Data Object interface<br>
 * 
 */
public class PaymentSqlDao implements PaymentDao {

	private static final String ID = "sa";

	private static final String PASSWORD = "";

	private static final String DRIVER_NAME = "org.hsqldb.jdbcDriver";

	private static final String URL = "jdbc:hsqldb:hsql://localhost;shutdown=true";

	private static final String TABLE_NAME = "PAYMENT";

	/**
	 * @see domain.payment.PaymentDao#getPayment(java.util.Date, java.lang.String)
	 */
	public Payment getPayment(Date stayingDate, String roomNumber) throws PaymentException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		Payment payment = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT roomnumber, stayingdate, amount, status FROM ");
			sql.append(TABLE_NAME);
			sql.append(" WHERE ROOMNUMBER= '");
			sql.append(roomNumber);
			sql.append("' AND stayingdate='");
			sql.append(DateUtil.convertToString(stayingDate));
			sql.append("';");
			resultSet = statement.executeQuery(sql.toString());
			if (resultSet.next() == true) {
				payment = new Payment();
				payment.setRoomNumber(roomNumber);
				payment.setStayingDate(stayingDate);
				payment.setAmount(Integer.parseInt(resultSet.getString("amount")));
				payment.setStatus(resultSet.getString("status"));
			}
		}
		catch (SQLException e) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getPayment()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
		return payment;
	}

	/**
	 * @see domain.payment.PaymentDao#updatePayment(domain.payment.Payment)
	 */
	public void updatePayment(Payment payment) throws PaymentException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("UPDATE ");
			sql.append(TABLE_NAME);
			sql.append(" SET status = '");
			sql.append(payment.getStatus());
			sql.append("' WHERE roomnumber='");
			sql.append(payment.getRoomNumber());
			sql.append("';");

			resultSet = statement.executeQuery(sql.toString());
		}
		catch (SQLException e) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("updatePayment()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
	}

	/**
	 * @see domain.payment.PaymentDao#createPayment(domain.payment.Payment)
	 */
	public void createPayment(Payment payment) throws PaymentException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("INSERT INTO ");
			sql.append(TABLE_NAME);
			sql.append(" (roomnumber, stayingDate, amount, status) values ('");
			sql.append(payment.getRoomNumber());
			sql.append("', '");
			sql.append(DateUtil.convertToString(payment.getStayingDate()));
			sql.append("', '");
			sql.append(payment.getAmount());
			sql.append("', '");
			sql.append(payment.getStatus());
			sql.append("');");

			resultSet = statement.executeQuery(sql.toString());
		}
		catch (SQLException e) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("createPayment()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
	}

	/**
	 * データベースコネクションを取得します。<br>
	 * 
	 * @return コネクション
	 * @throws PaymentException
	 *            データベースコネクション取得が失敗した場合に発生します。
	 */
	private Connection getConnection() throws PaymentException {
		Connection connection = null;
		try {
			Class.forName(DRIVER_NAME);
			connection = DriverManager.getConnection(URL, ID, PASSWORD);
		}
		catch (Exception e) {
			throw new PaymentException(PaymentException.CODE_DB_CONNECT_ERROR, e);
		}
		return connection;
	}

	private void close(ResultSet resultSet, Statement statement, Connection connection)
			throws PaymentException {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		catch (SQLException e) {
			throw new PaymentException(PaymentException.CODE_DB_CLOSE_ERROR, e);
		}
	}
}
