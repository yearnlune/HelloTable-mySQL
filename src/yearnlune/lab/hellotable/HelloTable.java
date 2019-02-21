package yearnlune.lab.hellotable;

import yearnlune.lab.hellotable.parse.Column;
import yearnlune.lab.hellotable.template.Comment;
import yearnlune.lab.hellotable.template.Index;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project : HelloTable-mySQL
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2019.02.21
 * DESCRIPTION :
 */

public class HelloTable {
    private final int TABLE_NAME_POS = 1;
    private final int COLUMN_NAME_POS = 1;
    private final int COLUMN_DEFINITION_POS = 2;

    private String tableName = "";
    private StringBuilder sb = new StringBuilder();
    private HashMap<Integer, Column> columnHashMap = new HashMap<>();

    public HelloTable(String sql) {
        parse(sql);
        makeTableComment();
        makeColumnComment();
    }

    private void parse(String sql) {
        if (!checkCreateStatement(sql))
            System.out.println("Sorry, Can't be analyzed");
        parseColumn(sql);
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

    private void parseColumn(String sql) {
        int idx = 0;

        String regex = "^\\s*(?!primary\\s*key|create\\s*table)((?:\\w+)|(?:`\\w+`))\\s*(.*?)(?:,|\\s*\\n\\s*\\))";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            columnHashMap.put(idx++, new Column.Builder()
                    .name(matcher.group(COLUMN_NAME_POS))
                    .def(refineTab(matcher.group(COLUMN_DEFINITION_POS)))
                    .build());
        }
    }

    private void makeTableComment() {
        sb.append("-- Table Comment\n");
        sb.append("ALTER TABLE ");
        sb.append(tableName);
        sb.append(" COMMENT = '';\n\n");
    }

    private void makeColumnComment() {
        sb.append("-- Column Comment\n");
        for (int i = 0; i < columnHashMap.size(); i++) {
            sb.append(new Comment.Builder()
                    .tableName(tableName)
                    .column(columnHashMap.get(i))
                    .build().toString());
        }
    }

    public void makeIndex(Index index) {
        sb.append("\n-- Index\n");
        index.setTableName(tableName);
        sb.append(index.toString());
    }

    public void makeIndex(Index[] indexes) {
        sb.append("\n-- Index\n");
        for (Index index : indexes) {
            index.setTableName(tableName);
            sb.append(index.toString());
        }
    }

    public void makeChecker() {
        sb.append("\n-- Check\n" +
                "SELECT DISTINCT TABLE_NAME\n" +
                "              , COLUMN_NAME\n" +
                "              , INDEX_NAME\n" +
                "              , CASE WHEN NON_UNIQUE = 0 THEN 'TRUE' ELSE 'FALSE' END AS IsUnique\n" +
                "FROM information_schema.STATISTICS\n" +
                "WHERE TABLE_NAME = '"+ tableName + "'\n" +
                ";\n" +
                "\n" +
                "-- Check\n" +
                "-- SELECT *\n" +
                "-- FROM "+ tableName + "\n" +
                "-- WHERE 1 = 1\n" +
                "-- ;");
    }

    private String refineTab(String str) {
        return str.replaceAll(" {2,}", " ");
    }

    public void print() {
        System.out.println(sb.toString());
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
