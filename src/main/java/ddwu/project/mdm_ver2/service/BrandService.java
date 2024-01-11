package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Brand;
import ddwu.project.mdm_ver2.repository.BrandRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @PostConstruct
    public void initializeBrands() {
        List<Brand> brandCodes = new ArrayList<>();
        brandCodes.add(new Brand("B8000", "참나무"));
        brandCodes.add(new Brand("B8001", "아이닉"));
        brandCodes.add(new Brand("B8002", "마틸라"));
        brandCodes.add(new Brand("B8003", "최고심"));
        brandCodes.add(new Brand("B8004", "mash!"));
        brandCodes.add(new Brand("B8005", "우드공장"));
        brandCodes.add(new Brand("B8006", "apple"));
        brandCodes.add(new Brand("B8007", "LG"));
        brandCodes.add(new Brand("B8008", "삼성"));

        brandRepository.saveAll(brandCodes);
    }
}
