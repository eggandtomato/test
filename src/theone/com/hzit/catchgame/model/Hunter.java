package theone.com.hzit.catchgame.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import theone.com.hzit.catchgame.model.data.App;
import theone.com.hzit.catchgame.model.data.Circle;
import theone.com.hzit.catchgame.model.data.Direction;
import theone.com.hzit.catchgame.model.data.State;
import theone.com.hzit.catchgame.model.inter.Prey;
import theone.com.hzit.catchgame.model.inter.Weapon;
import theone.com.hzit.catchgame.service.HunterListener;

public class Hunter extends Circle implements Runnable {
	private Weapon weapon;

	private double powerValues;

	private List<Prey> bag;

	private Set<HunterListener> listener = new HashSet<>();

	public void addListener(HunterListener listener) {
		this.listener.add(listener);
	}

	public void removeListener(HunterListener listener) {
		this.listener.remove(listener);
	}

	public Hunter(Weapon weapon, double powerValues, List<Prey> bag) {
		this.weapon = weapon;
		this.powerValues = powerValues;

		this.bag = bag;

	}

	// 装备武器
	public void arm(Weapon weapon) {
		double x = getPoint().getX() + getRadius() - weapon.getWeapon().getRadius();
		double y = getPoint().getY() + getRadius() - weapon.getWeapon().getRadius();
		weapon.getWeapon().setPoint(new Point2D.Double(x, y));
		weapon.getWeapon().setDirection(getDirection());
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public double getPowerValues() {
		return powerValues;
	}

	public void setPowerValues(double powerValues) {
		this.powerValues = powerValues;
	}

	public List<Prey> getBag() {
		return bag;
	}

	public void setBag(List<Prey> bag) {
		this.bag = bag;
	}

	public double catching(Direction direction) {
		System.out.println("Hunter:捕捉--");
		Iterator<HunterListener> it = listener.iterator();
		while (it.hasNext()) {
			it.next().attack(this);
		}
		if (powerValues > 0) {
			double result = weapon.attack(direction);
			return result;
		} else {
			System.out.println("没有能量了");
			return 0;
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		weapon = null;
		powerValues = 0;
		bag = null;
		listener = null;
	}

	@Override
	public synchronized void draw(Graphics g) {
		super.draw(g);
		Graphics2D g2D = (Graphics2D) g;
		// 武器不在RUNNABLE状态下不画
		if (((Ball) weapon.getWeapon()).getState() == State.RUNNABLE)
			weapon.draw(g2D);
	}


	@Override
	public void run() {

		while (App.STATE != State.STOP) {
			while (getState() != State.DIE) {
				System.out.println("hunter:App.state="+App.STATE);
				if (App.STATE == State.PAUSE) {
					continue;
				}
					move();
					for (HunterListener l : listener) {
						l.hunterMove(Hunter.this);
					}
					try {
						TimeUnit.MICROSECONDS.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			weapon.getWeapon().setState(State.DIE);
		}

	}
}
