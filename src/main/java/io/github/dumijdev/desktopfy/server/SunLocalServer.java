package io.github.dumijdev.desktopfy.server;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class SunLocalServer implements Server {
  private int port = Math.abs((int) System.currentTimeMillis() % 10_000);
  private final Path mainPath;
  private HttpServer server = null;
  private final Logger logger = LoggerFactory.getLogger(SunLocalServer.class);

  public SunLocalServer(Path mainPath, int port) {
    this.mainPath = mainPath;
    this.port = port;
  }

  public SunLocalServer(Path mainPath) {
    this.mainPath = mainPath;
  }

  @Override
  public int port() {
    return port;
  }

  @Override
  public String host() {
    return "localhost";
  }

  @Override
  public String url() {
    return "http://" + host() + ":" + port;
  }

  @Override
  public void start() throws IOException {
    if (server != null) {
      logger.warn("Server is already started");
      throw new IllegalStateException("server is already started");
    }

    Path baseDir = mainPath.getParent();
    String indexFileName = mainPath.getFileName().toString();

    logger.info("Starting server on port {}", port);
    server = HttpServer.create(new InetSocketAddress(port), port);
    server.setExecutor(Executors.newCachedThreadPool());

    logger.info("Creating server context");
    server.createContext("/", exchange -> {
      String requestPath = exchange.getRequestURI().getPath();
      if (requestPath.equals("/")) {
        requestPath = "/" + indexFileName;
      }

      Path filePath = baseDir.resolve(requestPath.substring(1)).normalize();

      if (!filePath.startsWith(baseDir)) {
        exchange.sendResponseHeaders(403, -1);
        return;
      }

      if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
        byte[] content = Files.readAllBytes(filePath);
        String contentType = guessContentType(filePath.toString());
        exchange.getResponseHeaders().add("Content-Type", contentType);
        exchange.sendResponseHeaders(200, content.length);
        exchange.getResponseBody().write(content);
      } else {
        exchange.sendResponseHeaders(404, -1);
      }
      exchange.close();
    });

    server.start();
  }

  @Override
  public void stop() {
    server.stop(10);
    server = null;
  }

  private String guessContentType(String filePath) {
    if (filePath.endsWith(".html")) {
      return "text/html";
    } else if (filePath.endsWith(".css")) {
      return "text/css";
    } else if (filePath.endsWith(".js")) {
      return "application/javascript";
    } else if (filePath.endsWith(".png")) {
      return "image/png";
    } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
      return "image/jpeg";
    } else if (filePath.endsWith(".gif")) {
      return "image/gif";
    } else {
      return "application/octet-stream";
    }
  }
}
