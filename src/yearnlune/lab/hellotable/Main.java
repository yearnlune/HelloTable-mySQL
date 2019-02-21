package yearnlune.lab.hellotable;

import yearnlune.lab.hellotable.template.Index;

/**
 * Project : HelloTable-mySQL
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2019.02.21
 * DESCRIPTION :
 */
public class Main {

    private static final String sql = "CREATE TABLE mt_log\n" +
            "(\n" +
            "  seq        INTEGER(11) AUTO_INCREMENT,\n" +
            "  zid        VARCHAR(40),\n" +
            "  created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  ip         VARCHAR(64) NOT NULL,\n" +
            "  user_agent VARCHAR(64),\n" +
            "  operation  CHAR(8)     NOT NULL,\n" +
            "  referer    VARCHAR(256),\n" +
            "  content    TEXT,\n" +
            "  PRIMARY KEY pk_mt_log (seq)\n" +
            ") ENGINE = InnoDB\n" +
            "  CHARACTER SET 'utf8';";

    public static void main(String[] args) {

        HelloTable helloTable = new HelloTable(sql);

        helloTable.makeIndex(
                new Index[]{
                        new Index.Builder()
                                .name("ix_mt_log_created_at")
                                .column("created_at")
                                .build(),
                        new Index.Builder()
                                .name("fk_mt_log_ot_acnt_zid")
                                .constraint(Index.Constraint.FOREIGN_KEY)
                                .column("zid")
                                .targetTable("ot_acnt")
                                .targetColumn("zid")
                                .build()
                }
        );
        helloTable.makeChecker();

        helloTable.print();
    }
}
