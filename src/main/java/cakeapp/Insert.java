package cakeapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Insert {
    Scanner sc = new Scanner(System.in);

    // 케이크 주문 추가
    public void insertCakeOrder() {
        String cakeName;
        String customizing;

        System.out.println("=== 케이크 주문 추가 ===");
        while (true) {
            try {
                System.out.print("케이크 이름을 입력하세요 : ");
                cakeName = sc.nextLine();
                if (cakeName.trim().isEmpty()) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("문자열을 입력하세요");
                sc.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("커스터마이징 상세 옵션을 입력하세요 : ");
                customizing = sc.nextLine();
                if (customizing.trim().isEmpty()) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("문자열을 입력하세요");
                sc.nextLine();
            }
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO cake_orders (cake_name, customizing_option) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cakeName);
            pstmt.setString(2, customizing);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println(cakeName + ", " + customizing + " 주문 추가되었습니다.");
            } else {
                System.out.println("주문 추가에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }

    // 결제 방법 추가
    public void insertPayment() {
        String payment;

        System.out.println("=== 케이크 주문 결제 ===");
        while (true) {
            try {
                System.out.print("원하시는 결제 방식을 입력하세요: ");
                payment = sc.nextLine();
                if (payment.trim().isEmpty()) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("문자열을 입력하세요");
                sc.nextLine();
            }
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO payments (payment_method) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, payment);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println(payment + "으로 결제되었습니다.");
            } else {
                System.out.println("결제 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }

    // 신규 회원 등록
    public void insertCustomer() {
        String customerName;
        String customerPhoneNumber;
        String customerAddress;

        System.out.println("=== 신규 회원 등록 ===");

        while (true) {
            try {
                System.out.print("이름을 입력하세요: ");
                customerName = sc.nextLine();
                if (customerName.trim().isEmpty()) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("문자열을 입력하세요");
                sc.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("전화번호를 입력하세요: ");
                customerPhoneNumber = sc.nextLine();
                if (customerPhoneNumber.trim().isEmpty()) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("문자열을 입력하세요");
                sc.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("주소를 입력하세요: ");
                customerAddress = sc.nextLine();
                if (customerAddress.trim().isEmpty()) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("문자열을 입력하세요");
                sc.nextLine();
            }
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO customers (name, phone_number, address) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customerName);
            pstmt.setString(2, customerPhoneNumber);
            pstmt.setString(3, customerAddress);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println(customerName + ", " + customerPhoneNumber + ", " + customerAddress + " 회원 추가 완료되었습니다.");
            } else {
                System.out.println("회원 추가에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }
}
