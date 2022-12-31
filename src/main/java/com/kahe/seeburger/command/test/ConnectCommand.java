package com.kahe.seeburger.command.test;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ConnectCommand {
	
	@ShellMethod("Connect to server, format: connect ip port")
	public String connect(String ip,int port) {
		return String.format("Successfully connected to service:%s:%s", ip,port);
	}
	
}