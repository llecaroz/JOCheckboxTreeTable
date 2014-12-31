/*
 * Copyright 2014-2015 Louis Lecaroz, This file is part of
 * JOCheckboxTreeTable. JOCheckboxTreeTable is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version. CheckboxTree is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * General Public License along with CheckboxTree; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA
 */
package name.lecaroz.java.swing.jocheckboxtree;

import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 * ExtendedTreeTableModel inherits from TreeTableModel written by Philip Milne & Scott Violet the model used by a JTreeTable.  *
 * @version %I% %G%
 *
 * @author Louis Lecaroz 
 */
public interface ExtendedTreeTableModel extends TreeTableModel
{
    /**
     * Returns the tooltip to be displayed for node <code>node</code>, 
     * at column number <code>column</code>.
     */
    public String getTooltipAt(Object treeNodeObject, int column);

    /**
     * Returns the object stored in the TreeNodeObject
     */
    Object getObject(TreeNodeObject node);
}
