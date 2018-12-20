package org.coode.owlapi.owlxmlrenderer;

import java.io.PrintWriter;
import java.util.Map;

import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyWriterConfiguration;
import org.semanticweb.owlapi.vocab.Namespaces;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group, Date:
 *         12-Dec-2006
 */
public class OWLXMLRenderer extends AbstractOWLRenderer {
    private OWLOntologyWriterConfiguration config = new OWLOntologyWriterConfiguration();

    /**
     * @param ontology ontology
     * @param writer writer
     * @param format format
     */
    public void render(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format) {
        OWLXMLWriter w = new OWLXMLWriter(writer, ontology, config);
        w.startDocument(ontology);
        if (format instanceof PrefixDocumentFormat) {
            PrefixDocumentFormat fromPrefixFormat = (PrefixDocumentFormat) format;
            final Map<String, String> map = fromPrefixFormat.getPrefixName2PrefixMap();
            for (String prefixName : map.keySet()) {
                String prefix = map.get(prefixName);
                if (prefix != null && prefix.length() > 0) {
                    w.writePrefix(prefixName, prefix);
                }
            }
            if (!map.containsKey("rdf:")) {
                w.writePrefix("rdf:", Namespaces.RDF.toString());
            }
            if (!map.containsKey("rdfs:")) {
                w.writePrefix("rdfs:", Namespaces.RDFS.toString());
            }
            if (!map.containsKey("xsd:")) {
                w.writePrefix("xsd:", Namespaces.XSD.toString());
            }
            if (!map.containsKey("owl:")) {
                w.writePrefix("owl:", Namespaces.OWL.toString());
            }
        } else {
            w.writePrefix("rdf:", Namespaces.RDF.toString());
            w.writePrefix("rdfs:", Namespaces.RDFS.toString());
            w.writePrefix("xsd:", Namespaces.XSD.toString());
            w.writePrefix("owl:", Namespaces.OWL.toString());
        }
        OWLXMLObjectRenderer ren = new OWLXMLObjectRenderer(w);
        ontology.accept(ren);
        w.endDocument();
        writer.flush();
    }

    @Override
    public void render(OWLOntology ontology, PrintWriter writer) throws OWLRendererException {
        render(ontology, writer);
    }
}
