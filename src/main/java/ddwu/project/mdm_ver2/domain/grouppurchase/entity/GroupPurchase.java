package ddwu.project.mdm_ver2.domain.grouppurchase.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import ddwu.project.mdm_ver2.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@SuppressWarnings("serial")
@Table(name = "group_purchase")
public class GroupPurchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "gp_id")
    private Long id;

    @Column(name = "gp_name")
    private String name;

    @Column(name = "gp_price")
    private int price;

    @Column(name = "gp_content")
    private String content;

    @Column(name = "gp_state")
    private GPStatus state;

    @Column(name = "gp_img_url")
    private String imgUrl;

    @Column(name = "gp_max_qty")
    private int maxQty; //최대 구매 가능 수량

    @Column(name = "gp_now_qty")
    private int nowQty;

    @Column(name = "gp_goal_qty")
    private int goalQty;

    @Column(name = "gp_start")
    private LocalDate start;

    @Column(name = "gp_end")
    private LocalDate end;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "cate_id", referencedColumnName = "cate_id")
    private Category category;
}
