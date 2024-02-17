package ddwu.project.mdm_ver2.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order { //일반상품(장바구니) 결제, 공동구매 결제

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @NotNull
    @Column(name = "order_name")
    private String name; // 이름

    @NotNull
    @Column(name = "order_contact")
    private String contact; // 전화번호

    @NotNull
    @Column(name = "order_email")
    private String email;   // 이메일

    @NotNull
    @Column(name = "order_zipcode")
    private int zipcode;

    @NotNull
    @Column(name = "order_street_addr")
    private String streetAddr;  //배송지

    @Column(name = "order_detail_addr")
    private String detailAddr;

    public void Address(int zipcode, String streetAder, String detailAddr) {
        this.zipcode = zipcode;
        this.streetAddr = streetAder;
        this.detailAddr = detailAddr;
    }
}
