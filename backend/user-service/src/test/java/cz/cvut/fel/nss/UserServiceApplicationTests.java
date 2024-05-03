package cz.cvut.fel.nss;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.dto.UserDto;
import cz.cvut.fel.nss.feignClient.OrdersServiceClient;
import cz.cvut.fel.nss.model.RegisterRequest;
import cz.cvut.fel.nss.repository.UserRepository;
import cz.cvut.fel.nss.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class UserServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"));

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
    public void shouldRegisterUser() throws Exception {
        RegisterRequest registerRequest = getRegisterRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(2));
    }

//    @Test
//    public void shouldGetUser() throws Exception {
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(1L);
//        userDto.setFirstName("Artem");
//        userDto.setLastName("Aivazian");
//        userDto.setEmail("aivazart@fel.cvut.cz");
//        userDto.setRole(Role.ADMIN);
//        userDto.setEncryptedPassword(null);
//        userDto.setOrders(new ArrayList<>());
//
//        String userId = "1";
//        String role = "ROLE_ADMIN";
//        String authorizationHeader = new JwtUtil().generateToken(userId, role);
//
////        when(userService.getUserByUserId(userId, authorizationHeader)).thenReturn(userDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId)
//                        .header("Authorization", authorizationHeader))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Artem"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Aivazian"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("aivazart@fel.cvut.cz"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ADMIN"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.orders").isEmpty());
//    }




    private RegisterRequest getRegisterRequest() {
        return RegisterRequest.builder()
                .firstName("Anthony")
                .lastName("John")
                .email("anthony1986@gmail.com")
                .password("P@ssword123")
                .build();
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
