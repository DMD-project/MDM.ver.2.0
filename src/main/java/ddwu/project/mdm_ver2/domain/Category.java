package ddwu.project.mdm_ver2.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cateCode")
    private String cateCode;

    @Column(name = "cateName")
    private String name;

    public Category(String cateCode, String name) {
        this.cateCode = cateCode;
        this.name = name;
    }
}
