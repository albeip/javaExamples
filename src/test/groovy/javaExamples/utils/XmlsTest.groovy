package javaExamples.utils

import spock.lang.Specification

class XmlsTest extends Specification{
    def "Test XML unescape 001"() {
        when:"the line starts with an escaped symbol"
        String toEvaluate = "&lt;Messages&gt;"
        then: "returns the line with  unescaped symbol"
        Xmls.Escaping.unescape(toEvaluate) == "<Messages>"
    }

    def "Test XML unescape 002"() {
        when: "the line starts with no escaped symbol"
        String toEvaluate = "a &lt;Messages&gt;"
        then: "return the line with unescaped symbol"
        Xmls.Escaping.unescape(toEvaluate) == "a <Messages>"
    }

    def "Test XML unescape 003"() {
        when: "the line starts and ends with no escaped symbol"
        String toEvaluate = "a &lt;Messages&gt; to show"
        then: "return the line with unescaped symbol"
        Xmls.Escaping.unescape(toEvaluate) == "a <Messages> to show"
    }

    def "Test XML unescape 004"() {
        when: "the line has a escaped symbol that is not in the dictionary"
        String toEvaluate = "a &ltgt; symbol"
        then: "return the line with no escaped symbol"
        Xmls.Escaping.unescape(toEvaluate) == "a &ltgt; symbol"
    }

    def "Test XML unescape 005"() {
        when: "the line contains escaped symbols"
        String toEvaluate = "    &lt;msgData&gt;&lt;![CDATA[&lt;p&gt; "
        then: "return the line with escaped symbols"
        Xmls.Escaping.unescape(toEvaluate) == "    <msgData><![CDATA[<p> "
    }

    def "Test XML unescape 006"() {
        when: "the line has a escaped symbol that exceeds the maximum size"
        String toEvaluate = "a &ltgtm; symbol"
        then: "return the line with no escaped symbol"
        Xmls.Escaping.unescape(toEvaluate) == "a &ltgtm; symbol"
    }

}
