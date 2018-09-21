package theone.com.hzit.catchgame.model;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import theone.com.hzit.catchgame.model.data.App;
import theone.com.hzit.catchgame.model.data.Circle;
import theone.com.hzit.catchgame.model.data.Direction;
import theone.com.hzit.catchgame.model.data.State;
import theone.com.hzit.catchgame.model.inter.Weapon;
import theone.com.hzit.catchgame.service.WeaponListener;

public class Ball extends Circle implements Weapon {
	private double shotRange;
	private double power;
//	private boolean flag = false;

	private Set<WeaponListener> listener = new HashSet<>();

	@Override
	public void addListener(WeaponListener listener) {
		this.listener.add(listener);
	}

	public void removeListener(WeaponListener listener) {
		this.listener.remove(listener);
	}

	public Ball(double shotRange, double power, double speed) {
		super();
		setState(State.PAUSE);
		this.shotRange = shotRange;
		this.power = power;
		setSpeed(speed);

	}

	public double getShotRange() {
		return shotRange;
	}

	public void setShotRange(double shotRange) {
		this.shotRange = shotRange;
	}

	public void setPower(double power) {
		this.power = power;
	}

	@Override
	public double attack(Direction direction) {
		System.out.println("开始射击");
		Shot shot = new Shot(direction);
		new Thread(shot).start();
		return 0;
	}

	public void move(Direction direction) {
		Point2D p = getPoint();
		double temp = getSpeed();
		switch (direction) {
		case UP:

			setPoint(new Point2D.Double(p.getX(), p.getY() - temp));
			break;
		case DOWN:

			setPoint(new Point2D.Double(p.getX(), p.getY() + temp));
			break;
		case LEFT:

			setPoint(new Point2D.Double(p.getX() - temp, p.getY()));
			break;
		case RIGHT:

			setPoint(new Point2D.Double(p.getX() + temp, p.getY()));
			break;
		default:

			setPoint(new Point2D.Double(p.getX() + temp, p.getY()));
			break;
		}
	}

	private class Shot implements Runnable {
		Direction direc;
		double speed;

		public Shot(Direction direc) {
			this.direc = direc;
			this.speed = getSpeed();
		}

		@Override
		public void run() {
			double temp = shotRange;
			while (getState() != State.DIE) {
				while (temp > 0) {
					if (App.STATE != State.PAUSE) {
						Ball.this.move(direc);
						temp -= speed;
						for (WeaponListener l : listener) {
							l.weaponAction(Ball.this);
						}
						try {
							TimeUnit.MICROSECONDS.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				setState(State.DIE);
			}
		}
	}

//	@Override
//	public void setUnable() {
//		shotRange = 0;
//	}

//	public boolean getFlag() {
//		return flag;
//	}
//
//	public void setFlag(boolean flag) {
//		this.flag = flag;
//	}

	@Override
	public double getPower() {
		return power;
	}

	// 初始化武器Ball的发射开始地点----Hunter对象的中心
//	@Override
//	public void init(Hunter hunter) {
//		double x = hunter.getPoint().getX() - getPoint().getX();
//		double y = hunter.getPoint().getY() - getPoint().getY();
//		this.setPoint(new Point2D.Double(x, y));
//		this.setDirection(hunter.getDirection());
//	}

	@Override
	public void dispose() {
		setState(State.DIE);

	}

	public static void main(String[] args) {
		Ball b = new Ball(100, 10, 2);
		System.out.println(b.getSpeed());
		b.attack(Direction.RIGHT);
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}

	@Override
	public Circle getWeapon() {
		// TODO Auto-generated method stub
		return this;
	}

}
