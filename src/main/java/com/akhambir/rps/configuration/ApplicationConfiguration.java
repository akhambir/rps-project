package com.akhambir.rps.configuration;

import com.akhambir.rps.model.Action;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApplicationConfiguration {

  @Bean
  public Map<Integer, Action> actionsMap() {
    var actionsMap = new HashMap<Integer, Action>();
    actionsMap.put(1, Action.ROCK);
    actionsMap.put(2, Action.PAPER);
    actionsMap.put(3, Action.SCISSORS);
    return actionsMap;
  }

  @Bean
  public Map<Action, Action> counterPickMap() {
    var counterPickMap = new HashMap<Action, Action>();
    counterPickMap.put(Action.ROCK, Action.SCISSORS);
    counterPickMap.put(Action.PAPER, Action.ROCK);
    counterPickMap.put(Action.SCISSORS, Action.PAPER);
    return counterPickMap;
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }
}
