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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.owllink.PrefixManagerProvider;

/**
 * @author Olaf Noppens
 */
public interface OWLlinkWriter {

    /**
     * @param isRequest isRequest 
     */
    void startDocument(boolean isRequest);

    /**
     * End document.
     */
    void endDocument();

    /**
     * @param name name 
     */
    void writeStartElement(IRI name);

    /**
     * End element.
     */
    void writeEndElement();

    /**
     * @param attribute attribute 
     * @param value value 
     */
    void writeAttribute(String attribute, String value);

    /**
     * @param attribute attribute 
     * @param value value 
     */
    void writeAttribute(IRI attribute, String value);

    /**
     * @param isNegative isNegative 
     */
    void writeNegativeAttribute(boolean isNegative);

    /**
     * @param isNegative isNegative 
     */
    void writeDirectAttribute(boolean isNegative);

    /**
     * @param kb kb 
     */
    void writeKBAttribute(IRI kb);

    /**
     * @param iri iri 
     */
    void writeFullIRIAttribute(IRI iri);

    /**
     * @param object object 
     * @param KB KB 
     */
    void writeOWLObject(OWLObject object, IRI KB);

    /**
     * @param text text 
     */
    void writeTextContent(String text);

    /** @return prefix manager provider */
    PrefixManagerProvider getPrefixManagerProvider();
}
