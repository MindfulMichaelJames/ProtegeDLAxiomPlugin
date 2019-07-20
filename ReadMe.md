# Description Logic Renderer  - Plugin for Protégé

This plugin is a "view" component for Protégé. 
It renders the axioms of the selected entity in Description Logic syntax.

## To simply install the plugin:

### Prerequisites 
Ensure that you have Protege 5.5.0 installed (although older versions should still work).

### Instructions
1. Click [here](https://github.com/MindfulMichaelJames/ProtegeDLAxiomPlugin/blob/master/target/dl-render-2.0.jar) to download the JAR plugin and put it in the "plugins" folder of your Protégé installation.
2. Launch Protégé
3. Go to Window>View>Class Views > DL Axiom Renderer
4. Drop the plugin in a window slot

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

## A screenshot of the plugin in action
