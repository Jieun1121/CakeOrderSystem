package cakeapp;

import java.util.*;

public class Insert {
	
	
	
	Scanner sc = new Scanner(System.in);
	//INSERT MENU 1: '케이크 이름', '커스터마이징 상세 옵션' 입력 받아 케이크 주문	
	public void insertCakeOrder() {
		String cakeName;
		String customizing;
		System.out.println("=== 케이크 주문 추가 ===");
		while(true) {
			try {	
				System.out.print("케이크 이름을 입력하세요 :");
				cakeName = sc.nextLine();	
				if (cakeName.trim().isEmpty() || cakeName == null) {
	                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				break;
			}catch(InputMismatchException e){
				System.out.println("문자열을 입력하세요");
				sc.nextLine();
			}
		}	
		
		while(true) {
			try {	
				System.out.print("커스터마이징 상세 옵션을 입력하세요 :");
				customizing =  sc.nextLine();	
				if (customizing.trim().isEmpty() || customizing == null) {
	                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				break;
			}catch(InputMismatchException e){
				System.out.println("문자열을 입력하세요");
				sc.nextLine();
			}
		}
		
		System.out.println(cakeName + "," + customizing +"주문 추가되었습니다.");
	}
	// INSERT MENU 2: 결제 방법 입력받아 내역 추가
	
	void insertPayment() {
		String payment;
		System.out.println("=== 케이크 주문 결제 ===");
		
		while(true) {
			try {	
				System.out.println("원하시는 결제 방식을 입력하세요: ");
				payment =  sc.nextLine();	
				if (payment.trim().isEmpty() || payment == null) {
	                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				break;
			}catch(InputMismatchException e){
				System.out.println("문자열을 입력하세요");
				sc.nextLine();
			}
		}
		System.out.println(payment+"으로 결제되었습니다.");
	}
	// 추가 INSERT 메뉴: 고객 정보 입력 받아 회원 등록 , 이름, 폰넘버, 주소
	
	void insertCustomer() {
		String customerName;
		String customerPhoneNumber;
		String customerAddress;
		
		System.out.println("=== 신규 회원 등록 ===");
		
		while(true) {
			try {	
				System.out.println("이름을 입력하세요: ");
				customerName =  sc.nextLine();	
				if (customerName.trim().isEmpty() || customerName == null) {
	                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				break;
			}catch(InputMismatchException e){
				System.out.println("문자열을 입력하세요");
				sc.nextLine();
			}
		}
		
		while(true) {
			try {	
				System.out.println("전화번호를 입력하세요: ");
				customerPhoneNumber =  sc.nextLine();	
				if (customerPhoneNumber.trim().isEmpty() || customerPhoneNumber == null) {
	                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				break;
			}catch(InputMismatchException e){
				System.out.println("문자열을 입력하세요");
				sc.nextLine();
			}
		}
		
		while(true) {
			try {	
				System.out.println("주소를 입력하세요: ");
				customerAddress =  sc.nextLine();	
				if (customerAddress.trim().isEmpty() || customerAddress == null) {
	                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
	                continue; // 다시 입력 받기
	            }
				break;
			}catch(InputMismatchException e){
				System.out.println("문자열을 입력하세요");
				sc.nextLine();
			}
		}
		System.out.println(customerName +", " +customerPhoneNumber +", " +customerAddress +"회원 추가 완료되었습니다.");
	}

}
