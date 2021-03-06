package com.vgalloy.gatlingjavaapi.api.dsl.core.wrapper.trait;

import com.vgalloy.gatlingjavaapi.api.dsl.feeder.wrapper.SourceFeederBuilderWrapper;
import com.vgalloy.gatlingjavaapi.internal.util.expression.Expression;
import io.gatling.core.structure.StructureBuilder;
import java.util.Objects;

/**
 * Created by Vincent Galloy on 28/02/2017.
 *
 * @author Vincent Galloy.
 */
public interface FeedsWrapper<
        STRUCTURE extends StructureBuilder<STRUCTURE>,
        WRAPPER extends StructureBuilderWrapper<STRUCTURE, WRAPPER>>
    extends ExecsWrapper<STRUCTURE, WRAPPER> {

  default WRAPPER feed(SourceFeederBuilderWrapper recordSeqFeederBuilderWrapper) {
    return feed(recordSeqFeederBuilderWrapper, 1);
  }

  @SuppressWarnings("unchecked")
  default WRAPPER feed(SourceFeederBuilderWrapper recordSeqFeederBuilderWrapper, int number) {
    Objects.requireNonNull(recordSeqFeederBuilderWrapper);

    return newInstance(
        (STRUCTURE) get().feed(recordSeqFeederBuilderWrapper.get(), Expression.of(number)));
  }
}
