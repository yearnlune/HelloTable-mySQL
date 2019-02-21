package yearnlune.lab.hellotable.template;

/**
 * Project : HelloTable-mySQL
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2019.02.21
 * DESCRIPTION :
 */
public class Index {
    public enum Constraint {
        INDEX((byte)0),
        PRIMARY_KEY((byte)1),
        FOREIGN_KEY((byte)2);

        byte value;

        Constraint(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }
    private String name;
    private Constraint constraint;
    private String[] column;
    private boolean isUnique;

    public static class Builder {
        private String name;
        private String[] column;
        private Constraint constraint = Constraint.INDEX;
        private boolean isUnique = false;

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

        public Builder constraint(Constraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public Builder isUnique(boolean isUnique) {
            this.isUnique = isUnique;
            return this;
        }

        public Index build() {
            return new Index(this);
        }
    }

    public Index() {
    }

    private Index(Builder builder) {
        name = builder.name;
        column = builder.column;
        constraint = builder.constraint;
        isUnique = builder.isUnique;
    }

}
