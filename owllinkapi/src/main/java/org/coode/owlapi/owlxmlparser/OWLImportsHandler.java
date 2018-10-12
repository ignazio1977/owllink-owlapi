package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 18-Dec-2006
 */
public class OWLImportsHandler extends AbstractOWLElementHandler<OWLOntology> {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLImportsHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        IRI ontIRI = getIRI(getText().trim());
        OWLImportsDeclaration decl = getOWLDataFactory()
                .getOWLImportsDeclaration(ontIRI);
        getOWLOntologyManager().applyChange(new AddImport(getOntology(), decl));
        getOWLOntologyManager().makeLoadImportRequest(decl, getConfiguration());
    }

    @Override
    public OWLOntology getOWLObject() {
        return null;
    }

    @Override
    public boolean isTextContentPossible() {
        return true;
    }
}
