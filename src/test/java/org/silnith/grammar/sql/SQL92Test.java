package org.silnith.grammar.sql;

import static org.silnith.grammar.sql.Terminals.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.silnith.grammar.EnumSetFactory;
import org.silnith.grammar.Grammar;
import org.silnith.grammar.NonTerminalSymbol;
import org.silnith.grammar.TestProductionHandler;

public class SQL92Test {
    
    private Grammar<Terminals> grammar;
    
    private NonTerminalSymbol nt(final String name) {
        return grammar.getNonTerminalSymbol(name);
    }
    
    /**
     * From <a href="https://ronsavage.github.io/SQL/sql-92.bnf.html">https://ronsavage.github.io/SQL/sql-92.bnf.html</a>
     */
    @Before
    public void setUp() {
        final Logger logger = Logger.getLogger(Grammar.class.getName());
        final ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.FINE);
        
        grammar = new Grammar<>(new EnumSetFactory<>(Terminals.class));
        
        /*
         * Basic Definitions of Characters Used, Tokens, Symbols, Etc.
         */
        
        /*
         * Literal Numbers, Strings, Dates and Times
         */
        
        /*
         * SQL Module
         */
        grammar.addProduction(nt("module"), new TestProductionHandler("module"), nt("module name clause"), nt("language clause"), nt("module authorization clause"), nt("module contents"));
        grammar.addProduction(nt("module"), new TestProductionHandler("module"), nt("module name clause"), nt("language clause"), nt("module authorization clause"), nt("temporary table declaration"), nt("module contents"));
        
        grammar.addProduction(nt("module name clause"), new TestProductionHandler("module name clause"), MODULE);
        grammar.addProduction(nt("module name clause"), new TestProductionHandler("module name clause"), MODULE, nt("module name"));
        grammar.addProduction(nt("module name clause"), new TestProductionHandler("module name clause"), MODULE, nt("module character set specification"));
        grammar.addProduction(nt("module name clause"), new TestProductionHandler("module name clause"), MODULE, nt("module name"), nt("module character set specification"));
        
        grammar.addProduction(nt("module name"), new TestProductionHandler("module name"), nt("identifier"));
        
        grammar.addProduction(nt("module character set specification"), new TestProductionHandler("module character set specification"), NAMES, ARE, nt("character set specification"));
        
        grammar.addProduction(nt("language clause"), new TestProductionHandler("language clause"), LANGUAGE, nt("language name"));
        
//        grammar.addProduction(nt("language name"), new TestProductionHandler("language name"), ADA);
//        grammar.addProduction(nt("language name"), new TestProductionHandler("language name"), C);
//        grammar.addProduction(nt("language name"), new TestProductionHandler("language name"), COBOL);
//        grammar.addProduction(nt("language name"), new TestProductionHandler("language name"), FORTRAN);
//        grammar.addProduction(nt("language name"), new TestProductionHandler("language name"), MUMPS);
//        grammar.addProduction(nt("language name"), new TestProductionHandler("language name"), PASCAL);
//        grammar.addProduction(nt("language name"), new TestProductionHandler("language name"), PLI);
        
        grammar.addProduction(nt("module authorization clause"), new TestProductionHandler("module authorization clause"), SCHEMA, nt("schema name"));
        grammar.addProduction(nt("module authorization clause"), new TestProductionHandler("module authorization clause"), AUTHORIZATION, nt("module authorization identifier"));
        grammar.addProduction(nt("module authorization clause"), new TestProductionHandler("module authorization clause"), SCHEMA, nt("schema name"), AUTHORIZATION, nt("module authorization identifier"));
        
        grammar.addProduction(nt("module authorization identifier"), new TestProductionHandler("module authorization identifier"), nt("authorization identifier"));
        
        grammar.addProduction(nt("authorization identifier"), new TestProductionHandler("authorization identifier"), nt("identifier"));
        
        grammar.addProduction(nt("temporary table declaration"), new TestProductionHandler("temporary table declaration"), DECLARE, LOCAL, TEMPORARY, TABLE, nt("qualified local table name"), nt("table element list"));
        grammar.addProduction(nt("temporary table declaration"), new TestProductionHandler("temporary table declaration"), DECLARE, LOCAL, TEMPORARY, TABLE, nt("qualified local table name"), nt("table element list"), ON, COMMIT, PRESERVE, ROWS);
        grammar.addProduction(nt("temporary table declaration"), new TestProductionHandler("temporary table declaration"), DECLARE, LOCAL, TEMPORARY, TABLE, nt("qualified local table name"), nt("table element list"), ON, COMMIT, DELETE, ROWS);
        
        grammar.addProduction(nt("qualified local table name"), new TestProductionHandler("qualified local table name"), MODULE, period, nt("local table name"));
        
        grammar.addProduction(nt("local table name"), new TestProductionHandler("local table name"), nt("qualified identifier"));
        
        grammar.addProduction(nt("qualified identifier"), new TestProductionHandler("qualified identifier"), nt("identifier"));
        
        grammar.addProduction(nt("table element list"), new TestProductionHandler("table element list"), left_paren, nt("table element"), right_paren);
        grammar.addProduction(nt("table element list"), new TestProductionHandler("table element list"), left_paren, nt("table element"), comma, nt("table element"), right_paren);
        // TODO: loop
        
        grammar.addProduction(nt("table element"), new TestProductionHandler("table element"), nt("column definition"));
        grammar.addProduction(nt("table element"), new TestProductionHandler("table element"), nt("table constraint definition"));
        
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("data type"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("domain name"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("data type"), nt("default clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("domain name"), nt("default clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("data type"), nt("column constraint definition"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("domain name"), nt("column constraint definition"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("data type"), nt("default clause"), nt("column constraint definition"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("domain name"), nt("default clause"), nt("column constraint definition"));
        // TODO: loop constraints
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("data type"), nt("collate clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("domain name"), nt("collate clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("data type"), nt("default clause"), nt("collate clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("domain name"), nt("default clause"), nt("collate clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("data type"), nt("column constraint definition"), nt("collate clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("domain name"), nt("column constraint definition"), nt("collate clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("data type"), nt("default clause"), nt("column constraint definition"), nt("collate clause"));
        grammar.addProduction(nt("column definition"), new TestProductionHandler("column definition"), nt("column name"), nt("domain name"), nt("default clause"), nt("column constraint definition"), nt("collate clause"));
        
        grammar.addProduction(nt("column name"), new TestProductionHandler("column name"), nt("identifier"));
        
        /*
         * Data Types
         */
        grammar.addProduction(nt("data type"), new TestProductionHandler("data type"), nt("character string type"));
        grammar.addProduction(nt("data type"), new TestProductionHandler("data type"), nt("character string type"), CHARACTER, SET, nt("character set specification"));
        grammar.addProduction(nt("data type"), new TestProductionHandler("data type"), nt("national character string type"));
        grammar.addProduction(nt("data type"), new TestProductionHandler("data type"), nt("bit string type"));
        grammar.addProduction(nt("data type"), new TestProductionHandler("data type"), nt("numeric type"));
        grammar.addProduction(nt("data type"), new TestProductionHandler("data type"), nt("datetime type"));
        grammar.addProduction(nt("data type"), new TestProductionHandler("data type"), nt("interval type"));
        
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), CHARACTER);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), CHARACTER, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), CHAR);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), CHAR, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), CHARACTER, VARYING);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), CHARACTER, VARYING, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), CHAR, VARYING);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), CHAR, VARYING, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), VARCHAR);
        grammar.addProduction(nt("character string type"), new TestProductionHandler("character string type"), VARCHAR, left_paren, nt("length"), right_paren);
        
        grammar.addProduction(nt("length"), new TestProductionHandler("length"), unsigned_integer);
        
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NATIONAL, CHARACTER);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NATIONAL, CHARACTER, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NATIONAL, CHAR);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NATIONAL, CHAR, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NCHAR);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NCHAR, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NATIONAL, CHARACTER, VARYING);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NATIONAL, CHARACTER, VARYING, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NATIONAL, CHAR, VARYING);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NATIONAL, CHAR, VARYING, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NCHAR, VARYING);
        grammar.addProduction(nt("national character string type"), new TestProductionHandler("national character string type"), NCHAR, VARYING, left_paren, nt("length"), right_paren);
        
        grammar.addProduction(nt("bit string type"), new TestProductionHandler("bit string type"), BIT);
        grammar.addProduction(nt("bit string type"), new TestProductionHandler("bit string type"), BIT, left_paren, nt("length"), right_paren);
        grammar.addProduction(nt("bit string type"), new TestProductionHandler("bit string type"), BIT, VARYING);
        grammar.addProduction(nt("bit string type"), new TestProductionHandler("bit string type"), BIT, VARYING, left_paren, nt("length"), right_paren);
        
        grammar.addProduction(nt("numeric type"), new TestProductionHandler("numeric type"), nt("exact numeric type"));
        grammar.addProduction(nt("numeric type"), new TestProductionHandler("numeric type"), nt("approximate numeric type"));
        
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), NUMERIC);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), NUMERIC, left_paren, nt("precision"), right_paren);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), NUMERIC, left_paren, nt("precision"), comma, nt("scale"), right_paren);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), DECIMAL);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), DECIMAL, left_paren, nt("precision"), right_paren);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), DECIMAL, left_paren, nt("precision"), comma, nt("scale"), right_paren);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), DEC);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), DEC, left_paren, nt("precision"), right_paren);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), DEC, left_paren, nt("precision"), comma, nt("scale"), right_paren);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), INTEGER);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), INT);
        grammar.addProduction(nt("exact numeric type"), new TestProductionHandler("exact numeric type"), SMALLINT);
        
        grammar.addProduction(nt("precision"), new TestProductionHandler("precision"), unsigned_integer);
        
        grammar.addProduction(nt("scale"), new TestProductionHandler("scale"), unsigned_integer);
        
        grammar.addProduction(nt("approximate numeric type"), new TestProductionHandler("approximate numeric type"), FLOAT);
        grammar.addProduction(nt("approximate numeric type"), new TestProductionHandler("approximate numeric type"), FLOAT, left_paren, nt("precision"), right_paren);
        grammar.addProduction(nt("approximate numeric type"), new TestProductionHandler("approximate numeric type"), REAL);
        grammar.addProduction(nt("approximate numeric type"), new TestProductionHandler("approximate numeric type"), DOUBLE, PRECISION);
        
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), DATE);
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), TIME);
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), TIME, left_paren, nt("time precision"), right_paren);
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), TIME, WITH, TIME, ZONE);
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), TIME, left_paren, nt("time precision"), right_paren, WITH, TIME, ZONE);
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), TIMESTAMP);
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), TIMESTAMP, left_paren, nt("timestamp precision"), right_paren);
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), TIMESTAMP, WITH, TIME, ZONE);
        grammar.addProduction(nt("datetime type"), new TestProductionHandler("datetime type"), TIMESTAMP, left_paren, nt("timestamp precision"), right_paren, WITH, TIME, ZONE);
        
        grammar.addProduction(nt("time precision"), new TestProductionHandler("time precision"), nt("time fractional seconds precision"));
        
        grammar.addProduction(nt("time fractional seconds precision"), new TestProductionHandler("time fractional seconds precision"), unsigned_integer);
        
        grammar.addProduction(nt("timestamp precision"), new TestProductionHandler("timestamp precision"), nt("time fractional seconds precision"));
        
        grammar.addProduction(nt("interval type"), new TestProductionHandler("interval type"), INTERVAL, nt("interval qualifier"));
        
        grammar.addProduction(nt("interval qualifier"), new TestProductionHandler("interval qualifier"), nt("start field"), TO, nt("end field"));
        grammar.addProduction(nt("interval qualifier"), new TestProductionHandler("interval qualifier"), nt("single datetime field"));
        
        grammar.addProduction(nt("start field"), new TestProductionHandler("start field"), nt("non-second datetime field"));
        grammar.addProduction(nt("start field"), new TestProductionHandler("start field"), nt("non-second datetime field"), left_paren, nt("interval leading field precision"), right_paren);
        
        grammar.addProduction(nt("non-second datetime field"), new TestProductionHandler("non-second datetime field"), YEAR);
        grammar.addProduction(nt("non-second datetime field"), new TestProductionHandler("non-second datetime field"), MONTH);
        grammar.addProduction(nt("non-second datetime field"), new TestProductionHandler("non-second datetime field"), DAY);
        grammar.addProduction(nt("non-second datetime field"), new TestProductionHandler("non-second datetime field"), HOUR);
        grammar.addProduction(nt("non-second datetime field"), new TestProductionHandler("non-second datetime field"), MINUTE);
        
        grammar.addProduction(nt("interval leading field precision"), new TestProductionHandler("interval leading field precision"), unsigned_integer);
        
        grammar.addProduction(nt("single datetime field"), new TestProductionHandler("single datetime field"), nt("non-second datetime field"));
        grammar.addProduction(nt("single datetime field"), new TestProductionHandler("single datetime field"), nt("non-second datetime field"), left_paren, nt("interval leading field precision"), right_paren);
        grammar.addProduction(nt("single datetime field"), new TestProductionHandler("single datetime field"), SECOND);
        grammar.addProduction(nt("single datetime field"), new TestProductionHandler("single datetime field"), SECOND, left_paren, nt("interval leading field precision"), right_paren);
        grammar.addProduction(nt("single datetime field"), new TestProductionHandler("single datetime field"), SECOND, left_paren, nt("interval leading field precision"), comma, nt("interval fractional seconds precision"), right_paren);
        // TODO: the above production is claimed to need TWO left parentheses, but I think that's a typo so I omitted one
        
        grammar.addProduction(nt("domain name"), new TestProductionHandler("domain name"), nt("qualified name"));
        
        grammar.addProduction(nt("qualified name"), new TestProductionHandler("qualified name"), nt("qualified identifier"));
        grammar.addProduction(nt("qualified name"), new TestProductionHandler("qualified name"), nt("schema name"), period, nt("qualified identifier"));
        
        grammar.addProduction(nt("default clause"), new TestProductionHandler("default clause"), DEFAULT, nt("default option"));
        
        grammar.addProduction(nt("default option"), new TestProductionHandler("default option"), nt("literal"));
        grammar.addProduction(nt("default option"), new TestProductionHandler("default option"), nt("datetime value function"));
        grammar.addProduction(nt("default option"), new TestProductionHandler("default option"), USER);
        grammar.addProduction(nt("default option"), new TestProductionHandler("default option"), CURRENT_USER);
        grammar.addProduction(nt("default option"), new TestProductionHandler("default option"), SESSION_USER);
        grammar.addProduction(nt("default option"), new TestProductionHandler("default option"), SYSTEM_USER);
        grammar.addProduction(nt("default option"), new TestProductionHandler("default option"), NULL);
        
        /*
         * Literals
         */
        grammar.addProduction(nt("literal"), new TestProductionHandler("literal"), nt("signed numeric literal"));
        grammar.addProduction(nt("literal"), new TestProductionHandler("literal"), nt("general literal"));
        
        grammar.addProduction(nt("signed numeric literal"), new TestProductionHandler("signed numeric literal"), nt("unsigned numeric literal"));
        grammar.addProduction(nt("signed numeric literal"), new TestProductionHandler("signed numeric literal"), nt("sign"), nt("unsigned numeric literal"));
        
        grammar.addProduction(nt("general literal"), new TestProductionHandler("general literal"), nt("character string literal"));
        grammar.addProduction(nt("general literal"), new TestProductionHandler("general literal"), nt("national character string literal"));
        grammar.addProduction(nt("general literal"), new TestProductionHandler("general literal"), nt("bit string literal"));
        grammar.addProduction(nt("general literal"), new TestProductionHandler("general literal"), nt("hex string literal"));
        grammar.addProduction(nt("general literal"), new TestProductionHandler("general literal"), nt("datetime literal"));
        grammar.addProduction(nt("general literal"), new TestProductionHandler("general literal"), nt("interval literal"));
        
        grammar.addProduction(nt("datetime literal"), new TestProductionHandler("datetime literal"), nt("date literal"));
        grammar.addProduction(nt("datetime literal"), new TestProductionHandler("datetime literal"), nt("time literal"));
        grammar.addProduction(nt("datetime literal"), new TestProductionHandler("datetime literal"), nt("timestamp literal"));
        
        grammar.addProduction(nt("date literal"), new TestProductionHandler("date literal"), DATE, nt("date string"));
        
        grammar.addProduction(nt("time literal"), new TestProductionHandler("time literal"), TIME, nt("time string"));
        
        grammar.addProduction(nt("timestamp literal"), new TestProductionHandler("timestamp literal"), TIMESTAMP, nt("timestamp string"));
        
        grammar.addProduction(nt("interval literal"), new TestProductionHandler("interval literal"), INTERVAL, nt("interval string"), nt("interval qualifier"));
        grammar.addProduction(nt("interval literal"), new TestProductionHandler("interval literal"), INTERVAL, nt("sign"), nt("interval string"), nt("interval qualifier"));
        
        grammar.addProduction(nt("datetime value function"), new TestProductionHandler("datetime value function"), nt("current date value function"));
        grammar.addProduction(nt("datetime value function"), new TestProductionHandler("datetime value function"), nt("current time value function"));
        grammar.addProduction(nt("datetime value function"), new TestProductionHandler("datetime value function"), nt("current timestamp value function"));
        
        grammar.addProduction(nt("current date value function"), new TestProductionHandler("current date value function"), CURRENT_DATE);
        
        grammar.addProduction(nt("current time value function"), new TestProductionHandler("current time value function"), CURRENT_TIME);
        grammar.addProduction(nt("current time value function"), new TestProductionHandler("current time value function"), CURRENT_TIME, left_paren, nt("time precision"), right_paren);
        
        grammar.addProduction(nt("current timestamp value function"), new TestProductionHandler("current timestamp value function"), CURRENT_TIMESTAMP);
        grammar.addProduction(nt("current timestamp value function"), new TestProductionHandler("current timestamp value function"), CURRENT_TIMESTAMP, left_paren, nt("timestamp precision"), right_paren);
        
        /*
         * Constraints
         */
        grammar.addProduction(nt("column constraint definition"), new TestProductionHandler("column constraint definition"), nt("column constraint"));
        grammar.addProduction(nt("column constraint definition"), new TestProductionHandler("column constraint definition"), nt("column constraint"), nt("constraint attributes"));
        grammar.addProduction(nt("column constraint definition"), new TestProductionHandler("column constraint definition"), nt("constraint name definition"), nt("column constraint"));
        grammar.addProduction(nt("column constraint definition"), new TestProductionHandler("column constraint definition"), nt("constraint name definition"), nt("column constraint"), nt("constraint attributes"));
        
        grammar.addProduction(nt("constraint name definition"), new TestProductionHandler("constraint name definition"), CONSTRAINT, nt("constraint name"));
        
        grammar.addProduction(nt("constraint name"), new TestProductionHandler("constraint name"), nt("qualified name"));
        
        grammar.addProduction(nt("column constraint"), new TestProductionHandler("column constraint"), NOT, NULL);
        grammar.addProduction(nt("column constraint"), new TestProductionHandler("column constraint"), nt("unique specification"));
        grammar.addProduction(nt("column constraint"), new TestProductionHandler("column constraint"), nt("references specification"));
        grammar.addProduction(nt("column constraint"), new TestProductionHandler("column constraint"), nt("check constraint definition"));
        
        grammar.addProduction(nt("unique specification"), new TestProductionHandler("unique specification"), UNIQUE);
        grammar.addProduction(nt("unique specification"), new TestProductionHandler("unique specification"), PRIMARY, KEY);
        
        grammar.addProduction(nt("references specification"), new TestProductionHandler("references specification"), REFERENCES, nt("referenced table and columns"));
        grammar.addProduction(nt("references specification"), new TestProductionHandler("references specification"), REFERENCES, nt("referenced table and columns"), MATCH, nt("match type"));
        grammar.addProduction(nt("references specification"), new TestProductionHandler("references specification"), REFERENCES, nt("referenced table and columns"), nt("referential triggered action"));
        grammar.addProduction(nt("references specification"), new TestProductionHandler("references specification"), REFERENCES, nt("referenced table and columns"), MATCH, nt("match type"), nt("referential triggered action"));
        
        grammar.addProduction(nt("referenced table and columns"), new TestProductionHandler("referenced table and columns"), nt("table name"));
        grammar.addProduction(nt("referenced table and columns"), new TestProductionHandler("referenced table and columns"), nt("table name"), left_paren, nt("reference column list"), right_paren);
        
        grammar.addProduction(nt("table name"), new TestProductionHandler("table name"), nt("qualified name"));
        grammar.addProduction(nt("table name"), new TestProductionHandler("table name"), nt("qualified local table name"));
        
        grammar.addProduction(nt("reference column list"), new TestProductionHandler("reference column list"), nt("column name list"));
        
        grammar.addProduction(nt("column name list"), new TestProductionHandler("column name list"), nt("column name"));
        grammar.addProduction(nt("column name list"), new TestProductionHandler("column name list"), nt("column name list"), comma, nt("column name"));
        
        grammar.addProduction(nt("match type"), new TestProductionHandler("match type"), FULL);
        grammar.addProduction(nt("match type"), new TestProductionHandler("match type"), PARTIAL);
        
        grammar.addProduction(nt("referential triggered action"), new TestProductionHandler("referential triggered action"), nt("update rule"));
        grammar.addProduction(nt("referential triggered action"), new TestProductionHandler("referential triggered action"), nt("update rule"), nt("delete rule"));
        grammar.addProduction(nt("referential triggered action"), new TestProductionHandler("referential triggered action"), nt("delete rule"));
        grammar.addProduction(nt("referential triggered action"), new TestProductionHandler("referential triggered action"), nt("delete rule"), nt("update rule"));
        
        grammar.addProduction(nt("update rule"), new TestProductionHandler("update rule"), ON, UPDATE, nt("referential action"));
        
        grammar.addProduction(nt("referential action"), new TestProductionHandler("referential action"), CASCADE);
        grammar.addProduction(nt("referential action"), new TestProductionHandler("referential action"), SET, NULL);
        grammar.addProduction(nt("referential action"), new TestProductionHandler("referential action"), SET, DEFAULT);
        grammar.addProduction(nt("referential action"), new TestProductionHandler("referential action"), NO, ACTION);
        
        grammar.addProduction(nt("delete rule"), new TestProductionHandler("delete rule"), ON, DELETE, nt("referential action"));
        
        grammar.addProduction(nt("check constraint definition"), new TestProductionHandler("check constraint definition"), CHECK, left_paren, nt("search condition"), right_paren);
        
        /*
         * Search Condition
         */
        grammar.addProduction(nt("search condition"), new TestProductionHandler("search condition"), nt("boolean term"));
        grammar.addProduction(nt("search condition"), new TestProductionHandler("search condition"), nt("search condition"), OR, nt("boolean term"));
        
        grammar.addProduction(nt("boolean term"), new TestProductionHandler("boolean term"), nt("boolean factor"));
        grammar.addProduction(nt("boolean term"), new TestProductionHandler("boolean term"), nt("boolean term"), AND, nt("boolean factor"));
        
        grammar.addProduction(nt("boolean factor"), new TestProductionHandler("boolean factor"), nt("boolean test"));
        grammar.addProduction(nt("boolean factor"), new TestProductionHandler("boolean factor"), NOT, nt("boolean test"));
        
        grammar.addProduction(nt("boolean test"), new TestProductionHandler("boolean test"), nt("boolean primary"));
        grammar.addProduction(nt("boolean test"), new TestProductionHandler("boolean test"), nt("boolean primary"), IS, nt("truth value"));
        grammar.addProduction(nt("boolean test"), new TestProductionHandler("boolean test"), nt("boolean primary"), IS, NOT, nt("truth value"));
        
        grammar.addProduction(nt("boolean primary"), new TestProductionHandler("boolean primary"), nt("predicate"));
        grammar.addProduction(nt("boolean primary"), new TestProductionHandler("boolean primary"), left_paren, nt("search condition"), right_paren);
        
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("comparison predicate"));
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("between predicate"));
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("in predicate"));
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("like predicate"));
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("null predicate"));
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("qualified comparison predicate"));
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("exists predicate"));
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("match predicate"));
        grammar.addProduction(nt("predicate"), new TestProductionHandler("predicate"), nt("overlaps predicate"));
        
        grammar.addProduction(nt("comparison predicate"), new TestProductionHandler("comparison predicate"), nt("row value constructor"), nt("comp op"), nt("row value constructor"));
        
        grammar.addProduction(nt("row value constructor"), new TestProductionHandler("row value constructor"), nt("row value constructor element"));
        grammar.addProduction(nt("row value constructor"), new TestProductionHandler("row value constructor"), left_paren, nt("row value constructor list"), right_paren);
        grammar.addProduction(nt("row value constructor"), new TestProductionHandler("row value constructor"), nt("row subuery"));
        
        grammar.addProduction(nt("row value constructor element"), new TestProductionHandler("row value constructor element"), nt("value expression"));
        grammar.addProduction(nt("row value constructor element"), new TestProductionHandler("row value constructor element"), nt("null specification"));
        grammar.addProduction(nt("row value constructor element"), new TestProductionHandler("row value constructor element"), nt("default specification"));
        
        grammar.addProduction(nt("value expression"), new TestProductionHandler("value expression"), nt("numeric value expression"));
        grammar.addProduction(nt("value expression"), new TestProductionHandler("value expression"), nt("string value expression"));
        grammar.addProduction(nt("value expression"), new TestProductionHandler("value expression"), nt("datetime value expression"));
        grammar.addProduction(nt("value expression"), new TestProductionHandler("value expression"), nt("interval value expression"));
        
        grammar.addProduction(nt("numeric value expression"), new TestProductionHandler("numeric value expression"), nt("term"));
        grammar.addProduction(nt("numeric value expression"), new TestProductionHandler("numeric value expression"), nt("numeric value expression"), plus_sign, nt("term"));
        grammar.addProduction(nt("numeric value expression"), new TestProductionHandler("numeric value expression"), nt("numeric value expression"), minus_sign, nt("term"));
        
        grammar.addProduction(nt("term"), new TestProductionHandler("term"), nt("factor"));
        grammar.addProduction(nt("term"), new TestProductionHandler("term"), nt("term"), asterisk, nt("factor"));
        grammar.addProduction(nt("term"), new TestProductionHandler("term"), nt("term"), solidus, nt("factor"));
        
        grammar.addProduction(nt("factor"), new TestProductionHandler("factor"), nt("numeric primary"));
        grammar.addProduction(nt("factor"), new TestProductionHandler("factor"), nt("sign"), nt("numeric primary"));
        
        grammar.addProduction(nt("numeric primary"), new TestProductionHandler("numeric primary"), nt("value expression primary"));
        grammar.addProduction(nt("numeric primary"), new TestProductionHandler("numeric primary"), nt("numeric value function"));
        
        grammar.addProduction(nt("value expression primary"), new TestProductionHandler("value expression primary"), nt("unsigned value specification"));
        grammar.addProduction(nt("value expression primary"), new TestProductionHandler("value expression primary"), nt("column reference"));
        grammar.addProduction(nt("value expression primary"), new TestProductionHandler("value expression primary"), nt("set function specification"));
        grammar.addProduction(nt("value expression primary"), new TestProductionHandler("value expression primary"), nt("scalar subquery"));
        grammar.addProduction(nt("value expression primary"), new TestProductionHandler("value expression primary"), nt("case expression"));
        grammar.addProduction(nt("value expression primary"), new TestProductionHandler("value expression primary"), left_paren, nt("value expression"), right_paren);
        grammar.addProduction(nt("value expression primary"), new TestProductionHandler("value expression primary"), nt("cast specification"));
        
        grammar.addProduction(nt("unsigned value specification"), new TestProductionHandler("unsigned value specification"), nt("unsigned literal"));
        grammar.addProduction(nt("unsigned value specification"), new TestProductionHandler("unsigned value specification"), nt("general value specification"));
        
        grammar.addProduction(nt("unsigned literal"), new TestProductionHandler("unsigned literal"), nt("unsigned numeric literal"));
        grammar.addProduction(nt("unsigned literal"), new TestProductionHandler("unsigned literal"), nt("general literal"));
        
        grammar.addProduction(nt("general value specification"), new TestProductionHandler("general value specification"), nt("parameter specification"));
        grammar.addProduction(nt("general value specification"), new TestProductionHandler("general value specification"), nt("dynamic parameter specification"));
        grammar.addProduction(nt("general value specification"), new TestProductionHandler("general value specification"), nt("variable specification"));
        grammar.addProduction(nt("general value specification"), new TestProductionHandler("general value specification"), USER);
        grammar.addProduction(nt("general value specification"), new TestProductionHandler("general value specification"), CURRENT_USER);
        grammar.addProduction(nt("general value specification"), new TestProductionHandler("general value specification"), SESSION_USER);
        grammar.addProduction(nt("general value specification"), new TestProductionHandler("general value specification"), SYSTEM_USER);
        grammar.addProduction(nt("general value specification"), new TestProductionHandler("general value specification"), VALUE);
        
        grammar.addProduction(nt("parameter specification"), new TestProductionHandler("parameter specification"), nt("parameter name"));
        grammar.addProduction(nt("parameter specification"), new TestProductionHandler("parameter specification"), nt("parameter name"), nt("indicator parameter"));
        
        grammar.addProduction(nt("parameter name"), new TestProductionHandler("parameter name"), colon, nt("identifier"));
        
        grammar.addProduction(nt("indicator parameter"), new TestProductionHandler("indicator parameter"), nt("parameter name"));
        grammar.addProduction(nt("indicator parameter"), new TestProductionHandler("indicator parameter"), INDICATOR, nt("parameter name"));
        
        grammar.addProduction(nt("dynamic parameter specification"), new TestProductionHandler("dynamic parameter specification"), question_mark);
        
        grammar.addProduction(nt("variable specification"), new TestProductionHandler("variable specification"), nt("embedded variable name"));
        grammar.addProduction(nt("variable specification"), new TestProductionHandler("variable specification"), nt("embedded variable name"), nt("indicator variable"));
        
        grammar.addProduction(nt("embedded variable name"), new TestProductionHandler("embedded variable name"), colon, nt("host identifier"));
        
        grammar.addProduction(nt("host identifier"), new TestProductionHandler("host identifier"), nt("Ada host identifier"));
        grammar.addProduction(nt("host identifier"), new TestProductionHandler("host identifier"), nt("C host identifier"));
        grammar.addProduction(nt("host identifier"), new TestProductionHandler("host identifier"), nt("Cobol host identifier"));
        grammar.addProduction(nt("host identifier"), new TestProductionHandler("host identifier"), nt("Fortran host identifier"));
        grammar.addProduction(nt("host identifier"), new TestProductionHandler("host identifier"), nt("MUMPS host identifier"));
        grammar.addProduction(nt("host identifier"), new TestProductionHandler("host identifier"), nt("Pascal host identifier"));
        grammar.addProduction(nt("host identifier"), new TestProductionHandler("host identifier"), nt("PL/I host identifier"));
        
//        grammar.addProduction(nt("Ada host identifier"), new TestProductionHandler("Ada host identifier"));
//        
//        grammar.addProduction(nt("C host identifier"), new TestProductionHandler("C host identifier"));
//        
//        grammar.addProduction(nt("Cobol host identifier"), new TestProductionHandler("Cobol host identifier"));
//        
//        grammar.addProduction(nt("Fortran host identifier"), new TestProductionHandler("Fortran host identifier"));
//        
//        grammar.addProduction(nt("MUMPS host identifier"), new TestProductionHandler("MUMPS host identifier"));
//        
//        grammar.addProduction(nt("Pascal host identifier"), new TestProductionHandler("Pascal host identifier"));
//        
//        grammar.addProduction(nt("PL/I host identifier"), new TestProductionHandler("PL/I host identifier"));
        
        grammar.addProduction(nt("indicator variable"), new TestProductionHandler("indicator variable"), nt("embedded variable name"));
        grammar.addProduction(nt("indicator variable"), new TestProductionHandler("indicator variable"), INDICATOR, nt("embedded variable name"));
        
        grammar.addProduction(nt("column reference"), new TestProductionHandler("column reference"), nt("column name"));
        grammar.addProduction(nt("column reference"), new TestProductionHandler("column reference"), nt("qualifier"), period, nt("column name"));
        
        grammar.addProduction(nt("qualifier"), new TestProductionHandler("qualifier"), nt("table name"));
        grammar.addProduction(nt("qualifier"), new TestProductionHandler("qualifier"), nt("correlation name"));
        
        grammar.addProduction(nt("correlation name"), new TestProductionHandler("correlation name"), nt("identifier"));
        
        grammar.addProduction(nt("set function specification"), new TestProductionHandler("set function specification"), COUNT, left_paren, asterisk, right_paren);
        grammar.addProduction(nt("set function specification"), new TestProductionHandler("set function specification"), nt("general set function"));
        
        grammar.addProduction(nt("general set function"), new TestProductionHandler("general set function"), nt("set function type"), left_paren, nt("value expression"), right_paren);
        grammar.addProduction(nt("general set function"), new TestProductionHandler("general set function"), nt("set function type"), left_paren, nt("set quantifier"), nt("value expression"), right_paren);
        
        grammar.addProduction(nt("set function type"), new TestProductionHandler("set function type"), AVG);
        grammar.addProduction(nt("set function type"), new TestProductionHandler("set function type"), MAX);
        grammar.addProduction(nt("set function type"), new TestProductionHandler("set function type"), MIN);
        grammar.addProduction(nt("set function type"), new TestProductionHandler("set function type"), SUM);
        grammar.addProduction(nt("set function type"), new TestProductionHandler("set function type"), COUNT);
        
        grammar.addProduction(nt("set quantifier"), new TestProductionHandler("set quantifier"), DISTINCT);
        grammar.addProduction(nt("set quantifier"), new TestProductionHandler("set quantifier"), ALL);
        
        /*
         * Queries
         */
        grammar.addProduction(nt("scalar subquery"), new TestProductionHandler("scalar subquery"), nt("subquery"));
        
        grammar.addProduction(nt("subquery"), new TestProductionHandler("subquery"), left_paren, nt("query expression"), right_paren);
        
        grammar.addProduction(nt("query expression"), new TestProductionHandler("query expression"), nt("non-join query expression"));
        grammar.addProduction(nt("query expression"), new TestProductionHandler("query expression"), nt("joined table"));
        
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("non-join query term"));
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("query expression"), UNION, nt("query term"));
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("query expression"), UNION, ALL, nt("query term"));
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("query expression"), UNION, nt("corresponding spec"), nt("query term"));
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("query expression"), UNION, ALL, nt("corresponding spec"), nt("query term"));
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("query expression"), EXCEPT, nt("query term"));
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("query expression"), EXCEPT, ALL, nt("query term"));
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("query expression"), EXCEPT, nt("corresponding spec"), nt("query term"));
        grammar.addProduction(nt("non-join query expression"), new TestProductionHandler("non-join query expression"), nt("query expression"), EXCEPT, ALL, nt("corresponding spec"), nt("query term"));
        
        grammar.addProduction(nt("non-join query term"), new TestProductionHandler("non-join query term"), nt("non-join query primary"));
        grammar.addProduction(nt("non-join query term"), new TestProductionHandler("non-join query term"), nt("query term"), INTERSECT, nt("query primary"));
        grammar.addProduction(nt("non-join query term"), new TestProductionHandler("non-join query term"), nt("query term"), INTERSECT, ALL, nt("query primary"));
        grammar.addProduction(nt("non-join query term"), new TestProductionHandler("non-join query term"), nt("query term"), INTERSECT, nt("corresponding spec"), nt("query primary"));
        grammar.addProduction(nt("non-join query term"), new TestProductionHandler("non-join query term"), nt("query term"), INTERSECT, ALL, nt("corresponding spec"), nt("query primary"));
        
        grammar.addProduction(nt("non-join query primary"), new TestProductionHandler("non-join query primary"), nt("simple table"));
        grammar.addProduction(nt("non-join query primary"), new TestProductionHandler("non-join query primary"), left_paren, nt("non-join query expression"), right_paren);
        
        grammar.addProduction(nt("simple table"), new TestProductionHandler("simple table"), nt("query specification"));
        grammar.addProduction(nt("simple table"), new TestProductionHandler("simple table"), nt("table value constructor"));
        grammar.addProduction(nt("simple table"), new TestProductionHandler("simple table"), nt("explicit table"));
        
        grammar.addProduction(nt("query specification"), new TestProductionHandler("query specification"), SELECT, nt("select list"), nt("table expression"));
        grammar.addProduction(nt("query specification"), new TestProductionHandler("query specification"), SELECT, nt("set quantifier"), nt("select list"), nt("table expression"));
        
        grammar.addProduction(nt("select list"), new TestProductionHandler("select list"), asterisk);
        grammar.addProduction(nt("select list"), new TestProductionHandler("select list"), nt("select sublist"));
        grammar.addProduction(nt("select list"), new TestProductionHandler("select list"), nt("select sublist"), comma, nt("select sublist"));
        // TODO: loop
        
        grammar.addProduction(nt("select sublist"), new TestProductionHandler("select sublist"), nt("derived column"));
        grammar.addProduction(nt("select sublist"), new TestProductionHandler("select sublist"), nt("qualifier"), period, asterisk);
        
        grammar.addProduction(nt("derived column"), new TestProductionHandler("derived column"), nt("value expression"));
        grammar.addProduction(nt("derived column"), new TestProductionHandler("derived column"), nt("value expression"), nt("as clause"));
        
        grammar.addProduction(nt("as clause"), new TestProductionHandler("as clause"), nt("column name"));
        grammar.addProduction(nt("as clause"), new TestProductionHandler("as clause"), AS, nt("column name"));
        
        grammar.addProduction(nt("table expression"), new TestProductionHandler("table expression"), nt("from clause"));
        grammar.addProduction(nt("table expression"), new TestProductionHandler("table expression"), nt("from clause"), nt("where clause"));
        grammar.addProduction(nt("table expression"), new TestProductionHandler("table expression"), nt("from clause"), nt("group by clause"));
        grammar.addProduction(nt("table expression"), new TestProductionHandler("table expression"), nt("from clause"), nt("where clause"), nt("group by clause"));
        grammar.addProduction(nt("table expression"), new TestProductionHandler("table expression"), nt("from clause"), nt("having clause"));
        grammar.addProduction(nt("table expression"), new TestProductionHandler("table expression"), nt("from clause"), nt("where clause"), nt("having clause"));
        grammar.addProduction(nt("table expression"), new TestProductionHandler("table expression"), nt("from clause"), nt("group by clause"), nt("having clause"));
        grammar.addProduction(nt("table expression"), new TestProductionHandler("table expression"), nt("from clause"), nt("where clause"), nt("group by clause"), nt("having clause"));
        
        grammar.addProduction(nt("from clause"), new TestProductionHandler("from clause"), FROM, nt("table reference"));
        grammar.addProduction(nt("from clause"), new TestProductionHandler("from clause"), FROM, nt("table reference"), comma, nt("table reference"));
        // TODO: loop
        
        grammar.addProduction(nt("table reference"), new TestProductionHandler("table reference"), nt("table name"));
        grammar.addProduction(nt("table reference"), new TestProductionHandler("table reference"), nt("table name"), nt("correlation specification"));
        grammar.addProduction(nt("table reference"), new TestProductionHandler("table reference"), nt("derived name"), nt("correlation specification"));
        grammar.addProduction(nt("table reference"), new TestProductionHandler("table reference"), nt("joined table"));
        
        grammar.addProduction(nt("correlation specification"), new TestProductionHandler("correlation specification"), nt("correlation name"));
        grammar.addProduction(nt("correlation specification"), new TestProductionHandler("correlation specification"), AS, nt("correlation name"));
        grammar.addProduction(nt("correlation specification"), new TestProductionHandler("correlation specification"), nt("correlation name"), left_paren, nt("derived column list"), right_paren);
        grammar.addProduction(nt("correlation specification"), new TestProductionHandler("correlation specification"), AS, nt("correlation name"), left_paren, nt("derived column list"), right_paren);
        
        grammar.addProduction(nt("derived column list"), new TestProductionHandler("derived column list"), nt("column name list"));
        
        grammar.addProduction(nt("derived table"), new TestProductionHandler("derived table"), nt("table subquery"));
        
        grammar.addProduction(nt("table subquery"), new TestProductionHandler("table subquery"), nt("subquery"));
        
        grammar.addProduction(nt("joined table"), new TestProductionHandler("joined table"), nt("cross join"));
        grammar.addProduction(nt("joined table"), new TestProductionHandler("joined table"), nt("qualified join"));
        grammar.addProduction(nt("joined table"), new TestProductionHandler("joined table"), left_paren, nt("joined table"), right_paren);
        
        grammar.addProduction(nt("cross join"), new TestProductionHandler("cross join"), nt("table reference"), CROSS, JOIN, nt("table reference"));
        
        grammar.addProduction(nt("qualified join"), new TestProductionHandler("qualified join"), nt("table reference"), JOIN, nt("table reference"));
        grammar.addProduction(nt("qualified join"), new TestProductionHandler("qualified join"), nt("table reference"), NATURAL, JOIN, nt("table reference"));
        grammar.addProduction(nt("qualified join"), new TestProductionHandler("qualified join"), nt("table reference"), nt("join type"), JOIN, nt("table reference"));
        grammar.addProduction(nt("qualified join"), new TestProductionHandler("qualified join"), nt("table reference"), NATURAL, nt("join type"), JOIN, nt("table reference"));
        grammar.addProduction(nt("qualified join"), new TestProductionHandler("qualified join"), nt("table reference"), JOIN, nt("table reference"), nt("join specification"));
        grammar.addProduction(nt("qualified join"), new TestProductionHandler("qualified join"), nt("table reference"), NATURAL, JOIN, nt("table reference"), nt("join specification"));
        grammar.addProduction(nt("qualified join"), new TestProductionHandler("qualified join"), nt("table reference"), nt("join type"), JOIN, nt("table reference"), nt("join specification"));
        grammar.addProduction(nt("qualified join"), new TestProductionHandler("qualified join"), nt("table reference"), NATURAL, nt("join type"), JOIN, nt("table reference"), nt("join specification"));
        
        grammar.addProduction(nt("join type"), new TestProductionHandler("join type"), INNER);
        grammar.addProduction(nt("join type"), new TestProductionHandler("join type"), nt("outer join type"));
        grammar.addProduction(nt("join type"), new TestProductionHandler("join type"), nt("outer join type"), OUTER);
        grammar.addProduction(nt("join type"), new TestProductionHandler("join type"), UNION);
        
        grammar.addProduction(nt("outer join type"), new TestProductionHandler("outer join type"), LEFT);
        grammar.addProduction(nt("outer join type"), new TestProductionHandler("outer join type"), RIGHT);
        grammar.addProduction(nt("outer join type"), new TestProductionHandler("outer join type"), FULL);
        
        grammar.addProduction(nt("join specification"), new TestProductionHandler("join specification"), nt("join condition"));
        grammar.addProduction(nt("join specification"), new TestProductionHandler("join specification"), nt("named columns join"));
        
        grammar.addProduction(nt("join condition"), new TestProductionHandler("join condition"), ON, nt("search condition"));
        
        grammar.addProduction(nt("named columns join"), new TestProductionHandler("named columns join"), USING, left_paren, nt("named column list"), right_paren);
        
        grammar.addProduction(nt("join column list"), new TestProductionHandler("join column list"), nt("column name list"));
        
        grammar.addProduction(nt("where clause"), new TestProductionHandler("where clause"), WHERE, nt("search condition"));
        
        grammar.addProduction(nt("group by clause"), new TestProductionHandler("group by clause"), GROUP, BY, nt("grouping column reference list"));
        
        grammar.addProduction(nt("grouping column reference list"), new TestProductionHandler("grouping column reference list"), nt("grouping column reference"));
        grammar.addProduction(nt("grouping column reference list"), new TestProductionHandler("grouping column reference list"), nt("grouping column reference"), comma, nt("grouping column reference"));
        // TODO: loop
        
        grammar.addProduction(nt("grouping column reference"), new TestProductionHandler("grouping column reference"), nt("column reference"));
        grammar.addProduction(nt("grouping column reference"), new TestProductionHandler("grouping column reference"), nt("column reference"), nt("collate clause"));
        
        grammar.addProduction(nt("collate clause"), new TestProductionHandler("collate clause"), COLLATE, nt("collation name"));
        
        grammar.addProduction(nt("collation name"), new TestProductionHandler("collation name"), nt("qualified name"));
        
        grammar.addProduction(nt("having clause"), new TestProductionHandler("having clause"), HAVING, nt("search condition"));
        
        grammar.addProduction(nt("table value constructor"), new TestProductionHandler("table value constructor"), VALUES, nt("table value constructor list"));
        
        grammar.addProduction(nt("table value constructor list"), new TestProductionHandler("table value constructor list"), nt("row value constructor"));
        grammar.addProduction(nt("table value constructor list"), new TestProductionHandler("table value constructor list"), nt("row value constructor"), comma, nt("row value constructor"));
        // TODO: loop
        
        grammar.addProduction(nt("explicit table"), new TestProductionHandler("explicit table"), TABLE, nt("table name"));
        
        grammar.addProduction(nt("query term"), new TestProductionHandler("query term"), nt("non-join query term"));
        grammar.addProduction(nt("query term"), new TestProductionHandler("query term"), nt("joined table"));
        
        grammar.addProduction(nt("corresponding spec"), new TestProductionHandler("corresponding spec"), CORRESPONDING);
        grammar.addProduction(nt("corresponding spec"), new TestProductionHandler("corresponding spec"), CORRESPONDING, BY, left_paren, nt("corresponding column list"), right_paren);
        
        grammar.addProduction(nt("corresponding column list"), new TestProductionHandler("corresponding column list"), nt("column name list"));
        
        grammar.addProduction(nt("query primary"), new TestProductionHandler("query primary"), nt("non-join query primary"));
        grammar.addProduction(nt("query primary"), new TestProductionHandler("query primary"), nt("joined table"));
        
        /*
         * Query expression components
         */
        grammar.addProduction(nt("case expression"), new TestProductionHandler("case expression"), nt("case abbreviation"));
        grammar.addProduction(nt("case expression"), new TestProductionHandler("case expression"), nt("case specification"));
        
        grammar.addProduction(nt("case abbreviation"), new TestProductionHandler("case abbreviation"), NULLIF, left_paren, nt("value expression"), comma, nt("value expression"), right_paren);
        grammar.addProduction(nt("case abbreviation"), new TestProductionHandler("case abbreviation"), COALESCE, left_paren, nt("value expression"), right_paren);
        grammar.addProduction(nt("case abbreviation"), new TestProductionHandler("case abbreviation"), COALESCE, left_paren, nt("value expression"), comma, nt("value expression"), right_paren);
        // TODO: loop
        
        grammar.addProduction(nt("case specification"), new TestProductionHandler("case specification"), nt("simple case"));
        grammar.addProduction(nt("case specification"), new TestProductionHandler("case specification"), nt("searched case"));
        
        grammar.addProduction(nt("simple case"), new TestProductionHandler("simple case"), CASE, nt("case operand"), nt("simple when clause"), END);
        grammar.addProduction(nt("simple case"), new TestProductionHandler("simple case"), CASE, nt("case operand"), nt("simple when clause"), nt("else clause"), END);
        // TODO: loop
        
        grammar.addProduction(nt("case operand"), new TestProductionHandler("case operand"), nt("value expression"));
        
        grammar.addProduction(nt("simple when clause"), new TestProductionHandler("simple when clause"), WHEN, nt("when operand"), THEN, nt("result"));
        
        grammar.addProduction(nt("when operand"), new TestProductionHandler("when operand"), nt("value expression"));
        
        grammar.addProduction(nt("result"), new TestProductionHandler("result"), nt("result expression"));
        grammar.addProduction(nt("result"), new TestProductionHandler("result"), NULL);
        
        grammar.addProduction(nt("result expression"), new TestProductionHandler("result expression"), nt("value expression"));
        
        grammar.addProduction(nt("else clause"), new TestProductionHandler("else clause"), ELSE, nt("result"));
        
        grammar.addProduction(nt("searched case"), new TestProductionHandler("searched case"), CASE, nt("searched when clause"), END);
        grammar.addProduction(nt("searched case"), new TestProductionHandler("searched case"), CASE, nt("searched when clause"), nt("else clause"), END);
        // TODO: loop
        
        grammar.addProduction(nt("searched when clause"), new TestProductionHandler("searched when clause"), WHEN, nt("search condition"), THEN, nt("result"));
        
        grammar.addProduction(nt("cast specification"), new TestProductionHandler("cast specification"), CAST, left_paren, nt("cast operand"), AS, nt("cast target"), right_paren);
        
        grammar.addProduction(nt("cast operand"), new TestProductionHandler("cast operand"), nt("value expression"));
        grammar.addProduction(nt("cast operand"), new TestProductionHandler("cast operand"), NULL);
        
        grammar.addProduction(nt("cast target"), new TestProductionHandler("cast target"), nt("domain name"));
        grammar.addProduction(nt("cast target"), new TestProductionHandler("cast target"), nt("data type"));
        
        grammar.addProduction(nt("numeric value function"), new TestProductionHandler("numeric value function"), nt("position expression"));
        grammar.addProduction(nt("numeric value function"), new TestProductionHandler("numeric value function"), nt("extract expression"));
        grammar.addProduction(nt("numeric value function"), new TestProductionHandler("numeric value function"), nt("length expression"));
        
        grammar.addProduction(nt("position expression"), new TestProductionHandler("position expression"), POSITION, left_paren, nt("character value expression"), IN, nt("character value expression"), right_paren);
        
        grammar.addProduction(nt("character value expression"), new TestProductionHandler("character value expression"), nt("concatenation"));
        grammar.addProduction(nt("character value expression"), new TestProductionHandler("character value expression"), nt("character factor"));
        
        grammar.addProduction(nt("concatenation"), new TestProductionHandler("concatenation"), nt("character value expression"), nt("concatenation operator"), nt("character factor"));
        
        grammar.addProduction(nt("character factor"), new TestProductionHandler("character factor"), nt("character primary"));
        grammar.addProduction(nt("character factor"), new TestProductionHandler("character factor"), nt("character primary"), nt("collate clause"));
        
        grammar.addProduction(nt("character primary"), new TestProductionHandler("character primary"), nt("value expression"));
        grammar.addProduction(nt("character primary"), new TestProductionHandler("character primary"), nt("string value function"));
        
        grammar.addProduction(nt("string value function"), new TestProductionHandler("string value function"), nt("character value function"));
        grammar.addProduction(nt("string value function"), new TestProductionHandler("string value function"), nt("bit value function"));
        
        grammar.addProduction(nt("character value function"), new TestProductionHandler("character value function"), nt("character substring function"));
        grammar.addProduction(nt("character value function"), new TestProductionHandler("character value function"), nt("fold"));
        grammar.addProduction(nt("character value function"), new TestProductionHandler("character value function"), nt("form-of-use conversion"));
        grammar.addProduction(nt("character value function"), new TestProductionHandler("character value function"), nt("character translation"));
        grammar.addProduction(nt("character value function"), new TestProductionHandler("character value function"), nt("trim function"));
        
        grammar.addProduction(nt("character substring function"), new TestProductionHandler("character substring function"), SUBSTRING, left_paren, nt("character value expression"), FROM, nt("start position"), right_paren);
        grammar.addProduction(nt("character substring function"), new TestProductionHandler("character substring function"), SUBSTRING, left_paren, nt("character value expression"), FROM, nt("start position"), FOR, nt("string length"), right_paren);
        
        grammar.addProduction(nt("start position"), new TestProductionHandler("start position"), nt("numeric value expression"));
        
        grammar.addProduction(nt("string length"), new TestProductionHandler("string length"), nt("numeric value expression"));
        
        grammar.addProduction(nt("fold"), new TestProductionHandler("fold"), UPPER, left_paren, nt("character value expression"), right_paren);
        grammar.addProduction(nt("fold"), new TestProductionHandler("fold"), LOWER, left_paren, nt("character value expression"), right_paren);
        
        grammar.addProduction(nt("form-of-use conversion"), new TestProductionHandler("form-of-use conversion"), CONVERT, left_paren, nt("character value expression"), USING, nt("form-of-use conversion name"), right_paren);
        
        grammar.addProduction(nt("form-of-use conversion name"), new TestProductionHandler("form-of-use conversion name"), nt("qualified name"));
        
        grammar.addProduction(nt("character translation"), new TestProductionHandler("character translation"), TRANSLATE, left_paren, nt("character value expression"), USING, nt("translation name"), right_paren);
        
        grammar.addProduction(nt("translation name"), new TestProductionHandler("translation name"), nt("qualified name"));
        
        grammar.addProduction(nt("trim function"), new TestProductionHandler("trim function"), TRIM, left_paren, nt("trim operands"), right_paren);
        
        grammar.addProduction(nt("trim operands"), new TestProductionHandler("trim operands"), nt("trim source"));
        grammar.addProduction(nt("trim operands"), new TestProductionHandler("trim operands"), FROM, nt("trim source"));
        grammar.addProduction(nt("trim operands"), new TestProductionHandler("trim operands"), nt("trim character"), FROM, nt("trim source"));
        grammar.addProduction(nt("trim operands"), new TestProductionHandler("trim operands"), nt("trim specification"), FROM, nt("trim source"));
        grammar.addProduction(nt("trim operands"), new TestProductionHandler("trim operands"), nt("trim specification"), nt("trim character"), FROM, nt("trim source"));
        
        grammar.addProduction(nt("trim specification"), new TestProductionHandler("trim specification"), LEADING);
        grammar.addProduction(nt("trim specification"), new TestProductionHandler("trim specification"), TRAILING);
        grammar.addProduction(nt("trim specification"), new TestProductionHandler("trim specification"), BOTH);
        
        grammar.addProduction(nt("trim character"), new TestProductionHandler("trim character"), nt("character value expression"));
        
        grammar.addProduction(nt("trim source"), new TestProductionHandler("trim source"), nt("character value expression"));
        
        grammar.addProduction(nt("bit value function"), new TestProductionHandler("bit value function"), nt("bit substring function"));
        
        grammar.addProduction(nt("bit substring function"), new TestProductionHandler("bit substring function"), SUBSTRING, left_paren, nt("bit value expression"), FROM, nt("start position"), right_paren);
        grammar.addProduction(nt("bit substring function"), new TestProductionHandler("bit substring function"), SUBSTRING, left_paren, nt("bit value expression"), FROM, nt("start position"), FOR, nt("string length"), right_paren);
        
        grammar.addProduction(nt("bit value expression"), new TestProductionHandler("bit value expression"), nt("bit concatenation"));
        grammar.addProduction(nt("bit value expression"), new TestProductionHandler("bit value expression"), nt("bit factor"));
        
        grammar.addProduction(nt("bit concatenation"), new TestProductionHandler("bit concatenation"), nt("bit value expression"), nt("concatenation operator"), nt("bit factor"));
        
        grammar.addProduction(nt("bit factor"), new TestProductionHandler("bit factor"), nt("bit primary"));
        
        grammar.addProduction(nt("bit primary"), new TestProductionHandler("bit primary"), nt("value expression primary"));
        grammar.addProduction(nt("bit primary"), new TestProductionHandler("bit primary"), nt("string value function"));
        
        grammar.addProduction(nt("extract expression"), new TestProductionHandler("extract expression"), EXTRACT, left_paren, nt("extract field"), FROM, nt("extract source"), right_paren);
        
        grammar.addProduction(nt("extract field"), new TestProductionHandler("extract field"), nt("datetime field"));
        grammar.addProduction(nt("extract field"), new TestProductionHandler("extract field"), nt("time zone field"));
        
        grammar.addProduction(nt("datetime field"), new TestProductionHandler("datetime field"), nt("non-second datetime field"));
        grammar.addProduction(nt("datetime field"), new TestProductionHandler("datetime field"), SECOND);
        
        grammar.addProduction(nt("time zone field"), new TestProductionHandler("time zone field"), TIMEZONE_HOUR);
        grammar.addProduction(nt("time zone field"), new TestProductionHandler("time zone field"), TIMEZONE_MINUTE);
        
        grammar.addProduction(nt("extract source"), new TestProductionHandler("extract source"), nt("datetime value expression"));
        grammar.addProduction(nt("extract source"), new TestProductionHandler("extract source"), nt("interval value expression"));
        
        grammar.addProduction(nt("datetime value expression"), new TestProductionHandler("datetime value expression"), nt("datetime term"));
        grammar.addProduction(nt("datetime value expression"), new TestProductionHandler("datetime value expression"), nt("interval value expression"), plus_sign, nt("datetime term"));
        grammar.addProduction(nt("datetime value expression"), new TestProductionHandler("datetime value expression"), nt("datetime value expression"), plus_sign, nt("interval term"));
        grammar.addProduction(nt("datetime value expression"), new TestProductionHandler("datetime value expression"), nt("datetime value expression"), minus_sign, nt("interval term"));
        
        grammar.addProduction(nt("interval term"), new TestProductionHandler("interval term"), nt("interval factor"));
        grammar.addProduction(nt("interval term"), new TestProductionHandler("interval term"), nt("interval term 2"), asterisk, nt("factor"));
        grammar.addProduction(nt("interval term"), new TestProductionHandler("interval term"), nt("interval term 2"), solidus, nt("factor"));
        grammar.addProduction(nt("interval term"), new TestProductionHandler("interval term"), nt("term"), asterisk, nt("interval factor"));
        
        grammar.addProduction(nt("interval factor"), new TestProductionHandler("interval factor"), nt("interval primary"));
        grammar.addProduction(nt("interval factor"), new TestProductionHandler("interval factor"), nt("sign"), nt("interval primary"));
        
        grammar.addProduction(nt("interval primary"), new TestProductionHandler("interval primary"), nt("value expression primary"));
        grammar.addProduction(nt("interval primary"), new TestProductionHandler("interval primary"), nt("value expression primary"), nt("interval qualifier"));
        
        grammar.addProduction(nt("interval term 2"), new TestProductionHandler("interval term 2"), nt("interval term"));
        
        grammar.addProduction(nt("interval value expression"), new TestProductionHandler("internal value expression"), nt("interval term"));
        grammar.addProduction(nt("interval value expression"), new TestProductionHandler("internal value expression"), nt("interval value expression 1"), plus_sign, nt("interval term 1"));
        grammar.addProduction(nt("interval value expression"), new TestProductionHandler("internal value expression"), nt("interval value expression 1"), minus_sign, nt("interval term 1"));
        grammar.addProduction(nt("interval value expression"), new TestProductionHandler("internal value expression"), left_paren, nt("datetime value expression"), minus_sign, nt("datetime term"), right_paren, nt("interval qualifier"));
        
        grammar.addProduction(nt("interval value expression 1"), new TestProductionHandler("interval value expression 1"), nt("interval value expression"));
        
        grammar.addProduction(nt("interval term 1"), new TestProductionHandler("interval term 1"), nt("interval term"));
        
        grammar.addProduction(nt("datetime term"), new TestProductionHandler("datetime term"), nt("datetime factor"));
        
        grammar.addProduction(nt("datetime factor"), new TestProductionHandler("datetime factor"), nt("datetime primary"));
        grammar.addProduction(nt("datetime factor"), new TestProductionHandler("datetime factor"), nt("datetime primary"), nt("time zone"));
        
        grammar.addProduction(nt("datetime primary"), new TestProductionHandler("datetime primary"), nt("value expression primary"));
        grammar.addProduction(nt("datetime primary"), new TestProductionHandler("datetime primary"), nt("datetime value function"));
        
        grammar.addProduction(nt("time zone"), new TestProductionHandler("time zone"), AT, nt("time zone specifier"));
        
        grammar.addProduction(nt("time zone specifier"), new TestProductionHandler("time zone specifier"), LOCAL);
        grammar.addProduction(nt("time zone specifier"), new TestProductionHandler("time zone specifier"), TIME, ZONE, nt("interval value expression"));
        
        grammar.addProduction(nt("length expression"), new TestProductionHandler("length expression"), nt("char length expression"));
        grammar.addProduction(nt("length expression"), new TestProductionHandler("length expression"), nt("octet length expression"));
        grammar.addProduction(nt("length expression"), new TestProductionHandler("length expression"), nt("bit length expression"));
        
        grammar.addProduction(nt("char length expression"), new TestProductionHandler("char length expression"), CHAR_LENGTH, left_paren, nt("string value expression"), right_paren);
        grammar.addProduction(nt("char length expression"), new TestProductionHandler("char length expression"), CHARACTER_LENGTH, left_paren, nt("string value expression"), right_paren);
        
        grammar.addProduction(nt("string value expression"), new TestProductionHandler("string value expression"), nt("character value expression"));
        grammar.addProduction(nt("string value expression"), new TestProductionHandler("string value expression"), nt("bit value expression"));
        
        grammar.addProduction(nt("octet length expression"), new TestProductionHandler("octet length expression"), OCTET_LENGTH, left_paren, nt("string value expression"), right_paren);
        
        grammar.addProduction(nt("bit length expression"), new TestProductionHandler("bit length expression"), BIT_LENGTH, left_paren, nt("string value expression"), right_paren);
        
        grammar.addProduction(nt("null specification"), new TestProductionHandler("null specification"), NULL);
        
        grammar.addProduction(nt("default specification"), new TestProductionHandler("default specification"), DEFAULT);
        
        grammar.addProduction(nt("row value constructor list"), new TestProductionHandler("row value constructor list"), nt("row value constructor element"));
        grammar.addProduction(nt("row value constructor list"), new TestProductionHandler("row value constructor list"), nt("row value constructor element"), comma, nt("row value constructor element"));
        // TODO: list
        
        grammar.addProduction(nt("row subquery"), new TestProductionHandler("row subquery"), nt("subquery"));
        
        grammar.addProduction(nt("comp op"), new TestProductionHandler("comp op"), equals_operator);
        grammar.addProduction(nt("comp op"), new TestProductionHandler("comp op"), not_equals_operator);
        grammar.addProduction(nt("comp op"), new TestProductionHandler("comp op"), less_than_operator);
        grammar.addProduction(nt("comp op"), new TestProductionHandler("comp op"), greater_than_operator);
        grammar.addProduction(nt("comp op"), new TestProductionHandler("comp op"), less_than_or_equals_operator);
        grammar.addProduction(nt("comp op"), new TestProductionHandler("comp op"), greater_than_or_equals_operator);
        
        grammar.addProduction(nt("between predicate"), new TestProductionHandler("between predicate"), nt("row value constructor"), BETWEEN, nt("row value constructor"), AND, nt("row value constructor"));
        grammar.addProduction(nt("between predicate"), new TestProductionHandler("between predicate"), nt("row value constructor"), NOT, BETWEEN, nt("row value constructor"), AND, nt("row value constructor"));
        
        grammar.addProduction(nt("in predicate"), new TestProductionHandler("in predicate"), nt("table subquery"));
        grammar.addProduction(nt("in predicate"), new TestProductionHandler("in predicate"), left_paren, nt("in value list"), right_paren);
        
        grammar.addProduction(nt("in value list"), new TestProductionHandler("in value list"), nt("value expression"));
        grammar.addProduction(nt("in value list"), new TestProductionHandler("in value list"), nt("value expression"), comma, nt("value expression"));
        // TODO: loop
        
        grammar.addProduction(nt("like predicate"), new TestProductionHandler("like predicate"), nt("match value"), LIKE, nt("pattern"));
        grammar.addProduction(nt("like predicate"), new TestProductionHandler("like predicate"), nt("match value"), NOT, LIKE, nt("pattern"));
        grammar.addProduction(nt("like predicate"), new TestProductionHandler("like predicate"), nt("match value"), LIKE, nt("pattern"), ESCAPE, nt("escape character"));
        grammar.addProduction(nt("like predicate"), new TestProductionHandler("like predicate"), nt("match value"), NOT, LIKE, nt("pattern"), ESCAPE, nt("escape character"));
        
        grammar.addProduction(nt("match value"), new TestProductionHandler("match value"), nt("character value expression"));
        
        grammar.addProduction(nt("pattern"), new TestProductionHandler("pattern"), nt("character value expression"));
        
        grammar.addProduction(nt("escape character"), new TestProductionHandler("escape character"), nt("character value expression"));
        
        grammar.addProduction(nt("null predicate"), new TestProductionHandler("null predicate"), nt("row value constructor"), IS, NULL);
        grammar.addProduction(nt("null predicate"), new TestProductionHandler("null predicate"), nt("row value constructor"), IS, NOT, NULL);
        
        grammar.addProduction(nt("quantified comparison predicate"), new TestProductionHandler("quantified comparison predicate"), nt("row value constructor"), nt("comp op"), nt("quantifier"), nt("table subquery"));
        
        grammar.addProduction(nt("quantifier"), new TestProductionHandler("quantifier"), nt("all"));
        grammar.addProduction(nt("quantifier"), new TestProductionHandler("quantifier"), nt("some"));
        
        grammar.addProduction(nt("all"), new TestProductionHandler("all"), ALL);
        
        grammar.addProduction(nt("some"), new TestProductionHandler("some"), SOME);
        grammar.addProduction(nt("some"), new TestProductionHandler("some"), ANY);
        
        grammar.addProduction(nt("exists predicate"), new TestProductionHandler("exists predicate"), EXISTS, nt("table subquery"));
        
        grammar.addProduction(nt("unique predicate"), new TestProductionHandler("unique predicate"), UNIQUE, nt("table subquery"));
        
        grammar.addProduction(nt("match predicate"), new TestProductionHandler("match predicate"), nt("row value constructor"), MATCH, nt("table subquery"));
        grammar.addProduction(nt("match predicate"), new TestProductionHandler("match predicate"), nt("row value constructor"), MATCH, UNIQUE, nt("table subquery"));
        grammar.addProduction(nt("match predicate"), new TestProductionHandler("match predicate"), nt("row value constructor"), MATCH, PARTIAL, nt("table subquery"));
        grammar.addProduction(nt("match predicate"), new TestProductionHandler("match predicate"), nt("row value constructor"), MATCH, UNIQUE, PARTIAL, nt("table subquery"));
        grammar.addProduction(nt("match predicate"), new TestProductionHandler("match predicate"), nt("row value constructor"), MATCH, FULL, nt("table subquery"));
        grammar.addProduction(nt("match predicate"), new TestProductionHandler("match predicate"), nt("row value constructor"), MATCH, UNIQUE, FULL, nt("table subquery"));
        
        grammar.addProduction(nt("overlaps predicate"), new TestProductionHandler("overlaps predicate"), nt("row value constructor 1"), OVERLAPS, nt("row value constructor 2"));
        
        grammar.addProduction(nt("row value constructor 1"), new TestProductionHandler("row value constructor 1"), nt("row value constructor"));
        
        grammar.addProduction(nt("row value constructor 2"), new TestProductionHandler("row value constructor 2"), nt("row value constructor"));
        
        grammar.addProduction(nt("truth value"), new TestProductionHandler("truth value"), TRUE);
        grammar.addProduction(nt("truth value"), new TestProductionHandler("truth value"), FALSE);
        grammar.addProduction(nt("truth value"), new TestProductionHandler("truth value"), UNKNOWN);
        
        /*
         * More about constraints
         */
        grammar.addProduction(nt("constraint attribute"), new TestProductionHandler("constraint attribute"), nt("constraint check time"));
        grammar.addProduction(nt("constraint attribute"), new TestProductionHandler("constraint attribute"), nt("constraint check time"), DEFERRABLE);
        grammar.addProduction(nt("constraint attribute"), new TestProductionHandler("constraint attribute"), nt("constraint check time"), NOT, DEFERRABLE);
        grammar.addProduction(nt("constraint attribute"), new TestProductionHandler("constraint attribute"), DEFERRABLE);
        grammar.addProduction(nt("constraint attribute"), new TestProductionHandler("constraint attribute"), NOT, DEFERRABLE);
        grammar.addProduction(nt("constraint attribute"), new TestProductionHandler("constraint attribute"), DEFERRABLE, nt("constraint check time"));
        grammar.addProduction(nt("constraint attribute"), new TestProductionHandler("constraint attribute"), NOT, DEFERRABLE, nt("constraint check time"));
        
        grammar.addProduction(nt("constraint check time"), new TestProductionHandler("constraint check time"), INITIALLY, DEFERRED);
        grammar.addProduction(nt("constraint check time"), new TestProductionHandler("constraint check time"), INITIALLY, IMMEDIATE);
        
        grammar.addProduction(nt("table constraint definition"), new TestProductionHandler("table constraint definition"), nt("table constraint"));
        grammar.addProduction(nt("table constraint definition"), new TestProductionHandler("table constraint definition"), nt("constraint name definition"), nt("table constraint"));
        grammar.addProduction(nt("table constraint definition"), new TestProductionHandler("table constraint definition"), nt("table constraint"), nt("constraint check time"));
        grammar.addProduction(nt("table constraint definition"), new TestProductionHandler("table constraint definition"), nt("constraint name definition"), nt("table constraint"), nt("constraint check time"));
        
        grammar.addProduction(nt("table constraint"), new TestProductionHandler("table constraint"), nt("unique constraint definition"));
        grammar.addProduction(nt("table constraint"), new TestProductionHandler("table constraint"), nt("referential constraint definition"));
        grammar.addProduction(nt("table constraint"), new TestProductionHandler("table constraint"), nt("check constraint definition"));
        
        grammar.addProduction(nt("unique constraint definition"), new TestProductionHandler("unique constraint definition"), nt("unique specification"), left_paren, nt("unique column list"), right_paren);
        
        grammar.addProduction(nt("unique column list"), new TestProductionHandler("unique column list"), nt("column name list"));
        
        grammar.addProduction(nt("referential constraint definition"), new TestProductionHandler("referential constraint definition"), FOREIGN, KEY, left_paren, nt("referencing columns"), right_paren, nt("references specification"));
        
        grammar.addProduction(nt("referencing columns"), new TestProductionHandler("referencing columns"), nt("reference column list"));
        
        /*
         * Module contents
         */
        grammar.addProduction(nt("module contents"), new TestProductionHandler("module contents"), nt("declare cursor"));
        grammar.addProduction(nt("module contents"), new TestProductionHandler("module contents"), nt("dynamic declare cursor"));
        grammar.addProduction(nt("module contents"), new TestProductionHandler("module contents"), nt("procedure"));
        
        grammar.addProduction(nt("declare cursor"), new TestProductionHandler("declare cursor"), DECLARE, nt("cursor name"), CURSOR, FOR, nt("cursor specification"));
        grammar.addProduction(nt("declare cursor"), new TestProductionHandler("declare cursor"), DECLARE, nt("cursor name"), INSENSITIVE, CURSOR, FOR, nt("cursor specification"));
        grammar.addProduction(nt("declare cursor"), new TestProductionHandler("declare cursor"), DECLARE, nt("cursor name"), SCROLL, CURSOR, FOR, nt("cursor specification"));
        grammar.addProduction(nt("declare cursor"), new TestProductionHandler("declare cursor"), DECLARE, nt("cursor name"), INSENSITIVE, SCROLL, CURSOR, FOR, nt("cursor specification"));
        
        grammar.addProduction(nt("cursor name"), new TestProductionHandler("cursor name"), nt("identifier"));
        
        grammar.addProduction(nt("cursor specification"), new TestProductionHandler("cursor specification"), nt("query expression"));
        grammar.addProduction(nt("cursor specification"), new TestProductionHandler("cursor specification"), nt("query expression"), nt("order by clause"));
        grammar.addProduction(nt("cursor specification"), new TestProductionHandler("cursor specification"), nt("query expression"), nt("updatability clause"));
        grammar.addProduction(nt("cursor specification"), new TestProductionHandler("cursor specification"), nt("query expression"), nt("order by clause"), nt("updatability clause"));
        
        grammar.addProduction(nt("order by clause"), new TestProductionHandler("order by clause"), ORDER, BY, nt("sort specification list"));
        
        grammar.addProduction(nt("sort specification list"), new TestProductionHandler("sort specification list"), nt("sort specification"));
        grammar.addProduction(nt("sort specification list"), new TestProductionHandler("sort specification list"), nt("sort specification"), comma, nt("sort specification"));
        // TODO: list
        
        grammar.addProduction(nt("sort specification"), new TestProductionHandler("sort specification"), nt("sort key"));
        grammar.addProduction(nt("sort specification"), new TestProductionHandler("sort specification"), nt("sort key"), nt("collate clause"));
        grammar.addProduction(nt("sort specification"), new TestProductionHandler("sort specification"), nt("sort key"), nt("ordering specification"));
        grammar.addProduction(nt("sort specification"), new TestProductionHandler("sort specification"), nt("sort key"), nt("collate clause"), nt("ordering specification"));
        
        grammar.addProduction(nt("sort key"), new TestProductionHandler("sort key"), nt("column name"));
        grammar.addProduction(nt("sort key"), new TestProductionHandler("sort key"), unsigned_integer);
        
        grammar.addProduction(nt("ordering specification"), new TestProductionHandler("ordering specification"), ASC);
        grammar.addProduction(nt("ordering specification"), new TestProductionHandler("ordering specification"), DESC);
        
        grammar.addProduction(nt("updatability clause"), new TestProductionHandler("updatability clause"), FOR, READ, ONLY);
        grammar.addProduction(nt("updatability clause"), new TestProductionHandler("updatability clause"), FOR, UPDATE);
        grammar.addProduction(nt("updatability clause"), new TestProductionHandler("updatability clause"), FOR, READ, ONLY, OF, nt("column name list"));
        grammar.addProduction(nt("updatability clause"), new TestProductionHandler("updatability clause"), FOR, UPDATE, nt("column name list"));
        
        grammar.addProduction(nt("dynamic declare cursor"), new TestProductionHandler("dynamic declare cursor"), DECLARE, nt("cursor name"), CURSOR, FOR, nt("statement name"));
        grammar.addProduction(nt("dynamic declare cursor"), new TestProductionHandler("dynamic declare cursor"), DECLARE, nt("cursor name"), INSENSITIVE, CURSOR, FOR, nt("statement name"));
        grammar.addProduction(nt("dynamic declare cursor"), new TestProductionHandler("dynamic declare cursor"), DECLARE, nt("cursor name"), SCROLL, CURSOR, FOR, nt("statement name"));
        grammar.addProduction(nt("dynamic declare cursor"), new TestProductionHandler("dynamic declare cursor"), DECLARE, nt("cursor name"), INSENSITIVE, SCROLL, CURSOR, FOR, nt("statement name"));
        
        grammar.addProduction(nt("statement name"), new TestProductionHandler("statement name"), nt("identifier"));
        
        /*
         * SQL Procedures
         */
        grammar.addProduction(nt("procedure"), new TestProductionHandler("procedure"), PROCEDURE, nt("procedure name"), nt("parameter declaration list"), semicolon, nt("SQL procedure statement"), semicolon);
        
        grammar.addProduction(nt("procedure name"), new TestProductionHandler("procedure name"), nt("identifier"));
        
        grammar.addProduction(nt("parameter declaration list"), new TestProductionHandler("parameter declaration list"), left_paren, nt("parameter declaration"), right_paren);
        grammar.addProduction(nt("parameter declaration list"), new TestProductionHandler("parameter declaration list"), left_paren, nt("parameter declaration"), comma, nt("parameter declaration"), right_paren);
        // TODO: list
        
        grammar.addProduction(nt("parameter declaration"), new TestProductionHandler("parameter declaration"), nt("parameter name"), nt("data type"));
        grammar.addProduction(nt("parameter declaration"), new TestProductionHandler("parameter declaration"), nt("status parameter"));
        
        grammar.addProduction(nt("status parameter"), new TestProductionHandler("status parameter"), SQLCODE);
        grammar.addProduction(nt("status parameter"), new TestProductionHandler("status parameter"), SQLSTATE);
        
        grammar.addProduction(nt("SQL procedure statement"), new TestProductionHandler("SQL procedure statement"), nt("SQL schema statement"));
        grammar.addProduction(nt("SQL procedure statement"), new TestProductionHandler("SQL procedure statement"), nt("SQL data statement"));
        grammar.addProduction(nt("SQL procedure statement"), new TestProductionHandler("SQL procedure statement"), nt("SQL transaction statement"));
        grammar.addProduction(nt("SQL procedure statement"), new TestProductionHandler("SQL procedure statement"), nt("SQL connection statement"));
        grammar.addProduction(nt("SQL procedure statement"), new TestProductionHandler("SQL procedure statement"), nt("SQL session statement"));
        grammar.addProduction(nt("SQL procedure statement"), new TestProductionHandler("SQL procedure statement"), nt("SQL dynamic statement"));
        grammar.addProduction(nt("SQL procedure statement"), new TestProductionHandler("SQL procedure statement"), nt("SQL diagnostics statement"));
        
        /*
         * SQL Schema Definition Statements
         */
        grammar.addProduction(nt("SQL schema statement"), new TestProductionHandler("SQL schema statement"), nt("SQL schema definition statement"));
        grammar.addProduction(nt("SQL schema statement"), new TestProductionHandler("SQL schema statement"), nt("SQL schema manipulation statement"));
        
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("schema definition"));
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("table definition"));
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("view definition"));
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("grant statement"));
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("domain definition"));
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("character set definition"));
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("collation definition"));
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("translation definition"));
        grammar.addProduction(nt("SQL schema definition statement"), new TestProductionHandler("SQL schema definition statement"), nt("assertion definition"));
        
        grammar.addProduction(nt("schema definition"), new TestProductionHandler("schema definition"), CREATE, SCHEMA, nt("schema name clause"));
        grammar.addProduction(nt("schema definition"), new TestProductionHandler("schema definition"), CREATE, SCHEMA, nt("schema name clause"), nt("schema character set specification"));
        grammar.addProduction(nt("schema definition"), new TestProductionHandler("schema definition"), CREATE, SCHEMA, nt("schema name clause"), nt("schema element"));
        grammar.addProduction(nt("schema definition"), new TestProductionHandler("schema definition"), CREATE, SCHEMA, nt("schema name clause"), nt("schema character set specification"), nt("schema element"));
        // TODO: list elements
        
        grammar.addProduction(nt("schema name clause"), new TestProductionHandler("schema name clause"), nt("schema name"));
        grammar.addProduction(nt("schema name clause"), new TestProductionHandler("schema name clause"), AUTHORIZATION, nt("schema authorization identifier"));
        grammar.addProduction(nt("schema name clause"), new TestProductionHandler("schema name clause"), nt("schema name"), AUTHORIZATION, nt("schema authorization identifier"));
        
        grammar.addProduction(nt("schema authorization identifier"), new TestProductionHandler("schema authorization identifier"), nt("authorization identifier"));
        
        grammar.addProduction(nt("schema character set specification"), new TestProductionHandler("schema character set specification"), DEFAULT, CHARACTER, SET, nt("character set specification"));
        
        grammar.addProduction(nt("schema element"), new TestProductionHandler("schema element"), nt("domain definition"));
        grammar.addProduction(nt("schema element"), new TestProductionHandler("schema element"), nt("table definition"));
        grammar.addProduction(nt("schema element"), new TestProductionHandler("schema element"), nt("view definition"));
        grammar.addProduction(nt("schema element"), new TestProductionHandler("schema element"), nt("grant statement"));
        grammar.addProduction(nt("schema element"), new TestProductionHandler("schema element"), nt("assertion definition"));
        grammar.addProduction(nt("schema element"), new TestProductionHandler("schema element"), nt("character set definition"));
        grammar.addProduction(nt("schema element"), new TestProductionHandler("schema element"), nt("collation definition"));
        grammar.addProduction(nt("schema element"), new TestProductionHandler("schema element"), nt("translation definition"));
        
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), nt("data type"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), AS, nt("data type"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), nt("data type"), nt("default clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), AS, nt("data type"), nt("default clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), nt("data type"), nt("domain constraint"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), AS, nt("data type"), nt("domain constraint"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), nt("data type"), nt("default clause"), nt("domain constraint"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), AS, nt("data type"), nt("default clause"), nt("domain constraint"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), nt("data type"), nt("collate clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), AS, nt("data type"), nt("collate clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), nt("data type"), nt("default clause"), nt("collate clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), AS, nt("data type"), nt("default clause"), nt("collate clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), nt("data type"), nt("domain constraint"), nt("collate clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), AS, nt("data type"), nt("domain constraint"), nt("collate clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), nt("data type"), nt("default clause"), nt("domain constraint"), nt("collate clause"));
        grammar.addProduction(nt("domain definition"), new TestProductionHandler("domain definition"), CREATE, DOMAIN, nt("domain name"), AS, nt("data type"), nt("default clause"), nt("domain constraint"), nt("collate clause"));
        
        grammar.addProduction(nt("domain constraint"), new TestProductionHandler("domain constraint"), nt("check constraint definition"));
        grammar.addProduction(nt("domain constraint"), new TestProductionHandler("domain constraint"), nt("constraint name definition"), nt("check constraint definition"));
        grammar.addProduction(nt("domain constraint"), new TestProductionHandler("domain constraint"), nt("check constraint definition"), nt("constraint attributes"));
        grammar.addProduction(nt("domain constraint"), new TestProductionHandler("domain constraint"), nt("constraint name definition"), nt("check constraint definition"), nt("constraint attributes"));
        
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, TABLE, nt("table name"), nt("table element list"));
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, GLOBAL, TEMPORARY, TABLE, nt("table name"), nt("table element list"));
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, LOCAL, TEMPORARY, TABLE, nt("table name"), nt("table element list"));
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, TABLE, nt("table name"), nt("table element list"), ON, COMMIT, DELETE, ROWS);
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, GLOBAL, TEMPORARY, TABLE, nt("table name"), nt("table element list"), ON, COMMIT, DELETE, ROWS);
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, LOCAL, TEMPORARY, TABLE, nt("table name"), nt("table element list"), ON, COMMIT, DELETE, ROWS);
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, TABLE, nt("table name"), nt("table element list"), ON, COMMIT, PRESERVE, ROWS);
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, GLOBAL, TEMPORARY, TABLE, nt("table name"), nt("table element list"), ON, COMMIT, PRESERVE, ROWS);
        grammar.addProduction(nt("table definition"), new TestProductionHandler("table definition"), CREATE, LOCAL, TEMPORARY, TABLE, nt("table name"), nt("table element list"), ON, COMMIT, PRESERVE, ROWS);
        
        grammar.addProduction(nt("view definition"), new TestProductionHandler("view definition"), CREATE, VIEW, nt("table name"), AS, nt("query expression"));
        grammar.addProduction(nt("view definition"), new TestProductionHandler("view definition"), CREATE, VIEW, nt("table name"), left_paren, nt("view column list"), right_paren, AS, nt("query expression"));
        grammar.addProduction(nt("view definition"), new TestProductionHandler("view definition"), CREATE, VIEW, nt("table name"), AS, nt("query expression"), WITH, CHECK, OPTION);
        grammar.addProduction(nt("view definition"), new TestProductionHandler("view definition"), CREATE, VIEW, nt("table name"), left_paren, nt("view column list"), right_paren, AS, nt("query expression"), WITH, CHECK, OPTION);
        grammar.addProduction(nt("view definition"), new TestProductionHandler("view definition"), CREATE, VIEW, nt("table name"), AS, nt("query expression"), WITH, nt("levels clause"), CHECK, OPTION);
        grammar.addProduction(nt("view definition"), new TestProductionHandler("view definition"), CREATE, VIEW, nt("table name"), left_paren, nt("view column list"), right_paren, AS, nt("query expression"), WITH, nt("levels clause"), CHECK, OPTION);
        
        grammar.addProduction(nt("view column list"), new TestProductionHandler("view column list"), nt("column name list"));
        
        grammar.addProduction(nt("levels clause"), new TestProductionHandler("levels clause"), CASCADED);
        grammar.addProduction(nt("levels clause"), new TestProductionHandler("levels clause"), LOCAL);
        
        grammar.addProduction(nt("grant statement"), new TestProductionHandler("grant statement"), GRANT, nt("privileges"), ON, nt("object name"), TO, nt("grantee"));
        grammar.addProduction(nt("grant statement"), new TestProductionHandler("grant statement"), GRANT, nt("privileges"), ON, nt("object name"), TO, nt("grantee"), comma, nt("grantee"));
        grammar.addProduction(nt("grant statement"), new TestProductionHandler("grant statement"), GRANT, nt("privileges"), ON, nt("object name"), TO, nt("grantee"), WITH, GRANT, OPTION);
        grammar.addProduction(nt("grant statement"), new TestProductionHandler("grant statement"), GRANT, nt("privileges"), ON, nt("object name"), TO, nt("grantee"), comma, nt("grantee"), WITH, GRANT, OPTION);
        // TODO: list of grantees
        
        grammar.addProduction(nt("privileges"), new TestProductionHandler("privileges"), ALL, PRIVILEGES);
        grammar.addProduction(nt("privileges"), new TestProductionHandler("privileges"), nt("action list"));
        
        grammar.addProduction(nt("action list"), new TestProductionHandler("action list"), nt("action"));
        grammar.addProduction(nt("action list"), new TestProductionHandler("action list"), nt("action"), comma, nt("action"));
        // TODO: action list
        
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), SELECT);
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), DELETE);
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), INSERT);
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), INSERT, left_paren, nt("privilege column list"), right_paren);
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), UPDATE);
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), UPDATE, left_paren, nt("privilege column list"), right_paren);
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), REFERENCES);
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), REFERENCES, left_paren, nt("privilege column list"), right_paren);
        grammar.addProduction(nt("action"), new TestProductionHandler("action"), USAGE);
        
        grammar.addProduction(nt("privilege column list"), new TestProductionHandler("privilege column list"), nt("column name list"));
        
        grammar.addProduction(nt("object name"), new TestProductionHandler("object name"), nt("table name"));
        grammar.addProduction(nt("object name"), new TestProductionHandler("object name"), TABLE, nt("table name"));
        grammar.addProduction(nt("object name"), new TestProductionHandler("object name"), DOMAIN, nt("domain name"));
        grammar.addProduction(nt("object name"), new TestProductionHandler("object name"), COLLATION, nt("collation name"));
        grammar.addProduction(nt("object name"), new TestProductionHandler("object name"), CHARACTER, SET, nt("character set name"));
        grammar.addProduction(nt("object name"), new TestProductionHandler("object name"), TRANSLATION, nt("translation name"));
        
        grammar.addProduction(nt("grantee"), new TestProductionHandler("grantee"), PUBLIC);
        grammar.addProduction(nt("grantee"), new TestProductionHandler("grantee"), nt("authorization identifier"));
        
        grammar.addProduction(nt("assertion definition"), new TestProductionHandler("assertion definition"), CREATE, ASSERTION, nt("constraint name"), nt("assertion check"));
        grammar.addProduction(nt("assertion definition"), new TestProductionHandler("assertion definition"), CREATE, ASSERTION, nt("constraint name"), nt("assertion check"), nt("constraint attributes"));
        
        grammar.addProduction(nt("assertion check"), new TestProductionHandler("assertion check"), CHECK, left_paren, nt("search condition"), right_paren);
        
        grammar.addProduction(nt("character set definition"), new TestProductionHandler("character set definition"), CREATE, CHARACTER, SET, nt("character set name"), nt("character set source"));
        grammar.addProduction(nt("character set definition"), new TestProductionHandler("character set definition"), CREATE, CHARACTER, SET, nt("character set name"), AS, nt("character set source"));
        grammar.addProduction(nt("character set definition"), new TestProductionHandler("character set definition"), CREATE, CHARACTER, SET, nt("character set name"), nt("character set source"), nt("collate clause"));
        grammar.addProduction(nt("character set definition"), new TestProductionHandler("character set definition"), CREATE, CHARACTER, SET, nt("character set name"), AS, nt("character set source"), nt("collate clause"));
        grammar.addProduction(nt("character set definition"), new TestProductionHandler("character set definition"), CREATE, CHARACTER, SET, nt("character set name"), nt("character set source"), nt("limited collation definition"));
        grammar.addProduction(nt("character set definition"), new TestProductionHandler("character set definition"), CREATE, CHARACTER, SET, nt("character set name"), AS, nt("character set source"), nt("limited collation definition"));
        
        grammar.addProduction(nt("character set source"), new TestProductionHandler("character set source"), GET, nt("existing character set name"));
        
        grammar.addProduction(nt("existing character set name"), new TestProductionHandler("existing character set name"), nt("standard character repertoire name"));
        grammar.addProduction(nt("existing character set name"), new TestProductionHandler("existing character set name"), nt("implementation-defined character repertoire name"));
        grammar.addProduction(nt("existing character set name"), new TestProductionHandler("existing character set name"), nt("schema character set name"));
        
        grammar.addProduction(nt("schema character set name"), new TestProductionHandler("schema character set name"), nt("character set name"));
        
        grammar.addProduction(nt("limited collation definition"), new TestProductionHandler("limited collation definition"), COLLATION, FROM, nt("collation source"));
        
        grammar.addProduction(nt("collation source"), new TestProductionHandler("collation source"), nt("collationg sequence definition"));
        grammar.addProduction(nt("collation source"), new TestProductionHandler("collation source"), nt("translation collation"));
        
        grammar.addProduction(nt("collating sequence definition"), new TestProductionHandler("collating sequence definition"), nt("external collation"));
        grammar.addProduction(nt("collating sequence definition"), new TestProductionHandler("collating sequence definition"), nt("schema collation name"));
        grammar.addProduction(nt("collating sequence definition"), new TestProductionHandler("collating sequence definition"), DESC, left_paren, nt("collation name"), right_paren);
        grammar.addProduction(nt("collating sequence definition"), new TestProductionHandler("collating sequence definition"), DEFAULT);
        
        grammar.addProduction(nt("external collation"), new TestProductionHandler("external collation"), EXTERNAL, left_paren, quote, nt("external collation name"), quote, right_paren);
        
        grammar.addProduction(nt("external collation name"), new TestProductionHandler("external collation name"), nt("standard collation name"));
        grammar.addProduction(nt("external collation name"), new TestProductionHandler("external collation name"), nt("implementation-defined collation name"));
        
        grammar.addProduction(nt("standard collation name"), new TestProductionHandler("standard collation name"), nt("collation name"));
        
        grammar.addProduction(nt("implementation-defined collation name"), new TestProductionHandler("implementation-defined collation name"), nt("collation name"));
        
        grammar.addProduction(nt("schema collation name"), new TestProductionHandler("schema collation name"), nt("collation name"));
        // TODO: definition a reduce-reduce conflict here
        
        grammar.addProduction(nt("translation collation"), new TestProductionHandler("translation collation"), TRANSLATION, nt("translation name"));
        grammar.addProduction(nt("translation collation"), new TestProductionHandler("translation collation"), TRANSLATION, nt("translation name"), THEN, COLLATION, nt("collation name"));
        
        grammar.addProduction(nt("collation definition"), new TestProductionHandler("collation definition"), CREATE, COLLATION, nt("collation name"), FOR, nt("character set specification"), FROM, nt("collation source"));
        grammar.addProduction(nt("collation definition"), new TestProductionHandler("collation definition"), CREATE, COLLATION, nt("collation name"), FOR, nt("character set specification"), FROM, nt("collation source"), nt("pad attribute"));
        
        grammar.addProduction(nt("pad attribute"), new TestProductionHandler("pad attribute"), NO, PAD);
        grammar.addProduction(nt("pad attribute"), new TestProductionHandler("pad attribute"), PAD, SPACE);
        
        grammar.addProduction(nt("translation definition"), new TestProductionHandler("translation definition"), CREATE, TRANSLATION, nt("translation name"), FOR, nt("source character set specification"), TO, nt("target character set specification"), FROM, nt("translation source"));
        
        grammar.addProduction(nt("source character set specification"), new TestProductionHandler("source character set specification"), nt("character set specification"));
        
        grammar.addProduction(nt("target character set specification"), new TestProductionHandler("target character set specification"), nt("character set specification"));
        
        grammar.addProduction(nt("translation source"), new TestProductionHandler("translation source"), nt("translation specification"));
        
        grammar.addProduction(nt("translation specification"), new TestProductionHandler("translation specification"), nt("external translation"));
        grammar.addProduction(nt("translation specification"), new TestProductionHandler("translation specification"), IDENTITY);
        grammar.addProduction(nt("translation specification"), new TestProductionHandler("translation specification"), nt("schema translation name"));
        
        grammar.addProduction(nt("external translation"), new TestProductionHandler("external translation"), EXTERNAL, left_paren, quote, nt("external translation name"), quote, right_paren);
        
        grammar.addProduction(nt("external translation name"), new TestProductionHandler("external translation name"), nt("standard translation name"));
        grammar.addProduction(nt("external translation name"), new TestProductionHandler("external translation name"), nt("implementation-defined translation name"));
        
        grammar.addProduction(nt("standard translation name"), new TestProductionHandler("standard translation name"), nt("translation name"));
        
        grammar.addProduction(nt("implementation-defined translation name"), new TestProductionHandler("implementation-defined translation name"), nt("translation name"));
        
        grammar.addProduction(nt("schema translation name"), new TestProductionHandler("schema translation name"), nt("translation name"));
        
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("drop schema statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("alter table statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("drop table statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("drop view statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("revoke statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("alter domain statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("drop domain statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("drop character set statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("drop collation statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("drop translation statement"));
        grammar.addProduction(nt("SQL schema manipulation statement"), new TestProductionHandler("SQL schema manipulation statement"), nt("drop assertion statement"));
        
        grammar.addProduction(nt("drop schema statement"), new TestProductionHandler("drop schema statement"), DROP, SCHEMA, nt("schema name"), nt("drop behaviour"));
        
        grammar.addProduction(nt("drop behaviour"), new TestProductionHandler("drop behaviour"), CASCADE);
        grammar.addProduction(nt("drop behaviour"), new TestProductionHandler("drop behaviour"), RESTRICT);
        
        grammar.addProduction(nt("alter table statement"), new TestProductionHandler("alter table statement"), ALTER, TABLE, nt("table name"), nt("alter table action"));
        
        grammar.addProduction(nt("alter table action"), new TestProductionHandler("alter table action"), nt("add column definition"));
        grammar.addProduction(nt("alter table action"), new TestProductionHandler("alter table action"), nt("alter column definition"));
        grammar.addProduction(nt("alter table action"), new TestProductionHandler("alter table action"), nt("drop column definition"));
        grammar.addProduction(nt("alter table action"), new TestProductionHandler("alter table action"), nt("add table constraint definition"));
        grammar.addProduction(nt("alter table action"), new TestProductionHandler("alter table action"), nt("drop table constraint definition"));
        
        grammar.addProduction(nt("add column definition"), new TestProductionHandler("add column definition"), ADD, nt("column definition"));
        grammar.addProduction(nt("add column definition"), new TestProductionHandler("add column definition"), ADD, COLUMN, nt("column definition"));
        
        grammar.addProduction(nt("alter column definition"), new TestProductionHandler("alter column definition"), ALTER, nt("column name"), nt("alter column action"));
        grammar.addProduction(nt("alter column definition"), new TestProductionHandler("alter column definition"), ALTER, COLUMN, nt("column name"), nt("alter column action"));
        
        grammar.addProduction(nt("alter column action"), new TestProductionHandler("alter column action"), nt("set column default clause"));
        grammar.addProduction(nt("alter column action"), new TestProductionHandler("alter column action"), nt("drop column default clause"));
        
        grammar.addProduction(nt("set column default clause"), new TestProductionHandler("set column default clause"), SET, nt("default clause"));
        
        grammar.addProduction(nt("drop column default clause"), new TestProductionHandler("drop column default clause"), DROP, DEFAULT);
        
        grammar.addProduction(nt("drop column definition"), new TestProductionHandler("drop column definition"), DROP, nt("column name"), nt("drop behaviour"));
        grammar.addProduction(nt("drop column definition"), new TestProductionHandler("drop column definition"), DROP, COLUMN, nt("column name"), nt("drop behaviour"));
        
        grammar.addProduction(nt("add table constraint definition"), new TestProductionHandler("add table constraint definition"), ADD, nt("table constraint definition"));
        
        grammar.addProduction(nt("drop table constraint definition"), new TestProductionHandler("drop table constraint definition"), DROP, CONSTRAINT, nt("constraint name"), nt("drop behaviour"));
        
        grammar.addProduction(nt("drop table statement"), new TestProductionHandler("drop table statement"), DROP, TABLE, nt("table name"), nt("drop behaviour"));
        
        grammar.addProduction(nt("drop view statement"), new TestProductionHandler("drop view statement"), DROP, VIEW, nt("table name"), nt("drop behaviour"));
        
        grammar.addProduction(nt("revoke statement"), new TestProductionHandler("revoke statement"), REVOKE, nt("privileges"), ON, nt("object name"), FROM, nt("grantee"), nt("drop behaviour"));
        grammar.addProduction(nt("revoke statement"), new TestProductionHandler("revoke statement"), REVOKE, GRANT, OPTION, FOR, nt("privileges"), ON, nt("object name"), FROM, nt("grantee"), nt("drop behaviour"));
        grammar.addProduction(nt("revoke statement"), new TestProductionHandler("revoke statement"), REVOKE, nt("privileges"), ON, nt("object name"), FROM, nt("grantee"), comma, nt("grantee"), nt("drop behaviour"));
        grammar.addProduction(nt("revoke statement"), new TestProductionHandler("revoke statement"), REVOKE, GRANT, OPTION, FOR, nt("privileges"), ON, nt("object name"), FROM, nt("grantee"), comma, nt("grantee"), nt("drop behaviour"));
        // TODO: grantee list
        
        grammar.addProduction(nt("alter domain statement"), new TestProductionHandler("alter domain statement"), ALTER, DOMAIN, nt("domain name"), nt("alter domain action"));
        
        grammar.addProduction(nt("alter domain action"), new TestProductionHandler("alter domain action"), nt("set domain default clause"));
        grammar.addProduction(nt("alter domain action"), new TestProductionHandler("alter domain action"), nt("drop domain default clause"));
        grammar.addProduction(nt("alter domain action"), new TestProductionHandler("alter domain action"), nt("add domain constraint definition"));
        grammar.addProduction(nt("alter domain action"), new TestProductionHandler("alter domain action"), nt("drop domain constraint definition"));
        
        grammar.addProduction(nt("set domain default clause"), new TestProductionHandler("set domain default clause"), SET, nt("default clause"));
        
        grammar.addProduction(nt("drop domain default clause"), new TestProductionHandler("drop domain default clause"), DROP, DEFAULT);
        
        grammar.addProduction(nt("add domain constraint definition"), new TestProductionHandler("add domain constraint definition"), ADD, nt("domain constraint"));
        
        grammar.addProduction(nt("drop domain constraint definition"), new TestProductionHandler("drop domain constraint definition"), DROP, CONSTRAINT, nt("constraint name"));
        
        grammar.addProduction(nt("drop domain statement"), new TestProductionHandler("drop domain statement"), DROP, DOMAIN, nt("domain name"), nt("drop behaviour"));
        
        grammar.addProduction(nt("drop character set statement"), new TestProductionHandler("drop character set statement"), DROP, CHARACTER, SET, nt("character set name"));
        
        grammar.addProduction(nt("drop collation statement"), new TestProductionHandler("drop collation statement"), DROP, COLLATION, nt("collation name"));
        
        grammar.addProduction(nt("drop translation statement"), new TestProductionHandler("drop translation statement"), DROP, TRANSLATION, nt("translation name"));
        
        grammar.addProduction(nt("drop assertion statement"), new TestProductionHandler("drop assertion statement"), DROP, ASSERTION, nt("constraint name"));
        
        /*
         * SQL Data Manipulation Statements
         */
        grammar.addProduction(nt("SQL data statement"), new TestProductionHandler("SQL data statement"), nt("open statement"));
        grammar.addProduction(nt("SQL data statement"), new TestProductionHandler("SQL data statement"), nt("fetch statement"));
        grammar.addProduction(nt("SQL data statement"), new TestProductionHandler("SQL data statement"), nt("close statement"));
        grammar.addProduction(nt("SQL data statement"), new TestProductionHandler("SQL data statement"), nt("select statement: single row"));
        grammar.addProduction(nt("SQL data statement"), new TestProductionHandler("SQL data statement"), nt("SQL data change statement"));
        
        grammar.addProduction(nt("open statement"), new TestProductionHandler("open statement"), OPEN, nt("cursor name"));
        
        grammar.addProduction(nt("fetch statement"), new TestProductionHandler("fetch statement"), FETCH, nt("cursor name"), INTO, nt("fetch target list"));
        grammar.addProduction(nt("fetch statement"), new TestProductionHandler("fetch statement"), FETCH, FROM, nt("cursor name"), INTO, nt("fetch target list"));
        grammar.addProduction(nt("fetch statement"), new TestProductionHandler("fetch statement"), nt("fetch orientation"), FETCH, FROM, nt("cursor name"), INTO, nt("fetch target list"));
        
        grammar.addProduction(nt("fetch orientation"), new TestProductionHandler("fetch orientation"), NEXT);
        grammar.addProduction(nt("fetch orientation"), new TestProductionHandler("fetch orientation"), PRIOR);
        grammar.addProduction(nt("fetch orientation"), new TestProductionHandler("fetch orientation"), FIRST);
        grammar.addProduction(nt("fetch orientation"), new TestProductionHandler("fetch orientation"), LAST);
        grammar.addProduction(nt("fetch orientation"), new TestProductionHandler("fetch orientation"), ABSOLUTE, nt("simple value specification"));
        grammar.addProduction(nt("fetch orientation"), new TestProductionHandler("fetch orientation"), RELATIVE, nt("simple value specification"));
        
        grammar.addProduction(nt("simple value specification"), new TestProductionHandler("simple value specification"), nt("parameter name"));
        grammar.addProduction(nt("simple value specification"), new TestProductionHandler("simple value specification"), nt("embedded variable name"));
        grammar.addProduction(nt("simple value specification"), new TestProductionHandler("simple value specification"), nt("literal"));
        
        grammar.addProduction(nt("fetch target list"), new TestProductionHandler("fetch target list"), nt("target specification"));
        grammar.addProduction(nt("fetch target list"), new TestProductionHandler("fetch target list"), nt("target specification"), comma, nt("target specification"));
        // TODO: target list
        
        grammar.addProduction(nt("target specification"), new TestProductionHandler("target specification"), nt("parameter specification"));
        grammar.addProduction(nt("target specification"), new TestProductionHandler("target specification"), nt("variable specification"));
        
        grammar.addProduction(nt("close statement"), new TestProductionHandler("close statement"), CLOSE, nt("cursor name"));
        
        grammar.addProduction(nt("select statement: single row"), new TestProductionHandler("select statement: single row"), SELECT, nt("select list"), INTO, nt("select target list"), nt("table expression"));
        grammar.addProduction(nt("select statement: single row"), new TestProductionHandler("select statement: single row"), SELECT, nt("set quantifier"), nt("select list"), INTO, nt("select target list"), nt("table expression"));
        
        grammar.addProduction(nt("select target list"), new TestProductionHandler("select target list"), nt("target specification"));
        grammar.addProduction(nt("select target list"), new TestProductionHandler("select target list"), nt("target specification"), comma, nt("target specification"));
        // TODO: target list
        
        grammar.addProduction(nt("SQL data change statement"), new TestProductionHandler("SQL data change statement"), nt("delete statement: positioned"));
        grammar.addProduction(nt("SQL data change statement"), new TestProductionHandler("SQL data change statement"), nt("delete statement: searched"));
        grammar.addProduction(nt("SQL data change statement"), new TestProductionHandler("SQL data change statement"), nt("insert statement"));
        grammar.addProduction(nt("SQL data change statement"), new TestProductionHandler("SQL data change statement"), nt("update statement: positioned"));
        grammar.addProduction(nt("SQL data change statement"), new TestProductionHandler("SQL data change statement"), nt("update statement: searched"));
        
        grammar.addProduction(nt("delete statement: positioned"), new TestProductionHandler("delete statement: positioned"), DELETE, FROM, nt("table name"), WHERE, CURRENT, OF, nt("cursor name"));
        
        grammar.addProduction(nt("delete statement: searched"), new TestProductionHandler("delete statement: searched"), DELETE, FROM, nt("table name"));
        grammar.addProduction(nt("delete statement: searched"), new TestProductionHandler("delete statement: searched"), DELETE, FROM, nt("table name"), WHERE, nt("search condition"));
        
        grammar.addProduction(nt("insert statement"), new TestProductionHandler("insert statement"), INSERT, INTO, nt("table name"), nt("insert columns and source"));
        
        grammar.addProduction(nt("insert columns and source"), new TestProductionHandler("insert columns and source"), nt("query expression"));
        grammar.addProduction(nt("insert columns and source"), new TestProductionHandler("insert columns and source"), left_paren, nt("insert column list"), right_paren, nt("query expression"));
        grammar.addProduction(nt("insert columns and source"), new TestProductionHandler("insert columns and source"), DEFAULT, VALUES);
        
        grammar.addProduction(nt("insert column list"), new TestProductionHandler("insert column list"), nt("column name list"));
        
        grammar.addProduction(nt("update statement: positioned"), new TestProductionHandler("update statement: positioned"), UPDATE, nt("table name"), SET, nt("set clause list"), WHERE, CURRENT, OF, nt("cursor name"));
        
        grammar.addProduction(nt("set clause list"), new TestProductionHandler("set clause list"), nt("set clause"));
        grammar.addProduction(nt("set clause list"), new TestProductionHandler("set clause list"), nt("set clause"), comma, nt("set clause"));
        // TODO: list
        
        grammar.addProduction(nt("set clause"), new TestProductionHandler("set clause"), nt("object column"), nt("equals operator"), nt("update source"));
        
        grammar.addProduction(nt("object column"), new TestProductionHandler("object column"), nt("column name"));
        
        grammar.addProduction(nt("update source"), new TestProductionHandler("update source"), nt("value expression"));
        grammar.addProduction(nt("update source"), new TestProductionHandler("update source"), nt("null specification"));
        grammar.addProduction(nt("update source"), new TestProductionHandler("update source"), DEFAULT);
        
        grammar.addProduction(nt("update statement: searched"), new TestProductionHandler("update statement: searched"), UPDATE, nt("table name"), SET, nt("set clause list"));
        grammar.addProduction(nt("update statement: searched"), new TestProductionHandler("update statement: searched"), UPDATE, nt("table name"), SET, nt("set clause list"), WHERE, nt("search condition"));
        
        grammar.addProduction(nt("SQL transaction statement"), new TestProductionHandler("SQL transaction statement"), nt("set transaction statement"));
        grammar.addProduction(nt("SQL transaction statement"), new TestProductionHandler("SQL transaction statement"), nt("set constraints mode statement"));
        grammar.addProduction(nt("SQL transaction statement"), new TestProductionHandler("SQL transaction statement"), nt("commit statement"));
        grammar.addProduction(nt("SQL transaction statement"), new TestProductionHandler("SQL transaction statement"), nt("rollback statement"));
        
        grammar.addProduction(nt("set transaction statement"), new TestProductionHandler("set transaction statement"), SET, TRANSACTION, nt("transaction mode"));
        grammar.addProduction(nt("set transaction statement"), new TestProductionHandler("set transaction statement"), SET, TRANSACTION, nt("transaction mode"), comma, nt("transaction mode"));
        // TODO: list
        
        grammar.addProduction(nt("transaction mode"), new TestProductionHandler("transaction mode"), nt("isolation level"));
        grammar.addProduction(nt("transaction mode"), new TestProductionHandler("transaction mode"), nt("transaction access mode"));
        grammar.addProduction(nt("transaction mode"), new TestProductionHandler("transaction mode"), nt("diagnostics size"));
        
        grammar.addProduction(nt("isolation level"), new TestProductionHandler("isolation level"), ISOLATION, LEVEL, nt("level of isolation"));
        
        grammar.addProduction(nt("level of isolation"), new TestProductionHandler("level of isolation"), READ, UNCOMMITTED);
        grammar.addProduction(nt("level of isolation"), new TestProductionHandler("level of isolation"), READ, COMMITTED);
        grammar.addProduction(nt("level of isolation"), new TestProductionHandler("level of isolation"), REPEATABLE, READ);
        grammar.addProduction(nt("level of isolation"), new TestProductionHandler("level of isolation"), SERIALIZABLE);
        
        grammar.addProduction(nt("transaction access mode"), new TestProductionHandler("transaction access mode"), READ, ONLY);
        grammar.addProduction(nt("transaction access mode"), new TestProductionHandler("transaction access mode"), READ, WRITE);
        
        grammar.addProduction(nt("diagnostics size"), new TestProductionHandler("diagnostics size"), DIAGNOSTICS, SIZE, nt("number of conditions"));
        
        grammar.addProduction(nt("number of conditions"), new TestProductionHandler("number of conditions"), nt("simple value specification"));
        
        grammar.addProduction(nt("set constraints mode statement"), new TestProductionHandler("set constraints mode statement"), SET, CONSTRAINTS, nt("constraint name list"), DEFERRED);
        grammar.addProduction(nt("set constraints mode statement"), new TestProductionHandler("set constraints mode statement"), SET, CONSTRAINTS, nt("constraint name list"), IMMEDIATE);
        
        grammar.addProduction(nt("constraint name list"), new TestProductionHandler("constraint name list"), ALL);
        grammar.addProduction(nt("constraint name list"), new TestProductionHandler("constraint name list"), nt("constraint name"));
        grammar.addProduction(nt("constraint name list"), new TestProductionHandler("constraint name list"), nt("constraint name"), comma, nt("constraint name"));
        // TODO: list
        
        grammar.addProduction(nt("commit statement"), new TestProductionHandler("commit statement"), COMMIT);
        grammar.addProduction(nt("commit statement"), new TestProductionHandler("commit statement"), COMMIT, WORK);
        
        grammar.addProduction(nt("rollback statement"), new TestProductionHandler("rollback statement"), ROLLBACK);
        grammar.addProduction(nt("rollback statement"), new TestProductionHandler("rollback statement"), ROLLBACK, WORK);
        
        /*
         * Connection Management
         */
        grammar.addProduction(nt("SQL connection statement"), new TestProductionHandler("SQL connection statement"), nt("connection statement"));
        grammar.addProduction(nt("SQL connection statement"), new TestProductionHandler("SQL connection statement"), nt("set connection statement"));
        grammar.addProduction(nt("SQL connection statement"), new TestProductionHandler("SQL connection statement"), nt("disconnect statement"));
        
        grammar.addProduction(nt("connect statement"), new TestProductionHandler("connect statement"), CONNECT, TO, nt("connection target"));
        
        grammar.addProduction(nt("connection target"), new TestProductionHandler("connection target"), nt("SQL-server name"));
        grammar.addProduction(nt("connection target"), new TestProductionHandler("connection target"), nt("SQL-server name"), AS, nt("connection name"));
        grammar.addProduction(nt("connection target"), new TestProductionHandler("connection target"), nt("SQL-server name"), USER, nt("user name"));
        grammar.addProduction(nt("connection target"), new TestProductionHandler("connection target"), nt("SQL-server name"), AS, nt("connection name"), USER, nt("user name"));
        grammar.addProduction(nt("connection target"), new TestProductionHandler("connection target"), DEFAULT);
        
        grammar.addProduction(nt("SQL-server name"), new TestProductionHandler("SQL-server name"), nt("simple value specification"));
        
        grammar.addProduction(nt("connection name"), new TestProductionHandler("connection name"), nt("simple value specification"));
        
        grammar.addProduction(nt("user name"), new TestProductionHandler("user name"), nt("simple value specification"));
        
        grammar.addProduction(nt("set connection statement"), new TestProductionHandler("set connection statement"), SET, CONNECTION, nt("connection object"));
        
        grammar.addProduction(nt("connection object"), new TestProductionHandler("connection object"), DEFAULT);
        grammar.addProduction(nt("connection object"), new TestProductionHandler("connection object"), nt("connection name"));
        
        grammar.addProduction(nt("disconnect statement"), new TestProductionHandler("disconnect statement"), DISCONNECT, nt("disconnect object"));
        
        grammar.addProduction(nt("disconnect object"), new TestProductionHandler("disconnect object"), nt("connection object"));
        grammar.addProduction(nt("disconnect object"), new TestProductionHandler("disconnect object"), ALL);
        grammar.addProduction(nt("disconnect object"), new TestProductionHandler("disconnect object"), CURRENT);
        
        /*
         * Session Attributes
         */
        grammar.addProduction(nt("SQL session statement"), new TestProductionHandler("SQL session statement"), nt("set catalog statement"));
        grammar.addProduction(nt("SQL session statement"), new TestProductionHandler("SQL session statement"), nt("set schema statement"));
        grammar.addProduction(nt("SQL session statement"), new TestProductionHandler("SQL session statement"), nt("set names statement"));
        grammar.addProduction(nt("SQL session statement"), new TestProductionHandler("SQL session statement"), nt("set session authorization identifier statement"));
        grammar.addProduction(nt("SQL session statement"), new TestProductionHandler("SQL session statement"), nt("set local time zone statement"));
        
        grammar.addProduction(nt("set catalog statement"), new TestProductionHandler("set catalog statement"), SET, CATALOG, nt("value specification"));
        
        grammar.addProduction(nt("value specification"), new TestProductionHandler("value specification"), nt("literal"));
        grammar.addProduction(nt("value specification"), new TestProductionHandler("value specification"), nt("general value specification"));
        
        grammar.addProduction(nt("set schema statement"), new TestProductionHandler("set schema statement"), SET, SCHEMA, nt("value specification"));
        
        grammar.addProduction(nt("set names statement"), new TestProductionHandler("set names statement"), SET, NAMES, nt("value specification"));
        
        grammar.addProduction(nt("set session authorization identifier statement"), new TestProductionHandler("set session authorization identifier statement"), SET, SESSION, AUTHORIZATION, nt("value specification"));
        
        grammar.addProduction(nt("set local time zone statement"), new TestProductionHandler("set local time zone statement"), SET, TIME, ZONE, nt("set time zone value"));
        
        grammar.addProduction(nt("set time zone value"), new TestProductionHandler("set time zone value"), nt("interval value expression"));
        grammar.addProduction(nt("set time zone value"), new TestProductionHandler("set time zone value"), LOCAL);
        
        /*
         * Dynamic SQL
         */
        
        /*
         * Identifying the version of SQL in use
         */
        grammar.addProduction(nt("SQL object identifier"), new TestProductionHandler("SQL object identifier"), nt("SQL provenance"), nt("SQL variant"));
        
        grammar.addProduction(nt("SQL provenance"), new TestProductionHandler("SQL provenance"), nt("arc1"), nt("arc2"), nt("arc3"));
        
        grammar.addProduction(nt("arc1"), new TestProductionHandler("arc1"), nt("iso"));
        grammar.addProduction(nt("arc1"), new TestProductionHandler("arc1"), nt("1"));
        grammar.addProduction(nt("arc1"), new TestProductionHandler("arc1"), nt("iso"), left_paren, nt("1"), right_paren);
        
        grammar.addProduction(nt("arc2"), new TestProductionHandler("arc2"), nt("standard"));
        grammar.addProduction(nt("arc2"), new TestProductionHandler("arc2"), nt("0"));
        grammar.addProduction(nt("arc2"), new TestProductionHandler("arc2"), nt("standard"), left_paren, nt("0"), right_paren);
        
        grammar.addProduction(nt("arc3"), new TestProductionHandler("arc3"), nt("9075"));
        
        grammar.addProduction(nt("SQL variant"), new TestProductionHandler("SQL variant"), nt("SQL edition"), nt("SQL conformance"));
        
        grammar.addProduction(nt("SQL edition"), new TestProductionHandler("SQL edition"), nt("1987"));
        grammar.addProduction(nt("SQL edition"), new TestProductionHandler("SQL edition"), nt("1989"));
        grammar.addProduction(nt("SQL edition"), new TestProductionHandler("SQL edition"), nt("1992"));
        
        grammar.addProduction(nt("1987"), new TestProductionHandler("1987"), nt("0"));
        grammar.addProduction(nt("1987"), new TestProductionHandler("1987"), nt("edition1987"), left_paren, nt("0"), right_paren);
        
        grammar.addProduction(nt("1989"), new TestProductionHandler("1989"), nt("1989 base"), nt("1989 package"));
        
        grammar.addProduction(nt("1989 base"), new TestProductionHandler("1989 base"), nt("1"));
        grammar.addProduction(nt("1989 base"), new TestProductionHandler("1989 base"), nt("edition1989"), left_paren, nt("1"), right_paren);
        
        grammar.addProduction(nt("1989 package"), new TestProductionHandler("1989 package"), nt("integrity no"));
        grammar.addProduction(nt("1989 package"), new TestProductionHandler("1989 package"), nt("integrity yes"));
        
        grammar.addProduction(nt("integrity no"), new TestProductionHandler("integrity no"), nt("0"));
        grammar.addProduction(nt("integrity no"), new TestProductionHandler("integrity no"), nt("IntegrityNo"), left_paren, nt("0"), right_paren);
        
        grammar.addProduction(nt("integrity yes"), new TestProductionHandler("integrity yes"), nt("1"));
        grammar.addProduction(nt("integrity yes"), new TestProductionHandler("integrity yes"), nt("IntegrityYes"), left_paren, nt("1"), right_paren);
        
        grammar.addProduction(nt("1992"), new TestProductionHandler("1992"), nt("2"));
        grammar.addProduction(nt("1992"), new TestProductionHandler("1992"), nt("edition1992"), left_paren, nt("2"), right_paren);
        
        grammar.addProduction(nt("SQL conformance"), new TestProductionHandler("SQL conformance"), nt("low"));
        grammar.addProduction(nt("SQL conformance"), new TestProductionHandler("SQL conformance"), nt("intermediate"));
        grammar.addProduction(nt("SQL conformance"), new TestProductionHandler("SQL conformance"), nt("high"));
        
        grammar.addProduction(nt("low"), new TestProductionHandler("low"), nt("0"));
        grammar.addProduction(nt("low"), new TestProductionHandler("low"), nt("Low"), left_paren, nt("0"), right_paren);
        
        grammar.addProduction(nt("intermediate"), new TestProductionHandler("intermediate"), nt("1"));
        grammar.addProduction(nt("intermediate"), new TestProductionHandler("intermediate"), nt("Low"), left_paren, nt("1"), right_paren);
        
        grammar.addProduction(nt("high"), new TestProductionHandler("high"), nt("2"));
        grammar.addProduction(nt("high"), new TestProductionHandler("high"), nt("Low"), left_paren, nt("2"), right_paren);
    }
    
//    @Ignore
    @Test
    public void testGrammar() throws InterruptedException, ExecutionException {
        grammar.setStartSymbol(nt("module"));
        grammar.setEndOfFileSymbol(end_of_file);
        grammar.threadedCreateParser1(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }
}
