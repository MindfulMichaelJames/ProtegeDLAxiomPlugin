package za.ac.uct.cs.dlrenderer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FunctionMappings {

    private static Map<String, String> functions = new HashMap<String, String>() {{
        put("ObjectInverseOf", "objectInverseOf");
        put("SubClassOf", "subClassOf");
        put("EquivalentClasses", "equivalentClasses");
        put("DisjointClasses", "disjointClasses");
        put("DisjointUnion", "disjointUnion");
        put("ObjectIntersectionOf", "objectIntersectionOf");
        put("ObjectUnionOf", "objectUnionOf");
        put("ObjectComplementOf", "objectComplementOf");
        put("ObjectOneOf", "oneOf");
        put("ObjectSomeValuesFrom", "someValuesFrom");
        put("ObjectAllValuesFrom", "allValuesFrom");
        put("ObjectHasValue", "hasValue");
        put("ObjectHasSelf", "objectHasSelf");
        put("ObjectMinCardinality", "minCardinality");
        put("ObjectMaxCardinality", "maxCardinality");
        put("ObjectExactCardinality", "exactCardinality");
        put("DataOneOf", "oneOf");
        put("DataSomeValuesFrom", "someValuesFrom");
        put("DataAllValuesFrom", "allValuesFrom");
        put("DataHasValue", "hasValue");
        put("DataMinCardinality", "minCardinality");
        put("DataMaxCardinality", "maxCardinality");
        put("DataExactCardinality", "exactCardinality");
        put("owl:topObjectProperty", "topObjectProperty");
        put("owl:bottomObjectProperty", "bottomObjectProperty");
        put("owl:Thing", "owlThing");
        put("owl:Nothing", "owlNothing");
        put("DataRangeRestriction", "dataRangeRestriction");
        put("minInclusive", "minInclusive");
        put("maxInclusive", "maxInclusive");
        put("minExclusive", "minExclusive");
        put("maxExclusive", "maxExclusive");
    }};


    public static String treeToDL(Node<String> owlClassAxiom){
        Method renderToDL = null;
        String result = "";
        try {
            renderToDL = FunctionMappings.class.getDeclaredMethod(
            functions.get(owlClassAxiom.getData()), Node.class);
        } catch (NoSuchMethodException | NullPointerException e){
            return owlClassAxiom.getData();
        }

        try {
            result = (String) renderToDL.invoke(null, owlClassAxiom);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String objectInverseOf(Node<String> inputNode){
        return inputNode.getChildren().get(0).getData()+"¯";
    }

    private static String subClassOf(Node<String> inputNode){
        List<Node<String>> subclassChildren = inputNode.getChildren();
        return treeToDL(subclassChildren.get(0)) + " \u2291 " +
                treeToDL(subclassChildren.get(1));
    }

    private static String equivalentClasses(Node<String> inputNode){
        List<Node<String>> equivalentChildren = inputNode.getChildren();
        String equivalentResultString = treeToDL(equivalentChildren.get(0));
        if (equivalentChildren.size() > 2){
            for (int i = 1; i < equivalentChildren.size(); i++) {
                equivalentResultString = equivalentResultString.concat(" \u2261 " +
                        parenthesise(equivalentChildren.get(i)));
            }
            return equivalentResultString;
        }
        else {
            return equivalentResultString + " \u2261 " + treeToDL(equivalentChildren.get(1));
        }
    }  //Yes

    private static String disjointClasses(Node<String> inputNode){
        return objectIntersectionOf(inputNode) + " \u2261 \u22A5";
    }  //Yes

    private static String disjointUnion(Node<String> inputNode){
        List<Node<String>> disjointChildren = inputNode.getChildren();
        String equivalentResultString = parenthesise(disjointChildren.get(0)) + " \u2261 " +
                parenthesise(disjointChildren.get(1));
        String disjointResultString = parenthesise(disjointChildren.get(1));
        for (int i = 2; i < disjointChildren.size(); i++){
            equivalentResultString = equivalentResultString.concat(" \u2294 " +
                    parenthesise(disjointChildren.get(i)));
            disjointResultString = disjointResultString.concat(" \u2293 " +
                    parenthesise(disjointChildren.get(i)));
        }
        return equivalentResultString + "\n" + disjointResultString + " \u2261 \u22A5";
    }  //Yes

    private static String objectIntersectionOf(Node<String> inputNode){
        List<Node<String>> intersectionChildren = inputNode.getChildren();
        String intersectionResultString = parenthesise(intersectionChildren.get(0));
        for (int i = 1; i < intersectionChildren.size(); i++) {
            intersectionResultString = intersectionResultString.concat(" \u2293 " +
                    parenthesise(intersectionChildren.get(i)));
        }
        return intersectionResultString;
    }  //Yes

    private static String objectUnionOf(Node<String> inputNode){
        List<Node<String>> unionChildren = inputNode.getChildren();
        String unionResultString = parenthesise(unionChildren.get(0));
        for (int i = 1; i < unionChildren.size(); i++) {
            unionResultString = unionResultString.concat(" \u2294 " + parenthesise(unionChildren.get(i)));
        }
        return unionResultString;
    }  //Yes

    private static String objectComplementOf(Node<String> inputNode){
        return "\u00AC" + parenthesise(inputNode.getChildren().get(0));
    }  //Yes

    private static String oneOf(Node<String> inputNode){
        List<Node<String>> oneChildren = inputNode.getChildren();
        String oneResultsString = "{" + oneChildren.get(0).getData() + "}";
        for (int i = 1; i < oneChildren.size(); i++){
            oneResultsString = oneResultsString.concat(" \u2294 {" +
                    oneChildren.get(i).getData()) + "}";
        }
        return oneResultsString;
    }

    private static String someValuesFrom(Node<String> inputNode){
        List<Node<String>> someChildren = inputNode.getChildren();
        return "\u2203" + treeToDL(someChildren.get(0)) + "." + parenthesise(someChildren.get(1));
    }  //Yes

    private static String allValuesFrom(Node<String> inputNode){
        List<Node<String>> allChildren = inputNode.getChildren();
        return "\u2200" + parenthesise(allChildren.get(0)) + "." + parenthesise(allChildren.get(1));
    }  //Yes

    private static String hasValue(Node<String> inputNode){
        List<Node<String>> hasChildren = inputNode.getChildren();
        return "\u2203" + hasChildren.get(0).getData() + ".{" + hasChildren.get(1).getData() + "}";
    }

    private static String objectHasSelf(Node<String> inputNode){
        return "\u2203" + inputNode.getChildren().get(0).getData() + ".Self";
    }

    private static String minCardinality(Node<String> inputNode){
        List<Node<String>> minCardinalityChildren = inputNode.getChildren();
        if (minCardinalityChildren.size() > 2) {
            return "\u2265 " + minCardinalityChildren.get(0).getData() + " " +
                        minCardinalityChildren.get(1).getData() + "." +
                        parenthesise(minCardinalityChildren.get(2));
        }
        else{
            return "\u2265 " + minCardinalityChildren.get(0).getData() + " " +
                    minCardinalityChildren.get(1).getData();
        }
    }   //Yes

    private static String maxCardinality(Node<String> inputNode){
        List<Node<String>> maxCardinalityChildren = inputNode.getChildren();
        if (maxCardinalityChildren.size() > 2){
            return "\u2264 " + maxCardinalityChildren.get(0).getData() + " " +
                        maxCardinalityChildren.get(1).getData() + "." +
                        parenthesise(maxCardinalityChildren.get(2));
        }
        else {
            return "\u2264 " + maxCardinalityChildren.get(0).getData() + " " +
                    maxCardinalityChildren.get(1).getData();
        }
    }   //Yes

    private static String exactCardinality(Node<String> inputNode){
        List<Node<String>> exactCardinalityChildren = inputNode.getChildren();
        if (exactCardinalityChildren.size() > 2){
            return "= " + exactCardinalityChildren.get(0).getData() + " " +
                        exactCardinalityChildren.get(1).getData() + "." +
                        parenthesise(exactCardinalityChildren.get(2));
        }
        else {
            return "= " + exactCardinalityChildren.get(0).getData() + " " +
                    exactCardinalityChildren.get(1).getData();
        }
    }   //Yes

    private static String topObjectProperty(Node<String> inputNode){
        return "U";
    }

    private static String bottomObjectProperty(Node<String> inputNode){
        return "B";
    }

    private static String owlThing(Node<String> inputNode){
        return "\u22A4";
    }

    private static String owlNothing(Node<String> inputNode){
        return "\u22A5";
    }

    private static String dataRangeRestriction(Node<String> inputNode){
        List<Node<String>> rangeChildren = inputNode.getChildren();
        String constrainingFacet = treeToDL(rangeChildren.get(1).getChildren().get(0));
        String valueSpace = rangeChildren.get(1).getChildren().get(1).getData();
        return treeToDL(rangeChildren.get(0)) + ": " + constrainingFacet +
                valueSpace.substring(valueSpace.indexOf('"')+1, valueSpace.indexOf("\"^^"));
    }

    private static String minInclusive(Node<String> inputNode){
        return "\u2265";
    }

    private static String maxInclusive(Node<String> inputNode){
        return "\u2264";
    }

    private static String minExclusive(Node<String> inputNode){
        return ">";
    }

    private static String maxExclusive(Node<String> inputNode){
        return "<";
    }

    private static String parenthesise(Node<String> inputNode){
        if (inputNode.getChildren().size() > 1){
            return "(" + treeToDL(inputNode) + ")";
        }
        else {
            return treeToDL(inputNode);
        }
    }
}
