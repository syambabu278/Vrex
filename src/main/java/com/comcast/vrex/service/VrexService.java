package com.comcast.vrex.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.comcast.vrex.model.Command;
import com.comcast.vrex.model.CommandsResponse;

@Service
public class VrexService {
	private static final Log LOGGER = LogFactory.getLog(VrexService.class);

	public Map<String, Object> processCommands(Map<String, List<Command>> request) {		
		// Merge Duplicate States with same name
		
		Map<String, List<Command>> updatedrequest = new HashMap<String, List<Command>>();
		for (Map.Entry<String, List<Command>> entry : request.entrySet()) {
			if (updatedrequest.containsKey(entry.getKey().toLowerCase())) {
				updatedrequest.get(entry.getKey().toLowerCase()).addAll(entry.getValue());
			} else
				updatedrequest.put(entry.getKey().toLowerCase(), entry.getValue());
		}

		
		Map<String, Object> response = new HashMap<String, Object>();
		//get top commands by state
		for (Map.Entry<String, List<Command>> entry : updatedrequest.entrySet()) {
			response.put(entry.getKey(), processStateCommands(entry.getKey(), entry.getValue()));
		}
		
		//get top 3 commands nation wide
		List<String> nationTopCOmmands = getCountryTopCommands(request, 3);	
		response.put("topCommandsNationally", nationTopCOmmands);				
		
		return response;
	}

	// returns top number commands from request
	private List<String> getCountryTopCommands(Map<String, List<Command>> request, int number) {
		List<Command> allCommands = new ArrayList<>();

		for (Map.Entry<String, List<Command>> entry : request.entrySet())
			allCommands.addAll(entry.getValue());

		// group by commands
		Map<String, List<Command>> commandsMap = allCommands.stream()
				.collect(Collectors.groupingBy(s -> s.command.toLowerCase()));

		Map<String, List<Command>> sortedNewMap = commandsMap.entrySet().stream()
				.sorted((e1, e2) -> e1.getValue().size() < e2.getValue().size() ? 1 : -1)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		List<String> topCommands = new ArrayList<>();

		for (Map.Entry<String, List<Command>> entry : sortedNewMap.entrySet()) {
			topCommands.add(entry.getKey());
		}
		if (topCommands.size() > number)
			return topCommands.subList(0, number);
		else
			return topCommands;
	}

	private CommandsResponse processStateCommands(String state, List<Command> value) {
		CommandsResponse stateResponse = new CommandsResponse();
		stateResponse.setStartProcessTime(new Date().getTime());
		// group by commands
		Map<String, List<Command>> commandsMap = value.stream()
				.collect(Collectors.groupingBy(s -> s.command.toLowerCase()));
		System.out.println(commandsMap);
		// Get key with top used command
		String mostFrequentCommand = Collections
				.max(commandsMap.entrySet(), new Comparator<Entry<String, List<Command>>>() {
					@Override
					public int compare(Entry<String, List<Command>> o1, Entry<String, List<Command>> o2) {
						return o1.getValue().size() > o2.getValue().size() ? 1 : -1;
					}
				}).getKey().toLowerCase();

		stateResponse.setMostFrequentCommand(mostFrequentCommand);
		stateResponse.setStopProcessTime(new Date().getTime());
		LOGGER.info(mostFrequentCommand + " is top used command for state:" + state);
		return stateResponse;
	}

}
