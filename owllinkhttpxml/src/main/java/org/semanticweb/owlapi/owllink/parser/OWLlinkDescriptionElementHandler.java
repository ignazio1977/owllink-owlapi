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
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.owllink.OWLlinkXMLVocabulary;
import org.semanticweb.owlapi.owllink.builtin.response.*;

import java.util.HashSet;
import java.util.Set;

/**
 * User: noppens
 * Date: 21.10.2009
 * Time: 17:47:55
 * To change this template use File | Settings | File Templates.
 */
public class OWLlinkDescriptionElementHandler extends AbstractOWLlinkResponseElementHandler<Description> {
    private String name;
    private String message;
    private Set<PublicKB> publicKBs;
    private Set<Configuration> configurations;
    private ProtocolVersion pVersion;
    private ReasonerVersion rVersion;
    private Set<IRI> supportedExtensions;

    /** @param handler handler */
    public OWLlinkDescriptionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String elementName) throws OWLXMLParserException {
        super.startElement(elementName);
        this.name = null;
        this.message = null;
        this.publicKBs = new HashSet<>();
        this.configurations = new HashSet<>();
        this.supportedExtensions = new HashSet<>();
        this.pVersion = null;
        this.rVersion = null;
    }


    @Override
    public void attribute(String localName, String value) throws OWLXMLParserException {
        if (OWLlinkXMLVocabulary.NAME_Attribute.getShortName().equals(localName)) {
            this.name = value;
        } else if (OWLlinkXMLVocabulary.MESSAGE_ATTRIBUTE.getShortName().equals(localName)) {
            this.message = value;
        }
    }

    @Override
    public void endElement() throws OWLXMLParserException {
        getParentHandler().handleChild(this);
    }

    @Override
    public void handleChild(OWLlinkSettingElementHandler h) throws OWLXMLParserException {
        configurations.add(h.getOWLLinkObject());
    }

    @Override
    public void handleChild(OWLlinkPropertyElementHandler h) throws OWLXMLParserException {
        configurations.add(h.getOWLLinkObject());
    }

    @Override
    public void handleChild(OWLlinkPublicKBElementHandler h) throws OWLXMLParserException {
        publicKBs.add(h.getOWLLinkObject());
    }

    @Override
    public void handleChild(OWLlinkSupportedExtensionElemenetHandler h) throws OWLXMLParserException {
        supportedExtensions.add(h.getOWLLinkObject());
    }

    @Override
    public void handleChild(OWLlinkReasonerVersionElementHandler h) throws OWLXMLParserException {
        this.rVersion = h.getOWLLinkObject();
    }

    @Override
    public void handleChild(OWLlinkProtocolVersionElementHandler h) throws OWLXMLParserException {
        this.pVersion = h.getOWLLinkObject();
    }

    @Override
    public Description getOWLLinkObject() {
        return new DescriptionImpl(name, message, configurations, rVersion, pVersion, supportedExtensions, publicKBs);
    }
}
