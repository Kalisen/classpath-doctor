import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SandBox {
    public static void main(String[] args) {
        String path = "BEFORE%VAR%MIDDLE%VAR%AFTER";
        String regexp = "%(.*?)%";
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(path);
        while (m.find()) {
            System.out.println("var = "
                    + m.group() + " " + m.start());
        }
    }
}
