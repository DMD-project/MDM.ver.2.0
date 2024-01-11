package ddwu.project.mdm_ver2.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="brandCode", unique = true)
    private String brandCode;

    @Column(name="brandName")
    private String name;

    @Column(name="brandAdderess")
    private String address;

    @Column(name="brandPhone")
    private String phone;

    public Brand(String brandCode, String name, String address, String phone) {
        this.brandCode = brandCode;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Brand(String brandCode, String name) {
        this.brandCode = brandCode;
        this.name = name;
    }
    public Brand(String brandCode) {
        this.brandCode = brandCode;
    }
}
