package br.com.gd.pagarme.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaldoResponseDTO {

    @JsonProperty("Saldo total")
    private BigDecimal saldoTotal;
    @JsonProperty("Saldo disponivel para saque")
    private BigDecimal saldoDisponivel;
    @JsonProperty("Saldo que ainda vai liberar")
    private BigDecimal saldoALiberar;

}
