package com.example.bankaccountservice.mappers;

import com.example.bankaccountservice.dto.CompteResponseDTO;
import com.example.bankaccountservice.entities.Compte;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompteMapper {
    public CompteResponseDTO fromCompte(Compte compte){
        CompteResponseDTO compteResponseDTO = new CompteResponseDTO();
        BeanUtils.copyProperties(compte, compteResponseDTO);
        return compteResponseDTO;
    }
}
