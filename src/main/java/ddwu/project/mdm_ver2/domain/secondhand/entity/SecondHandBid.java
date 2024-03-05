package ddwu.project.mdm_ver2.domain.secondhand.entity;

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
@Table(name = "second_hand_bid")
public class SecondHandBid {

    @Id
    @SequenceGenerator(
            name = "sh_bid_seq_generator",
            sequenceName = "SH_BID_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sh_bid_seq_generator"
    )
    @Column(name = "sh_bid_id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="sh_id", referencedColumnName="sh_id")
    private SecondHand secondHand;

    /* 요청자 (가격 제안 댓글 작성자) */
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name="user_id", referencedColumnName="user_id")
    @Column(name = "sh_bid_user_id")
    private Long bidUserId;

    @Column(name="sh_bid_price")
    private int price;

    @Column(name="bid_state")
    private Character bidState;
}
