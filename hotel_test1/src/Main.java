import ui.ManagementScreen;
import ui.ReservationScreen;
import application.CheckInOutManager;
import application.ReservationManager;
import domain.Room;
import domain.RoomType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ---------- ドメイン初期化 ----------
        List<Room> initialRooms = Arrays.asList(
                new Room(101, RoomType.SINGLE),
                new Room(102, RoomType.SINGLE),
                new Room(201, RoomType.DOUBLE),
                new Room(202, RoomType.DOUBLE),
                new Room(301, RoomType.LARGE)
        );

        ReservationManager reservationManager = new ReservationManager(initialRooms);
        CheckInOutManager checkManager       = new CheckInOutManager(reservationManager);

        // ---------- メインメニュー ----------
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== ホテル予約システム ===");
            System.out.println("1. 客として操作する");
            System.out.println("2. 受付係として操作する");
            System.out.println("0. 終了");
            System.out.print("選択 > ");

            switch (sc.nextLine().trim()) {
                case "1" -> new ReservationScreen(reservationManager,chechManager).start();
                case "2" -> new ManagementScreen(checkManager).start();
                case "0" -> {
                    System.out.println("終了します。ご利用ありがとうございました。");
                    return;
                }
                default  -> System.out.println("不正な入力です。");
            }
        }
    }
}

