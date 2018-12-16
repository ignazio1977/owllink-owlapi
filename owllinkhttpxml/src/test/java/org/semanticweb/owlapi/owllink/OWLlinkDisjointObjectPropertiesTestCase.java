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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDisjointObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.ObjectPropertySynsets;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkDisjointObjectPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb(), opc()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opd()));
        return axioms;
    }

    public void testAreObjectPropertiesDisjoint() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb()));
        BooleanResponse answer = reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb(), opc()));
        answer = reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb(), opE()));
        answer = reasoner.answer(query);
        falseResponse(answer);


        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb(), opc()));
        answer = reasoner.answer(query);
        trueResponse(answer);
    }

    public void testAreObjectPropertiesDisjointViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb(), opc());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb(), opE());
        assertFalse(reasoner.isEntailed(axiom));


        axiom = getDataFactory().getOWLDisjointObjectPropertiesAxiom(opa(), opb(), opc());
        assertTrue(reasoner.isEntailed(axiom));
    }

    public void testGetDisjointObjectProperties() {
        GetDisjointObjectProperties query = new GetDisjointObjectProperties(getKBIRI(), opb());
        ObjectPropertySynsets response = reasoner.answer(query);
        assertEquals(response.getNodes().toString(),3,response.nodes().count());
    }

    public void testGetDisjointObjectPropertiesViaOWLReasoner() {
        NodeSet<OWLObjectPropertyExpression> response = reasoner.getDisjointObjectProperties(opb());
        assertEquals(response.getNodes().toString(),3,response.nodes().count());
    }
}
