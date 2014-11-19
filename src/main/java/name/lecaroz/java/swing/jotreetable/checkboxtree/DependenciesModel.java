package name.lecaroz.java.swing.jotreetable.checkboxtree;


/**
 * TreeNodeObject is the model used by a PropagatePreservingCheckDependenciesTreeCheckingMode. 
 * It allows developers to declare dependencies between nodes for allowing cascading/propagating 
 * checking & unchecking on a selected or an unselected node.
 *
 * @version %I% %G%
 *
 * @author Louis Lecaroz 
 */
public interface DependenciesModel<E>
{
  DependenciesModel<E> addDependency(E entry,E dependency);
  boolean isDependency(E node, E dependency);
  E[] parents(E node);
  E[] dependencies(E node);
}
