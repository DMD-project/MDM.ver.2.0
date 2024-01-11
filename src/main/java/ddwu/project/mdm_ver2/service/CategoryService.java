package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Category;
import ddwu.project.mdm_ver2.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void initCategories() {
        List<Category> categories = Stream.of(
                        new Category("C001", "가구"),
                        new Category("C002", "페브릭"),
                        new Category("C003", "조명"),
                        new Category("C004", "수납/정리"),
                        new Category("C005", "소품"),
                        new Category("C006", "식물")
                )
                .filter(category -> !categoryRepository.existsByCateCode(category.getCateCode()))
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }
}
