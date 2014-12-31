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
package name.lecaroz.java.swing.sun.examples;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Vector;

import name.lecaroz.java.swing.jocheckboxtree.CheckboxTree;
import name.lecaroz.java.swing.jocheckboxtree.DefaultTreeCheckingModel;
import name.lecaroz.java.swing.jocheckboxtree.DependenciesModel;
import name.lecaroz.java.swing.jocheckboxtree.ExtendedAbstractTreeTableModel;
import name.lecaroz.java.swing.jocheckboxtree.ExtendedTreeTableModel;
import name.lecaroz.java.swing.jocheckboxtree.PropagatePreservingCheckDependenciesTreeCheckingMode;
import name.lecaroz.java.swing.jocheckboxtree.TreeNodeObject;
import name.lecaroz.java.swing.sun.JOCheckboxTreeTable;

public class JOCheckboxTreeTableExample extends JFrame {
  private static final long serialVersionUID = 4421111660963742252L;

  // Node implementation for managing display for each node of data
  public class StringNode implements TreeNodeObject {
    final private String data;
    final private boolean canBeChecked;
    final private StringNode[] childs;
    final private Icon icon;


    StringNode(String data, boolean canBeChecked, StringNode[] childs) {
      this.data=data;
      this.canBeChecked=canBeChecked;
      this.childs=childs;
      
      Icon tmpIcon=null;
      try {
        final InputStream stream=this.getClass().getResourceAsStream("/images/"+data+".png");
        if(stream!=null) tmpIcon = new ImageIcon(ImageIO.read(stream));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      this.icon=tmpIcon!=null?new ImageIcon(((ImageIcon) tmpIcon).getImage().getScaledInstance(((ImageIcon)UIManager.get("Tree.openIcon")).getIconWidth(), ((ImageIcon)UIManager.get("Tree.openIcon")).getIconWidth(), java.awt.Image.SCALE_SMOOTH)):null;
    }
    
    public String getObject()
    {
      return data;
    }

    public boolean canBeChecked()
    {
      return this.canBeChecked;
    }

    public Component getTreeCellRendererComponent(JTree tree, TreeCellRenderer treeCellRenderer,
          DefaultTreeCellRenderer label, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
      label.getTreeCellRendererComponent(tree, this.getObject().toString().equals("0")?"All":this.getObject(), selected, expanded, leaf, row, hasFocus);
      label.setIcon(this.icon);
      return null;
    }

    public StringNode[] getChildren()
    {
      return childs;
    }

    public boolean isEnabled()
    {
      return true;
    }
    
  };

  // Data model for returning rows & columns data
  public class SampleDataTreeModel  extends ExtendedAbstractTreeTableModel 
  implements ExtendedTreeTableModel {    
    
    // Names of the columns.
    private final String[] cNames = { "Name", "Description" };

    // Types of the columns.
    private final Class<?>[] cTypes = { ExtendedTreeTableModel.class, String.class };
    
    public SampleDataTreeModel(Object root)
    {
      super(root);
    }

    public Object getChild(Object node, int i)
    {
      return this.getChildren(node)[i];
    }

    private StringNode[] getChildren(Object node)
    {
      return ((StringNode) node).getChildren();
    }

    public int getChildCount(Object node)
    {
      Object[] children = this.getChildren(node);
      return (children == null) ? 0 : children.length;
    }

    public int getColumnCount()
    {
      return cNames.length;
    }

    public String getColumnName(int column)
    {
      return cNames[column];
    }

    public Class<?> getColumnClass(int column)
    {
      return cTypes[column];
    }

    public Object getValueAt(Object node, int column)
    {
      Object data = this.getObject((TreeNodeObject) node);
      switch (column) {
        case 0:
          if(data.equals("0")) return "All"; else return ""+data.toString();
        case 1:
          return "Description: "+data.toString();
      }
      return null;
    }

    public String getTooltipAt(Object node, int column)
    {
      switch (column) {
        case 0:
          return "Tooltip: "+((TreeNodeObject) node).getObject().toString();
        case 1:
          return "Description tooltip: "+((TreeNodeObject) node).getObject().toString();
      }
      return null;
    }

    public void setValueAt(Object aValue, Object treeNodeObject, int column)
    {
    }

    public String getObject(TreeNodeObject node)
    {
      return (String) ((TreeNodeObject) node).getObject();
    }

    public int getHierarchicalColumn()
    {
      // TODO Auto-generated method stub
      return 0;
    }

    public int getIndexOfChild(Object arg0, Object arg1)
    {
      // TODO Auto-generated method stub
      return 0;
    }

  }
  
  public class StringDependencies implements DependenciesModel<String> {
    final AbstractMap<String,AbstractList<String>> dependenciesMap;
    final AbstractMap<String,AbstractList<String>> parentsMap;
    
    public StringDependencies() {
      this.dependenciesMap=new HashMap<String, AbstractList<String>>();
      this.parentsMap=new HashMap<String, AbstractList<String>>();
    }

    private void add(final AbstractMap<String,AbstractList<String>> map, String key, String dependency) {
      Vector<String> elements=(Vector<String>)map.get(key);
      if(elements==null) {
        elements=new Vector<String>();
        map.put(key, elements);
      }
      if(elements.contains(dependency)==false) elements.add(dependency);
      
    }

    public DependenciesModel<String> addDependency(String entry, String dependency)
    {
      this.add(this.dependenciesMap,entry,dependency);
      this.add(this.parentsMap,dependency,entry);
      return this;
    }

    public boolean isDependency(String node, String dependency)
    {
      return this.dependenciesMap.containsKey(node)?this.dependenciesMap.get(node).contains(dependency):false;
    }

    public String[] parents(String node)
    {
      final AbstractList<String> parents=this.parentsMap.get(node);
      return (parents!=null?(String[]) parents.toArray(new String[0]):null);
    }

    public String[] dependencies(String node)
    {
      final AbstractList<String> dependencies=this.dependenciesMap.get(node);
      return (dependencies!=null?(String[]) dependencies.toArray(new String[0]):null);
    }
  }
  
  // Constructor for instantiating dataset & Swing UI components
  public JOCheckboxTreeTableExample() {
    
    // dataset to be displayed in the JOCheckboxTreeTable
    StringNode root=new StringNode(
          "0", true,
            new StringNode[]{ new StringNode(
                "1", true, new StringNode[]{
                      new StringNode("1.1", false, null),
                      new StringNode("1.2", false, null) }),
            new StringNode(
                "2", true, new StringNode[]{
                      new StringNode("2.1", false, null),
                      new StringNode("2.2", false, null) }),
            new StringNode(
                "3", true, new StringNode[]{
                      new StringNode("3.1", false, null),
                      new StringNode("3.2", false, null) }),
            new StringNode(
                "4", true, new StringNode[]{
                      new StringNode("4.1", false, null),
                      new StringNode("4.2", false, null) }),
          }
    );
   // Entry "1" and "2.2" have custom icons 
   SampleDataTreeModel dataModel=new SampleDataTreeModel(root);
   final JOCheckboxTreeTable<String> checkboxTreeTable = new JOCheckboxTreeTable<String>(dataModel);

   // Dependencies..
   // 1 depends on 2 & 4
   // 3 depends on 4
   DependenciesModel<String> dependencies=(DependenciesModel<String>)new StringDependencies().addDependency("1","2").addDependency("1","4").addDependency("3","4");

   // Set the checking mode, must be done after having set data in the JOCheckboxTreeTable constructor or thru JOCheckboxTreeTable.setTreeTableModel()
   ((CheckboxTree)checkboxTreeTable.getTree()).getCheckingModel().setCheckingMode(new PropagatePreservingCheckDependenciesTreeCheckingMode<String>((DefaultTreeCheckingModel)((CheckboxTree)checkboxTreeTable.getTree()).getCheckingModel(),dependencies));

   final JScrollPane treeTablePane=new JScrollPane(checkboxTreeTable);
   getContentPane().add(treeTablePane, BorderLayout.CENTER);
   setMinimumSize(new Dimension(400, 400));
   setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * 
   */
 
  public static void main(String[] args) throws IOException
  {
    EventQueue.invokeLater(new Runnable() {
      public void run()
      {
        try {
          JOCheckboxTreeTableExample frame = new JOCheckboxTreeTableExample();
          frame.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

  }
}