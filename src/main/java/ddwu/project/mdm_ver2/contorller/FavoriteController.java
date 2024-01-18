package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.Favorite;
import ddwu.project.mdm_ver2.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/product/{id}")
public class FavoriteController {

    private FavoriteService fs;

    @PostMapping() // 수정 ..
    public boolean getFavState(@RequestParam(value="userCode", required=true) long userCode,
                                 @PathVariable(value="id", required=true) long prodID) {
        return fs.getFavState(userCode, prodID); // true(exist)-> 찜 상태, false -> 찜 아닌 상태
    }

    @GetMapping("/favState/{favState}/userCode/{userCode}")
    public Favorite changeFavState(@PathVariable(value="userCode", required=true) long userCode,
                                   @PathVariable(value="id", required=true) long prodID,
                                   @PathVariable(value="favState", required = true) Character favState) {

        if(favState.equals('Y')) {
            fs.deleteFav(userCode, prodID);
            return null;
        } else {
            return fs.addFav(userCode, prodID);
        }

    }

}
