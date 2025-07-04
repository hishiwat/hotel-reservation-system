/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.payment;

import java.util.Date;

/**
 * Interface for accessing to Payment Data Object<br>
 * 
 */
public interface PaymentDao {

	public abstract Payment getPayment(Date stayingDate, String roomNumber) throws PaymentException;

	public abstract void updatePayment(Payment payment) throws PaymentException;

	public abstract void createPayment(Payment payment) throws PaymentException;
}
