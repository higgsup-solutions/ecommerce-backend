package com.higgsup.crawler;

import java.util.Random;

public class Product {

  private int id;

  private int amount;

  private int category_id;

  private String status;

  private String nameProduct;

  private String price;

  private String percent;

  private ProductDetail productDetail;

  String arrayStatus[] = { "NEW", "USED" };

  public Product(String nameProduct, String price, String percent,
      String status, ProductDetail productDetail) {
    this.nameProduct = nameProduct;
    this.price = price;
    this.percent = percent;
    this.status = status;
    this.productDetail = productDetail;
  }

  public Product() {
  }

  public int getCategoryId() {
    return category_id;
  }

  public void setCategoryId(int category_id) {
    this.category_id = category_id;
  }

  public void setProductDetail(ProductDetail productDetail) {
    this.productDetail = productDetail;
  }

  public void setNameProduct(String nameProduct) {
    this.nameProduct = nameProduct;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public void setPercent(String percent) {
    this.percent = percent;
  }

  public String getNameProduct() {
    return this.nameProduct;
  }

  public String getPrice() {
    return this.price.replace(" ₫", "").replace(".", "");
  }

  public String getPercent() {
    return this.percent.replace("%", "").replace("-", "");
  }

  public ProductDetail getProductDetail() {
    return productDetail;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public int getAmount() {
    Random r = new Random();
    return r.nextInt((100 - 1) + 1) + 1;
  }

  public String getStatus() {
    int rnd = new Random().nextInt(this.arrayStatus.length);
    return this.arrayStatus[rnd];
  }

  public double getWeight() {
    Random r = new Random();
    return (0.5 + (10.0 - 0.5) * r.nextDouble());
  }

  public String generateSQL() {
    String sql =
        "INSERT INTO product (id, name, short_desc, full_desc, category_id, brand_name, status, weight, available_item, unit_price, discount_percent, avg_rating, img_url, total_rating) VALUES"
            + "("
            + this.getId() + ", "
            + "'" + this.getNameProduct() + "'" + ", "
            + "'" + this.getNameProduct() + "'" + ", "
            + "'" + this.getProductDetail().getProductDescription() + "', "
            + this.getCategoryId() + ", "
            + "'" + this.getProductDetail().getBrandName() + "', "
            + "'" + this.getStatus() + "', " +
            +this.getWeight() + ", "
            + this.getAmount() + ", "
            + this.getPrice() + ", "
            + this.getPercent() + ", "
            + this.getProductDetail().getRatingPoint() + ", "
            + "'" + this.getProductDetail().getListThumbnail() + "', "
            + this.getProductDetail().getTotalRating()
            + ");" + "\r\n\r\n";
    return sql;
  }

}
