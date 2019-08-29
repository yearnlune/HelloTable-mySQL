package yearnlune.lab.hellotable.template;

import java.util.HashMap;

/**
 * Project : HelloTable-mySQL
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2019.02.21
 * DESCRIPTION :
 */
public class Index {
    public enum Constraint {
        INDEX("INDEX"),
        PRIMARY_KEY("PRIMARY KEY"),
        FOREIGN_KEY("FOREIGN KEY");

        String value;

        Constraint(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private String name;
    private Constraint constraint;
    private String[] column;
    private boolean isUnique;
    private String tableName;
    private String targetTable;
    private String targetColumn;

    public Index(String tableName) {
        this.tableName = tableName;
    }

    private Index(Builder builder) {
        name = builder.name;
        column = builder.column;
        constraint = builder.constraint;
        isUnique = builder.isUnique;
        targetTable= builder.targetTable;
        targetColumn = builder.targetColumn;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        if (this.name == null) {
            setDefaultName();
        }
    }

    private void setDefaultName() {
        HashMap<String, String> abbreviation = setNamingConvention();
        StringBuilder sb = new StringBuilder();
        sb.append(abbreviation.get(constraint.getValue()));
        sb.append("_");
        sb.append(tableName);
        sb.append("_");
        if (constraint.getValue().equals(Constraint.FOREIGN_KEY.getValue())) {
            sb.append(targetTable);
        } else {
            for (int i = 0; i < column.length; ++i) {
                if (i != 0) sb.append("_");
                sb.append(column[i]);
            }
        }

       this.name = sb.toString();
    }

    private HashMap<String, String> setNamingConvention() {
        HashMap<String, String> abbreviation = new HashMap<>();
        abbreviation.put(Constraint.PRIMARY_KEY.getValue(), "pk");
        abbreviation.put(Constraint.FOREIGN_KEY.getValue(), "fk");
        abbreviation.put(Constraint.INDEX.getValue(), "ix");

        return abbreviation;
    }

    private String refineColumnString() {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String col : column) {
            if (!isFirst) {
                sb.append(", ");
            }
            sb.append(col);
            isFirst = false;
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ");
        sb.append(tableName);
        sb.append(" ADD ");
        sb.append(constraint.getValue());
        sb.append(" ");
        sb.append(name);
        sb.append(" (");
        sb.append(refineColumnString());
        sb.append(")");
        if (constraint == Constraint.FOREIGN_KEY) {
            sb.append(" REFERENCES ");
            sb.append(targetTable);
            sb.append(" (");
            sb.append(targetColumn);
            sb.append(")");
        }
        sb.append(";\n");
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String[] column;
        private Constraint constraint = Constraint.INDEX;
        private boolean isUnique = false;
        private String targetTable;
        private String targetColumn;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder column(String[] column) {
            if (column != null)
                this.column = column.clone();
            else this.column = null;
            return this;
        }

        public Builder column(String column) {
            this.column = new String[]{column};
            return this;
        }

        public Builder constraint(Constraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public Builder isUnique(boolean isUnique) {
            this.isUnique = isUnique;
            return this;
        }

        public Builder targetTable(String targetTable) {
            this.targetTable = targetTable;
            return this;
        }

        public Builder targetColumn(String targetColumn) {
            this.targetColumn = targetColumn;
            return this;
        }

        public Index build() {
            return new Index(this);
        }
    }
}
