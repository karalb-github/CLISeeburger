package com.kahe.seeburger.command.prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.ValueProvider;

import com.kahe.seeburger.Arguments;
import com.kahe.seeburger.data.message.Message;
import com.kahe.seeburger.data.message.Record;

@ShellComponent
public class MainShell implements ValueProvider {

	@ShellMethod("Insert segments")
	public String insertSegments(@ShellOption({ "-N", "--name" }) String messageName) throws Exception {
		Message message = MessageFactory.getMessage(arguments.getRootPath(), messagesDir, messageName);
		String template = TemplateFactory.getTemplate(arguments.getRootPath(), templatesDir, "InsertSegments.txt");
		Map<String, Record> segments = SegmentsFactory.getSegments(message);

		return OutputFactory.getSegments(template, segments);
	}

	@ShellMethod("Insert a messages segment trees")
	public String insertMessagesSegmentTree(@ShellOption({ "-N", "--name" }) String messageName) throws Exception {
		Message message = MessageFactory.getMessage(arguments.getRootPath(), messagesDir, messageName);
		String template = TemplateFactory.getTemplate(arguments.getRootPath(), templatesDir, "InsertSegmentTree.txt");

		Map<String, Record> segments = SegmentsFactory.getSegments(message);
		return OutputFactory.getSegmentTree(template, segments);
	}

	@ShellMethod("Insert a segmentstree")
	public String insertSegmentTree(@ShellOption({ "-N", "--name" }) String messageName, String segmentName)
			throws Exception {
		Message message = MessageFactory.getMessage(arguments.getRootPath(), messagesDir, messageName);
		String template = TemplateFactory.getTemplate(arguments.getRootPath(), templatesDir, "InsertSegmentTree.txt");

		Record segment = new Record();
		segment.setName(segmentName);
		return OutputFactory.getXMLSegmentTree(message, segment, template);
	}

	@ShellMethod("Insert message tree")
	public String insertMessageTree(@ShellOption({ "-N", "--name" }) String messageName) throws Exception {
		Message message = MessageFactory.getMessage(arguments.getRootPath(), messagesDir, messageName);
		String template = TemplateFactory.getTemplate(arguments.getRootPath(), templatesDir, "InsertMessageTree.txt");

		return OutputFactory.getSqlMessageTree(template, message);

	}

	@ShellMethod("Show message tree")
	public String showMessageTree(@ShellOption({ "-N", "--name" }) String messageName) throws Exception {
		Message message = MessageFactory.getMessage(arguments.getRootPath(), messagesDir, messageName);

		return OutputFactory.getTextMessageTree(message);

	}

	@Override
	public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext,
			String[] hints) {
		List<CompletionProposal> result = new ArrayList<CompletionProposal>();
		List<String> knownThings = new ArrayList<>();
		List<String> knownPaths = new ArrayList<>();

		knownPaths.add("something");

		String userInput = completionContext.currentWordUpToCursor();
		knownThings.stream().filter(t -> t.startsWith(userInput)).forEach(t -> result.add(new CompletionProposal(t)));

		return result;
	}

	@Autowired
	Arguments arguments;
	@Value("${message-factory.messages-dir}")
	String messagesDir;
	@Value("${message-factory.templates-dir}")
	String templatesDir;

}