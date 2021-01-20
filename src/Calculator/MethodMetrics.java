package Calculator;

import java.util.ArrayList;
import java.util.HashMap;

import ssmc.Class;
import ssmc.Method;
import tree.JDTree;

/**
* The MethodMetrics class contains all of the metrics that are related 
* to methods
*
* @author  Paul Hewson
*/
public class MethodMetrics {

	/**
	* The constructor currently calls each method in the class for testing purposes
	*/
	public MethodMetrics(JDTree[] classes) {
		this.numNonFinalPrivateProtectedMethods(classes);
		this.numClassifiedMethods(classes);
		this.numPublicMethods(classes);
		this.numMethods(classes);
	}

	/**
	* Method numNonFinalPrivateProtectedMethods determines the number of 
	* public instance attributes in each class 
	* @param classes An array of JDTree nodes containing classes
	* @return HashMap<String, Integer> Returns the class name, total pair for each class
	*/
	private HashMap<String, Integer> numNonFinalPrivateProtectedMethods(JDTree[] classes) {
		System.out.println("======================================================");
		System.out.println("Non-Final Private or Protected Methods");
		System.out.println("======================================================");
		HashMap<String, Integer> calcMap = new HashMap<String, Integer>();
		int total = 0;
		int count = 0;

		for(int i = 0; i < classes.length; i++) { //iterate over classes
			Object o = classes[i].getNode();	//get current class node as an object
			if(o instanceof Class) {			//ensure that object is of type class
				Class classNode = (Class) o;	//cast object to type class
				ArrayList<Method> methodList = classNode.getMethods(); //get methods for current class
				for(Method method : methodList) {	//iterate over methods in selected class
					if((method.getModifiers().contains("private") || method.getModifiers().contains("protected")) && !method.getFinalized()) {
						if(!method.getIdentifier().equals(classNode.getIdentifier())) { //exclude constructors
							count++; //increment counter only if method is private or protected and is not finalized and not the constructor
						}
					}
				}
				System.out.println(classNode.getIdentifier() + " : " + count); 
				calcMap.put(classNode.getIdentifier(), count); //add class, count pair to hashmap
				total += count;						//add count for class to program total
				count = 0;							//reset counter for next class
			}
		}
		System.out.println("program total: " + total);
		calcMap.put("total", total);			//add program total to hashmap
		//printMap(calcMap);
		return calcMap;
	}
	
	/**
	* Method numClassifiedMethods determines the number of 
	* classified (non-public) attributes in each class 
	* @param classes An array of JDTree nodes containing classes
	* @return HashMap<String, Integer> Returns the class name, total pair for each class
	*/
	private HashMap<String, Integer> numClassifiedMethods(JDTree[] classes) {
		System.out.println("======================================================");
		System.out.println("Classified Methods");
		System.out.println("======================================================");
		HashMap<String, Integer> calcMap = new HashMap<String, Integer>();
		int total = 0;
		int count = 0;

		for(int i = 0; i < classes.length; i++) {
			Object o = classes[i].getNode();
			if(o instanceof Class) {
				Class classNode = (Class) o;
				ArrayList<Method> methodList = classNode.getMethods();
				for(Method method : methodList) {
					if(!method.getModifiers().contains("public")) {
						if(!method.getIdentifier().equals(classNode.getIdentifier())) {
							count++; 	//increment counter only if the method is not public and not a constructor
						}
					}
				}
				System.out.println(classNode.getIdentifier() + " : " + count);
				calcMap.put(classNode.getIdentifier(), count);
				total += count;
				count = 0;
			}
		}
		System.out.println("program total: " + total);
		calcMap.put("total", total);
		//printMap(calcMap);
		return calcMap;
	}
	
	/**
	* Method numPublicMethods determines the number of
	* public methods in each class 
	* @param classes An array of JDTree nodes containing classes
	* @return HashMap<String, Integer> Returns the class name, total pair for each class
	*/
	private HashMap<String, Integer> numPublicMethods(JDTree[] classes){
		System.out.println("======================================================");
		System.out.println("Public methods");
		System.out.println("======================================================");
		HashMap<String, Integer> calcMap = new HashMap<String, Integer>();
		int total = 0;
		int count = 0;

		for(int i = 0; i < classes.length; i++) {
			Object o = classes[i].getNode();
			if(o instanceof Class) {
				Class classNode = (Class) o;
				ArrayList<Method> methodList = classNode.getMethods();
				for(Method method : methodList) {
					if(method.getModifiers().contains("public")) {
						if(!method.getIdentifier().equals(classNode.getIdentifier())) {
							count++; 
						}
					}
				}
				System.out.println(classNode.getIdentifier() + " : " + count);
				calcMap.put(classNode.getIdentifier(), count);
				total += count;
				count = 0;
			}
			//ArrayList<Method> methods = (Method) classes[i]
		}
		System.out.println("program total: " + total);
		calcMap.put("total", total);
		//printMap(calcMap);
		return calcMap;
	}
	
	/**
	* Method numMethods determines the total number 
	* of methods in each class 
	* @param classes An array of JDTree nodes containing classes
	* @return HashMap<String, Integer> Returns the class name, total pair for each class
	*/
	private HashMap<String, Integer> numMethods(JDTree[] classes){
		System.out.println("======================================================");
		System.out.println("Total Methods");
		System.out.println("======================================================");
		HashMap<String, Integer> calcMap = new HashMap<String, Integer>();
		int total = 0;
		int count = 0;

		for(int i = 0; i < classes.length; i++) {
			Object o = classes[i].getNode();
			if(o instanceof Class) {
				Class classNode = (Class) o;
				ArrayList<Method> methodList = classNode.getMethods();
				for(Method method : methodList) {
					if(!method.getIdentifier().equals(classNode.getIdentifier())) {
						count++; //increment counter only if method is not a constructor
					}
				}
				System.out.println(classNode.getIdentifier() + " : " + count);
				calcMap.put(classNode.getIdentifier(), count);
				total += count;
				count = 0;
			}
			//ArrayList<Method> methods = (Method) classes[i]
		}
		System.out.println("program total: " + total);
		calcMap.put("total", total);
		//printMap(calcMap);
		return calcMap;
	}
	
	private void printMap(HashMap<String, Integer> map) {
		for(String key : map.keySet()) {
			System.out.println(key + ": " + map.get(key) + ", ");
		}
	}
}