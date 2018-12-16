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

import org.coode.owlapi.owlxmlparser.AbstractOWLElementHandler;
import org.coode.owlapi.owlxmlparser.OWLXMLParserException;
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.owllink.Request;

/**
 * Author: Olaf Noppens
 * Date: 21.10.2009
 * @param <O> object type
 */
public abstract class AbstractOWLlinkElementHandler<O> extends AbstractOWLElementHandler<O> implements OWLlinkElementHandler<O> {
    OWLlinkXMLParserHandler handler;

    /** @param handler handler */
    public AbstractOWLlinkElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        this.handler = (OWLlinkXMLParserHandler) handler;
    }

    @Override
    public void handleChild(OWLlinkClassSubClassesPairElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkDataPropertySubDataPropertiesPairElementHandler h) {
    }

    @Override
    public void handleChild(OWLlinkObjectPropertySubPropertiesPairElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSubDataPropertySynsetsElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSubObjectPropertySynsetsElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSubClassSynsetsElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkResponseElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkErrorElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkBooleanResponseElementHandler h) {
    }

    @Override
    public void handleChild(OWLlinkConfigurationElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkDataRangeElementHandler<?> h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkLiteralElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkPrefixElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkProtocolVersionElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkReasonerVersionElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkPublicKBElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSupportedExtensionElemenetHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkClassSynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkSettingElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkPropertyElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkObjectPropertySynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkDataPropertySynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkIndividualSynsetElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public void handleChild(OWLlinkResponseMessageElementHandler h) throws OWLXMLParserException {
    }

    @Override
    public abstract O getOWLLinkObject() throws OWLXMLParserException;

    @Override
    public O getOWLObject() throws OWLXMLParserException {
        return this.getOWLLinkObject();
    }

    /** @param value value 
     * @return iri */
    public IRI getFullIRI(String value) {
        return super.getIRI(value);
    }

    @Override
    protected OWLlinkElementHandler<?> getParentHandler() {
        return (OWLlinkElementHandler<?>) super.getParentHandler();
    }

    protected Request<?> getRequest() {
        int index = handler.responseMessageHandler.getOWLLinkObject().size();
        return handler.getRequest(index);
    }
}
