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

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 24.10.2009
 */
public class SettingsImpl extends KBResponseImpl implements Settings {
    final Set<Setting> settings;

    public SettingsImpl(Set<Setting> settings) {
        if (settings == null || settings.isEmpty())
            this.settings = Collections.emptySet();
        else
            this.settings = Collections.unmodifiableSet(new HashSet<>(settings));

    }

    @Override
    public Set<Setting> getSettings() {
        return this.settings;
    }

    @Override
    public <O> O accept(ResponseVisitor<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Iterator<Setting> iterator() {
        return this.settings.iterator();
    }
}
