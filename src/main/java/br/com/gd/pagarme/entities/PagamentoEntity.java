package br.com.gd.pagarme.entities;

import br.com.gd.pagarme.enums.PagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Pagaveis")
public class PagamentoEntity {

    @Id
    private String id;
    private PagamentoEnum status;
    private LocalDateTime dataPagamento;


    public PagamentoEntity(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
