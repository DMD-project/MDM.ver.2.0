package ddwu.project.mdm_ver2.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="brandCode")
    private String brandCode;

    @Column(name="brandName")
    private String name;

    @Column(name="brandAdderess")
    private String address;

    @Column(name="brandPhone")
    private String phone;

    public Brand(String brandCode, String name) {
        super();
        this.brandCode = brandCode;
        this.name = name;
    }
}
