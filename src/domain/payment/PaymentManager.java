/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.payment;

import java.util.Date;

import util.DateUtil;
import domain.DaoFactory;

/**
 * Manager for payments<br>
 * 
 */
public class PaymentManager {

	/**
	 * Fee per one night<br>
	 */
	private static final int RATE_PER_DAY = 8000;

	public void createPayment(Date stayingDate, String roomNumber) throws PaymentException,
			NullPointerException {
		if (stayingDate == null) {
			throw new NullPointerException("stayingDate");
		}
		if (roomNumber == null) {
			throw new NullPointerException("roomNumber");
		}

		Payment payment = new Payment();
		payment.setStayingDate(stayingDate);
		payment.setRoomNumber(roomNumber);
		payment.setAmount(getRatePerDay(roomNumber));
		payment.setStatus(Payment.PAYMENT_STATUS_CREATE);

		PaymentDao paymentDao = getPaymentDao();
		paymentDao.createPayment(payment);
	}

	private int getRatePerDay(String roomNumber) {
		return RATE_PER_DAY;
	}

	public void consumePayment(Date stayingDate, String roomNumber) throws PaymentException,
			NullPointerException {
		if (stayingDate == null) {
			throw new NullPointerException("stayingDate");
		}
		if (roomNumber == null) {
			throw new NullPointerException("roomNumber");
		}

		PaymentDao paymentDao = getPaymentDao();
		Payment payment = paymentDao.getPayment(stayingDate, roomNumber);
		//If corresponding payment does not exist
		if (payment == null) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_PAYMENT_NOT_FOUND);
			exception.getDetailMessages().add("staying_date[" + DateUtil.convertToString(stayingDate) + "]");
			exception.getDetailMessages().add("room_number[" + roomNumber + "]");
			throw exception;
		}
		//If payment has been consumed already
		if (payment.getStatus().equals(Payment.PAYMENT_STATUS_CONSUME)) {
			PaymentException exception = new PaymentException(
					PaymentException.CODE_PAYMENT_ALREADY_CONSUMED);
			exception.getDetailMessages().add("staying_date[" + DateUtil.convertToString(stayingDate) + "]");
			exception.getDetailMessages().add("room_number[" + roomNumber + "]");
			throw exception;
		}
		
		payment.setStatus(Payment.PAYMENT_STATUS_CONSUME);
		paymentDao.updatePayment(payment);
	}

	private PaymentDao getPaymentDao() {
		return DaoFactory.getInstance().getPaymentDao();
	}
}
