package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public class OWLClassElementHandler extends
        AbstractClassExpressionElementHandler {

    private IRI iri;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLClassElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    public void endClassExpressionElement() throws OWLXMLParserException {
        if (iri == null) {
            throw new OWLXMLParserAttributeNotFoundException(getLineNumber(),
                    getColumnNumber(), "IRI");
        }
        setClassExpression(getOWLDataFactory().getOWLClass(iri));
    }
}
