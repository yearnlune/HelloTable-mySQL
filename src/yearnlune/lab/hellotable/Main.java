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

    private static final String sql = "CREATE TABLE IF NOT EXISTS ot_preg (\n" +
            "    id char(36) primary key,\n" +
            "    company_id char(36) not null,\n" +
            "    user_id char(36) not null,\n" +
            "    group_id char(36) not null,\n" +
            "    created_at timestamp default current_timestamp not null\n" +
            ");";

    public static void main(String[] args) {

        HelloTable helloTable = new HelloTable(sql);
        helloTable.makeIndex(
                new Index[]{
                        new Index.Builder()
                                .name("fk_ot_preg_ot_solu_user")
                                .constraint(Index.Constraint.FOREIGN_KEY)
                                .column("user_id")
                                .targetTable("ot_solu_user")
                                .targetColumn("id")
                                .build(),
                        new Index.Builder()
                                .name("fk_ot_preg_ot_grp")
                                .constraint(Index.Constraint.FOREIGN_KEY)
                                .column("group_id")
                                .targetTable("ot_grp")
                                .targetColumn("id")
                                .build(),
                        new Index.Builder()
                                .name("fk_ot_preg_created_at")
                                .constraint(Index.Constraint.INDEX)
                                .column("created_at")
                                .build(),
                }
        );
        helloTable.makeChecker();

        helloTable.print();
    }
}
