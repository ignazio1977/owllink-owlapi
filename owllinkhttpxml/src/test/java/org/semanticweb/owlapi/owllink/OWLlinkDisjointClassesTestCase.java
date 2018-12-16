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
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.owllink.builtin.requests.GetDisjointClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.owllink.builtin.response.ClassSynsets;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkDisjointClassesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLDisjointClassesAxiom(a(), b(), c()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(a(), d()));
        return axioms;
    }

    public void testAreClassesDisjoint() {
        Set<OWLClassExpression> classes = CollectionFactory.createSet();
        classes.add(a());
        classes.add(b());
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointClassesAxiom(classes));
        BooleanResponse answer = super.reasoner.answer(query);
        trueResponse(answer);
        classes.add(c());
        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointClassesAxiom(classes));
        answer = super.reasoner.answer(query);
        trueResponse(answer);
        classes.add(d());
        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDisjointClassesAxiom(classes));
        answer = super.reasoner.answer(query);
        falseResponse(answer);
    }

    public void testAreClassesDisjointViaOWLReasoner() {
        Set<OWLClassExpression> classes = CollectionFactory.createSet();
        classes.add(a());
        classes.add(b());
        OWLAxiom axiom = getDataFactory().getOWLDisjointClassesAxiom(classes);
        assertTrue(super.reasoner.isEntailed(axiom));

        classes.add(c());
        axiom = getDataFactory().getOWLDisjointClassesAxiom(classes);
        assertTrue(super.reasoner.isEntailed(axiom));
        classes.add(d());
        axiom = getDataFactory().getOWLDisjointClassesAxiom(classes);
        assertFalse(super.reasoner.isEntailed(axiom));
    }

    public void testGetDisjointClasses() {
        GetDisjointClasses query = new GetDisjointClasses(getKBIRI(), b());
        ClassSynsets response = super.reasoner.answer(query);
        assertEquals(3,response.getNodes().size());
    }

    public void testGetDisjointClassesViaOWLReasoner() {
        NodeSet<OWLClass> response = super.reasoner.getDisjointClasses(b());
        assertEquals(3,response.getNodes().size());
    }
}
