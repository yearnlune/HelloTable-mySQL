package yearnlune.lab.hellotable;

import yearnlune.lab.hellotable.template.Index;

/**
 * Project : mySQL_comment_generator
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2018.11.29
 * DESCRIPTION :
 */
public class Main {
    public static void main(String[] args) {
        String sql = "CREATE TABLE mt_log\n" +
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

        HelloTable helloTable = new HelloTable(sql);
        helloTable.makeIndex(new Index[]
                {
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
        System.out.println(helloTable);

    }
}
