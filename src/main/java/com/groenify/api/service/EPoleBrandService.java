package com.groenify.api.service;

import com.groenify.api.database.EPoleBrand;
import com.groenify.api.repository.CompanyRepository;
import com.groenify.api.util.LongUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EPoleBrandService {

    private final CompanyRepository repository;

    public EPoleBrandService(final CompanyRepository repository) {
        this.repository = repository;
    }
}
