import org.apache.commons.lang.StringUtils;

public class Research {

    public static void main(String[] args) {
       int  position=0;
        String input = "1 2 3 * 5 + * * 6 5";
        String[] result = input.split("\\s+");
        for (String aResult : result) {
            StringBuffer sb= new StringBuffer(input);
            position = input.indexOf(aResult);
            input =  sb.replace(0,++position,StringUtils.repeat(" ",position)).toString();
            System.out.println(input.length() + ":"+ input);
            System.out.println(aResult + " : " + position);
        }
    }
}
