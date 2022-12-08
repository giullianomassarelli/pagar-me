package br.com.gd.pagarme.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PagamentoEnum {

   PAGAMENTO_INVALIDO("PG_PGM_001", "Tipo de pagamento invalido", 400);

    private String code;
    private String message;
    private Integer statusCode;

}
