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

        Generator generator = new Generator();


        System.out.println(generator.generateComment(sql));
    }
}
