/**
 * Created by Almaz on 23.10.2017.
 */
public class JSONHandler {

    public static String handle(String string){
        StringBuilder builder = new StringBuilder();
        String five_spaces = "     ";
        builder.append(five_spaces).append("{\n");
        int last = 2;
        for(int i = 2; i < string.length(); i++){
            if(string.charAt(i) == '\n'){
                builder.append(five_spaces);
                builder.append(string.substring(last, i));
                if(i != string.length() - 2)
                    builder.append("\n");
                last = i + 1;
            }
        }
        return builder.append("\n").append(five_spaces).append("}").toString();
    }
}
