package ddwu.project.mdm_ver2.domain.order.entity;

import ddwu.project.mdm_ver2.domain.cartItem.entity.Items;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
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
public class Order { /* 일반상품 즉시 주문, 일반상품 (장바구니) 주문, 공동구매 참여 */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @Column(name = "order_name")
    private String name;

    @Column(name = "order_contact")
    private String contact;

    @Column(name = "order_email")
    private String email;

    @Column(name = "order_zipcode")
    private String zipcode;

    @Column(name = "order_street_addr")
    private String streetAddr;

    @Column(name = "order_detail_addr")
    private String detailAddr;

    @Column(name = "order_price")
    private Integer price;

    @Column(name = "order_qty")
    private Integer qty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_id")
    private Product product;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gp_id")
    private GroupPurchase groupPurchase;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Items> cartItems = new ArrayList<>();

    public List<Items> getCartItems() {
        return cartItems;
    }

    public void Address(String zipcode, String streetAder, String detailAddr) {
        this.zipcode = zipcode;
        this.streetAddr = streetAder;
        this.detailAddr = detailAddr;
    }
}