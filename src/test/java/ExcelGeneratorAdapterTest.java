//import static org.junit.Assert.assertThat;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class ExcelGeneratorAdapterTest {
//
//    private final ExcelGeneratorAdapter<entities.ReportTemplate> excelGenerator = new ExcelGeneratorAdapter<>();
//
//
//    @Test
//
//    public void testConstruirBinario() {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        try {
//            byte[] bytes = excelGenerator.construirBinario(workbook);
//            assertThat(bytes != null).isEqualTo(true);
//        } catch (Exception e) {
//            Assertions.fail("falha no teste de construir binario");
//        }
//    }
//
//    @Test
//
//    public void testConstruirBinarioNull() {
//        try {
//            byte[] bytes = excelGenerator.construirBinario(null);
//            assertThat(bytes != null).isEqualTo(true);
//        } catch (Exception e) {
//            Assertions.fail("falha no teste de construir binario");
//        }
//    }
//
//    @Test
//
//    public void testProcurarMetodoNaClasse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        ReportIn200 reportIn200 = new ReportIn200();
//        reportIn200.setDataBase("2023-01-01");
//        Method method = excelGenerator.procurarMetodoNaClasse(reportIn200.getClass(), null, "getDataBase");
//        assertThat(method.getName()).isEqualTo("getDataBase");
//        assertThat(method.invoke(reportIn200)).isEqualTo("2023-01-01");
//    }
//
//    @Test
//
//    public void testProcurarMetodoNaClasse_withNomeMetodoNull() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        ReportIn200 reportIn200 = new ReportIn200();
//        reportIn200.setDataBase("2023-01-01");
//        Method method = excelGenerator.procurarMetodoNaClasse(reportIn200.getClass(), "dataBase", null);
//        assertThat(method.getName()).isEqualTo("getDataBase");
//        assertThat(method.invoke(reportIn200)).isEqualTo("2023-01-01");
//    }
//
//    @Test
//
//    public void testGerarLinhaCabecalho() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("testGerarLinhaCabecalho");
//        XSSFRow row = sheet.createRow(0);
//        ReportIn200 reportIn200 = ReportIn200Fixture.get().withRandomData().build();
//        excelGenerator.gerarLinha(workbook, row, reportIn200);
//        assertThat(row.getRowNum()).isEqualTo(0);
//        assertThat(row.getCell(0).getStringCellValue()).isEqualTo("Data-base");
//        assertThat(row.getCell(1).getStringCellValue()).isEqualTo("ISBP do provedor de conta transacional do usuário pagador (sacador)");
//        assertThat(row.getCell(2).getStringCellValue()).isEqualTo("ISPB do facilitador de serviço de saque (FSS)");
//        assertThat(row.getCell(3).getStringCellValue()).isEqualTo("ISPB do provedor de conta transacional do usuário recebedor (agente de saque)");
//        assertThat(row.getCell(4).getStringCellValue()).isEqualTo(
//
//            "Quantidade de transações Pix com finalidade de saque ou de troco em que o agente de saque seja um estabelecimento comercial de qualquer natureza");
//    }
//
//    @Test
//
//    public void testGerarLinhaNormal() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("testGerarLinhaNormal");
//        XSSFRow row = sheet.createRow(1);
//        ReportIn200 reportIn200 = ReportIn200Fixture.get().withRandomData().build();
//        excelGenerator.gerarLinha(workbook, row, reportIn200);
//        assertThat(row.getRowNum()).isEqualTo(1);
//        assertThat(row.getCell(0).getStringCellValue()).isEqualTo(reportIn200.getDataBase());
//        assertThat(row.getCell(1).getStringCellValue()).isEqualTo(reportIn200.getDebtorIspb());
//        assertThat(row.getCell(2).getStringCellValue()).isEqualTo(reportIn200.getIspbFss());
//        assertThat(row.getCell(3).getStringCellValue()).isEqualTo(reportIn200.getCreditorIspb());
//        assertThat(row.getCell(4).getStringCellValue()).isEqualTo(reportIn200.getNumberOfTransactions().toString());
//    }
//
//    @Test
//
//    public void testCriarAbaPlanilha() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
//        InstantiationException,
//
//        IllegalAccessException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        List<ReportIn200> listaTitulo = Arrays.asList(
//            ReportIn200Fixture.get().withRandomData().build(),
//            ReportIn200Fixture.get().withRandomData().build()
//        );
//        excelGenerator.criarAbaPlanilha(workbook, listaTitulo);
//        XSSFSheet sheetAt = workbook.getSheetAt(0);
//        assertThat(sheetAt != null).isEqualTo(true);
//        assert sheetAt != null;
//        assertThat(sheetAt.getLastRowNum()).isEqualTo(2);
//    }
//
//    @Test
//
//    public void testCriarAbaPlanilhaListaNull()
//        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
//
//        IllegalAccessException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        excelGenerator.criarAbaPlanilha(workbook, null);
//        int numberOfSheets = workbook.getNumberOfSheets();
//        assertThat(numberOfSheets).isEqualTo(0);
//    }
//
//    @Test
//
//    public void testCriarAbaPlanilhaListaEmpty()
//        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
//
//        IllegalAccessException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        excelGenerator.criarAbaPlanilha(workbook, new ArrayList<>());
//        int numberOfSheets = workbook.getNumberOfSheets();
//        assertThat(numberOfSheets).isEqualTo(0);
//    }
//
//    @Test
//
//    public void testGerarPlanilhaLista()
//        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException,
//
//        IOException {
//        List<ReportIn200> listaTitulo = Arrays.asList(
//            ReportIn200Fixture.get().withRandomData().build(),
//            ReportIn200Fixture.get().withRandomData().build()
//        );
//        byte[] ret = excelGenerator.generateBytes(listaTitulo);
//        assertThat(ret != null).isEqualTo(true);
//    }
//
//    @Test
//
//    public void testGerarPlanilhaNull()
//        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException,
//
//        IOException {
//        byte[] ret = excelGenerator.generateBytes(null);
//        assertThat(ret != null).isEqualTo(true);
//    }
//
//    @Test
//
//    public void testRelatorioAllAnotations() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("testWithAllAnotations");
//        XSSFRow row = sheet.createRow(1);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        entities.ReportTemplate report = getReportTemplate();
//        excelGenerator.gerarLinha(workbook, row, report);
//        assertThat(row.getRowNum()).isEqualTo(1);
//        assertThat(row.getCell(0).getStringCellValue()).isEqualTo(report.getColunaString());
//        assertThat(row.getCell(1).getStringCellValue()).isEqualTo(formatter.format(report.getColunaData()));
//        assertThat(row.getCell(2).getStringCellValue()).isEqualTo("01.234.567/8910-11");
//        assertThat(row.getCell(3).getStringCellValue()).isEqualTo("5");
//        assertThat(row.getCell(4).getStringCellValue()).isEqualTo(report.getColunaDinheiro().toString());
//        assertThat(row.getCell(5).getStringCellValue()).isEqualTo(report.getColunaPercentual().toString());
//    }
//
//    @Test
//
//    public void testRelatorioAllAnotations_WithNullValues() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("testWithAllAnotations");
//        XSSFRow row = sheet.createRow(1);
//        entities.ReportTemplate report = getReportTemplate();
//        report.setColunaInteiro(null);
//        report.setColunaDinheiro(null);
//        report.setColunaData(null);
//        excelGenerator.gerarLinha(workbook, row, report);
//        assertThat(row.getRowNum()).isEqualTo(1);
//        assertThat(row.getCell(0).getStringCellValue()).isEqualTo(report.getColunaString());
//        assertThat(row.getCell(2).getStringCellValue()).isEqualTo("01.234.567/8910-11");
//        assertThat(row.getCell(3).getStringCellValue()).isEqualTo("0");
//        assertThat(row.getCell(4).getStringCellValue()).isEqualTo("0.00");
//        assertThat(row.getCell(5).getStringCellValue()).isEqualTo(report.getColunaPercentual().toString());
//    }
//
//
//    private entities.ReportTemplate getReportTemplate() {
//        entities.ReportTemplate report = new entities.ReportTemplate();
//        report.setColunaCNPJ("01234567891011");
//        report.setColunaData(LocalDate.now());
//        report.setColunaDinheiro(BigDecimal.TEN);
//        report.setColunaInteiro(5);
//        report.setColunaPercentual(BigDecimal.valueOf(50));
//        report.setColunaString(RandomStringUtils.randomAlphabetic(5));
//        report.setColunaSemAnotation(RandomStringUtils.randomAlphabetic(5));
//        return report;
//    }
//}