package com.comcast.vrex.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateCommands {
	
	Map<String, List<Command>> stateCommands = new HashMap<>();

	public Map<String, List<Command>> getStateCommands() {
		return stateCommands;
	}

	public void setStateCommands(Map<String, List<Command>> stateCommands) {
		this.stateCommands = stateCommands;
	}
	
	

}
