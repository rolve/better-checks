## Overview

The **Better Checks** library provides a lightweight and concise, but powerful way for precodition checking in Java, in particular for method arguments. Checks are written in a fluent way.

Examples:

    Check.that(name).matches("hello .*!").hasLengthBetween(0, 20);
    Check.that(list).isNullOr().hasSize(0);
    Check.that(args).named("arguments").isNotEmpty();

The actual checking methods, such as `matches(...)` or `hasSize(...)`, all throw an exception if the check fails. The exact type of exception depends on the kind of check that is called but in most cases it is `IllegalArgumentException`.

## Download

The recommended way of getting the Better Checks library is declaring a dependency on the "better-checks-core" library using your build system. For Maven, use:

    <dependency>
        <groupId>ch.trick17.better-checks</groupId>
        <artifactId>better-checks-core</artifactId>
        <version>1.0</version>
    </dependency>

Otherwise, you can download the source code or binaries from Github:

<https://github.com/rolve/better-checks/releases>