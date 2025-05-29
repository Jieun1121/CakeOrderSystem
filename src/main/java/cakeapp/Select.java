package cakeapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class Select {
	 Scanner scanner = new Scanner(System.in);
	
	//SELECT MENU 1: 특정 기간 동안 주문한 내역 조회 (기간 별 소비 내역)
	public void selectDateOrder() {
	 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);  // 날짜 검증 강화
        
        String customerID;
        String startDateStr = null;
        String endDateStr = null;
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
        System.out.println("입력된 값: " +customerID+", "+ startDateStr+ ", " + endDateStr);
	}
	
	//SELECT MENU 2: 가격 범위 내 케이크별 주문 횟수와 가격 조회(인기 케이크 확인 가능
	public void selectCakePopularity() {
		int minPrice, maxPrice;
		
		while (true) {
            System.out.print("최소 가격을 입력하세요: ");
            String input = scanner.nextLine().trim();
            try {
                minPrice = Integer.parseInt(input);
                if (minPrice < 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("유효한 정수를 입력해주세요. (0 이상의 숫자)");
            }
        }
		
		while (true) {
            System.out.print("최대 가격을 입력하세요: ");
            String input = scanner.nextLine().trim();
            try {
                maxPrice = Integer.parseInt(input);
                if (maxPrice < minPrice) {
                    System.out.println("최대 가격은 최소 가격보다 크거나 같아야 합니다.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("유효한 정수를 입력해주세요.");
            }
        }
		
		System.out.println("입력된 값: " + minPrice + "," + maxPrice);
	}
	//SELECT MENU 3: 고객별 최근 주문 조회(날짜 상 최근순으로 정렬)
	public void selectCustomerOrder() {
		String customerID;
		
		 while (true) {
	            System.out.print("고객 ID를 입력하세요: ");
	            customerID= scanner.nextLine().trim();

	            // 입력이 null, 빈 문자열, 숫자만 있는 경우 예외 처리
	            if (customerID.isEmpty()) {
	                System.out.println("ID를 입력해주세요.");
	            } 
	            else {
	                break;
	            }
	        }
		 
		 System.out.println("입력된 값: " + customerID);
	}
	//SELECT MENU 4: 케이크 사이즈 별 선택 가능 케이크와 옵션
	public void selectCakeSize() {
		String cakeSize;
		
		 while (true) {
	            System.out.print("케이크 사이즈를 입력하세요 (S, M, L): ");
	            cakeSize = scanner.nextLine().trim().toUpperCase();

	            // 유효한 값인지 검사
	            if (cakeSize.isEmpty()) {
	                System.out.println("사이즈를 입력해주세요.");
	            } else if (!cakeSize.equals("S") && !cakeSize.equals("M") && !cakeSize.equals("L")) {
	                System.out.println("잘못된 사이즈입니다. S, M, L 중 하나만 입력해주세요.");
	            } else {
	                break;
	            }
	        }

	        System.out.println("입력된 케이크 사이즈: " + cakeSize);
	}
	//SELECT MENU 5: 일정 가격 이상 주문한 고객 리스트
	public void selectMinOrdor() {
		int minAmount = 0;
		
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

        System.out.println("입력된 최소 주문 금액: " + minAmount);
	}

}
