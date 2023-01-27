package entities;

import annotations.ExportarColunaExcel;
import enums.TipoColuna;
import enums.TipoMascara;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportTemplate {

    @ExportarColunaExcel(coluna = 0, titulo = "COLUNA_STRING", tipo = TipoColuna.TEXTO)
    private String colunaString;

    @ExportarColunaExcel(coluna = 1, titulo = "COLUNA_DATA", tipo = TipoColuna.DATA)
    private LocalDate colunaData;

    @ExportarColunaExcel(coluna = 2, titulo = "COLUNA_CNPJ", mascara = TipoMascara.CNPJ)
    private String colunaCNPJ;

    @ExportarColunaExcel(coluna = 3, titulo = "COLUNA_INTEIRO", tipo = TipoColuna.INTEIRO)
    private Integer colunaInteiro;

    @ExportarColunaExcel(coluna = 4, titulo = "COLUNA_DINHEIRO", tipo = TipoColuna.NUMERO, mascara = TipoMascara.DINHEIRO)
    private BigDecimal colunaDinheiro;

    @ExportarColunaExcel(coluna = 5, titulo = "COLUNA_PERCENTUAL", tipo = TipoColuna.NUMERO, mascara = TipoMascara.PERCENTUAL)
    private BigDecimal colunaPercentual;

    private String colunaSemAnotation;

}
