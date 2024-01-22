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

    public boolean getFavoriteState(UserDTO userDTO, Product product) {
        return favoriteRepository.existsByUserAndProduct(userDTO.toEntity(), product);
    }

    public Favorite addFavorite(Favorite fav) {
        return favoriteRepository.saveAndFlush(fav);
    }

    public void deleteFavorite(UserDTO userDTO, Product product) {
        favoriteRepository.deleteByUserAndProduct(userDTO.toEntity(), product);
    }

}
