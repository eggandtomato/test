package theone.com.hzit.catchgame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import theone.com.hzit.catchgame.model.Ball;
import theone.com.hzit.catchgame.model.Hunter;
import theone.com.hzit.catchgame.model.Sheep;
import theone.com.hzit.catchgame.model.inter.Constant;
import theone.com.hzit.catchgame.model.inter.Prey;
import theone.com.hzit.catchgame.model.inter.Weapon;
import theone.com.hzit.catchgame.service.HuntingGround;

public class TestView {
	private JFrame jFrame;
	private MyComponent myComponent;

	private JLabel userName;
	private JLabel currentScore;
	private JLabel maxScore;
	
	private JLabel maxScoreLabel;
	private JLabel currScoreLabel;
	private JLabel userLabel;
	public JFrame getjFrame() {
		return jFrame;
	}

	public void setjFrame(JFrame jFrame) {
		this.jFrame = jFrame;
	}


	public JLabel getUserName() {
		return userName;
	}

	public void setUserName(JLabel userName) {
		this.userName = userName;
	}

	public JLabel getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(JLabel currentScore) {
		this.currentScore = currentScore;
	}

	public JLabel getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(JLabel maxScore) {
		this.maxScore = maxScore;
	}

	public TestView(MyComponent myComponent) {
		
		this.myComponent = myComponent;
		jFrame = new JFrame();

//		jFrame.setBackground(Color.red);
		jFrame.getContentPane().setBackground(Color.black);
		userLabel = new JLabel();
		userLabel.setLocation(new Point(5, 5));
		userLabel.setText("当前玩家：");
		userLabel.setSize(new Dimension(80, 15));
		userLabel.setForeground(Color.white);
		
		userName = new JLabel();
		userName.setLocation(new Point(85, 5));
		userName.setText("admin");
		userName.setSize(new Dimension(80, 15));
		userName.setForeground(Color.white);
		
		currScoreLabel = new JLabel();
		currScoreLabel.setLocation(new Point(5, 20));
		currScoreLabel.setText("当前得分：");
		currScoreLabel.setSize(new Dimension(80, 15));
		currScoreLabel.setForeground(Color.white);

		currentScore = new JLabel();
		currentScore.setLocation(new Point(85, 20));
		currentScore.setText("0");
		currentScore.setSize(new Dimension(80, 15));
		currentScore.setForeground(Color.white);

		maxScoreLabel = new JLabel();
		maxScoreLabel.setLocation(new Point(5, 35));
		maxScoreLabel.setText("历史最高：");
		maxScoreLabel.setSize(new Dimension(80, 15));
		maxScoreLabel.setForeground(Color.white);

		maxScore = new JLabel();
		maxScore.setLocation(new Point(85, 35));
		maxScore.setText("0");
		maxScore.setSize(new Dimension(80, 15));
		maxScore.setForeground(Color.white);

		jFrame.add(userLabel);
		jFrame.add(userName);
		jFrame.add(currScoreLabel);
		jFrame.add(currentScore);
		jFrame.add(maxScoreLabel);
		jFrame.add(maxScore);
		jFrame.add(this.myComponent);
//		jFrame.setSize(Constant.APP_WIDTH, Constant.APP_HEIGHT);
		jFrame.setSize(Constant.WIDTH, Constant.HEIGHT);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		
		jFrame.setVisible(true);
	}

	public MyComponent getMyComponent() {
		return myComponent;
	}

	public void setMyComponent(MyComponent myComponent) {
		this.myComponent = myComponent;
	}


}
