/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.payment;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception specific to payment
 * 
 */
public class PaymentException extends Exception {

	private List detailMessages = new ArrayList();

	public static final int CODE_PAYMENT_NOT_FOUND = 1;

	public static final int CODE_PAYMENT_ALREADY_CONSUMED = 2;

	public static final int CODE_DB_EXEC_QUERY_ERROR = 3;

	public static final int CODE_DB_CONNECT_ERROR = 4;

	public static final int CODE_DB_CLOSE_ERROR = 5;

	private static final String MESSAGE_PAYMENT_NOT_FOUND = "No applicable payment data was found.";

	private static final String MESSAGE_PAYMENT_ALREADY_CONSUMED = "The relevant payment data has already been completed.";

	private static final String MESSAGE_DB_EXEC_QUERY_ERROR = "An error occurred while executing the query.";

	private static final String MESSAGE_DB_CONNECT_ERROR = "The system could not connect to the database.";

	private static final String MESSAGE_DB_CLOSE_ERROR = "The system could not disconnect from the database.";

	private int code;

	public PaymentException(int code) {
		super();
		this.code = code;
	}

	public PaymentException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		String result = null;
		switch (code) {
			case CODE_PAYMENT_NOT_FOUND:
				result = MESSAGE_PAYMENT_NOT_FOUND;
				break;
			case CODE_PAYMENT_ALREADY_CONSUMED:
				result = MESSAGE_PAYMENT_ALREADY_CONSUMED;
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
