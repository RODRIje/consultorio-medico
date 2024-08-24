package com.proymedic.consultoriomedico.Service;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import com.proymedic.consultoriomedico.Service.impl.IMedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class MedicoServiceTests {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private IMedicoService iMedicoService;

    private Medico medicoFijo;

    @BeforeEach
    void setup(){
        medicoFijo = Medico.builder()
                .id(1L)
                .nombre("Raul")
                .apellido("Rodriguez")
                .especialidad("Clinico")
                .matricula("445asda878")
                .build();
    }

    @DisplayName("Test para guardar un medico")
    @Test
    void testSaveMedico(){
        // given
        given(medicoRepository.save(medicoFijo)).willReturn(medicoFijo);
        // when
        Medico medicoSave = iMedicoService.guardarMedico(medicoFijo);
        // then
        assertThat(medicoSave).isNotNull();
    }

    @DisplayName("Test para buscar clientes")
    @Test
    void testFindMedico(){
        // given
        Medico medico = Medico.builder()
                .nombre("Ramon")
                .apellido("Zapata")
                .especialidad("Pediatra")
                .matricula("885asdasd69")
                .build();
        given(medicoRepository.findAll()).willReturn(List.of(medico, medicoFijo));
        // when
        List<Medico> list = iMedicoService.findAllMedico();
        // then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
    }

    @DisplayName("Test para buscar clientes por id")
    @Test
    void tetsFindMedicoById(){
        // given
        Medico medico = Medico.builder()
                .id(3L)
                .nombre("Ramon")
                .apellido("Zapata")
                .especialidad("Pediatra")
                .matricula("885asdasd69")
                .build();
        given(medicoRepository.findById(3L)).willReturn(Optional.of(medico));
        // when
        Medico medicoSave = iMedicoService.findById(medico.getId());
        // then
        assertThat(medicoSave).isNotNull();
        assertThat(medicoSave.getId()).isEqualTo(3L);
        assertThat(medicoSave.getApellido()).isEqualTo("Zapata");
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    void testListCollectionMedicoEmpty(){
        // given
        Medico medico = Medico.builder()
                .id(3L)
                .nombre("Ramon")
                .apellido("Zapata")
                .especialidad("Pediatra")
                .matricula("885asdasd69")
                .build();
        given(medicoRepository.findAll()).willReturn(Collections.emptyList());
        // when
        List<Medico> list = iMedicoService.findAllMedico();
        // then
        assertThat(list).isEmpty();
        assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("Test para actualizar un medico")
    @Test
    void testUpdateMedico(){
        // given
        given(medicoRepository.save(medicoFijo)).willReturn(medicoFijo);
        medicoFijo.setNombre("Mike");
        medicoFijo.setApellido("Torres");
        // when
        Medico medicoUpdate = iMedicoService.guardarMedico(medicoFijo);
        // then
        assertThat(medicoUpdate).isNotNull();
        assertThat(medicoUpdate.getNombre()).isEqualTo("Mike");
        assertThat(medicoUpdate.getApellido()).isEqualTo("Torres");
    }
    @DisplayName("Test para actualizar un medico por id")
    @Test
    void testUpdateMedicoById(){
        // given
        Long medicoId = 1L;

        Medico medicoExistente = Medico.builder()
                .id(medicoId)
                .nombre("John")
                .apellido("Doe")
                .matricula("123XYZ")
                .email("john.doe@example.com")
                .especialidad("Cardiología")
                .build();

        Medico medicoActualizado = Medico.builder()
                .id(medicoId)
                .nombre("Mike")
                .apellido("Torres")
                .matricula("789ABC")
                .email("mike.torres@example.com")
                .especialidad("Neurología")
                .build();

        given(medicoRepository.findById(medicoId)).willReturn(Optional.of(medicoExistente));
        given(medicoRepository.save(any(Medico.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        iMedicoService.updateMedico(medicoId, medicoActualizado);

        // then
        assertThat(medicoExistente.getNombre()).isEqualTo("Mike");
        assertThat(medicoExistente.getApellido()).isEqualTo("Torres");
        assertThat(medicoExistente.getMatricula()).isEqualTo("789ABC");
        assertThat(medicoExistente.getEmail()).isEqualTo("mike.torres@example.com");
        assertThat(medicoExistente.getEspecialidad()).isEqualTo("Neurología");
        assertThat(medicoExistente.getHorariosDisponibles()).isEmpty();

        verify(medicoRepository).save(medicoExistente);
    }

    @DisplayName("Test para eliminar un medico por id")
    @Test
    void testDeleteMedicoById(){
        // given
        Long medicoId = 5L;
        willDoNothing().given(medicoRepository).deleteById(medicoId);
        // when
        iMedicoService.deleteMedico(medicoId);
        // then
        verify(medicoRepository, times(1)).deleteById(medicoId);
    }

    @DisplayName("Test para buscar medicos por su nombre")
    @Test
    void testFindMedicoByName(){
        // given
        Medico medico = Medico.builder()
                .id(1L)
                .nombre("Rene")
                .apellido("Favaloro")
                .especialidad("Cardiocirujano")
                .matricula("123ABC456")
                .build();

        List<Medico> listMedicoMismoNombre = Arrays.asList(medico);
        given(medicoRepository.findMedicoByname("Rene")).willReturn(listMedicoMismoNombre);

        // when
        List<Medico> listMedico = iMedicoService.findMedicoByname("Rene");

        // then
        assertThat(listMedico).isNotNull();
        assertThat(listMedico).hasSize(1);
        assertThat(listMedico.get(0).getNombre()).isEqualTo("Rene");
        assertThat(listMedico.get(0).getApellido()).isEqualTo("Favaloro");
        assertThat(listMedico.get(0).getEspecialidad()).isEqualTo("Cardiocirujano");
        assertThat(listMedico.get(0).getMatricula()).isEqualTo("123ABC456");
    }

    @DisplayName("Test para buscar medicos por su especialidad")
    @Test
    void testFindMedicoByEspecialidad(){
        // given
        Medico medico = Medico.builder()
                .id(1L)
                .nombre("Marcelo")
                .apellido("Duran")
                .especialidad("Pediatra")
                .matricula("123ABC456")
                .build();

        List<Medico> listMedicoMismaEspecialidad = Arrays.asList(medico);
        given(medicoRepository.findMedicoByEspecialidad("Pediatra")).willReturn(listMedicoMismaEspecialidad);

        // when
        List<Medico> listMedico = iMedicoService.findMedicoByEspecialidad("Pediatra");

        // then
        assertThat(listMedico).isNotNull();
        assertThat(listMedico).hasSize(1);
        assertThat(listMedico.get(0).getNombre()).isEqualTo("Marcelo");
        assertThat(listMedico.get(0).getApellido()).isEqualTo("Duran");
        assertThat(listMedico.get(0).getEspecialidad()).isEqualTo("Pediatra");
        assertThat(listMedico.get(0).getMatricula()).isEqualTo("123ABC456");
    }
}
