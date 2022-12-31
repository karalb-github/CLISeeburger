package com.kahe.seeburger;

import java.io.IOException;

import org.jline.reader.impl.history.DefaultHistory;
import org.springframework.stereotype.Component;

@Component
public class NoSaveHistory extends DefaultHistory {
    @Override
    public void save() throws IOException {

    }
}