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

    private static final String sql =
            "CREATE TABLE IF NOT EXISTS ot_cach (\n" +
                    "    zmid char(36) not null,\n" +
                    "    latest_time timestamp default current_timestamp not null ON UPDATE current_timestamp,\n" +
                    "    category varchar(64) not null,\n" +
                    "    data mediumtext\n" +
                    ");";

    public static void main(String[] args) {

        HelloTable helloTable = new HelloTable(sql);
        helloTable.makeIndex(
                new Index[]{
                        Index.builder()
                                .constraint(Index.Constraint.PRIMARY_KEY)
                                .column(new String[]{"zmid", "category"})
                                .build(),
                        Index.builder()
                                .constraint(Index.Constraint.FOREIGN_KEY)
                                .column("zmid")
                                .targetTable("ot_agnt")
                                .targetColumn("zmid")
                                .build(),
                        Index.builder()
                                .constraint(Index.Constraint.INDEX)
                                .column("created_at")
                                .build(),
                        Index.builder()
                                .constraint(Index.Constraint.INDEX)
                                .column("company_id")
                                .build(),
                }
        );
        helloTable.makeChecker();

        helloTable.print();
    }
}
