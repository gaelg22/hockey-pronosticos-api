package com.upiiz.hockey.controllers;

import com.upiiz.hockey.PlayoffRound;
import com.upiiz.hockey.services.PlayoffRoundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayoffRoundController.class)
class PlayoffRoundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayoffRoundService service;

    @Test
    void getAll_regresaLaListaDeRondas() throws Exception {
        PlayoffRound ronda = new PlayoffRound();
        ronda.setId(1L);
        ronda.setRoundNum(1);
        when(service.findAll()).thenReturn(List.of(ronda));

        mockMvc.perform(get("/api/playoffrounds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].roundNum").value(1));
    }

    @Test
    void create_conDatosValidos_guardaLaRonda() throws Exception {
        when(service.save(any(PlayoffRound.class))).thenAnswer(inv -> inv.getArgument(0));

        String json = """
                {"team1Id": 1, "team2Id": 2,
                 "startTime": "2026-04-20T19:00:00", "endTime": "2026-04-30T22:00:00",
                 "roundNum": 1}
                """;

        mockMvc.perform(post("/api/playoffrounds").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team1Id").value(1));
    }

    @Test
    void create_conRondaInvalida_regresa400YNoGuarda() throws Exception {
        String json = """
                {"team1Id": 1, "team2Id": 2, "roundNum": 0}
                """;

        mockMvc.perform(post("/api/playoffrounds").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
        verify(service, never()).save(any());
    }
}
