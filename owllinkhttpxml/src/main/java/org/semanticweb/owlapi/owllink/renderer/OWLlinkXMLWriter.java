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


import org.coode.owlapi.owlxmlrenderer.OWLXMLObjectRenderer;
import org.coode.owlapi.owlxmlrenderer.OWLXMLWriter;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyWriterConfiguration;
import org.semanticweb.owlapi.owllink.OWLlinkNamespaces;
import org.semanticweb.owlapi.owllink.OWLlinkXMLVocabulary;
import org.semanticweb.owlapi.owllink.PrefixManagerProvider;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriter;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterNamespaceManager;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.Namespaces;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author Olaf Noppens
 */
public class OWLlinkXMLWriter implements OWLlinkWriter {
    private XMLWriter writer;
    private PrefixManagerProvider prefixProvider;
    Map<IRI, OWLXMLObjectRenderer> rendererByIRI;
    OWLXMLObjectRenderer defaultRenderer;
    Writer baseWriter;
    OWLOntologyWriterConfiguration config;

    public OWLlinkXMLWriter(PrintWriter writer, PrefixManagerProvider prefixProvider, OWLOntologyWriterConfiguration config) {
        this.config=config;
        XMLWriterNamespaceManager nsm = new XMLWriterNamespaceManager(OWLlinkNamespaces.OWLLink.toString() + "#");
        nsm.setPrefix("xsd", Namespaces.XSD.toString());
        nsm.setPrefix("owl", Namespaces.OWL.toString());
        String base = OWLlinkNamespaces.OWLLink.toString();
        //we need an own xml writer because in OWL attribute's NS are not allowed.
        this.writer = new MyXMLWriterImpl(writer, nsm, base, config) {
            @Override
            public void writeAttribute(String attr, String val) {
                if (attr.startsWith(Namespaces.OWL.toString())) {
                    String localName = attr.substring(Namespaces.OWL.toString().length(), attr.length());
                    super.writeAttribute(localName, val);
                } else
                    super.writeAttribute(attr, val);
            }
        };
        this.writer.setEncoding("UTF-8");
        OWLXMLWriter owlxmlWriter = new MyOWLXMLWriter(this.writer, null, config);
        this.defaultRenderer = new OWLXMLObjectRenderer(owlxmlWriter);
        this.baseWriter = writer;
        rendererByIRI = CollectionFactory.createMap();
        this.prefixProvider = prefixProvider;
    }

    @Override
    public void startDocument(final boolean isRequest) {
        if (isRequest)
            writer.startDocument(OWLlinkXMLVocabulary.REQUEST_MESSAGE.getIRI());
        else
            writer.startDocument(OWLlinkXMLVocabulary.RESPONSE_MESSAGE.getIRI());
    }

    @Override
    public void endDocument() {
        writer.endDocument();
    }

    public final void writeStartElement(OWLlinkXMLVocabulary v) {
        this.writeStartElement(v.getURI());
    }

    @Override
    public void writeStartElement(IRI name) {
        writer.writeStartElement(name);
    }

    @Override
    public void writeEndElement() {
        writer.writeEndElement();
    }

    @Override
    public void writeAttribute(String attribute, String value) {
        writer.writeAttribute(attribute, value);
    }

    @Override
    public void writeAttribute(IRI attribute, String value) {
        writer.writeAttribute(attribute.toString(), value);
    }

    @Override
    public void writeNegativeAttribute(boolean isNegative) {
        writer.writeAttribute(OWLlinkXMLVocabulary.NEGATIVE_ATTRIBUTE.getURI().toString(), Boolean.toString(isNegative));
    }

    @Override
    public void writeDirectAttribute(boolean isNegative) {
        writer.writeAttribute(OWLlinkXMLVocabulary.DIRECT_ATTRIBUTE.getURI().toString(), Boolean.toString(isNegative));
    }

    @Override
    public void writeKBAttribute(IRI kb) {
        writer.writeAttribute(OWLlinkXMLVocabulary.KB_ATTRIBUTE.getURI().toString(), kb.toString());
    }

    @Override
    public void writeFullIRIAttribute(IRI iri) {
        writer.writeAttribute(OWLlinkXMLVocabulary.IRI_ATTRIBUTE.getURI().toString(), iri.toString());
    }

    @Override
    public void writeOWLObject(OWLObject object, IRI KB) {
        if (KB == null) {
            object.accept(defaultRenderer);
        } else {
            OWLXMLObjectRenderer renderer = rendererByIRI.get(KB);
            if (renderer == null) {
                //OWLXMLWriter writer = new OWLXMLWriter(baseWriter, null);
                OWLXMLWriter writer = new MyOWLXMLWriter(this.writer, null, config);
                if (prefixProvider.contains(KB)) {
                    Map<String, String> map = prefixProvider.getPrefixes(KB).getPrefixName2PrefixMap();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        writer.getIRIPrefixMap().put(entry.getValue(), entry.getKey());
                    }
                }
                renderer = new OWLXMLObjectRenderer(writer);
                rendererByIRI.put(KB, renderer);
            }
            object.accept(renderer);
        }
    }

    @Override
    public void writeTextContent(String text) {
        writer.writeTextContent(text);
    }

    @Override
    public PrefixManagerProvider getPrefixManagerProvider() {
        return this.prefixProvider;
    }
}
