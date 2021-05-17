package com.sang.entity;

import com.sang.entity.finder.ProductFinder;
import com.sang.entity.query.QProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Product entity bean.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
public class Product {

  public static final ProductFinder find = new ProductFinder();

  @Id
  Long id;

  @Size(max = 20)
  String sku;

  @Size(max = 50)
  String name;

  public static Product byName(String name) {
    return new QProduct()
            .name.equalTo(name)
            .findOne();
  }
}
