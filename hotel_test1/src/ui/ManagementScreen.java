package ui;

import application.CheckInOutManager;

import java.util.Scanner;

public class ManagementScreen {
    private final CheckInOutManager cm;
    private final Scanner sc = new Scanner(System.in);

    public ManagementScreen(CheckInOutManager cm) { this.cm = cm; }

    public void start() {
        System.out.println("\n-- 管理画面 --");
        while (true) {
            System.out.println("1. チェックイン処理");
            System.out.println("2. チェックアウト処理");
            System.out.println("0. 戻る");
            System.out.print("選択 > ");
            switch (sc.nextLine().trim()) {
                case "1" -> handleCheckIn();
                case "2" -> handleCheckOut();
                case "0" -> { return; }
                default  -> System.out.println("不正な入力です。");
            }
        }
    }
    private void handleCheckIn() {
        System.out.print("予約番号 > ");
        boolean ok = cm.checkIn(sc.nextLine().trim());
        System.out.println(ok ? "チェックイン完了しました。" : "予約が見つからないか、既にチェックイン済みです。");
    }
    private void handleCheckOut() {
        System.out.print("部屋番号 > ");
        try {
            int roomNo = Integer.parseInt(sc.nextLine().trim());
            Integer price = cm.calcPrice(roomNo);
            if (price == null) {
                System.out.println("該当部屋は現在使用中ではありません。");
				return;
            } else {
                System.out.println("宿泊料金: " + price + " 円");
            }
			while (true) {
				System.out.println("\n--- 支払い確認 ---");
				System.out.println("1.料金の支払い完了");
				System.out.println("2.料金の支払い未完了");
				System.out.print("選択 > ");
				switch (sc.nextLine().trim()) {
					case "1" : {
						cm.checkOut(roomNo);
						System.out.println("チェックアウトが完了しました。");
						return;
					}
					case "2" : {
						System.out.println("チェックアウトがキャンセルされました。");
						return;
					}
					default  : System.out.println("不正な入力です。");
				}
			}
        } catch (NumberFormatException e) {
            System.out.println("番号が不正です。");
        }
    }
}

