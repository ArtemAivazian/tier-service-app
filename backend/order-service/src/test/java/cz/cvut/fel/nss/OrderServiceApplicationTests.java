package cz.cvut.fel.nss;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.dto.OrderedProductDto;
import cz.cvut.fel.nss.feign.ProductServiceClient;
import cz.cvut.fel.nss.shared.ProductDto;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductServiceClient productServiceClient;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);

    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    void testPlaceOrder() throws Exception {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("1", "ROLE_USER");

        OrderedProductDto orderedProduct = new OrderedProductDto();
        orderedProduct.setName("Product1");
        orderedProduct.setPrice(BigDecimal.valueOf(10.0));
        orderedProduct.setQuantity(2);
        orderedProduct.setStockId(1L);

        OrderDto orderRequest = new OrderDto();
        orderRequest.setUserId(1L);
        orderRequest.setOrderedProducts(Collections.singletonList(orderedProduct));

        ProductDto productDto = new ProductDto();
        productDto.setName("Product1");
        productDto.setQuantity(5);

        Mockito.when(productServiceClient.getProductByName("Product1", "Bearer " + token))
                .thenReturn(productDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String orderRequestJson = objectMapper.writeValueAsString(orderRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/place")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(orderRequestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderedProducts[0].name").value("Product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderedProducts[0].quantity").value(2));
    }
}
