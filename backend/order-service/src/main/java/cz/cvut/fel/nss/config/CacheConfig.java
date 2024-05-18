package cz.cvut.fel.nss.config;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.service.OrderService;
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

@Configuration
@EnableCaching
@Slf4j
@AllArgsConstructor
public class CacheConfig {
}
