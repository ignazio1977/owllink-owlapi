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

package org.semanticweb.owlapi.owllink.parser;

import org.coode.owlapi.owlxmlparser.*;
import org.semanticweb.owlapi.model.*;
import java.util.*;

/**
 * SWR*Handler: protected class constructor
 * <p/>
 * This OWLXMLParserHandler is based on Matthew Horridge's implementation.
 */
public class MyOWLXMLParserHandler extends OWLXMLParserHandler {

    /**
     * Creates an OWLXML handler.
     *
     * @param owlOntologyManager The manager that should be used to obtain a data factory,
     *                           imported ontologies etc.
     * @param ontology           The ontology that the XML representation will be parsed into.
     */
    public MyOWLXMLParserHandler(OWLOntologyManager owlOntologyManager, OWLOntology ontology) {
        this(owlOntologyManager, ontology, null);
    }

    /**
     * Creates an OWLXML handler with the specified top level handler.  This allows OWL/XML
     * representations of axioms to be embedded in abitrary XML documents e.g. DIG 2.0 documents.
     * (The default handler behaviour expects the top level element to be an Ontology
     * element).
     *
     * @param owlOntologyManager The manager that should be used to obtain a data factory,
     *                           imported ontologies etc.
     * @param ontology           The ontology object that the XML representation should be parsed into.
     * @param topHandler         The handler for top level elements - may be <code>null</code>, in which
     *                           case the parser will expect an Ontology element to be the root element.
     */
    public MyOWLXMLParserHandler(OWLOntologyManager owlOntologyManager, OWLOntology ontology,
                                 OWLElementHandler<?> topHandler) {
        super(owlOntologyManager, ontology, topHandler);
    }

    /**
     * @param map map 
     */
    public void setPrefixName2PrefixMap(Map<String, String> map) {
        prefixName2PrefixMap.clear();
        prefixName2PrefixMap.putAll(map);
    }
}
