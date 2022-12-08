package br.com.gd.pagarme.exceptions;

import br.com.gd.pagarme.exceptions.enums.TransacaoEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class TransacaoException extends PagarMeException{

    private static final long serialVersionUID = -4589179341768493322L;

    public TransacaoException(TransacaoEnum error) {
        super(error.getMessage());
        this.error = error;
    }

    private final TransacaoEnum error;
}
