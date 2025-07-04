/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.room;

import java.util.Date;

/**
 * Interface for accessing to Available Qty Data Object<br>
 * 
 */
public interface AvailableQtyDao {

	public AvailableQty getAvailableQty(Date date) throws RoomException;

	public void updateAvailableQty(AvailableQty availableQty) throws RoomException;

	public void createAbailableQty(AvailableQty availableQty) throws RoomException;

}
