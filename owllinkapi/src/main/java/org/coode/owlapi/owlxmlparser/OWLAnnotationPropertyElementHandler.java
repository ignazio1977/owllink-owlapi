package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 23-Apr-2009
 */
public class OWLAnnotationPropertyElementHandler extends
        AbstractOWLElementHandler<OWLAnnotationProperty> {

    private OWLAnnotationProperty prop;
    private IRI iri;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLAnnotationPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public OWLAnnotationProperty getOWLObject() {
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
        prop = getOWLDataFactory().getOWLAnnotationProperty(iri);
        getParentHandler().handleChild(this);
    }
}
