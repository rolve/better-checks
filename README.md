# Better Checks

The **Better Checks** library aims to provide a lightweight and compact, but powerful way for precodition checking in Java, in particular for method arguments. Checks are written in a fluent way.

Examples:

    Check.that(name).matches("hello .*!").hasLenghtBetween(0, 20);
    Check.that(list).isNullOr().hasSize(0);
    Check.that(args).named("arguments").isNotEmpty();

The actual checking methods (such as `matches(...)` or `hasSize(...)`) all throw an exception if the check fails. The exact type of exception depends on the kind of check that is called but in most cases it is `IllegalArgumentException`.

## Project Website

<http://better-checks.trick17.ch>