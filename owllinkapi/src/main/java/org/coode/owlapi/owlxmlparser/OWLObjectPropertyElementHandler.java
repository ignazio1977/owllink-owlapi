package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectPropertyElementHandler extends
        AbstractOWLObjectPropertyElementHandler {

    private IRI iri;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    protected void endObjectPropertyElement() {
        setOWLObjectPropertyExpression(getOWLDataFactory()
                .getOWLObjectProperty(iri));
    }
}
