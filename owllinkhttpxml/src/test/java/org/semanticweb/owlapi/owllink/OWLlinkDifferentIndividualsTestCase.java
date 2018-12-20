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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDifferentIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedDifferentIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividualSynsets;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividuals;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkDifferentIndividualsTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLDifferentIndividualsAxiom(ia(), ib(), ic()));
        axioms.add(getDataFactory().getOWLSameIndividualAxiom(ia(), id()));
        return axioms;
    }

    public void testAreIndividualsDisjoint() {
        Set<OWLIndividual> indis = CollectionFactory.createSet();
        indis.add(ia());
        indis.add(ib());
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDifferentIndividualsAxiom(indis));
        BooleanResponse answer = super.reasoner.answer(query);
        trueResponse(answer);
        indis.add(ic());

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDifferentIndividualsAxiom(indis));
        answer = super.reasoner.answer(query);
        trueResponse(answer);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(indis));
        answer = super.reasoner.answer(query);
        falseResponse(answer);
    }

    public void testAreIndividualsDisjointViaOWLReasoner() {
        Set<OWLIndividual> indis = CollectionFactory.createSet();
        indis.add(ia());
        indis.add(ib());
        OWLAxiom axiom = getDataFactory().getOWLDifferentIndividualsAxiom(indis);
        assertTrue(super.reasoner.isEntailed(axiom));

        indis.add(ic());

        axiom = getDataFactory().getOWLDifferentIndividualsAxiom(indis);
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSameIndividualAxiom(indis);
        assertFalse(super.reasoner.isEntailed(axiom));
    }

    public void testGetDisjointIndividuals() {
        GetDifferentIndividuals query = new GetDifferentIndividuals(getKBIRI(), ib());
        SetOfIndividualSynsets response = super.reasoner.answer(query);
        assertEquals(2, response.getSynsets().size());
    }

    public void testGetDisjointIndividualsWithOWLReasoner() {
        NodeSet<OWLNamedIndividual> response = super.reasoner.getDifferentIndividuals(ib());
        assertEquals(set(ia(),ic(),id()),asUnorderedSet(response.entities()));
    }

    public void testGetFlattenedDisjointIndividuals() {
        GetFlattenedDifferentIndividuals query = new GetFlattenedDifferentIndividuals(getKBIRI(), ib());
        SetOfIndividuals response = super.reasoner.answer(query);
        assertEquals(set(ia(),ic(),id()), response);
    }
}
