package ddwu.project.mdm_ver2.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "favorite")
public class Favorite implements Serializable {

    @Id
    @SequenceGenerator(
            name = "fav_seq_generator",
            sequenceName = "favSeq",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "fav_seq_generator"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long favID;

    @Column(name = "userID")
    private Long userID;

    @Column(name = "prodID")
    private Long prodID;

    @Column(name = "favstate")
    private Character favState;

    @Builder
    public Favorite(Long userID, Long prodID, Character favState) {
        this.userID = userID;
        this.prodID = prodID;
        this.favState = favState;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setProdID(Long prodID) {
        this.prodID = prodID;
    }

    public void setFavState(Character favState) {
        this.favState = favState;
    }
}
