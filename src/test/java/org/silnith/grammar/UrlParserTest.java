package org.silnith.grammar;

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
import org.silnith.grammar.uri.AlphaHex;
import org.silnith.grammar.uri.AlphaNonHex;
import org.silnith.grammar.uri.Ampersand;
import org.silnith.grammar.uri.Apostrophe;
import org.silnith.grammar.uri.Asterisk;
import org.silnith.grammar.uri.AtSign;
import org.silnith.grammar.uri.Colon;
import org.silnith.grammar.uri.Comma;
import org.silnith.grammar.uri.Digit;
import org.silnith.grammar.uri.Dollar;
import org.silnith.grammar.uri.Equals;
import org.silnith.grammar.uri.ExclamationMark;
import org.silnith.grammar.uri.ForwardSlash;
import org.silnith.grammar.uri.Hyphen;
import org.silnith.grammar.uri.LeftBracket;
import org.silnith.grammar.uri.LeftParenthesis;
import org.silnith.grammar.uri.NumberSign;
import org.silnith.grammar.uri.Percent;
import org.silnith.grammar.uri.Period;
import org.silnith.grammar.uri.Plus;
import org.silnith.grammar.uri.QuestionMark;
import org.silnith.grammar.uri.RightBracket;
import org.silnith.grammar.uri.RightParenthesis;
import org.silnith.grammar.uri.Semicolon;
import org.silnith.grammar.uri.Tilde;
import org.silnith.grammar.uri.Underscore;
import org.silnith.grammar.uri.UriTerminal;

public class UrlParserTest {

    private Parser<UriTerminal> parser;
    private Grammar<UriTerminal> grammar;
    private NonTerminalSymbol uriReference;
    private NonTerminalSymbol relativeRef;
    private NonTerminalSymbol uri;
    private NonTerminalSymbol pctEncoded;
    private NonTerminalSymbol host;
    private NonTerminalSymbol port;
    private NonTerminalSymbol userinfo;
    private NonTerminalSymbol authority;

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
        grammar = new Grammar<UriTerminal>();
        
        pctEncoded = grammar.getNonTerminalSymbol("pct-encoded");
//        final NonTerminalSymbol unreserved = grammar.getNonTerminalSymbol("unreserved");
        final NonTerminalSymbol digit = grammar.getNonTerminalSymbol("DIGIT");
        final NonTerminalSymbol alphaHex = grammar.getNonTerminalSymbol("ALPHA-HEX");
        final NonTerminalSymbol alphaNonHex = grammar.getNonTerminalSymbol("ALPHA-NON-HEX");

        uri = grammar.getNonTerminalSymbol("URI");
        final NonTerminalSymbol scheme = grammar.getNonTerminalSymbol("scheme");
        final NonTerminalSymbol hierPart = grammar.getNonTerminalSymbol("hier-part");
        final NonTerminalSymbol query = grammar.getNonTerminalSymbol("query");
        final NonTerminalSymbol fragment = grammar.getNonTerminalSymbol("fragment");

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
        final NonTerminalSymbol segment = grammar.getNonTerminalSymbol("segment");
        final NonTerminalSymbol segmentNz = grammar.getNonTerminalSymbol("segment-nz");
        final NonTerminalSymbol segmentNzNc = grammar.getNonTerminalSymbol("segment-nz-nc");
        
        final NonTerminalSymbol segmentSequence = grammar.getNonTerminalSymbol("segment-sequence");
        final NonTerminalSymbol segmentSequenceElement = grammar.getNonTerminalSymbol("segment-sequence-element");
        
        final NonTerminalSymbol segmentPrime = grammar.getNonTerminalSymbol("segment'");
        
        final NonTerminalSymbol queryPrime = grammar.getNonTerminalSymbol("query'");
        
        final NonTerminalSymbol fragmentPrime = grammar.getNonTerminalSymbol("fragment'");
        
        uriReference = grammar.getNonTerminalSymbol("URI-reference");
        relativeRef = grammar.getNonTerminalSymbol("relative-ref");
        final NonTerminalSymbol relativePart = grammar.getNonTerminalSymbol("relative-part");

        final NonTerminalSymbol absoluteUri = grammar.getNonTerminalSymbol("absolute-URI");

        final TerminalSymbol colon = Colon.getInstance();
        final TerminalSymbol forwardSlash = ForwardSlash.getInstance();
        final TerminalSymbol questionMark = QuestionMark.getInstance();
        final TerminalSymbol numberSign = NumberSign.getInstance();
        final TerminalSymbol leftBracket = LeftBracket.getInstance();
        final TerminalSymbol rightBracket = RightBracket.getInstance();
        final TerminalSymbol atSign = AtSign.getInstance();
        // gen-delims = ":" / "/" / "?" / "#" / "[" / "]" / "@"
        final Collection<TerminalSymbol> genDelims = new ArrayList<TerminalSymbol>(7);
        genDelims.addAll(Arrays.asList(colon, forwardSlash, questionMark, numberSign, leftBracket, rightBracket, atSign));

        final TerminalSymbol exclamationMark = ExclamationMark.getInstance();
        final TerminalSymbol dollarSign = Dollar.getInstance();
        final TerminalSymbol ampersand = Ampersand.getInstance();
        final TerminalSymbol singleQuote = Apostrophe.getInstance();
        final TerminalSymbol leftParen = LeftParenthesis.getInstance();
        final TerminalSymbol rightParen = RightParenthesis.getInstance();
        final TerminalSymbol asterisk = Asterisk.getInstance();
        final TerminalSymbol plus = Plus.getInstance();
        final TerminalSymbol comma = Comma.getInstance();
        final TerminalSymbol semicolon = Semicolon.getInstance();
        final TerminalSymbol equals = Equals.getInstance();
        // sub-delims = "!" / "$" / "&" / "'" / "(" / ")" / "*" / "+" / "," / ";" / "="
        final Collection<TerminalSymbol> subDelims = new ArrayList<TerminalSymbol>(11);
        subDelims.addAll(Arrays.asList(exclamationMark, dollarSign, ampersand, singleQuote, leftParen, rightParen, asterisk, plus, comma, semicolon, equals));
        
        final TerminalSymbol hyphen = Hyphen.getInstance();
        final TerminalSymbol period = Period.getInstance();
        final TerminalSymbol underscore = Underscore.getInstance();
        final TerminalSymbol tilde = Tilde.getInstance();
//        final List<TerminalSymbol> allDigits = new ArrayList<TerminalSymbol>(10);
        for (final char c : Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')) {
            final TerminalSymbol terminalSymbol = Digit.getInstance(c);
//            allDigits.add(terminalSymbol);
            grammar.addProduction(digit, new TestProductionHandler("DIGIT"), terminalSymbol);
        }
        for (char c = 'a'; c <= 'f'; c++) {
            final char lowerCase = Character.toLowerCase(c);
            final char upperCase = Character.toUpperCase(c);
            grammar.addProduction(alphaHex, new TestProductionHandler("ALPHA-HEX"), AlphaHex.getInstance(lowerCase));
            grammar.addProduction(alphaHex, new TestProductionHandler("ALPHA-HEX"), AlphaHex.getInstance(upperCase));
        }
        for (char c = 'g'; c <= 'z'; c++) {
            final char lowerCase = Character.toLowerCase(c);
            final char upperCase = Character.toUpperCase(c);
            grammar.addProduction(alphaNonHex, new TestProductionHandler("ALPHA-NON-HEX"), AlphaNonHex.getInstance(lowerCase));
            grammar.addProduction(alphaNonHex, new TestProductionHandler("ALPHA-NON-HEX"), AlphaNonHex.getInstance(upperCase));
        }
        final Collection<Symbol> unreservedSymbols = new ArrayList<Symbol>();
        unreservedSymbols.add(digit);
        unreservedSymbols.add(alphaHex);
        unreservedSymbols.add(alphaNonHex);
        unreservedSymbols.add(hyphen);
        unreservedSymbols.add(period);
        unreservedSymbols.add(underscore);
        unreservedSymbols.add(tilde);

        // HEXDIG =
        
        // ALPHA = 

        final TerminalSymbol percentSign = Percent.getInstance();
        // pct-encoded = "%" HEXDIG HEXDIG
        grammar.addProduction(pctEncoded, new TestProductionHandler("pct-encoded"), percentSign, digit, digit);
        grammar.addProduction(pctEncoded, new TestProductionHandler("pct-encoded"), percentSign, digit, alphaHex);
        grammar.addProduction(pctEncoded, new TestProductionHandler("pct-encoded"), percentSign, alphaHex, digit);
        grammar.addProduction(pctEncoded, new TestProductionHandler("pct-encoded"), percentSign, alphaHex, alphaHex);
        
        // reserved = gen-delims / sub-delims
        
        // unreserved = ALPHA / DIGIT / "-" / "." / "_" / "~"
        
        // URI = scheme ":" hier-part [ "?" query ] [ "#" fragment ]
//        grammar.addProduction(uri, new TestProductionHandler("URI"), scheme, colon, hierPart);
//        grammar.addProduction(uri, new TestProductionHandler("URI"), scheme, colon, hierPart, questionMark, query);
//        grammar.addProduction(uri, new TestProductionHandler("URI"), scheme, colon, hierPart, numberSign, fragment);
//        grammar.addProduction(uri, new TestProductionHandler("URI"), scheme, colon, hierPart, questionMark, query, numberSign, fragment);
        
        // hier-part = "//" authority path-abempty / path-absolute / path-rootless / path-empty
//        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), forwardSlash, forwardSlash, authority, pathAbEmpty);
//        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathAbsolute);
//        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathRootless);
//        grammar.addProduction(hierPart, new TestProductionHandler("hier-part"), pathEmpty);
        
        // scheme = ALPHA *( ALPHA / DIGIT / "+" / "-" / ".")
//        grammar.addProduction(scheme, new TestProductionHandler("scheme"), alphaHex, schemePrime);
//        grammar.addProduction(scheme, new TestProductionHandler("scheme"), alphaNonHex, schemePrime);
//        grammar.addProduction(schemePrime, new TestProductionHandler("scheme'"), alphaHex, schemePrime);
//        grammar.addProduction(schemePrime, new TestProductionHandler("scheme'"), alphaNonHex, schemePrime);
//        grammar.addProduction(schemePrime, new TestProductionHandler("scheme'"), digit, schemePrime);
//        grammar.addProduction(schemePrime, new TestProductionHandler("scheme'"), plus, schemePrime);
//        grammar.addProduction(schemePrime, new TestProductionHandler("scheme'"), hyphen, schemePrime);
//        grammar.addProduction(schemePrime, new TestProductionHandler("scheme'"), period, schemePrime);
//        grammar.addProduction(schemePrime, new TestProductionHandler("scheme'"));

        // IP-literal = "[" ( IPv6address / IPvFuture ) "]"
//        grammar.addProduction(ipLiteral, new TestProductionHandler("IP-literal"), leftBracket, ipv6address, rightBracket);
//        grammar.addProduction(ipLiteral, new TestProductionHandler("IP-literal"), leftBracket, ipFuture, rightBracket);

        // IPvFuture = "v" 1*HEXDIG "." 1*( unreserved / sub-delims / ":" )
//        final TerminalSymbol v = new CharacterTerminal('v');
//        grammar.addProduction(ipFuture, new TestProductionHandler("IPvFuture"), v, ipFutureVersion, period, ipFutureContent);
//        grammar.addProduction(ipFutureVersion, new TestProductionHandler("IPvFuture-version"), digit);
//        grammar.addProduction(ipFutureVersion, new TestProductionHandler("IPvFuture-version"), alphaHex);
//        grammar.addProduction(ipFutureVersion, new TestProductionHandler("IPvFuture-version"), digit, ipFutureVersion);
//        grammar.addProduction(ipFutureVersion, new TestProductionHandler("IPvFuture-version"), alphaHex, ipFutureVersion);
//        grammar.addProduction(ipFutureContent, new TestProductionHandler("IPvFuture'"), unreserved);
//        grammar.addProduction(ipFutureContent, new TestProductionHandler("IPvFuture'"), unreserved, ipFutureContent);
//        for (final TerminalSymbol terminalSymbol : subDelims) {
//            grammar.addProduction(ipFutureContent, new TestProductionHandler("IPvFuture'"), terminalSymbol);
//            grammar.addProduction(ipFutureContent, new TestProductionHandler("IPvFuture'"), terminalSymbol, ipFutureContent);
//        }
//        grammar.addProduction(ipFutureContent, new TestProductionHandler("IPvFuture'"), colon);
//        grammar.addProduction(ipFutureContent, new TestProductionHandler("IPvFuture'"), colon, ipFutureContent);
        
        // TODO: IPv6 (it's really nasty)
        // IPv6address = 6( h16 ":" ) ls32 / "::" 5( h16 ":" ) ls32
        
        // h16 = 1*4HEXDIG
        // ls32 = ( h16 ":" h16 ) / IPv4address
        
        // IPv4address = dec-octet "." dec-octet "." dec-octet "." dec-octet
//        grammar.addProduction(ipv4address, new TestProductionHandler("IPv4address"), decOctet, period, decOctet, period, decOctet, period, decOctet);

        // dec-octet = DIGIT / %x31-39 DIGIT / "1" 2DIGIT / "2" %x30-34 DIGIT / "25" %x30-35
//        grammar.addProduction(decOctet, new TestProductionHandler("dec-octet"), digit);
//        grammar.addProduction(decOctet, new TestProductionHandler("dec-octet"), digit, digit);
//        grammar.addProduction(decOctet, new TestProductionHandler("dec-octet"), digit, digit, digit);
        
        // path = path-abempty / path-absolute / path-noscheme / pathrootless / path-empty
//        grammar.addProduction(path, new TestProductionHandler("path"), pathAbEmpty);
//        grammar.addProduction(path, new TestProductionHandler("path"), pathAbsolute);
//        grammar.addProduction(path, new TestProductionHandler("path"), pathNoScheme);
//        grammar.addProduction(path, new TestProductionHandler("path"), pathRootless);
//        grammar.addProduction(path, new TestProductionHandler("path"), pathEmpty);

        // path-rootless = segment-nz *( "/" segment )
//        grammar.addProduction(pathRootless, new TestProductionHandler("path-rootless"), segmentNz);
//        grammar.addProduction(pathRootless, new TestProductionHandler("path-rootless"), segmentNz, segmentSequence);
        
        // pchar = unreserved / pct-encoded / sub-delims / ":" / "@"

        // query = *( pchar / "/" / "?" )
        // query = *( unreserved / pct-encoded / sub-delims / ":" / "@" / "/" / "?" )
//        grammar.addProduction(query, new TestProductionHandler("query"), queryPrime);
//        grammar.addProduction(queryPrime, new TestProductionHandler("query'"), unreserved, queryPrime);
//        grammar.addProduction(queryPrime, new TestProductionHandler("query'"), pctEncoded, queryPrime);
//        for (final TerminalSymbol terminalSymbol : subDelims) {
//            grammar.addProduction(queryPrime, new TestProductionHandler("query'"), terminalSymbol, queryPrime);
//        }
//        grammar.addProduction(queryPrime, new TestProductionHandler("query'"), colon, queryPrime);
//        grammar.addProduction(queryPrime, new TestProductionHandler("query'"), atSign, queryPrime);
//        grammar.addProduction(queryPrime, new TestProductionHandler("query'"), forwardSlash, queryPrime);
//        grammar.addProduction(queryPrime, new TestProductionHandler("query'"), questionMark, queryPrime);
//        grammar.addProduction(queryPrime, new TestProductionHandler("query'"));

        // absolute-URI = scheme ":" hier-part [ "?" query ]
//        grammar.addProduction(absoluteUri, new TestProductionHandler("absolute-URI"), scheme, colon, hierPart);
//        grammar.addProduction(absoluteUri, new TestProductionHandler("absolute-URI"), scheme, colon, hierPart, questionMark, query);

        // fragment = *( pchar / "/" / "?" )
        // fragment = *( unreserved / pct-encoded / sub-delims / ":" / "@" / "/" / "?" )
//        grammar.addProduction(fragment, new TestProductionHandler("fragment"), fragmentPrime);
//        grammar.addProduction(fragmentPrime, new TestProductionHandler("fragment'"), unreserved, fragmentPrime);
//        grammar.addProduction(fragmentPrime, new TestProductionHandler("fragment'"), pctEncoded, fragmentPrime);
//        for (final TerminalSymbol terminalSymbol : subDelims) {
//            grammar.addProduction(fragmentPrime, new TestProductionHandler("fragment'"), terminalSymbol, fragmentPrime);
//        }
//        grammar.addProduction(fragmentPrime, new TestProductionHandler("fragment'"), colon, fragmentPrime);
//        grammar.addProduction(fragmentPrime, new TestProductionHandler("fragment'"), atSign, fragmentPrime);
//        grammar.addProduction(fragmentPrime, new TestProductionHandler("fragment'"), forwardSlash, fragmentPrime);
//        grammar.addProduction(fragmentPrime, new TestProductionHandler("fragment'"), questionMark, fragmentPrime);
//        grammar.addProduction(fragmentPrime, new TestProductionHandler("fragment'"));

        // userinfo = *( unreserved / pct-encoded / sub-delims / ":" )
        for (final Symbol unreserved : unreservedSymbols) {
//            grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), unreserved);
            grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), unreserved, userinfo);
        }
//        grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), pctEncoded);
        grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), pctEncoded, userinfo);
        for (final TerminalSymbol subDelim : subDelims) {
//            grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), subDelim);
            grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), subDelim, userinfo);
        }
//        grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), colon);
        grammar.addProduction(userinfo, new TestProductionHandler("userinfo"), colon, userinfo);
        grammar.addProduction(userinfo, new TestProductionHandler("userinfo"));

        // TODO: ambiguous with IPv4address
        // reg-name = *( unreserved / pct-encoded / sub-delims )
        for (final Symbol unreserved : unreservedSymbols) {
//            grammar.addProduction(regName, new TestProductionHandler("reg-name"), unreserved);
            grammar.addProduction(regName, new TestProductionHandler("reg-name"), unreserved, regName);
        }
//        grammar.addProduction(regName, new TestProductionHandler("reg-name"), pctEncoded);
        grammar.addProduction(regName, new TestProductionHandler("reg-name"), pctEncoded, regName);
        for (final TerminalSymbol subDelim : subDelims) {
//            grammar.addProduction(regName, new TestProductionHandler("reg-name"), subDelim);
            grammar.addProduction(regName, new TestProductionHandler("reg-name"), subDelim, regName);
        }
        grammar.addProduction(regName, new TestProductionHandler("reg-name"));

        // host = IP-literal / IPv4address / reg-name
//        grammar.addProduction(host, new TestProductionHandler("host"), ipLiteral);
//        grammar.addProduction(host, new TestProductionHandler("host"), ipv4address);
        grammar.addProduction(host, new TestProductionHandler("host"), regName);

        // port = *DIGIT
        grammar.addProduction(port, new TestProductionHandler("port"));
//        grammar.addProduction(port, new TestProductionHandler("port"), digit);
        grammar.addProduction(port, new TestProductionHandler("port"), digit, port);

        // authority = [ userinfo "@" ] host [ ":" port ]
        grammar.addProduction(authority, new TestProductionHandler("authority"), host);
        grammar.addProduction(authority, new TestProductionHandler("authority"), host, colon, port);
        grammar.addProduction(authority, new TestProductionHandler("authority"), userinfo, atSign, host);
        grammar.addProduction(authority, new TestProductionHandler("authority"), userinfo, atSign, host, colon, port);

        // segment-nz-nc = 1*( unreserved / pct-encoded / sub-delims / "@" )
//        for (final Symbol symbol : unreservedSymbols) {
//            grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), symbol);
//            grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), symbol, segmentNzNc);
//        }
//        grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), pctEncoded);
//        grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), pctEncoded, segmentNzNc);
//        for (final TerminalSymbol subDelim : subDelims) {
//            grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), subDelim);
//            grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), subDelim, segmentNzNc);
//        }
//        grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), atSign);
//        grammar.addProduction(segmentNzNc, new TestProductionHandler("segment-nz-nc"), atSign, segmentNzNc);

        // segment-nz = 1*pchar
        // segment-nz = 1*( unreserved / pct-encoded / sub-delims / ":" / "@" )
//        for (final Symbol symbol : unreservedSymbols) {
//            grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), symbol, segmentNz);
//        }
//        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), pctEncoded, segmentNz);
//        for (final TerminalSymbol subDelim : subDelims) {
//            grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), subDelim, segmentNz);
//        }
//        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), colon);
//        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), colon, segmentNz);
//        grammar.addProduction(segmentNz, new TestProductionHandler("segment-nz"), atSign, segmentNz);
        
        // segment = *pchar
        // segment = *( unreserved / pct-encoded / sub-delims / ":" / "@" )
//        grammar.addProduction(segment, new TestProductionHandler("segment"));
//        grammar.addProduction(segment, new TestProductionHandler("segment"), segmentNz);
        
        // segment-sequence = *( "/" segment )
//        grammar.addProduction(segmentSequence, new TestProductionHandler("segment-sequence"), forwardSlash, segment, segmentSequence);
//        grammar.addProduction(segmentSequence, new TestProductionHandler("segment-sequence"));

        // path-abempty = *( "/" segment )
//        grammar.addProduction(pathAbEmpty, new TestProductionHandler("path-abempty"), segmentSequence);
        
        // path-absolute = "/" [ segment-nz *( "/" segment ) ]
//        grammar.addProduction(pathAbsolute, new TestProductionHandler("path-absolute"), forwardSlash);
//        grammar.addProduction(pathAbsolute, new TestProductionHandler("path-absolute"), forwardSlash, segmentNz, segmentSequence);
        
        // path-noscheme = segment-nz-nc *( "/" segment )
//        grammar.addProduction(pathNoScheme, new TestProductionHandler("path-noscheme"), segmentNzNc);
//        grammar.addProduction(pathNoScheme, new TestProductionHandler("path-noscheme"), segmentNzNc, segmentSequence);
        
        // path-empty = 0<pchar>
//        grammar.addProduction(pathEmpty, new TestProductionHandler("path-empty"));

        // relative-part = "//" authority path-abempty / path-absolute / path-noscheme / path-empty
//        grammar.addProduction(relativePart, new TestProductionHandler("relative-part"), forwardSlash, forwardSlash, authority, pathAbEmpty);
//        grammar.addProduction(relativePart, new TestProductionHandler("relative-part"), pathAbsolute);
//        grammar.addProduction(relativePart, new TestProductionHandler("relative-part"), pathNoScheme);
//        grammar.addProduction(relativePart, new TestProductionHandler("relative-part"), pathEmpty);

        // relative-ref = relative-part [ "?" query ] [ "#" fragment ]
//        grammar.addProduction(relativeRef, new TestProductionHandler("relative-ref"), relativePart);
//        grammar.addProduction(relativeRef, new TestProductionHandler("relative-ref"), relativePart, questionMark, query);
//        grammar.addProduction(relativeRef, new TestProductionHandler("relative-ref"), relativePart, numberSign, fragment);
//        grammar.addProduction(relativeRef, new TestProductionHandler("relative-ref"), relativePart, questionMark, query, numberSign, fragment);
        
        // URI-reference = URI / relative-ref
//        grammar.addProduction(uriReference, new TestProductionHandler("URI-reference"), uri);
//        grammar.addProduction(uriReference, new TestProductionHandler("URI-reference"), relativeRef);
        
//        parser = grammar.createParser(uriReference, TestLexer.endOfFileSymbol);
    }
    
    @Test
    public void testPercentEncoded() {
        parser = grammar.createParser(pctEncoded, UriLexer.endOfFileSymbol);
        
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
    public void testUserinfo() {
        parser = grammar.createParser(userinfo, UriLexer.endOfFileSymbol);
        
        parser.parse(new UriLexer(""));
        parser.parse(new UriLexer("example"));
        parser.parse(new UriLexer("example:com"));
        parser.parse(new UriLexer("foo-bar:example:com"));
        parser.parse(new UriLexer("%00abyz0189-._~:!$&'()*+,;=%ff"));
        
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
        parser = grammar.createParser(host, UriLexer.endOfFileSymbol);
        
        parser.parse(new UriLexer(""));
        parser.parse(new UriLexer("example"));
        parser.parse(new UriLexer("example.com"));
        parser.parse(new UriLexer("foo-bar.example.com"));
        parser.parse(new UriLexer("foo-bar.example.com."));
        parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%ff"));
        
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
        parser = grammar.createParser(port, UriLexer.endOfFileSymbol);
        
        parser.parse(new UriLexer(""));
        parser.parse(new UriLexer("80"));
        parser.parse(new UriLexer("65535"));
        parser.parse(new UriLexer("65536"));
        parser.parse(new UriLexer(Long.toString(Long.MAX_VALUE)));
        
        final String reservedCharacters = "-._~!$&'()*+,;=:/?#[]@%";
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
    public void testAuthority() {
        parser = grammar.createParser(authority, UriLexer.endOfFileSymbol);
        
        parser.parse(new UriLexer(""));
        parser.parse(new UriLexer("example"));
        parser.parse(new UriLexer("example.com"));
        parser.parse(new UriLexer("foo-bar.example.com"));
        parser.parse(new UriLexer("foo-bar.example.com."));
        parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%ff"));
        
        parser.parse(new UriLexer(":80"));
        parser.parse(new UriLexer("example:80"));
        parser.parse(new UriLexer("example.com:80"));
        parser.parse(new UriLexer("foo-bar.example.com:80"));
        parser.parse(new UriLexer("foo-bar.example.com.:80"));
        parser.parse(new UriLexer("%00abyz0189-._~!$&'()*+,;=%ff:80"));
        
        parser.parse(new UriLexer("foo:bar@"));
        parser.parse(new UriLexer("foo:bar@example"));
        parser.parse(new UriLexer("foo:bar@example.com"));
        parser.parse(new UriLexer("foo:bar@foo-bar.example.com"));
        parser.parse(new UriLexer("foo:bar@foo-bar.example.com."));
        parser.parse(new UriLexer("foo:bar@%00abyz0189-._~!$&'()*+,;=%ff"));
        
        parser.parse(new UriLexer("foo:bar@:80"));
        parser.parse(new UriLexer("foo:bar@example:80"));
        parser.parse(new UriLexer("foo:bar@example.com:80"));
        parser.parse(new UriLexer("foo:bar@foo-bar.example.com:80"));
        parser.parse(new UriLexer("foo:bar@foo-bar.example.com.:80"));
        parser.parse(new UriLexer("foo:bar@%00abyz0189-._~!$&'()*+,;=%ff:80"));
    }

    @Test
    @Ignore
    public void testRelativeRef() {
        parser = grammar.createParser(relativeRef, UriLexer.endOfFileSymbol);
        
        parser.parse(new UriLexer("//foo.bar.com"));
        parser.parse(new UriLexer("//foo.bar.com?abc"));
        parser.parse(new UriLexer("//foo.bar.com#abc"));
        parser.parse(new UriLexer("//foo.bar.com?abc#abc"));
        
        parser.parse(new UriLexer("//foo.bar.com/bar/baz"));
        parser.parse(new UriLexer("//foo.bar.com/bar/baz?abc"));
        parser.parse(new UriLexer("//foo.bar.com/bar/baz#abc"));
    }
    
    @Test
    @Ignore
    public void testURI() {
        parser = grammar.createParser(uri, UriLexer.endOfFileSymbol);
        
        parser.parse(new UriLexer("https://foo.bar.com"));
        parser.parse(new UriLexer("https://foo.bar.com?abc"));
        parser.parse(new UriLexer("https://foo.bar.com#abc"));
        parser.parse(new UriLexer("https://foo.bar.com?abc#abc"));
        
        parser.parse(new UriLexer("https://foo.bar.com/bar/baz"));
        parser.parse(new UriLexer("https://foo.bar.com/bar/baz?abc"));
        parser.parse(new UriLexer("https://foo.bar.com/bar/baz#abc"));
    }

}
