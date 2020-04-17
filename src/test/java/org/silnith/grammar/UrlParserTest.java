package org.silnith.grammar;

import static org.silnith.grammar.uri.UriTerminalType.AlphaHex;
import static org.silnith.grammar.uri.UriTerminalType.AlphaNonHex;
import static org.silnith.grammar.uri.UriTerminalType.Ampersand;
import static org.silnith.grammar.uri.UriTerminalType.Apostrophe;
import static org.silnith.grammar.uri.UriTerminalType.Asterisk;
import static org.silnith.grammar.uri.UriTerminalType.AtSign;
import static org.silnith.grammar.uri.UriTerminalType.Colon;
import static org.silnith.grammar.uri.UriTerminalType.Comma;
import static org.silnith.grammar.uri.UriTerminalType.Digit;
import static org.silnith.grammar.uri.UriTerminalType.Dollar;
import static org.silnith.grammar.uri.UriTerminalType.EndOfFile;
import static org.silnith.grammar.uri.UriTerminalType.Equals;
import static org.silnith.grammar.uri.UriTerminalType.ExclamationMark;
import static org.silnith.grammar.uri.UriTerminalType.ForwardSlash;
import static org.silnith.grammar.uri.UriTerminalType.Hyphen;
import static org.silnith.grammar.uri.UriTerminalType.LeftBracket;
import static org.silnith.grammar.uri.UriTerminalType.LeftParenthesis;
import static org.silnith.grammar.uri.UriTerminalType.NumberSign;
import static org.silnith.grammar.uri.UriTerminalType.Percent;
import static org.silnith.grammar.uri.UriTerminalType.Period;
import static org.silnith.grammar.uri.UriTerminalType.Plus;
import static org.silnith.grammar.uri.UriTerminalType.QuestionMark;
import static org.silnith.grammar.uri.UriTerminalType.RightBracket;
import static org.silnith.grammar.uri.UriTerminalType.RightParenthesis;
import static org.silnith.grammar.uri.UriTerminalType.Semicolon;
import static org.silnith.grammar.uri.UriTerminalType.Tilde;
import static org.silnith.grammar.uri.UriTerminalType.Underscore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.silnith.grammar.uri.UriTerminalType;

public class UrlParserTest {

    private static final String all = "%00abyz0189-._~:/?#[]@!$&'()*+,;=%ff";
    private static final String unreserved = "abyz0189-._~";
    private static final String genDelims = ":/?#[]@";
    private static final String subDelims = "!$&'()*+,;=";
    private static final String pchar = "abyz0189-._~:@!$&'()*+,;=";
    private static final String nonPchar = "/?#[]";
    
    private Parser<UriTerminalType> parser;
    private Grammar<UriTerminalType> grammar;
    private NonTerminalSymbol uriReference;
    private NonTerminalSymbol relativeRef;
    private NonTerminalSymbol uri;
    private NonTerminalSymbol pctEncoded;
    private NonTerminalSymbol scheme;
    private NonTerminalSymbol userinfo;
    private NonTerminalSymbol host;
    private NonTerminalSymbol port;
    private NonTerminalSymbol authority;
    private NonTerminalSymbol segment;
    private NonTerminalSymbol segmentSequence;
    private NonTerminalSymbol query;
    private NonTerminalSymbol fragment;
    @Test
    @Ignore
    public void testRegularExpression() {
        final Pattern urlPattern = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
        final Matcher matcher = urlPattern.matcher("foo");
        if (matcher.matches()) {
            final String scheme = matcher.group(2);
            final String authority = matcher.group(4);
            final String path = matcher.group(5);
            final String query = matcher.group(7);
            final String fragment = matcher.group(9);
        }
    }
    
    @Before
    public void setUp() {
        grammar = new Grammar<UriTerminalType>();
        
        pctEncoded = grammar.getNonTerminalSymbol("pct-encoded");
//        final NonTerminalSymbol unreserved = grammar.getNonTerminalSymbol("unreserved");

        uri = grammar.getNonTerminalSymbol("URI");
        scheme = grammar.getNonTerminalSymbol("scheme");
        final NonTerminalSymbol hierPart = grammar.getNonTerminalSymbol("hier-part");
        query = grammar.getNonTerminalSymbol("query");
        fragment = grammar.getNonTerminalSymbol("fragment");

        authority = grammar.getNonTerminalSymbol("authority");
        final NonTerminalSymbol pathAbEmpty = grammar.getNonTerminalSymbol("path-abempty");
        final NonTerminalSymbol pathAbsolute = grammar.getNonTerminalSymbol("path-absolute");
        final NonTerminalSymbol pathRootless = grammar.getNonTerminalSymbol("path-rootless");
        final NonTerminalSymbol pathEmpty = grammar.getNonTerminalSymbol("path-empty");

        userinfo = grammar.getNonTerminalSymbol("userinfo");
        host = grammar.getNonTerminalSymbol("host");
        port = grammar.getNonTerminalSymbol("port");

        final NonTerminalSymbol ipLiteral = grammar.getNonTerminalSymbol("IP-literal");
        final NonTerminalSymbol ipv4address = grammar.getNonTerminalSymbol("IPv4address");
        final NonTerminalSymbol regName = grammar.getNonTerminalSymbol("reg-name");
        
        final NonTerminalSymbol ipv6address = grammar.getNonTerminalSymbol("IPv6address");
        final NonTerminalSymbol ipFuture = grammar.getNonTerminalSymbol("IPvFuture");

        final NonTerminalSymbol ipFutureVersion = grammar.getNonTerminalSymbol("IPvFuture-version");
        final NonTerminalSymbol ipFutureContent = grammar.getNonTerminalSymbol("IPvFuture-content");

        final NonTerminalSymbol decOctet = grammar.getNonTerminalSymbol("dec-octet");

        final NonTerminalSymbol schemePrime = grammar.getNonTerminalSymbol("scheme'");
        
        final NonTerminalSymbol userinfoPrime = grammar.getNonTerminalSymbol("userinfoPrime");
        
        final NonTerminalSymbol regNamePrime = grammar.getNonTerminalSymbol("reg-name-prime");
        final NonTerminalSymbol portPrime = grammar.getNonTerminalSymbol("port-prime");
        
        final NonTerminalSymbol path = grammar.getNonTerminalSymbol("path");
        final NonTerminalSymbol pathNoScheme = grammar.getNonTerminalSymbol("path-noscheme");
        segment = grammar.getNonTerminalSymbol("segment");
        final NonTerminalSymbol segmentNz = grammar.getNonTerminalSymbol("segment-nz");
        final NonTerminalSymbol segmentNzNc = grammar.getNonTerminalSymbol("segment-nz-nc");
        
        segmentSequence = grammar.getNonTerminalSymbol("segment-sequence");
        final NonTerminalSymbol segmentSequenceElement = grammar.getNonTerminalSymbol("segment-sequence-element");
        
        final NonTerminalSymbol segmentPrime = grammar.getNonTerminalSymbol("segment'");
        
        final NonTerminalSymbol queryPrime = grammar.getNonTerminalSymbol("query'");
        
        final NonTerminalSymbol fragmentPrime = grammar.getNonTerminalSymbol("fragment'");
        
        uriReference = grammar.getNonTerminalSymbol("URI-reference");
        relativeRef = grammar.getNonTerminalSymbol("relative-ref");
        final NonTerminalSymbol relativePart = grammar.getNonTerminalSymbol("relative-part");

        final NonTerminalSymbol absoluteUri = grammar.getNonTerminalSymbol("absolute-URI");
        
        // gen-delims = ":" / "/" / "?" / "#" / "[" / "]" / "@"
        final Collection<UriTerminalType> genDelims = new ArrayList<>(7);
        genDelims.addAll(Arrays.asList(Colon, ForwardSlash, QuestionMark, NumberSign, LeftBracket, RightBracket, AtSign));

        // sub-delims = "!" / "$" / "&" / "'" / "(" / ")" / "*" / "+" / "," / ";" / "="
        final Collection<UriTerminalType> subDelims = new ArrayList<>(11);
        subDelims.addAll(Arrays.asList(ExclamationMark, Dollar, Ampersand, Apostrophe, LeftParenthesis, RightParenthesis, Asterisk, Plus, Comma, Semicolon, Equals));
        
        final Collection<UriTerminalType> unreservedSymbols = new ArrayList<>(7);
        unreservedSymbols.add(Digit);
        unreservedSymbols.add(AlphaHex);
        unreservedSymbols.add(AlphaNonHex);
        unreservedSymbols.add(Hyphen);
        unreservedSymbols.add(Period);
        unreservedSymbols.add(Underscore);
        unreservedSymbols.add(Tilde);

        // pct-encoded = "%" HEXDIG HEXDIG
        grammar.addProduction(pctEncoded, new TestProductionHandler("pct-encoded"), Percent, Digit, Digit);
        grammar.addProduction(pctEncoded, new TestProductionHandler("pct-encoded"), Percent, Digit, AlphaHex);
        grammar.addProduction(pctEncoded, new TestProductionHandler("pct-encoded"), Percent, AlphaHex, Digit);
        grammar.addProduction(pctEncoded, new TestProductionHandler("pct-encoded"), Percent, AlphaHex, AlphaHex);
        
        // reserved = gen-delims / sub-delims
        
        // unreserved = ALPHA / DIGIT / "-" / "." / "_" / "~"
        
        // hier-part = "//" authority path-abempty / path-absolute / path-rootless / path-empty
        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), ForwardSlash, ForwardSlash, authority, pathAbEmpty);
        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathAbsolute);
        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathRootless);
        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathEmpty);
        
        // scheme = ALPHA *( ALPHA / DIGIT / "+" / "-" / ".")
        grammar.addProduction(scheme, new TestProductionHandler("scheme"), AlphaHex);
        grammar.addProduction(scheme, new TestProductionHandler("scheme"), AlphaNonHex);
        grammar.addProduction(scheme, new TestProductionHandler("scheme"), scheme, AlphaHex);
        grammar.addProduction(scheme, new TestProductionHandler("scheme"), scheme, AlphaNonHex);
        grammar.addProduction(scheme, new TestProductionHandler("scheme"), scheme, Digit);
        grammar.addProduction(scheme, new TestProductionHandler("scheme"), scheme, Plus);
        grammar.addProduction(scheme, new TestProductionHandler("scheme"), scheme, Hyphen);
        grammar.addProduction(scheme, new TestProductionHandler("scheme"), scheme, Period);

        // IP-literal = "[" ( IPv6address / IPvFuture ) "]"
        grammar.addProduction(ipLiteral, new TestProductionHandler("IP-literal"), LeftBracket, ipv6address, RightBracket);
        grammar.addProduction(ipLiteral, new TestProductionHandler("IP-literal"), LeftBracket, ipFuture, RightBracket);

        // IPvFuture = "v" 1*HEXDIG "." 1*( unreserved / sub-delims / ":" )
        
        // TODO: IPv6 (it's really nasty)
        // IPv6address = 6( h16 ":" ) ls32 / "::" 5( h16 ":" ) ls32
        
        // h16 = 1*4HEXDIG
        // ls32 = ( h16 ":" h16 ) / IPv4address
        
        // IPv4address = dec-octet "." dec-octet "." dec-octet "." dec-octet
        grammar.addProduction(ipv4address, new TestProductionHandler("IPv4address"), decOctet, Period, decOctet, Period, decOctet, Period, decOctet);

        // dec-octet = DIGIT / %x31-39 DIGIT / "1" 2DIGIT / "2" %x30-34 DIGIT / "25" %x30-35
        grammar.addProduction(decOctet, new TestProductionHandler("dec-octet"), Digit);
        grammar.addProduction(decOctet, new TestProductionHandler("dec-octet"), Digit, Digit);
        grammar.addProduction(decOctet, new TestProductionHandler("dec-octet"), Digit, Digit, Digit);
        
        // path = path-abempty / path-absolute / path-noscheme / pathrootless / path-empty
        grammar.addProduction(path, new TestProductionHandler("path"), pathAbEmpty);
        grammar.addProduction(path, new TestProductionHandler("path"), pathAbsolute);
        grammar.addProduction(path, new TestProductionHandler("path"), pathNoScheme);
        grammar.addProduction(path, new TestProductionHandler("path"), pathRootless);
        grammar.addProduction(path, new TestProductionHandler("path"), pathEmpty);

        // path-rootless = segment-nz *( "/" segment )
        grammar.addProduction(pathRootless, new TestProductionHandler("path-rootless"), segmentNz);
        grammar.addProduction(pathRootless, new TestProductionHandler("path-rootless"), segmentNz, segmentSequence);
        
        // pchar = unreserved / pct-encoded / sub-delims / ":" / "@"

        // query = *( pchar / "/" / "?" )
        // query = *( unreserved / pct-encoded / sub-delims / ":" / "@" / "/" / "?" )
        for (final UriTerminalType unreserved : unreservedSymbols) {
            grammar.addProduction(query, new TestProductionHandler("query"), unreserved, query);
        }
        grammar.addProduction(query, new TestProductionHandler("query"), pctEncoded, query);
        for (final UriTerminalType terminalSymbol : subDelims) {
            grammar.addProduction(query, new TestProductionHandler("query"), terminalSymbol, query);
        }
        grammar.addProduction(query, new TestProductionHandler("query"), Colon, query);
        grammar.addProduction(query, new TestProductionHandler("query"), AtSign, query);
        grammar.addProduction(query, new TestProductionHandler("query"), ForwardSlash, query);
        grammar.addProduction(query, new TestProductionHandler("query"), QuestionMark, query);
        grammar.addProduction(query, new TestProductionHandler("query"));

        // absolute-URI = scheme ":" hier-part [ "?" query ]
        grammar.addProduction(absoluteUri, new TestProductionHandler("absolute-URI"), scheme, Colon, hierPart);
        grammar.addProduction(absoluteUri, new TestProductionHandler("absolute-URI"), scheme, Colon, hierPart, QuestionMark, query);

        // fragment = *( pchar / "/" / "?" )
        // fragment = *( unreserved / pct-encoded / sub-delims / ":" / "@" / "/" / "?" )
        grammar.addProduction(fragment, new TestProductionHandler("fragment"));
        for (final UriTerminalType unreserved : unreservedSymbols) {
            grammar.addProduction(fragment, new TestProductionHandler("fragment"), unreserved, fragment);
        }
        grammar.addProduction(fragment, new TestProductionHandler("fragment"), pctEncoded, fragment);
        for (final UriTerminalType terminalSymbol : subDelims) {
            grammar.addProduction(fragment, new TestProductionHandler("fragment"), terminalSymbol, fragment);
        }
        grammar.addProduction(fragment, new TestProductionHandler("fragment"), Colon, fragment);
        grammar.addProduction(fragment, new TestProductionHandler("fragment"), AtSign, fragment);
        grammar.addProduction(fragment, new TestProductionHandler("fragment"), ForwardSlash, fragment);
        grammar.addProduction(fragment, new TestProductionHandler("fragment"), QuestionMark, fragment);
        
        // userinfo = *( unreserved / pct-encoded / sub-delims / ":" )
        for (final UriTerminalType unreserved : unreservedSymbols) {
            grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), unreserved, userinfo);
        }
        grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), pctEncoded, userinfo);
        for (final UriTerminalType subDelim : subDelims) {
            grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), subDelim, userinfo);
        }
        grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), Colon, userinfo);
        grammar.addProduction(userinfo, new TestProductionHandler("userinfo"));

        // TODO: ambiguous with IPv4address
        // reg-name = *( unreserved / pct-encoded / sub-delims )
        for (final UriTerminalType unreserved : unreservedSymbols) {
            grammar.addProduction(regName, new TestProductionHandler("reg-name"), unreserved, regName);
        }
        grammar.addProduction(regName, new TestProductionHandler("reg-name"), pctEncoded, regName);
        for (final UriTerminalType subDelim : subDelims) {
            grammar.addProduction(regName, new TestProductionHandler("reg-name"), subDelim, regName);
        }
        grammar.addProduction(regName, new TestProductionHandler("reg-name"));

        // host = IP-literal / IPv4address / reg-name
//        grammar.addProduction(host, new TestProductionHandler("host"), ipLiteral);
//        grammar.addProduction(host, new TestProductionHandler("host"), ipv4address);
        grammar.addProduction(host, new TestProductionHandler("host"), regName);

        // port = *DIGIT
        grammar.addProduction(port, new TestProductionHandler("port"));
        grammar.addProduction(port, new TestProductionHandler("port"), Digit, port);

        // authority = [ userinfo "@" ] host [ ":" port ]
        grammar.addProduction(authority, new TestProductionHandler("authority"), host);
        grammar.addProduction(authority, new TestProductionHandler("authority"), host, Colon, port);
//        grammar.addProduction(authority, new TestProductionHandler("authority"), userinfo, AtSign, host);
//        grammar.addProduction(authority, new TestProductionHandler("authority"), userinfo, AtSign, host, Colon, port);

        // segment-nz-nc = 1*( unreserved / pct-encoded / sub-delims / "@" )
        for (final UriTerminalType symbol : unreservedSymbols) {
            grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), symbol);
            grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), symbol, segmentNzNc);
        }
        grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), pctEncoded);
        grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), pctEncoded, segmentNzNc);
        for (final UriTerminalType subDelim : subDelims) {
            grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), subDelim);
            grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), subDelim, segmentNzNc);
        }
        grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), AtSign);
        grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), AtSign, segmentNzNc);

        // segment-nz = 1*pchar
        // segment-nz = 1*( unreserved / pct-encoded / sub-delims / ":" / "@" )
        for (final UriTerminalType symbol : unreservedSymbols) {
            grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), symbol);
            grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), symbol, segmentNz);
        }
        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), pctEncoded);
        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), pctEncoded, segmentNz);
        for (final UriTerminalType subDelim : subDelims) {
            grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), subDelim);
            grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), subDelim, segmentNz);
        }
        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), Colon);
        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), Colon, segmentNz);
        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), AtSign);
        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), AtSign, segmentNz);
        
        // segment = *pchar
        // segment = *( unreserved / pct-encoded / sub-delims / ":" / "@" )
        grammar.addProduction(segment, new TestProductionHandler("segment"));
        grammar.addProduction(segment, new TestProductionHandler("segment"), segmentNz);
        
        // segment-sequence = *( "/" segment )
        grammar.addProduction(segmentSequence, new TestProductionHandler("segment-sequence"), ForwardSlash, segment, segmentSequence);
        grammar.addProduction(segmentSequence, new TestProductionHandler("segment-sequence"));

        // path-abempty = *( "/" segment )
        grammar.addProduction(pathAbEmpty, new TestProductionHandler("path-abempty"), segmentSequence);
        
        // path-absolute = "/" [ segment-nz *( "/" segment ) ]
        grammar.addProduction(pathAbsolute, new TestProductionHandler("path-absolute"), ForwardSlash);
        grammar.addProduction(pathAbsolute, new TestProductionHandler("path-absolute"), ForwardSlash, segmentNz, segmentSequence);
        
        // path-noscheme = segment-nz-nc *( "/" segment )
        grammar.addProduction(pathNoScheme, new TestProductionHandler("path-noscheme"), segmentNzNc);
        grammar.addProduction(pathNoScheme, new TestProductionHandler("path-noscheme"), segmentNzNc, segmentSequence);
        
        // path-empty = 0<pchar>
        grammar.addProduction(pathEmpty, new TestProductionHandler("path-empty"));

        // URI = scheme ":" hier-part [ "?" query ] [ "#" fragment ]
        grammar.addProduction(uri, new TestProductionHandler("URI"), scheme, Colon, hierPart);
        grammar.addProduction(uri, new TestProductionHandler("URI"), scheme, Colon, hierPart, QuestionMark, query);
        grammar.addProduction(uri, new TestProductionHandler("URI"), scheme, Colon, hierPart, NumberSign, fragment);
        grammar.addProduction(uri, new TestProductionHandler("URI"), scheme, Colon, hierPart, QuestionMark, query, NumberSign, fragment);
        
        // relative-part = "//" authority path-abempty / path-absolute / path-noscheme / path-empty
        grammar.addProduction(relativePart, new TestProductionHandler("relative-part"), ForwardSlash, ForwardSlash, authority, pathAbEmpty);
        grammar.addProduction(relativePart, new TestProductionHandler("relative-part"), pathAbsolute);
        grammar.addProduction(relativePart, new TestProductionHandler("relative-part"), pathNoScheme);
        grammar.addProduction(relativePart, new TestProductionHandler("relative-part"), pathEmpty);

        // relative-ref = relative-part [ "?" query ] [ "#" fragment ]
        grammar.addProduction(relativeRef, new TestProductionHandler("relative-ref"), relativePart);
        grammar.addProduction(relativeRef, new TestProductionHandler("relative-ref"), relativePart, QuestionMark, query);
        grammar.addProduction(relativeRef, new TestProductionHandler("relative-ref"), relativePart, NumberSign, fragment);
        grammar.addProduction(relativeRef, new TestProductionHandler("relative-ref"), relativePart, QuestionMark, query, NumberSign, fragment);
        
        // URI-reference = URI / relative-ref
        grammar.addProduction(uriReference, new TestProductionHandler("URI-reference"), uri);
        grammar.addProduction(uriReference, new TestProductionHandler("URI-reference"), relativeRef);
        
//        parser = grammar.createParser(uriReference, TestLexer.endOfFileSymbol);
    }
    
    @Test
    public void testPercentEncoded() {
        parser = grammar.createParser(pctEncoded, EndOfFile);
        
        for (int b = 0; b < 256; b++) {
            final Object ast = parser.parse(new UriLexer(String.format(Locale.ROOT, "%%%02x", b)));
            Assert.assertNotNull(ast);
        }
        
        try {
            parser.parse(new UriLexer("ab"));
            Assert.fail();
        } catch (final RuntimeException e) {
            // pass
        }
    }

    @Test
    public void testScheme() {
        parser = grammar.createParser(scheme, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer("http"));
        ast = parser.parse(new UriLexer("https"));
        ast = parser.parse(new UriLexer("file"));
        ast = parser.parse(new UriLexer("a"));
        ast = parser.parse(new UriLexer("z0+-."));
        
        final String reservedCharacters = "_~:/?#[]@!$&'()*,;=";
        for (final char c : reservedCharacters.toCharArray()) {
            try {
                final String s = "a" + String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
        }
        try {
            parser.parse(new UriLexer(""));
            Assert.fail();
        } catch (final RuntimeException e) {
            // pass
        }
        try {
            parser.parse(new UriLexer("0"));
            Assert.fail();
        } catch (final RuntimeException e) {
            // pass
        }
        try {
            parser.parse(new UriLexer("%00"));
            Assert.fail();
        } catch (final RuntimeException e) {
            // pass
        }
    }

    @Test
    public void testUserinfo() {
        parser = grammar.createParser(userinfo, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("example"));
        ast = parser.parse(new UriLexer("example:com"));
        ast = parser.parse(new UriLexer("foo-bar:example:com"));
        ast = parser.parse(new UriLexer("%00abyz0189-._~:!$&'()*+,;=%ff"));
        
        final String reservedCharacters = "/?#[]@";
        for (final char c : reservedCharacters.toCharArray()) {
            try {
                final String s = String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }

    @Test
    public void testHost() {
        parser = grammar.createParser(host, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("example"));
        ast = parser.parse(new UriLexer("example.com"));
        ast = parser.parse(new UriLexer("foo-bar.example.com"));
        ast = parser.parse(new UriLexer("foo-bar.example.com."));
        ast = parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%ff"));
        
        final String reservedCharacters = ":/?#[]@";
        for (final char c : reservedCharacters.toCharArray()) {
            try {
                final String s = String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }

    @Test
    public void testPort() {
        parser = grammar.createParser(port, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("80"));
        ast = parser.parse(new UriLexer("65535"));
        ast = parser.parse(new UriLexer("65536"));
        ast = parser.parse(new UriLexer(Long.toString(Long.MAX_VALUE)));
        
        final String reservedCharacters = "-._~:/?#[]@!$&'()*+,;=%";
        for (final char c : reservedCharacters.toCharArray()) {
            try {
                final String s = String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
        }
        try {
            parser.parse(new UriLexer("%00"));
            Assert.fail();
        } catch (final RuntimeException e) {
            // pass
        }
    }

    @Test
    public void testAuthority() {
        parser = grammar.createParser(authority, EndOfFile);
        Object ast;

        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("example"));
        ast = parser.parse(new UriLexer("example.com"));
        ast = parser.parse(new UriLexer("foo-bar.example.com"));
        ast = parser.parse(new UriLexer("foo-bar.example.com."));
        ast = parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%ff"));

        ast = parser.parse(new UriLexer(":80"));
        ast = parser.parse(new UriLexer("example:80"));
        ast = parser.parse(new UriLexer("example.com:80"));
        ast = parser.parse(new UriLexer("foo-bar.example.com:80"));
        ast = parser.parse(new UriLexer("foo-bar.example.com.:80"));
        ast = parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%ff:80"));

//        ast = parser.parse(new UriLexer("foo:bar@"));
//        ast = parser.parse(new UriLexer("foo:bar@example"));
//        ast = parser.parse(new UriLexer("foo:bar@example.com"));
//        ast = parser.parse(new UriLexer("foo:bar@foo-bar.example.com"));
//        ast = parser.parse(new UriLexer("foo:bar@foo-bar.example.com."));
//        ast = parser.parse(new UriLexer("foo:bar@%00abyz0189-._~!$&'()*+,;=%ff"));
//
//        ast = parser.parse(new UriLexer("foo:bar@:80"));
//        ast = parser.parse(new UriLexer("foo:bar@example:80"));
//        ast = parser.parse(new UriLexer("foo:bar@example.com:80"));
//        ast = parser.parse(new UriLexer("foo:bar@foo-bar.example.com:80"));
//        ast = parser.parse(new UriLexer("foo:bar@foo-bar.example.com.:80"));
//        ast = parser.parse(new UriLexer("foo:bar@%00abyz0189-._~!$&'()*+,;=%ff:80"));
    }
    
    @Test
    public void testSegment() {
        parser = grammar.createParser(segment, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("%00abyz0189-._~:@!$&'()*+,;=%ff"));
        
        final String reservedCharacters = "/?#[]";
        for (final char c : reservedCharacters.toCharArray()) {
            try {
                final String s = String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }
    
    @Test
    public void testSegmentSequence() {
        parser = grammar.createParser(segmentSequence, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
    }

    @Test
    public void testQuery() {
        parser = grammar.createParser(query, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("%00abyz0189-._~:/?@!$&'()*+,;=%ff"));
        
        final String reservedCharacters = "#[]";
        for (final char c : reservedCharacters.toCharArray()) {
            try {
                final String s = String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }
    
    @Test
    public void testFragment() {
        parser = grammar.createParser(fragment, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("%00abyz0189-._~:/?@!$&'()*+,;=%ff"));

        final String reservedCharacters = "#[]";
        for (final char c : reservedCharacters.toCharArray()) {
            try {
                final String s = String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }

    @Test
    @Ignore
    public void testRelativeRef() {
        parser = grammar.createParser(relativeRef, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer("//foo.bar.com"));
        ast = parser.parse(new UriLexer("//foo.bar.com?abc"));
        ast = parser.parse(new UriLexer("//foo.bar.com#abc"));
        ast = parser.parse(new UriLexer("//foo.bar.com?abc#abc"));
        
        ast = parser.parse(new UriLexer("//foo.bar.com/bar/baz"));
        ast = parser.parse(new UriLexer("//foo.bar.com/bar/baz?abc"));
        ast = parser.parse(new UriLexer("//foo.bar.com/bar/baz#abc"));
    }
    
    @Test
    @Ignore
    public void testURI() {
        parser = grammar.createParser(uri, EndOfFile);
        Object ast;
        
        ast = parser.parse(new UriLexer("https://foo.bar.com"));
        ast = parser.parse(new UriLexer("https://foo.bar.com?abc"));
        ast = parser.parse(new UriLexer("https://foo.bar.com#abc"));
        ast = parser.parse(new UriLexer("https://foo.bar.com?abc#abc"));
        
        ast = parser.parse(new UriLexer("https://foo.bar.com/bar/baz"));
        ast = parser.parse(new UriLexer("https://foo.bar.com/bar/baz?abc"));
        ast = parser.parse(new UriLexer("https://foo.bar.com/bar/baz#abc"));
    }

}
