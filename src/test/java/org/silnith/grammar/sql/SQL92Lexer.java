package org.silnith.grammar.sql;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.silnith.grammar.Lexer;
import org.silnith.grammar.Token;

public class SQL92Lexer implements Lexer<Terminals> {
    
    private final Set<String> reservedWords = new HashSet<String>(Arrays.asList(
            "ABSOLUTE", "ACTION", "ADD", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE",
            "AS", "ASC", "ASSERTION", "AT", "AUTHORIZATION", "AVG",
            "BEGIN", "BETWEEN", "BIT", "BIT_LENGTH", "BOTH", "BY",
            "CASCADE", "CASCADED", "CASE", "CAST", "CATALOG", "CHAR", "CHARACTER", "CHARACTER_LENGTH",
            "CHAR_LENGTH", "CHECK", "CLOSE", "COALESCE", "COLLATE", "COLLATION", "COLUMN", "COMMIT",
            "CONNECT", "CONNECTION", "CONSTRAINT", "CONSTRAINTS", "CONTINUE", "CONVERT", "CORRESPONDING",
            "CREATE", "CROSS", "CURRENT", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR",
            "DATE", "DAY", "DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT",
            "DEFERRABLE", "DEFERRED", "DELETE", "DESC", "DESCRIBE", "DESCRIPTOR", "DIAGNOSTICS",
            "DISCONNECT", "DISTINCT", "DOMAIN", "DOUBLE", "DROP",
            "ELSE", "END", "END-EXEC", "ESCAPE", "EXCEPT", "EXCEPTION", "EXEC", "EXECUTE", "EXISTS", "EXTERNAL", "EXTRACT",
            "FALSE", "FETCH", "FIRST", "FLOAT", "FOR", "FOREIGN", "FOUND", "FROM", "FULL",
            "GET", "GLOBAL", "GO", "GOTO", "GRANT", "GROUP",
            "HAVING", "HOUR",
            "IDENTITY", "IMMEDIATE", "IN", "INDICATOR", "INITIALLY", "INNER", "INPUT", "INSENSITIVE",
            "INSERT", "INT", "INTEGER", "INTERSECT", "INTERVAL", "INTO", "IS", "ISOLATION",
            "JOIN",
            "KEY",
            "LANGUAGE", "LAST", "LEADING", "LEFT", "LEVEL", "LIKE", "LOCAL", "LOWER",
            "MATCH", "MAX", "MIN", "MINUTE", "MODULE", "MONTH",
            "NAMES", "NATIONAL", "NATURAL", "NCHAR", "NEXT", "NO", "NOT", "NULL", "NULLIF", "NUMERIC",
            "OCTET_LENGTH", "OF", "ON", "ONLY", "OPEN", "OPTION", "OR", "ORDER", "OUTER", "OUTPUT", "OVERLAPS",
            "PAD", "PARTIAL", "POSITION", "PRECISION", "PREPARE", "PRESERVE", "PRIMARY", "PRIOR", "PRIVILEGES", "PROCEDURE", "PUBLIC",
            "READ", "REAL", "REFERENCES", "RELATIVE", "RESTRICT", "REVOKE", "RIGHT", "ROLLBACK", "ROWS",
            "SCHEMA", "SCROLL", "SECOND", "SECTION", "SELECT", "SESSION", "SESSION_USER", "SET",
            "SIZE", "SMALLINT", "SOME", "SPACE", "SQL", "SQLCODE", "SQLERROR", "SQLSTATE", "SUBSTRINT", "SUM", "SYSTEM_USER",
            "TO", "TRAILING", "TRANSACTION", "TRANSLATE", "TRANSLATION", "TRIM", "TRUE",
            "UNION", "UNIQUE", "UNKNOWN", "UPDATE", "UPPER", "USAGE", "USER", "USING",
            "VALUE", "VALUES", "VARCHAR", "VARYING", "VIEW",
            "WHEN", "WHENEVER", "WHERE", "WITH", "WORK", "WRITE",
            "YEAR",
            "ZONE"));
    
    private final Set<String> nonReservedWords = new HashSet<String>(Arrays.asList(
            "ADA",
            "C", "CATALOG_NAME", "CHARACTER_SET_CATALOG", "CHARACTER_SET_NAME", "CHARACTER_SET_SCHEMA",
            "CLASS_ORIGIN", "COBOL", "COLLATION_CATALOG", "COLLATION_NAME", "COLLATION_SCHEMA",
            "COLUMN_NAME", "COMMAND_FUNCTION", "COMMITTED", "CONDITION_NUMBER", "CONNECTION_NAME",
            "CONSTRAINT_CATALOG", "CONSTRAINT_NAME", "CONSTRAINT_SCHEMA", "CURSOR_NAME",
            "DATA", "DATETIME_INTERVAL_CODE", "DATETIME_INTERVAL_PRECISION", "DYNAMIC_FUNCTION",
            "FORTRAN",
            "LENGTH",
            "MESSAGE_LENGTH", "MESSAGE_OCTET_LENGTH", "MESSAGE_TEXT", "MORE", "MUMPS",
            "NAME", "NULLABLE", "NUMBER",
            "PASCAL", "PLI",
            "REPEATABLE", "RETURNED_LENGTH", "RETURNED_OCTET_LENGTH", "RETURNED_SQLSTATE", "ROW_COUNT",
            "SCALE", "SCHEMA_NAME", "SERIALIZABLE", "SERVER_NAME", "SUBCLASS_ORIGIN",
            "TABLE_NAME", "TYPE",
            "UNCOMMITTED", "UNNAMED"));

    @Override
    public Iterator<Token<Terminals>> iterator() {
        return null;
    }
    
}