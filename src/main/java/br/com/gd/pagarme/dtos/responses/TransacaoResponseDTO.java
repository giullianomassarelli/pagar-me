package br.com.gd.pagarme.dtos.responses;

import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransacaoResponseDTO {

   @JsonProperty("Descrição")
   private String descricao;

   @JsonProperty("Valor da transação")
   private BigDecimal valorTransacao;

   @JsonProperty("Metodo de pagamento")
   private MetodoPagamentoEnum metodoPagamento;

   @JsonProperty("Numero do cartão")
   private String numeroCartao;

   @JsonProperty("Portador do cartão")
   private String nomePortadorCartao;

   @JsonProperty("Data de validade do cartão")
   private String dataValidadeCartao;

   @JsonProperty("CVV")
   private String cvv;

   @JsonProperty("Informações do pagamento")
   private PagamentoResponseDTO pagamento;
}
