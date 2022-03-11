package com.madhurtoppo.multiplication.challenge.controllers;

import com.madhurtoppo.multiplication.challenge.domain.Challenge;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttemptDTO;
import com.madhurtoppo.multiplication.challenge.services.ChallengeService;
import com.madhurtoppo.multiplication.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeAttemptController.class)
class ChallengeAttemptControllerTest {

    @MockBean private ChallengeService challengeService;
    @Autowired private MockMvc mvc;
    @Autowired private JacksonTester<ChallengeAttemptDTO> jsonRequestAttempt;
    @Autowired private JacksonTester<ChallengeAttempt> jsonResultAttempt;
    @Autowired private JacksonTester<List<ChallengeAttempt>> jsonResultAttemptList;

    @Test
    void postValidResult() throws Exception {
        // given
        User user = new User(1L, "john");
        Challenge challenge = new Challenge(50, 70);
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 70, "john", 3500);
        ChallengeAttempt expectedResponse = new ChallengeAttempt(5L, user, challenge, 3500, true);
        given(challengeService.verifyAttempt(eq(attemptDTO))).willReturn(expectedResponse);

        // when
        MockHttpServletResponse response = mvc.perform(post("/attempts").contentType(MediaType.APPLICATION_JSON)
                                                               .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn()
                .getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonResultAttempt.write(expectedResponse).getJson());
    }

    @Test
    void postInvalidResult() throws Exception {
        // given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(2000, -70, "john", 1);

        // when
        MockHttpServletResponse response = mvc.perform(post("/attempts").contentType(MediaType.APPLICATION_JSON)
                                                               .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn()
                .getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getUserStats() throws Exception {
        // given
        User user = new User("john_doe");
        Challenge challenge1 = new Challenge(50, 70);
        Challenge challenge2 = new Challenge(20, 10);
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, challenge1, 3500, true);
        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user, challenge2, 210, false);
        List<ChallengeAttempt> recentAttempts = List.of(attempt1, attempt2);
        given(challengeService.getStatsForUser("john_doe")).willReturn(recentAttempts);

        // when
        MockHttpServletResponse response = mvc.perform(get("/attempts/{userAlias}", user.getAlias()))
                .andReturn()
                .getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());
    }
}