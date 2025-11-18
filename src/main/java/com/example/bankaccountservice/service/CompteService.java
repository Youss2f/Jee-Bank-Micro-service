package com.example.bankaccountservice.service;

import com.example.bankaccountservice.dto.CompteRequestDTO;
import com.example.bankaccountservice.dto.CompteResponseDTO;

public interface CompteService {
    CompteResponseDTO addCompte(CompteRequestDTO compteDTO);
}
