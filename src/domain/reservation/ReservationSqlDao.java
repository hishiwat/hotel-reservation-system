/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DateUtil;

/**
 * DB SQL implementation of Reservation Data Object interface<br>
 * 
 */
public class ReservationSqlDao implements ReservationDao {

	private static final String ID = "sa";

	private static final String PASSWORD = "";

	private static final String DRIVER_NAME = "org.hsqldb.jdbcDriver";

	private static final String URL = "jdbc:hsqldb:hsql://localhost;shutdown=true";

	private static final String TABLE_NAME = "RESERVATION";

	/**
	 * @see domain.reservation.ReservationDao#getReservation(java.lang.String)
	 */
	public Reservation getReservation(String reservationNumber) throws ReservationException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		Reservation reservation = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT reservationnumber, stayingdate, status FROM ");
			sql.append(TABLE_NAME);
			sql.append(" WHERE RESERVATIONNUMBER= '");
			sql.append(reservationNumber);
			sql.append("';");
			resultSet = statement.executeQuery(sql.toString());
			if (resultSet.next() == true) {
				reservation = new Reservation();
				reservation.setReservationNumber(reservationNumber);
				reservation.setStatus(resultSet.getString("status"));
				reservation.setStayingDate(DateUtil.convertToDate(resultSet
						.getString("stayingDate")));
			}
		}
		catch (SQLException e) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getReservation()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
		return reservation;
	}

	/**
	 * @see domain.reservation.ReservationDao#updateReservation(domain.reservation.Reservation)
	 */
	public void updateReservation(Reservation reservation) throws ReservationException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("UPDATE ");
			sql.append(TABLE_NAME);
			sql.append(" set status = '");
			sql.append(reservation.getStatus());
			sql.append("' where reservationNumber='");
			sql.append(reservation.getReservationNumber());
			sql.append("';");
			resultSet = statement.executeQuery(sql.toString());
		}
		catch (SQLException e) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("updateReservation()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
	}

	/**
	 * @see domain.reservation.ReservationDao#createReservation(domain.reservation.Reservation)
	 */
	public void createReservation(Reservation reservation) throws ReservationException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("INSERT INTO ");
			sql.append(TABLE_NAME);
			sql.append(" (reservationNumber, stayingDate, status) ");
			sql.append("values ('");
			sql.append(reservation.getReservationNumber());
			sql.append("', '");
			sql.append(DateUtil.convertToString(reservation.getStayingDate()));
			sql.append("', '");
			sql.append(reservation.getStatus());
			sql.append("');");
			resultSet = statement.executeQuery(sql.toString());
		}
		catch (SQLException e) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("createReservation()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
	}

	private Connection getConnection() throws ReservationException {
		Connection connection = null;
		try {
			Class.forName(DRIVER_NAME);
			connection = DriverManager.getConnection(URL, ID, PASSWORD);
		}
		catch (Exception e) {
			throw new ReservationException(ReservationException.CODE_DB_CONNECT_ERROR, e);
		}
		return connection;
	}

	private void close(ResultSet resultSet, Statement statement, Connection connection)
			throws ReservationException {
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
			throw new ReservationException(ReservationException.CODE_DB_CLOSE_ERROR, e);
		}
	}
}
