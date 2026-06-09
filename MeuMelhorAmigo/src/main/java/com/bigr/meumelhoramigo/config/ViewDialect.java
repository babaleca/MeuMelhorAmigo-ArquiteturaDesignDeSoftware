package com.bigr.meumelhoramigo.config;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.Set;

/**
 * Expõe o ViewHelper como objeto de expressão do Thymeleaf: #view
 *
 * Por que não usar @view (bean Spring)? Porque o Thymeleaf avalia expressões
 * de ATRIBUTOS (th:attr, th:data-*, th:href, etc.) em "modo restrito", onde
 * referências a beans (@bean) são proibidas. Objetos de expressão (#view),
 * por outro lado, são permitidos em qualquer contexto — assim como #strings.
 */
@Component
public class ViewDialect extends AbstractDialect implements IExpressionObjectDialect {

    private final ViewHelper viewHelper;
    private final IExpressionObjectFactory factory;

    public ViewDialect(ViewHelper viewHelper) {
        super("MeuMelhorAmigoViewDialect");
        this.viewHelper = viewHelper;
        this.factory = new IExpressionObjectFactory() {
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("view");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return "view".equals(expressionObjectName) ? ViewDialect.this.viewHelper : null;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return factory;
    }
}
