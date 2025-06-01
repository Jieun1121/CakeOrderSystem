package cakeapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete {
    Scanner scanner = new Scanner(System.in);

    // DELETE MENU 1: 고객 회원 탈퇴
    public void deleteCustomer(Connection conn) {
        String customerId;

        while (true) {
            System.out.print("삭제할 고객의 ID를 입력하세요 (예: CU001): ");
            customerId = scanner.nextLine().trim();

            if (customerId.isEmpty()) {
                System.out.println("고객 ID는 비어 있을 수 없습니다. 다시 입력하세요.");
            } else {
                break;
            }
        }

        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("고객 ID " + customerId + "에 대한 회원 탈퇴가 완료되었습니다.");
            } else {
                System.out.println("해당 ID의 고객이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            System.out.println("회원 탈퇴 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // DELETE MENU 2: 취소된 주문 내역 삭제
    public void deleteCancelledOrder(Connection conn) {
        String confirm;

        while (true) {
            System.out.print("모든 'cancelled' 상태의 주문을 삭제하시겠습니까? (Y/N): ");
            confirm = scanner.nextLine().trim().toUpperCase();

            if (confirm.isEmpty()) {
                System.out.println("입력이 비어 있습니다. 다시 입력하세요.");
            } else if (confirm.equals("Y")) {
                String sql = "DELETE FROM CakeOrder WHERE status = 'cancelled'";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    int result = pstmt.executeUpdate();
                    System.out.println(result + "개의 'cancelled' 주문이 삭제되었습니다.");
                } catch (SQLException e) {
                    System.out.println("주문 삭제 중 오류가 발생했습니다: " + e.getMessage());
                }
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
