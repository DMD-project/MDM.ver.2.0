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
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_profile_img_url")
    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @Column(name = "user_street_addr")
    private String streetAddr;

    @Column(name = "user_detail_addr")
    private String detailAddr;

    @Column(name = "user_zipcode")
    private String zipcode;

    public UserResponse toDTO() {
        return UserResponse.builder()
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

    public Role getRole() {
        return role;
    }
}
