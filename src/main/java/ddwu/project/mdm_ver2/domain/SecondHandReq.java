package ddwu.project.mdm_ver2.domain;

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
@Table(name = "secondHandReq")
public class SecondHandReq {

    @Id
    @SequenceGenerator(
            name = "shReq_seq_generator",
            sequenceName = "SHREQ_SEQ",
            initialValue = 6000,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shReq_seq_generator"
    )
    @Column(name = "shReqID")
    private Long shReqID;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="shID", referencedColumnName="shID")
    private SecondHand secondHand;

    /* 요청자 (가격 제안 댓글 작성자) */
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name="userID", referencedColumnName="user_code")
    @Column(name = "shReqUserID")
    private Long shReqUserID;

    @Column(name="shReqPrice")
    private int shReqPrice;
}
