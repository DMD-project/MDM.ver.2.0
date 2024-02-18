package ddwu.project.mdm_ver2.domain.secondhand.entity;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "second_hand")
public class SecondHand {

    @Id
    @SequenceGenerator(
            name = "sh_seq_generator",
            sequenceName = "SH_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sh_seq_generator"
    )
    @Column(name = "sh_id")
    private Long id;

    /* 중고거래 작성자 */
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name="user_id", referencedColumnName="user_id")
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="cate_id", referencedColumnName="cate_id")
    private Category category;

    @Column(name = "sh_name")
    private String name;

    @Column(name = "sh_price")
    private int price;

    @Column(name = "sh_img_url")
    private String imgUrl;

    @Column(name = "sh_content")
    private String content;

    /* 거래 상황 : 판매중(y)/거래 완료(n) */
    @Column(name = "sh_state")
    private char state;

    /* 거래 요청 수 (가격 제안 댓글 수) */
    @Column(name = "sh_bid_cnt")
    private Long bidCnt;

//    @ElementCollection(fetch = FetchType.LAZY)
//    private List<SecondHandBid> ShBidList;
}
