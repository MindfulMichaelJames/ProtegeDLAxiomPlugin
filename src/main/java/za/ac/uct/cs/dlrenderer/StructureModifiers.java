package za.ac.uct.cs.dlrenderer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class StructureModifiers {

    public static Node<String> owlToTree(String owlClassAxiom){

        Node<String> root = new Node<>(owlClassAxiom);
        splitExpression(root);

        return root;
    }

    private static void splitExpression(Node<String> inputNode){
        String wholeExpression = inputNode.getData();
        System.out.println(wholeExpression);
        if (wholeExpression.contains("(")) {
            inputNode.setData(wholeExpression.substring(0, wholeExpression.indexOf("(")));
            int bracketCounter = 0;
            int startIndex = wholeExpression.indexOf("(");
            int endIndex;
            for (int i = wholeExpression.indexOf("("); i < wholeExpression.lastIndexOf(")"); i++){
                Character currentChar = new Character(wholeExpression.charAt(i));
                if (currentChar.equals('(')){
                    bracketCounter += 1;
                }
                else if (currentChar.equals(')')){
                    bracketCounter -= 1;
                }
                else if (currentChar.equals(' ')){
                    if (bracketCounter == 1){
                        endIndex = i;
                        Node<String> nextChildNode =
                                inputNode.addChild(new Node<String>(wholeExpression.substring(startIndex+1, endIndex)));
                        startIndex = endIndex;
                        splitExpression(nextChildNode);
                    }
                }
            }
            String endOfString = wholeExpression.substring(startIndex+1, wholeExpression.lastIndexOf(")"));
            if (endOfString.length()>0){
                if (!Character.isWhitespace(endOfString.charAt(0))) {
                    Node<String> lastChildNode = inputNode.addChild(new Node<String>(endOfString));
                    splitExpression(lastChildNode);
                }
            }
        }
        else{
            if (wholeExpression.contains("#")) {
                inputNode.setData(wholeExpression.substring(wholeExpression.indexOf("#")+1,
                        wholeExpression.indexOf(">")));
            }
        }
    }
}
