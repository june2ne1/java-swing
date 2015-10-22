package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class TopLevelDemo {
	 /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
    	//Create and set up the window.
        JFrame frame = new JFrame("TopLevelDemo");
        //프레임을 닫는 이벤트 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
 
        //Create the menu bar.  Make it have a green background.
        //메뉴바 생성 코드 바탕색과 크기를 설정하는 코드
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200, 20));
 
        //Create a yellow label to put in the content pane.
        //라벨 생성 opaque 투명도 true면 100%
        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(new Color(255, 216, 216));
        yellowLabel.setPreferredSize(new Dimension(200, 180));
 
        //Set the menu bar and add the label to the content pane.
        //메뉴바 생성 보더레이아웃은 중앙정렬이 가능하다
        frame.setJMenuBar(greenMenuBar);
        frame.getContentPane().add(yellowLabel, BorderLayout.EAST);
 
        //Display the window.
        //pack() 메소드는 컴포넌트들을 팩킹시키는 역할
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	/*public static void main(String[] args) {
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(someBorder);
		contentPane.add(someComponent, BorderLayout.CENTER);
		contentPane.add(anotherComponent, BorderLayout.PAGE_END);


		topLevelContainer.setContentPane(contentPane);
		
	}*/
	

}
