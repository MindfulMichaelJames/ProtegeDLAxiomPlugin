package za.ac.uct.cs.dlrenderer;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.util.*;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import org.apache.log4j.Logger;
import org.protege.editor.owl.model.selection.OWLSelectionModel;
import org.protege.editor.owl.model.selection.OWLSelectionModelListener;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.ui.frame.OWLAnnotationsFrame;
import org.protege.editor.owl.ui.framelist.OWLFrameList;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxOWLObjectRendererImpl;

/**
 * 
 * @author Michael Harrison
 *
 */
public class DLAxiomRenderer extends AbstractOWLViewComponent {
	
	private JTextArea textArea;
	private JScrollPane scrollPlanes;
	private OWLSelectionModel selectionModel;
	private OWLSelectionModelListener listener = new OWLSelectionModelListener() {
		
		@Override
		public void selectionChanged() throws Exception {
			OWLEntity entity = getOWLWorkspace().getOWLSelectionModel().getSelectedEntity();
			updateView(entity);
		}
	};
	
	
	@Override
	protected void disposeOWLView() {
		selectionModel.removeListener(listener);
	}
	
	private void updateView(OWLEntity e) {
		if (e != null) {
			OWLModelManager manager = getOWLModelManager();
			String entityName = manager.getRendering(e);
    		OWLOntology ont = manager.getActiveOntology();
			Set<OWLClass> allClasses = ont.getClassesInSignature();
    		OWLDataFactory factory = manager.getOWLDataFactory();
			OWLClass curClass = factory.getOWLClass(e.getIRI());
			OWLClassExpression expression = curClass;
			ManchesterOWLSyntaxOWLObjectRendererImpl rendering = new ManchesterOWLSyntaxOWLObjectRendererImpl();
			Set<OWLClassAxiom> curAxioms = ont.getAxioms(curClass);
			
			String[] parts = new String[2];
			textArea.setText("");
			for (OWLClassAxiom ax : curAxioms){ //for each axiom in current set of axioms

				String output  = String.valueOf(rendering.render(ax)); //type casting to a string


//				Case for DISJOINT
				if (output.contains(" DisjointWith ")){
					output = output.replace(" DisjointWith ", " \u2293 ").concat(" = \u2205"); //replacing disjoint with the respective symbol and adding on additional symbol
				}
				//Case for SOME
				if (output.contains(" some "))
				{
						parts = output.split("\\s(?=\\S{1,100}\\ssome)");

						int sizeOfArray = parts.length; //getting length of array
						output = parts[0];
						for(int i = 1 ; i < sizeOfArray; i++)
						{
							output = output + " \u2203" + parts[i];
							for(int j = 0 ; j <sizeOfArray-1;j++) // if there are 2 some's. size of array is 3 So we wan t to replace it 2 times.
							{
								output = output.replace(" some ", ".");
							}
						}


				}
				//Case for ONLY
				if (output.contains(" only ")){

					parts = output.split("\\s(?=\\S{1,100}\\sonly)");

						int sizeOfArray = parts.length; //getting length of array
						output = parts[0];
						for(int i = 1 ; i < sizeOfArray; i++)
						{
							output = output + " \u2200" + parts[i];
							for(int j = 0 ; j <sizeOfArray-1;j++) // if there are 2 some's. size of array is 3 So we wan t to replace it 2 times.
							{
								output = output.replace(" only ", ".");
							}
						}

				}
				//Case for VALUE
				if (output.contains(" value ")){

					parts = output.split("\\s(?=\\S{1,100}\\svalue)");

						int sizeOfArray = parts.length; //getting length of array
						output = parts[0];
						for(int i = 1 ; i < sizeOfArray; i++)
						{
							output = output + " \u2203" + parts[i];
							for(int j = 0 ; j <sizeOfArray-1;j++) // if there are 2 some's. size of array is 3 So we wan t to replace it 2 times.
							{
								output = output.replace(" value ", ".");
							}
						}




				}
				//Case for MIN
				if (output.contains(" min ")){
					parts = output.split("\\s(?=\\S{1,100}\\smin)");
					/*
						Part 0 = pizza and
						part 1 = (hasTopping min 3 Thing) and
						part 2 = (hasTopping min 6 Thing)
					*/
					int sizeOfArray = parts.length; //getting length of array
					//3
					String outputDuplicate = output;
					output = parts[0];
					for(int i = 1 ; i < sizeOfArray; i++)
						{
							int firstIndex  = outputDuplicate.indexOf(" min ") + 5; //index before the first digit
							outputDuplicate = outputDuplicate.substring(firstIndex);

							int secondIndex = outputDuplicate.indexOf(' ');
							String number = outputDuplicate.substring(0, secondIndex); //string of entire number

							String conversion = outputDuplicate.substring(0,secondIndex+1);
							output = output + " \u2265" + number + parts[i].replace(" min ",".").replace(conversion,"");

						}


				}
				//Case for MAX
				if (output.contains(" max ")){
					parts = output.split("\\s(?=\\S{1,100}\\smax)");
					/*
						Part 0 = pizza and
						part 1 = (hasTopping min 3 Thing) and
						part 2 = (hasTopping min 6 Thing)
					*/
					int sizeOfArray = parts.length; //getting length of array
					//3
					String outputDuplicate = output;
					output = parts[0];
					for(int i = 1 ; i < sizeOfArray; i++)
						{
							int firstIndex  = outputDuplicate.indexOf(" max ") + 5; //index before the first digit
							outputDuplicate = outputDuplicate.substring(firstIndex);

							int secondIndex = outputDuplicate.indexOf(' ');
							String number = outputDuplicate.substring(0, secondIndex); //string of entire number

							String conversion = outputDuplicate.substring(0,secondIndex+1);
							output = output + " \u2264" + number + parts[i].replace(" max ",".").replace(conversion,"");

						}


				}

				//EXACTLY
				if (output.contains(" exactly ")){
					parts = output.split("\\s(?=\\S{1,100}\\sexactly)");
					/*
						Part 0 = pizza and
						part 1 = (hasTopping min 3 Thing) and
						part 2 = (hasTopping min 6 Thing)
					*/
					int sizeOfArray = parts.length; //getting length of array
					//3
					String outputDuplicate = output;
					output = parts[0];
					for(int i = 1 ; i < sizeOfArray; i++)
						{
							int firstIndex  = outputDuplicate.indexOf(" exactly ") + 9; //index before the first digit
							outputDuplicate = outputDuplicate.substring(firstIndex);

							int secondIndex = outputDuplicate.indexOf(' ');
							String number = outputDuplicate.substring(0, secondIndex); //string of entire number

							String conversion = outputDuplicate.substring(0,secondIndex+1);
							output = output + " \u003D" + number + parts[i].replace(" exactly ",".").replace(conversion,"");

						}

				}
				//Case for EQUIVALENT/SUBCLASS/AND/OR/NOT/INDIVIDUALS
				// using \\b to ensure that the word won't be mismatched in other words. For example, "and" will be picked up in "england"
				output = output.replaceAll("\\bEquivalentTo\\b", "\u2261").replaceAll("\\bSubClassOf\\b", "\u2291").replaceAll("\\band\\b", "\u2293").replaceAll("\\bor\\b", "\u2294").replaceAll("\\bnot\\b", "\u00AC").replaceAll(" , ","} \u2294 {");

				if (textArea.getText().equals("")){
					textArea.append(output);
				}
				else{
					textArea.append("\n" + output);
				}
			}

		}
		else {
			textArea.setText("");
		}
		
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		setLayout(new BorderLayout());
		textArea = new JTextArea(); //Area where the DL syntax will be displayed. 
		textArea.setEditable(false); //Locking the text area making it not editable
		textArea.setLineWrap(true); //wrapping the text
		scrollPlanes = new JScrollPane(textArea); //enables scrolling in the plane
        scrollPlanes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPlanes);
        selectionModel = getOWLWorkspace().getOWLSelectionModel();
		selectionModel.addListener(listener); //adding listener to each selection in the ontology.

		
	}

}
