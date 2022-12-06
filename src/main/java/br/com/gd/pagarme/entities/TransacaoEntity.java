package br.com.gd.pagarme.entities;

import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Transacoes")
public class TransacaoEntity {

    @Id
    private String id;
    private BigDecimal valorTransacao;
    private MetodoPagamentoEnum metodoPagamento;
    private String numeroCartao;
    private String nomePortadorCartao;
    private String dataValidadeCartao;
    private String cvv;
    private PagamentoEntity pagamento;

}
