package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.RemappingIndividualProvider;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group, Date: 17-May-2009
 */
public class OWLAnonymousIndividualElementHandler extends
    AbstractOWLElementHandler<OWLAnonymousIndividual> {

    protected RemappingIndividualProvider anonProvider;
    private OWLAnonymousIndividual ind;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLAnonymousIndividualElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        anonProvider = new RemappingIndividualProvider(new OntologyConfigurator(), getOWLDataFactory());
    }

    @Override
    public OWLAnonymousIndividual getOWLObject() throws OWLXMLParserException {
        return ind;
    }

    @Override
    public void attribute(String localName, String value)
        throws OWLParserException {
        if (localName.equals(OWLXMLVocabulary.NODE_ID.getShortForm())) {
            ind = anonProvider.getOWLAnonymousIndividual(value.trim());
        } else {
            super.attribute(localName, value);
        }
    }

    @Override
    public void endElement() throws OWLParserException,
        UnloadableImportException {
        getParentHandler().handleChild(this);
    }
}
