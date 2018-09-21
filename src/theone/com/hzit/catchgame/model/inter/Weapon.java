package theone.com.hzit.catchgame.model.inter;

import java.awt.Graphics2D;

import theone.com.hzit.catchgame.model.data.Circle;
import theone.com.hzit.catchgame.model.data.Direction;
import theone.com.hzit.catchgame.service.WeaponListener;

public interface Weapon {

	public double attack(Direction direction);

	public double getPower();

	public void draw(Graphics2D g2d);

	public Circle getWeapon();

	public void addListener(WeaponListener weaponListener);

}
