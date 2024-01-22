package ddwu.project.mdm_ver2.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ddwu.project.mdm_ver2.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "user_role")
    private String userRole;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .userCode(userCode)
                .userNickname(userNickname)
                .kakaoEmail(kakaoEmail)
                .kakaoProfileImg(kakaoProfileImg)
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
