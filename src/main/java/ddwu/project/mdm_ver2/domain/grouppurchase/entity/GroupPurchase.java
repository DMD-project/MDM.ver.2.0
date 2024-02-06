package ddwu.project.mdm_ver2.domain.grouppurchase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddwu.project.mdm_ver2.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private GPStatus state; //진행 중, 마감 임박, 공구 성공

    @Column(name = "gp_img_url")
    private String imgUrl;

    @Column(name = "gp_max_qty")
    private int maxQty; //최대 구매 가능 수량

    @Column(name = "gp_now_qty")
    private int nowQty;

    @Column(name = "gp_goal_qty")
    private int goalQty;

    @Column(name = "gp_start")
    private Date start;

    @Column(name = "gp_end")
    private Date end;

    @JsonIgnore
    @OneToMany(mappedBy = "groupPurchase", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<GroupPurchaseParticipant> participants = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "cate_id", referencedColumnName = "cate_id")
    private Category category;

}
