/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.phenotips.tools.javadoc;

import java.util.Map;

import com.sun.tools.doclets.Taglet;

/**
 * Provides support for adding {@code @todo} comments in the Javadoc.
 *
 * @version $Id$
 */
public class TodoTaglet extends AbstractBlockTaglet
{
    /**
     * Register this Taglet. This method is required by the javadoc tool.
     *
     * @param tagletMap the map to register this tag to
     */
    public static void register(Map<String, Taglet> tagletMap)
    {
        TodoTaglet tag = new TodoTaglet();
        tagletMap.put(tag.getName(), tag);
    }

    public String getName()
    {
        return "todo";
    }

    @Override
    protected String getHeader()
    {
        return "<dt>To do:</dt>";
    }
}
