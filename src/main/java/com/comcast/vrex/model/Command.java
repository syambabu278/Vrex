package com.comcast.vrex.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


public class Command implements Serializable{
	
	public Command() {
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	public String speaker;
	@NotNull
	public String command;
	
	

	public Command(@NotNull String speaker, @NotNull String command) {
		super();
		this.speaker = speaker;
		this.command = command;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
