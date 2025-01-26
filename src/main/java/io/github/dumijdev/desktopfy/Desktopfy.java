package io.github.dumijdev.desktopfy;

import io.github.dumijdev.desktopfy.desktop.application.DesktopApplication;
import io.github.dumijdev.desktopfy.desktop.application.factories.DesktopApplicationFactory;
import io.github.dumijdev.desktopfy.desktop.application.factories.JavaFXDesktopApplicationFactory;
import io.github.dumijdev.desktopfy.desktop.application.factories.SwingDesktopApplicationFactory;
import io.github.dumijdev.desktopfy.server.Server;
import io.github.dumijdev.desktopfy.server.SunLocalServer;

import java.io.IOException;
import java.nio.file.Paths;

public class Desktopfy {
  private String url;
  private String htmlPath;
  private int width = 800;
  private int height = 600;
  private boolean resizable = true;
  private DesktopApplicationFactory applicationContainerFactory = new SwingDesktopApplicationFactory();
  private DesktopApplication app;
  private Server localServer;
  private String title;


  private Desktopfy(String url, String htmlPath) {
    if (url != null && htmlPath != null) {
      throw new IllegalStateException("Url or html path, not both");
    }

    if (htmlPath != null) {
      this.htmlPath = htmlPath;
      localServer = new SunLocalServer(Paths.get(htmlPath));
      this.url = localServer.url();
    }

    if (url != null) {
      this.url = url;
    }
  }

  public static Desktopfy url(String url) {
    return new Desktopfy(url, null);
  }

  public static Desktopfy mainPage(String htmlPath) {
    return new Desktopfy(null, htmlPath);
  }

  public Desktopfy width(int width) {
    this.width = width;
    return this;
  }

  public Desktopfy height(int height) {
    this.height = height;
    return this;
  }

  public Desktopfy size(int width, int height) {
    this.width = width;
    this.height = height;
    return this;
  }

  public Desktopfy resizable(boolean resizable) {
    this.resizable = resizable;
    return this;
  }

  public Desktopfy title(String title) {
    this.title = title;
    return this;
  }

  public Desktopfy applicationContainer(DesktopApplicationFactory applicationContainerFactory) {
    this.applicationContainerFactory = applicationContainerFactory;
    return this;
  }

  public Desktopfy javaFxContainer() {
    this.applicationContainerFactory = new JavaFXDesktopApplicationFactory();
    return this;
  }

  public Desktopfy swingContainer() {
    this.applicationContainerFactory = new SwingDesktopApplicationFactory();
    return this;
  }

  public Desktopfy open() throws IOException {
    if (applicationContainerFactory == null) {
      throw new IllegalArgumentException("Application container must not be null");
    }

    if (localServer != null) {
      localServer.start();
    }

    try {
      app = applicationContainerFactory.createDesktopApplication(title, url, width, height, resizable);
      app.start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return this;
  }

  public void close() {
    if (app == null) {
      throw new IllegalArgumentException("Application container must not be null");
    }

    if (localServer != null) {
      localServer.stop();
    }

    app.close();
  }
}

