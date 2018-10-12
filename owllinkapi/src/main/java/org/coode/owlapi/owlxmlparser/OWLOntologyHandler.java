package org.coode.owlapi.owlxmlparser;

import java.util.Optional;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public class OWLOntologyHandler extends AbstractOWLElementHandler<OWLOntology> {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLOntologyHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {}

    @Override
    public void attribute(String name, String value) throws OWLParserException {
        if (name.equals("ontologyIRI")) {
            OWLOntologyID newID = new OWLOntologyID(Optional.of(IRI.create(value)), getOntology().getOntologyID().getVersionIRI());
            getOWLOntologyManager().applyChange(new SetOntologyID(getOntology(), newID));
        }
        if (name.equals("versionIRI")) {
            OWLOntologyID newID = new OWLOntologyID(getOntology().getOntologyID().getOntologyIRI(), Optional.of(IRI.create(value)));
            getOWLOntologyManager().applyChange(new SetOntologyID(getOntology(), newID));
        }
    }

    @Override
    public void handleChild(AbstractOWLAxiomElementHandler handler) throws OWLXMLParserException {
        OWLAxiom axiom = handler.getOWLObject();
        if (!axiom.isAnnotationAxiom() || getConfiguration().isLoadAnnotationAxioms()) {
            getOWLOntologyManager().applyChange(new AddAxiom(getOntology(), axiom));
        }
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        getOWLOntologyManager().applyChange(new AddOntologyAnnotation(getOntology(), handler.getOWLObject()));
    }

    @Override
    public void endElement() throws OWLParserException, UnloadableImportException {}

    @Override
    public OWLOntology getOWLObject() {
        return getOntology();
    }

    @Override
    public void setParentHandler(OWLElementHandler<?> handler) {}
}
