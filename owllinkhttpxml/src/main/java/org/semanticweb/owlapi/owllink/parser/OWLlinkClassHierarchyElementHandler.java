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
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.owllink.builtin.response.ClassHierarchyImpl;
import org.semanticweb.owlapi.owllink.builtin.response.Hierarchy;

/**
 * Created by IntelliJ IDEA.
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
public class OWLlinkClassHierarchyElementHandler extends AbstractOWLlinkHierarchyElementHandler<OWLClass> {

    public OWLlinkClassHierarchyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    public void handleChild(OWLlinkClassSubClassesPairElementHandler handler) throws OWLXMLParserException {
        super.pairs.add(handler.getOWLLinkObject());
    }

    public void handleChild(OWLlinkClassSynsetElementHandler handler) throws OWLXMLParserException {
        super.unsatisfiables = handler.getOWLLinkObject();
    }

    @Override
    public Hierarchy<OWLClass> getOWLLinkObject() throws OWLXMLParserException {
        if (super.unsatisfiables == null)
            throw new OWLlinkXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "Found zero elmements of ClassSynsets");
        return new ClassHierarchyImpl(super.pairs, super.unsatisfiables);
    }
}
