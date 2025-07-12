package domain;

public enum RoomType {
    SINGLE("シングル", 1,  8000),
    DOUBLE("ダブル",   2, 12000),
    LARGE ("大部屋",   4, 20000);

    private final String jpName;
    private final int capacity;
    private final int ratePerNight;

    RoomType(String jpName, int capacity, int rate) {
        this.jpName = jpName;
        this.capacity = capacity;
        this.ratePerNight = rate;
    }
    public String getJpName()      { return jpName; }
    public int getCapacity()       { return capacity; }
    public int getRatePerNight()   { return ratePerNight; }
}

