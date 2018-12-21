/*
 * This file is part of the OWLlink API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, derivo GmbH
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
 * Copyright 2011, derivo GmbH
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

package org.semanticweb.owlapi.owllink.parser;

import org.coode.owlapi.owlxmlparser.OWLXMLParserException;
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.owllink.OWLlinkXMLVocabulary;
import org.semanticweb.owlapi.owllink.builtin.response.PublicKB;
import org.semanticweb.owlapi.owllink.builtin.response.PublicKBImpl;

/**
 * User: noppens
 * Date: 21.10.2009
 * Time: 17:48:07
 * To change this template use File | Settings | File Templates.
 */
public class OWLlinkPublicKBElementHandler extends AbstractOWLlinkElementHandler<PublicKB> {
    private String name;
    private IRI kb;

    /** @param handler handler */
    public OWLlinkPublicKBElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String elementName) throws OWLXMLParserException {
        super.startElement(elementName);
        this.name = null;
        this.kb = null;
    }

    @Override
    public void attribute(String localName, String value) throws OWLParserException {
        if (OWLlinkXMLVocabulary.KB_ATTRIBUTE.getShortName().equalsIgnoreCase(localName)) {
            this.kb = getFullIRI(value);
        } else if (OWLlinkXMLVocabulary.NAME_Attribute.getShortName().equalsIgnoreCase(localName)) {
            this.name = value;
        }
    }

    @Override
    public PublicKB getOWLLinkObject() {
        if (name == null)
            return new PublicKBImpl(kb);
        return new PublicKBImpl(kb, name);
    }

    @Override
    public void endElement() throws OWLXMLParserException {
        getParentHandler().handleChild(this);
    }
}
