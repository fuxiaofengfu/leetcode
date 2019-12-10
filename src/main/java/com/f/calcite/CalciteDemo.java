package com.f.calcite;

import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.*;

public class CalciteDemo {

	public static void main(String[] args) throws SqlParseException, ValidationException, RelConversionException {

		String sql = "select * from (select * from t inner join tt on t.f1=tt.f2) t1 ,c ,e  where c.f1=t1.f1";

		SqlParser sqlParser = //select * from a , b where a.f1=b.f1 union
				SqlParser.create(sql);
		SchemaPlus rootSchema = Frameworks.createRootSchema(false);
		FrameworkConfig build = Frameworks.newConfigBuilder().defaultSchema(rootSchema).build();
		SqlParser.Config parserConfig = build.getParserConfig();
		Planner planner = Frameworks.getPlanner(build);
		SqlNode parsed = planner.parse(sql);
		planner.validate(parsed);
		RelRoot relRoot = planner.rel(parsed);
		RelNode relNode = relRoot.project();
		System.out.print(RelOptUtil.toString(relNode));
	}
}
