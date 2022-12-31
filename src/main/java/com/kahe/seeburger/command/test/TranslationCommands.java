package com.kahe.seeburger.command.test;

// import java.util.Locale;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class TranslationCommands {
//
//    private final TranslationService service;
//
//    @Autowired
//    public TranslationCommands(TranslationService service) {
//      this.service = service;
//    }
//
//    @ShellMethod("Translate text from one language to another.")
//    public String translate(
//      @ShellOption(mandatory = true) String text,
//      @ShellOption(mandatory = true, defaultValue = "en_US") Locale from,
//      @ShellOption(mandatory = true) Locate to
//    ) {
//      // invoke service
//      return service.translate(text, from, to);
//    }
  @ShellMethod("Translate text from one language to another.")
  public String translate(
 
  ) {
    // invoke service
    return "hello Wrold";
  }
}