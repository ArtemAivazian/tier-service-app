package cz.cvut.fel.nss.config;

import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.repository.StockRepository;
import cz.cvut.fel.nss.service.ProductsService;
import cz.cvut.fel.nss.service.impl.ProductsServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static cz.cvut.fel.nss.mapper.Mapper.mapToProduct;

@Configuration
@EnableCaching
@Slf4j
@AllArgsConstructor
public class CacheConfig {
//    private final CacheManager cacheManager;
//    private final ProductsService productsService;
//    private final StockRepository stockRepository;
//    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);
//
//    /**
//     * Preloading cache at the start of the app
//     */
//    @PostConstruct
//    public void preloadCache() {
//        Cache cache = cacheManager.getCache("productServiceCache");
//        if (cache != null) {
//            LOGGER.info("*********** Initializing cache");
//
//            List<ProductDto> productsDtos = productsService.findAllProducts();
//
//            productsDtos.stream()
//                    .map(productDto -> mapToProduct(productDto, stockRepository.findByStockId(productDto.getStockId())))
//                    .forEach(
//                            product -> cache.put(product.getProductId(), product)
//                    );
//        }
//    }
}
