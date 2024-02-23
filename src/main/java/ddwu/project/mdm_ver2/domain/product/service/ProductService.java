package ddwu.project.mdm_ver2.domain.product.service;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import ddwu.project.mdm_ver2.domain.favorite.repository.FavoriteRepository;
import ddwu.project.mdm_ver2.domain.product.dto.ProductResponse;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.dto.ProductRequest;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.domain.review.repository.ReviewRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.exception.ResourceNotFoundException;
import ddwu.project.mdm_ver2.domain.category.repository.CategoryRepository;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;

    //  일반상품 등록
    @Transactional
    public CustomResponse<Product> addProduct(ProductRequest request) {
        try {
            Category category = categoryRepository.findByCateCode(request.getCategory());

            if (category == null) {
                throw new NotFoundException("카테고리를 찾을 수 없습니다: " + request.getCategory());
            }

            Product product = Product.builder()
                    .category(category)
                    .name(request.getName())
                    .price(request.getPrice())
                    .content(request.getContent())
                    .imgUrl(request.getProdImgUrl())
                    .reviewCnt(0L)
                    .build();

            Product addProduct = productRepository.save(product);

            return CustomResponse.onSuccess(addProduct);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 일반상품 수정
    @Transactional
    public CustomResponse<Product> updateProduct(Long prodId, ProductRequest updatedProduct) {
        try {
            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "prodId", prodId));

            if (product != null) {
                Category category = categoryRepository.findByCateCode(updatedProduct.getCategory());

                product.setCategory(category);
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                product.setContent(updatedProduct.getContent());
                product.setImgUrl(updatedProduct.getProdImgUrl());

                Product updateProduct = productRepository.save(product);

                return CustomResponse.onSuccess(updateProduct);
            }
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), "상품을 찾을 수 없습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }


    //  일반상품 삭제
    @Transactional
    public CustomResponse<Void> deleteProduct(Long prodId) {
        try {
            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "prodId", prodId));

            productRepository.delete(product);
            return CustomResponse.onSuccess(null);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    //  일반상품 조회
    @Transactional
    public CustomResponse<List<Product>> findAllProduct() {
        try {
            List<Product> productList = productRepository.findAll();
            return CustomResponse.onSuccess(productList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 일반상품 정렬
    @Transactional
    public CustomResponse<List<Product>> SortProduct(String sort, String cateCode) {
        try {
            List<Product> sortedProductList;
            if (cateCode != null && !cateCode.isEmpty()) {
                sortedProductList = sortProductsByCategory(sort, cateCode);
            } else {
                switch (sort) {
                    case "lowprice":
                        sortedProductList = productRepository.findAllByOrderByPriceAsc();
                        break;
                    case "highprice":
                        sortedProductList = productRepository.findAllByOrderByPriceDesc();
                        break;
                    case "newest":
                        sortedProductList = productRepository.findAllByOrderByIdDesc();
                        break;
                    default:
                        sortedProductList = productRepository.findAll();
                        break;
                }
            }
            return CustomResponse.onSuccess(sortedProductList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }


    // 카테고리 분류 후 일반상품 정렬
    @Transactional
    public List<Product> sortProductsByCategory(String sort, String cateCode) {
        List<Product> productList;

        switch (sort) {
            case "lowprice":
                productList = productRepository.findAllByCategoryCateCodeOrderByPriceAsc(cateCode);
                break;
            case "highprice":
                productList = productRepository.findAllByCategoryCateCodeOrderByPriceDesc(cateCode);
                break;
            case "newest":
                productList = productRepository.findAllByCategoryCateCodeOrderByIdDesc(cateCode);
                break;
            default:
                productList = productRepository.findAllByCategoryCateCode(cateCode);
                break;
        }

        return productList;
    }


    private List<Product> categorySort(String category, List<Product> defaultList, List<Product> categoryList) {
        return StringUtils.isNotBlank(category) ? categoryList : defaultList;
    }

    //일반상품 개별 조회
    @Transactional
    public CustomResponse<ProductResponse> getProduct(Principal principal, Long prodId) {
        try {
            ProductResponse response = setResponse(principal, prodId);
            return CustomResponse.onSuccess(response);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    //일반상품 개수 - 전체
    public CustomResponse<Long> getProductCount() {
        try {
            long count = productRepository.count();
            return CustomResponse.onSuccess(count);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    //일반상품 개수 - 카테고리 분류
    @Transactional
    public CustomResponse<Long> getProductCountByCategory(String cateCode) {
        try {
            long count = productRepository.countByCategoryCateCode(cateCode);
            return CustomResponse.onSuccess(count);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 일반상품 검색
    @Transactional
    public CustomResponse<List<Product>> searchProduct(String keyword) {
        try {
            if (StringUtils.isBlank(keyword)) {
                return CustomResponse.onSuccess(Collections.emptyList());
            }

            List<Product> searchResults = productRepository.findByNameContainingIgnoreCase(keyword);
            return CustomResponse.onSuccess(searchResults);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public ProductResponse setResponse(Principal principal, Long prodId) {
        Product product = productRepository.findById(prodId)
                .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

        Character favState;

        if(principal == null) {
            favState = 'n';
        } else {
            boolean exist = favoriteRepository.existsByUserEmailAndProductId(principal.getName(), prodId);

            if (exist) {
                favState = 'y';
            } else {
                favState = 'n';
            }
        }

        List<Review> reviewList = reviewRepository.findAllByProductId(prodId);

        ProductResponse response =
                new ProductResponse(product.getId(),
                        product.getCategory().getCateCode(),
                        product.getName(),
                        product.getPrice(),
                        product.getContent(),
                        product.getImgUrl(),
                        favState,
                        reviewList,
                        reviewRepository.countByProductId(prodId),
                        product.getReviewStarAvg());

        return response;
    }

}
