/*
 * Copyright(C) 2007 National Institute of Informatics, All rights reserved.
 */
package domain.room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import util.DateUtil;

/**
 * DB SQL implementation of Available Qty Data Object interface<br>
 * 
 */
public class AvailableQtySqlDao implements AvailableQtyDao {

	private static final String ID = "sa";

	private static final String PASSWORD = "";

	private static final String DRIVER_NAME = "org.hsqldb.jdbcDriver";

	private static final String URL = "jdbc:hsqldb:hsql://localhost;shutdown=true";

	private static final String TABLE_NAME = "AVAILABLEQTY";

	/**
	 * @see domain.room.AvailableQtyDao#getAvailableQty(java.util.Date)
	 */
	public AvailableQty getAvailableQty(Date date) throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		AvailableQty availableQty = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT QTY FROM ");
			sql.append(TABLE_NAME);
			sql.append(" WHERE DATE='");
			sql.append(DateUtil.convertToString(date));
			sql.append("';");

			resultSet = statement.executeQuery(sql.toString());
			if (resultSet.next() == true) {
				availableQty = new AvailableQty();
				availableQty.setDate(date);
				availableQty.setQty(Integer.parseInt(resultSet.getString("qty")));
			}
			else {
				return null;
			}
		}
		catch (SQLException e) {
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getAvailableQty()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
		return availableQty;
	}

	/**
	 * @see domain.room.AvailableQtyDao#updateAvailableQty(domain.room.AvailableQty)
	 */
	public void updateAvailableQty(AvailableQty availableQty) throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("UPDATE ");
			sql.append(TABLE_NAME);
			sql.append(" SET QTY='");
			sql.append(availableQty.getQty());
			sql.append("' WHERE DATE='");
			sql.append(DateUtil.convertToString(availableQty.getDate()));
			sql.append("';");

			resultSet = statement.executeQuery(sql.toString());
		}
		catch (SQLException e) {
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("updateAvailableQty()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
	}

	/**
	 * @see domain.room.AvailableQtyDao#createAbailableQty(domain.room.AvailableQty)
	 */
	public void createAbailableQty(AvailableQty availableQty) throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("INSERT INTO ");
			sql.append(TABLE_NAME);
			sql.append(" (date, qty) ");
			sql.append("values ('");
			sql.append(DateUtil.convertToString(availableQty.getDate()));
			sql.append("', '");
			sql.append(availableQty.getQty());
			sql.append("');");

			resultSet = statement.executeQuery(sql.toString());
		}
		catch (SQLException e) {
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("createAbailableQty()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
	}

	private Connection getConnection() throws RoomException {
		Connection connection = null;
		try {
			Class.forName(DRIVER_NAME);
			connection = DriverManager.getConnection(URL, ID, PASSWORD);
		}
		catch (Exception e) {
			throw new RoomException(RoomException.CODE_DB_CONNECT_ERROR, e);
		}
		return connection;
	}

	private void close(ResultSet resultSet, Statement statement, Connection connection)
			throws RoomException {
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
			throw new RoomException(RoomException.CODE_DB_CLOSE_ERROR, e);
		}
	}
}
