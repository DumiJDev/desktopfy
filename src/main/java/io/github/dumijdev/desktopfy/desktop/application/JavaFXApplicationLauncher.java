package io.github.dumijdev.desktopfy.desktop.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class JavaFXApplicationLauncher implements DesktopApplication {

  public JavaFXApplicationLauncher(String title, String url, double width, double height, boolean resizable) {
    JavaFXDesktopApplication.initialize(title, url, width, height, resizable);
  }

  @Override
  public void start() {
    Application.launch(JavaFXDesktopApplication.class);
  }

  @Override
  public void close() {
    Platform.exit();
  }

  public static class JavaFXDesktopApplication extends Application {
    private static String url;
    private static String title;
    private Stage stage;
    private static double width;
    private static double height;
    private static boolean resizable;

    public static void initialize(String title, String url, double width, double height, boolean resizable) {
      JavaFXDesktopApplication.title = title;
      JavaFXDesktopApplication.url = url;
      JavaFXDesktopApplication.width = width;
      JavaFXDesktopApplication.height = height;
      JavaFXDesktopApplication.resizable = resizable;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
      var webView = new WebView();
      var engine = webView.getEngine();
      System.out.println(engine.getUserAgent());
      engine.setJavaScriptEnabled(true);
      engine.setUserDataDirectory(null);
      engine.load(url);

      stage = primaryStage;
      stage.setScene(new Scene(webView, width, height));
      stage.setResizable(resizable);
      stage.setTitle(title);
      stage.show();
      stage.centerOnScreen();
      stage.setOnCloseRequest(event -> {
        stop();
      });
    }

    @Override
    public void stop() {
      if (stage != null) {
        stage.close();
        System.exit(0);
      }
    }

  }
}
