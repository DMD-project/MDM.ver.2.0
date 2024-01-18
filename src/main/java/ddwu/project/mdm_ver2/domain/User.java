package ddwu.project.mdm_ver2.domain;

import java.security.Timestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
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

//    @Column(name = "user_role")
//    @ColumnDefault("general")
//    private String userRole;

    @Builder
    public User(long userCode, String kakaoEmail, String kakaoProfileImg) {

        this.userCode = userCode;
//        this.userNickname = userNickname;
        this.kakaoEmail = kakaoEmail;
        this.kakaoProfileImg = kakaoProfileImg;
//        this.userRole = userRole;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
