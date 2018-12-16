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

import org.coode.owlapi.owlxmlparser.OWLXMLParserException;
import org.semanticweb.owlapi.owllink.Response;

import java.util.List;
import java.util.Vector;

/**
 * Author: Olaf Noppens
 * Date: 23.10.2009
 */
public class OWLlinkResponseMessageElementHandler extends AbstractOWLlinkElementHandler<List<Object>> {
    protected List<Object> responses;

    /** @param handler handler */
    public OWLlinkResponseMessageElementHandler(OWLlinkXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void attribute(String localName, String value) throws OWLXMLParserException {
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        this.responses = new Vector<>();
    }

    @Override
    public List<Object> getOWLLinkObject() {
        return this.responses;
    }

    @Override
    public void handleChild(OWLlinkElementHandler<?> h) throws OWLXMLParserException {
        try {
            if (h.getOWLObject() instanceof Response)
                this.responses.add(h.getOWLObject());
        } catch (OWLXMLParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement() throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkResponseElementHandler<?> h) throws OWLXMLParserException {
        //if response ==> Prefixes ==> which kb? prov.put(kb, prefixes);
        this.responses.add(h.getOWLLinkObject());
    }

    @Override
    public void handleChild(OWLlinkErrorElementHandler<?> h) throws OWLXMLParserException {
        this.responses.add(h.getOWLLinkObject());
    }

    protected void handle(Response response) {
        this.responses.add(response);
    }

    @Override
    public void handleChild(OWLlinkClassSynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkObjectPropertySynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkDataPropertySynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkBooleanResponseElementHandler h) {
        handle(h.getOWLLinkObject());
    }

    /**
     * @param h h 
     */
    public void handleChild(OWLlinkStringResponseElementHandler h) {
        handle(h.getOWLLinkObject());
    }
}
