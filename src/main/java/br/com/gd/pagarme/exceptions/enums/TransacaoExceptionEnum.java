package br.com.gd.pagarme.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TransacaoExceptionEnum {

    PORTADOR_CARTAO_INVALIDO("PG_TRS_001", "Nome do portão do cartão invalido", 400);

    private String code;
    private String message;
    private Integer statusCode;

}
