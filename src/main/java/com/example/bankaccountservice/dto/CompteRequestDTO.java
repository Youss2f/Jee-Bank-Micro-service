package com.example.bankaccountservice.dto;

import com.example.bankaccountservice.entities.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteRequestDTO {
    private Double balance;
    private String currency;
    private AccountType type;
}
