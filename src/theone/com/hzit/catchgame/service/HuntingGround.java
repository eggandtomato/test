package theone.com.hzit.catchgame.service;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import theone.com.hzit.catchgame.model.Ball;
import theone.com.hzit.catchgame.model.Hunter;
import theone.com.hzit.catchgame.model.Sheep;
import theone.com.hzit.catchgame.model.data.App;
import theone.com.hzit.catchgame.model.data.Relationship;
import theone.com.hzit.catchgame.model.data.State;
import theone.com.hzit.catchgame.model.inter.Weapon;

public class HuntingGround implements Runnable {
	private Hunter hunter;
	private Weapon weapon;
	private volatile List<Sheep> sheepList;
	
	public HuntingGround(Hunter hunter, Weapon weapon, List<Sheep> sheepList) {
		this.hunter = hunter;
		this.weapon = weapon;
		this.sheepList = sheepList;
	}


//	public TimerTask addNewSheep() {
//		TimerTask task = new TimerTask() {
//			@Override
//			public void run() {
//
//				Sheep sheep = new Sheep();
//				if (hunter.getRelationship(sheep) != Relationship.SEPARATION) {
//					sheepList.add(sheep);
//					try {
//						TimeUnit.SECONDS.sleep(2);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					new Thread(sheep).start();
//				}
//			}
//		};
//		return task;
//	}

	public void startSheep() {

		for (Sheep sheep : sheepList) {
			new Thread(sheep).start();
		}
	}

	public List<Sheep> getSheepList() {
		return sheepList;
	}

	public void setSheepList(List<Sheep> sheepList) {
		this.sheepList = sheepList;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	// 撞到猎物，猎人死亡
	public boolean hitPrey() {
		if (!sheepList.isEmpty())
			for (int i = 0; i < sheepList.size(); i++) {
				if (hunter.getRelationship(sheepList.get(i)) != Relationship.SEPARATION)
					return true;
			}
		return false;
	}

	public boolean isShotted() {
		if (weapon.getWeapon().getState() != State.RUNNABLE)
			return false;
		for (Sheep sheep : sheepList) {
			if (weapon.getWeapon().getRelationship(sheep) != Relationship.SEPARATION) {
				sheep.dispose();
				System.out.println("HuntingGround:射击成功" + sheep);
				weapon.getWeapon().dispose();// 武器置于不可用，只能打中一个猎物，不可贯穿
				return true;

			}
		}
		return false;
	}

	public void pause() {
//		hunter.setState(State.PAUSE);
//		for (int i = 0; i < sheepList.size(); i++) {
//			sheepList.get(i).setState(State.PAUSE);
//		}
//		weapon.getWeapon().setState(State.PAUSE);

		if (App.STATE == State.RUNNABLE)
			App.STATE = State.PAUSE;
		else if (App.STATE == State.PAUSE)
			App.STATE = State.RUNNABLE;
	}

	public void resume() {
		hunter.setState(State.RUNNABLE);
		for (int i = 0; i < sheepList.size(); i++) {
			sheepList.get(i).setState(State.RUNNABLE);
		}
	}

	public void intiApp() {
		
	}

	public void restart() {
		System.gc();
		hunter.setState(State.RUNNABLE);
		sheepList.clear();
		hunter.arm(new Ball(100, 50, 50));
	}

	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}

	public void addSheep(Sheep s) {
		sheepList.add(s);

	}

	public void addNewSheep(Sheep s) {
		sheepList.add(s);

	}
   //定时添加sheep对象
	@Override
	public void run() {
		while (App.STATE != State.STOP) {
			System.out.println("HuntingGround:App.STATE");
			while (hunter.getState() != State.DIE) {
				System.out.println("HuntingGround:App.STATE");
				if (App.STATE != State.PAUSE) {
					Sheep sheep = new Sheep();
					if (hunter.getRelationship(sheep) == Relationship.SEPARATION) {
						sheepList.add(sheep);
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						new Thread(sheep).start();
					}
				}
			}
		}
	}

}
