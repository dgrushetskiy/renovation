package ru.fx.develop.renovation;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.fx.develop.renovation.config.AbstractJavaFXApplicationSupport;
import ru.fx.develop.renovation.fxml.StartView;
import ru.fx.develop.renovation.util.LocaleManager;

import java.util.Locale;

@Configuration
@SpringBootApplication
@ComponentScan("ru.fx.develop.renovation")
public class RenovationApplication extends AbstractJavaFXApplicationSupport {

	@Autowired
	private StartView startView;

	public static Stage stage;

	@Value("${ui.title: Генератор Отчетов}")
	private String winTitle;

	public static void main(String[] args) {
		launchApp(RenovationApplication.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		super.start(primaryStage);
		loadMainFXML(LocaleManager.EN_LOCALE, primaryStage);
	}

	// загружает дерево компонентов и возвращает в виде VBox (корневой элемент в FXML)
	private void loadMainFXML(Locale locale, Stage primaryStage) {
		this.stage = primaryStage;
		primaryStage.setScene(new Scene(startView.getView(locale)));
		primaryStage.setResizable(true);
		primaryStage.centerOnScreen();
		primaryStage.setTitle(winTitle);//startView.getResourceBundle().getString("address_book")
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		System.exit(0);
	}
}
