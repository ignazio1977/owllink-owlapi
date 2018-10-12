package org.coode.owlapi.owlxmlrenderer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URI;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyWriterConfiguration;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriter;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterImpl;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterNamespaceManager;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * Writes OWL/XML. In an OWL/XML documents written by this writer, the base is
 * always the ontology URI, and the default namespace is always the OWL
 * namespace (http://www.w3.org/2002/07/owl#). Unlike RDF/XML, entity URIs
 * aren't abbreviated using the XML namespace mechanism, instead they are
 * encoded using 'prefix' elements.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 12-Dec-2006
 */
public class OWLXMLWriter {

    private static final String LANG_IRI = "xml:lang";
    private static final IRI VERSION_IRI = IRI.create(
            Namespaces.OWL.getPrefixIRI(), "versionIRI");
    private static final IRI ONTOLOGY_IRI = IRI.create(
            Namespaces.OWL.getPrefixIRI(), "ontologyIRI");

    /**
     * String comparator that takes length into account before natural ordering.
     */
    private static final class StringLengthComparator implements
            Comparator<String>, Serializable {

        private static final long serialVersionUID = 30406L;

        public StringLengthComparator() {}

        @Override
        public int compare(String o1, String o2) {
            int diff = o1.length() - o2.length();
            if (diff != 0) {
                return diff;
            }
            return o1.compareTo(o2);
        }
    }

    private static final StringLengthComparator STRING_LENGTH_COMPARATOR = new StringLengthComparator();
    private XMLWriter writer;
    private Map<String, String> iriPrefixMap = new TreeMap<>(STRING_LENGTH_COMPARATOR);

    /**
     * @param writer
     *        writer
     * @param ontology
     *        ontology
     */
    public OWLXMLWriter(PrintWriter writer, OWLOntology ontology, OWLOntologyWriterConfiguration config) {
        XMLWriterNamespaceManager nsm = new XMLWriterNamespaceManager(Namespaces.OWL.toString());
        nsm.setPrefix("xsd", Namespaces.XSD.toString());
        nsm.setPrefix("rdf", Namespaces.RDF.toString());
        nsm.setPrefix("rdfs", Namespaces.RDFS.toString());
        nsm.setPrefix("xml", Namespaces.XML.toString());
        String base = Namespaces.OWL.toString();
        if (ontology != null && !ontology.isAnonymous()) {
            base = ontology.getOntologyID().getOntologyIRI().toString();
        }
        if(writer != null) {
            this.writer = new XMLWriterImpl(writer, nsm, base, config);
        }
    }

    /** @return iri to prefix map */
    public Map<String, String> getIRIPrefixMap() {
        return iriPrefixMap;
    }

    /** @return namespace manager */
    public XMLWriterNamespaceManager getNamespaceManager() {
        return writer.getNamespacePrefixes();
    }

    /**
     * A convenience method to write a prefix.
     * 
     * @param prefixName
     *        The name of the prefix (e.g. owl: is the prefix name for the OWL
     *        prefix)
     * @param iri
     *        The prefix iri
     * @throws IOException
     *         io error
     */
    public void writePrefix(String prefixName, String iri) {
        writer.writeStartElement(OWLXMLVocabulary.PREFIX.getIRI());
        if (prefixName.endsWith(":")) {
            String attName = prefixName.substring(0, prefixName.length() - 1);
            writer.writeAttribute(OWLXMLVocabulary.NAME_ATTRIBUTE.getIRI(),
                    attName);
        } else {
            writer.writeAttribute(OWLXMLVocabulary.NAME_ATTRIBUTE.getIRI(),
                    prefixName);
        }
        writer.writeAttribute(OWLXMLVocabulary.IRI_ATTRIBUTE.getIRI(), iri);
        writer.writeEndElement();
        iriPrefixMap.put(iri, prefixName);
    }

    /**
     * Gets an IRI attribute value for a full IRI. If the IRI has a prefix that
     * coincides with a written prefix then the compact IRI will be returned,
     * otherwise the full IRI will be returned.
     * 
     * @param iri
     *        The IRI
     * @return Either the compact version of the IRI or the full IRI.
     */
    public String getIRIString(IRI iri) {
        String prefixName = iriPrefixMap.get(iri.getNamespace());
        if (prefixName == null) {
            return iri.toString();
        }
        return iri.prefixedBy(prefixName);
    }

    /**
     * @param ontology
     *        ontology
     * @throws OWLRendererException
     *         renderer error
     */
    public void startDocument(OWLOntology ontology) {
        writer.startDocument(OWLXMLVocabulary.ONTOLOGY.getIRI());
        if (!ontology.isAnonymous()) {
            writer.writeAttribute(ONTOLOGY_IRI, ontology.getOntologyID()
                    .getOntologyIRI().toString());
            if (ontology.getOntologyID().getVersionIRI() != null) {
                writer.writeAttribute(VERSION_IRI, ontology.getOntologyID()
                        .getVersionIRI().toString());
            }
        }
    }

    /**
     * 
     */
    public void endDocument() {
        writer.endDocument();
        writer.writeComment(VersionInfo.getVersionInfo()
                .getGeneratedByMessage());
    }

    /**
     * @param name
     *        name
     */
    public void writeStartElement(OWLXMLVocabulary name) {
        writer.writeStartElement(name.getIRI());
    }

    /** write end element */
    public void writeEndElement() {
        writer.writeEndElement();
    }

    /**
     * Writes a datatype attributed (used on Literal elements). The full
     * datatype IRI is written out
     * 
     * @param datatype
     *        The datatype
     */
    public void writeDatatypeAttribute(OWLDatatype datatype) {
        writer.writeAttribute(OWLXMLVocabulary.DATATYPE_IRI.getIRI(), datatype.getIRI().toString());
    }

    /**
     * @param nodeID
     *        nodeID
     */
    public void writeNodeIDAttribute(NodeID nodeID) {
        writer.writeAttribute(OWLXMLVocabulary.NODE_ID.getIRI(), nodeID.getID());
    }

    /**
     * @param iri
     *        iri
     */
    public void writeIRIAttribute(IRI iri) {
        IRI attName = OWLXMLVocabulary.IRI_ATTRIBUTE.getIRI();
        String value = iri.toString();
        if (value.startsWith(writer.getXMLBase())) {
            writer.writeAttribute(attName, value.substring(writer.getXMLBase().length(), value.length()));
        } else {
            String val = getIRIString(iri);
            if (!val.equals(iri.toString())) {
                writer.writeAttribute(OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE.getIRI(), val);
            } else {
                writer.writeAttribute(attName, val);
            }
        }
    }

    /**
     * Writes an IRI element for a given IRI.
     * 
     * @param iri
     *        The IRI to be written as an element. If the IRI can be abbreviated
     *        then an AbbreviatedIRI element will be written
     */
    public void writeIRIElement(IRI iri) {
        String iriString = iri.toString();
        if (iriString.startsWith(writer.getXMLBase())) {
            writeStartElement(OWLXMLVocabulary.IRI_ELEMENT);
            writeTextContent(iriString.substring(writer.getXMLBase().length(), iriString.length()));
            writeEndElement();
        } else {
            String val = getIRIString(iri);
            if (!val.equals(iriString)) {
                writeStartElement(OWLXMLVocabulary.ABBREVIATED_IRI_ELEMENT);
                writer.writeTextContent(val);
                writeEndElement();
            } else {
                writeStartElement(OWLXMLVocabulary.IRI_ELEMENT);
                writer.writeTextContent(val);
                writeEndElement();
            }
        }
    }

    /**
     * @param lang
     *        lang
     */
    public void writeLangAttribute(String lang) {
        writer.writeAttribute(LANG_IRI, lang);
    }

    /**
     * @param cardinality
     *        cardinality
     */
    public void writeCardinalityAttribute(int cardinality) {
        writer.writeAttribute(OWLXMLVocabulary.CARDINALITY_ATTRIBUTE.getIRI(), Integer.toString(cardinality));
    }

    /**
     * @param text
     *        text
     */
    public void writeTextContent(String text) {
        writer.writeTextContent(text);
    }

    /**
     * @param facet
     *        facet
     */
    public void writeFacetAttribute(OWLFacet facet) {
        writer.writeAttribute(OWLXMLVocabulary.DATATYPE_FACET.getIRI(), facet.getIRI().toString());
    }

    /**
     * @param uri
     *        uri
     */
    public void writeAnnotationURIAttribute(URI uri) {
        writer.writeAttribute(OWLXMLVocabulary.ANNOTATION_URI.getIRI(), uri.toString());
    }
}
