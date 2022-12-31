package com.kahe.seeburger;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

@Component
public class PromptProvider implements org.springframework.shell.jline.PromptProvider {

	@Override
	public AttributedString getPrompt() {
		return new AttributedString("CLI-Seeburger:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
	}
}