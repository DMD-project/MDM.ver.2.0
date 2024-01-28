package ddwu.project.mdm_ver2.domain.favorite.service;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.favorite.repository.FavoriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavoriteService {

    private FavoriteRepository favoriteRepository;

    public boolean getFavoriteState(User user, Product product) {
        return favoriteRepository.existsByUserAndProduct(user, product);
    }

    public Favorite addFavorite(Favorite fav) {
        return favoriteRepository.saveAndFlush(fav);
    }

    public void deleteFavorite(User user, Product product) {
        favoriteRepository.deleteByUserAndProduct(user, product);
    }

}
