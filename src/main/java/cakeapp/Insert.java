package cakeapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.sql.Date;

public class Insert {
    Scanner sc = new Scanner(System.in);

    // INSERT MENU 1: 케이크 주문 추가
    public void insertCakeOrder() {
        String customizingOptionId = ""; // 최종 선택된 option_id 저장
        String customizingDetail = ""; // 화면에 보여줄 option_detail 저장

        // 1) 케이크 목록
        List<String> cakeList = Arrays.asList(
                "초코 케이크", "딸기 생크림 케이크", "레드벨벳 케이크", "고구마 케이크", "블루베리 케이크",
                "망고 요거트 케이크", "티라미수 케이크", "말차 케이크", "오레오 크림 케이크", "카라멜 케이크",
                "유자 케이크", "당근 케이크", "바나나 케이크", "코코넛 케이크", "체리 케이크",
                "레몬 케이크", "복숭아 케이크", "호두 케이크", "얼그레이 케이크", "치즈 케이크");
        Map<String, String> cakeNameToId = new HashMap<>();
        for (int i = 0; i < cakeList.size(); i++) {
            String id = String.format("CK%03d", i + 1); // CK001, CK002, ..., CK020
            cakeNameToId.put(cakeList.get(i), id);
        }

        Scanner sc = new Scanner(System.in);

        // 2) 프롬프트에 번호+이름 모두 표시
        StringBuilder cakePrompt = new StringBuilder("주문할 케이크 번호를 입력하세요 (");
        for (int i = 0; i < cakeList.size(); i++) {
            cakePrompt.append(i + 1).append(". ").append(cakeList.get(i));
            if (i < cakeList.size() - 1)
                cakePrompt.append(" / ");
        }
        cakePrompt.append("): ");

        // 3) 사용자에게 번호 입력받아 케이크 선택
        int cakeIndex;
        while (true) {
            System.out.print(cakePrompt);
            try {
                cakeIndex = Integer.parseInt(sc.nextLine().trim());
                if (cakeIndex >= 1 && cakeIndex <= cakeList.size())
                    break;
                System.out.println("1~" + cakeList.size() + " 사이의 숫자를 입력해주세요.");
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
            }
        }
        String cakeNameSelected = cakeList.get(cakeIndex - 1);
        String cakeId = cakeNameToId.get(cakeNameSelected);

        // 4) DB에서 해당 케이크의 커스터마이징 옵션 조회
        List<Option> options = new ArrayList<>();
        String optSql = "SELECT option_id, option_category, option_detail, letter " +
                "FROM Customize " +
                "WHERE cake_id = ? AND is_customizable = TRUE";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement psOpt = conn.prepareStatement(optSql)) {

            psOpt.setString(1, cakeId);
            ResultSet rs = psOpt.executeQuery();

            while (rs.next()) {
                options.add(new Option(
                        rs.getString("option_id"),
                        rs.getString("option_category"),
                        rs.getString("option_detail"),
                        rs.getString("letter")));
            }

        } catch (SQLException e) {
            System.out.println("옵션 조회 중 DB 오류: " + e.getMessage());
            return;
        }

        // 5) 옵션이 있으면 사용자에게 출력 후 선택 유도
        if (!options.isEmpty()) {
            System.out.println("사용 가능한 커스터마이징 옵션:");

            for (int i = 0; i < options.size(); i++) {
                Option o = options.get(i);
                System.out.print((i + 1) + ". [" + o.category + "] " + o.detail);
                if (o.letter != null && !o.letter.isEmpty()) {
                    System.out.print(" (\"" + o.letter + "\")");
                }
                System.out.println();
            }

            int optIndex;
            while (true) {
                System.out.print("옵션 번호를 선택하세요 (1 ~ " + options.size() + "): ");
                try {
                    optIndex = Integer.parseInt(sc.nextLine().trim());
                    if (optIndex >= 1 && optIndex <= options.size()) {
                        Option selectedOption = options.get(optIndex - 1);
                        customizingOptionId = selectedOption.id;
                        customizingDetail = selectedOption.detail;
                        System.out.println("선택한 옵션: [" + selectedOption.category + "] " + selectedOption.detail);
                        if (selectedOption.letter != null && !selectedOption.letter.isEmpty()) {
                            System.out.println("적용 문구: \"" + selectedOption.letter + "\"");
                        }
                        break;
                    } else {
                        System.out.println("유효한 번호를 입력해주세요 (1~" + options.size() + ").");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("숫자를 입력해주세요.");
                }
            }
        } else {
            System.out.println("이 케이크는 커스터마이징 가능한 옵션이 없습니다.");
        }

        // 6) 최종 주문 정보 DB 저장
        String insertSql = "INSERT INTO CakeOrder (order_id, customer_id, cake_id, option_id, order_date, total_amount, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement psIns = conn.prepareStatement(insertSql)) {

            String orderId = "OD" + System.currentTimeMillis(); // 예시: 고유 주문 ID
            String customerId = "CU001"; // TODO: 실제 로그인한 사용자 ID로 교체
            LocalDate orderDate = LocalDate.now(); // 현재 날짜
            int cakePrice = 20000; // TODO: 케이크 가격 DB에서 조회해서 사용
            int optionPrice = customizingOptionId.isEmpty() ? 0 : 3000; // 예시 옵션 가격
            int totalAmount = cakePrice + optionPrice;
            String status = "pending"; // 기본 상태

            psIns.setString(1, orderId);
            psIns.setString(2, customerId);
            psIns.setString(3, cakeId);
            psIns.setString(4, customizingOptionId.isEmpty() ? null : customizingOptionId);
            psIns.setDate(5, java.sql.Date.valueOf(orderDate));
            psIns.setInt(6, totalAmount);
            psIns.setString(7, status);

            int result = psIns.executeUpdate();
            if (result > 0) {
                System.out.println(cakeNameSelected + " - " +
                        (customizingDetail.isEmpty() ? "옵션 없음" : customizingDetail) +
                        " 주문이 완료되었습니다!");
            } else {
                System.out.println("주문 추가에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println("DB 저장 중 오류: " + e.getMessage());
        }
    }

    // 헬퍼 클래스: 옵션 정보를 담을 DTO
    class Option {
        String id, category, detail, letter;

        Option(String id, String category, String detail, String letter) {
            this.id = id;
            this.category = category;
            this.detail = detail;
            this.letter = letter;
        }
    }

    // INSERT MENU 2: 결제 방법 추가가
    public void insertPayment() {
        Scanner sc = new Scanner(System.in);
        String[] paymentOptions = { "card", "cash" };
        int choice = -1;

        System.out.println("=== 케이크 주문 결제 ===");

        // 결제 방식 선택
        while (true) {
            System.out.println("결제 방식을 선택하세요:");
            for (int i = 0; i < paymentOptions.length; i++) {
                System.out.println((i + 1) + ". " + paymentOptions[i]);
            }

            System.out.print("번호 입력 (1~" + paymentOptions.length + "): ");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= paymentOptions.length) {
                    break;
                } else {
                    System.out.println("유효한 번호를 입력하세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
            }
        }

        String selectedPayment = paymentOptions[choice - 1];

        // 필요한 추가 정보 입력 받기
        System.out.print("주문 ID를 입력하세요 (예: OR024): ");
        String orderId = sc.nextLine();

        System.out.print("결제 금액을 입력하세요: ");
        int amountPaid = Integer.parseInt(sc.nextLine());

        LocalDate today = LocalDate.now();

        // DB 삽입
        try (Connection conn = DatabaseUtil.getConnection()) {
            // payment_id 생성
            String getMaxIdSql = "SELECT MAX(payment_id) FROM Payment";
            PreparedStatement maxStmt = conn.prepareStatement(getMaxIdSql);
            ResultSet rs = maxStmt.executeQuery();

            String paymentId = "PM001"; // 기본값
            if (rs.next() && rs.getString(1) != null) {
                String maxId = rs.getString(1); // 예: "PM012"
                int idNum = Integer.parseInt(maxId.substring(2)) + 1;
                paymentId = String.format("PM%03d", idNum); // 예: "PM013"
            }

            // INSERT 실행
            String sql = "INSERT INTO Payment (payment_id, order_id, payment_method, payment_date, amount_paid) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paymentId);
            pstmt.setString(2, orderId);
            pstmt.setString(3, selectedPayment);
            pstmt.setDate(4, Date.valueOf(today));
            pstmt.setInt(5, amountPaid);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("결제가 완료되었습니다. (결제 수단: " + selectedPayment + ")");
            } else {
                System.out.println("결제 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }
    
    // INSERT MENU 3: 신규 회원 등록
    public void insertCustomer() {
        String customerName;
        String customerPhoneNumber;
        String customerAddress;

        // 사용자 입력
        while (true) {
            System.out.print("이름을 입력하세요: ");
            customerName = sc.nextLine();
            if (customerName.trim().isEmpty()) {
                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("전화번호를 입력하세요: ");
            customerPhoneNumber = sc.nextLine();
            if (customerPhoneNumber.trim().isEmpty()) {
                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("주소를 입력하세요: ");
            customerAddress = sc.nextLine();
            if (customerAddress.trim().isEmpty()) {
                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                continue;
            }
            break;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            // 가장 큰 customer_id 조회 (예: CU010 → CU011)
            String getMaxIdSql = "SELECT MAX(customer_id) FROM customer";
            PreparedStatement maxStmt = conn.prepareStatement(getMaxIdSql);
            ResultSet rs = maxStmt.executeQuery();

            String newCustomerId = "CU001"; // 기본값
            if (rs.next() && rs.getString(1) != null) {
                String maxId = rs.getString(1); // "CU010"
                int idNum = Integer.parseInt(maxId.substring(2)) + 1;
                newCustomerId = String.format("CU%03d", idNum); // "CU011"
            }

            // INSERT 실행
            String sql = "INSERT INTO customer (customer_id, name, phone_number, address) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCustomerId);
            pstmt.setString(2, customerName);
            pstmt.setString(3, customerPhoneNumber);
            pstmt.setString(4, customerAddress);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("신규 회원 등록 성공: " + newCustomerId + " / " + customerName);
            } else {
                System.out.println("회원 추가에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }

}