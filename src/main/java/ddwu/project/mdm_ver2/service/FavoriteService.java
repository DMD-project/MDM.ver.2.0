package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Favorite;
import ddwu.project.mdm_ver2.repository.FavoriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavoriteService {

    private FavoriteRepository favoriteRepository;

    public boolean getFavState(Long userID, Long prodID) {
        return favoriteRepository.existsByUserIDAndProdID(userID, prodID);
    }

    public Favorite addFav(Long userID, Long prodID) {
        return favoriteRepository.saveAndFlush(new Favorite(userID, prodID, 'Y'));
    }

    public void deleteFav(Long userID, Long prodID) {
        favoriteRepository.deleteByUserIDAndProdID(userID, prodID);
    }

}
