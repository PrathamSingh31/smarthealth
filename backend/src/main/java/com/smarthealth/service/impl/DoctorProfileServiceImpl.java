package com.smarthealth.service.impl;

import com.smarthealth.model.entity.DoctorProfile;
import com.smarthealth.model.entity.User;
import com.smarthealth.repository.DoctorProfileRepository;
import com.smarthealth.repository.UserRepository;
import com.smarthealth.service.impl.DoctorProfileService;
import org.springframework.stereotype.Service;

@Service
public class DoctorProfileServiceImpl implements DoctorProfileService {

    private final DoctorProfileRepository profileRepository;
    private final UserRepository userRepository;

    public DoctorProfileServiceImpl(DoctorProfileRepository profileRepository,
                                    UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DoctorProfile createOrUpdateProfile(Long doctorId, DoctorProfile profileData) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DoctorProfile profile = profileRepository.findByUser(doctor).orElse(new DoctorProfile());
        profile.setUser(doctor);
        profile.setSpecialization(profileData.getSpecialization());
        profile.setAvailability(profileData.getAvailability());

        return profileRepository.save(profile);
    }

    @Override
    public DoctorProfile getProfileByDoctor(User doctor) {
        return profileRepository.findByUser(doctor)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }
}
