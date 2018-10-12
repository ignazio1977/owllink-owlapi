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
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSubObjectPropertyHierarchy;
import org.semanticweb.owlapi.owllink.builtin.requests.GetSuperObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.owllink.builtin.response.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNode;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
public class OWLlinkSubObjectPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();

        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opb()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opb(), opc()));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opd(), opc()));

        return axioms;
    }

    public void testSubsumedBy() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opb()));
        BooleanResponse response = super.reasoner.answer(query);
        assertTrue(response.getResult());

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opc()));
        response = super.reasoner.answer(query);
        assertTrue(response.getResult());

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLSubObjectPropertyOfAxiom(opd(), opb()));
        response = super.reasoner.answer(query);
        assertFalse(response.getResult());
    }

    public void testSubsumedByViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opb());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubObjectPropertyOfAxiom(opa(), opc());
        assertTrue(super.reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLSubObjectPropertyOfAxiom(opd(), opb());
        assertFalse(super.reasoner.isEntailed(axiom));
    }


    public void testGetSubObjectProperties() {
        //indirect case
        GetSubObjectProperties query = new GetSubObjectProperties(getKBIRI(), opb());
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);

        assertEquals(2,response.nodes().count());
        Node<OWLObjectPropertyExpression> synset = response.iterator().next();
        assertEquals(set(opa()), synset.getEntities());
        assertEquals(set(opa(), manager.getOWLDataFactory().getOWLBottomObjectProperty()), response.getFlattened());
    }

    public void testGetSubObjectPropertiesViaOWLReasoner() {
        //indirect case
        NodeSet<OWLObjectPropertyExpression> response = super.reasoner.getSubObjectProperties(opb(), false);

        assertEquals(2,response.nodes().count());
        Node<OWLObjectPropertyExpression> synset = response.iterator().next();
        assertEquals(set(opa()), synset.getEntities());
        assertEquals(set(opa(), manager.getOWLDataFactory().getOWLBottomObjectProperty()), response.getFlattened());
    }

    public void testGetDirectSubObjectProperties() {
        //direct case
        GetSubObjectProperties query = new GetSubObjectProperties(getKBIRI(), opb(), true);
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertEquals(1,response.nodes().count());
        assertEquals(set(opa()), response.getFlattened());
    }

    public void testGetDirectSubObjectPropertiesViaOWLReasoner() {
        //direct case
        NodeSet<OWLObjectPropertyExpression> response = super.reasoner.getSubObjectProperties(opb(), true);
        assertEquals(1,response.nodes().count());
        assertEquals(set(opa()), response.getFlattened());
    }


    public void testGetSuperProperties() {
        GetSuperObjectProperties query = new GetSuperObjectProperties(getKBIRI(), opa());
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertEquals(3,response.nodes().count());
        assertEquals(set(new OWLObjectPropertyNode(opb()), new OWLObjectPropertyNode(opc()), new OWLObjectPropertyNode(manager.getOWLDataFactory().getOWLTopObjectProperty())),response.getNodes());
    }

    public void testGetSuperPropertiesViaOWLReasoner() {
        NodeSet<OWLObjectPropertyExpression> response = super.reasoner.getSuperObjectProperties(opa(), false);
        assertEquals(set(new OWLObjectPropertyNode(opb()), new OWLObjectPropertyNode(opc()), new OWLObjectPropertyNode(manager.getOWLDataFactory().getOWLTopObjectProperty())),response.getNodes());
    }

    public void testGetSuperPropertiesDirect() {
        GetSuperObjectProperties query = new GetSuperObjectProperties(getKBIRI(), opa(), true);
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertEquals(1,response.nodes().count());
    }

    public void testGetSuperPropertiesDirectViaOWLReasoner() {
        NodeSet<OWLObjectPropertyExpression> response = super.reasoner.getSuperObjectProperties(opa(), true);
        assertEquals(1,response.nodes().count());
    }

    public void testSubPropertyHierarchy() {
        GetSubObjectPropertyHierarchy query = new GetSubObjectPropertyHierarchy(getKBIRI());
        Hierarchy<OWLObjectPropertyExpression> response = super.reasoner.answer(query);
        Set<HierarchyPair<OWLObjectPropertyExpression>> pairs = response.getPairs();
        assertFalse(pairs.isEmpty());
        assertEquals(3, pairs.size());

        Set<HierarchyPair<OWLObjectPropertyExpression>> expectedSet = CollectionFactory.createSet();
        OWLObjectPropertyNode synset = new OWLObjectPropertyNode(getDataFactory().getOWLTopObjectProperty());
        Set<Node<OWLObjectPropertyExpression>> set = CollectionFactory.createSet();
        set.add(new OWLObjectPropertyNode(opc()));
        SubEntitySynsets<OWLObjectPropertyExpression> setOfSynsets = new SubObjectPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLObjectPropertyNode(opc());
        set = CollectionFactory.createSet();
        set.add(new OWLObjectPropertyNode(opb()));
        set.add(new OWLObjectPropertyNode(opd()));

        setOfSynsets = new SubObjectPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        synset = new OWLObjectPropertyNode(opb());
        set = CollectionFactory.createSet();
        set.add(new OWLObjectPropertyNode(opa()));
        setOfSynsets = new SubObjectPropertySynsets(set);
        expectedSet.add(new HierarchyPairImpl<>(synset, setOfSynsets));

        assertEquals(expectedSet, pairs);
    }
}
