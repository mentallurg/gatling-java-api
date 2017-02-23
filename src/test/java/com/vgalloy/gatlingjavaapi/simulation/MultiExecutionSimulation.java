package com.vgalloy.gatlingjavaapi.simulation;

import com.vgalloy.gatlingjavaapi.api.dsl.core.wrapper.StructureSupportWrapper;
import com.vgalloy.gatlingjavaapi.api.dsl.core.wrapper.ScenarioBuilderWrapper;
import com.vgalloy.gatlingjavaapi.api.dsl.http.wrapper.HttpProtocolBuilderWrapper;
import com.vgalloy.gatlingjavaapi.api.dsl.core.wrapper.SimulationWrapper;

import java.util.concurrent.TimeUnit;

import static com.vgalloy.gatlingjavaapi.api.dsl.core.JavaCoreDSL.exec;
import static com.vgalloy.gatlingjavaapi.api.dsl.core.JavaCoreDSL.scenario;
import static com.vgalloy.gatlingjavaapi.api.dsl.core.JavaInjectionSupport.atOnceUsers;
import static com.vgalloy.gatlingjavaapi.api.dsl.http.JavaHttpDSL.http;

/**
 * Created by Vincent Galloy on 23/02/2017.
 *
 * @author Vincent Galloy.
 */
public class MultiExecutionSimulation extends SimulationWrapper {

	public static int port = 8082;

	@Override
	protected void configure() {
		HttpProtocolBuilderWrapper httpConf = http()
				.baseURL("http://localhost:" + port)
				.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.doNotTrackHeader("1")
				.acceptLanguageHeader("en-US,en;q=0.5")
				.acceptEncodingHeader("gzip, deflate")
				.userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");

		StructureSupportWrapper chain1 = exec(http("Home").get("/home"))
				.pause(500, TimeUnit.MILLISECONDS)
				.exec(http("Home").get("/home"));

		StructureSupportWrapper chain2 = exec(http("Home").get("/home"))
				.pause(1, TimeUnit.SECONDS)
				.exec(http("Home").get("/home"));

		ScenarioBuilderWrapper scn = scenario("MyScenario")
				.exec(chain1, chain2);

		setUp(
				scn.inject(atOnceUsers(2))
		).protocols(httpConf);
	}
}
