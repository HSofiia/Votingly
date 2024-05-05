package be.kdg.team9.integration4.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SurveysControllerTest {
    @Autowired
    private MockMvc mockMvc;
    // private final Logger logger;

    // @Autowired
    // public SurveysControllerTest(Logger logger) {
    //     this.logger = logger;
    // }

    @Test
    public void getAllSurveysShouldReturnListOfSurveysNames() throws Exception {
        // logger.info("Active profiles:");
        // logger.info(System.getProperty("spring.profiles.active"));
        // logger.info(System.getProperty("SPRING_PROFILES_ACTIVE"));
        // System.out.println("Active profiles:");
        // System.out.println(System.getProperty("spring.profiles.active"));
        // System.out.println(System.getProperty("SPRING_PROFILES_ACTIVE"));

        mockMvc.perform(
                get("/api/surveys")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print());
    }

    @Test
    public void getQuestionsOfSurveyShouldReturnNoContentIfEmpty() throws Exception {
        mockMvc.perform(
                get("/api/surveys/{id}/questions", 55).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void getQuestionsOfSurveyShouldReturnIsOkWIthQuestions() throws Exception {
        mockMvc.perform(
                        get("/api/surveys/{id}/questions", 3).accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());
    }
}