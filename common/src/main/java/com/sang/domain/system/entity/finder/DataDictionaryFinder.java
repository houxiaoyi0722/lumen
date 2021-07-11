package com.sang.domain.system.entity.finder;

import com.sang.domain.system.entity.DataDictionary;
import io.ebean.Finder;
import lombok.Builder;

@Builder
public class DataDictionaryFinder extends Finder<Long, DataDictionary> {

  /**
   * Construct using the default EbeanServer.
   */
  public DataDictionaryFinder() {
    super(DataDictionary.class);
  }

}

