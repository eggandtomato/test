package theone.com.hzit.catchgame.cotroller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import theone.com.hzit.catchgame.model.Ball;
import theone.com.hzit.catchgame.model.Hunter;
import theone.com.hzit.catchgame.model.Sheep;
import theone.com.hzit.catchgame.model.data.Direction;
import theone.com.hzit.catchgame.model.data.State;
import theone.com.hzit.catchgame.model.inter.Prey;
import theone.com.hzit.catchgame.model.inter.Weapon;
import theone.com.hzit.catchgame.service.HunterAdapter;
import theone.com.hzit.catchgame.service.HuntingGround;
import theone.com.hzit.catchgame.service.WeaponListener;
import theone.com.hzit.catchgame.view.MyComponent;
import theone.com.hzit.catchgame.view.TestView;

public class TestController2 {
	private TestView view;
	private HuntingGround hg;
	private ExecutorService executor = Executors.newCachedThreadPool();

	public TestController2(TestView view, HuntingGround hg) {
		this.hg = hg;

		this.view = view;

		view.getMyComponent().addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				Hunter hunter = hg.getHunter();
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					hunter.setDirection(Direction.UP);
					break;
				case KeyEvent.VK_DOWN:
					hunter.setDirection(Direction.DOWN);
					break;
				case KeyEvent.VK_LEFT:
					hunter.setDirection(Direction.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					hunter.setDirection(Direction.RIGHT);
					break;
				case KeyEvent.VK_N:
					hg.restart();
					int curr = Integer.parseInt(view.getCurrentScore().getText());
					int max = Integer.parseInt(view.getMaxScore().getText());
					max = Math.max(curr, max);
					view.getMaxScore().setText(String.valueOf(max));
					view.getCurrentScore().setText("0");
					break;
				case KeyEvent.VK_P:
					hg.pause();
				case KeyEvent.VK_SPACE:
					hg.getHunter().catching(hg.getHunter().getDirection());
					break;
				}
			}
		});

		init();
	}

	public TestController2() {
		// TODO Auto-generated constructor stub
	}

	public void init() {
		view.getMyComponent().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				System.out.println("鼠标点击了");
				view.getMyComponent().grabFocus();
			}

		});
		// 事件源为Hunter，监听Cacthing事件，事件发生则初始化武器，这里初始化ball
		hg.getHunter().addListener(new HunterAdapter() {

			@Override
			public double attack(Hunter hunter) {
				// 装备武器
				hg.getHunter().arm(hg.getWeapon());
				// 把武器置于可画状态，即RUNNABLE状态
				if (hg.getHunter().getState() == State.RUNNABLE)
					((Ball) hg.getWeapon()).setState(State.RUNNABLE);
				return 0;
			}
		});
		// 添加监听hunter移动的监听器，hunter碰到prey则hunter置于DIE状态

		hg.getHunter().addListener(new HunterAdapter() {

			@Override
			public void hunterMove(Hunter hunter) {
				if (hg.hitPrey()) {
					hg.getHunter().setState(State.DIE);// 撞到猎物，猎人死亡
				}
			}
		});
		// 监听猎杀,猎杀成功分数加一，猎人半径加一
		hg.getWeapon().addListener(new WeaponListener() {

			@Override
			public void weaponAction(Weapon weapon) {

				if (hg.isShotted()) {
					System.out.println("TestController: 射击成功");
					hg.getHunter().setRadius(hg.getHunter().getRadius() + 1);
					String currSco = view.getCurrentScore().getText();
					int sco = Integer.parseInt(currSco) + 1;
					view.getCurrentScore().setText(String.valueOf(sco));
				}
			}
		});
	}

	public void startGame() {
		Runnable task = new Runnable() {

			@Override
			public void run() {
				Ball ball = new Ball(500, 100, 2);
				ball.setColor(Color.red);
				ball.setRadius(20);
				List<Prey> list = new ArrayList<>();
				Hunter hunter = new Hunter(ball, 50, list);
				hunter.setPoint(new Point2D.Double(100, 100));
				hunter.setRadius(50);
				hunter.setSpeed(1);

//				Sheep s1 = new Sheep();

				ArrayList<Sheep> sheepList = new ArrayList<>();
//				sheepList.add(s1);


				HuntingGround hg = new HuntingGround(hunter, ball, sheepList);

				MyComponent myComponent = new MyComponent(hg);

				TestView view = new TestView(myComponent);

				TestController2 con = new TestController2(view, hg);

				con.launch();

			}
		};
		Thread t = new Thread(task);
		t.start();
	}

	public void launch() {
		hg.startSheep();// 启动猎物线程
		hg.getHunter().setState(State.RUNNABLE);
		executor.execute(hg);
		executor.execute(hg.getHunter());
		new Thread(view.getMyComponent()).start();// 画组件
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				TestController2 con = new TestController2();
				con.startGame();
			}
		});
	}
}
