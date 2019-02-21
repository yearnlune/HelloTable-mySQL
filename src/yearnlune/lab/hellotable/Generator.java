package yearnlune.lab.hellotable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project : mySQL_comment_generator
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2018.11.29
 * DESCRIPTION :
 */

public class Generator {
    private String tableName = "";
    private StringBuilder sb = new StringBuilder();

    private final int TABLE_NAME_POS = 1;
    private final int COLUMN_NAME_POS = 1;
    private final int COLUMN_DEFINITION_POS = 2;

    public String generateComment(String sql) {
        if (!checkCreateStatement(sql))
            return "Sorry, Can't be analyzed";
        return parseColumn(sql);
    }

    private boolean checkCreateStatement(String sql) {
        boolean chk = false;

        String regex = "^\\s*create\\s*table\\s*((?:\\w+)|(?:`\\w+`))\\s*\\(.*?\\n\\s*\\).*?;";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            tableName = matcher.group(TABLE_NAME_POS);
            chk = true;
        }

        return chk;
    }

    private String parseColumn(String sql) {
        boolean isFisrt = true;

        String regex = "^\\s*(?!primary\\s*key|create\\s*table)((?:\\w+)|(?:`\\w+`))\\s*(.*?)(?:,|\\s*\\n\\s*\\))";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            makeCommentSQLForm(matcher.group(COLUMN_NAME_POS), matcher.group(COLUMN_DEFINITION_POS), isFisrt);
            isFisrt = false;
        }

        return sb.toString();
    }

    private void makeCommentSQLForm(String columnName, String columnDef, boolean isFirst) {
        if (isFirst) {
            sb.append("-- Table Comment\n");
            sb.append("ALTER TABLE ");
            sb.append(tableName);
            sb.append(" COMMENT = '';\n\n");
            sb.append("-- Column Comment\n");
        }
        sb.append("ALTER TABLE ");
        sb.append(tableName);
        sb.append(" CHANGE ");
        sb.append(columnName);
        sb.append(" ");
        sb.append(columnName);
        sb.append(" ");
        sb.append(refineTab(columnDef));
        sb.append(" COMMENT '';");
        sb.append("\n");
    }

    private String refineTab(String str) {
        return str.replaceAll(" {2,}"," ");
    }

    public void generateIndex() {

    }

}
