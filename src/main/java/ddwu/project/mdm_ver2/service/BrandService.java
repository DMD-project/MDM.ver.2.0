package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Brand;
import ddwu.project.mdm_ver2.repository.BrandRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @PostConstruct
    public void initBrands() {
        List<Brand> brandCodes = Stream.of(
                        new Brand("B8000", "참나무"),
                        new Brand("B8001", "아이닉"),
                        new Brand("B8002", "마틸라"),
                        new Brand("B8003", "최고심"),
                        new Brand("B8004", "mash!"),
                        new Brand("B8005", "우드공장"),
                        new Brand("B8006", "apple"),
                        new Brand("B8007", "LG"),
                        new Brand("B8008", "삼성")
                )
                .filter(brand -> !brandRepository.existsByBrandCode(brand.getBrandCode()))
                .collect(Collectors.toList());

        brandRepository.saveAll(brandCodes);
    }
}
