package ui;

import application.ReservationManager;
import domain.Room;
import domain.RoomType;
import domain.ReservationInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReservationScreen {
    private final ReservationManager rm;
    private final Scanner sc = new Scanner(System.in);

    public ReservationScreen(final ReservationManager rm) {
        this.rm = rm;
    }

    public void start() {
        System.out.println("\n-- 予約画面 --");

        /* -------- 入力 -------- */
        final LocalDate from = inputDate("到着日 (例 2025-07-01) > ");
        final LocalDate to = inputDate("出発日 (例 2025-07-03) > ");
        
        while(true){
            System.out.println("人数 > ");
            final int guests = inputGuests();

            if(guest<1){
                System.out.println("不正な人数です。\n再度人数の入力をお願いします。\n");
            }
            else break;
        }
        
        final RoomType type = inputRoomType();
        

        /* -------- 検索 -------- */
        final List<Room> list = rm.findAvailableRooms(from, to, guests, type);
        if (list.isEmpty()) {
            System.out.println("該当する空室がありません。");
            return;
        }
        System.out.println("\n--- 予約可能部屋リスト ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ") " + list.get(i));
        }
        System.out.print("予約する部屋を番号で選択 (0でキャンセル) > ");
        final int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
        if (idx < 0 || idx >= list.size()) {
            return;
        }

        /* -------- 予約 -------- */
        final Room chosen = list.get(idx);
        final ReservationInfo info = rm.makeReservation(chosen, from, to, guests);
        System.out.println("予約完了！ 予約番号: " + info.getId());
    }

    /* ------------- helper ------------- */
    private LocalDate inputDate(final String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("日付形式が不正です。");
            }
        }
    }

    private int inputGuests() {
        while (true) {
            System.out.print("人数 > ");
            final String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (final NumberFormatException e) {
                System.out.println("不正な値です（例：3）");
            }
        }
    }

    private RoomType inputRoomType() {
        while (true) {
            System.out.print("部屋種別 (1=シングル 2=ダブル 3=大部屋) > ");
            switch (sc.nextLine().trim()) {
                case "1":
                    return RoomType.SINGLE;
                case "2":
                    return RoomType.DOUBLE;
                case "3":
                    return RoomType.LARGE;
            }
            System.out.println("不正な入力です。");
        }
    }
}

