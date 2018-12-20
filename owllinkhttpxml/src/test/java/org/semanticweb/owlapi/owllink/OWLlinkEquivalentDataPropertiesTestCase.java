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
import org.semanticweb.owlapi.owllink.builtin.requests.GetEquivalentDataProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.DataPropertySynonyms;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkEquivalentDataPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpA(), dpB()));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpB(), dpC()));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpC(), dpA()));
        axioms.add(getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpD(), dpE()));
        return axioms;
    }

    public void testAreDataPropertiesEquivalent() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpA(), dpB()));
        BooleanResponse answer = super.reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpA(), dpB(), dpC()));
        answer = super.reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpD(), dpE(), dpA()));
        answer = super.reasoner.answer(query);
        falseResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpD(), dpE()));
        answer = super.reasoner.answer(query);
        trueResponse(answer);
    }

    public void testAreDataPropertiesEquivalentViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpA(), dpB());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpA(), dpB(), dpC());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpD(), dpE(), dpA());
        assertFalse(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpD(), dpE());
        assertTrue(super.reasoner.isEntailed(axiom));
    }

    public void testGetEquivalentDataProperties() {
        GetEquivalentDataProperties query = new GetEquivalentDataProperties(getKBIRI(), dpA());
        DataPropertySynonyms result = super.reasoner.answer(query);
        assertEquals(set(dpA(),dpB(),dpC()),asUnorderedSet(result.entities()));
    }

    public void testGetEquivalentDataPropertiesViaOWLReasoner() {
        Node<OWLDataProperty> result = super.reasoner.getEquivalentDataProperties(dpA());
        assertEquals(set(dpA(),dpB(),dpC()),asUnorderedSet(result.entities()));
    }
}
