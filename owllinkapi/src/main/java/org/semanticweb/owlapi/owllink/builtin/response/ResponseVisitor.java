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

package org.semanticweb.owlapi.owllink.builtin.response;

import org.semanticweb.owlapi.owllink.Response;

/**
 * Author: Olaf Noppens
 * Date: 24.11.2009
 * @param <O> object type
 */
public interface ResponseVisitor<O> {

    /** @param response response to visit
     * @return value */
    O visit(Response response);

    /** @param response response to visit
     * @return value */
    O visit(KBResponse response);

    /** @param response response to visit
     * @return value */
    O visit(BooleanResponse response);

    /** @param response response to visit
     * @return value */
    O visit(Classes response);

    /** @param response response to visit
     * @return value */
    O visit(ClassHierarchy response);

    /** @param response response to visit
     * @return value */
    O visit(ClassSynsets response);

    /** @param response response to visit
     * @return value */
    O visit(DataPropertyHierarchy response);

    /** @param response response to visit
     * @return value */
    O visit(DataPropertySynsets response);

    /** @param response response to visit
     * @return value */
    O visit(DataPropertySynonyms response);

    /** @param response response to visit
     * @return value */
    O visit(Description response);

    /** @param response response to visit
     * @return value */
    O visit(IndividualSynonyms response);

    /** @param response response to visit
     * @return value */
    O visit(KB response);

    /** @param response response to visit
     * @return value */
    O visit(ObjectPropertyHierarchy response);

    /** @param response response to visit
     * @return value */
    O visit(ObjectPropertySynsets response);

    /** @param response response to visit
     * @return value */
    O visit(OK response);

    /** @param response response to visit
     * @return value */
    O visit(Prefixes response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfAnnotationProperties response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfClasses response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfClassSynsets response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfDataProperties response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfDataPropertySynsets response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfDatatypes response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfIndividuals response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfIndividualSynsets response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfLiterals response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfObjectProperties response);

    /** @param response response to visit
     * @return value */
    O visit(SetOfObjectPropertySynsets response);

    /** @param response response to visit
     * @return value */
    O visit(Settings response);

    /** @param response response to visit
     * @return value */
    O visit(StringResponse response);
}
