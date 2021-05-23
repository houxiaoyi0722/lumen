package com.sang.system.domain.finder;

import com.sang.system.domain.DataDictionaryItem;
import io.ebean.Finder;
import lombok.Builder;

@Builder
public class DataDictionaryItemFinder extends Finder<Long, DataDictionaryItem> {

  /**
   * Construct using the default EbeanServer.
   */
  public DataDictionaryItemFinder() {
    super(DataDictionaryItem.class);
  }

}

