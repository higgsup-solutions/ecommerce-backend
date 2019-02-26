package com.higgsup.crawler;

import java.util.Random;

public class Review {

  private int id;

  private int productId;

  private String reviewer;

  private int rating;

  private String commment;

  public Review(int productId, String reviewer, int rating,
      String commment) {
    this.productId = productId;
    this.reviewer = reviewer;
    this.rating = rating;
    this.commment = commment;
  }

  public Review() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public String getReviewer() {
    return this.reviewer;
  }

  public void setReviewer(String reviewer) {
    this.reviewer = reviewer;
  }

  public int getRating() {
    Random r = new Random();
    return r.nextInt(5);
  }

  public String getCommment() {
    return this.commment;
  }

  public void setCommment(String commment) {
    this.commment = commment;
  }

  public int getProductId() {
    return this.productId;
  }

  public String generateSQL() {
    String sql =
        "INSERT INTO review (id, product_id, reviewer, rating, `comment`) VALUES"
            + "("
            + this.getId() + ", "
            + this.getProductId() + ", "
            + "'" + this.getReviewer() + "'" + ", "
            + this.getRating() + ", "
            + "'" + this.getCommment() + "'"
            + ");" + "\r\n\r\n";
    return sql;
  }
}
