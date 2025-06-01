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
            System.out.print("고객 ID를 입력하세요(ex: CU001): ");
            customerID = scanner.nextLine().trim();
            if (!customerID.isEmpty())
                break;
            System.out.println("고객 ID는 비어 있을 수 없습니다. 다시 입력해주세요.");
        }

        while (true) {
            System.out.print("조회 시작 날짜를 입력하세요 (예: 2024-05-01): ");
            startDateStr = scanner.nextLine().trim();
            try {
                startDate = sdf.parse(startDateStr);
                break;
            } catch (ParseException e) {
                System.out.println("날짜 형식이 잘못되었습니다. 형식: yyyy-MM-dd");
            }
        }

        while (true) {
            System.out.print("조회 종료 날짜를 입력하세요 (예: 2024-05-30): ");
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

        String query = "SELECT * FROM CakeOrder WHERE customer_id = ? AND order_date BETWEEN ? AND ?";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerID);
            pstmt.setDate(2, java.sql.Date.valueOf(startDateStr));
            pstmt.setDate(3, java.sql.Date.valueOf(endDateStr));

            ResultSet rs = pstmt.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("주문 ID: " + rs.getString("order_id")
                        + ", 날짜: " + rs.getDate("order_date")
                        + ", 금액: " + rs.getInt("total_amount"));
            }
            if (!found) {
                System.out.println("해당 조건에 맞는 주문 내역이 없습니다.");
            }

            if (!found) {
                System.out.println("해당 조건에 맞는 주문 내역이 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT MENU 2: 가격 범위 내 케이크별 주문 횟수와 평균 가격 조회
    public void selectCakePopularity() {
        int minPrice, maxPrice;

        while (true) {
            System.out.print("최소 가격을 입력하세요: ");
            try {
                minPrice = Integer.parseInt(scanner.nextLine().trim());
                if (minPrice < 0)
                    throw new NumberFormatException();
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

        String query = "SELECT c.name AS cake_name, COUNT(*) AS order_count, AVG(o.total_amount) AS avg_price " +
                "FROM CakeOrder o JOIN Cake c ON o.cake_id = c.cake_id " +
                "WHERE o.total_amount BETWEEN ? AND ? " +
                "GROUP BY c.name";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, minPrice);
            pstmt.setInt(2, maxPrice);

            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("케이크 이름: " + rs.getString("cake_name") +
                        ", 주문 횟수: " + rs.getInt("order_count") +
                        ", 평균 가격: " + rs.getInt("avg_price"));
            }

            if (!found) {
                System.out.println("해당 가격 범위 내 주문된 케이크가 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT MENU 3: 고객 최근 주문 조회
    public void selectCustomerOrder() {
        String customerID;

        while (true) {
            System.out.print("고객 ID를 입력하세요(ex: CU001): ");
            customerID = scanner.nextLine().trim();
            if (!customerID.isEmpty())
                break;
            System.out.println("ID를 입력해주세요.");
        }

        String query = "SELECT o.order_id, o.order_date, o.total_amount, c.name AS cake_name " +
                "FROM CakeOrder o JOIN Cake c ON o.cake_id = c.cake_id " +
                "WHERE o.customer_id = ? ORDER BY o.order_date DESC LIMIT 5";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerID);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("주문 ID: " + rs.getString("order_id") +
                        ", 날짜: " + rs.getDate("order_date") +
                        ", 금액: " + rs.getInt("total_amount") +
                        ", 케이크 이름: " + rs.getString("cake_name"));
            }

            if (!found) {
                System.out.println("해당 고객의 주문 기록이 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT MENU 4: 사이즈별 케이크 옵션 조회
    public void selectCakeSize() {
        String cakeSize;

        while (true) {
            System.out.print("케이크 사이즈를 입력하세요 (S, M, L): ");
            cakeSize = scanner.nextLine().trim().toUpperCase();
            if (cakeSize.matches("[SML]"))
                break;
            System.out.println("잘못된 사이즈입니다. S, M, L 중 하나만 입력해주세요.");
        }

        String query = "SELECT c.name AS cake_name, cu.option_category, cu.option_detail " +
                "FROM Cake c JOIN Customize cu ON c.cake_id = cu.cake_id " +
                "WHERE c.size = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cakeSize);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("케이크 이름: " + rs.getString("cake_name") +
                        ", 옵션 카테고리: " + rs.getString("option_category") +
                        ", 옵션 내용: " + rs.getString("option_detail"));
            }

            if (!found) {
                System.out.println("해당 사이즈의 케이크 옵션이 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT MENU 5: 단골 고객 조회회
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

        String query = "SELECT DISTINCT customer_id FROM CakeOrder WHERE total_amount >= ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, minAmount);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("고객 ID: " + rs.getString("customer_id"));
            }

            if (!found) {
                System.out.println("해당 조건을 만족하는 고객이 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}