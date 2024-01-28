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
    @Column(name = "userID")
    private long userCode;

    @Column(name = "userNickname")
    private String userNickname;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "userProfileImg")
    private String userProfileImg;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Cart cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "userRole")
//    @ColumnDefault("user")
    private Role userRole;

    public UserResponse toDTO() {
        return UserResponse.builder()
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

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getuserProfileImg() {
        return userProfileImg;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
