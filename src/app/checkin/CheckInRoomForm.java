/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.checkin;

import app.AppException;

/**
 * Form class for Check-in Customer
 * 
 */
public class CheckInRoomForm {

	private CheckInRoomControl checkInRoomControl = new CheckInRoomControl();

	private CheckInRoomControl getCheckInRoomControl() {
		return checkInRoomControl;
	}

	private String reservationNumber;

	public String checkIn() throws AppException {
		CheckInRoomControl checkInRoomControl = getCheckInRoomControl();
		return checkInRoomControl.checkIn(reservationNumber);
	}

	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	
}
