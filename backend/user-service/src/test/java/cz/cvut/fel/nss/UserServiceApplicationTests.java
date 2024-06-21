package cz.cvut.fel.nss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.cvut.fel.nss.dto.UserDto;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserServiceApplicationTests contains integration tests for the UserServiceApplication.
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"));

    /**
     * Sets properties for the test containers.
     *
     * @param dynamicPropertyRegistry the dynamic property registry
     */
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);

    }

    /**
     * Starts the PostgreSQL container before all tests.
     */
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    /**
     * Stops the PostgreSQL container after all tests.
     */
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    /**
     * Tests the user registration functionality.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testRegisterUser() throws Exception {
        UserDto registerRequest = getRegisterRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").isNotEmpty());
    }

    /**
     * Tests the user deletion functionality.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testDeleteUser() throws Exception {
        // Register a user
        UserDto registerRequest = getRegisterRequest();

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract userId from the response
        UserDto registeredUser = new ObjectMapper().readValue(response, UserDto.class);
        Long userId = registeredUser.getUserId();

        // Generate JWT token for the user with role USER
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(userId.toString(), "ROLE_USER");

        // Delete the user
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());

        // Verify the user is deleted
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests the user update functionality.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testUpdateUser() throws Exception {
        // Register a user
        UserDto registerRequest = getRegisterRequest();

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract userId from the response
        UserDto registeredUser = new ObjectMapper().readValue(response, UserDto.class);
        Long userId = registeredUser.getUserId();

        // Generate JWT token for the user with role USER
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(userId.toString(), "ROLE_USER");

        // Update the user
        UserDto updateRequest = UserDto.builder()
                .firstName("UpdatedAnthony")
                .lastName("UpdatedJohn")
                .email("updated" + System.currentTimeMillis() + "@gmail.com")
                .password("NewP@ssword123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("UpdatedAnthony"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("UpdatedJohn"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updateRequest.getEmail()));
    }

    /**
     * Generates a UserDto for registration.
     *
     * @return a UserDto with registration details
     */
    private UserDto getRegisterRequest() {
        String uniqueEmail = "anthony" + System.currentTimeMillis() + "@gmail.com";
        return UserDto.builder()
                .firstName("Anthony")
                .lastName("John")
                .email(uniqueEmail)
                .password("P@ssword123")
                .build();
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
