package com.sang.entity;

import com.sang.entity.finder.ProductFinder;
import com.sang.entity.query.QProduct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Product entity bean.
 */
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

  public Product(String sku, String name) {
    this.sku = sku;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Return sku.
   */
  public String getSku() {
    return sku;
  }

  /**
   * Set sku.
   */
  public void setSku(String sku) {
    this.sku = sku;
  }

  /**
   * Return name.
   */
  public String getName() {
    return name;
  }

  /**
   * Set name.
   */
  public void setName(String name) {
    this.name = name;
  }

  public static Product byName(String name) {
    return new QProduct()
      .name.equalTo(name)
      .findOne();
  }
}
