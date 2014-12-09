package name.lecaroz.java.swing.sun;

/*
 * %W% %E%
 *
 * Copyright 1997, 1998 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

import javax.swing.table.AbstractTableModel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

import name.lecaroz.java.swing.jocheckboxtree.TreeNodeObject;

/**
 * This is a wrapper class takes a TreeTableModel and implements the table model interface. The
 * implementation is trivial, with all of the event dispatching support provided by the superclass:
 * the AbstractTableModel.
 *
 * @version %I% %G%
 *
 * @author Philip Milne
 * @author Scott Violet
 */

public class TreeTableModelAdapter<E> extends AbstractTableModel
{
  /**
   * 
   */
  private static final long serialVersionUID = -3472763471994086774L;
  JTree tree;
  TreeTableModel<E> treeTableModel;

  public TreeTableModelAdapter(TreeTableModel<E> treeTableModel, JTree tree)
  {
    this.tree = tree;
    this.treeTableModel = treeTableModel;

    tree.addTreeExpansionListener(new TreeExpansionListener() {
      // Don't use fireTableRowsInserted() here; 
      // the selection model would get  updated twice. 
      public void treeExpanded(TreeExpansionEvent event)
      {
        fireTableDataChanged();
      }

      public void treeCollapsed(TreeExpansionEvent event)
      {
        fireTableDataChanged();
      }
    });
  }

  // Wrappers, implementing TableModel interface. 

  public int getColumnCount()
  {
    return (treeTableModel!=null?treeTableModel.getColumnCount():0);
  }

  public String getColumnName(int column)
  {
    return (treeTableModel!=null?treeTableModel.getColumnName(column):"");
  }

  public Class<?> getColumnClass(int column)
  {
    return (treeTableModel!=null?treeTableModel.getColumnClass(column):null);
  }

  public int getRowCount()
  {
    return tree.getRowCount();
  }

  @SuppressWarnings("unchecked")
  protected TreeNodeObject<E> nodeForRow(int row)
  {
    TreePath treePath = tree.getPathForRow(row);
    return (TreeNodeObject<E>) treePath.getLastPathComponent();
  }

  public Object getValueAt(int row, int column)
  {
    return treeTableModel!=null?treeTableModel.getValueAt(nodeForRow(row), column):null;
  }

  public Object getTooltipAt(int row, int column)
  {
    return treeTableModel!=null?treeTableModel.getTooltipAt(nodeForRow(row), column):null;
  }

  public boolean isCellEditable(int row, int column)
  {
    return treeTableModel!=null?treeTableModel.isCellEditable(nodeForRow(row), column):null;
  }

  public void setValueAt(Object value, int row, int column)
  {
    if(treeTableModel!=null) treeTableModel.setValueAt(value, nodeForRow(row), column);
  }
}
