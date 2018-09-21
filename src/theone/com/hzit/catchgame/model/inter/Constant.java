package theone.com.hzit.catchgame.model.inter;

import java.awt.Toolkit;

public interface Constant {
	public int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize()
			.getWidth();
	public int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize()
			.getHeight();
	public int APP_WIDTH = WIDTH / 2;
	public int APP_HEIGHT = HEIGHT * 3 / 4;
}
