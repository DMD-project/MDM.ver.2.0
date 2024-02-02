package ddwu.project.mdm_ver2.domain.favorite.service;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.favorite.repository.FavoriteRepository;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CustomResponse<Favorite> setFavoriteState(String userEmail, Long prodId, Character favState) {
        try {
            if(favState.equals('y')) { // 클릭 시 'n' -> 찜 해제(db 삭제)
                deleteFavorite(userEmail, prodId);
                return CustomResponse.onSuccess(null);
            } else { // 클릭 시 'y' -> 찜 등록(db 추가)
                User user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
                Product product = productRepository.findById(prodId)
                        .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

                Favorite favorite = new Favorite(user, product, 'y');
                return CustomResponse.onSuccess(addFavorite(favorite));
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

//    public boolean getFavoriteState(User user, Product product) {
//        return favoriteRepository.existsByUserAndProduct(user, product);
//    }

    public Favorite addFavorite(Favorite favorite) {
        return favoriteRepository.saveAndFlush(favorite);
    }

    public void deleteFavorite(String userEmail, Long prodId) {
        favoriteRepository.deleteByUserEmailAndProductId(userEmail, prodId);
    }

}
