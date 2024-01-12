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
                        new Category("FUR", "가구"),
                        new Category("FAB", "패브릭"),
                        new Category("AD", "가전/디지털"),
                        new Category("STO", "수납/정리"),
                        new Category("DEC", "소품"),
                        new Category("LIT", "조명"),
                        new Category("PLA", "식물")
                )
                .filter(category -> !categoryRepository.existsByCateCode(category.getCateCode()))
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }
}
