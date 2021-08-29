package com.comcast.vrex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comcast.vrex.model.Command;
import com.comcast.vrex.service.VrexService;

@RestController
public class VrexController {
	private static final Log LOGGER = LogFactory.getLog(VrexController.class);

	@Autowired
	private VrexService verxService;

	@RequestMapping(value = "/commands", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getCommandsInfo(@Valid @RequestBody Map<String, List<Command>> request) {

		synchronized (this) { // simple way to block other requests - there is other ways .
			LOGGER.info("Received Request:" + request);
			try {

				return new ResponseEntity<>(verxService.processCommands(request), HttpStatus.OK);
			} catch (Exception e) {
				LOGGER.error("Unabel to Process request:" + e);
				Map<String, Object> response = new HashMap<String, Object>();
				response.put("error", e.getMessage());
				// internal server error in case of exception
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
	}

}
