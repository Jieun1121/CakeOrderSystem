package cakeapp;

import java.util.Scanner;

public class Delete {
	Scanner scanner = new Scanner(System.in);
	//DELETE MENU 1: 고객 회원 탈퇴
	public void deleteCustomer() {
		String customerId;

        // 고객 ID 입력 받기
        while (true) {
            System.out.print("삭제할 고객의 ID를 입력하세요 (예: CU001): ");
            customerId = scanner.nextLine().trim();

            if (customerId == null || customerId.isEmpty()) {
                System.out.println("고객 ID는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else {
                break;
            }
        }

        System.out.println("입력된 고객 ID: " + customerId);

	}
	//DELETE MENU 2: 취소된 주문 내역 삭제
	public void deleteCancledOrder() {
		String confirm;
		
		//삭제 여부만 확인
		while (true) {
            System.out.print("모든 'cancelled' 상태의 주문을 삭제하시겠습니까? (Y/N): ");
            confirm = scanner.nextLine().trim().toUpperCase();

            if (confirm == null || confirm.isEmpty()) {
                System.out.println("입력이 비어 있습니다. 다시 입력하세요.");
            } else if (confirm.equals("Y")) {
                System.out.println("'cancelled' 상태의 주문을 삭제합니다.");
                break;
            } else if (confirm.equals("N")) {
                System.out.println("삭제를 취소하였습니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다. Y 또는 N으로 입력해주세요.");
            }
        }
	}
}
