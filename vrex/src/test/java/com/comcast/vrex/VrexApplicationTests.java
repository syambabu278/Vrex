package com.comcast.vrex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.comcast.vrex.model.Command;
import com.comcast.vrex.model.CommandsResponse;
import com.comcast.vrex.service.VrexService;

@SpringBootTest
class VrexApplicationTests {
@Autowired 
 VrexService vrexService;
	
	@Test
	void nationTopCommandsTest() {
		
		Map<String, List<Command>> request = new HashMap<String, List<Command>>();
		List<Command> commands = new ArrayList<Command>();
		commands.add(new Command("Fred Zhang", "CNN"));
		commands.add(new Command("Fred Zhang", "NBC"));
		commands.add(new Command("Fred Zhang", "CNN"));
		request.put("alabama", commands);
		commands = new ArrayList<Command>();
		commands.add(new Command("Thomas Brown", "Show me movies"));
		commands.add(new Command("Alisha Brown", "Turn off the TV"));
		commands.add(new Command("Marcus Brown", "CNN"));
		request.put("florida", commands);
		
		commands = new ArrayList<Command>();
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		request.put("maryland", commands);
		Map<String, Object> response = vrexService.processCommands(request);
		
		assertEquals("game of thrones", ((List<String>) response.get("topCommandsNationally")).get(0));
		assertEquals("cnn", ((List<String>) response.get("topCommandsNationally")).get(1));
		assertEquals("nbc", ((List<String>) response.get("topCommandsNationally")).get(2));
		
	}
	
	@Test
	void StateTopCommandsTest() {
		
		Map<String, List<Command>> request = new HashMap<String, List<Command>>();
		List<Command> commands = new ArrayList<Command>();
		commands.add(new Command("Fred Zhang", "CNN"));
		commands.add(new Command("Fred Zhang", "NBC"));
		commands.add(new Command("Fred Zhang", "CNN"));
		request.put("alabama", commands);
		commands = new ArrayList<Command>();
		commands.add(new Command("Thomas Brown", "Show me movies"));
		commands.add(new Command("Alisha Brown", "Turn off the TV"));
		commands.add(new Command("Marcus Brown", "Turn off the TV"));
		request.put("florida", commands);
		
		commands = new ArrayList<Command>();
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		request.put("maryland", commands);
		Map<String, Object> response = vrexService.processCommands(request);		
		assertEquals("CNN".toLowerCase(), ((CommandsResponse) response.get("alabama")).getMostFrequentCommand());
		assertEquals("Turn off the TV".toLowerCase(), ((CommandsResponse) response.get("florida")).getMostFrequentCommand());
		assertEquals("Game of Thrones".toLowerCase(), ((CommandsResponse) response.get("maryland")).getMostFrequentCommand());
		
	}
	
	@Test
	void StateTopCommandsTestWithCaseSensitive() {
		
		Map<String, List<Command>> request = new HashMap<String, List<Command>>();
		List<Command> commands = new ArrayList<Command>();
		commands.add(new Command("Fred Zhang", "CNN"));
		commands.add(new Command("Fred Zhang", "NBC"));
		commands.add(new Command("Fred Zhang", "cnn")); // case change
		commands.add(new Command("Fred Zhang", "test"));
		request.put("alabama", commands);
		commands = new ArrayList<Command>();
		commands.add(new Command("Thomas Brown", "Show me movies"));
		commands.add(new Command("Alisha Brown", "Turn off the TV"));
		commands.add(new Command("Marcus Brown", "Turn off the TV"));
		request.put("florida", commands);
		
		commands = new ArrayList<Command>();
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		commands.add(new Command("Fred Zhang", "Game of Thrones"));
		request.put("maryland", commands);
		Map<String, Object> response = vrexService.processCommands(request);		
		assertEquals("CNN".toLowerCase(), ((CommandsResponse) response.get("alabama")).getMostFrequentCommand());
		
		
	}

}
