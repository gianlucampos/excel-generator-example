import entities.ReportTemplate;
import infrastucture.ExcelGeneratorImpl;
import port.ExcelGenerator;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        ExcelGenerator<ReportTemplate> excelGenerator = new ExcelGeneratorImpl<>();
        byte[] file = excelGenerator.generateBytes(List.of(buildReportTemplate()));
        writeFile(file);
    }

    @SneakyThrows
    private static void writeFile(byte[] file) {
        try (FileOutputStream fos = new FileOutputStream("src/main/resources/REPORT_FILE.xls")) {
            fos.write(file);
        }
    }

    private static ReportTemplate buildReportTemplate() {
        return ReportTemplate.builder()
            .colunaCNPJ("1234567891011")
            .colunaData(LocalDate.now())
            .colunaDinheiro(BigDecimal.TEN)
            .colunaInteiro(100)
            .colunaPercentual(BigDecimal.TEN)
            .colunaString("String")
            .colunaSemAnotation("Sem Anotation")
            .build();
    }

}
