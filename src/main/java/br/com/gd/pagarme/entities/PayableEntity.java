package br.com.gd.pagarme.entities;

import br.com.gd.pagarme.enums.PayableEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Pagaveis")
public class PayableEntity {

    @Id
    private String id;
    private PayableEnum status;
    private LocalDateTime dataPagamento;

}
