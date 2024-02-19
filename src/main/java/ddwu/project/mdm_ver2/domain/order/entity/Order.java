package ddwu.project.mdm_ver2.domain.order.entity;

import ddwu.project.mdm_ver2.domain.cartItem.entity.Items;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
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

    @Column(name = "order_name")
    private String name; // 이름

    @Column(name = "order_contact")
    private String contact; // 전화번호

    @Column(name = "order_email")
    private String email;   // 이메일

    @Column(name = "order_zipcode")
    private String zipcode;

    @Column(name = "order_street_addr")
    private String streetAddr;  //배송지

    @Column(name = "order_detail_addr")
    private String detailAddr;

    @Column(name = "order_price")
    private Integer price; // 주문 총 가격

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Items> cartItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gp_id")
    private GroupPurchase groupPurchase; // 공동구매

    public List<Items> getCartItems() {
        return cartItems;
    }

    public void Address(String zipcode, String streetAder, String detailAddr) {
        this.zipcode = zipcode;
        this.streetAddr = streetAder;
        this.detailAddr = detailAddr;
    }

//    public void calculateTotalPrice() {
//        int totalPrice = 0;
//        for (Items item : cartItems) {
//            totalPrice += item.getPrice();
//        }
//        this.price = totalPrice;
//    }
}
