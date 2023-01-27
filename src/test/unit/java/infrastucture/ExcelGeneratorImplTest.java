package infrastucture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import entities.ReportTemplate;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExcelGeneratorImplTest {

    private final ExcelGeneratorImpl<ReportTemplate> excelGenerator = new ExcelGeneratorImpl<>();

    @Test
    @SneakyThrows
    public void testConstruirBinario() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
            byte[] bytes = excelGenerator.construirBinario(workbook);
            assertThat(bytes != null).isEqualTo(true);
        } catch (Exception e) {
            Assertions.fail("falha no teste de construir binario");
        }
    }

    @Test
    @SneakyThrows
    public void testConstruirBinarioNull() {
        try {
            byte[] bytes = excelGenerator.construirBinario(null);
            assertThat(bytes != null).isEqualTo(true);
        } catch (Exception e) {
            Assertions.fail("falha no teste de construir binario");
        }
    }

    @Test
    @SneakyThrows
    public void testProcurarMetodoNaClasse() {
        var date = LocalDate.of(2023, 1, 1);
        ReportTemplate report = new ReportTemplate();
        report.setColunaData(date);
        Method method = excelGenerator.procurarMetodoNaClasse(report.getClass(), null, "getColunaData");
        assertThat(method.getName()).isEqualTo("getColunaData");
        assertThat(method.invoke(report)).isEqualTo(date);
    }

    @Test
    @SneakyThrows
    public void testProcurarMetodoNaClasse_withNomeMetodoNull() {
        var date = LocalDate.of(2023, 1, 1);
        ReportTemplate report = new ReportTemplate();
        report.setColunaData(date);
        Method method = excelGenerator.procurarMetodoNaClasse(report.getClass(), "colunaData", null);
        assertThat(method.getName()).isEqualTo("getColunaData");
        assertThat(method.invoke(report)).isEqualTo(date);
    }

    @Test
    @SneakyThrows
    public void testGerarLinhaCabecalho() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("testGerarLinhaCabecalho");
        XSSFRow row = sheet.createRow(0);
        ReportTemplate report = getReportTemplate();
        excelGenerator.gerarLinha(workbook, row, report);
        assertThat(row.getRowNum()).isEqualTo(0);
        assertThat(row.getCell(0).getStringCellValue()).isEqualTo("COLUNA_STRING");
        assertThat(row.getCell(1).getStringCellValue()).isEqualTo("COLUNA_DATA");
        assertThat(row.getCell(2).getStringCellValue()).isEqualTo("COLUNA_CNPJ");
        assertThat(row.getCell(3).getStringCellValue()).isEqualTo("COLUNA_INTEIRO");
        assertThat(row.getCell(4).getStringCellValue()).isEqualTo("COLUNA_DINHEIRO");
        assertThat(row.getCell(5).getStringCellValue()).isEqualTo("COLUNA_PERCENTUAL");
    }

    @Test
    @SneakyThrows
    public void testGerarLinhaNormal() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("testGerarLinhaNormal");
        XSSFRow row = sheet.createRow(1);
        ReportTemplate report = getReportTemplate();
        excelGenerator.gerarLinha(workbook, row, report);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        assertThat(row.getRowNum()).isEqualTo(1);
        assertThat(row.getCell(0).getStringCellValue()).isEqualTo(report.getColunaString());
        assertThat(row.getCell(1).getStringCellValue()).isEqualTo(formatter.format(report.getColunaData()));
        assertThat(row.getCell(2).getStringCellValue()).isEqualTo("01.234.567/8910-11");
        assertThat(row.getCell(3).getStringCellValue()).isEqualTo("5");
        assertThat(row.getCell(4).getStringCellValue()).isEqualTo(report.getColunaDinheiro().toString());
        assertThat(row.getCell(5).getStringCellValue()).isEqualTo(report.getColunaPercentual().toString());
    }

    @Test
    @SneakyThrows
    public void testCriarAbaPlanilha() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        List<ReportTemplate> listaTitulo = Arrays.asList(
            getReportTemplate(),
            getReportTemplate()
        );
        excelGenerator.criarAbaPlanilha(workbook, listaTitulo);
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        assertThat(sheetAt != null).isEqualTo(true);
        assert sheetAt != null;
        assertThat(sheetAt.getLastRowNum()).isEqualTo(2);
    }

    @Test
    @SneakyThrows
    public void testCriarAbaPlanilhaListaNull() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        excelGenerator.criarAbaPlanilha(workbook, null);
        int numberOfSheets = workbook.getNumberOfSheets();
        assertThat(numberOfSheets).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    public void testCriarAbaPlanilhaListaEmpty() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        excelGenerator.criarAbaPlanilha(workbook, new ArrayList<>());
        int numberOfSheets = workbook.getNumberOfSheets();
        assertThat(numberOfSheets).isEqualTo(0);
    }

    @Test
    @SneakyThrows
    public void testGerarPlanilhaLista() {
        List<ReportTemplate> listaTitulo = Arrays.asList(
            getReportTemplate(),
            getReportTemplate()
        );
        byte[] ret = excelGenerator.generateBytes(listaTitulo);
        assertThat(ret != null).isEqualTo(true);
    }

    @Test
    @SneakyThrows
    public void testGerarPlanilhaNull() {
        byte[] ret = excelGenerator.generateBytes(null);
        assertThat(ret != null).isEqualTo(true);
    }

    @Test
    @SneakyThrows
    public void testRelatorioAllAnotations() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("testWithAllAnotations");
        XSSFRow row = sheet.createRow(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ReportTemplate report = getReportTemplate();
        excelGenerator.gerarLinha(workbook, row, report);
        assertThat(row.getRowNum()).isEqualTo(1);
        assertThat(row.getCell(0).getStringCellValue()).isEqualTo(report.getColunaString());
        assertThat(row.getCell(1).getStringCellValue()).isEqualTo(formatter.format(report.getColunaData()));
        assertThat(row.getCell(2).getStringCellValue()).isEqualTo("01.234.567/8910-11");
        assertThat(row.getCell(3).getStringCellValue()).isEqualTo("5");
        assertThat(row.getCell(4).getStringCellValue()).isEqualTo(report.getColunaDinheiro().toString());
        assertThat(row.getCell(5).getStringCellValue()).isEqualTo(report.getColunaPercentual().toString());
    }

    @Test
    @SneakyThrows
    public void testRelatorioAllAnotations_WithNullValues() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("testWithAllAnotations");
        XSSFRow row = sheet.createRow(1);
        ReportTemplate report = getReportTemplate();
        report.setColunaInteiro(null);
        report.setColunaDinheiro(null);
        report.setColunaData(null);
        excelGenerator.gerarLinha(workbook, row, report);
        assertThat(row.getRowNum()).isEqualTo(1);
        assertThat(row.getCell(0).getStringCellValue()).isEqualTo(report.getColunaString());
        assertThat(row.getCell(2).getStringCellValue()).isEqualTo("01.234.567/8910-11");
        assertThat(row.getCell(3).getStringCellValue()).isEqualTo("0");
        assertThat(row.getCell(4).getStringCellValue()).isEqualTo("0.00");
        assertThat(row.getCell(5).getStringCellValue()).isEqualTo(report.getColunaPercentual().toString());
    }


    private ReportTemplate getReportTemplate() {
        ReportTemplate report = new ReportTemplate();
        report.setColunaCNPJ("01234567891011");
        report.setColunaData(LocalDate.now());
        report.setColunaDinheiro(BigDecimal.TEN);
        report.setColunaInteiro(5);
        report.setColunaPercentual(BigDecimal.valueOf(50));
        report.setColunaString(RandomStringUtils.randomAlphabetic(5));
        report.setColunaSemAnotation(RandomStringUtils.randomAlphabetic(5));
        return report;
    }
}