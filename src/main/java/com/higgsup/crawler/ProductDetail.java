package com.higgsup.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetail {

  private WebDriver driver;

  private String link;

  private WebElement rootDiv;

  private ArrayList<String> listThumbnail;

  private String brandName;

  private String productDescription;

  private ArrayList<HashMap<String, String>> reviewComments = new ArrayList();

  private Double ratingPoint;

  private int totalRating;

  public ProductDetail(String link) {

    ChromeOptions options = new ChromeOptions();
    options.addArguments(ApplicationProperties.PROXY_SETTING)
        .addArguments(ApplicationProperties.DRIVER_SCREEN_SIZE)
        .addArguments(ApplicationProperties.DRIVER_HEADLESS)
        .addArguments(ApplicationProperties.DRIVER_DISABLE_EXTENSION);
    this.driver = new ChromeDriver(options);
    try {
      this.driver.get(link);
      this.rootDiv = this.driver.findElement(
          By.xpath("//div[@class='pdp-block pdp-block__main-information']"));
    } catch (Exception e) {
      System.out.println(e);
    }
    this.setListThumbnail(this.rootDiv);
    this.setBrandName(this.rootDiv);
    this.setReviewComments(this.rootDiv);
    this.setProductDescription(this.rootDiv);
    this.setRatingPoint(this.rootDiv);
    this.setTotalRating(this.rootDiv);

    this.driver.close();
    this.driver.quit();
  }

  public String getListThumbnail() {
    String result = "";
    for (String elm : this.listThumbnail) {
      result += elm + ";";
    }
    return result;
  }

  public void setListThumbnail(WebElement rootDiv) {
    this.listThumbnail = new ArrayList<String>();
    WebElement imageFrame = rootDiv
        .findElement(By.xpath("//div[@class='pdp-block module']"));
    List<WebElement> listImages = imageFrame.findElements(By.xpath(
        "//div[@class='item-gallery__image-wrapper']/img[@class='pdp-mod-common-image item-gallery__thumbnail-image']"));
    for (WebElement image : listImages) {
      this.listThumbnail.add(image.getAttribute("src").replace("_.webp", "")
          .replace("_80x80q80.jpg", ""));
    }
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(WebElement rootDiv) {
    WebElement brandName = rootDiv.findElement(By.xpath(
        "//div[@class='pdp-product-brand']//*[@class='pdp-link pdp-link_size_s pdp-link_theme_blue pdp-product-brand__brand-link']"));
    this.brandName = brandName.getText();
  }

  public String getProductDescription() {
    return "<pre>" + productDescription + "</pre>";
  }

  public void setProductDescription(WebElement rootDiv) {
    WebElement detail = rootDiv.findElement(By.xpath(
        "//div[@class='html-content pdp-product-highlights']"));
    this.productDescription = detail.getText();
  }

  public ArrayList<HashMap<String, String>> getReviewComments() {
    return reviewComments;
  }

  public void setReviewComments(WebElement rootDiv) {
    this.reviewComments = new ArrayList<HashMap<String, String>>();
    List<WebElement> reviewers = rootDiv.findElements(
        By.xpath(
            "//div[@class='mod-reviews']//*[@class='item']//div[@class='middle']"));
    List<WebElement> comments = rootDiv.findElements(
        By.xpath(
            "//div[@class='mod-reviews']//*[@class='item']//div[@class='item-content']//div[@class='content']"));

    for (int i = 0; i < reviewers.size(); i++) {
      HashMap<String, String> temp = new HashMap();
      String user = reviewers.get(i).getText().replace("bởi ", "")
          .replace("Chứng nhận đã mua hàng", "");
      String comment = comments.get(i).getText();

      temp.put("user", user);
      temp.put("comment", comment);
      this.reviewComments.add(temp);
    }
  }

  public Double getRatingPoint() {
    return ratingPoint;
  }

  public void setRatingPoint(WebElement rootDiv) {
    WebElement rating = rootDiv.findElement(By.xpath(
        "//div[@id='module_product_review']//div[@class='score']//span[@class='score-average']"));
    this.ratingPoint = Double.valueOf(rating.getText());
  }

  public int getTotalRating() {
    return this.totalRating;
  }

  public void setTotalRating(WebElement rootDiv) {
    WebElement totalRatingTmp = rootDiv.findElement(By.xpath(
        "//div[@id='module_product_review']//div[@class='count']"));
    this.totalRating = Integer
        .parseInt(totalRatingTmp.getText().replace(" đánh giá", ""));
  }

  //  public static void main(String args[]) throws Exception {
  //
  //    System.setProperty("webdriver.chrome.driver",
  //        "C:\\Users\\thanhdt\\project\\crawl-data\\chromedriver.exe");
  //
  //    ChromeOptions options = new ChromeOptions();
  //    String proxy = "78.108.108.192:61847";
  //    options.addArguments("start-maximized");
  //    options.addArguments("headless");
  //    //    options.addArguments("window-size=1920x10000");
  //    options.addArguments("--disable-extensions");
  //    options.addArguments("--proxy-server=http://" + proxy);
  //    WebDriver driver = new ChromeDriver(options);
  //
  //    ProductDetail detail = new ProductDetail(
  //        "https://www.lazada.vn/products/doc-va-la-dien-thoai-mini-x8-dien-thoai-mini-sieu-nho-v8-dien-thoai-2-sim-dien-thoai-hoc-sinh-goodshop4u-i258425109-s358408526.html?spm=a2o4n.searchlistcategory.list.20.29432590QDZCAn&search=1");
  //    System.out.println(detail.getReviewComments());
  //
  //  }
}
