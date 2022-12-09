package br.com.gd.pagarme.dtos.requests;

import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequestDTO {

    @NotNull
    private String descricao;

    @NotNull
    private BigDecimal valorTransacao;

    @NotNull
    private MetodoPagamentoEnum metodoPagamento;

    @Size(min = 16, max = 16, message = "Informe todos os numeros do cartão")
    private String numeroCartao;

    @NotNull
    private String nomePortadorCartao;

    @NotNull
    private String dataValidadeCartao;

    @NotNull
    @Size(min = 3, max = 3, message = "Verifique o código de segurança do cartão")
    private String cvv;

    @JsonIgnore
    private PagamentoResponseDTO pagamento;
}
