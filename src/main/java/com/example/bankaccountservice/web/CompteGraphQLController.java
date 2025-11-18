package com.example.bankaccountservice.web;

import com.example.bankaccountservice.entities.Compte;
import com.example.bankaccountservice.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CompteGraphQLController {
    @Autowired
    private CompteRepository compteRepository;

    @QueryMapping
    public List<Compte> accountsList(){
        return compteRepository.findAll();
    }

    @QueryMapping
    public Compte accountById(@Argument String id){
        return compteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(String.format("Account %s not found", id)));
    }
}
