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

package org.semanticweb.owlapi.owllink;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.owllink.builtin.requests.GetEquivalentObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfObjectProperties;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkEquivalentObjectPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opb()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opb(), opc()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opb(), opa()));
        axioms.add(getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE()));
        return axioms;
    }

    public void testAreObjectPropertiesEquivalent() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opa(), opb()));
        BooleanResponse result = super.reasoner.answer(query);
        trueResponse(result);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opa(), opb(), opc()));
        result = super.reasoner.answer(query);
        falseResponse(result);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE(), opa()));
        result = super.reasoner.answer(query);
        falseResponse(result);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE()));
        result = super.reasoner.answer(query);
        trueResponse(result);
    }

    public void testAreObjectPropertiesEquivalentViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opa(), opb());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opa(), opb(), opc());
        assertFalse(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE(), opa());
        assertFalse(super.reasoner.isEntailed(axiom));


        axiom = getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opd(), opE());
        assertTrue(super.reasoner.isEntailed(axiom));
    }

    public void testGetEquivalentObjectProperties() {
        GetEquivalentObjectProperties query = new GetEquivalentObjectProperties(getKBIRI(), opa());
        SetOfObjectProperties result = super.reasoner.answer(query);
        assertEquals(set(opa(),opb()),result);
    }

    public void testGetEquivalentObjectPropertiesViaOWLReasoner() {
        Node<OWLObjectPropertyExpression> result = super.reasoner.getEquivalentObjectProperties(opa());
        assertEquals(set(opa(),opb()),asUnorderedSet(result.entities()));
    }
}
