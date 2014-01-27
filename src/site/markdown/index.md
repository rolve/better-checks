## Better Checks

The **Better Checks** library provides a lightweight and concise, but powerful way for precodition checking in Java, in particular for method arguments. Checks are written in a fluent way.

Examples:

    Check.that(name).matches("hello .*!").hasLenghtBetween(0, 20);
    Check.that(list).isNullOr().hasSize(0);
    Check.that(args).named("arguments").isNotEmpty();

The actual checking methods, such as `matches(...)` or `hasSize(...)`, all throw an exception if the check fails. The exact type of exception depends on the kind of check that is called but in most cases it is `IllegalArgumentException`.

### Project Status

The first version of the library is feature-complete. You can download and play around with the source code at the project&apos;s GitHub page:

<https://github.com/rolve/better-checks>