package com.kahe.seeburger.command.prod;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateFactory {
	
	public final static String getTemplate(String rootPath, String templatesDir, String filename) throws Exception {
		Path path = Paths.get(rootPath, templatesDir, filename);
		File file = path.toFile();
		if (!file.exists())
			throw new Exception(String.format("File '%s' not found.", file.getAbsoluteFile()));
		String template = Files.readAllLines(path).get(0);
		return template;
	}


}