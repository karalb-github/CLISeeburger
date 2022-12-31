package com.kahe.seeburger;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
class ShutdownManager {

	public void immediate(String message) {
		log.error(message);
		log.info("Applicaton stopped immediate.");
		System.exit(0);
	}

}