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

import org.coode.owlapi.owlxmlparser.OWLDataPropertyElementHandler;
import org.coode.owlapi.owlxmlparser.OWLXMLParserException;
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.owllink.builtin.response.DataPropertySynonyms;
import org.semanticweb.owlapi.owllink.builtin.response.DataPropertySynonymsImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 11.12.2009
 */
public class OWLlinkDataPropertySynonymsElementHandler extends AbstractOWLlinkKBResponseElementHandler<DataPropertySynonyms> {
    protected Set<OWLDataProperty> props;

    /** @param handler handler */
    public OWLlinkDataPropertySynonymsElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        this.props = new HashSet<>();
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler h) throws OWLXMLParserException {
        this.props.add(h.getOWLObject().asOWLDataProperty());
    }

    @Override
    public DataPropertySynonyms getOWLLinkObject() {
        return new DataPropertySynonymsImpl(this.props);
    }
}
