package io.github.dumijdev.desktopfy.desktop.application.factories;

import io.github.dumijdev.desktopfy.desktop.application.DesktopApplication;

public abstract class DesktopApplicationFactory {
  public abstract DesktopApplication createDesktopApplication(String title, String url, double width, double height, boolean resizable);
}
