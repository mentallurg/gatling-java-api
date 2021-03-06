package com.vgalloy.gatlingjavaapi.api.service;

import com.vgalloy.gatlingjavaapi.api.dsl.core.wrapper.impl.PopulationBuilderWrapper;
import com.vgalloy.gatlingjavaapi.api.dsl.http.wrapper.HttpProtocolBuilderWrapper;
import io.gatling.commons.stats.assertion.Assertion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vincent Galloy on 24/02/2017.
 *
 * @author Vincent Galloy.
 */
public final class JavaSimulation {

  private final PopulationBuilderWrapper[] populationBuilderWrappers;
  private final HttpProtocolBuilderWrapper[] httpProtocolBuilderWrappers;
  private final Assertion[] assertions;

  private JavaSimulation(
      List<PopulationBuilderWrapper> populationBuilderWrappers,
      List<HttpProtocolBuilderWrapper> httpProtocolBuilderWrappers,
      List<Assertion> assertions) {
    Objects.requireNonNull(populationBuilderWrappers);
    Objects.requireNonNull(httpProtocolBuilderWrappers);
    Objects.requireNonNull(assertions);

    this.populationBuilderWrappers =
        populationBuilderWrappers.toArray(new PopulationBuilderWrapper[0]);
    this.httpProtocolBuilderWrappers =
        httpProtocolBuilderWrappers.toArray(new HttpProtocolBuilderWrapper[0]);
    this.assertions = assertions.toArray(new Assertion[0]);
  }

  public static Builder builder() {
    return new Builder();
  }

  public PopulationBuilderWrapper[] getPopulationBuilderWrappers() {
    return populationBuilderWrappers;
  }

  public HttpProtocolBuilderWrapper[] getHttpProtocolBuilderWrapper() {
    return httpProtocolBuilderWrappers;
  }

  public Assertion[] getAssertions() {
    return assertions;
  }

  public static class Builder {

    private final List<PopulationBuilderWrapper> populationBuilderWrappers = new ArrayList<>();
    private final List<HttpProtocolBuilderWrapper> httpProtocolBuilderWrappers = new ArrayList<>();
    private final List<Assertion> assertions = new ArrayList<>();

    public Builder scenario(final PopulationBuilderWrapper... populationBuilderWrappers) {
      Objects.requireNonNull(populationBuilderWrappers, "populationBuilderWrappers");

      this.populationBuilderWrappers.addAll(Arrays.asList(populationBuilderWrappers));
      return this;
    }

    public Builder protocols(final HttpProtocolBuilderWrapper... httpProtocolBuilderWrapper) {
      Objects.requireNonNull(httpProtocolBuilderWrapper, "httpProtocolBuilderWrapper");

      this.httpProtocolBuilderWrappers.addAll(Arrays.asList(httpProtocolBuilderWrapper));
      return this;
    }

    public Builder assertions(final Assertion... assertions) {
      Objects.requireNonNull(assertions, "assertions");

      this.assertions.addAll(Arrays.asList(assertions));
      return this;
    }

    public JavaSimulation build() {
      return new JavaSimulation(populationBuilderWrappers, httpProtocolBuilderWrappers, assertions);
    }
  }
}
