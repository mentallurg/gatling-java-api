package com.vgalloy.gatlingjavaapi.test;

import static com.vgalloy.gatlingjavaapi.api.dsl.assertion.JavaAssertionSupport.global;
import static com.vgalloy.gatlingjavaapi.api.dsl.core.JavaCoreDsl.scenario;
import static com.vgalloy.gatlingjavaapi.api.dsl.core.JavaInjectionSupport.atOnceUsers;
import static com.vgalloy.gatlingjavaapi.api.dsl.http.JavaHttpDsl.http;

import com.vgalloy.gatlingjavaapi.api.dsl.core.wrapper.impl.ScenarioBuilderWrapper;
import com.vgalloy.gatlingjavaapi.api.dsl.http.wrapper.HttpProtocolBuilderWrapper;
import com.vgalloy.gatlingjavaapi.api.service.JavaGatlingResultAnalyzer;
import com.vgalloy.gatlingjavaapi.api.service.JavaGatlingRunner;
import com.vgalloy.gatlingjavaapi.api.service.JavaSimulation;
import com.vgalloy.gatlingjavaapi.api.service.SimulationResult;
import com.vgalloy.gatlingjavaapi.server.TestServerConfig;
import com.vgalloy.gatlingjavaapi.simulation.AssertionSimulation;
import io.gatling.app.RunResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Created by Vincent Galloy on 23/02/2017.
 *
 * @author Vincent Galloy.
 */
@SpringBootTest(
    classes = TestServerConfig.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResultAnalyzerIT {

  @LocalServerPort private int serverPort;

  @Test
  void simple() {
    AssertionSimulation.port = serverPort;
    RunResult runResult = JavaGatlingRunner.getInstance().run(AssertionSimulation.class);
    SimulationResult simulationResult = JavaGatlingResultAnalyzer.getInstance().load(runResult);

    Assertions.assertFalse(simulationResult.isSuccess());
    JavaGatlingResultAnalyzer.getInstance().generateHtml(runResult);
  }

  @Test
  void generation() {
    JavaGatlingRunner javaGatlingRunner = JavaGatlingRunner.getInstance();
    JavaGatlingResultAnalyzer javaGatlingResultAnalyzer = JavaGatlingResultAnalyzer.getInstance();

    ScenarioBuilderWrapper scn = scenario("MyScenario").exec(http("request_1").get("/home"));
    HttpProtocolBuilderWrapper httpConf = http().baseURL("http://localhost:" + serverPort);

    JavaSimulation javaSimulation =
        JavaSimulation.builder()
            .scenario(scn.inject(atOnceUsers(2)))
            .protocols(httpConf)
            .assertions(
                global().responseTime().max().lt(2),
                global().successfulRequests().percent().gt(105d))
            .build();

    RunResult runResult = javaGatlingRunner.run(javaSimulation);
    javaGatlingResultAnalyzer.generateHtml(runResult);
  }
}
