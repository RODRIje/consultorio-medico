package com.proymedic.consultoriomedico.Service.impl;

import com.proymedic.consultoriomedico.Entities.Medico;
import com.proymedic.consultoriomedico.Repositories.MedicoRepository;
import com.proymedic.consultoriomedico.Service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IMedicoService implements MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void updateMedico(Long id, Medico medico) {
        Medico medicoUpdate = findById(id);
        medicoUpdate.setNombre(medico.getNombre());
        medicoUpdate.setApellido(medico.getApellido());
        medicoUpdate.setMatricula(medico.getMatricula());
        medicoUpdate.setEmail(medico.getEmail());
        medicoUpdate.setEspecialidad(medico.getEspecialidad());
        medicoUpdate.setHorariosDisponibles(new ArrayList<>()); // Asignar lista vacía

        medicoRepository.save(medicoUpdate);
    }

    @Override
    public Medico ActuMedic(Medico medico) {
        if(medico.getHorariosDisponibles() == null){
            medico.setHorariosDisponibles(new ArrayList<>()); // Asignar lista vacía
        }
        return medicoRepository.save(medico);
    }

    @Override
    public void deleteMedico(Long id) {
         medicoRepository.deleteById(id);
    }

    @Override
    public List<Medico> findAllMedico() {
        return medicoRepository.findAll();
    }
    @Override
    public Medico findById(Long id){
        return medicoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Medico> findMedicoByEspecialidad(String especialidad) {
        return medicoRepository.findMedicoByEspecialidad(especialidad);
    }

    @Override
    public List<Medico> findMedicoByname(String nombre) {
        return medicoRepository.findMedicoByname(nombre);
    }

    @Override
    public Medico guardarMedico(Medico medico) {
        return medicoRepository.save(medico);
    }
}
