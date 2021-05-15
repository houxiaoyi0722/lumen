package com.sang.entity.finder;

import com.sang.entity.Product;
import io.ebean.Finder;

public class ProductFinder extends Finder<Long, Product> {

  /**
   * Construct using the default EbeanServer.
   */
  public ProductFinder() {
    super(Product.class);
  }

}

