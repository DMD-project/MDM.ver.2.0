package ddwu.project.mdm_ver2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "favID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "userID", referencedColumnName = "user_code")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "prodID", referencedColumnName = "id")
    private Product product;

    @Column(name = "favstate")
    private Character favState;

    public Favorite(User user, Product product, Character favState) {
        this.user = user;
        this.product = product;
        this.favState = favState;
    }
}
