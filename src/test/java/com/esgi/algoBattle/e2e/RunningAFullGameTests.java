package com.esgi.algoBattle.e2e;

import com.esgi.algoBattle.compiler.domain.model.ExecutionOutput;
import com.esgi.algoBattle.game.infrastructure.web.response.GameResponse;
import com.esgi.algoBattle.user.infrastructure.web.response.JWTResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RunningAFullGameTests {

    private final Integer algorithmId = 1;
    private ObjectMapper objectMapper;
    private Long firstUserId;
    private String firstUserName;
    private String firstUserEmail;
    private String firstUserPassword;
    private String firstUserAuthorizationToken;

    private Long secondUserId;
    private String secondUserName;
    private String secondUserEmail;
    private String secondUserPassword;
    private String secondUserAuthorizationToken;

    private GameResponse gameResponse;
    private ExecutionOutput executionOutput;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void initAll() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        firstUserId = 1L;
        firstUserName = "Jean";
        firstUserEmail = "jean-dupont@gmail.com";
        firstUserPassword = "azeaze";
        firstUserAuthorizationToken = null;

        secondUserId = 2L;
        secondUserName = "John";
        secondUserEmail = "john-doe@gmail.com";
        secondUserPassword = "azeaze";
        secondUserAuthorizationToken = null;

        gameResponse = null;
        executionOutput = null;
    }

    @Test
    @Order(1)
    public void should_sign_up_first_user() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/users")
                                .content(
                                        "{" +
                                                "\"name\":\"" + firstUserName + "\"," +
                                                "\"email\": \"" + firstUserEmail + "\"," +
                                                "\"password\":\"" + firstUserPassword + "\"" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void should_login_first_user_and_retrieve_authentication_token() throws Exception {
        var loginFirstUserResponse = this.mockMvc
                .perform(
                        post("/api/users/signin")
                                .content(
                                        "{" +
                                                "\"name\": \"" + firstUserName + "\"," +
                                                "\"password\": \"" + firstUserPassword + "\"" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        var jwtResponse = objectMapper.readValue(loginFirstUserResponse.getResponse().getContentAsString(), JWTResponse.class);
        firstUserAuthorizationToken = jwtResponse.getJwttoken();
        firstUserId = jwtResponse.getId();
    }

    @Test
    @Order(3)
    public void should_sign_up_second_user() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/users")
                                .content(
                                        "{" +
                                                "\"name\":\"" + secondUserName + "\"," +
                                                "\"email\": \"" + secondUserEmail + "\"," +
                                                "\"password\":\"" + secondUserPassword + "\"" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    public void should_login_second_user_and_retrieve_authentication_token() throws Exception {
        var loginSecondUserResponse = this.mockMvc
                .perform(
                        post("/api/users/signin")
                                .content(
                                        "{" +
                                                "\"name\": \"" + secondUserName + "\"," +
                                                "\"password\": \"" + secondUserPassword + "\"" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        var jwtResponse = objectMapper.readValue(loginSecondUserResponse.getResponse().getContentAsString(), JWTResponse.class);
        secondUserAuthorizationToken = jwtResponse.getJwttoken();
        secondUserId = jwtResponse.getId();
    }

    @Test
    @Order(5)
    public void should_create_a_game_that_is_not_over() throws Exception {
        var createdGameResponse = this.mockMvc
                .perform(
                        post("/api/games")
                                .content(
                                        "{" +
                                                "\"date\": \"2022-07-24T15:00:00\"," +
                                                "\"over\": false" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + firstUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"id\":1,\"date\":\"2022-07-24T15:00:00\",\"over\":false}")))
                .andReturn();

        gameResponse = objectMapper.readValue(createdGameResponse.getResponse().getContentAsString(), GameResponse.class);
    }

    @Test
    @Order(6)
    public void should_add_first_player_to_created_game() throws Exception {
        System.out.println("gameResponse.getId() = " + gameResponse.getId());
        this.mockMvc
                .perform(
                        put("/api/games/" + gameResponse.getId() + "/users/" + firstUserId)
                                .content(
                                        "{" +
                                                "\"remainingHealthPoints\": 100," +
                                                "\"won\": false" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + firstUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        "{\"game\":{\"id\":" + gameResponse.getId() + ",\"date\":\"2022-07-24T15:00:00\",\"over\":" + gameResponse.getOver() + "},\"user\":{\"id\":" + firstUserId + ",\"name\":\"" + firstUserName + "\",\"email\":\"" + firstUserEmail + "\",\"level\":0},\"remainingHealthPoints\":100,\"won\":false}"
                                )
                ).andReturn();
    }

    @Test
    @Order(7)
    public void should_add_second_player_to_created_game() throws Exception {
        System.out.println("gameResponse.getId() = " + gameResponse.getId());
        this.mockMvc
                .perform(
                        put("/api/games/" + gameResponse.getId() + "/users/" + secondUserId)
                                .content(
                                        "{" +
                                                "\"remainingHealthPoints\": 100," +
                                                "\"won\": false" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + secondUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        "{\"game\":{\"id\":" + gameResponse.getId() + ",\"date\":\"2022-07-24T15:00:00\",\"over\":" + gameResponse.getOver() + "},\"user\":{\"id\":" + secondUserId + ",\"name\":\"" + secondUserName + "\",\"email\":\"" + secondUserEmail + "\",\"level\":0},\"remainingHealthPoints\":100,\"won\":false}"
                                )
                ).andReturn();
    }

    @Test
    @Order(8)
    public void should_start_resolution_of_game() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/resolutions/start")
                                .content(
                                        "{" +
                                                "\"gameId\": " + gameResponse.getId() + "," +
                                                "\"algorithmId\": " + algorithmId +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + firstUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "{\"gameId\":" + gameResponse.getId() + ",\"resolutionTime\":null,\"startedTime\":"
                                        )
                                )
                ) // Avoiding date comparison
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "\"solved\":null,\"player\":null,\"linterErrors\":[{\"errorNumber\":null,\"errorMessage\":null}]}"
                                        )
                                )
                );
    }

    @Test
    @Order(9)
    public void should_compile_code_resolution_for_first_player() throws Exception {
        var compileCodeResult = this.mockMvc
                .perform(
                        post("/api/compiler")
                                .content(
                                        "{" +
                                                "\"algorithmId\": " + algorithmId + ",\n" +
                                                "\"sourceCode\": \"def sum(array):\\n  sum = 0;\\n  for x in array:\\n    sum = sum + x;\\n  return sum;\",\n" +
                                                "\"language\": \"Python\"\n" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + firstUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "{\"successfullyCompiled\":true,\"cases\":[{\"status\":\"Success\",\"name\":\"Avec nombres positifs\",\"log\":\"9\\n\",\"expectedOutput\":\"9\",\"successful\":true},{\"status\":\"Success\",\"name\":\"Avec nombres nÃ©gatifs\",\"log\":\"15\\n\",\"expectedOutput\":\"15\",\"successful\":true},{\"status\":\"Success\",\"name\":\"AlÃ©atoire\",\"log\":\"-5\\n\",\"expectedOutput\":\"-5\",\"successful\":true}],\"errorOutput\":\"\",\"linterErrors\":[],\"successful\":true}"
                                        )
                                )
                )
                .andReturn();
        executionOutput = objectMapper.readValue(compileCodeResult.getResponse().getContentAsString(), ExecutionOutput.class);
    }

    @Test
    @Order(10)
    public void should_add_resolution_of_first_player() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/resolutions")
                                .content(
                                        """
                                                {"algorithmId": 1,
                                                "solved": true,
                                                "linterErrors": []
                                                }"""
                                )
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + firstUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "{\"gameId\":1,\"resolutionTime\""
                                        )
                                )
                )
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "\"startedTime\":"
                                        )
                                )
                )
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "\"solved\":true,\"player\":{\"game\":{\"id\":1,\"date\":\"2022-07-24T15:00:00\",\"over\":false},\"user\":{\"id\":1,\"name\":\"Jean\",\"email\":\"jean-dupont@gmail.com\",\"level\":0},\"remainingHealthPoints\":100,\"won\":false},\"linterErrors\":[{\"errorNumber\":0,\"errorMessage\":null}]}"
                                        )
                                )
                );
    }

    @Test
    @Order(11)
    public void should_set_health_points_of_first_player_to_0() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/games/" + gameResponse.getId() + "/users/" + secondUserId)
                                .content(
                                        "{" +
                                                "\"remainingHealthPoints\": 0," +
                                                "\"won\": false" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + secondUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "{\"game\":{\"id\":1,\"date\":"
                                        )
                                )
                )
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "\"over\":false},\"user\":{\"id\":2,\"name\":\"John\",\"email\":\"john-doe@gmail.com\",\"level\":0},\"remainingHealthPoints\":0,\"won\":false}"
                                        )
                                )
                );
    }

    @Test
    @Order(12)
    public void should_update_game_status_to_over() throws Exception {
        this.mockMvc
                .perform(
                        put("/api/games/" + gameResponse.getId())
                                .content(
                                        "{" +
                                                "\"over\": true" +
                                                "}"
                                )
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + firstUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "{\"id\":1,\"date\":\"2022-07-24T15:00:00\",\"over\":true}"
                                        )
                                )
                );
    }

    @Test
    @Order(13)
    public void should_check_game_status() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/games/" + gameResponse.getId())
                                .header("Authorization", "Bearer " + firstUserAuthorizationToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .string(
                                        containsString(
                                                "{\"id\":1,\"date\":\"2022-07-24T15:00:00\",\"over\":true}"
                                        )
                                )
                );
    }
}