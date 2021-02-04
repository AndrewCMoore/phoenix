package Calculator;

import tree.JDTree;
import Calculator.*;

/**
* The Calculator class is responsible for creating
* each necessary metrics object as well as calling 
* the appropriate methods for the operation of the 
* calculator module.
*
* @author  Paul Hewson, Anthony Maevski-Popov
*/
public class Calculator {
	
	private JDTree tree;
	private JDTree[] classes;
	
	/**
	* The constructor takes the JDT tree, gets the 
	* leaf nodes of the tree which contains the class objects
	* and saves both as private attributes to be used in the 
	* metrics calculations
	*/
	public Calculator(JDTree tree) {
		this.tree = tree;
		this.classes = tree.getLeefs();
	}

	/**
	* The calculate function creates the necessary objects and 
	* calls the appropriate methods for the calculator module
	*/
	public void calculate() {
		//AttributeMetrics attributes = new AttributeMetrics(classes);
		//MethodMetrics methods = new MethodMetrics(classes);
		ComplexityMetrics statements = new ComplexityMetrics(classes);
		//ClasseMetrics cm = new ClasseMetrics(classes);
		//InheritanceMetrics im = new InheritanceMetrics(classes);
		
		//PulledValues pv = new PulledValues(classes);
		//TertiaryMetrics tm = new TertiaryMetrics(pv);
		//SecondaryMetrics sm = new SecondaryMetrics(tm);
		//PrimaryMetrics pm = new PrimaryMetrics(sm);
	}
	
}
