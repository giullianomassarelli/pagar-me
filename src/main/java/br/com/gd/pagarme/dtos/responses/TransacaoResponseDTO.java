package br.com.gd.pagarme.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransacaoResponseDTO {

   private BigDecimal saldoDisponivel;
   private BigDecimal saldoALiberar;
}
