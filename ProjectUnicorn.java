package com.java.projectUnicorn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

class Member implements java.io.Serializable {
//   개인정보 private 지정해, member class 내의 메소드로만 접근/수장 가능하게 설정,
	private String id;
	private String pw;
	private String name;
	private String phoneNumber;
	private int purchaseAmount; // 고객 총 구입액
	private int grossSales; // 가게 총 매출액
	private int couponPoint; // 고객 쿠폰포인트
	Table table; // 예약한 테이블

//  생성자로 회원가입 by 권성직
	public Member(String id, String pw, String name, String phoneNumber) {
		if (id.contains("@") && id.contains(".")) {
			this.id = id;
		} else {
			System.out.println("이메일은 '@'와 '.'를 포함해야 합니다.");
		}

		if (pw.length() >= 10 && (pw.contains("~") || pw.contains("!") || pw.contains("@") || pw.contains("#")
				|| pw.contains("$") || pw.contains("%") || pw.contains("^") || pw.contains("&") || pw.contains("*"))) {
			this.pw = pw;
		} else {
			System.out.println("비밀번호는 10자리 이상, 특수문자(~!@#$%^&*)를 하나 이상 포함해야 합니다.");
		}

		if (name.equals("")) {
			System.out.println("이름을 공백으로 입력할 수 없습니다.");
		} else {
			this.name = name;
		}

		if (phoneNumber.equals("")) {
//			if (phoneNumber == Integer.valueOf("")) {
			System.out.println("휴대폰번호를 공백으로 입력할 수 없습니다.");
		} else {
			this.phoneNumber = phoneNumber;
		}

		this.purchaseAmount = 0;
		this.couponPoint = 0;
	}

//	가입한 회원 파일에 저장 by 권성직
	void saveMemberInfo(List<Member> list) {

		try (ObjectOutputStream oo = new ObjectOutputStream(
				new FileOutputStream("D:\\java\\JavaBook\\src\\com\\java\\projectUnicorn\\Member.data"))) {
			for (int i = 0; i < list.size(); i++) {
				oo.writeObject(list.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("");
		}

//		파일에 저장된 회원 정보 불러오기 by 권성직
//		try (ObjectInputStream oi = new ObjectInputStream(
//				new FileInputStream("D:\\java\\JavaBook\\src\\com\\java\\projectUnicorn\\Member.data"))) {
//
//			boolean ro = true;
//			Member member;
//			while (ro) {
//				member = (Member) oi.readObject();
//				if (member != null) {
//					list.add(member);
//				} else {
//					ro = false;
//				}
//			}
//		}
//
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			// e.printStackTrace();
//		}
//		System.out.println("불러오기 완료!!!");
	}

//  로그인 by 권성직
	void logIn(String id, String pw, List<Member> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("회원수는 " + list.size() + "명 입니다.");
			if (list.get(i).id.equals(id) && list.get(i).pw.equals(pw)) {
				System.out.println(list.get(i).name + "님 로그인 하셨습니다.");
			} else {
				// System.out.println(list.get(i).name);
				// System.out.println("회원목록에 존재하지 않습니다.");
			}
		}
	}

//  주문 : 회원의 구입금액 합산, 커피숍 매출 합산 by 권성직
	void addPurchasedAmount(String id, String pw, int coffeeItem, List<Member> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).id.equals(id) && list.get(i).pw.equals(pw)) {
				System.out.println(list.get(i).name + "님께서 " + coffeeItem + "원 결제 하셨습니다.");

				list.get(i).purchaseAmount += coffeeItem;
				System.out.println(list.get(i).name + "님의 지금까지 총 결제 금액은 " + list.get(i).purchaseAmount + "원 입니다.");
				grossSales += coffeeItem;
				System.out.println("커피숍의 총 매출은 " + grossSales + "원 입니다.");
			}
		}
	}

//	관리자모드 : 전체회원검색 by 신중하	
	void searchList(List<Member> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).id);
			System.out.println(list.get(i).pw);
			System.out.println("=============");
			System.out.println(list.get(i).name);
			System.out.println(list.get(i).phoneNumber);
		}
	}

//  관리자모드 : 회원삭제 by 신중하	  
	void delete(List<Member> list) {
		Scanner s = new Scanner(System.in);
		System.out.println("삭제할 번호를 입력하시오");
		int dnum = s.nextInt();
		list.remove(s);
	}

//  회원이 자기정보수정 by 이동우
	void changeInfo(String id, String pw, List<Member> list) {
		for (ListIterator<Member> litr = list.listIterator(); litr.hasNext();) {
			if (litr.next().id.equals(id) && litr.next().pw.equals(pw)) {
				System.out.println(litr.previous());
				System.out.println(list.size());
				Scanner sc = new Scanner(System.in);
				System.out.println("=====회원수정=====");
				System.out.print("바꿀 이메일 주소를 입력해 주세요(계정형식으로)> ");
				sc.nextLine();
				id = sc.nextLine();
				System.out.print("바꿀 비밀번호(10자리 이상, 특수기호 하나이상 포함)를 입력해 주세요> ");
				pw = sc.nextLine();
				System.out.print("바꿀 이름을 입력해 주세요> ");
				name = sc.nextLine();
				System.out.print("바꿀 휴대폰 번호를 -나 공백없이 숫자만 입력해 주세요> ");
				phoneNumber = sc.nextLine();
				// Member member = litr.previous();
				// System.out.println(member.id);
				// System.out.println(member.pw);
				litr.set(new Member(id, pw, name, phoneNumber));
			}
		}
	}
}
