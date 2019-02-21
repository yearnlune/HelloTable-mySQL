package yearnlune.lab.hellotable;

/**
 * Project : HelloTable-mySQL
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2019.02.21
 * DESCRIPTION :
 */
public class Column {
    private String name;
    private String def;

    public Column(){};

    public Column(Builder builder) {
        name = builder.name;
        def = builder.def;
    }

    public String getName() {
        return name;
    }

    public String getDef() {
        return def;
    }

    public static class Builder {
        private String name;
        private String def;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder def(String def) {
            this.def = def;
            return this;
        }

        public Column build() {
            return new Column(this);
        }
    }


}
