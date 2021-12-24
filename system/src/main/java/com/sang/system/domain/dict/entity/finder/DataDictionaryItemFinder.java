package com.sang.system.domain.dict.entity.finder;

import com.sang.system.domain.dict.entity.DataDictionaryItem;
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

