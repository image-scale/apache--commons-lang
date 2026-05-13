# Todo

## Plan
Implement the library top-down by user-facing functionality. Start with the most widely used utility classes (StringUtils, NumberUtils, ArrayUtils), then builder infrastructure, then specialized utilities and concurrent features. Each task delivers one cohesive module with full tests.

## Tasks
- [x] Task 1: Maven project setup + StringUtils with null-safe string operations (isEmpty, isBlank, trim, strip, equals, contains, indexOf, substring, split, join, replace, remove, capitalize, pad, reverse, abbreviate, type checks, countMatches, wrap, normalizeSpace, defaultString, truncate, etc.)
- [x] Task 2: NumberUtils with safe parsing (toInt, toLong, toFloat, toDouble), number creation (createNumber, createInteger), validation (isCreatable, isParsable, isDigits), and min/max operations across primitives
- [x] Task 3: ArrayUtils with null-safe array operations (isEmpty, contains, indexOf, add, addAll, remove, subarray, clone, reverse, swap, shift, isSorted, nullToEmpty, toObject/toPrimitive, toMap, insert)
- [x] Task 4: ObjectUtils with null-safe object operations (defaultIfNull, firstNonNull, allNotNull, anyNull, compare, clone, identityToString, toString, isEmpty, min, max, mode, median, requireNonEmpty) and BooleanUtils with boolean/String/int conversions and logical operations
- [x] Task 5: CharUtils (ASCII classification, conversion, unicode escape) and Validate (notNull, notEmpty, notBlank, isTrue, validState, validIndex, matchesPattern, isInstanceOf, inclusiveBetween, exclusiveBetween, finite)
- [x] Task 6: Builder classes - EqualsBuilder (field-by-field and reflection-based equality), HashCodeBuilder (field-by-field and reflection hashing), CompareToBuilder (field-by-field comparison), and ToStringBuilder with ToStringStyle (DEFAULT, MULTI_LINE, SHORT_PREFIX, SIMPLE, JSON styles)
- [ ] Task 7: Tuple classes - Pair (abstract + ImmutablePair + MutablePair) and Triple (abstract + ImmutableTriple + MutableTriple) with factory methods, Comparable support, and Map.Entry implementation for Pair
- [ ] Task 8: Mutable wrappers - MutableInt, MutableLong, MutableDouble, MutableFloat, MutableBoolean, MutableByte, MutableShort, MutableObject with increment/decrement/add/subtract and atomic-style API naming
- [ ] Task 9: Range class with generic inclusive ranges, containment testing, overlap detection, intersection, fit/clamp, and natural/custom ordering support. Plus Fraction with immutable arithmetic (add, subtract, multiply, divide, negate, abs, invert, pow, reduce)
- [ ] Task 10: ExceptionUtils for exception chain traversal (getRootCause, getThrowableList, getStackTrace, getMessage, indexOfThrowable, hasCause, rethrow, isChecked) and RegExUtils for null-safe regex operations (removeAll, removeFirst, replaceAll, replaceFirst)
- [ ] Task 11: ClassUtils (name retrieval, primitive/wrapper conversion, assignability, hierarchy traversal, class loading) and EnumUtils (safe lookup, validation, collections, bit vectors)
- [ ] Task 12: SystemUtils (OS detection booleans, Java version detection, system property constants, directory helpers) and SerializationUtils (serialize, deserialize, clone via serialization)
- [ ] Task 13: Reflection utilities - FieldUtils (find, read, write fields including private) and MethodUtils (find, invoke methods by name with type matching)
- [ ] Task 14: StopWatch timer with start/stop/suspend/resume/split/reset lifecycle, state machine, and duration tracking
- [ ] Task 15: Concurrent utilities - LazyInitializer (thread-safe lazy init), BasicThreadFactory (configurable thread factory with builder), Memoizer (concurrent caching), ConcurrentUtils (helper methods), and ThresholdCircuitBreaker
