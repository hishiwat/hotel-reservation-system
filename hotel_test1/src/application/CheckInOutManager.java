package application;

import domain.ReservationInfo;

public class CheckInOutManager {
    private final ReservationManager reservationManager;

    public CheckInOutManager(ReservationManager rm) {
        this.reservationManager = rm;
    }

    /* ---------- チェックイン ---------- */
    public boolean checkIn(String reservationId) {
        return reservationManager.findById(reservationId)
                .filter(r -> r.getStatus() == ReservationInfo.Status.BOOKED)
                .map(r -> { r.checkIn(); return true; })
                .orElse(false);
    }

    /* ---------- チェックアウト ---------- */
    public Integer calcPrice(int roomNumber) {
        return reservationManager.findActiveByRoom(roomNumber)
                .map(r -> {
                    return r.calcPrice();
                })
                .orElse(null);
    }

    public void checkOut(int roomNumber) {
        reservationManager.findActiveByRoom(roomNumber)
                .ifPresent(r -> r.checkOut());
    }
}

