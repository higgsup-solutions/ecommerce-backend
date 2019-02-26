package com.higgsup.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Run {

  public static void main(String args[]) throws Exception {

    ArrayList<Product> productArrayList = new ArrayList<Product>();

    System.setProperty(ApplicationProperties.DRIVER_TYPE,
        ApplicationProperties.DRIVER_PATH);
    ChromeOptions options = new ChromeOptions();
    options.addArguments(ApplicationProperties.DRIVER_SCREEN_SIZE)
        .addArguments(ApplicationProperties.DRIVER_HEADLESS)
        .addArguments(ApplicationProperties.DRIVER_DISABLE_EXTENSION);
    WebDriver driver = new ChromeDriver(options);

    driver.get(ApplicationProperties.LINK_PARENT);

    WebElement rootDiv = driver.findElement(By.id("root"));

    List<WebElement> listItem = rootDiv.findElements(By.className("c5TXIP"));

    List<WebElement> listLink = rootDiv.findElements(By.xpath(
        "//*[@class='c5TXIP']//*[@class='c2iYAv']//*[@class='cRjKsc']//a"));

    for (int i = 0; i < listItem.size(); i++) {
      Product product = new Product();
      product.setCategoryId(ApplicationProperties.CATEGORY_ID);
      product.setId(Integer.valueOf(product.getCategoryId() + "" + i));
      List<WebElement> listText = listItem.get(i)
          .findElements(By.className("c3KeDq"));
      for (WebElement x : listText) {
        WebElement nameElm = x.findElement(By.className("c16H9d"));
        WebElement priceElm = x.findElement(By.className("c3gUW0"));
        WebElement percent = x.findElement(By.className("c3lr34"))
            .findElement(By.xpath("//span[@class='c1hkC1']"));
        product.setNameProduct(nameElm.getText());
        product.setPercent(percent.getText());
        product.setPrice(priceElm.getText());
      }
      String link = listLink.get(i).getAttribute("href");
      try {
        ProductDetail detail = new ProductDetail(link);
        product.setProductDetail(detail);
        productArrayList.add(product);
      } catch (Exception e) {

      }
    }

    /*write file*/
    BufferedWriter bw = null;
    FileWriter fw = null;

    try {
      fw = new FileWriter(ApplicationProperties.OUTPUT_FILE_PATH);
      bw = new BufferedWriter(fw);
      for (Product product : productArrayList) {

        bw.write(product.generateSQL());

        int reviewCounting = product.getProductDetail()
            .getReviewComments().size();

        for (int i = 0; i < reviewCounting; i++) {
          Map<String, String> elm = product.getProductDetail()
              .getReviewComments().get(i);
          Review review = new Review();
          review.setId(Integer.parseInt(product.getId() + "" + i));
          review.setProductId(product.getId());
          review.setReviewer(elm.get("user"));
          review.setCommment(elm.get("comment"));
          bw.write(review.generateSQL());
        }
      }
      System.out.println("Done");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bw != null)
          bw.close();
        if (fw != null)
          fw.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    System.out.println("Done");

    driver.close();
    driver.quit();
  }
}
