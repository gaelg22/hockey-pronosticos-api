package com.upiiz.hockey.services;

import com.upiiz.hockey.PlayoffRound;
import com.upiiz.hockey.repositories.PlayoffRoundRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayoffRoundServiceTest {

    @Mock
    private PlayoffRoundRepository repo;

    @InjectMocks
    private PlayoffRoundService service;

    @Test
    void findById_inexistente_regresaVacio() {
        when(repo.findById(42L)).thenReturn(Optional.empty());

        assertThat(service.findById(42L)).isEmpty();
    }

    @Test
    void save_delegaEnElRepositorio() {
        PlayoffRound ronda = new PlayoffRound();
        ronda.setRoundNum(2);
        when(repo.save(ronda)).thenReturn(ronda);

        assertThat(service.save(ronda).getRoundNum()).isEqualTo(2);
    }

    @Test
    void delete_borraPorId() {
        service.delete(7L);

        verify(repo).deleteById(7L);
    }
}
