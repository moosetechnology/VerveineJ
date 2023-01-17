# VerveineJ

[![Use Java 10](https://img.shields.io/badge/Java-10-brightgreen)](https://jdk.java.net/10/) ![Build travis](https://api.travis-ci.com/moosetechnology/VerveineJ.svg?branch=master&status=passed)

![Deprecated](https://img.shields.io/badge/Deprecated-FAMIX%20Compatibility-red)

(Note that this version of VerveineJ is for Moose8 which is deprecated)

A Java to MSE parser

Based on JDT, it parser java code to export it in the MSE format used by the Moose data analysis platform.
(Similar to the https://github.com/feenkcom/jdt2famix project, but more complete in what it extracts)

## Installation

[Installation page](https://moosetechnology.github.io/moose-wiki/Developers/Parsers/VerveineJ.html)

You only have to clone this project and then run verveineJ

```sh
# https
git clone https://github.com/moosetechnology/VerveineJ.git

# ssh
git clone git@github.com:moosetechnology/VerveineJ.git
```

## Developers

To test the project, remember that you **must** disable the `assert` by removing (or not using) the -ea parameter.
You also need to run test one by one (fork method in IntelliJ).

> You can also use And or the pre-created IntelliJ build
