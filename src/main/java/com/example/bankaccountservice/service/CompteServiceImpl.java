package com.example.bankaccountservice.service;

import com.example.bankaccountservice.dto.CompteRequestDTO;
import com.example.bankaccountservice.dto.CompteResponseDTO;
import com.example.bankaccountservice.entities.Compte;
import com.example.bankaccountservice.mappers.CompteMapper;
import com.example.bankaccountservice.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private CompteMapper compteMapper;

    @Override
    public CompteResponseDTO addCompte(CompteRequestDTO compteDTO) {
        Compte compte = Compte.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(compteDTO.getBalance())
                .type(compteDTO.getType())
                .currency(compteDTO.getCurrency())
                .build();
        Compte savedCompte = compteRepository.save(compte);
        return compteMapper.fromCompte(savedCompte);
    }
}
