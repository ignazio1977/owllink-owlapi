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
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubClassHierarchy;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSuperClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNode;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkIsSubClassesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();

        axioms.add(getDataFactory().getOWLSubClassOfAxiom(a(), b()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(b(), c()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(d(), c()));


        return axioms;
    }

    public void testSubsumedBy() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubClassOfAxiom(a(), b()));
        BooleanResponse response = super.reasoner.answer(query);
        trueResponse(response);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubClassOfAxiom(a(), c()));
        response = super.reasoner.answer(query);
        trueResponse(response);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubClassOfAxiom(d(), b()));
        response = super.reasoner.answer(query);
        falseResponse(response);
    }

    public void testSubsumedByViaOWLReasoner() {
        OWLSubClassOfAxiom axiom = getDataFactory().getOWLSubClassOfAxiom(a(), b());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubClassOfAxiom(a(), c());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubClassOfAxiom(d(), b());
        assertFalse(super.reasoner.isEntailed(axiom));

    }

    public void testGetSubClasses() {
        GetSubClasses query = new GetSubClasses(getKBIRI(), b());
        NodeSet<OWLClass> response = super.reasoner.answer(query);
        assertEquals(2,response.nodes().count());
        Set<OWLClass> flattenedClasses = response.getFlattened();
        assertEquals(set(a(),manager.getOWLDataFactory().getOWLNothing()), flattenedClasses);
    }

    public void testGetSubClassesViaOWLReasoner() {
        NodeSet<OWLClass> nodeSet = super.reasoner.getSubClasses(b(), false);
        assertEquals(2,nodeSet.nodes().count());

        Set<OWLClass> flattenedClasses = nodeSet.getFlattened();
        assertEquals(set(a(),manager.getOWLDataFactory().getOWLNothing()), flattenedClasses);
    }


    public void testGetSuperClasses() {
        GetSuperClasses query = new GetSuperClasses(getKBIRI(), a());
        SetOfClassSynsets response = super.reasoner.answer(query);
        assertEquals(3,response.nodes().count());

        query = new GetSuperClasses(getKBIRI(), a(), true);
        response = super.reasoner.answer(query);
        assertEquals(1,response.nodes().count());
    }

    public void testGetSuperClassesViaOWLReasoner() {
        NodeSet<OWLClass> response = super.reasoner.getSuperClasses(a(), false);
        assertEquals(3,response.nodes().count());

        response = super.reasoner.getSuperClasses(a(), true);
        assertEquals(1,response.nodes().count());
    }

    public void testClassHierarchy() {
        GetSubClassHierarchy query = new GetSubClassHierarchy(getKBIRI());
        Hierarchy<OWLClass> response = super.reasoner.answer(query);
        Set<HierarchyPair<OWLClass>> pairs = response.getPairs();
        assertEquals(3,pairs.size());

        Set<HierarchyPair<OWLClass>> expectedSet = CollectionFactory.createSet();
        Node<OWLClass> synset = new OWLClassNode(getDataFactory().getOWLThing());
        Set<Node<OWLClass>> set = CollectionFactory.createSet();
        set.add(new OWLClassNode(c()));
        SubEntitySynsets<OWLClass> setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLClassNode(c());
        set = CollectionFactory.createSet();
        set.add(new OWLClassNode(b()));
        set.add(new OWLClassNode(d()));
        setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLClassNode(b());
        set = CollectionFactory.createSet();
        set.add(new OWLClassNode(a()));
        setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        assertEquals(expectedSet, pairs);
    }

    public void testSubClassHierarchy() {
        GetSubClassHierarchy query = new GetSubClassHierarchy(getKBIRI(), c());
        Hierarchy<OWLClass> response = super.reasoner.answer(query);
        Set<HierarchyPair<OWLClass>> pairs = response.getPairs();
        assertEquals(2,pairs.size());

        Set<HierarchyPair<OWLClass>> expectedSet = CollectionFactory.createSet();

        Node<OWLClass> synset = new OWLClassNode(c());
        Set<Node<OWLClass>> set = CollectionFactory.createSet();
        set.add(new OWLClassNode(d()));
        set.add(new OWLClassNode(b()));
        SubEntitySynsets<OWLClass> setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLClassNode(b());
        set = CollectionFactory.createSet();
        set.add(new OWLClassNode(a()));
        setOfSynsets = new SubClassSynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        assertEquals(expectedSet, pairs);
    }
}
