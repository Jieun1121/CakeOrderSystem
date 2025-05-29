package cakeapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Menu {
	Scanner key = new Scanner(System.in);
	
	// 2 insert menus, 5 select menus, 2 update menus, 2 delete menus using database queries
	void menuPrint() {
		System.out.println("~~~ Cake Ordering System Menu ~~~");
		System.out.println("1. insert menus");
		System.out.println("2. select menus");
		System.out.println("3. update menus");
		System.out.println("4. delete menus");
		System.out.println("5. 종료");
		System.out.print("메뉴 입력 : ");		
	}
	
	private int getMenu() {
		int menu;			
		while(true) {
			try {	
				menuPrint();
				menu = key.nextInt();
				String ex = key.nextLine();
				return menu;
			}catch(InputMismatchException e){
				System.out.println("정수만 입력하세요");
				key.nextLine();
			}
			
		}	
	}
	
	public void run() {
		System.out.println("~~~ Cake Ordering System을 시작합니다. ~~~ ");
				
		while(true) {
			int choice = getMenu();
			
			switch(choice) {
			case 1: //2 insert menus 
				insertRun();		
				break;	
			case 2: //5 select menus
				selectRun();
				break;				
			case 3: //2 update menus
				updateRun();
				break;				
			case 4: //2 delete menus 
				deleteRun();
				break;			
			case 5:
				System.out.println("시스템을 종료합니다.");
				return;
			default:
				System.out.println("범위에 맞는 정수를 입력하세요.");
			}
			System.out.println(" ");
		}
	}
	
	//insert 
	void insertMenuPrint() {
		System.out.println("--- Insert Menu ---");
		System.out.println("1. 케이크 주문 추가"); //INSERT MENU 1: '케이크 이름', '커스터마이징 상세 옵션' 입력 받아 케이크 주문
		System.out.println("2. 케이크 주문 결제"); // INSERT MENU 2: 결제 방법 입력받아 내역 추가
		System.out.println("3. 신규 회원 등록"); // 추가 INSERT 메뉴: 고객 정보 입력 받아 회원 등록
		System.out.println("4. (뒤로가기)");
		System.out.print("메뉴 입력 : ");		
	}
	
	private int getInsertMenu() {
		int i_menu;			
		while(true) {
			try {	
				insertMenuPrint();
				i_menu = key.nextInt();
				String ex = key.nextLine();
				
				if (i_menu < 1 || i_menu > 4) {
	                System.out.println("범위에 맞는 정수를 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				
				return i_menu;
			}catch(InputMismatchException e){
				System.out.println("정수만 입력하세요");
				key.nextLine();
			}
		}	
	}
	
public void insertRun() {
			int choice = getInsertMenu();
			Insert insert = new Insert();
			
			switch(choice) {
			case 1:  //INSERT MENU 1: '케이크 이름', '커스터마이징 상세 옵션' 입력 받아 케이크 주문
				insert.insertCakeOrder();
				break;	
			case 2: // INSERT MENU 2: 결제 방법 입력받아 내역 추가
				insert.insertPayment();
				break;				
			case 3: // 추가 INSERT 메뉴: 고객 정보 입력 받아 회원 등록
				insert.insertCustomer();
				break;		
			case 4:
				System.out.println("메인 화면으로 돌아갑니다.");
				break;
		}
	}
	
	//select
	void selectMenuPrint() {
		System.out.println("--- Select Menu ---");
		System.out.println("1. 기간 별 고객 소비 조회"); //SELECT MENU 1: 특정 기간 동안 주문한 내역 조회 (기간 별 소비 내역)
		System.out.println("2. 가격 별 주문 빈도 조회"); //SELECT MENU 2: 가격 범위 내 케이크별 주문 횟수와 가격 조회(인기 케이크 확인 가능
		System.out.println("3. 고객 최근 주문 조회"); //SELECT MENU 3: 고객별 최근 주문 조회(날짜 상 최근순으로 정렬)
		System.out.println("4. 사이즈별 케이크 옵션 조회"); //SELECT MENU 4: 케이크 사이즈 별 선택 가능 케이크와 옵션
		System.out.println("5. 단골 고객 조회"); //SELECT MENU 5: 일정 가격 이상 주문한 고객 리스트
		System.out.println("6.-뒤로가기");
		System.out.print("메뉴 입력 : ");		
	}
	
	private int getSelectMenu() {
		int s_menu;			
		while(true) {
			try {	
				selectMenuPrint();
				s_menu = key.nextInt();
				String ex = key.nextLine();
				
				if (s_menu < 1 || s_menu > 6) {
					System.out.println("범위에 맞는 숫자를 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				
				return s_menu;
			}catch(InputMismatchException e){
				System.out.println("정수만 입력하세요");
				key.nextLine();
			}
		}	
	}

	
public void selectRun() {	
			int choice = getSelectMenu();
			Select select = new Select();
			
			switch(choice) {
			case 1: //SELECT MENU 1: 특정 기간 동안 주문한 내역 조회 (기간 별 소비 내역)
				select.selectDateOrder();
				break;	
			case 2: //SELECT MENU 2: 가격 범위 내 케이크별 주문 횟수와 가격 조회(인기 케이크 확인 가능
				select.selectCakePopularity();
				break;				
			case 3: //SELECT MENU 3: 고객별 최근 주문 조회(날짜 상 최근순으로 정렬)
				select.selectCustomerOrder();	
				break;				
			case 4: //SELECT MENU 4: 케이크 사이즈 별 선택 가능 케이크와 옵션
				select.selectCakeSize();
				break;			
			case 5: //SELECT MENU 5: 일정 가격 이상 주문한 고객 리스트
				select.selectMinOrdor();
				break;
			case 6:
				System.out.println("메인 화면으로 돌아갑니다.");
				break;
				
			}
	}
	
	//update
	void updateMenuPrint() {
		System.out.println("--- Update Menu ---");
		System.out.println("1. 주문 상태 변경"); //UPDATE MENU 1: 주문 상태 변경
		System.out.println("2. 고객 주소 변경"); //UPDATE MENU 2: 고객 주소 변경
		System.out.println("3.-뒤로가기");
		System.out.print("메뉴 입력 : ");		
	}
	
	private int getUpdateMenu() {
		int u_menu;			
		while(true) {
			try {	
				updateMenuPrint();
				u_menu = key.nextInt();
				String ex = key.nextLine();
				
				if (u_menu < 1 || u_menu > 3) {
					System.out.println("범위에 맞는 숫자를 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				
				return u_menu;
			}catch(InputMismatchException e){
				System.out.println("정수만 입력하세요");
				key.nextLine();
			}
		}	
	}
	
public void updateRun() {		
			int choice = getUpdateMenu();
			Update update = new Update();
			try (Connection conn = DatabaseUtil.getConnection()) { // DB 연결
        switch (choice) {
            case 1:
                update.updateOrderState(conn); 
                break;
            case 2:
                update.updateCustomerAddress(conn); 
                break;
            case 3:
                System.out.println("메인 화면으로 돌아갑니다.");
                break;
        }
    } catch (SQLException e) {
        System.out.println("DB 연결 실패: " + e.getMessage())
;	}
    }
	
	//delete
	void deleteMenuPrint() {
		System.out.println("--- Delete Menu ---");
		System.out.println("1. 고객 회원 탈퇴"); 
		System.out.println("2. 취소된 주문 삭제");
		System.out.println("3. -뒤로가기");
		System.out.print("메뉴 입력 : ");		
	}
	
	private int getDeleteMenu() {
		int d_menu;			
		while(true) {
			try {	
				deleteMenuPrint();
				d_menu = key.nextInt();
				String ex = key.nextLine();
				
				if (d_menu < 1 || d_menu > 3) {
					System.out.println("범위에 맞는 숫자를 입력하세요.");
	                continue; 
	            }
				
				return d_menu;
			}catch(InputMismatchException e){
				System.out.println("정수만 입력하세요");
				key.nextLine();
			}
		}	
	}
	
public void deleteRun() {
			int choice = getDeleteMenu();
			Delete delete = new Delete();
			
			try (Connection conn = DatabaseUtil.getConnection()) { 
        switch (choice) {
            case 1:
                delete.deleteCustomer(conn); 
                break;
            case 2:
                delete.deleteCancelledOrder(conn); 
                break;
            case 3:
                System.out.println("메인 화면으로 돌아갑니다.");
                break;
        }
    } catch (SQLException e) {
        System.out.println("DB 연결 실패: " + e.getMessage());
    }
}
}