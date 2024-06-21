package cz.cvut.fel.nss;

import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.dto.StockDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the Product Service application.
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"));

    /**
     * Sets dynamic properties for the PostgreSQL container.
     *
     * @param dynamicPropertyRegistry the dynamic property registry
     */
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

    /**
     * Tests the creation of a product.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testCreateProduct() throws Exception {
        StockDto stockDto = createStock("123 Main St");

        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setPrice(new BigDecimal("19.99"));
        productDto.setQuantity(100);
        productDto.setStockId(stockDto.getStockId());
        String productJson = asJsonString(productDto);

        // Generate JWT token for the user with role ADMIN
        JwtUtil jwtUtil = new JwtUtil();
        long userId = 10L;
        String token = jwtUtil.generateToken(Long.toString(userId), "ROLE_ADMIN");

        mockMvc.perform(MockMvcRequestBuilders.post("/product/create")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(19.99))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockId").value(stockDto.getStockId()));
    }

    /**
     * Tests the creation of a stock.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testCreateStock() throws Exception {
        createStock("123 Main St");
    }

    /**
     * Tests the deletion of a stock.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testDeleteStock() throws Exception {
        StockDto stockDto = createStock("456 Secondary St");

        // Generate JWT token for the user with role ADMIN
        JwtUtil jwtUtil = new JwtUtil();
        long userId = 10L;
        String token = jwtUtil.generateToken(Long.toString(userId), "ROLE_ADMIN");

        mockMvc.perform(MockMvcRequestBuilders.delete("/stock/delete/" + stockDto.getStockId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/stock/" + stockDto.getStockId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    /**
     * Creates a stock with the given address.
     *
     * @param address the address of the stock
     * @return the created StockDto object
     * @throws Exception if an error occurs during the test
     */
    private StockDto createStock(String address) throws Exception {
        StockDto stockDto = new StockDto();
        stockDto.setAddress(address);
        String stockJson = asJsonString(stockDto);

        // Generate JWT token for the user with role ADMIN
        JwtUtil jwtUtil = new JwtUtil();
        long userId = 10L;
        String token = jwtUtil.generateToken(Long.toString(userId), "ROLE_ADMIN");

        String stockResponse = mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(address))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readValue(stockResponse, StockDto.class);
    }

    /**
     * Converts an object to its JSON string representation.
     *
     * @param obj the object to convert
     * @return the JSON string representation of the object
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
