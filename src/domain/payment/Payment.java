/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.payment;

import java.util.Date;

/**
 * Payment entity<br>
 * 
 */
public class Payment {

	public static final String PAYMENT_STATUS_CREATE = "create";

	public static final String PAYMENT_STATUS_CONSUME = "consume";

	private Date stayingDate;

	private String roomNumber;

	/**
	 * Fee
	 */
	private int amount;

	private String status;

	public Date getStayingDate() {
		return stayingDate;
	}

	public void setStayingDate(Date stayingDate) {
		this.stayingDate = stayingDate;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * Return fee
	 * 
	 * @return fee
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Set fee
	 * 
	 * @param fee
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
