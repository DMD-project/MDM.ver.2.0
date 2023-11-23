package ddwu.project.mdm_ver2.service.category;

import ddwu.project.mdm_ver2.dao.CategoryDao;
import ddwu.project.mdm_ver2.domain.Category;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @PostConstruct
    public void initializeData() {
        List<Category> categories = Arrays.asList(
                new Category("C001", "가구"),
                new Category("C002", "페브릭"),
                new Category("C003", "조명"),
                new Category("C004", "수납/정리"),
                new Category("C005", "소품"),
                new Category("C006", "식물")
        );

        categoryDao.saveAll(categories);
    }

}
