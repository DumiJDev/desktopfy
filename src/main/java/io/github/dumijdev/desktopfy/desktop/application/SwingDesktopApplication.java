package io.github.dumijdev.desktopfy.desktop.application;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import me.friwi.jcefmaven.impl.progress.ConsoleProgressHandler;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class SwingDesktopApplication extends JFrame implements DesktopApplication {
  private final String url;
  private CefBrowser browser;
  private CefClient client;
  private CefApp app;

  public SwingDesktopApplication(String title, String url, double width, double height, boolean resizable) {
    this.url = url;
    setTitle(title);

    setSize((int) width, (int) height);
    setResizable(resizable);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    var frame = this;

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {

        int result = JOptionPane.showConfirmDialog(
            frame,
            "Are you sure you want to exit?",
            "Confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
          close();
        }
      }
    });
  }


  @Override
  public void start() {
    JPanel jcefPanel = new JPanel(new BorderLayout());

    CefAppBuilder builder = new CefAppBuilder();

    builder.setInstallDir(new File("jcef-bundle"));
    builder.setProgressHandler(new ConsoleProgressHandler());
    builder.addJcefArgs("--disable-gpu");
    builder.getCefSettings().windowless_rendering_enabled = false;
    builder.getCefSettings().user_agent = "Desktopfy Agent";
    builder.getCefSettings().root_cache_path = new File("jcef-cache").getAbsolutePath();

    try {
      app = builder.build();
    } catch (IOException | UnsupportedPlatformException | InterruptedException | CefInitializationException e) {
      throw new RuntimeException(e);
    }

    client = app.createClient();
    browser = client.createBrowser(url, false, false);

    jcefPanel.add(browser.getUIComponent(), BorderLayout.CENTER);

    getContentPane().add(jcefPanel, BorderLayout.CENTER);

    setVisible(true);
  }

  @Override
  public void close() {
    dispose();
    browser.close(true);
    client.dispose();
    app.dispose();
    System.exit(0);
  }


}
