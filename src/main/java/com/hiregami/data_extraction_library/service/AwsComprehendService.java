package com.hiregami.data_extraction_library.service;

import com.hiregami.data_extraction_library.dto.CandidateProfile;
import org.springframework.stereotype.Service;

import java.util.List;

public class AwsComprehendService {

    public CandidateProfile createCandidateProfile(String parsedData) {
        System.out.println("Calling AWS Comprehend for parsed data: " + parsedData);

        CandidateProfile profile = new CandidateProfile();
        profile.setName("John Doe");
        profile.setSkills(List.of("Java", "Spring Boot", "AWS"));
        return profile;
    }
}
