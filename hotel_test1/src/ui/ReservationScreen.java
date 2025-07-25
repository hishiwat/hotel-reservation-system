package ui;

import application.ReservationManager;
import application.CheckInOutManager;
import domain.Room;
import domain.RoomType;
import domain.ReservationInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReservationScreen {
    private final ReservationManager rm;
    private final CheckInOutManager cm;
    private final Scanner sc = new Scanner(System.in);

    public ReservationScreen(ReservationManager rm, CheckInOutManager cm) {
        this.rm = rm;
        this.cm = cm;
    }

    public void start() {
        System.out.println("\n--予約/キャンセル画面--");
        while (true) {
            System.out.println("1. 部屋を予約する");
            System.out.println("2. 予約をキャンセルする");
            System.out.println("0. 戻る");
            System.out.print("選択 > ");
            switch (sc.nextLine().trim()) {
                case "1" -> handleReservation();
                case "2" -> handleCancel();
                case "0" -> { return; }
                default  -> System.out.println("不正な入力です。");
            }
        }
    }
    
    private void handleReservation(){
        System.out.println("\n-- 予約画面 --");

        /* -------- 入力 -------- */
        LocalDate from;
        LocalDate to;
        while(true){
            from = inputDate("到着日 (例 2025-07-01) > ");
            to   = inputDate("出発日 (例 2025-07-03) > ");

            if(to.isBefore(from)){
                System.out.println("不正な日付の組み合わせです。\n再度日付の入力をお願いします。\n");
            }
            else break;
        }
        System.out.print("人数 > ");
        final int guests = Integer.parseInt(sc.nextLine().trim());
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

    private void handleCancel(){
        System.out.println("\n-- キャンセル画面 --");
        System.out.println("キャンセルする予約番号・部屋番号を入力してください。");
        System.out.print("予約番号 > ");
        int roomNo = cm.checkIn(sc.nextLine().trim());
        System.out.print("部屋番号 > ");
        try {
            roomNo = Integer.parseInt(sc.nextLine().trim());
            Integer price = cm.calcPrice(roomNo);
            if (price == null) {
                System.out.println("該当部屋は予約中ではありません。");
				return;
            } else {
                cm.checkOut(roomNo);
                System.out.println("キャンセルが完了しました。\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("番号が不正です。");
        }
    }

    /* ------------- helper ------------- */
    private LocalDate inputDate(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("日付形式が不正です。");
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

