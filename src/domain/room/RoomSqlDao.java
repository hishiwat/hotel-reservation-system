/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DateUtil;

/**
 * DB SQL implementation of Room Data Object interface<br>
 * 
 */
public class RoomSqlDao implements RoomDao {

	private static final String ID = "sa";

	private static final String PASSWORD = "";

	private static final String DRIVER_NAME = "org.hsqldb.jdbcDriver";

	private static final String URL = "jdbc:hsqldb:hsql://localhost;shutdown=true";

	private static final String TABLE_NAME = "ROOM";

	/**
	 * @see domain.room.RoomDao#getRooms()
	 */
	public List getRooms() throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		List roomList = new ArrayList();
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT roomnumber FROM ");
			sql.append(TABLE_NAME);
			sql.append(";");
			resultSet = statement.executeQuery(sql.toString());
			while (resultSet.next()) {
				roomList.add(resultSet.getString("roomnumber"));
			}
		}
		catch (SQLException e) {
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getRooms()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
		return roomList;
	}

	/**
	 * @see domain.room.RoomDao#getRoom(java.lang.String)
	 */
	public Room getRoom(String roomNumber) throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		Room room = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT roomnumber, stayingdate FROM ");
			sql.append(TABLE_NAME);
			sql.append(" WHERE ROOMNUMBER='");
			sql.append(roomNumber);
			sql.append("';");

			resultSet = statement.executeQuery(sql.toString());
			if (resultSet.next() == true) {
				room = new Room();
				room.setRoomNumber(roomNumber);
				room.setStayingDate(DateUtil.convertToDate(resultSet.getString("stayingDate")));
			}
		}
		catch (SQLException e) {
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getRoom()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
		return room;
	}

	/**
	 * @see domain.room.RoomDao#getEmptyRooms()
	 */
	public List getEmptyRooms() throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		List emptyRoomList = new ArrayList();
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT roomnumber FROM ");
			sql.append(TABLE_NAME);
			sql.append(" WHERE stayingdate='';");
			resultSet = statement.executeQuery(sql.toString());
			while (resultSet.next()) {
				Room room = new Room();
				room.setRoomNumber(resultSet.getString("roomnumber"));
				emptyRoomList.add(room);
			}
		}
		catch (SQLException e) {
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getEmptyRooms()");
			throw exception;
		}
		finally {
			close(resultSet, statement, connection);
		}
		return emptyRoomList;
	}

	/**
	 * @see domain.room.RoomDao#updateRoom(domain.room.Room)
	 */
	public void updateRoom(Room room) throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("UPDATE ");
			sql.append(TABLE_NAME);
			sql.append(" SET stayingdate =");
			//Room status and staying date share the same portion on DB table
			if (room.getStayingDate() == null) {
				sql.append("''");
			}
			else {
				sql.append("'");
				sql.append(DateUtil.convertToString(room.getStayingDate()));
				sql.append("'");
			}
			sql.append(" WHERE roomnumber='");
			sql.append(room.getRoomNumber());
			sql.append("';");
			resultSet = statement.executeQuery(sql.toString());
		}
		catch (SQLException e) {
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("updateRoom()");
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
