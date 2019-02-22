package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.DataUtil;
import com.higgsup.xshop.common.ProductStatus;
import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.RelatedProductDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.higgsup.xshop.common.ConstantNumber.NUMBER_OF_RELATED_PRODUCT;

@Service
public class ProductService implements IProductService {

  private final ProductRepository productRepository;

  public ProductService(
      ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> getProductTopSale() {
    List<ProductDTO> productDTOs = new ArrayList<>();
    Pageable pageRequest = PageRequest
        .of(0, 18, Sort.Direction.DESC, "discountPercent");
    Page<Product> products = productRepository.findAll(pageRequest);
    products.getContent()
        .forEach(product -> productDTOs.add(mapProductDTO(product)));
    return productDTOs;
  }

  @Override
  @Transactional(readOnly = true)
  public IPagedResponse<List<ProductDTO>> searchProduct(
      ProductCriteriaDTO criteria, int pageSize, int pageIndex) {
    ResponseMessage<List<ProductDTO>> responseMessage = new ResponseMessage<>();
    IPagedResponse<List<ProductDTO>> iPagedResponse = new IPagedResponse<>(
        responseMessage);

    List<ProductDTO> productDTOs = new ArrayList<>();

    if (!isEmptyCriteria(criteria)) {
      Pageable pageRequest = PageRequest
          .of(pageIndex, pageSize, Sort.Direction.ASC, "unitPrice");

      Page<Product> productPage = productRepository
          .findAll(Specification.where(buildCriteria(criteria)), pageRequest);

      iPagedResponse.setTotalItem(productPage.getTotalElements());
      iPagedResponse.setTotalPage(productPage.getTotalPages());

      if (!CollectionUtils.isEmpty(productPage.getContent())) {
        productPage.getContent()
            .forEach(product -> productDTOs.add(mapProductDTO(product)));
      }
    }

    responseMessage.setData(productDTOs);

    iPagedResponse.setPageIndex(pageIndex);
    iPagedResponse.setPageSize(pageSize);

    return iPagedResponse;
  }

  @Override
  @Transactional(readOnly = true)
  public List<RelatedProductDTO> getRelatedProduct(Integer productId) {
    List<RelatedProductDTO> relatedProductDTOS = new ArrayList<>();
    Product searchedProduct = productRepository.getOne(productId);
    Integer category_id = searchedProduct.getCategory().getId();

    List<Product> relatedProducts = productRepository
        .findRelatedProductByCategoryId(category_id, NUMBER_OF_RELATED_PRODUCT.getValue());

    relatedProducts.forEach(product -> relatedProductDTOS
        .add(convertEntityToRelatedProductDTO(product)));

    return relatedProductDTOS;
  }

  private Specification<Product> buildCriteria(ProductCriteriaDTO criteria) {
    Specification<Product> conditionWhere = Specification.where(
        (
            (root, query, cb) -> cb.conjunction()
        ));

    if (!StringUtils.isEmpty(criteria.getTextSearch())) {
      String textSearch = criteria.getTextSearch().trim();
      textSearch = DataUtil.removeAccent(textSearch);
      conditionWhere = Specification.where(conditionWhere).and(
          buildCriteriaTextSearch(StringUtils.replace(textSearch, "d", "đ")))
          .or(buildCriteriaTextSearch(
              StringUtils.replace(textSearch, "đ", "d")));
    }

    if (criteria.getAvgRating() != null) {
      Integer avgRating = criteria.getAvgRating();
      conditionWhere = Specification.where(conditionWhere)
          .and(buildCriteriaAvgRating(avgRating));
    }

    if (criteria.getSupplierId() != null) {
      Integer supplierId = criteria.getSupplierId();
      conditionWhere = Specification.where(conditionWhere)
          .and(buildCriteriaSupplier(supplierId));
    }

    if (criteria.getStatus() != null) {
      ProductStatus status = criteria.getStatus();
      conditionWhere = Specification.where(conditionWhere)
          .and(buildCriteriaStatus(status));
    }

    if (criteria.getFromUnitPrice() != null
        && criteria.getToUnitPrice() != null) {
      BigDecimal fromUnitPrice = criteria.getFromUnitPrice();
      BigDecimal toUnitPrice = criteria.getToUnitPrice();
      conditionWhere = Specification.where(conditionWhere)
          .and(buildCriteriaUnitPrice(fromUnitPrice, toUnitPrice));
    }

    return conditionWhere;
  }

  private Specification<Product> buildCriteriaTextSearch(String textSearch) {
    return (root, query, cb) -> cb.or(cb.like(root.get("name"), '%' + textSearch + '%'),
        cb.like(root.get("shortDesc"), '%' + textSearch + '%'),
        cb.like(root.get("fullDesc"), '%' + textSearch + '%'));
  }

  private Specification<Product> buildCriteriaSupplier(Integer supplierId) {
    return (root, query, cb) -> cb.equal(root.get("supplierId"), supplierId);
  }

  private Specification<Product> buildCriteriaStatus(ProductStatus status) {
    return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("status"), status);
  }

  private Specification<Product> buildCriteriaAvgRating(Integer avgRating) {
    return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("avgRating"), avgRating);
  }

  private Specification<Product> buildCriteriaUnitPrice(
      BigDecimal fromUnitPrice, BigDecimal toUnitPrice) {
    return (root, query, cb) -> cb.and(cb.lessThanOrEqualTo(root.get("unitPrice"), toUnitPrice),
        cb.greaterThanOrEqualTo(root.get("unitPrice"), fromUnitPrice));
  }

  private ProductDTO mapProductDTO(Product product) {
    ProductDTO productDTO = new ProductDTO();
    BeanUtils.copyProperties(product, productDTO);
    String[] imgUrls = product.getImgUrl().split(";");
    productDTO.setMailImgUrl(imgUrls[0]);
    return productDTO;
  }

  private RelatedProductDTO convertEntityToRelatedProductDTO(Product product) {
    RelatedProductDTO relatedProductDTO = new RelatedProductDTO();
    BeanUtils.copyProperties(product, relatedProductDTO);
    String[] imgUrls = product.getImgUrl().split(";");
    relatedProductDTO.setMainImgUrl(imgUrls[0]);
    return relatedProductDTO;
  }

  private boolean isEmptyCriteria(ProductCriteriaDTO criteria) {
    if (!StringUtils.isEmpty(criteria.getTextSearch())) {
      return false;
    }
    if (criteria.getStatus() != null) {
      return false;
    }
    if (criteria.getSupplierId() != null) {
      return false;
    }
    if (criteria.getFromUnitPrice() != null
        && criteria.getToUnitPrice() != null) {
      return false;
    }
    return criteria.getAvgRating() == null;
  }

}
