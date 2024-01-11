package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Category;
import ddwu.project.mdm_ver2.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void initializeCategories() {
        categoryRepository.save(new Category("C001", "가구"));
        categoryRepository.save(new Category("C002", "페브릭"));
        categoryRepository.save(new Category("C003", "조명"));
        categoryRepository.save(new Category("C004", "수납/정리"));
        categoryRepository.save(new Category("C005", "소품"));
        categoryRepository.save(new Category("C006", "식물"));
    }
}
