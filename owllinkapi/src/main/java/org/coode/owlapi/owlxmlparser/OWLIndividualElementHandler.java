package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public class OWLIndividualElementHandler extends
        AbstractOWLElementHandler<OWLNamedIndividual> {

    private OWLNamedIndividual individual;
    private IRI name;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLIndividualElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public OWLNamedIndividual getOWLObject() {
        return individual;
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        name = getIRIFromAttribute(localName, value);
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        // URI uri = getNameAttribute();
        individual = getOWLDataFactory().getOWLNamedIndividual(name);
        getParentHandler().handleChild(this);
    }
}
