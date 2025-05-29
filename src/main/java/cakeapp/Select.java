package cakeapp;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class Select {
    Scanner scanner = new Scanner(System.in);

    // SELECT MENU 1: 특정 기간 동안 주문 내역 조회
    public void selectDateOrder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        String customerID;
        String startDateStr, endDateStr;
        Date startDate = null, endDate = null;

        while (true) {
            System.out.print("고객 ID를 입력하세요: ");
            customerID = scanner.nextLine().trim();
            if (!customerID.isEmpty()) break;
            System.out.println("고객 ID는 비어 있을 수 없습니다. 다시 입력해주세요.");
        }

        while (true) {
            System.out.print("조회 시작 날짜를 입력하세요 (예: 2025-05-05): ");
            startDateStr = scanner.nextLine().trim();
            try {
                startDate = sdf.parse(startDateStr);
                break;
            } catch (ParseException e) {
                System.out.println("날짜 형식이 잘못되었습니다. 형식: yyyy-MM-dd");
            }
        }

        while (true) {
            System.out.print("조회 종료 날짜를 입력하세요 (예: 2025-05-10): ");
            endDateStr = scanner.nextLine().trim();
            try {
                endDate = sdf.parse(endDateStr);
                if (endDate.before(startDate)) {
                    System.out.println("종료 날짜는 시작 날짜보다 이후여야 합니다.");
                    continue;
                }
                break;
            } catch (ParseException e) {
                System.out.println("날짜 형식이 잘못되었습니다. 형식: yyyy-MM-dd");
            }
        }

        String query = "SELECT * FROM orders WHERE customer_id = ? AND order_date BETWEEN ? AND ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerID);
            pstmt.setDate(2, java.sql.Date.valueOf(startDateStr));
            pstmt.setDate(3, java.sql.Date.valueOf(endDateStr));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("주문 ID: " + rs.getInt("order_id") + ", 날짜: " + rs.getDate("order_date") + ", 금액: " + rs.getInt("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT MENU 2: 가격 범위 내 케이크별 주문 횟수와 가격 조회
    public void selectCakePopularity() {
        int minPrice, maxPrice;

        while (true) {
            System.out.print("최소 가격을 입력하세요: ");
            try {
                minPrice = Integer.parseInt(scanner.nextLine().trim());
                if (minPrice < 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("유효한 정수를 입력해주세요. (0 이상의 숫자)");
            }
        }

        while (true) {
            System.out.print("최대 가격을 입력하세요: ");
            try {
                maxPrice = Integer.parseInt(scanner.nextLine().trim());
                if (maxPrice < minPrice) {
                    System.out.println("최대 가격은 최소 가격보다 크거나 같아야 합니다.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("유효한 정수를 입력해주세요.");
            }
        }

        String query = "SELECT cake_name, COUNT(*) as order_count, AVG(price) as avg_price " +
                       "FROM orders WHERE price BETWEEN ? AND ? GROUP BY cake_name";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, minPrice);
            pstmt.setInt(2, maxPrice);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("케이크 이름: " + rs.getString("cake_name") +
                                   ", 주문 횟수: " + rs.getInt("order_count") +
                                   ", 평균 가격: " + rs.getInt("avg_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT MENU 3: 고객별 최근 주문 조회
    public void selectCustomerOrder() {
        String customerID;

        while (true) {
            System.out.print("고객 ID를 입력하세요: ");
            customerID = scanner.nextLine().trim();
            if (!customerID.isEmpty()) break;
            System.out.println("ID를 입력해주세요.");
        }

        String query = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_date DESC LIMIT 5";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("주문 ID: " + rs.getInt("order_id") +
                                   ", 날짜: " + rs.getDate("order_date") +
                                   ", 금액: " + rs.getInt("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT MENU 4: 케이크 사이즈 별 선택 가능 케이크와 옵션
    public void selectCakeSize() {
        String cakeSize;

        while (true) {
            System.out.print("케이크 사이즈를 입력하세요 (S, M, L): ");
            cakeSize = scanner.nextLine().trim().toUpperCase();
            if (cakeSize.matches("[SML]")) break;
            System.out.println("잘못된 사이즈입니다. S, M, L 중 하나만 입력해주세요.");
        }

        String query = "SELECT cake_name, options FROM cakes WHERE size = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cakeSize);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("케이크 이름: " + rs.getString("cake_name") +
                                   ", 옵션: " + rs.getString("options"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT MENU 5: 일정 금액 이상 주문한 고객 리스트
    public void selectMinOrdor() {
        int minAmount;

        while (true) {
            System.out.print("고객 조회를 위한 최소 주문 금액을 입력하세요 (예: 50000): ");
            String input = scanner.nextLine().trim();

            try {
                if (input.isEmpty()) {
                    System.out.println("금액을 입력해주세요.");
                    continue;
                }

                minAmount = Integer.parseInt(input);
                if (minAmount < 0) {
                    System.out.println("금액은 0 이상이어야 합니다.");
                } else {
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력 가능합니다.");
            }
        }

        String query = "SELECT DISTINCT customer_id FROM orders WHERE amount >= ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, minAmount);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("고객 ID: " + rs.getString("customer_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}