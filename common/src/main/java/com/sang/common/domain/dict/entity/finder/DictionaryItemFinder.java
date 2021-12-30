package com.sang.common.domain.dict.entity.finder;

import com.sang.common.domain.dict.entity.DictionaryItem;
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

