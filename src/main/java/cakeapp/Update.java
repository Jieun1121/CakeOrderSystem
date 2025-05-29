package cakeapp;

import java.util.Scanner;

public class Update {
	Scanner scanner = new Scanner(System.in);
	
	 //UPDATE MENU 1: 주문 상태 변경
	public void updateOrderState() {
		String orderId = null;
        String newStatus = null;

        // 주문 ID 입력 받기
        while (true) {
            System.out.print("주문 ID를 입력하세요 (예: OR001): ");
            orderId = scanner.nextLine().trim();

            if (orderId == null || orderId.isEmpty()) {
                System.out.println("주문 ID는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else {
                break;
            }
        }

        // 상태 입력 받기
        while (true) {
            System.out.print("변경할 주문 상태를 입력하세요 (pending/completed/cancelled): ");
            newStatus = scanner.nextLine().trim().toLowerCase();

            if (newStatus == null || newStatus.isEmpty()) {
                System.out.println("상태는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else if (!newStatus.equals("pending") && !newStatus.equals("completed") && !newStatus.equals("cancelled")) {
                System.out.println("올바른 상태값이 아닙니다. (pending/completed/cancelled 중 선택)");
            } else {
                break;
            }
        }

        System.out.println("입력된 주문 ID: " + orderId);
        System.out.println("변경할 상태: " + newStatus);
	}
	//UPDATE MENU 2: 고객 주소 변경
	public void updateCustomerAddress() {
        String customerId = null; 
        String newAddress = null;
        
        while (true) {
            System.out.print("변경할 고객 ID를 입력하세요 (예: CU008): ");
            customerId = scanner.nextLine().trim();

            if (customerId == null || customerId.isEmpty()) {
                System.out.println("ID는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else {
                break;
            }
        }

        // 주소 입력 받기
        while (true) {
            System.out.print("변경할 고객 주소를 입력하세요 (예: Busan, Korea): ");
            newAddress = scanner.nextLine().trim();

            if (newAddress == null || newAddress.isEmpty()) {
                System.out.println("주소는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else {
                break;
            }
        }

        System.out.println("입력된 고객 ID: " + customerId);
        System.out.println("변경할 주소: " + newAddress);
	}

}
