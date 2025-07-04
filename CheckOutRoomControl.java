/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.checkout;

import app.AppException;
import app.ManagerFactory;
import domain.payment.PaymentException;
import domain.payment.PaymentManager;
import domain.room.RoomException;
import domain.room.RoomManager;
import java.util.Date;

/**
 * Control class for Check-out Customer
 * 
 */
public class CheckOutRoomControl {
	
	public void checkOut(String roomNumber) throws AppException {
		try {
			//Clear room
			/*
			 * Your code for clearing room by using domain.room.RoomManager
			 * 
			 */
			//この下2行が加えた処理
			RoomManager roomManager = getRoomManager(); 
			Date stayingDate = roomManager.removeCustomer(roomNumber); //客退出後の処理（たぶん）
			//Consume payment
			/*
			 * Your code for consuming payment by using domain.payment.PaymentManager
			 * 
			 */
			//この下2行が加えた処理
			PaymentManager paymentManager = getPaymentManager();
			paymentManager.consumePayment(stayingDate, roomNumber); //料金の支払いを完了させる
		}
		catch (RoomException e) {
			AppException exception = new AppException("Failed to check-out", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
		catch (PaymentException e) {
			AppException exception = new AppException("Failed to check-out", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
	}

	private RoomManager getRoomManager() {
		return ManagerFactory.getInstance().getRoomManager();
	}

	private PaymentManager getPaymentManager() {
		return ManagerFactory.getInstance().getPaymentManager();
	}
}
