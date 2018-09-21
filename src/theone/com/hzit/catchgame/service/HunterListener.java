package theone.com.hzit.catchgame.service;

import theone.com.hzit.catchgame.model.Hunter;

public interface HunterListener {

	double attack(Hunter hunter);
	void hunterMove(Hunter hunter);
}
