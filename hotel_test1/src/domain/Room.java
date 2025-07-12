package domain;

public class Room {
    private final int roomNumber;
    private final RoomType type;
    private boolean isInUse = false; // チェックイン中かどうか

    public Room(int roomNumber, RoomType type) {
        this.roomNumber = roomNumber;
        this.type = type;
    }
    public int getRoomNumber() { return roomNumber; }
    public RoomType getType()  { return type; }
    public boolean isInUse()   { return isInUse; }

    /* パッケージプライベート：サービス層のみが状態変更できる */
    void markInUse()   { isInUse = true; }
    void markVacant()  { isInUse = false; }

    @Override public String toString() {
        return roomNumber + " (" + type.getJpName() + ")";
    }
}

