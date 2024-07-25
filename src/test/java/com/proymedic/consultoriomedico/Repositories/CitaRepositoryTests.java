package com.proymedic.consultoriomedico.Repositories;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CitaRepositoryTests {
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private Cita citaFija;
    private Medico medicoFija;
    private Cliente clienteFijo;

    @BeforeEach
    void setup(){
        medicoFija = new Medico();
        medicoFija.setNombre("Martin");
        medicoFija.setApellido("Fara");

        clienteFijo = new Cliente();
        clienteFijo.setNombre("Rodrigo");
        clienteFijo.setApellido("Gonzalez");


        citaFija = Cita.builder()
                .medico(medicoFija)
                .cliente(clienteFijo)
                .observaciones("El paciente debe volver en 7 dias")
                .hora(LocalTime.of(15, 20))  // 15:20 en formato LocalTime
                .fecha(LocalDate.of(2024, 12, 18))  // 2024-12-18 en formato LocalDate
                .build();
    }
    @DisplayName("Test para guarda un cita")
    @Test
    void testSaveCita(){
        // given
        Cita cita = Cita.builder()
                .cliente(clienteFijo)
                .medico(medicoFija)
                .observaciones("El cliente ya esta de alta")
                .hora(LocalTime.of(10,30))
                .fecha(LocalDate.of(2024,07,25))
                .build();
        // when
        Cita citaGuardada = citaRepository.save(cita);
        // then
        assertThat(citaGuardada).isNotNull();
        assertThat(citaGuardada.getObservaciones()).isEqualTo("El cliente ya esta de alta");
    }
    @DisplayName("Test para traer todas las citas")
    @Test
    void testFindAllCitas(){
        // given
        Medico medicoGuardado = medicoRepository.save(medicoFija);
        Cliente clienteGuardado = clienteRepository.save(clienteFijo);

        Cita cita1 = Cita.builder()
                .cliente(clienteGuardado)
                .medico(medicoGuardado)
                .observaciones("El cliente ya esta de alta")
                .hora(LocalTime.of(10, 30))
                .fecha(LocalDate.of(2024, 07, 25))
                .build();
        Cita cita2 = Cita.builder()
                .cliente(clienteGuardado)
                .medico(medicoGuardado)
                .observaciones("El paciente debe volver en 7 días")
                .hora(LocalTime.of(15, 20))
                .fecha(LocalDate.of(2024, 12, 18))
                .build();
        citaRepository.save(cita1);
        citaRepository.save(cita2);
        citaRepository.save(citaFija);
        // when
        List<Cita> list = citaRepository.findAll();
        // then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(4);
    }
    @DisplayName("Test para buscar cita por id")
    @Test
    void testFindCitaById(){
        // given
        Medico medicoGuardado = medicoRepository.save(medicoFija);
        Cliente clienteGuardado = clienteRepository.save(clienteFijo);
        Cita cita = Cita.builder()
                .cliente(clienteGuardado)
                .medico(medicoGuardado)
                .observaciones("El cliente ya esta de alta")
                .hora(LocalTime.of(10, 30))
                .fecha(LocalDate.of(2024, 07, 25))
                .build();
        citaRepository.save(cita);
        // when
        Cita citaBuscada = citaRepository.findById(cita.getId()).get();
        // then
        assertThat(citaBuscada).isNotNull();
        assertThat(citaBuscada.getObservaciones()).isEqualTo("El cliente ya esta de alta");
    }
    @DisplayName("Test para actualizar un cita")
    @Test
    void testCitaUpdate(){
        // given
        citaRepository.save(citaFija);
        // when
        Cita citaGuardada = citaRepository.findById(citaFija.getId()).get();
        citaGuardada.setFecha(LocalDate.of(2024, 12,24));
        citaGuardada.setHora(LocalTime.of(15,30));
        citaGuardada.setObservaciones("El cliente debe volver dentro de 14 dias");
        Cita citaUpdate = citaRepository.save(citaGuardada);
        // then
        assertThat(citaUpdate).isNotNull();
        assertThat(citaUpdate.getHora()).isEqualTo(LocalTime.of(15,30));
        assertThat(citaUpdate.getFecha()).isEqualTo(LocalDate.of(2024,12,24));
    }
    @DisplayName("Test para eliminar un cita por id")
    @Test
    void testDeleteCita(){
        // given
        citaRepository.save(citaFija);
        // when
        citaRepository.deleteById(citaFija.getId());
        Optional<Cita> citaOptional = citaRepository.findById(citaFija.getId());
        // then
        assertThat(citaOptional).isEmpty();
    }
}
