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
- [x] stripStart("  abc"," ") returns "abc"; stripEnd("abc  "," ") returns "abc"

## Task 2: NumberUtils

### Acceptance Criteria
- [ ] toInt("123") returns 123; toInt("abc") returns 0; toInt("abc",5) returns 5
- [ ] toLong("123") returns 123L; toLong("abc") returns 0L; toLong("abc",5L) returns 5L
- [ ] toFloat("1.5") returns 1.5f; toFloat("abc") returns 0.0f; toFloat("abc",1.0f) returns 1.0f
- [ ] toDouble("1.5") returns 1.5; toDouble("abc") returns 0.0; toDouble("abc",1.0) returns 1.0
- [ ] toByte("1") returns (byte)1; toShort("1") returns (short)1
- [ ] createNumber("123") returns Integer 123; createNumber("123L") returns Long 123; createNumber("0xFF") returns Integer 255; createNumber(null) returns null
- [ ] createNumber("1.5") returns a floating-point Number; createNumber("1.5f") returns Float
- [ ] createInteger("123") returns 123; createLong("123") returns 123L; createFloat/createDouble work similarly
- [ ] createBigInteger and createBigDecimal parse valid strings
- [ ] isCreatable("123") is true; isCreatable("0xFF") is true; isCreatable("1.5e10") is true; isCreatable("abc") is false
- [ ] isParsable("123") is true; isParsable("12.3") is true; isParsable("0xFF") is false; isParsable("abc") is false
- [ ] isDigits("123") is true; isDigits("") is false; isDigits(null) is false; isDigits("12.3") is false
- [ ] min(1,2,3) returns 1; max(1,2,3) returns 3 (for int, long, double, float, short, byte)
- [ ] min/max throw IllegalArgumentException for null or empty arrays
- [ ] compare(1,2) returns negative; compare(2,1) returns positive; compare(1,1) returns 0
- [ ] Constants like INTEGER_ZERO, LONG_ZERO, DOUBLE_ZERO etc. exist
