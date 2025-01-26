package io.github.dumijdev.desktopfy.server;

import java.io.IOException;

public interface Server {
  int port();
  String host();
  String url();
  void start() throws IOException;
  void stop();
}
