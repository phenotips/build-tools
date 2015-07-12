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

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

/**
 * Base class for writing custom block taglets.
 *
 * @version $Id$
 */
public abstract class AbstractBlockTaglet implements Taglet
{
    public boolean inConstructor()
    {
        return true;
    }

    public boolean inField()
    {
        return true;
    }

    public boolean inMethod()
    {
        return true;
    }

    public boolean inOverview()
    {
        return true;
    }

    public boolean inPackage()
    {
        return true;
    }

    public boolean inType()
    {
        return true;
    }

    public boolean isInlineTag()
    {
        return false;
    }

    public String toString(Tag tag)
    {
        if (tag == null) {
            return null;
        }
        return getHeader() + getStartTag() + escapeXML(tag.text()) + getEndTag();
    }

    public String toString(Tag[] tags)
    {
        if (tags == null || tags.length == 0) {
            return null;
        }
        StringBuilder output = new StringBuilder();
        output.append(getHeader());
        for (Tag tag : tags) {
            output.append(getStartTag()).append(escapeXML(tag.text())).append(getEndTag());
        }
        return output.toString();
    }

    /**
     * The header that is to be displayed above the list of tags, as an HTML formatted string. The format should be
     * {@code <dt>Header name:</dt>}
     *
     * @return an HTML-formatted string
     */
    protected abstract String getHeader();

    /**
     * The HTML tag to prefix each taglet instance in the generated HTML javadoc.
     *
     * @return an HTML tag, by default {@code <dd>}, but subclasses can add their own style or nested tags
     */
    protected String getStartTag()
    {
        return "<dd>";
    }

    /**
     * The HTML tag to suffix each taglet instance in the generated HTML javadoc.
     *
     * @return an HTML tag, by default {@code </dd>}, but subclasses can add their own nested tags
     */
    protected String getEndTag()
    {
        return "</dd>";
    }

    /**
     * Escapes the XML special characters in a <code>String</code> using numerical XML entities, so that the resulting
     * string can safely be used as an XML text node. Specifically, escapes &lt;, &gt;, and &amp;.
     *
     * @param str the text to escape, may be {@code null}
     * @return a new escaped {@code String}, {@code null} if {@code null} input
     */
    protected String escapeXML(String str)
    {
        if (str == null) {
            return null;
        }
        StringBuilder result = new StringBuilder((int) (str.length() * 1.1));
        int length = str.length();
        char c;
        for (int i = 0; i < length; ++i) {
            c = str.charAt(i);
            switch (c) {
                case '&':
                    result.append("&#38;");
                    break;
                case '<':
                    result.append("&#60;");
                    break;
                case '>':
                    result.append("&#62;");
                    break;
                default:
                    result.append(c);
            }
        }
        return result.toString();
    }
}
