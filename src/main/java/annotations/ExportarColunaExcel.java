package annotations;

import enums.TipoColuna;
import enums.TipoMascara;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExportarColunaExcel {

    String titulo();

    String metodoNome() default "";

    int coluna();

    TipoMascara mascara() default TipoMascara.PADRAO;

    TipoColuna tipo() default TipoColuna.TEXTO;
}
