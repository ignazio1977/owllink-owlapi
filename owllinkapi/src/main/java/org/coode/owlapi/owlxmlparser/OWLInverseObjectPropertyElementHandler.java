package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLInverseObjectPropertyElementHandler extends
        AbstractOWLObjectPropertyElementHandler {

    private OWLObjectPropertyExpression inverse;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLInverseObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        inverse = handler.getOWLObject();
    }

    @Override
    protected void endObjectPropertyElement() throws OWLXMLParserException {
        if (inverse == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(),
                    OWLXMLVocabulary.OBJECT_INVERSE_OF.getShortForm());
        }
        setOWLObjectPropertyExpression(getOWLDataFactory().getOWLObjectInverseOf(inverse.asOWLObjectProperty()));
    }
}
