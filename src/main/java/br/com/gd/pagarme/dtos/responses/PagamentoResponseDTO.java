package br.com.gd.pagarme.dtos.responses;

import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagamentoResponseDTO {
   @JsonProperty("Status")
   private PagamentoEnum status;
   @JsonProperty("Data do pagamento")
   private LocalDateTime dataPagamento;
}
