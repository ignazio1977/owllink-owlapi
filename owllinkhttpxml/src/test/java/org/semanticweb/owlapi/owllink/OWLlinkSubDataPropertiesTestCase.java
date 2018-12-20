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
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubDataProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubDataPropertyHierarchy;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSuperDataProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNode;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkSubDataPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();

        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpA(), dpB()));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpB(), dpC()));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpD(), dpC()));

        return axioms;
    }

    public void testSubsumedBy() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubDataPropertyOfAxiom(dpA(), dpB()));
        BooleanResponse response = super.reasoner.answer(query);
        trueResponse(response);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubDataPropertyOfAxiom(dpA(), dpC()));
        response = super.reasoner.answer(query);
        trueResponse(response);

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubDataPropertyOfAxiom(dpD(), dpB()));
        response = super.reasoner.answer(query);
        falseResponse(response);
    }

    public void testSubsumedByViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLSubDataPropertyOfAxiom(dpA(), dpB());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubDataPropertyOfAxiom(dpA(), dpC());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubDataPropertyOfAxiom(dpD(), dpB());
        assertFalse(super.reasoner.isEntailed(axiom));
    }

    public void testGetSubProperties() {
        GetSubDataProperties query = new GetSubDataProperties(getKBIRI(), dpB());
        SetOfDataPropertySynsets response = super.reasoner.answer(query);

        assertEquals(2,response.nodes().count());

        assertEquals(set(dpA(), manager.getOWLDataFactory().getOWLBottomDataProperty()), asUnorderedSet(response.entities()));
    }

    public void testGetSubPropertiesViaOWLReasoner() {
        NodeSet<OWLDataProperty> response = super.reasoner.getSubDataProperties(dpB(), false);

        assertEquals(2,response.nodes().count());
        assertEquals(set(dpA(), manager.getOWLDataFactory().getOWLBottomDataProperty()), asUnorderedSet(response.entities()));
    }

    public void testGetDirectSubProperties() {
        GetSubDataProperties query = new GetSubDataProperties(getKBIRI(), dpB(), true);
        SetOfDataPropertySynsets response = super.reasoner.answer(query);
        assertEquals(1,response.nodes().count());
        assertEquals(set(dpA()), asUnorderedSet(response.entities()));
    }

    public void testGetDirectSubPropertiesViaOWLReasoner() {
        NodeSet<OWLDataProperty> response = super.reasoner.getSubDataProperties(dpB(), true);
        assertEquals(1,response.nodes().count());
        assertEquals(set(dpA()), asUnorderedSet(response.entities()));
    }

    public void testGetSuperProperties() {
        GetSuperDataProperties query = new GetSuperDataProperties(getKBIRI(), dpA());
        SetOfDataPropertySynsets response = super.reasoner.answer(query);
        assertEquals(3,response.nodes().count());

        query = new GetSuperDataProperties(getKBIRI(), dpA(), true);
        response = super.reasoner.answer(query);
        assertEquals(1,response.nodes().count());
    }

    public void testGetSuperPropertiesViaOWLReasoner() {
        NodeSet<OWLDataProperty> response = super.reasoner.getSuperDataProperties(dpA(), false);
        assertEquals(3,response.nodes().count());

        response = super.reasoner.getSuperDataProperties(dpA(), true);
        assertEquals(1,response.nodes().count());
    }

    public void testSubPropertyHierarchy() {
        GetSubDataPropertyHierarchy query = new GetSubDataPropertyHierarchy(getKBIRI());
        DataPropertyHierarchy response = super.reasoner.answer(query);
        Set<HierarchyPair<OWLDataProperty>> pairs = response.getPairs();
        assertFalse(pairs.isEmpty());
        assertEquals(3, pairs.size());

        Set<HierarchyPair<OWLDataProperty>> expectedSet = CollectionFactory.createSet();
        Node<OWLDataProperty> synset = new OWLDataPropertyNode(getDataFactory().getOWLTopDataProperty());
        Set<Node<OWLDataProperty>> set = CollectionFactory.createSet();
        set.add(new OWLDataPropertyNode(dpC()));
        SubEntitySynsets<OWLDataProperty> setOfSynsets = new SubDataPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLDataPropertyNode(dpC());
        set = CollectionFactory.createSet();
        set.add(new OWLDataPropertyNode(dpD()));
        set.add(new OWLDataPropertyNode(dpB()));

        setOfSynsets = new SubDataPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLDataPropertyNode(dpB());
        set = CollectionFactory.createSet();
        set.add(new OWLDataPropertyNode(dpA()));
        setOfSynsets = new SubDataPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        assertEquals(expectedSet, pairs);
    }
}
