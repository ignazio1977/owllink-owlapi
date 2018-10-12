package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectAllValuesFromElementHandler extends
        AbstractClassExpressionFillerRestriction {

    /** element name */
    public static final String ELEMENT_NAME = OWLXMLVocabulary.OBJECT_SOME_VALUES_FROM
            .toString();

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectAllValuesFromElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLClassExpression createRestriction() {
        return getOWLDataFactory().getOWLObjectAllValuesFrom(getProperty(),
                getFiller());
    }
}
