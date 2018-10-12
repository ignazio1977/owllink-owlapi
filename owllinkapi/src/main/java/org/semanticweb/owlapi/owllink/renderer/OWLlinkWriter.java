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

    void startDocument(boolean isRequest);

    void endDocument();

    void writeStartElement(IRI name);

    void writeEndElement();

    public void writeAttribute(String attribute, String value);

    public void writeAttribute(IRI attribute, String value);

    public void writeNegativeAttribute(boolean isNegative);

    public void writeDirectAttribute(boolean isNegative);

    public void writeKBAttribute(IRI kb);

    public void writeFullIRIAttribute(IRI iri);

    public void writeOWLObject(OWLObject object, IRI KB);

    public void writeTextContent(String text);

    public PrefixManagerProvider getPrefixManagerProvider();
}
