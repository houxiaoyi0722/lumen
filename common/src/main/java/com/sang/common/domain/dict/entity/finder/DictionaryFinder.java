package com.sang.common.domain.dict.entity.finder;

import com.sang.common.domain.dict.entity.Dictionary;
import io.ebean.Finder;
import lombok.Builder;

@Builder
public class DictionaryFinder extends Finder<Long, Dictionary> {

  /**
   * Construct using the default EbeanServer.
   */
  public DictionaryFinder() {
    super(Dictionary.class);
  }

}

