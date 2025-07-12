package domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ReservationInfo {
    public enum Status { BOOKED, CHECKED_IN, CHECKED_OUT }

    private final String reservationId;
    private final Room room;
    private final LocalDate from;
    private final LocalDate to;
    private final int guests;
    private Status status = Status.BOOKED;

    public ReservationInfo(Room room, LocalDate from, LocalDate to, int guests) {
        this.reservationId = UUID.randomUUID().toString().substring(0, 8);
        this.room = room;
        this.from = from;
        this.to = to;
        this.guests = guests;
    }
    /* ----------- getter ----------- */
    public String getId()    { return reservationId; }
    public Room getRoom()    { return room; }
    public LocalDate getFrom() { return from; }
    public LocalDate getTo()   { return to; }
    public int getGuests()     { return guests; }
    public Status getStatus()  { return status; }

    /* ----------- ビジネスロジック ----------- */
    public long nights() {
        return ChronoUnit.DAYS.between(from, to);
    }
    public int calcPrice() {
        return (int) (nights() * room.getType().getRatePerNight());
    }
    public void checkIn()  { status = Status.CHECKED_IN;  room.markInUse(); }
    public void checkOut() { status = Status.CHECKED_OUT; room.markVacant(); }
}

