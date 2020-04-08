import java.util.ArrayList;
import java.util.Collections;

class HTMLElement {
    public String name,text;
    public ArrayList<HTMLElement> elements = new ArrayList<>();
    private final int indentSize = 2;
    private final String newLine = System.lineSeparator();
    
    public HTMLElement(){}
    public HTMLElement(String name, String text){
        this.name = name;
        this.text = text;
    }

    private String toStringImpl(int indent){
        StringBuilder sb = new StringBuilder();
        String i = String.join("",Collections.nCopies(indent*indentSize, " "));

        sb.append(String.format("%s<%s>%s", i, name, newLine));

        if(text!=null && !text.isEmpty()){
            sb.append(String.join("", Collections.nCopies(indentSize*(indent+1), " ")))
            .append(text).append(newLine);
        }
        for(HTMLElement e: elements) sb.append(e.toStringImpl(indent+1));

        sb.append(String.format("%s</%s>%s", i, name, newLine));

        return sb.toString();
    }

    @Override
    public String toString(){
        return toStringImpl(0);
    }

}
class HTMLBuilder{
    private String rootName;
    private HTMLElement root = new HTMLElement();

    public HTMLBuilder(String rootName){
        this.rootName = rootName;
        root.name = rootName;
    }

    public HTMLBuilder addChild(String childName, String childText){
        HTMLElement e = new HTMLElement(childName,childText);
        root.elements.add(e);
        return this;
    }

    public void clear(){
        root = new HTMLElement();
        root.name = rootName;
    }
    @Override
    public String toString(){
        return root.toString();
    }
}

class Demo{

    public static void main(String[] args) {
    String hello = "hello";
    System.out.println("<p>"+hello+"</p>");

    String[] words = {"hello","world"};

    StringBuilder sb = new StringBuilder();
    sb.append("<ul> \n");
    for(String word:words){
        sb.append(String.format("<li>%s</li> \n", word));
    }
    sb.append("</ul>");
    System.out.println(sb);

    // HTML Builder Usage -
    HTMLBuilder builder = new HTMLBuilder("ul");
    builder.addChild("li", "hello").addChild("li", "world").addChild("a", "inside").addChild("li", "out");
    System.out.println(builder);
}
}