package com.smarthealth.service.impl;


import com.smarthealth.model.entity.DoctorProfile;
import com.smarthealth.model.entity.User;

public interface DoctorProfileService {
    DoctorProfile createOrUpdateProfile(Long doctorId, DoctorProfile profile);
    DoctorProfile getProfileByDoctor(User doctor);
}