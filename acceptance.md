# Acceptance Criteria

## Task 1: StringUtils with null-safe string operations

### Acceptance Criteria
- [ ] isEmpty(null) returns true; isEmpty("") returns true; isEmpty(" ") returns false; isEmpty("abc") returns false
- [ ] isBlank(null) returns true; isBlank("") returns true; isBlank("  ") returns true; isBlank("abc") returns false
- [ ] isNotEmpty and isNotBlank return the negation of isEmpty and isBlank
- [ ] trim(null) returns null; trim("  abc  ") returns "abc"; trimToNull("  ") returns null; trimToEmpty(null) returns ""
- [ ] strip(null) returns null; strip("  abc  ") returns "abc"; strip("abc", "a") strips character; stripToNull and stripToEmpty work
- [ ] equals(null,null) returns true; equals("a","a") returns true; equals("a","b") returns false; equalsIgnoreCase("a","A") returns true
- [ ] contains(null, *) returns false; contains("abc","bc") returns true; containsIgnoreCase("ABC","abc") returns true
- [ ] indexOf("abcabc","bc") returns 1; lastIndexOf works similarly; indexOfIgnoreCase works case-insensitively
- [ ] substring(null,*) returns null; substring("abc",1) returns "bc"; no IndexOutOfBoundsException on overflow
- [ ] substringBefore("abc-def","-") returns "abc"; substringAfter returns "def"; substringBetween("a[b]c","[","]") returns "b"
- [ ] split(null) returns null; split("a.b.c",".") returns ["a","b","c"]; split handles max tokens
- [ ] join(["a","b","c"],",") returns "a,b,c"; join(null,...) returns null
- [ ] replace("abc","b","d") returns "adc"; remove("abc","b") returns "ac"
- [ ] capitalize("abc") returns "Abc"; uncapitalize("Abc") returns "abc"
- [ ] upperCase(null) returns null; upperCase("abc") returns "ABC"; lowerCase("ABC") returns "abc"
- [ ] leftPad("abc",5) returns "  abc"; rightPad("abc",5) returns "abc  "; center("abc",7) returns "  abc  "
- [ ] reverse(null) returns null; reverse("abc") returns "cba"
- [ ] abbreviate("abcdefghij",5) returns "ab..."
- [ ] isNumeric("123") returns true; isAlpha("abc") returns true; isAlphanumeric("abc123") returns true
- [ ] countMatches("abcabc","abc") returns 2
- [ ] defaultString(null) returns ""; defaultIfBlank(null,"default") returns "default"
- [ ] wrap("abc","'") returns "'abc'"; unwrap("'abc'","'") returns "abc"
- [ ] normalizeSpace("  a  b  ") returns "a b"
- [ ] deleteWhitespace("a b c") returns "abc"
- [ ] truncate("abcdefg",4) returns "abcd"
- [ ] repeat("ab",3) returns "ababab"
- [ ] startsWith("abc","ab") returns true; endsWith("abc","bc") returns true
- [ ] chomp("abc\n") returns "abc"; chop("abc") returns "ab"
- [ ] stripStart("  abc"," ") returns "abc"; stripEnd("abc  "," ") returns "abc"
