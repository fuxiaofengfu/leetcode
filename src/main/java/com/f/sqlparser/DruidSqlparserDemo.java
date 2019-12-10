package com.f.sqlparser;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;
import java.util.Map;

public class DruidSqlparserDemo {

	public static void main(String[] args) {
		//
		String sql = "select * from table union select * from (select * from t inner join tt on t.f1=tt.f2) t1 ,c ,e  where c.f1=t1.f1 and t1.f2=c.f2";
		List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);

		for(SQLStatement sqlStatement: sqlStatements){
			SchemaStatVisitor schemaStatVisitor = SQLUtils.createSchemaStatVisitor(JdbcConstants.MYSQL);
			sqlStatement.accept(schemaStatVisitor);
			Map<TableStat.Name, TableStat> tables = schemaStatVisitor.getTables();
			System.out.println(sqlStatement.toLowerCaseString());
			tables.forEach((k,v)->{
				System.out.println(k.getName()+"===="+v.toString());
			});

			SQLSelectStatement selectStatement = (SQLSelectStatement)sqlStatement;
			SQLSelect select = selectStatement.getSelect();
			SQLSelectQuery query = select.getQuery();
			// 非union
			SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock)query;
			// union
			SQLUnionQuery sqlUnionQuery = (SQLUnionQuery)query;

			SQLTableSource from = selectQueryBlock.getFrom();

			// 需要递归处理
			if (from instanceof SQLExprTableSource) {
				SQLExprTableSource sqlExprTableSource = (SQLExprTableSource)from;
				System.out.println(sqlExprTableSource.getSchema());
				System.out.println(sqlExprTableSource.getName());
			} else if (from instanceof SQLJoinTableSource) {
				SQLJoinTableSource sqlJoinTableSource = (SQLJoinTableSource) from;

			} else if (from instanceof SQLSubqueryTableSource) {
				SQLSubqueryTableSource sqlSubqueryTableSource = (SQLSubqueryTableSource)from;
				System.out.println(sqlSubqueryTableSource.getSelect().getQuery());
			}
		}

		//System.out.println(SQLUtils.toSQLString(sqlStatements,JdbcConstants.MYSQL));
	}
}
