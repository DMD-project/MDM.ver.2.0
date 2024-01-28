package ddwu.project.mdm_ver2.domain.user.dto;

import ddwu.project.mdm_ver2.domain.user.entity.Role;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private long userCode;
    private String userNickname;
    private String userEmail;
    private String userProfileImg;
    private Role userRole;

    public User toEntity() {
        return User.builder()
                .userCode(userCode)
                .userNickname(userNickname)
                .userEmail(userEmail)
                .userProfileImg(userProfileImg)
                .userRole(userRole)
                .build();
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserProfileImg() {
        return userProfileImg;
    }

    public void setUserProfileImg(String userProfileImg) {
        this.userProfileImg = userProfileImg;
    }
}
