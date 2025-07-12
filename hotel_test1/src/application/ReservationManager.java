package application;

import domain.Room;
import domain.RoomType;
import domain.ReservationInfo;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationManager {
    private final List<Room> rooms;
    private final Map<String, ReservationInfo> reservations = new HashMap<>();

    public ReservationManager(List<Room> rooms) {
        this.rooms = rooms;
    }

    /* ----------- 検索 ----------- */
    public List<Room> findAvailableRooms(LocalDate from, LocalDate to,
                                         int guests, RoomType type) {
        return rooms.stream()
                .filter(r -> r.getType() == type)
                .filter(r -> r.getType().getCapacity() >= guests)
                .filter(r -> isRoomVacant(r, from, to))
                .collect(Collectors.toList());
    }

    /* ----------- 予約 ----------- */
    public ReservationInfo makeReservation(Room room, LocalDate from,
                                           LocalDate to, int guests) {
        ReservationInfo info = new ReservationInfo(room, from, to, guests);
        reservations.put(info.getId(), info);
        return info;
    }

    /* ----------- 参照 ----------- */
    public Optional<ReservationInfo> findById(String id) {
        return Optional.ofNullable(reservations.get(id));
    }
    public Optional<ReservationInfo> findActiveByRoom(int roomNumber) {
        return reservations.values().stream()
                .filter(r -> r.getRoom().getRoomNumber() == roomNumber)
                .filter(r -> r.getStatus() == ReservationInfo.Status.CHECKED_IN)
                .findFirst();
    }

    /* ----------- 内部ユーティリティ ----------- */
    private boolean isRoomVacant(Room room, LocalDate from, LocalDate to) {
        return reservations.values().stream()
                .filter(res -> res.getRoom().equals(room))
                .filter(res -> res.getStatus() != ReservationInfo.Status.CHECKED_OUT)
                .noneMatch(res -> !(to.isBefore(res.getFrom()) || from.isAfter(res.getTo())));
    }
}

