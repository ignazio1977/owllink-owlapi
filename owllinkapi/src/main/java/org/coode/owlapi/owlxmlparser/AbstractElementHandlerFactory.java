package org.coode.owlapi.owlxmlparser;

import java.util.function.Function;

import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class AbstractElementHandlerFactory implements
        OWLElementHandlerFactory {

    private String elementName;
    private Function<OWLXMLParserHandler, OWLElementHandler<?>> f;

    /**
     * @param v
     *        vocabulary
     * @param f builder
     */
    public AbstractElementHandlerFactory(OWLXMLVocabulary v, Function<OWLXMLParserHandler, OWLElementHandler<?>> f) {
        elementName = v.getShortForm();
        this.f = f;
    }

    /**
     * @param elementName
     *        name
     * @param f builder
     */
    public AbstractElementHandlerFactory(String elementName, Function<OWLXMLParserHandler, OWLElementHandler<?>> f) {
        this.elementName = elementName;
        this.f = f;
    }

    @Override
    public String getElementName() {
        return elementName;
    }

    @Override
    public final OWLElementHandler<?> createHandler(OWLXMLParserHandler handler) {
        return f.apply(handler);
    }
}
