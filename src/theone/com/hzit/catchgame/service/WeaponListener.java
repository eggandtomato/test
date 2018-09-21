package theone.com.hzit.catchgame.service;

import theone.com.hzit.catchgame.model.inter.Weapon;

public interface WeaponListener {
	//武器发生作用，发射，开枪
	void weaponAction(Weapon weapon);
}
