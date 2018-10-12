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
import org.semanticweb.owlapi.owllink.builtin.requests.GetEquivalentClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfClasses;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
public class OWLlinkEquivalentClassesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(a(), b()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(b(), c()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(c(), a()));
        return axioms;
    }

    public void testAreClassesEquivalent() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentClassesAxiom(a(), b()));
        BooleanResponse result = super.reasoner.answer(query);
        assertTrue(result.getResult());

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLEquivalentClassesAxiom(a(), b(), c()));
        result = super.reasoner.answer(query);
        assertTrue(result.getResult());
    }

    public void testAreClassesEquivalentViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLEquivalentClassesAxiom(a(), b());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLEquivalentClassesAxiom(a(), b(), c());
        assertTrue(super.reasoner.isEntailed(axiom));
    }

    public void testGetEquivalentClasses() {
        GetEquivalentClasses query = new GetEquivalentClasses(getKBIRI(), a());
        SetOfClasses result = super.reasoner.answer(query);
        assertEquals(set(a(),b(),c()),result);
    }

    public void testGetEquivalentClassesViaOWLReasoner() {
        Node<OWLClass> result = super.reasoner.getEquivalentClasses(a());
        assertEquals(set(a(),b(),c()),result.getEntities());
    }
}
