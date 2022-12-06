package br.com.gd.pagarme.dtos.responses;

import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransacaoResponseDTO {

   private String descricao;
   private BigDecimal valorTransacao;
   private MetodoPagamentoEnum metodoPagamento;
   private String numeroCartao;
   private String nomePortadorCartao;
   private String dataValidadeCartao;
   private String cvv;
   private PagamentoResponseDTO pagamento;
}
