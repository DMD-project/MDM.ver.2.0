package ddwu.project.mdm_ver2.domain;

import java.security.Timestamp;

import org.springframework.data.annotation.CreatedDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "create_time")
    @CreatedDate//(4)
    private Timestamp createTime;

    @Builder
    public User(long userCode, String kakaoEmail, String kakaoProfileImg) {

        this.userCode = userCode;
//        this.userNickname = userNickname;
        this.kakaoEmail = kakaoEmail;
        this.kakaoProfileImg = kakaoProfileImg;
//        this.userRole = userRole;
    }
}