package com.higgsup.xshop.common;

import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.ProductDetailDTO;
import com.higgsup.xshop.entity.Product;
import org.springframework.beans.BeanUtils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class DataUtil {

  public static String removeAccent(String input) {
    String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(temp).replaceAll("");
  }

  public static ProductDTO mapProductDTO(Product product) {
    ProductDTO productDTO = new ProductDTO();
    BeanUtils.copyProperties(product, productDTO);
    String[] imgUrls = product.getImgUrl().split(";");
    productDTO.setMainImgUrl(imgUrls[0]);
    return productDTO;
  }

  public static ProductDetailDTO mapProductDetailDTO(Product product) {
    ProductDetailDTO result = new ProductDetailDTO();
    BeanUtils.copyProperties(product, result);
    result.setCategoryId(product.getCategory().getId());
    result.setSupplierId(product.getSupplier().getId());
    result.setSupplierName(product.getSupplier().getName());
    result.setSupplierAddress(product.getSupplier().getAddress());
    return result;
  }
}
