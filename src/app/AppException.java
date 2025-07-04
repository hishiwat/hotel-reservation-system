/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app;

import java.util.ArrayList;
import java.util.List;

/**
 * Common Exception class specific to HRS
 * 
 */
public class AppException extends Exception {

	private List detailMessages = new ArrayList();

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public List getDetailMessages() {
		return detailMessages;
	}

	public String getFormattedDetailMessages(String separator) {
		StringBuffer buffer = new StringBuffer();
		String message = getMessage();
		if (message != null) {
			buffer.append(message);
			buffer.append(separator);
		}

		List detailMessages = getDetailMessages();
		int len = detailMessages.size();
		if (len > 0) {
			buffer.append("Detail:");
			buffer.append(separator);
			for (int i = 0; i < len; i++) {
				buffer.append(detailMessages.get(i));
				buffer.append(separator);
			}
		}
		return new String(buffer);
	}
}
