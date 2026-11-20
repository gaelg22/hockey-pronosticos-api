package com.upiiz.hockey;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Revisa las reglas de Bean Validation de las entidades sin levantar Spring.
 */
class ValidacionesTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void crearValidador() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void cerrarValidador() {
        factory.close();
    }

    @Test
    void pronosticoValido_noTieneErrores() {
        assertThat(validator.validate(pronostico(3, 2))).isEmpty();
    }

    @Test
    void pronosticoConMarcadorNegativo_esInvalido() {
        Set<ConstraintViolation<UserScorePicks>> errores = validator.validate(pronostico(-1, 2));

        assertThat(errores).hasSize(1);
        assertThat(errores.iterator().next().getPropertyPath().toString()).isEqualTo("team1score");
    }

    @Test
    void rondaConNumeroCero_esInvalida() {
        PlayoffRound ronda = ronda();
        ronda.setRoundNum(0);

        Set<ConstraintViolation<PlayoffRound>> errores = validator.validate(ronda);

        assertThat(errores).extracting(e -> e.getPropertyPath().toString()).containsExactly("roundNum");
    }

    @Test
    void rondaSinFechas_marcaAmbosCampos() {
        PlayoffRound ronda = ronda();
        ronda.setStartTime(null);
        ronda.setEndTime(null);

        assertThat(validator.validate(ronda))
                .extracting(e -> e.getPropertyPath().toString())
                .containsExactlyInAnyOrder("startTime", "endTime");
    }

    @Test
    void usuarioConEmailMalYPasswordCorto_esInvalido() {
        AppUser usuario = new AppUser();
        usuario.setUsername("gael");
        usuario.setPassword("123");
        usuario.setEmail("esto-no-es-un-correo");

        assertThat(validator.validate(usuario))
                .extracting(e -> e.getPropertyPath().toString())
                .containsExactlyInAnyOrder("password", "email");
    }

    private UserScorePicks pronostico(int equipo1, int equipo2) {
        UserScorePicks picks = new UserScorePicks();
        picks.setHockeyGameId(1L);
        picks.setUserId(1L);
        picks.setTeam1score(equipo1);
        picks.setTeam2score(equipo2);
        return picks;
    }

    private PlayoffRound ronda() {
        PlayoffRound ronda = new PlayoffRound();
        ronda.setTeam1Id(1L);
        ronda.setTeam2Id(2L);
        ronda.setStartTime(LocalDateTime.of(2026, 4, 20, 19, 0));
        ronda.setEndTime(LocalDateTime.of(2026, 4, 30, 22, 0));
        ronda.setRoundNum(1);
        return ronda;
    }
}
