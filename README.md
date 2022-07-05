# VerveineJ

[![Use Java 17](https://img.shields.io/badge/Java-17-brightgreen)](https://adoptium.net/) ![CI](https://github.com/moosetechnology/VerveineJ/workflows/CI/badge.svg?branch=master)
[![Moose version](https://img.shields.io/badge/Moose-9-%23aac9ff.svg)](https://github.com/moosetechnology/Moose)

A Java to JSON/MSE parser

Based on JDT, it parser java code to export it in the MSE or JSON format used by the [Moose](https://modularmoose.org/) data analysis platform.

## Installation

[Installation page](https://moosetechnology.github.io/moose-wiki/Developers/Parsers/VerveineJ.html)

You only have to clone this project and then run verveineJ

```sh
# https
git clone https://github.com/moosetechnology/VerveineJ.git

# ssh
git clone git@github.com:moosetechnology/VerveineJ.git
```

## Running it

The simplest command is
```sh
verveinej.sh <java-source-directory>
```
It will create an `output.mse` (JSON format also available) file with the model extracted from the \<java-source-directory\>

To see what other options are available:
```sh
verveinej.sh -h
```


## Developers

To test the project, remember that you **must** disable the `assert` by removing (or not using) the -ea parameter.
You also need to run tests one by one (fork method in IntelliJ).

You can also use Ant or the pre-created IntelliJ build

```sh
ant junit
```
