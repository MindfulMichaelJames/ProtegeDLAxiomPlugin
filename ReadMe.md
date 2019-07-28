# Description Logic Renderer  - Plugin for Protégé

This plugin is a "view" component for Protégé. 
It renders the axioms of the selected entity in Description Logic syntax.

## To simply install the plugin:

### Prerequisites 
Ensure that you have Protege 5.5.0 installed (although older versions should still work).

### Instructions
1. Clone/download this repository.
2. Drag the `dl-render-2.0.jar` plugin and put it in the "plugins" folder of your Protégé installation.
3. Launch Protégé
4. Go to Window>View>Class Views > DL Axiom Renderer
5. Drop the plugin in a window slot

## To build from source:

### Prerequisites
Ensure that you have the latest version of Maven installed.  

### Instructions
1. Clone/download this repository.
2. Open a Terminal and navigate to the directory of the downloaded repository (`cd ProtegeDLAxiomPlugin`).
3. Enter the command `mvn clean install`
4. JAR file will be in the “target” folder within “ProtegeDLAxiomPlugin” folder.
5. Copy the JAR file into the “plugins” folder within Protégé root folder. 
6. Launch Protégé
7. Go to Window>View>Class Views > DL Axiom Renderer
8. Drop the plugin in a window slot

## Screenshots of the plugin in action
![alt text](https://github.com/MindfulMichaelJames/ProtegeDLAxiomPlugin/blob/master/screenshots/screenshot1.png "Pizza ontology")<br/><br/>
![alt text](https://github.com/MindfulMichaelJames/ProtegeDLAxiomPlugin/blob/master/screenshots/screenshot2.png "Stuff ontology")
