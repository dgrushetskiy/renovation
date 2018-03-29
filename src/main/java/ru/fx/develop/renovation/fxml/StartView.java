package ru.fx.develop.renovation.fxml;

import org.springframework.stereotype.Component;

@Component
public class StartView extends SpringFxmlView{

    private static final String FXML_START = "fxml/start.fxml";

    public StartView() {
        super(StartView.class.getClassLoader().getResource(FXML_START));
    }
}
