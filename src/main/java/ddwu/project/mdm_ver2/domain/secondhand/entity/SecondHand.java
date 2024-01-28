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
@Table(name = "secondHand")
public class SecondHand {

    @Id
    @SequenceGenerator(
            name = "sh_seq_generator",
            sequenceName = "SH_SEQ",
            initialValue = 4000,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sh_seq_generator"
    )
    @Column(name = "shID")
    private Long shID;

    /* 중고거래 작성자 */
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name="userID", referencedColumnName="user_code")
    @Column(name = "userID")
    private Long userID;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="cateID", referencedColumnName="id")
    private Category category;

    @Column(name = "shName")
    private String shName;

    @Column(name = "shPrice")
    private int shPrice;

    @Column(name = "shImg")
    private String shImg;

    @Column(name = "shContent")
    private String shContent;

    /* 거래 상황 : 판매중(y)/거래 완료(n) */
    @Column(name = "shState")
    private char shState;

    /* 거래 요청 수 (가격 제안 댓글 수) */
    @Column(name = "shReqCnt")
    private int shReqCnt;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<SecondHandBid> SHReqList;

}
