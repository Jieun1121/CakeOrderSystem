package cakeapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Update {
    Scanner scanner = new Scanner(System.in);

    // UPDATE MENU 1: 주문 상태 변경
    public void updateOrderState(Connection conn) {
        String orderId;
        String newStatus;

        while (true) {
            System.out.print("주문 ID를 입력하세요 (예: OR001): ");
            orderId = scanner.nextLine().trim();

            if (orderId.isEmpty()) {
                System.out.println("주문 ID는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("변경할 주문 상태를 입력하세요 (pending/completed/cancelled): ");
            newStatus = scanner.nextLine().trim().toLowerCase();

            if (newStatus.isEmpty() ||
                !(newStatus.equals("pending") || newStatus.equals("completed") || newStatus.equals("cancelled"))) {
                System.out.println("올바른 상태값이 아닙니다. (pending/completed/cancelled 중 선택)");
            } else {
                break;
            }
        }

        String sql = "UPDATE CakeOrder SET status = ? WHERE order_id = ?";
        try {
            conn.setAutoCommit(false); 

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newStatus);
                pstmt.setString(2, orderId);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    System.out.println("주문 상태가 성공적으로 변경되었습니다.");
                } else {
                    System.out.println("해당 주문 ID를 찾을 수 없습니다.");
                }
            }

            conn.commit(); 
        } catch (SQLException e) {
            try {
                conn.rollback(); 
                System.out.println("트랜잭션 롤백됨: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.out.println("롤백 중 오류 발생: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true); 
            } catch (SQLException e) {
                System.out.println("autoCommit 복원 실패: " + e.getMessage());
            }
        }
    }

    // UPDATE MENU 2: 고객 주소 변경
    public void updateCustomerAddress(Connection conn) {
        String customerId;
        String newAddress;

        while (true) {
            System.out.print("변경할 고객 ID를 입력하세요 (예: CU008): ");
            customerId = scanner.nextLine().trim();

            if (customerId.isEmpty()) {
                System.out.println("ID는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("변경할 고객 주소를 입력하세요 (예: Busan, Korea): ");
            newAddress = scanner.nextLine().trim();

            if (newAddress.isEmpty()) {
                System.out.println("주소는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else {
                break;
            }
        }

        String sql = "UPDATE Customer SET address = ? WHERE customer_id = ?";
        try {
            conn.setAutoCommit(false); 

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newAddress);
                pstmt.setString(2, customerId);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    System.out.println("고객 주소가 성공적으로 변경되었습니다.");
                } else {
                    System.out.println("해당 고객 ID를 찾을 수 없습니다.");
                }
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("트랜잭션 롤백됨: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.out.println("롤백 중 오류 발생: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("autoCommit 복원 실패: " + e.getMessage());
            }
        }
    }
}