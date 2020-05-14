package org.silnith.grammar;

import static org.silnith.grammar.uri.token.UriTerminalType.AlphaHex;
import static org.silnith.grammar.uri.token.UriTerminalType.AlphaNonHex;
import static org.silnith.grammar.uri.token.UriTerminalType.Ampersand;
import static org.silnith.grammar.uri.token.UriTerminalType.Apostrophe;
import static org.silnith.grammar.uri.token.UriTerminalType.Asterisk;
import static org.silnith.grammar.uri.token.UriTerminalType.AtSign;
import static org.silnith.grammar.uri.token.UriTerminalType.Colon;
import static org.silnith.grammar.uri.token.UriTerminalType.Comma;
import static org.silnith.grammar.uri.token.UriTerminalType.Digit;
import static org.silnith.grammar.uri.token.UriTerminalType.Dollar;
import static org.silnith.grammar.uri.token.UriTerminalType.EndOfFile;
import static org.silnith.grammar.uri.token.UriTerminalType.Equals;
import static org.silnith.grammar.uri.token.UriTerminalType.ExclamationMark;
import static org.silnith.grammar.uri.token.UriTerminalType.ForwardSlash;
import static org.silnith.grammar.uri.token.UriTerminalType.Hyphen;
import static org.silnith.grammar.uri.token.UriTerminalType.LeftBracket;
import static org.silnith.grammar.uri.token.UriTerminalType.LeftParenthesis;
import static org.silnith.grammar.uri.token.UriTerminalType.NumberSign;
import static org.silnith.grammar.uri.token.UriTerminalType.Percent;
import static org.silnith.grammar.uri.token.UriTerminalType.Period;
import static org.silnith.grammar.uri.token.UriTerminalType.Plus;
import static org.silnith.grammar.uri.token.UriTerminalType.QuestionMark;
import static org.silnith.grammar.uri.token.UriTerminalType.RightBracket;
import static org.silnith.grammar.uri.token.UriTerminalType.RightParenthesis;
import static org.silnith.grammar.uri.token.UriTerminalType.Semicolon;
import static org.silnith.grammar.uri.token.UriTerminalType.Tilde;
import static org.silnith.grammar.uri.token.UriTerminalType.Underscore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.silnith.grammar.uri.production.FragmentProductionHandler;
import org.silnith.grammar.uri.production.PercentEncodedProductionHandler;
import org.silnith.grammar.uri.production.PortProductionHandler;
import org.silnith.grammar.uri.production.QueryProductionHandler;
import org.silnith.grammar.uri.production.SchemeProductionHandler;
import org.silnith.grammar.uri.production.SegmentProductionHandler;
import org.silnith.grammar.uri.token.UriTerminalType;

public class UrlParserTest {

    private static final ExecutorService executor = Executors.newFixedThreadPool(8);
    
    private static final String all = "%00abyz0189-._~:/?#[]@!$&'()*+,;=%ff";
    private static final String unreserved = "abyz0189-._~";
    private static final String genDelims = ":/?#[]@";
    private static final String subDelims = "!$&'()*+,;=";
    private static final String pchar = "abyz0189-._~:@!$&'()*+,;=";
    private static final String nonPchar = "/?#[]";
    
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

    private NonTerminalSymbol pathAbEmpty;
    private NonTerminalSymbol pathAbsolute;
    private NonTerminalSymbol pathRootless;
    private NonTerminalSymbol pathEmpty;
    
    @Test
    @Ignore
    public void testRegularExpression() {
        final Pattern urlPattern = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
        final Matcher matcher = urlPattern.matcher("https://foo-bar.example.com:65536/abyz/abyz/abyz?query=foo&query=bar#fragment");
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
//        grammar = new Grammar<UriTerminalType>();
        grammar = new Grammar<UriTerminalType>(new EnumSetFactory<>(UriTerminalType.class));
        
        pctEncoded = grammar.getNonTerminalSymbol("pct-encoded");
//        final NonTerminalSymbol unreserved = grammar.getNonTerminalSymbol("unreserved");

        uri = grammar.getNonTerminalSymbol("URI");
        scheme = grammar.getNonTerminalSymbol("scheme");
        final NonTerminalSymbol hierPart = grammar.getNonTerminalSymbol("hier-part");
        query = grammar.getNonTerminalSymbol("query");
        fragment = grammar.getNonTerminalSymbol("fragment");

        authority = grammar.getNonTerminalSymbol("authority");
        pathAbEmpty = grammar.getNonTerminalSymbol("path-abempty");
        pathAbsolute = grammar.getNonTerminalSymbol("path-absolute");
        pathRootless = grammar.getNonTerminalSymbol("path-rootless");
        pathEmpty = grammar.getNonTerminalSymbol("path-empty");

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
        
        final NonTerminalSymbol path = grammar.getNonTerminalSymbol("path");
        final NonTerminalSymbol pathNoScheme = grammar.getNonTerminalSymbol("path-noscheme");
        segment = grammar.getNonTerminalSymbol("segment");
        final NonTerminalSymbol segmentNz = grammar.getNonTerminalSymbol("segment-nz");
        final NonTerminalSymbol segmentNzNc = grammar.getNonTerminalSymbol("segment-nz-nc");
        
        segmentSequence = grammar.getNonTerminalSymbol("segment-sequence");
        
        uriReference = grammar.getNonTerminalSymbol("URI-reference");
        relativeRef = grammar.getNonTerminalSymbol("relative-ref");
        final NonTerminalSymbol relativePart = grammar.getNonTerminalSymbol("relative-part");

        final NonTerminalSymbol absoluteUri = grammar.getNonTerminalSymbol("absolute-URI");
        
        // gen-delims = ":" / "/" / "?" / "#" / "[" / "]" / "@"
        final Collection<UriTerminalType> genDelims = new ArrayList<>(7);
        genDelims.addAll(Arrays.asList(Colon, ForwardSlash, QuestionMark, NumberSign, LeftBracket, RightBracket, AtSign));

        // sub-delims = "!" / "$" / "&" / "'" / "(" / ")" / "*" / "+" / "," / ";" / "="
        final Collection<UriTerminalType> subDelims = new ArrayList<>(11);
        subDelims.addAll(Arrays.asList(ExclamationMark, Dollar, Ampersand, Apostrophe, LeftParenthesis, RightParenthesis,
                Asterisk, Plus, Comma, Semicolon, Equals));
        
        final Collection<UriTerminalType> unreservedSymbols = new ArrayList<>(7);
        unreservedSymbols.addAll(Arrays.asList(Digit, AlphaHex, AlphaNonHex, Hyphen, Period, Underscore, Tilde));

        // pct-encoded = "%" HEXDIG HEXDIG
        grammar.addProduction(pctEncoded, PercentEncodedProductionHandler.getInstance(), Percent, Digit, Digit);
        grammar.addProduction(pctEncoded, PercentEncodedProductionHandler.getInstance(), Percent, Digit, AlphaHex);
        grammar.addProduction(pctEncoded, PercentEncodedProductionHandler.getInstance(), Percent, AlphaHex, Digit);
        grammar.addProduction(pctEncoded, PercentEncodedProductionHandler.getInstance(), Percent, AlphaHex, AlphaHex);
        
        // reserved = gen-delims / sub-delims
        
        // unreserved = ALPHA / DIGIT / "-" / "." / "_" / "~"
        
        // hier-part = "//" authority path-abempty / path-absolute / path-rootless / path-empty
        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), ForwardSlash, ForwardSlash, authority, pathAbEmpty);
        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathAbsolute);
        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathRootless);
        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathEmpty);
        
        // scheme = ALPHA *( ALPHA / DIGIT / "+" / "-" / ".")
        grammar.addProduction(scheme, SchemeProductionHandler.getInstance(), AlphaHex);
        grammar.addProduction(scheme, SchemeProductionHandler.getInstance(), AlphaNonHex);
        grammar.addProduction(scheme, SchemeProductionHandler.getInstance(), scheme, AlphaHex);
        grammar.addProduction(scheme, SchemeProductionHandler.getInstance(), scheme, AlphaNonHex);
        grammar.addProduction(scheme, SchemeProductionHandler.getInstance(), scheme, Digit);
        grammar.addProduction(scheme, SchemeProductionHandler.getInstance(), scheme, Plus);
        grammar.addProduction(scheme, SchemeProductionHandler.getInstance(), scheme, Hyphen);
        grammar.addProduction(scheme, SchemeProductionHandler.getInstance(), scheme, Period);

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
        grammar.addProduction(pathRootless, new TestProductionHandler("path-rootless"), segmentNz, segmentSequence);
        
        // pchar = unreserved / pct-encoded / sub-delims / ":" / "@"

        // query = *( pchar / "/" / "?" )
        // query = *( unreserved / pct-encoded / sub-delims / ":" / "@" / "/" / "?" )
        for (final UriTerminalType unreserved : unreservedSymbols) {
            grammar.addProduction(query, QueryProductionHandler.getInstance(), unreserved, query);
        }
        grammar.addProduction(query, QueryProductionHandler.getInstance(), pctEncoded, query);
        for (final UriTerminalType subDelim : subDelims) {
            grammar.addProduction(query, QueryProductionHandler.getInstance(), subDelim, query);
        }
        grammar.addProduction(query, QueryProductionHandler.getInstance(), Colon, query);
        grammar.addProduction(query, QueryProductionHandler.getInstance(), AtSign, query);
        grammar.addProduction(query, QueryProductionHandler.getInstance(), ForwardSlash, query);
        grammar.addProduction(query, QueryProductionHandler.getInstance(), QuestionMark, query);
        grammar.addProduction(query, QueryProductionHandler.getInstance());

        // absolute-URI = scheme ":" hier-part [ "?" query ]
        grammar.addProduction(absoluteUri, new TestProductionHandler("absolute-URI"), scheme, Colon, hierPart);
        grammar.addProduction(absoluteUri, new TestProductionHandler("absolute-URI"), scheme, Colon, hierPart, QuestionMark, query);

        // fragment = *( pchar / "/" / "?" )
        // fragment = *( unreserved / pct-encoded / sub-delims / ":" / "@" / "/" / "?" )
        for (final UriTerminalType unreserved : unreservedSymbols) {
            grammar.addProduction(fragment, FragmentProductionHandler.getInstance(), unreserved, fragment);
        }
        grammar.addProduction(fragment, FragmentProductionHandler.getInstance(), pctEncoded, fragment);
        for (final UriTerminalType subDelim : subDelims) {
            grammar.addProduction(fragment, FragmentProductionHandler.getInstance(), subDelim, fragment);
        }
        grammar.addProduction(fragment, FragmentProductionHandler.getInstance(), Colon, fragment);
        grammar.addProduction(fragment, FragmentProductionHandler.getInstance(), AtSign, fragment);
        grammar.addProduction(fragment, FragmentProductionHandler.getInstance(), ForwardSlash, fragment);
        grammar.addProduction(fragment, FragmentProductionHandler.getInstance(), QuestionMark, fragment);
        grammar.addProduction(fragment, FragmentProductionHandler.getInstance());
        
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
        grammar.addProduction(port, PortProductionHandler.getInstance());
        grammar.addProduction(port, PortProductionHandler.getInstance(), Digit, port);

        // authority = [ userinfo "@" ] host [ ":" port ]
        grammar.addProduction(authority, new TestProductionHandler("authority"), host);
        grammar.addProduction(authority, new TestProductionHandler("authority"), host, Colon, port);
//        grammar.addProduction(authority, new TestProductionHandler("authority"), userinfo, AtSign, host);
//        grammar.addProduction(authority, new TestProductionHandler("authority"), userinfo, AtSign, host, Colon, port);

        // segment-nz-nc = 1*( unreserved / pct-encoded / sub-delims / "@" )
        for (final UriTerminalType unreserved : unreservedSymbols) {
            grammar.addProduction(segmentNzNc, SegmentProductionHandler.getInstance(), unreserved);
            grammar.addProduction(segmentNzNc, SegmentProductionHandler.getInstance(), unreserved, segmentNzNc);
        }
        grammar.addProduction(segmentNzNc, SegmentProductionHandler.getInstance(), pctEncoded);
        grammar.addProduction(segmentNzNc, SegmentProductionHandler.getInstance(), pctEncoded, segmentNzNc);
        for (final UriTerminalType subDelim : subDelims) {
            grammar.addProduction(segmentNzNc, SegmentProductionHandler.getInstance(), subDelim);
            grammar.addProduction(segmentNzNc, SegmentProductionHandler.getInstance(), subDelim, segmentNzNc);
        }
        grammar.addProduction(segmentNzNc, SegmentProductionHandler.getInstance(), AtSign);
        grammar.addProduction(segmentNzNc, SegmentProductionHandler.getInstance(), AtSign, segmentNzNc);

        // segment-nz = 1*pchar
        // segment-nz = 1*( unreserved / pct-encoded / sub-delims / ":" / "@" )
        for (final UriTerminalType unreserved : unreservedSymbols) {
            grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), unreserved);
            grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), unreserved, segmentNz);
        }
        grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), pctEncoded);
        grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), pctEncoded, segmentNz);
        for (final UriTerminalType subDelim : subDelims) {
            grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), subDelim);
            grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), subDelim, segmentNz);
        }
        grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), Colon);
        grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), Colon, segmentNz);
        grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), AtSign);
        grammar.addProduction(segmentNz, SegmentProductionHandler.getInstance(), AtSign, segmentNz);
        
        // segment = *pchar
        // segment = *( unreserved / pct-encoded / sub-delims / ":" / "@" )
        grammar.addProduction(segment, SegmentProductionHandler.getInstance());
        grammar.addProduction(segment, SegmentProductionHandler.getInstance(), segmentNz);
        
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
    }
    
    @Test
    public void testPercentEncodedSerial() {
        final NonTerminalSymbol startSymbol = pctEncoded;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testPercentEncoded(parser);
    }
    
    @Test
    public void testPercentEncodedParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(pctEncoded, EndOfFile, executor);
        testPercentEncoded(parser);
    }
    
    private void testPercentEncoded(final Parser<UriTerminalType> parser) {
        // pct-encoded = "%" HEXDIG HEXDIG
        for (int b = 0; b < 256; b++) {
            final Object ast = parser.parse(new UriLexer(String.format(Locale.ROOT, "%%%02X", b)));
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
    public void testSchemeSerial() {
        final NonTerminalSymbol startSymbol = scheme;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testScheme(parser);
    }
    
    @Test
    public void testSchemeParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(scheme, EndOfFile, executor);
        testScheme(parser);
    }
    
    private void testScheme(final Parser<UriTerminalType> parser) {
        // scheme = ALPHA *( ALPHA / DIGIT / "+" / "-" / ".")
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
    public void testUserinfoSerial() {
        final NonTerminalSymbol startSymbol = userinfo;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testUserinfo(parser);
    }
    
    @Test
    public void testUserinfoParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(userinfo, EndOfFile, executor);
        testUserinfo(parser);
    }
    
    private void testUserinfo(final Parser<UriTerminalType> parser) {
        // userinfo = *( unreserved / pct-encoded / sub-delims / ":" )
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("example"));
        ast = parser.parse(new UriLexer("example:com"));
        ast = parser.parse(new UriLexer("foo-bar:example:com"));
        ast = parser.parse(new UriLexer("%00abyz0189-._~:!$&'()*+,;=%FF"));
        
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
    public void testHostSerial() {
        final NonTerminalSymbol startSymbol = host;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testHost(parser);
    }
    
    @Test
    public void testHostParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(host, EndOfFile, executor);
        testHost(parser);
    }
    
    private void testHost(final Parser<UriTerminalType> parser) {
        // host = IP-literal / IPv4address / reg-name
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("example"));
        ast = parser.parse(new UriLexer("example.com"));
        ast = parser.parse(new UriLexer("foo-bar.example.com"));
        ast = parser.parse(new UriLexer("foo-bar.example.com."));
        ast = parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%FF"));
        
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
    public void testPortSerial() {
        final NonTerminalSymbol startSymbol = port;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testPort(parser);
    }
    
    @Test
    public void testPortParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(port, EndOfFile, executor);
        testPort(parser);
    }

    private void testPort(final Parser<UriTerminalType> parser) {
        // port = *DIGIT
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
    public void testAuthoritySerial() {
        final NonTerminalSymbol startSymbol = authority;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testAuthority(parser);
    }
    
    @Test
    public void testAuthorityParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(authority, EndOfFile, executor);
        testAuthority(parser);
    }
    
    private void testAuthority(final Parser<UriTerminalType> parser) {
        // authority = [ userinfo "@" ] host [ ":" port ]
        Object ast;

        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("example"));
        ast = parser.parse(new UriLexer("example.com"));
        ast = parser.parse(new UriLexer("foo-bar.example.com"));
        ast = parser.parse(new UriLexer("foo-bar.example.com."));
        ast = parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%FF"));

        ast = parser.parse(new UriLexer(":80"));
        ast = parser.parse(new UriLexer("example:80"));
        ast = parser.parse(new UriLexer("example.com:80"));
        ast = parser.parse(new UriLexer("foo-bar.example.com:80"));
        ast = parser.parse(new UriLexer("foo-bar.example.com.:80"));
        ast = parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%FF:80"));

//        ast = parser.parse(new UriLexer("foo:bar@"));
//        ast = parser.parse(new UriLexer("foo:bar@example"));
//        ast = parser.parse(new UriLexer("foo:bar@example.com"));
//        ast = parser.parse(new UriLexer("foo:bar@foo-bar.example.com"));
//        ast = parser.parse(new UriLexer("foo:bar@foo-bar.example.com."));
//        ast = parser.parse(new UriLexer("foo:bar@%00abyz0189-._~!$&'()*+,;=%FF"));
//
//        ast = parser.parse(new UriLexer("foo:bar@:80"));
//        ast = parser.parse(new UriLexer("foo:bar@example:80"));
//        ast = parser.parse(new UriLexer("foo:bar@example.com:80"));
//        ast = parser.parse(new UriLexer("foo:bar@foo-bar.example.com:80"));
//        ast = parser.parse(new UriLexer("foo:bar@foo-bar.example.com.:80"));
//        ast = parser.parse(new UriLexer("foo:bar@%00abyz0189-._~!$&'()*+,;=%FF:80"));
    }
    
    @Test
    public void testSegmentSerial() {
        final NonTerminalSymbol startSymbol = segment;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testSegment(parser);
    }
    
    @Test
    public void testSegmentParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(segment, EndOfFile, executor);
        testSegment(parser);
    }
    
    private void testSegment(final Parser<UriTerminalType> parser) {
        // segment = *pchar
        // segment = *( unreserved / pct-encoded / sub-delims / ":" / "@" )
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("%00abyz0189-._~:@!$&'()*+,;=%FF"));
        
        final String reservedCharacters = "/?#[]";
        for (final char c : reservedCharacters.toCharArray()) {
            try {
                final String s = String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
            try {
                final String s = "foo" + String.valueOf(c);
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
            try {
                final String s = String.valueOf(c) + "foo";
                parser.parse(new UriLexer(s));
                Assert.fail(s);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }
    
    @Test
    public void testSegmentSequenceSerial() {
        final NonTerminalSymbol startSymbol = segmentSequence;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testSegmentSequence(parser);
    }
    
    @Test
    public void testSegmentSequenceParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(segmentSequence, EndOfFile, executor);
        testSegmentSequence(parser);
    }
    
    private void testSegmentSequence(final Parser<UriTerminalType> parser) {
        // segment-sequence = *( "/" segment )
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("/"));
        ast = parser.parse(new UriLexer("//"));
        ast = parser.parse(new UriLexer("///"));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
    }
    
    @Test
    public void testQuerySerial() {
        final NonTerminalSymbol startSymbol = query;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testQuery(parser);
    }
    
    @Test
    public void testQueryParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(query, EndOfFile, executor);
        testQuery(parser);
    }

    private void testQuery(final Parser<UriTerminalType> parser) {
        // query = *( pchar / "/" / "?" )
        // query = *( unreserved / pct-encoded / sub-delims / ":" / "@" / "/" / "?" )
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("%00abyz0189-._~:/?@!$&'()*+,;=%FF"));
        
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
    public void testFragmentSerial() {
        final NonTerminalSymbol startSymbol = fragment;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testFragment(parser);
    }
    
    @Test
    public void testFragmentParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(fragment, EndOfFile, executor);
        testFragment(parser);
    }
    
    private void testFragment(final Parser<UriTerminalType> parser) {
        // fragment = *( pchar / "/" / "?" )
        // fragment = *( unreserved / pct-encoded / sub-delims / ":" / "@" / "/" / "?" )
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("%00abyz0189-._~:/?@!$&'()*+,;=%FF"));

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

    @Ignore
    @Test
    public void testRelativeRefSerial() {
        final NonTerminalSymbol startSymbol = relativeRef;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testRelativeRef(parser);
    }

    @Ignore
    @Test
    public void testRelativeRefParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(relativeRef, EndOfFile, executor);
        testRelativeRef(parser);
    }

    private void testRelativeRef(final Parser<UriTerminalType> parser) {
        // relative-ref = relative-part [ "?" query ] [ "#" fragment ]
        Object ast;
        
        ast = parser.parse(new UriLexer("//foo.bar.com"));
        ast = parser.parse(new UriLexer("//foo.bar.com?abc"));
        ast = parser.parse(new UriLexer("//foo.bar.com#abc"));
        ast = parser.parse(new UriLexer("//foo.bar.com?abc#abc"));
        
        ast = parser.parse(new UriLexer("//foo.bar.com/bar/baz"));
        ast = parser.parse(new UriLexer("//foo.bar.com/bar/baz?abc"));
        ast = parser.parse(new UriLexer("//foo.bar.com/bar/baz#abc"));
    }

    @Ignore
    @Test
    public void testURISerial() {
        final NonTerminalSymbol startSymbol = uri;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testURI(parser);
    }

    @Ignore
    @Test
    public void testURIParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(uri, EndOfFile, executor);
        testURI(parser);
    }
    
    private void testURI(final Parser<UriTerminalType> parser) {
        // URI = scheme ":" hier-part [ "?" query ] [ "#" fragment ]
        Object ast;
        
        ast = parser.parse(new UriLexer("https://foo.bar.com"));
        ast = parser.parse(new UriLexer("https://foo.bar.com?abc"));
        ast = parser.parse(new UriLexer("https://foo.bar.com#abc"));
        ast = parser.parse(new UriLexer("https://foo.bar.com?abc#abc"));
        
        ast = parser.parse(new UriLexer("https://foo.bar.com/bar/baz"));
        ast = parser.parse(new UriLexer("https://foo.bar.com/bar/baz?abc"));
        ast = parser.parse(new UriLexer("https://foo.bar.com/bar/baz#abc"));
    }
    
    @Test
    public void testPathAbEmptySerial() {
        final NonTerminalSymbol startSymbol = pathAbEmpty;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testPathAbEmpty(parser);
    }

    @Test
    public void testPathAbEmptyParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(pathAbEmpty, EndOfFile, executor);
        testPathAbEmpty(parser);
    }

    private void testPathAbEmpty(final Parser<UriTerminalType> parser) {
        // path-abempty = *( "/" segment )
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        ast = parser.parse(new UriLexer("/"));
        ast = parser.parse(new UriLexer("//"));
        ast = parser.parse(new UriLexer("///"));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("//abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/"));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=//"));
        ast = parser.parse(new UriLexer("//abyz0189%2F-._~:@!$&'()*+,;=//"));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
        
        for (final String str : Arrays.asList("foo", "foo/")) {
            try {
                ast = parser.parse(new UriLexer(str));
                Assert.fail(str);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }

    @Test
    public void testPathAbsoluteSerial() {
        final NonTerminalSymbol startSymbol = pathAbsolute;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testPathAbsolute(parser);
    }
    
    @Test
    public void testPathAbsoluteParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(pathAbsolute, EndOfFile, executor);
        testPathAbsolute(parser);
    }
    
    private void testPathAbsolute(final Parser<UriTerminalType> parser) {
        // path-absolute = "/" [ segment-nz *( "/" segment ) ]
        Object ast;
        
        ast = parser.parse(new UriLexer("/"));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/"));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
        
        for (final String str : Arrays.asList("", "//", "///", "//foo", "foo", "foo/")) {
            try {
                ast = parser.parse(new UriLexer(str));
                Assert.fail(str);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }

    @Test
    public void testPathRootlessSerial() {
        final NonTerminalSymbol startSymbol = pathRootless;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testPathRootless(parser);
    }
    
    @Test
    public void testPathRootlessParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(pathRootless, EndOfFile, executor);
        testPathRootless(parser);
    }
    
    private void testPathRootless(final Parser<UriTerminalType> parser) {
        // path-rootless = segment-nz *( "/" segment )
        Object ast;
        
        ast = parser.parse(new UriLexer("a"));
        ast = parser.parse(new UriLexer("abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
        ast = parser.parse(new UriLexer("abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;=/abyz0189%2F-._~:@!$&'()*+,;="));
        
        for (final String str : Arrays.asList("", "/", "//")) {
            try {
                ast = parser.parse(new UriLexer(str));
                Assert.fail(str);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }

    @Test
    public void testPathEmptySerial() {
        final NonTerminalSymbol startSymbol = pathEmpty;
        grammar.setStartSymbol(startSymbol);
        grammar.setEndOfFileSymbol(EndOfFile);
        final Parser<UriTerminalType> parser = grammar.createParser1();
        testPathEmpty(parser);
    }
    
    @Test
    public void testPathEmptyParallel() throws InterruptedException, ExecutionException {
        final Parser<UriTerminalType> parser = grammar.threadedCreateParser(pathEmpty, EndOfFile, executor);
        testPathEmpty(parser);
    }
    
    private void testPathEmpty(final Parser<UriTerminalType> parser) {
        // path-empty = 0<pchar>
        Object ast;
        
        ast = parser.parse(new UriLexer(""));
        
        for (final String str : Arrays.asList("/", "//", "/foo", "//foo", "foo", "foo/")) {
            try {
                ast = parser.parse(new UriLexer(str));
                Assert.fail(str);
            } catch (final RuntimeException e) {
                // pass
            }
        }
    }

}
