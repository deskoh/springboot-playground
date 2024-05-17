package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rewards.client")
public class Settings {
  private final String host;
  private final int port;

  public Settings(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  /*public void setHost(String host) {
    this.host = host;
  }*/

  public int getPort() {
    return port;
  }

  /*public void setPort(int port) {
    this.port = port;
  }*/
}
