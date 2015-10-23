package chatting;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Provider.Service;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;


public class ChatServer extends JFrame implements ActionListener, Runnable {
	public static void main(String[] args) {
		ChatServer cs = new ChatServer();
		new Thread(cs).start();
		
	}
	//채팅은 통신이라 스레드가 필요함
	//자바는 다중상속 안됨
	private static final long serialVersionUID = 1L; //직렬화
	/**
	 * 직렬화 : 데이터들을 한줄로 정렬 시켜라 더 빨라질것이다.=>스트림
	 */
	Vector<Service> vec; //접속자를 담아두는 자료구조
	JTextArea txt;
	JButton btn;
	
	public ChatServer() {
		super("서버");//프레임 이름 주고
		//부품준비
		vec = new Vector<Service>();
		txt = new JTextArea();
		btn = new JButton("서버종료");
		btn.addActionListener(this);
		
		//조립
		this.add(txt,"Center"); // BorderLayout.CENTER 텍스트는 중앙에 놓고
		this.add(btn,"South"); // BorderLayout.SOUTH 버튼은 아래에 놓고
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //닫기 버튼줘라
		this.pack(); //본드로 붙여라. 컴포넌트끼리 떨어지지 않게
		this.setBounds(50, 100, 300, 600); // 50 x좌표, 100 y좌표, 300픽셀 가로크기, 600픽셀 세로크기 
		this.setVisible(true); //완성했으면 보여줘라
	}
	//"서버종료" 라는 버튼을 클릭했을 경우 이벤트 발생(리스너의 기능)
	@Override
	public void actionPerformed(ActionEvent e) {
	//	if (e.getSource() == btn) { //이벤트(클릭)를 발생시킨 객체가 btn이라면
	//		}//else if (e.getSource() == btn2) {}
		switch (e.getActionCommand()) { 
		case "서버종료":
			System.exit(0); //이 프로그램을 종료시켜라
			break;

		default:
			break;
		}
		
	}
	@Override
	public void run() {
		ServerSocket ss = null; //지역변수는 초기화
		try {//외부 시스템과 접속할때 트라이캐치 뜸
			ss = new  ServerSocket(5555);
			// 5555는 서버 접속포트 번호 : 포트 = 1가 3번지 주소
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		while (true) {
			txt.append("클라이언트 접속 대기중\n");
			try {
				Socket s = ss.accept();
				//클라이언트의 소켓은 서버소켓이 허용해야 생성된다.
				//shallow copy
				txt.append("클라이언트 접속 처리\n");
				Service cs = new Service(s);
				cs.start(); // 클라이언트의 스레드가 작동하는것
				cs.nickName= cs.in.readLine();
				cs.sendMessageAll("/c"+cs.nickName); //이미 접속되어 있는 다른 클라이언트에 나를 알리고
				vec.add(cs);// 접속된 클라이언트를 백터에 추가함
				for (int i = 0; i < vec.size(); i++) {
					Service service = vec.elementAt(i);
					service.sendMessage("/c"+service.nickName);
					
				}
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			} 
			
		}
		
	}
	//이너클래스
	class Service extends Thread{ //접속자 객체를 생성하는 기능 // 서비스 서버에 접속하는 클라이언트
		String nickName = "guest"; //대화명
		BufferedReader in; //*Reader, *Writer는 2바이트씩 처리하므로 문자에 최적화(빠르다)
		OutputStream out;
		//*Stream 은 1바이트씩 처리하므로 이미지, 음악, 파일에 최적화
		Socket s; // 클라이언트쪽 단자 
		Calendar now = Calendar.getInstance(); //싱글톤
		//디폴트 생성자를 만들지 않고, 파라미터가 존재하는 생성자를 만든다면
		//반드시 객체를 생성할 때 소켓을 연결해야 한다는 의미가 된다.
		public Service(Socket s) {
			this.s = s;
			try {
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));//2바이트만 처리했는데 브릿지 패턴으로인해 1바이트 2바이트 다받음
				out = s.getOutputStream();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		@Override
		public void run() {
			while (true) {
				try {
					String msg = in.readLine();
					txt.append("전송메시지 : "+msg+"\n");
					if (msg == null) { //더이상 메시지가 없으면 종료
						return;
					}
					if (msg.charAt(0) =='/') {	
						char temp = msg.charAt(1);
						switch (temp) {
						case 'n': // n: 이름 바꾸기
							if (msg.charAt(2) == ' ') {
								sendMessageAll("\n"+nickName+"-"+msg.substring(3).trim());
								//" 위의 문장중에서 -는 닉네임과 새이름을분리하기 위해 임의로 설정한 기호"
								this.nickName = msg.substring(3).trim();
								
							}
							break;
						case 'q': //클라이언트 퇴장
							for (int i = 0; i < vec.size(); i++) {
								Service svc = (Service) vec.get(i);
								if (nickName.equals(svc.nickName)) {
									vec.remove(i);
									break;
									
								}
								
							}
							sendMessage(">"+nickName+" 님이 퇴장하셨습니다.");
							in.close(); //인풋네트워크 해제
							out.close(); // 아웃풋 네트워크 해제
							s.close(); // 소켓 해제
							return; //종료
						case 's' :  //귓속말
							String name = msg.substring(2,msg.indexOf('-')).trim();
							for (int i = 0; i <vec.size(); i++) {
								Service cs3 = (Service) vec.elementAt(i);
								if (name.equals(cs3.nickName)) {
									cs3.sendMessage(nickName+" >>(귓속말)"
											+msg.substring(msg.indexOf('-')+1));
									break;
			
								}
								
								
							}
						default:
							sendMessageAll(nickName + "  >>"+msg);
							break;
						}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		public void sendMessageAll(String msg){
			// 벡터의 데이터를 꺼내는 클라이언트에게 보내기
			for (int i = 0; i <vec.size(); i++) {
				Service cs = (Service) vec.elementAt(i);
				cs.sendMessage(msg);
				
			}
		} 
		public void sendMessage(String msg){
			try {
				out.write((msg+"\n").getBytes());
				txt.append("보냄 : "+msg+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
