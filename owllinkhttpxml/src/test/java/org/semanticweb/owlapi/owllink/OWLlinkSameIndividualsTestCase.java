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
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSameIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.IndividualSynonyms;
import org.semanticweb.owlapi.reasoner.Node;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 03.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkSameIndividualsTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = createSet();
        axioms.add(getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j"), getOWLIndividual("k")));
        axioms.add(getDataFactory().getOWLDifferentIndividualsAxiom(getOWLIndividual("i"), getOWLIndividual("l")));
        return axioms;
    }

    public void testAreSameIndividuals() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("k")));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j")));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j"), getOWLIndividual("k")));
        trueResponse(super.reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("l")));
        falseResponse(super.reasoner.answer(query));
    }

    public void testAreSameIndividualsViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("k"));
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j"));
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("j"), getOWLIndividual("k"));
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSameIndividualAxiom(getOWLIndividual("i"), getOWLIndividual("l"));
        assertFalse(super.reasoner.isEntailed(axiom));

    }

    public void testGetSameIndividuals() {
        GetSameIndividuals query = new GetSameIndividuals(getKBIRI(), getOWLIndividual("i"));
        IndividualSynonyms response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("i"),getOWLIndividual("j"),getOWLIndividual("k")), response.getIndividuals());
    }

    public void testGetSameIndividualsViaOWLReasoner() {
        Node<OWLNamedIndividual> nodeSet = super.reasoner.getSameIndividuals(getOWLIndividual("i"));
        assertEquals(set(getOWLIndividual("i"),getOWLIndividual("j"),getOWLIndividual("k")), asUnorderedSet(nodeSet.entities()));
    }
}
