package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public class OWLDataPropertyElementHandler extends
        AbstractOWLElementHandler<OWLDataPropertyExpression> {

    private OWLDataPropertyExpression prop;
    private IRI iri;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDataPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public OWLDataPropertyExpression getOWLObject() {
        return prop;
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        prop = getOWLDataFactory().getOWLDataProperty(iri);
        getParentHandler().handleChild(this);
    }
}
