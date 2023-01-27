import annotations.ExportarColunaExcel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelGeneratorAdapter<T> implements ExcelGenerator<T> {


    @Override
    public byte[] generateBytes(List<T> rows) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
        InstantiationException, IllegalAccessException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        criarAbaPlanilha(workbook, rows);

        return construirBinario(workbook);
    }


    /**
     * metodo para criar aba da planilha
     */
    protected void criarAbaPlanilha(XSSFWorkbook workbook, List<T> valoresLinhas)
        throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
        InvocationTargetException, InstantiationException {

        if (valoresLinhas == null || valoresLinhas.isEmpty()) {
            return;
        }

        String classeLinha = valoresLinhas.get(0).getClass().getName();
        XSSFSheet sheet = workbook.createSheet(classeLinha);

        AtomicInteger numeroLinha = new AtomicInteger();

        //gerar cabeçalho
        Object classeTitulo = Class.forName(classeLinha).getDeclaredConstructor().newInstance();
        gerarLinha(workbook, sheet.createRow(numeroLinha.getAndIncrement()), (T) classeTitulo);

        for (T valorLinha : valoresLinhas) {
            gerarLinha(workbook, sheet.createRow(numeroLinha.getAndIncrement()), valorLinha);
        }


    }


    /**
     * gerar linha da planilha, para cada objeto da lista gera uma linha.
     */
    protected void gerarLinha(XSSFWorkbook workbook, XSSFRow linha, T valorLinha) throws InvocationTargetException, IllegalAccessException,
        NoSuchMethodException {

        Field[] camposClasse = valorLinha.getClass().getDeclaredFields();
        for (Field campoClasse : camposClasse) {
            ExportarColunaExcel annotation = campoClasse.getAnnotation(ExportarColunaExcel.class);
            if (annotation != null) {
                gerarCelulaDaLinha(workbook, linha, valorLinha, campoClasse, annotation);
            }
        }
    }


    /**
     * gerar celulas da linha com os valores
     */
    protected void gerarCelulaDaLinha(XSSFWorkbook workbook, XSSFRow linha, T valorLinha, Field campo, ExportarColunaExcel anotacaoExportarExcel)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        boolean isHeader = linha.getRowNum() == 0;

        Cell cell = linha.createCell(anotacaoExportarExcel.coluna());
        if (isHeader) {
            cell.setCellValue(anotacaoExportarExcel.titulo());
        } else {
            atribuiValorCelula(workbook, valorLinha, campo, anotacaoExportarExcel, cell);
        }
    }


    /**
     * Atribui o valor na celula de acordo com o tipo do campo
     */
    protected void atribuiValorCelula(XSSFWorkbook workbook, T valorLinha, Field campo, ExportarColunaExcel anotacaoExportarExcel, Cell cell)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method metodo = procurarMetodoNaClasse(valorLinha.getClass(), campo.getName(), anotacaoExportarExcel.metodoNome());

        switch (anotacaoExportarExcel.tipo()) {
            case NUMERO -> formatValueNumber(valorLinha, cell, metodo);
            case DATA -> formatValueDate(valorLinha, cell, metodo);
            case INTEIRO -> formatValueInteger(valorLinha, cell, metodo);
            case TEXTO -> formatValueText(valorLinha, cell, metodo);
        }

        //Aplicar mascara
        anotacaoExportarExcel.mascara().instance().aplicarMascara(workbook, cell);
    }


    /**
     * procura o metodo com base na anotacao, caso nao encontrar usa o nome para achar
     */
    protected Method procurarMetodoNaClasse(Class<?> aClass, String nameCampo, String nomeMetodo) throws NoSuchMethodException {

        if (nomeMetodo != null && !nomeMetodo.isEmpty()) {
            return aClass.getMethod(nomeMetodo);
        }

        return aClass.getMethod("get" + nameCampo.substring(0, 1).toUpperCase() + nameCampo.substring(1));
    }


    /**
     * Constroi o array de byte que sera usado para exportar
     */
    protected byte[] construirBinario(XSSFWorkbook workbook) throws IOException {

        if (workbook == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }


    private void formatValueText(T valorLinha, Cell cell, Method metodo) throws IllegalAccessException, InvocationTargetException {
        cell.setCellValue((String) metodo.invoke(valorLinha));
    }


    private void formatValueInteger(T valorLinha, Cell cell, Method metodo) throws IllegalAccessException, InvocationTargetException {
        Object retorno = metodo.invoke(valorLinha);
        if (retorno != null) {
            Integer valor = (Integer) retorno;
            cell.setCellValue(valor.toString());
        } else {
            cell.setCellValue("0");
        }
    }


    private void formatValueDate(T valorLinha, Cell cell, Method metodo) throws IllegalAccessException, InvocationTargetException {
        Object data = metodo.invoke(valorLinha);
        if (data != null) {
            LocalDate localDate = (LocalDate) data;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String format = localDate.format(formatter);
            cell.setCellValue(format);
        }
    }


    private void formatValueNumber(T valorLinha, Cell cell, Method metodo) throws IllegalAccessException, InvocationTargetException {
        Object retorno = metodo.invoke(valorLinha);
        if (retorno != null) {
            BigDecimal valor = (BigDecimal) retorno;
            cell.setCellValue(valor.toString());
        } else {
            cell.setCellValue("0.00");
        }
    }


}