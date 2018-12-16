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

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import java.util.Collection;

/**
 * Author: Olaf Noppens
 * Date: 24.11.2009
 */
public class SetOfObjectPropertiesImpl extends SetOfImpl<OWLObjectPropertyExpression> implements SetOfObjectProperties {

    /**
     * @param owlObjectProperty owlObjectProperty 
     */
    public SetOfObjectPropertiesImpl(OWLObjectPropertyExpression owlObjectProperty) {
        super(owlObjectProperty);
    }

    /**
     * @param owlObjectProperty owlObjectProperty 
     * @param warning warning 
     */
    public SetOfObjectPropertiesImpl(OWLObjectPropertyExpression owlObjectProperty, String warning) {
        super(owlObjectProperty, warning);
    }

    /**
     * @param elements elements 
     */
    public SetOfObjectPropertiesImpl(Collection<OWLObjectPropertyExpression> elements) {
        super(elements);
    }

    /**
     * @param elements elements 
     * @param warning warning 
     */
    public SetOfObjectPropertiesImpl(Collection<OWLObjectPropertyExpression> elements, String warning) {
        super(elements, warning);
    }

    @Override
    public <O> O accept(ResponseVisitor<O> visitor) {
        return visitor.visit(this);
    }
}
