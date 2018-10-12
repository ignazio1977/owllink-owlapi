package org.coode.owlapi.owlxmlrenderer;

import java.io.PrintWriter;
import org.coode.owlapi.owlxmlrenderer.OWLXMLRenderer;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLStorer;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 07-Jan-2007
 */
public class OWLXMLOntologyStorer extends AbstractOWLStorer {

    private static final long serialVersionUID = 30406L;

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontologyFormat.equals(new OWLXMLDocumentFormat());
    }

    @Override
    protected void storeOntology(OWLOntology ontology, PrintWriter writer,
        OWLDocumentFormat format) throws OWLOntologyStorageException {
        OWLXMLRenderer renderer = new OWLXMLRenderer();
        renderer.render(ontology, writer);
    }
}
