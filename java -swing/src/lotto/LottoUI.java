package lotto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LottoUI extends JFrame implements ActionListener {
//연관관계에 따라 화면만 구성 vo랑 다른 개념
	//데이터가 필요없어서 프라이빗 안함 
	//겟터세터가 필요없다 은닉화 안함
	private static final long serialVersionUID =  1L;
	Lotto lotto;
	// 컴포넌트(스윙에서 화면을 나타내는 클래스)는 연관관계로 부모 = 자식 관계를 맺는다.
	JButton btn,btnTest,btnExit;
	JPanel panelNorth, panelSouth; //보더레이아웃 개념으로 볼때 동서남북으로 위치값을 줌.
	ImageIcon icon;
	List<JButton> btns;// 로또볼에 숫자가 붙어질 공
	//부품 준비 단계 -> 큰것에서 작은것 순으로
	public LottoUI() {
		init();
	}
	public void init(){ //initialize의 약자로 초기화 메소드 이름으로 많이 사용함
		this.setTitle("SBS로또추첨");
		lotto = new Lotto();
		btns = new ArrayList<JButton>();
		panelNorth = new JPanel();
		panelSouth = new JPanel();
		btn = new JButton("로또번호추첨");
		btnTest = new JButton("테스트");
		btnExit = new JButton("종료");
		// 조립단계 -> 작은것부터 큰것 순으로
		btn.addActionListener(this); //이 클래스에서 구현한 관련 메소드를 할당한다.
		btnTest.addActionListener(this);
		btnExit.addActionListener(this);
		panelNorth.add(btn); //북쪽 패널에 버튼을 장착
		panelNorth.add(btnTest);
		panelNorth.add(btnExit);
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelSouth, BorderLayout.SOUTH);
		this.setBounds(300, 400, 1200, 300);
		//300, 400은 x,y로 좌표값
		// 1200, 300은 픽셀로 크기
		this.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		//리스너메소드는 버튼을 눌렀을때만 움직여라
		if (btns.size() == 0) {// 공이 추첨이 안된 상태
			JButton tmp = null;
			for (int i = 0; i < 6; i++) {
				tmp = new JButton(); // 번호가 붙지 않은 로또볼 객체를 만들어라
				btns.add(tmp); // 6회전이므로 비어있는 로또볼 6개를 리스트 담아라
				panelSouth.add(tmp); // 리스트에 담고, 또 그로또볼을 패널에 붙여라
				
				
				switch (e.getActionCommand()) {
				case "테스트":
					System.out.println("테스트입니다 ");
					
					break;
				case "종료":
					System.exit(0);
					break;

				default:
					break;
				}
				
			}
			
		}
		lotto.setLotto(); //로또볼 추첨에 들어갑니다.
		int[] temp = lotto.getLotto(); //셋로또 했으니 겟로또
		for (int i = 0; i < temp.length; i++) {
		btns.get(i).setIcon(new ImageIcon("src/image/"+temp[i]+".gif"));
		}
	}

}
