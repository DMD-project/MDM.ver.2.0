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

    private long id;
    private String nickname;
    private String email;
    private String profileImgUrl;
    private Role role;

    public User toEntity() {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .profileImgUrl(profileImgUrl)
                .role(role)
                .build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
