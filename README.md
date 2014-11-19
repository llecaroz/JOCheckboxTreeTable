JOCheckboxTreeTable
===================

Java Opened source version of a TreeTable with Checkboxes in the tree part

This version is a fork from the eu.floraresearch.lablib.gui.checkboxtree (lablib-checkboxtree artifactId) Checkboxtree version 4.0-beta-1: http://essi-lab.eu/nexus/content/repositories/open.repos/eu/floraresearch/lablib-checkboxtree/4.0-beta-1/

The Checkboxtree class is used in the Sun JTreeTable modified class for enabling Tree tables with checkboxes in the tree part. 

A full example can be found under src/main/java/name/lecaroz/java/swing/sun/examples/JOCheckboxTreeTableExample.java
This example implements:
- TreeNodeObject for custom rendering on each node
- TreeTableModel for providing data to be displayed
- DependenciesModel for declaring dependencies between nodes in PropagatePreservingCheckDependenciesTreeCheckingMode 

Bug corrections
===============
- Painting problems when clicking on the expanding/collapsing column on the Checkboxtree

New features
============
- The TreeTableModel methods now receives some TreeNodeObject as parameters and the get. As Consequence, a node implementing the TreeNodeObject can:
	- render its own cell in the tree (custom icon for example)
	- be considered as normal cell instead of of a checkbox
- The TreeTableModel.getTooltipAt() method allows to return a tooltip for each cell
- PropagatePreservingCheckDependenciesTreeCheckingMode new checking mode allowing to propagate checking and un-checking requests based on nodes dependencies (a DependenciesModel model must be implemented)

Code authors
============
Most part of codes was initially written by:
- Enrico Boldrini
- Lorenzo Bigagli

Warning:
AbstractCellEditor.java, AbstractTreeTableModel.java, JTreeTable.java (renamed and modified here into JOCheckboxTreeTable), MergeSort.java, TreeTableModel.java, TreeTableModelAdapter.java stored in the name.lecaroz.java.swing.sun package, are under Sun Microsystems Copyright (1997, 1998) and were modified for supporting new features like contextual tooltips. They were initially written by: 
- Philip Milne
- Scott Violet

LICENSE
=======
See the LICENSE file for more details. Because the eu.floraresearch.lablib.gui.checkboxtree original project was initially under a GNU GENERAL PUBLIC LICENSE Version 2, it inherits of this license