package br.com.gd.pagarme.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaldoResponseDTO {

    @JsonProperty("Saldo total")
    private BigDecimal saldoTotal;
    @JsonProperty("Saldo disponivel para saque")
    private BigDecimal saldoDisponivel;
    @JsonProperty("Saldo que ainda vai liberar")
    private BigDecimal saldoALiberar;

}
