package com.mahmoud.appointmentsystem.service;

import com.mahmoud.appointmentsystem.DTO.UserDTO;
import com.mahmoud.appointmentsystem.client.UserServiceClient;
import com.mahmoud.appointmentsystem.model.Appointment;
import com.mahmoud.appointmentsystem.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository repository;
    private final UserServiceClient userServiceClient; // Feign Client

    public AppointmentService(AppointmentRepository repository, UserServiceClient userServiceClient) {
        this.repository = repository;
        this.userServiceClient = userServiceClient;
    }
    public Appointment book(Appointment appointment) {
        // Fetch doctor details via Feign
        UserDTO doctor = userServiceClient.getUserById(appointment.getDoctorId());
        // Fetch patient details via Feign
        UserDTO patient = userServiceClient.getUserById(appointment.getPatientId());
        logger.info("Patient Retrieved: {}", patient);
        logger.info("Doctor Retrieved: {}", doctor);

        appointment.setStatus("BOOKED");
        return repository.save(appointment);
    }
    public List<Appointment> getByDoctorId(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }

    public List<Appointment> getByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    public List<Appointment> getAll() {
        return repository.findAll();
    }
}

