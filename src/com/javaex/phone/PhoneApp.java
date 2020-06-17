package com.javaex.phone;

import java.util.List;
import java.util.Scanner;



public class PhoneApp {

	public static void main(String[] args) {
		
		
		PhoneDao phonedao = new PhoneDao();
		
		//스캐너
		Scanner sc = new Scanner(System.in);
		
		//리스트 가져오기
		List<PersonVo> keyList;
		
		//시작화면
		System.out.println("************************************************");
		System.out.println("*             전화번호 관리 프로그램           *");
		System.out.println("************************************************");

		//while시작
		boolean run = true;
		while(run) {
			
			//메뉴출력
			System.out.println("");
			System.out.println("1.리스트 2.등록 3.수정 4.삭제 5.검색 6.종료");
			System.out.println("---------------------------------------------");
			System.out.print(">메뉴번호: ");
			
			// 메뉴 입력
			int menuNum = sc.nextInt();
			sc.nextLine();
			
			// switch시작
			switch (menuNum) {
			
			case 1: 
				System.out.println("<1.리스트>");
				
				//화면에 출력
				phonedao.getPhoneList();
				break;
			
			case 2: 
				System.out.println("<2.등록>");
				// 이름받기
				System.out.print("이름 > ");
				String name = sc.nextLine();
				// 휴대전화 받기
				System.out.print("휴대전화 > ");
				String hp = sc.nextLine();
				// 회사번호받기
				System.out.print("회사번호 > ");
				String company = sc.nextLine();
				
				PersonVo vo01 = new PersonVo(name, hp, company);
			    phonedao.personInsert(vo01);
			    
			    System.out.println("[ 1건 등록되었습니다.]");
			    
			    break;
								
			case 3: 
				System.out.println("<3.수정>");
				System.out.print(">번호 : ");
				int no = sc.nextInt();
				sc.nextLine();  
				// 이름받기
				System.out.print("이름 > ");
				String chname = sc.nextLine();
				// 휴대전화 받기
				System.out.print("휴대전화 > ");
				String chhp = sc.nextLine();
				// 회사번호받기
				System.out.print("회사번호 > ");
				String chcompany = sc.nextLine();
				
				PersonVo vo02 = new PersonVo(no,chname,chhp,chcompany);
				phonedao.personUpdate(vo02);
				
				System.out.println("[ 1건 수정되었습니다.]");
				
				break;
				
			case 4: 
				System.out.println("<4.삭제>");
				System.out.print(">번호 : ");
				int no2 = sc.nextInt();
				
				phonedao.personDelete(no2);
				
				System.out.println("[ 1건 삭제되었습니다.]");
				
				break;
				
			case 5: 
				System.out.println("<5.검색>");
				System.out.print(">검색어: ");
				String keyword = sc.next();
				
				keyList = phonedao.personSh(keyword);
				
				for(PersonVo vo : keyList) {
					System.out.println(vo.getPersonId()+". "+vo.getName()+
							" "+vo.getHp()+" "+vo.getCompany());			
				}
				
				break;	
			case 6:
				run = false;
				break;
			default:
				System.out.println("[다시 입력해주세요.]");
				break;		
			}//switch끝			
		}//while끝
		sc.close();
		
		//종료
		System.out.println("************************************************");
		System.out.println("*                   감사합니다                 *");
		System.out.println("************************************************");

	}

}
