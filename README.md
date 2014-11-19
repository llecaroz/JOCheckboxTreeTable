JOTreeTable
===========

Java Opened source version of TreeTable

This version is a fork from the eu.floraresearch.lablib.gui.checkboxtree (lablib-checkboxtree artifactId) JTreeTable version 4.0-beta-1: http://essi-lab.eu/nexus/content/repositories/open.repos/eu/floraresearch/lablib-checkboxtree/4.0-beta-1/

Bug corrections
===============
- Painting problems when clicking on the expanding/collapsing column

New features
============
- The TreeTableModel methods now receives some TreeNodeObject as parameters and the get. As Consequence, a node implementing the TreeNodeObject can:
	- render its own cell in the tree (custom icon for example)
	- be considered as normal cell instead of of a checkbox
- The TreeTableModel.getTooltipAt() method allows to return a tooltip for each cell
- PropagatePreservingCheckDependenciesTreeCheckingMode new checking mode allowing to propagate checking and un-checking requests based on nodes dependencies (a DependenciesModel model must be implemented)

Code authors
============
Most part of codes were initially written by:
- Philip Milne
- Scott Violet
- Enrico Boldrini
- Lorenzo Bigagli

LICENSE
=======
See the LICENSE file for more details. Because this project was initially under a GNU GENERAL PUBLIC LICENSE Version 2, it inherits of this license