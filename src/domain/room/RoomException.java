/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.room;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception specific to room
 * 
 */
public class RoomException extends Exception {

	private List detailMessages = new ArrayList();

	public static final int CODE_AVAILABLE_QTY_OUT_OF_BOUNDS = 1;

	public static final int CODE_EMPTYROOM_NOT_FOUND = 2;

	public static final int CODE_ROOM_NOT_FOUND = 3;

	public static final int CODE_ROOM_NOT_FULL = 4;

	public static final int CODE_DB_EXEC_QUERY_ERROR = 5;

	public static final int CODE_DB_CONNECT_ERROR = 6;

	public static final int CODE_DB_CLOSE_ERROR = 7;

	private static final String MESSAGE_AVAILABLE_QTY_OUT_OF_BOUNDS = "Failed to update number of available rooms due invalied date specified";

	private static final String MESSAGE_EMPTYROOM_NOT_FOUND = "No available rooms";

	private static final String MESSAGE_ROOM_NOT_FOUND = "Corresponding room not found";

	private static final String MESSAGE_ROOM_NOT_FULL = "Corresponding room already available";

	private static final String MESSAGE_DB_EXEC_QUERY_ERROR = "DB execution query error";

	private static final String MESSAGE_DB_CONNECT_ERROR = "DB connection error";

	private static final String MESSAGE_DB_CLOSE_ERROR = "DB close error";

	private int code;

	public RoomException(int code) {
		super();
		this.code = code;
	}

	public RoomException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		String result = null;
		switch (code) {
			case CODE_AVAILABLE_QTY_OUT_OF_BOUNDS:
				result = MESSAGE_AVAILABLE_QTY_OUT_OF_BOUNDS;
				break;
			case CODE_EMPTYROOM_NOT_FOUND:
				result = MESSAGE_EMPTYROOM_NOT_FOUND;
				break;
			case CODE_ROOM_NOT_FOUND:
				result = MESSAGE_ROOM_NOT_FOUND;
				break;
			case CODE_ROOM_NOT_FULL:
				result = MESSAGE_ROOM_NOT_FULL;
				break;
			case CODE_DB_EXEC_QUERY_ERROR:
				result = MESSAGE_DB_EXEC_QUERY_ERROR;
				break;
			case CODE_DB_CONNECT_ERROR:
				result = MESSAGE_DB_CONNECT_ERROR;
				break;
			case CODE_DB_CLOSE_ERROR:
				result = MESSAGE_DB_CLOSE_ERROR;
				break;
			default:
				result = "";
				break;

		}
		return result;

	}

	public List getDetailMessages() {
		return detailMessages;
	}
}