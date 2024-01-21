package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Favorite;
import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.dto.ProductRequest;
import ddwu.project.mdm_ver2.dto.UserDTO;
import ddwu.project.mdm_ver2.repository.FavoriteRepository;
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
