package com.vgalloy.gatlingjavaapi.api.dsl.core.wrapper.trait;

import io.gatling.core.structure.StructureBuilder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;

/**
 * Created by Vincent Galloy on 28/02/2017.
 *
 * @author Vincent Galloy.
 */
public interface PausesWrapper<
        STRUCTURE extends StructureBuilder<STRUCTURE>,
        WRAPPER extends StructureBuilderWrapper<STRUCTURE, WRAPPER>>
    extends ExecsWrapper<STRUCTURE, WRAPPER> {

  default WRAPPER pause(long length, TimeUnit unit) {
    Objects.requireNonNull(unit);

    return newInstance(get().pause(Duration.apply(length, unit)));
  }

  default WRAPPER pause(final long timeMillis) {
    return pause(timeMillis, TimeUnit.MILLISECONDS);
  }
}
