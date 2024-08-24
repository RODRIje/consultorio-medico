package com.proymedic.consultoriomedico.Service;

import com.proymedic.consultoriomedico.Entities.Cita;
import com.proymedic.consultoriomedico.Entities.Cliente;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.CitaRepository;
import com.proymedic.consultoriomedico.Repositories.ClienteRepository;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import com.proymedic.consultoriomedico.Service.impl.ICitaService;
import com.proymedic.consultoriomedico.Service.impl.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CitaServiceTests {
    @Mock
    private CitaRepository citaRepository;
    @Mock
    private MedicoRepository medicoRepository;
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ICitaService citaService;

    private Cita citaFija;
    private  Medico medico;
    private Cliente cliente;

    @BeforeEach
    void setup(){
        medico = new Medico();
        medico.setNombre("Juan");
        medico.setApellido("Perez");

        cliente = new Cliente();
        cliente.setNombre("Ramiro");
        cliente.setApellido("Suarez");

        citaFija = Cita.builder()
                .id(1L)
                .hora(LocalTime.of(14,30))
                .fecha(LocalDate.of(2024,10,21))
                .observaciones("El cliente debe volver el 30 dias para control")
                .medico(medico)
                .cliente(cliente)
                .build();
    }

    @DisplayName("Test para guardar un cita")
    @Test
    void testSaveCita(){
        // given
        given(citaRepository.save(citaFija)).willReturn(citaFija);
        // when
        Cita citaSave = citaService.guardarCita(citaFija);
        // then
        assertThat(citaSave).isNotNull();
    }
    @DisplayName("Test para actualizar un cita por id")
    @Test
    void testUpdateCitaById(){
        // given
        Long citaId = 1L;

        Medico medico1 = new Medico();
        medico1.setNombre("Cristian");
        medico1.setApellido("Medina");

        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Rodrigo");
        cliente1.setApellido("Gonzalez");

        Cita citaExistente = Cita.builder()
                .hora(LocalTime.of(18,30))
                .fecha(LocalDate.of(2024,10,21))
                .observaciones("El cliente esta dado de alta")
                .medico(medico1)
                .cliente(cliente1)
                .build();

        Cita citaActualizada = Cita.builder()
                .hora(LocalTime.of(15,45))
                .fecha(LocalDate.of(2024,10,27))
                .observaciones("El cliente esta dado de alta. Debe tomar ibuprofeno 600g por 30 dias cada 8 horas")
                .medico(medico1)
                .cliente(cliente1)
                .build();

        given(citaRepository.findById(citaId)).willReturn(Optional.of(citaExistente));
        given(medicoRepository.findById(medico1.getId())).willReturn(Optional.of(medico1));
        given(clienteRepository.findById(cliente1.getId())).willReturn(Optional.of(cliente1));
        given(citaRepository.save(any(Cita.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        citaService.updateCita(citaId, citaActualizada);

        // then
        assertThat(citaActualizada.getHora()).isEqualTo("15:45");
        assertThat(citaActualizada.getFecha()).isEqualTo("2024-10-27");
        assertThat(citaActualizada.getObservaciones()).isEqualTo("El cliente esta dado de alta. Debe tomar ibuprofeno 600g por 30 dias cada 8 horas");
        assertThat(citaActualizada.getMedico()).isEqualTo(citaActualizada.getMedico());
        assertThat(citaActualizada.getCliente()).isEqualTo(citaActualizada.getCliente());
        verify(citaRepository).save(citaActualizada);
    }

    @DisplayName("Test para buscar todas las citas")
    @Test
    void testFindAllCitas() {
        // given
        given(citaRepository.findAll()).willReturn(List.of(citaFija));
        // when
        List<Cita> list = citaService.findAllCita();
        // then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);
    }

    @DisplayName("Test para buscar una cita por id")
    @Test
    void testFindCitaById(){
        // given
        Cita cita = Cita.builder()
                        .id(2L)
                        .build();
        given(citaRepository.findById(2L)).willReturn(Optional.of(cita));
        // when
        Cita citaSave = citaService.findById(cita.getId());
        // then
        assertThat(citaSave).isNotNull();
        assertThat(citaSave.getId()).isEqualTo(2L);
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    void testFindCollectionEmpty(){
        // given
        given(citaRepository.findAll()).willReturn(Collections.emptyList());
        // when
        List<Cita> listCita = citaService.findAllCita();
        // then
        assertThat(listCita).isEmpty();
    }

    @DisplayName("Test para actualizar un cita")
    @Test
    void testUpdateCita(){
        // given
        given(citaRepository.save(citaFija)).willReturn(citaFija);
        citaFija.setHora(LocalTime.of(16,30));
        citaFija.setFecha(LocalDate.of(2024, 11, 7));
        // when
        Cita citaUpdate = citaService.guardarCita(citaFija);
        // then
        assertThat(citaUpdate).isNotNull();
        assertThat(citaUpdate.getHora()).isEqualTo(LocalTime.of(16,30));
        assertThat(citaUpdate.getFecha()).isEqualTo(LocalDate.of(2024,11,7));
    }

    @DisplayName("Test para eliminar una cita por id")
    @Test
    void testDeleteCitaById(){
        // given
        Long citaId = 9L;
        willDoNothing().given(citaRepository).deleteById(citaId);
        // when
        citaService.deleteCita(citaId);
        // then
        verify(citaRepository, times(1)).deleteById(citaId);
    }

    @DisplayName("Test para buscar citas por nombre del medico")
    @Test
    void testFindCitaByMedico(){
        // given
        medico = new Medico();
        medico.setNombre("Cristian");
        medico.setApellido("Medina");

        cliente = new Cliente();
        cliente.setNombre("Roberto");
        cliente.setApellido("Carlos");

       Cita cita = Cita.builder()
                .id(2L)
                .hora(LocalTime.of(18,30))
                .fecha(LocalDate.of(2024,10,21))
                .observaciones("El cliente esta dado de alta")
                .medico(medico)
                .cliente(cliente)
                .build();

        List<Cita> expectedCitas = Arrays.asList(cita);
        given(citaRepository.findByMedico(medico.getNombre())).willReturn(expectedCitas);

        // when
        List<Cita> citaList = citaService.findByMedico(medico.getNombre());

        // then
        assertThat(citaList).isNotNull();
        assertThat(citaList).hasSize(1);
        assertThat(citaList.get(0).getMedico().getNombre()).isEqualTo(medico.getNombre());
        assertThat(citaList.get(0).getMedico().getApellido()).isEqualTo(medico.getApellido());
        assertThat(citaList.get(0).getCliente().getNombre()).isEqualTo(cliente.getNombre());
        assertThat(citaList.get(0).getCliente().getApellido()).isEqualTo(cliente.getApellido());
    }

    @DisplayName("Test para buscar una cita por nombre del cliente")
    @Test
    void testFindCitaByCliente(){
        // given
        medico = new Medico();
        medico.setNombre("Cristian");
        medico.setApellido("Medina");

        cliente = new Cliente();
        cliente.setNombre("Rodrigo");
        cliente.setApellido("Gonzalez");

        Cita cita = Cita.builder()
                .id(2L)
                .hora(LocalTime.of(18,30))
                .fecha(LocalDate.of(2024,10,21))
                .observaciones("El cliente esta dado de alta")
                .medico(medico)
                .cliente(cliente)
                .build();

        List<Cita> expectedCitas = Arrays.asList(cita);
        given(citaRepository.findByCliente(cliente.getNombre())).willReturn(expectedCitas);

        // when
        List<Cita> citaList = citaService.findByCliente(cliente.getNombre());

        // then
        assertThat(citaList).isNotNull();
        assertThat(citaList).hasSize(1);
        assertThat(citaList.get(0).getCliente().getNombre()).isEqualTo(cliente.getNombre());
        assertThat(citaList.get(0).getCliente().getApellido()).isEqualTo(cliente.getApellido());
        assertThat(citaList.get(0).getMedico().getNombre()).isEqualTo(medico.getNombre());
        assertThat(citaList.get(0).getMedico().getApellido()).isEqualTo(medico.getApellido());
    }
}
