package ru.fx.develop.renovation.config;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.fx.develop.renovation.preloader.TestPreloader;

import java.util.Arrays;

public abstract class AbstractJavaFXApplicationSupport extends Application {

    private static String[] saveArgs;// аргументы при старте (если они есть)

    protected ConfigurableApplicationContext context;

    protected static void launchApp(Class<? extends AbstractJavaFXApplicationSupport> appClass, String[] args) {
        AbstractJavaFXApplicationSupport.saveArgs = args;
        LauncherImpl.launchApplication(appClass, TestPreloader.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        context = SpringApplication.run(getClass(), saveArgs);

        // главный момент - "присоединяем" экземпляр Application (который стартует javafx приложение) к контексту Spring
        context.getAutowireCapableBeanFactory().autowireBean(this);

//		printBeans();

        // уведомить прелоадер, что загрузка прошла полностью (чтобы скрыть окно инициализации)
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(100));
    }

    // печать всех spring бинов
    private void printBeans() {
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }
}
