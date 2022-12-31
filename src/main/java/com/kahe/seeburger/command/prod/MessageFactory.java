package com.kahe.seeburger.command.prod;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.kahe.seeburger.XMLMarshaller;
import com.kahe.seeburger.data.message.Message;

public class MessageFactory {

	public final static Message getMessage(String rootPath, String messagesDir, String filename) throws Exception {
		Path path = Paths.get(rootPath, messagesDir, filename);
		File file = path.toFile();
		if (!file.exists())
			throw new Exception(String.format("File '%s' not found.", file.getAbsoluteFile()));
		Source source = new StreamSource(file);
		Message message = XMLMarshaller.unmarshall(Message.class, source);
		return message;
	}

}