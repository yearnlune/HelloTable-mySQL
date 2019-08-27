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

    private static final String sql = "CREATE TABLE IF NOT EXISTS ot_agnt (\n" +
            "    id char(36) primary key,\n" +
            "    company_id char(36) not null,\n" +
            "    device_id char(36) not null,\n" +
            "    activation boolean default false not null,\n" +
            "    created_at timestamp default current_timestamp not null\n" +
            ");";

    public static void main(String[] args) {

        HelloTable helloTable = new HelloTable(sql);
        helloTable.makeIndex(
                new Index[]{
                        Index.builder()
                                .constraint(Index.Constraint.FOREIGN_KEY)
                                .column("device_id")
                                .targetTable("ot_dvic")
                                .targetColumn("id")
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
