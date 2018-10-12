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

package org.semanticweb.owllink.protege;

import org.protege.editor.owl.model.inference.AbstractProtegeOWLReasonerInfo;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * Author: Olaf Noppens
 * Date: 14.05.2010
 */
public class OWLlinkHTTPXMLReasonerFactory extends AbstractProtegeOWLReasonerInfo {

    private org.semanticweb.owlapi.owllink.OWLlinkHTTPXMLReasonerFactory factory;

    @Override
    public void dispose() {
        this.factory = null;
    }

    @Override
    public void initialise() {
        this.factory = new org.semanticweb.owlapi.owllink.OWLlinkHTTPXMLReasonerFactory();
    }

    @Override
    public OWLReasonerFactory getReasonerFactory() {
        return factory;
    }

    @Override
    public BufferingMode getRecommendedBuffering() {
        return BufferingMode.NON_BUFFERING;
    }
}
