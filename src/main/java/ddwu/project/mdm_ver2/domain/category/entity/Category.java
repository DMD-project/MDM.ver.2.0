package ddwu.project.mdm_ver2.domain.category.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_id", unique = true)
    private Long id;

    @Column(name = "cate_code", unique = true)
    private String cateCode;

    @Column(name = "cate_name")
    private String name;

    public Category(String cateCode, String name) {
        this.cateCode = cateCode;
        this.name = name;
    }
}
