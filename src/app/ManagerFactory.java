/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app;

import domain.payment.PaymentManager;
import domain.reservation.ReservationManager;
import domain.room.RoomManager;

/**
 * Factory class for generating instance of any Manager class
 * 
 */
public class ManagerFactory {

	private static ManagerFactory instance = new ManagerFactory();

	private ManagerFactory() {
	}

	private ReservationManager reservationManager = new ReservationManager();

	private RoomManager roomManager = new RoomManager();

	private PaymentManager paymentManager = new PaymentManager();

	public static ManagerFactory getInstance() {
		return instance;
	}

	public ReservationManager getReservationManager() {
		return reservationManager;
	}

	public RoomManager getRoomManager() {
		return roomManager;
	}

	public PaymentManager getPaymentManager() {
		return paymentManager;
	}

}
