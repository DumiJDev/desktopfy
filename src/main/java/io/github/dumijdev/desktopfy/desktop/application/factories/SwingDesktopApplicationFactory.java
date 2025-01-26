package io.github.dumijdev.desktopfy.desktop.application.factories;

import io.github.dumijdev.desktopfy.desktop.application.DesktopApplication;
import io.github.dumijdev.desktopfy.desktop.application.SwingDesktopApplication;

public class SwingDesktopApplicationFactory extends DesktopApplicationFactory {
  @Override
  public DesktopApplication createDesktopApplication(String title, String url, double width, double height, boolean resizable) {
    return new SwingDesktopApplication(title, url, width, height, resizable);
  }
}
