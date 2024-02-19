package ddwu.project.mdm_ver2.domain.grouppurchase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@SuppressWarnings("serial")
@Table(name = "group_purchase_participant")
public class GroupPurchaseParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gp_participant_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gp_id")
    private GroupPurchase groupPurchase;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "gp_participant_purchased_qty")
    private int purchasedQty;

}
