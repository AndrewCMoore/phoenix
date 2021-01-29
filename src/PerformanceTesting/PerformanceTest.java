package PerformanceTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.jupiter.api.Test;

import Calculator.Calculator;
import ssmc.CAMValues;
import tree.JDTree;

/**
 * 
 * @author AndrewCMoore
 *
 */
class PerformanceTest {

	HashMap<String, Long> timeValues;
	HashMap<String, Integer> lengthValues;
	
	/*
	 * This class is a test class that runs when the file is run as a JUnit-plugin 
	 * test in order to utilize the functionality of the headless JDT library.
	 */
	public PerformanceTest() {
		// Initialize HashMap values
		timeValues = new HashMap<String, Long>();
		lengthValues = new HashMap<String, Integer>();
		
		// Intialize variables
		float systemAverage = 0;
		int projectsRun = 0;
		IProject[] projects = null;
		IPath path = null;
		
		// Try catch to remove errors in projects
		try {
			//Gets the root directory of the workspace
			IWorkspaceRoot fileRoot = ResourcesPlugin.getWorkspace().getRoot();
			// Get the array of projects within the workspace
			projects = fileRoot.getProjects();
			
			// For each project in the workspace
			for(IProject project : projects) {
				
				// Get the number of lines of code in the project 
				int linesOfCode = getNumberOfLinesInProject(project);
				// Start the timer for performace
				long projectStartTime = System.currentTimeMillis();
				
				// Run the SSMC for the project
				if(project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
					IJavaProject javaProject = JavaCore.create(project);
					String kind = project.getClass().getName();
					//Build a tree out of the project
					JDTree myTree = new JDTree(javaProject, null);
					//pass the tree to the calculator
					Calculator calc = new Calculator(myTree);
					//start calculating metrics
					calc.calculate();
				}
				// End the timer for performace
				long projectEndTime = System.currentTimeMillis();
				// Calculate performace time
				long time = projectEndTime - projectStartTime;
		
				// Put values into Hashmap data structures
				timeValues.put(project.getName(), time);
				lengthValues.put(project.getName(), linesOfCode);
				
				// Just incase a program is empty
				if(time > 0) {
					float average = (float) linesOfCode / (float) time * 60000;
					//System.out.println("The project: " + project.getName() + "had a runtime of " + time + "\n With " + linesOfCode + " lines of code for an average of: " + "\n " + average + " lines of code per minute");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			assert(false	);
		}
		
   		try {
   			// Generate the CSV file to observe the data
			generateCSV();
		} catch (IOException e) {
			e.printStackTrace();
		}   
   		
   		//System.out.println("LENGTH: " + Arrays.toString(projects));
	}
	
	/**
	 * This method returns the total number of lines 
	 * in the project by getting the number of lines
	 * for each ICompilationUnit in the IProject
	 * @param project IProject
	 * @return int Number of lines of code in the Project
	 * @throws JavaModelException For when a Project is not Java-based, ignore
	 */
	int getNumberOfLinesInProject(IProject project) throws JavaModelException {
		// Intialize variable to return
		int linesOfCode = 0;
		// Get the JavaProject object
		IJavaProject javaProject = JavaCore.create(project); 
		// Parse by packages
		IPackageFragment[] packages = javaProject.getPackageFragments(); 
		// For each package in the Java project
		for(IPackageFragment aPackage : packages){ 
			// If the folder is a source folder
			//if(aPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				// Get the ICompilationUnit (class)
				for(ICompilationUnit aClass : aPackage.getCompilationUnits()) {
					// Parse into a compilationUnit to access values
					CompilationUnit unit = CAMValues.parse(aClass);
					// Add to the total number of lines of code by getting the 
					// line number last line of code 
					linesOfCode += unit.getLineNumber(unit.getLength() - 1);
				}
			//}
		}
		// Return value
		return linesOfCode;
	}
	 
	
	void generateCSV() throws IOException {
	
		// Intitialize local variables
		Long compileTotal = (long) 0;
		Integer linesTotal = 0;
		
		// Create a CSV file 
		FileWriter csvWriter = new FileWriter("Performance.csv");
		
		// Title Lines: Project, CompileTime, Lines of Code
		csvWriter.append("Project");
		csvWriter.append(",");
		csvWriter.append("Compile Time");
		csvWriter.append(",");
		csvWriter.append("Lines of Code");
		csvWriter.append(",");
		csvWriter.append("Lines of Code per Minute");
		csvWriter.append("\n");

		// For each Project visited
		for(String key : timeValues.keySet()) {

			// Project name
			csvWriter.append(key);
			csvWriter.append(",");
			// Compile Time
			csvWriter.append(timeValues.get(key).toString());
			csvWriter.append(",");
			// Lines of Code
			csvWriter.append(lengthValues.get(key).toString());
			csvWriter.append(",");
			// Lines of Code per Minute
			float average = lengthValues.get(key) / timeValues.get(key).floatValue() * 60000;
			csvWriter.append("" + average);
			csvWriter.append("\n");
			
			// Add to values
			compileTotal += timeValues.get(key);
			linesTotal += lengthValues.get(key);
			
		}
		
		float average =  linesTotal / compileTotal.floatValue()  * 60000;

		//System.out.print(average + " " + compileTotal + " " + linesTotal);
		// Data Consolidation
		csvWriter.append("Totals: ");
		csvWriter.append(",");
		csvWriter.append(compileTotal.toString());
		csvWriter.append(",");
		csvWriter.append(linesTotal.toString());
		csvWriter.append(",");
		csvWriter.append("Average Lines/Minute: " + average);
		csvWriter.append("\n");
		
		

		csvWriter.flush();
		csvWriter.close();
	}
	
	
	
	@Test
	void test() {}
	

}
