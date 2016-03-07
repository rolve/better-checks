# Better Checks [![build status](https://travis-ci.org/rolve/better-checks.svg)](https://travis-ci.org/rolve/better-checks)

The **Better Checks** library provides a lightweight and concise, but powerful way for precodition checking in Java, in particular for method arguments. Checks are written in a fluent way.

Examples:

    Check.that(name).matches("hello .*!").hasLenghtBetween(0, 20);
    Check.that(list).isNullOr().hasSize(0);
    Check.that(args).named("arguments").isNotEmpty();

The actual checking methods, such as `matches(...)` or `hasSize(...)`, all throw an exception if the check fails. The exact type of exception depends on the kind of check that is called but in most cases it is `IllegalArgumentException`.

## Project Website

For more information, visit the project's website:

<http://better-checks.trick17.ch>
