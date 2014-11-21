JOCheckboxTreeTable
===================
J(ava) O(pen source) Checkbox Tree Table

Java Open source version of a TreeTable with Checkboxes in the tree part.

This version is a fork of the eu.floraresearch.lablib.gui.checkboxtree (lablib-checkboxtree artifactId) CheckboxTree version 4.0-beta-1: http://essi-lab.eu/nexus/content/repositories/open.repos/eu/floraresearch/lablib-checkboxtree/4.0-beta-1/

Original examples on the CheckboxTree from eu.floraresearch.lablib.gui.checkboxtree are stored under https://github.com/llecaroz/JOCheckboxTreeTable/tree/master/src/main/java/name/lecaroz/java/swing/jocheckboxtree/examples

The CheckboxTree class is used in the JOCheckboxTreeTable class (based on the Sun JTreeTable class) for enabling Tree tables with checkboxes in the tree part. A full example is stored here: https://github.com/llecaroz/JOCheckboxTreeTable/blob/master/src/main/java/name/lecaroz/java/swing/sun/examples/JOCheckboxTreeTableExample.java

This example implements:
- TreeNodeObject for custom rendering on each node
- TreeTableModel for providing data to be displayed
- DependenciesModel for declaring dependencies between nodes in PropagatePreservingCheckDependenciesTreeCheckingMode 

Bugs correction
===============
- Painting problems when clicking on the expanding/collapsing column on the Checkboxtree

New features
============
- The TreeTableModel methods now receives some TreeNodeObject as parameters and the get. As Consequence, a node implementing the TreeNodeObject can:
	- render its own cell in the tree (custom icon for example)
	- be considered as normal cell instead of of a checkbox
- The TreeTableModel.getTooltipAt() method allows to return a tooltip for each cell
- PropagatePreservingCheckDependenciesTreeCheckingMode new checking mode allowing to propagate checking and un-checking requests based on nodes dependencies (a DependenciesModel model must be implemented)

Using this project
==================
This project was uploaded as Maven Jar artifact under the OSSRH Nexus server. Please add the correct dependency in your POM project:
- artifactId: java.swing.jocheckboxtreetable
- groupId: name.lecaroz
- version: Check on the Central Maven repository server for the latest release.

Code authors
============
Most part of codes under src/main/java/name/lecaroz/java/swing/jocheckboxtree was initially written by:
- Enrico Boldrini
- Lorenzo Bigagli

Warning:
AbstractTreeTableModel.java, JTreeTable.java (renamed and modified here into JOCheckboxTreeTable), TreeTableModel.java, TreeTableModelAdapter.java stored in the name.lecaroz.java.swing.sun package, are under Sun Microsystems Copyright (1997, 1998) and were modified for supporting new features like contextual tooltips. They were initially written by: 
- Philip Milne
- Scott Violet

LICENSE
=======
See the LICENSE file for more details. Because the eu.floraresearch.lablib.gui.checkboxtree original project was initially under a GNU GENERAL PUBLIC LICENSE Version 2, it inherits of this license.