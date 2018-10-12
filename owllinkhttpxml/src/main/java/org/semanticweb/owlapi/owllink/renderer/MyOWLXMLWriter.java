/*
 * Copyright (C) 2010, Ulm University
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.semanticweb.owlapi.owllink.renderer;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriter;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterImpl;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterNamespaceManager;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

import java.io.PrintWriter;
import java.net.URI;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Author: Olaf Noppens
 * Date: 18.02.2010
 */
public class MyOWLXMLWriter extends org.coode.owlapi.owlxmlrenderer.OWLXMLWriter {

    private XMLWriter writer;

    private Map<String, String> iriPrefixMap = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            int diff = o1.length() - o2.length();
            if (diff != 0) {
                return diff;
            }
            return o1.compareTo(o2);
        }
    });

    public MyOWLXMLWriter(PrintWriter writer, OWLOntology ontology, OWLOntologyWriterConfiguration config) {
        super(writer, ontology, config);
        XMLWriterNamespaceManager nsm = new XMLWriterNamespaceManager(Namespaces.OWL.toString());
        nsm.setPrefix("xsd", Namespaces.XSD.toString());
        nsm.setPrefix("rdf", Namespaces.RDF.toString());
        nsm.setPrefix("rdfs", Namespaces.RDFS.toString());
        nsm.setPrefix("xml", Namespaces.XML.toString());
        String base = Namespaces.OWL.toString();
        if (ontology != null && !ontology.isAnonymous()) {
            base = ontology.getOntologyID().getOntologyIRI().toString();
        }
        this.writer = new XMLWriterImpl(writer, nsm, base, config);
    }

    public MyOWLXMLWriter(XMLWriter writer, OWLOntology ontology, OWLOntologyWriterConfiguration config) {
        super(null, null, config);
        this.writer = writer;
    }


    @Override
    public Map<String, String> getIRIPrefixMap() {
        return iriPrefixMap;
    }

    @Override
    public XMLWriterNamespaceManager getNamespaceManager() {
        return writer.getNamespacePrefixes();
    }

    /**
     * A convenience method to write a prefix.
     *
     * @param prefixName The name of the prefix (e.g.  owl: is the prefix name for the OWL prefix)
     * @param iri        The prefix iri
     */
    @Override
    public void writePrefix(String prefixName, String iri) {
        writer.writeStartElement(OWLXMLVocabulary.PREFIX.getIRI());
        if (prefixName.endsWith(":")) {
            String attName = prefixName.substring(0, prefixName.length() - 1);
            writer.writeAttribute(OWLXMLVocabulary.NAME_ATTRIBUTE.getIRI().toString(), attName);
        } else {
            writer.writeAttribute(OWLXMLVocabulary.NAME_ATTRIBUTE.getIRI().toString(), prefixName);
        }
        writer.writeAttribute(OWLXMLVocabulary.IRI_ATTRIBUTE.getIRI().toString(), iri);
        writer.writeEndElement();
        iriPrefixMap.put(iri, prefixName);
    }

    /**
     * Gets an IRI attribute value for a full IRI.  If the IRI has a prefix that coincides with
     * a written prefix then the compact IRI will be returned, otherwise the full IRI will be returned.
     *
     * @param iri The IRI
     * @return Either the compact version of the IRI or the full IRI.
     */
    public String getIRIString(URI iri) {
        String fullIRI = iri.toString();
        for (String prefixName : iriPrefixMap.keySet()) {
            if (fullIRI.startsWith(prefixName)) {
                StringBuilder sb = new StringBuilder();
                sb.append(iriPrefixMap.get(prefixName));
                sb.append(fullIRI.substring(prefixName.length()));
                return sb.toString();
            }
        }
        return fullIRI;
    }


    @Override
    public void startDocument(OWLOntology ontology) {
        writer.startDocument(OWLXMLVocabulary.ONTOLOGY.getIRI());
        if (!ontology.isAnonymous()) {
            writer.writeAttribute(Namespaces.OWL + "ontologyIRI", ontology.getOntologyID().getOntologyIRI().toString());
            if (ontology.getOntologyID().getVersionIRI() != null) {
                writer.writeAttribute(Namespaces.OWL + "versionIRI", ontology.getOntologyID().getVersionIRI().toString());
            }
        }
    }


    @Override
    public void endDocument() {
        writer.endDocument();
        writer.writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
    }


    @Override
    public void writeStartElement(OWLXMLVocabulary name) {
        writer.writeStartElement(name.getIRI());
    }


    @Override
    public void writeEndElement() {
        writer.writeEndElement();
    }


    /**
     * Writes a datatype attributed (used on Literal elements).  The full datatype IRI is written out
     *
     * @param datatype The datatype
     */
    @Override
    public void writeDatatypeAttribute(OWLDatatype datatype) {
        writer.writeAttribute(OWLXMLVocabulary.DATATYPE_IRI.getIRI().toString(), datatype.getIRI().toString());
    }

    @Override
    public void writeNodeIDAttribute(NodeID nodeID) {
        writer.writeAttribute(OWLXMLVocabulary.NODE_ID.getIRI().toString(), nodeID.toString());
    }

    @Override
    public void writeIRIAttribute(IRI iri) {
        String attName = OWLXMLVocabulary.IRI_ATTRIBUTE.getIRI().toString();
        String value = iri.toString();
        if (value.startsWith(writer.getXMLBase())) {
            writer.writeAttribute(attName, value.substring(writer.getXMLBase().length(), value.length()));
        } else {
            String val = getIRIString(iri.toURI());
            if (!val.equals(iri.toString())) {
                writer.writeAttribute(OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE.getIRI().toString(), val);
            } else {
                writer.writeAttribute(attName, val);
            }
        }
    }

    @Override
    public void writeIRIElement(IRI iri) {
        String iriString = iri.toString();
        if (iriString.startsWith(writer.getXMLBase())) {
            writeStartElement(OWLXMLVocabulary.IRI_ELEMENT);
            writeTextContent(iriString.substring(writer.getXMLBase().length(), iriString.length()));
            writeEndElement();
        } else {
            String val = getIRIString(iri.toURI());
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

    @Override
    public void writeLangAttribute(String lang) {
        writer.writeAttribute(Namespaces.XML + "lang", lang);
    }

    @Override
    public void writeCardinalityAttribute(int cardinality) {
        writer.writeAttribute(OWLXMLVocabulary.CARDINALITY_ATTRIBUTE.getIRI().toString(), Integer.toString(cardinality));
    }

    @Override
    public void writeTextContent(String text) {
        writer.writeTextContent(text);
    }

    @Override
    public void writeFacetAttribute(OWLFacet facet) {
        writer.writeAttribute(OWLXMLVocabulary.DATATYPE_FACET.getIRI().toString(), facet.getIRI().toString());
    }

    @Override
    public void writeAnnotationURIAttribute(URI uri) {
        writer.writeAttribute(OWLXMLVocabulary.ANNOTATION_URI.toString(), uri.toString());
    }
}