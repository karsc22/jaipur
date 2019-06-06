package com.kargames.jaipur.controller;

import com.kargames.jaipur.Player;
import com.kargames.jaipur.Round;
import com.kargames.jaipur.action.Action;

public interface Controller {
	
	public Action getAction(Round r, Player p);

}
