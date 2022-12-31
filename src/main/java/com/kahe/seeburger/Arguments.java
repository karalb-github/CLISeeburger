package com.kahe.seeburger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class Arguments {

	@Autowired
	public Arguments(ApplicationArguments arguments, ShutdownManager shutdownManager) throws Exception {
		List<String> args = arguments.getNonOptionArgs();
		if (args == null)
			shutdownManager.immediate("Applicaton arguments are null.");
		if (args.size() == 0)
			shutdownManager.immediate("Applicaton arguments contains no values.");
		
		rootPath = args.get(0);
	}

	public String getRootPath() {
		return rootPath;
	}

	String rootPath;
}
