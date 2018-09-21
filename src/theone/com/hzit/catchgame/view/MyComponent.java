package theone.com.hzit.catchgame.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;

import theone.com.hzit.catchgame.model.Sheep;
import theone.com.hzit.catchgame.model.inter.Constant;
import theone.com.hzit.catchgame.service.HuntingGround;

/**
 * 圆
 * 
 * @author tomato
 *
 */
public class MyComponent extends JComponent implements Runnable {

	private HuntingGround hg;
	private BufferedImage bufimg;
	
	public MyComponent(HuntingGround hg) {
		super();
		this.hg = hg;
		
		setFocusable(true);
	}

	public HuntingGround getHg() {
		return hg;
	}

	public void setHg(HuntingGround hg) {
		this.hg = hg;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		hg.getHunter().draw(g2D);
		if(!hg.getSheepList().isEmpty())
//		for (Sheep sheep : hg.getSheepList()) {
//			sheep.draw(g2D);
//		}//迭代修改元素 会发生错误
		for (int i = 0; i < hg.getSheepList().size(); i++) {
			hg.getSheepList().get(i).draw(g2D);
		}

	}

	
	@Override
	public void update(Graphics g) {
		 if(bufimg==null) {
			 bufimg = new BufferedImage(Constant.WIDTH, Constant.HEIGHT, BufferedImage.TYPE_INT_RGB);
		 }
		 Graphics2D g2D = (Graphics2D) bufimg.getGraphics();
		 paint(g2D);
	}

	@Override
	public void run() {
		while (true) {
			try {
				TimeUnit.MICROSECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
			Thread.yield();
		}
	}
}
