package com.example.bankaccountservice.web;

import com.example.bankaccountservice.dto.CompteRequestDTO;
import com.example.bankaccountservice.dto.CompteResponseDTO;
import com.example.bankaccountservice.entities.Compte;
import com.example.bankaccountservice.repository.CompteRepository;
import com.example.bankaccountservice.service.CompteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompteRestController {
    private CompteRepository compteRepository;
    private CompteService compteService;

    public CompteRestController(CompteRepository compteRepository, CompteService compteService){
        this.compteRepository = compteRepository;
        this.compteService = compteService;
    }

    @GetMapping("/comptes")
    public List<Compte> getComptes(){
        return compteRepository.findAll();
    }

    @GetMapping("/comptes/{id}")
    public Compte getCompte(@PathVariable String id){
        return compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
    }

    @PostMapping("/comptes")
    public CompteResponseDTO save(@RequestBody CompteRequestDTO requestDTO){
        return compteService.addCompte(requestDTO);
    }
}
