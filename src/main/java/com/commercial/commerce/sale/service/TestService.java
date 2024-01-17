package com.commercial.commerce.sale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.commerce.sale.entity.TestEntity;
import com.commercial.commerce.sale.repository.TestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public List<TestEntity> getAllEntity() {
        return testRepository.findAll();
    }
}
