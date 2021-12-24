package com.sang.system.domain.dict.entity.finder;

import com.sang.system.domain.dict.entity.DictionaryItem;
import io.ebean.Finder;
import lombok.Builder;

@Builder
public class DictionaryItemFinder extends Finder<Long, DictionaryItem> {

  /**
   * Construct using the default EbeanServer.
   */
  public DictionaryItemFinder() {
    super(DictionaryItem.class);
  }

}

