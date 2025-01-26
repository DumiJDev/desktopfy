package io.github.dumijdev.desktopfy.desktop.application.factories;

import io.github.dumijdev.desktopfy.desktop.application.DesktopApplication;
import io.github.dumijdev.desktopfy.desktop.application.JavaFXApplicationLauncher;

public class JavaFXDesktopApplicationFactory extends DesktopApplicationFactory {
  @Override
  public DesktopApplication createDesktopApplication(String title, String url, double width, double height, boolean resizable) {
    return new JavaFXApplicationLauncher(title, url, width, height, resizable);
  }
}
