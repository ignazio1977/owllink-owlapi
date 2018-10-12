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
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.owllink.builtin.requests.GetFlattenedTypes;
import org.semanticweb.owlapi.owllink.builtin.requests.GetInstances;
import org.semanticweb.owlapi.owllink.builtin.requests.GetTypes;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.ClassSynsets;
import org.semanticweb.owlapi.owllink.builtin.response.Classes;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividualSynsets;
import org.semanticweb.owlapi.reasoner.NodeSet;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Author: Olaf Noppens
 * Date: 03.11.2009
 */
public class OWLlinkIsInstanceOfTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = createSet();

        axioms.add(getDataFactory().getOWLClassAssertionAxiom(a(), i()));
        axioms.add(getDataFactory().getOWLClassAssertionAxiom(b(), i()));
        axioms.add(getDataFactory().getOWLClassAssertionAxiom(b(), j()));

        return axioms;
    }

    public void testIsInstanceOf() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLClassAssertionAxiom(a(), i()));
        assertTrue(super.reasoner.answer(query).getResult());

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLClassAssertionAxiom(top(), i()));
        assertTrue(super.reasoner.answer(query).getResult());

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLClassAssertionAxiom(b(), j()));
        assertTrue(super.reasoner.answer(query).getResult());

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLClassAssertionAxiom(a(), j()));
        assertFalse(super.reasoner.answer(query).getResult());
    }

    public void testIsInstanceOfViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLClassAssertionAxiom(a(), i());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLClassAssertionAxiom(top(), i());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLClassAssertionAxiom(b(), j());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLClassAssertionAxiom(a(), j());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testFlattenedTypes() {
        GetFlattenedTypes query = new GetFlattenedTypes(getKBIRI(), i());
        Classes answer = super.reasoner.answer(query);
        assertEquals(set(a(),b(),top()), answer.getEntities());

        query = new GetFlattenedTypes(getKBIRI(), i(), true);
        answer = super.reasoner.answer(query);
        assertEquals(set(a(),b()), answer.getEntities());
    }

    public void testTypes() {
        GetTypes types = new GetTypes(getKBIRI(), i(), false);
        ClassSynsets answerTypes = super.reasoner.answer(types);
        assertEquals(set(a(),b(),top()), answerTypes.getFlattened());
    }

    public void testTypesViaOWLReasoner() {
        NodeSet<OWLClass> answerTypes = super.reasoner.getTypes(i(), false);
        Set<OWLClass> expected = set(a(),b(),top());
        assertEquals(expected, answerTypes.getFlattened());
    }

    public void testGetInstances() {
        GetInstances query = new GetInstances(getKBIRI(), a());
        SetOfIndividualSynsets response = super.reasoner.answer(query);
        assertEquals(set(i()), response.getFlattened());
    }

    public void testGetInstancesViaOWLReasoner() {
        NodeSet<OWLNamedIndividual> response = super.reasoner.getInstances(a(), false);
        assertEquals(set(i()), response.getFlattened());
    }
}
