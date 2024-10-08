package miniProj1;

import java.util.List;

public class MemberService {
	//Members클래스는 값을 담을 객체니까 null로 선언 후 
	//아래에서 필요시 members에 new로 객체를 생성해서 쓰면 됨
	//MembersDB는 함수만 사용하기 위함이니까 객체 생성을 처음만 해주면 됨
	Members members = null; 
	MembersDB mDB = new MembersDB();
	//Main에 있는 스캐너 사용을 위한 변수 선언
	String cmd = null;

	public void signup() {
		//회원가입 화면
		boolean bool = true;

		while(bool) {
			System.out.println("[[[회원가입 화면입니다.]]]");
			System.out.print("-아이디 : ");
			String mid = Main.scanner.nextLine();
			members = new Members();
			members.setMid(mid);
			
			List<Members> idList = mDB.mSelectMembers(members);
			if (idList != null && !idList.isEmpty()) {
				System.out.println("존재하는 ID입니다. 다시 입력해주세요.");
				System.out.println();
				continue;
			}
			System.out.print("-비밀번호 : ");
			String mpw = Main.scanner.nextLine();
			System.out.print("-이름 : ");
			String mname = Main.scanner.nextLine();
			System.out.print("-전화번호('-'포함) : ");
			String mhp = Main.scanner.nextLine();
			System.out.print("-주소(ex.서울) : ");
			String maddr = Main.scanner.nextLine();
			System.out.print("-성별(여/남) : ");
			String msex = Main.scanner.nextLine();
			
			
			members = new Members();
			members.setMid(mid);
			members.setMpw(mpw);
			members.setMname(mname);
			members.setMhp(mhp);
			members.setMaddr(maddr);
			members.setMsex(msex);
			
			mDB.mInsert(members);

			System.out.println();
			System.out.println("1. 가입");
			System.out.println("2. 다시 입력");
			System.out.println("3. 이전 화면으로");
			System.out.println();
			System.out.print(">>>원하시는 번호를 입력해주세요: ");
			cmd = Main.scanner.nextLine();
			System.out.println();

			switch (cmd) {
			case "1":
				System.out.println("가입을 축하합니다!");
				System.out.println();
				bool = false;
				break;

			case "2":
				//switch문을 종료하고 회원가입 첫 화면으로 돌아감
				System.out.println("다시 입력해주세요.");
				continue;

			case "3":
				//while문을 종료하고 main 화면으로 돌아감
				bool = false;
				break;
			}
		}
	}

	public void login() {
		//로그인 화면
		boolean bool = true;
		while(bool) {
			System.out.println("[[[로그인 화면입니다.]]]");
			System.out.print("-아이디 : ");
			String mid = Main.scanner.nextLine();
			System.out.print("-비밀번호 : ");
			String mpw = Main.scanner.nextLine();

			members = new Members();
			members.setMid(mid);
			members.setMpw(mpw);
			List<Members> loginList = mDB.mSelectMembers(members);


			System.out.println();
			System.out.println("1. 로그인");
			System.out.println("2. 다시 입력");
			System.out.println("3. 이전 화면으로");
			System.out.println();
			System.out.print(">>>원하시는 번호를 입력해주세요: ");
			cmd = Main.scanner.nextLine();
			System.out.println();

			switch (cmd) {
			case "1":
				if(loginList == null || loginList.isEmpty()) {
					System.out.println("존재하지 않는 아이디 혹은 비밀번호 입니다.");
					System.out.println();
					continue;
				}
				
				//내 회원 정보에 로그인 일시 기록하는 함수호출
				mDB.mlogin(loginList.get(0));
				
				// 로그인 일시 업데이트 후 재조회(로그인 후 내 회원정보에가면 로그인일시가 기록되어 있음)
				loginList = mDB.mSelectMembers(members);
				
				//로그인이 유저와 관리자를 구분해서 다른 화면을 띄움
				if (mid.equals("admin")) {
					adminView(loginList);
				}else {
					userView(loginList);
				}			
				bool = false;
				break;

			case "2":
				//switch문을 종료하고 로그인 화면으로 돌아감
				System.out.println("다시 입력해주세요.");
				continue;

			case "3":
				//while문을 종료하고 main 화면으로 돌아감
				bool = false;
				break;
			}
		}
	}

	public void searchId() {
		//id 찾기 화면
		System.out.println("[[[아이디 찾기]]]");
		System.out.print(">>>이름을 입력해주세요: ");
		String mname = Main.scanner.nextLine();
		System.out.print(">>>전화번호를 입력해주세요: ");
		String mhp = Main.scanner.nextLine();
		System.out.println();
		
		members = new Members();
		members.setMname(mname);
		members.setMhp(mhp);
		List<Members> loginList = mDB.mSelectMembers(members);
		
		if(loginList == null || loginList.isEmpty()) {
			System.out.println("일치하는 정보가 없습니다. 다시 입력해주세요.");
			System.out.println();
		
		}else {
			System.out.println(loginList.get(0).getMname() + "님의 아이디는"
					+ loginList.get(0).getMid() + "입니다.");
			System.out.println();
		}
		
	}

	public void resetPw() {
		//비밀번호 초기화 화면
		System.out.println("[[[비밀번호 초기화]]]");
		System.out.print(">>>아이디를 입력해주세요: ");
		String mid = Main.scanner.nextLine();
		System.out.print(">>>이름을 입력해주세요: ");
		String mname = Main.scanner.nextLine();
		System.out.println();
		
		members = new Members();
		members.setMid(mid);
		members.setMname(mname);
		List<Members> mResetList = mDB.mResetPw(members);
		
		if(mResetList != null && !mResetList.isEmpty()) {
			System.out.println(">>>" + mResetList.get(0).getMid() + "님, 비밀번호 초기화가 완료되었습니다. 비밀번호는 0000입니다.");
			System.out.println();
			
		}else {
			System.out.println("일치하는 정보가 없습니다. 다시 입력해주세요.");
			System.out.println();
		}
		
		
		
		
	}
	
	public void infoModify(List<Members> loginList) {
		//회원정보 업데이트
		System.out.print(">>>비밀번호를 입력해주세요: ");
		cmd = Main.scanner.nextLine();
		
		if (cmd.equals(loginList.get(0).getMpw())) {
			System.out.print("-이름 : ");
			String mname = Main.scanner.nextLine();
			System.out.print("-전화번호('-'포함) : ");
			String mhp = Main.scanner.nextLine();
			System.out.print("-주소(ex.서울) : ");
			String maddr = Main.scanner.nextLine();
			System.out.println();
			
			//로그인 시 리스트 0번지에 기록된 유저정보를 members에 넣고 회원정보 수정 후 업데이트 함수 호출 
			members = loginList.get(0);
			members.setMname(mname);
			members.setMhp(mhp);
			members.setMaddr(maddr);
			mDB.mUpdate(members);
			
			loginList.set(0, members);
			
			System.out.println("수정이 완료되었습니다.");
			System.out.println();
			
			//수정된 값 리스트에 업데이트 후 내 정보 보기 클릭시 출력
			//해야되는데 안 됨 위에서 수정한 세개만 수정되어 있고, 나머진 null과 0임
//			members.getNo();
//			members.getMname();
//			members.getMhp();
//			members.getMaddr();
//			members.getMsex();
//			loginList.set(0, members);
			
		}
	}

	public void userView(List<Members> loginList) {
		//일반 사용자가 로그인 했을 떄 나오는 화면
		boolean bool = true;

		while (bool) {
	
			System.out.println("[[["+ loginList.get(0).getMname() +"님, 반갑습니다!]]]");
			System.out.println("1. 나의 정보 확인");
			System.out.println("2. 게시물 목록");
			System.out.println("3. 로그아웃");
			System.out.println("4. 종료");
			System.out.println();
			System.out.print(">>>원하시는 번호를 입력해주세요: ");
			cmd = Main.scanner.nextLine();
			System.out.println();

			switch (cmd) {
			case "1":
				System.out.println("[[[나의 정보 확인]]]");
				System.out.println("아이디      이름       전화번호         주소     성별          로그인일시            로그아웃일시");
				loginList.get(0).mprint();
				
				System.out.println();
				System.out.println("1. 회원 정보 수정");
				System.out.println("2. 회원 탈퇴");
				System.out.println("3. 이전 화면으로");
				System.out.println();
				System.out.print(">>>원하시는 번호를 입력해주세요: ");
				cmd = Main.scanner.nextLine();
				System.out.println();
				
				switch (cmd) {
				case "1":
					infoModify(loginList);
					continue;
					
				case "2":
					System.out.print(">>>비밀번호를 입력해주세요: ");
					cmd = Main.scanner.nextLine();
					if (cmd.equals( loginList.get(0).getMpw())) {
						mDB.mDelete(loginList.get(0));
					}
					System.out.println();
					System.out.println("회원 탈퇴가 완료되었습니다.");
					System.out.println();
					break;
				case "3":
					//로그아웃 일시 기록 함수 호출
					mDB.mlogout(loginList.get(0));
					//이전화면으로 가기(OOO님 반갑습니다! 화면)
					continue;
				}
				
				bool = false;
				break;

			case "2":			
				BoardService bs = new BoardService();
				bs.list(loginList.get(0));
				continue;

			case "3":
				//로그아웃시 로그아웃 시간을 찍고 while문을 종료하고 main 화면으로 돌아감
				mDB.mlogout(loginList.get(0));
				System.out.println("로그아웃 완료. 메인화면으로 돌아갑니다.");
				bool = false;
				break;
				
			case "4":
				System.out.println("프로그램 종료");
				System.exit(0);
				break;
			}
		}
	}
	
	public void adminView(List<Members> loginList) {
		//관리자가 로그인 했을 때 보이는 화면
		boolean bool = true;

		while (bool) {
			System.out.println("[[[관리자님, 반갑습니다!]]]");
			System.out.println("1. 나의 정보 확인");
			System.out.println("2. 게시물 목록");
			System.out.println("3. 회원 목록");
			System.out.println("4. 로그아웃");
			System.out.println("5. 종료");
			System.out.println();
			System.out.print(">>>원하시는 번호를 입력해주세요: ");
			cmd = Main.scanner.nextLine();
			System.out.println();

			switch (cmd) {
			case "1":
				System.out.println("[[[나의 정보 확인]]]");
				System.out.println("아이디      이름       전화번호         주소     성별          로그인일시            로그아웃일시");
				loginList.get(0).mprint();
	
				System.out.println();
				System.out.println("1. 회원 정보 수정");
				System.out.println("2. 회원 탈퇴");
				System.out.println("3. 이전 화면으로");
				System.out.println();
				System.out.print(">>>원하시는 번호를 입력해주세요: ");
				cmd = Main.scanner.nextLine();
				
				switch (cmd) {
				case "1":
					infoModify(loginList);
					continue;

				case "2":
					//관리자인 경우는 회원 탈퇴시 비밀번호 없이 바로 탈퇴
					mDB.mDelete(loginList.get(0));
					System.out.println();
					System.out.println("회원 탈퇴가 완료되었습니다.");
					System.out.println();
					break;
					
				case "3":
					//이전화면으로 가기(OOO님 반갑습니다! 화면)
					continue;
				}
				
				bool = false;
				break;

			case "2":			
				BoardService bs = new BoardService();
				bs.list(loginList.get(0));
				continue;

			case "3":
				System.out.println("[[[회원 목록]]]");
				System.out.println("번호  아이디       이름         전화번호        주소   성별");
//				System.out.println(" 아이디   이름       전화번호        주소   성별");
				members = new Members();
				List<Members> mAllList = mDB.mSelectMembers(members);
				mAllList.stream().forEach(Members::mAllprint);
				System.out.println();
				break;
				
			case "4":
				//로그아웃시 로그아웃 시간을 찍는 함수 호출
				mDB.mlogout(loginList.get(0));
				//while문을 종료하고 main 화면으로 돌아감
				System.out.println("로그아웃 되었습니다.");
				System.out.println();
				bool = false;
				break;
				
			case "5":
				System.out.println("프로그램 종료");
				System.exit(0);
				break;
			}
		}
		
	}

}
