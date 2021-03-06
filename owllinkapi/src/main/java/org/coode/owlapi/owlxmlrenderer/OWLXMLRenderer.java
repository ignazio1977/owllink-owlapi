/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
