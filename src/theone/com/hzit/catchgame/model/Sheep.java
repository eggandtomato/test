package theone.com.hzit.catchgame.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import theone.com.hzit.catchgame.model.data.App;
import theone.com.hzit.catchgame.model.data.Circle;
import theone.com.hzit.catchgame.model.data.State;
import theone.com.hzit.catchgame.model.inter.Prey;
import theone.com.hzit.catchgame.model.inter.Weapon;
import theone.com.hzit.catchgame.service.PreyListener;

public class Sheep extends Circle implements Prey, Runnable {
	private double blood;
	private double weight;
	private Timer timer;

	private Set<PreyListener> listener = new HashSet<>();

	public void addListener(PreyListener listener) {
		this.listener.add(listener);
	}

	public void removeListener(PreyListener listener) {
		this.listener.remove(listener);
	}

	public Sheep() {
		super();
		randomDirection(3);

	}

	public Prey init() {
		return new Sheep();
	}

	public boolean isDie() {
		return blood <= 0 ? true : false;
	}

	public void ishurt(Weapon weapon) {
		blood = blood - weapon.getPower();
	}

	// Ëæ»ú·½Ïò
	private void randomDirection(int n) {
		Random rand = new Random();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						setDirection(getDirection().values()[rand.nextInt(4)]);
						TimeUnit.SECONDS.sleep(n);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
//		for (PreyListener l : listener) {
//			l.initPrey(this);
//		}

	}

	@Override
	public void run() {
//		while (App.STATE != State.STOP) {
			while (getState() != State.DIE) {
				System.out.println("sheep:App.state="+App.STATE);
				if (App.STATE != State.PAUSE) {
					move();
					try {
						TimeUnit.MICROSECONDS.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
//		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof Sheep) {
			Sheep s = (Sheep) obj;
			if (s.getPoint().equals(s.getPoint()) && s.getRadius() == getRadius())
				return true;
		}
		return false;
	}

}
