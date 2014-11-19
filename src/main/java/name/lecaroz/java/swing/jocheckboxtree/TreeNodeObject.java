package name.lecaroz.java.swing.jocheckboxtree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * TreeNodeObject is the model used by a TreeTableModel. 
 * It allows developers to declare manage nodes cells custome
 * rendering and to declare un selectable nodes in the tree 
 *
 * @version %I% %G%
 *
 * @author Louis Lecaroz 
 */
public interface TreeNodeObject<E>
{
  E getObject();
  boolean canBeChecked();
  Component getTreeCellRendererComponent(JTree tree, TreeCellRenderer treeCellRenderer, DefaultTreeCellRenderer label, boolean selected, boolean expanded,
        boolean leaf, int row,
        boolean hasFocus);
}
