package yearnlune.lab.hellotable.template;

import yearnlune.lab.hellotable.parse.Column;

/**
 * Project : HelloTable-mySQL
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2019.02.21
 * DESCRIPTION :
 */
public class Comment {
    private String tableName;
    private Column column;

    public Comment(Builder builder) {
        this.tableName = builder.tableName;
        this.column = builder.column;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ");
        sb.append(tableName);
        sb.append(" CHANGE ");
        sb.append(column.getName());
        sb.append(" ");
        sb.append(column.getName());
        sb.append(" ");
        sb.append(column.getDef().replaceAll(" primary key", ""));
        sb.append(" COMMENT '';");
        sb.append("\n");
        return sb.toString();
    }

    public static class Builder {
        private String tableName;
        private Column column;

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder column(Column column) {
            this.column = column;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
