/*
 * Copyright(C) 2007 National Institute of Informatics, All rights reserved.
 */
package domain.reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception specific to reservation
 * 
 */
public class ReservationException extends Exception {

	private List detailMessages = new ArrayList();

	public static final int CODE_RESERVATION_NOT_FOUND = 1;

	public static final int CODE_RESERVATION_ALREADY_CONSUMED = 2;

	public static final int CODE_DB_EXEC_QUERY_ERROR = 3;

	public static final int CODE_DB_CONNECT_ERROR = 4;

	public static final int CODE_DB_CLOSE_ERROR = 5;

	private static final String MESSAGE_RESERVATION_NOT_FOUND = "Reservation not found";

	private static final String MESSAGE_RESERVATION_ALREADY_CONSUMED = "Reservation already consumed";

	private static final String MESSAGE_DB_EXEC_QUERY_ERROR = "DB execution query error";

	private static final String MESSAGE_DB_CONNECT_ERROR = "DB connection error";

	private static final String MESSAGE_DB_CLOSE_ERROR = "DB close error";

	private int code;

	public ReservationException(int code) {
		super();
		this.code = code;
	}

	public ReservationException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		String result = null;
		switch (code) {
			case CODE_RESERVATION_NOT_FOUND:
				result = MESSAGE_RESERVATION_NOT_FOUND;
				break;
			case CODE_RESERVATION_ALREADY_CONSUMED:
				result = MESSAGE_RESERVATION_ALREADY_CONSUMED;
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
