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

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.tree.*;
import javax.swing.table.*;

import name.lecaroz.java.swing.jotreetable.checkboxtree.PropagatePreservingCheckDependenciesTreeCheckingMode;
import name.lecaroz.java.swing.jotreetable.checkboxtree.CheckboxTree;
import name.lecaroz.java.swing.jotreetable.checkboxtree.DependenciesModel;
import name.lecaroz.java.swing.jotreetable.checkboxtree.DefaultTreeCheckingModel;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * This example shows how to create a simple JTreeTable component, by using a JTree as a renderer
 * (and editor) for the cells in a particular column in the JTable.
 *
 * @version %I% %G%
 *
 * @author Philip Milne
 * @author Scott Violet
 */

public class JTreeTable<E> extends JTable
{
  /**
   * 
   */
  private static final long serialVersionUID = 8346824860707881721L;
  /**
   * 
   */
  protected TreeTableCellRenderer tree;

  public JTreeTable<E> setTreeTableModel(TreeTableModel<E> treeTableModel, DependenciesModel<E> dependencies) {

    // Create the tree. It will be used as a renderer and editor. 
    tree = new TreeTableCellRenderer(treeTableModel);
    tree.setRootVisible(true);
    //tree.getCheckingModel().setCheckingMode(TreeCheckingModel.CheckingMode.PROPAGATE_PRESERVING_CHECK);
    tree.getCheckingModel().setCheckingMode(new PropagatePreservingCheckDependenciesTreeCheckingMode<E>((DefaultTreeCheckingModel)tree.getCheckingModel(),dependencies));

    // Install a tableModel representing the visible rows in the tree. 
    super.setModel(new TreeTableModelAdapter<E>(treeTableModel, tree));

    // Force the JTable and JTree to share their row selection models. 
    tree.setSelectionModel(new DefaultTreeSelectionModel() {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      // Extend the implementation of the constructor, as if: 
      /* public this() */{
        setSelectionModel(listSelectionModel);
      }
    });
    // Make the tree and table row heights the same. 
    tree.setRowHeight(getRowHeight());

    // Install the tree editor renderer and editor. 
    setDefaultRenderer(TreeTableModel.class, tree);
    setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());

    setShowGrid(false);
    setIntercellSpacing(new Dimension(0, 0));

    return this;
    
  }
  public JTreeTable(TreeTableModel<E> treeTableModel,DependenciesModel<E> dependencies)
  {
    super();
    this.setTreeTableModel(treeTableModel, dependencies);
  }

  /*
   * Workaround for BasicTableUI anomaly. Make sure the UI never tries to paint the editor. The UI
   * currently uses different techniques to paint the renderers and editors and overriding
   * setBounds() below is not the right thing to do for an editor. Returning -1 for the editing row
   * in this case, ensures the editor is never painted.
   */
  @Override
  public int getEditingRow()
  {
    return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1 : editingRow;
  }
  
  /**
   * Overridden to pass the new rowHeight to the tree.
   */
  @Override
  public void setRowHeight(int rowHeight) {
    super.setRowHeight(rowHeight);
    if(getTree() != null && getTree().getRowHeight() != rowHeight) {
        getTree().setRowHeight(getRowHeight());
    }
  }
  
  /**
   * Returns the tree that is being used as the first column renderer.
   * Implementors can over-ride this to return their own type of JTree which must
   * implement TableCellRenderer.
   * The Default is a DweezilTableTree.
   */
  public JTree getTree() {
      return tree;
  }

  /**
   * Added by PB
   * This stops deselection
   * See http://forum.java.sun.com/thread.jsp?forum=57&thread=223661
   * May have to do this:
   * "After a bit more investigation, overriding the following JTable
   * method in JTreeTable, resolves the painting issues that my last post refers to."
   */
  @Override
  public void tableChanged(TableModelEvent e) {
    revalidate();
    repaint();                 // Added 2004-11-22 because of repaint problems in getScrollableTracksViewportHeight()
    if(e!=null) super.tableChanged(e);
  }

  @Override
  public String getToolTipText(MouseEvent e) {
    Point p = e.getPoint();
    
    return (String) ((TreeTableModelAdapter<?>)super.getModel()).getTooltipAt(rowAtPoint(p), columnAtPoint(p));
    
  }
  
  // 
  // The renderer used to display the tree nodes, a JTree.  
  //

  public class TreeTableCellRenderer extends CheckboxTree implements TableCellRenderer
  {

    /**
     * 
     */
    private static final long serialVersionUID = -5522138959999856302L;
    protected int visibleRow;

    public TreeTableCellRenderer(TreeModel model)
    {
      super(model);
    }

    /**
     * updateUI is overridden to set the colors of the Tree's renderer
     * to match that of the table.
     */
    @Override
    public void updateUI() {
      super.updateUI();
      // Make the tree's cell renderer use the table's cell selection colors.
      TreeCellRenderer tcr = getCellRenderer();
      if(tcr instanceof DefaultTreeCellRenderer) {
        DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
        // For 1.1 uncomment this, 1.2 has a bug that will cause an
        // exception to be thrown if the border selection color is
        // null.
        // dtcr.setBorderSelectionColor(null);
        dtcr.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
        dtcr.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
      }
    }

    /**
     * Sets the row height of the tree, and forwards the row height to
     * the table.
     */
    @Override
    public void setRowHeight(int rowHeight) {
      if(rowHeight > 0) {
        super.setRowHeight(rowHeight);
        if(JTreeTable.this != null && JTreeTable.this.getRowHeight() != rowHeight) {
          JTreeTable.this.setRowHeight(getRowHeight());
        }
      }
    }
    
    
    @Override
    public void setBounds(int x, int y, int w, int h)
    {
      super.setBounds(x, 0, w, JTreeTable.this.getHeight());
    }

    @Override
    public void paint(Graphics g)
    {
      g.translate(0, -visibleRow * getRowHeight());
      super.paint(g);
    }

    public Component getTableCellRendererComponent(JTable table,
          Object value,
          boolean isSelected,
          boolean hasFocus,
          int row, int column)
    {
      if (isSelected)
        setBackground(table.getSelectionBackground());
      else
        setBackground(table.getBackground());

      visibleRow = row;
      return this;
    }
  }

  // 
  // The editor used to interact with tree nodes, a JTree.  
  //

  public class TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor
  {
    public Component getTableCellEditorComponent(JTable table, Object value,
          boolean isSelected, int r, int c)
    {
      return getTree();
    }
    /**
     * Overridden to return false, and if the event is a mouse event
     * it is forwarded to the tree.<p>
     * The behavior for this is debatable, and should really be offered
     * as a property. By returning false, all keyboard actions are
     * implemented in terms of the table. By returning true, the
     * tree would get a chance to do something with the keyboard
     * events. For the most part this is ok. But for certain keys,
     * such as left/right, the tree will expand/collapse where as
     * the table focus should really move to a different column. Page
     * up/down should also be implemented in terms of the table.
     * By returning false this also has the added benefit that clicking
     * outside of the bounds of the tree node, but still in the tree
     * column will select the row, whereas if this returned true
     * that wouldn't be the case.
     * <p>By returning false we are also enforcing the policy that
     * the tree will never be editable (at least by a key sequence).
     */

    public boolean isCellEditable(EventObject e) {
      if(e instanceof MouseEvent) {
        if(tree.getRowForLocation(((MouseEvent)e).getX(),((MouseEvent)e).getY())==-1) {
          for(int counter = getColumnCount() - 1; counter >= 0;
          counter--) {
            if(getColumnClass(counter) == TreeTableModel.class) {                                                                 
              // Bug fix for MacOs X. 09-03-2004
              // The triangular arrows on JTreeTables in the MacOS L&F did not
              // expand or colapse the branches.  This is an update to address that.
              // See http://lists.apple.com/archives/java-dev/2002/Feb/15/jtreetablev12workaroundf.txt
              MouseEvent me = (MouseEvent)e;
              MouseEvent newME = new MouseEvent(getTree(), me.getID(),
                  me.getWhen(), me.getModifiers(),
                  me.getX() - getCellRect(0, counter, true).x,
                  me.getY(), me.getClickCount(),
                  me.isPopupTrigger());
              getTree().dispatchEvent(newME);
              newME = new MouseEvent(getTree(), MouseEvent.MOUSE_RELEASED,
                  me.getWhen(), me.getModifiers(),
                  me.getX() - getCellRect(0, counter, true).x,
                  me.getY(), me.getClickCount(),
                  me.isPopupTrigger());
              getTree().dispatchEvent(newME);
              
              break;
            }
          }
        }
      }
      return tree.getRowForLocation(((MouseEvent)e).getX(),((MouseEvent)e).getY())==-1?false:true;
    }
  }
}
