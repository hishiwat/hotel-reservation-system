/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.room;

import java.util.Date;

/**
 * Class representing number of available rooms on certain date<br>
 * 
 */
public class AvailableQty {

	/**
	 * Literal of maximum available number
	 */
	public static final int AVAILABLE_ALL = -1;

	private Date date;

	/**
	 * number of available rooms
	 */
	private int qty;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}
