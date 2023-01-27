import entities.ReportTemplate;
import port.ExcelGenerator;
import infrastucture.ExcelGeneratorImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@Disabled
@ExtendWith(MockitoExtension.class)
class ExcelGeneratorIntegrationTest {

    private final ExcelGenerator<ReportTemplate> excelGenerator = new ExcelGeneratorImpl<>();
    private static final File RELATORIO = new File("GENERATED_FILE.xlsx");

    @AfterAll
    static void afterAll() {
        RELATORIO.delete();
    }

    @SneakyThrows
    @Test
    void gerarExcelTest() {
        ReportTemplate report = ReportTemplate.builder()
            .colunaCNPJ("1234567891011")
            .colunaData(LocalDate.now())
            .colunaDinheiro(BigDecimal.valueOf(100))
            .colunaInteiro(100)
            .colunaPercentual(BigDecimal.TEN)
            .colunaString("String")
            .colunaSemAnotation("No notation")
            .build();
        List<ReportTemplate> listReports = List.of(report);
        byte[] fileUpload = excelGenerator.generateBytes(listReports);
        File file = RELATORIO;
        try (FileOutputStream output = new FileOutputStream(file)) {
            output.write(fileUpload);
        }
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.length() > 0);
    }
}

