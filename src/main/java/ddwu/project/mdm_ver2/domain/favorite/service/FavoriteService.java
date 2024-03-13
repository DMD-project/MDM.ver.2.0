package ddwu.project.mdm_ver2.domain.favorite.service;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.favorite.entity.FavoriteType;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import ddwu.project.mdm_ver2.domain.grouppurchase.repository.GroupPurchaseRepository;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandRepository;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.favorite.repository.FavoriteRepository;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SecondHandRepository secondHandRepository;
    private final GroupPurchaseRepository groupPurchaseRepository;

    public CustomResponse<Favorite> setFavoriteState(String userEmail, String type, Long typeId, Character favState) {
        try {
            Favorite favorite = null;
            switch (type) {
                case "GENERAL":
                    favorite = setProdFavoriteState(userEmail, typeId, favState);
                    break;
                case "SH":
                    favorite = setSHFavoriteState(userEmail, typeId, favState);
                    break;
                case "GP":
                    favorite = setGPFavoriteState(userEmail, typeId, favState);
                    break;
            }
            if (favorite != null) {
                return CustomResponse.onSuccess(addFavorite(favorite));
            } else {
                return CustomResponse.onSuccess(null);
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public Favorite setProdFavoriteState(String userEmail, Long prodId, Character favState) {
        if(favState.equals('y')) {
            deleteFavorite(userEmail, FavoriteType.GENERAL.toString(), prodId);
            return null;
        } else {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

            return new Favorite(user, FavoriteType.GENERAL, product, 'y');
        }
    }

    public Favorite setSHFavoriteState(String userEmail, Long shId, Character favState) {
        if(favState.equals('y')) {
            deleteFavorite(userEmail, FavoriteType.SH.toString(), shId);
            return null;
        } else {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

            SecondHand secondHand = secondHandRepository.findById(shId)
                    .orElseThrow(() -> new NotFoundException("중고 거래 상품을 찾을 수 없습니다."));

            return new Favorite(user, FavoriteType.SH, secondHand, 'y');
        }
    }

    public Favorite setGPFavoriteState(String userEmail, Long gpId, Character favState) {
        if(favState.equals('y')) {
            deleteFavorite(userEmail, FavoriteType.GP.toString(), gpId);
            return null;
        } else {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

            GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
                    .orElseThrow(() -> new NotFoundException("공동 구매 상품을 찾을 수 없습니다."));

            return new Favorite(user, FavoriteType.GP, groupPurchase, 'y');
        }
    }

    public Favorite addFavorite(Favorite favorite) {
        return favoriteRepository.saveAndFlush(favorite);
    }

    public void deleteFavorite(String userEmail, String type, Long typeId) {
        switch (type) {
            case "GENERAL":
                favoriteRepository.deleteByUserEmailAndProductId(userEmail, typeId);
                break;
            case "SH":
                favoriteRepository.deleteByUserEmailAndSecondHandId(userEmail, typeId);
                break;
            case "GP":
                favoriteRepository.deleteByUserEmailAndGroupPurchaseId(userEmail, typeId);
                break;
        }
    }

    public CustomResponse<List<Favorite>> getUserFavoriteList(String userEmail) {
        try{
            List<Favorite> userFavoriteList = favoriteRepository.findAllByUserEmail(userEmail);
            return CustomResponse.onSuccess(userFavoriteList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }
}