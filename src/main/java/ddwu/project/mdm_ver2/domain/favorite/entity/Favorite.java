package ddwu.project.mdm_ver2.domain.favorite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorite")
public class Favorite implements Serializable {

    @Id
    @SequenceGenerator(
            name = "fav_seq_generator",
            sequenceName = "FAV_SEQ",
            initialValue = 61,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "fav_seq_generator"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "fav_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "fav_type")
    private FavoriteType type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "prod_id", referencedColumnName = "prod_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "sh_id", referencedColumnName = "sh_id")
    private SecondHand secondHand;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name = "gp_id", referencedColumnName = "gp_id")
//    private GroupPurchase groupPurchase;

    @Column(name = "fav_state")
    private Character state;

    public Favorite(User user, FavoriteType type, Product product, Character state) {
        this.user = user;
        this.type = type;
        this.product = product;
        this.state = state;
    }

    public Favorite(User user, FavoriteType type, SecondHand secondHand, Character state) {
        this.user = user;
        this.type = type;
        this.secondHand = secondHand;
        this.state = state;
    }

//    public Favorite(User user, FavoriteType type, GroupPurchase groupPurchase, Character state) {
//        this.user = user;
//        this.type = type;
//        this.groupPurchase = groupPurchase;
//        this.state = state;
//    }
}
