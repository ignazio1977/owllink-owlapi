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
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDisjointDataProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.DataPropertySynsets;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkDisjointDataPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpC()));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpA(), dpD()));
        return axioms;
    }

    public void testAreDataPropertiesDisjoint() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB()));
        BooleanResponse answer = super.reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpC()));
        answer = super.reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpE()));
        answer = super.reasoner.answer(query);
        falseResponse(answer);
    }

    public void testAreDataPropertiesDisjointViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpC());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLDisjointDataPropertiesAxiom(dpA(), dpB(), dpE());
        assertFalse(super.reasoner.isEntailed(axiom));
    }

    public void testGetDisjointDataProperties() {
        GetDisjointDataProperties query = new GetDisjointDataProperties(getKBIRI(), dpB());
        DataPropertySynsets response = super.reasoner.answer(query);
        assertEquals(response.getNodes().toString(), 3,response.nodes().count());
    }

    public void testGetDisjointDataPropertiesViaOWLReasoner() {
        NodeSet<OWLDataProperty> response = super.reasoner.getDisjointDataProperties(dpB());
        assertEquals(response.getNodes().toString(),3,response.nodes().count());
    }
}
