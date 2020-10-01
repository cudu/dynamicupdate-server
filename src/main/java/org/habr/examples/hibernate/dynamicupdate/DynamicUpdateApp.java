package org.habr.examples.hibernate.dynamicupdate;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "org.habr.examples.hibernate.dynamicupdate")
public class DynamicUpdateApp {
  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(DynamicUpdateApp.class)
        .bannerMode(Banner.Mode.CONSOLE)
        .logStartupInfo(true)
        .run(args);
  }
}
