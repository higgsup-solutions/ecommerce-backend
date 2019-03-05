package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.DataUtil;
import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.ProductDetailDTO;
import com.higgsup.xshop.dto.RatingDTO;
import com.higgsup.xshop.dto.RelatedProductDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.repository.ReviewRepository;
import com.higgsup.xshop.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.higgsup.xshop.common.ConstantNumber.NUMBER_OF_RELATED_PRODUCT;
import static com.higgsup.xshop.common.ConstantNumber.PAGE_INDEX;

@Service
public class ProductService implements IProductService {

  private final ProductRepository productRepository;

  private final CategoryService categoryService;

  private final ReviewRepository reviewRepository;

  public ProductService(
      ProductRepository productRepository,
      CategoryService categoryService,
      ReviewRepository reviewRepository) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
    this.reviewRepository = reviewRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> getProductTopSale() {
    List<ProductDTO> productDTOs = new ArrayList<>();
    Pageable pageRequest = PageRequest
        .of(0, 18, Sort.Direction.DESC, "discountPercent");
    Page<Product> products = productRepository.findAll(pageRequest);
    products.getContent()
        .forEach(product -> productDTOs.add(DataUtil.mapProductDTO(product)));
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

      Page<Product> productPage = productRepository
          .searchByCriteria(criteria, pageIndex, pageSize);
      iPagedResponse.setTotalItem(productPage.getTotalElements());
      iPagedResponse.setTotalPage(productPage.getTotalPages());

      if (!CollectionUtils.isEmpty(productPage.getContent())) {
        productPage.getContent()
            .forEach(
                product -> productDTOs.add(DataUtil.mapProductDTO(product)));
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
    Pageable pageRequest = PageRequest
        .of(PAGE_INDEX.getValue(), NUMBER_OF_RELATED_PRODUCT.getValue(),
            Sort.Direction.DESC, "unitPrice");
    Page<Product> relatedProducts = productRepository
        .findAllByCategory_Id(category_id, pageRequest);

    relatedProducts.forEach(product -> relatedProductDTOS
        .add(convertEntityToRelatedProductDTO(product)));

    return relatedProductDTOS;
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

  @Override
  @Transactional(readOnly = true)
  public IPagedResponse<ProductDetailDTO> getProductDetail(Integer productId) {

    ResponseMessage<ProductDetailDTO> responseMessage = new ResponseMessage<>();
    IPagedResponse<ProductDetailDTO> iPagedResponse = new IPagedResponse<>(
        responseMessage);

    Optional<Product> product = this.productRepository
        .findProductById(productId);

    ProductDetailDTO result = DataUtil.mapProductDetailDTO(product
        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND,
            "product not found")));

    List<RatingDTO> ratingCount = this.getProductRating(productId);

    if (!ratingCount.isEmpty()) {
      result.setRatingCount(ratingCount);
    }

    responseMessage.setData(result);

    return iPagedResponse;
  }

  private List<RatingDTO> getProductRating(Integer productId) {
    List<RatingDTO> result = new ArrayList<>();

    List<RatingDTO> listRating = this.reviewRepository
        .countRatingByProductId(productId);

    Map<Short, BigInteger> tempMap = listRating.stream().collect(
        Collectors.toMap(RatingDTO::getRating, RatingDTO::getCounting));

    for (Short i = 1; i <= 5; i++) {
      result.add(new RatingDTO(i, tempMap.getOrDefault(i, BigInteger.ZERO)));
    }
    return result;
  }

}
