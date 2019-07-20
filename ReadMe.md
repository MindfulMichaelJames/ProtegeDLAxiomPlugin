# Description Logic Renderer  - Plugin for Protégé

This plugin is a "view" component for Protégé. 
It renders the axioms of the selected entity in Description Logic syntax.

## To simply install the plugin:

### Prerequisites 
Ensure that you have Protege 5.5.0 installed (although older versions should still work).

### Instructions
Copy the [JAR file](https://github.com/MindfulMichaelJames/ProtegeDLAxiomPlugin/blob/master/target/dl-render-2.0.jar) from the "target" folder in this repository and put it in the "plugins" folder of your Protégé installation.

## To build from source:

### Prerequisites
Ensure that you have the latest version of Maven installed.  

### Instructions
1. Launch Terminal
2. cd to “ProtegeDLAxiomPlugin” folder
3. Type “mvn clean install”
4. JAR file will be in the “target” folder within “ProtegeDLAxiomPlugin” folder.
5. Copy the JAR file into the “plugins” folder within Protégé root folder. 
6. Launch Protégé
7. Go to Window>View>Class Views > DL Axiom Renderer
8. Drop the plugin in a window slot
