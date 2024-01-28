package ddwu.project.mdm_ver2.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "user_master")
public class User {

    @Id
    @Column(name = "user_code")
    private long userCode;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "kakao_email")
    private String kakaoEmail;

    @Column(name = "kakao_profile_img")
    private String kakaoProfileImg;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Cart cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
//    @ColumnDefault("user")
    private Role userRole;

    public UserResponse toDTO() {
        return UserResponse.builder()
                .userCode(userCode)
                .userNickname(userNickname)
                .kakaoEmail(kakaoEmail)
                .kakaoProfileImg(kakaoProfileImg)
                .userRole(userRole)
                .build();
    }

    public long getUserCode() {
        return userCode;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getKakaoEmail() {
        return kakaoEmail;
    }

    public String getKakaoProfileImg() {
        return kakaoProfileImg;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
