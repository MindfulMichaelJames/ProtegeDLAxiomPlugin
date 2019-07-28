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
//import org.apache.log4j.Logger;
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

import static za.ac.uct.cs.dlrenderer.FunctionMappings.treeToDL;
import static za.ac.uct.cs.dlrenderer.StructureModifiers.*;

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
    		OWLOntology ont = manager.getActiveOntology();
    		OWLDataFactory factory = manager.getOWLDataFactory();
			OWLClass curClass = factory.getOWLClass(e.getIRI());
			//getOWLDataProperty
			Set<OWLClassAxiom> curAxioms = ont.getAxioms(curClass);

			textArea.setText("");
			for (OWLClassAxiom ax : curAxioms){ //for each axiom in current set of axioms
				Node<String> root = owlToTree(String.valueOf(ax));
				String output = treeToDL(root);

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
