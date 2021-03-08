# Either
[![Version](https://jitpack.io/v/SiberianPathfinder/Either.svg)](https://jitpack.io/#SiberianPathfinder/Either)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Why?

This small pet-project is a library publication training exercise.

Maybe you are looking for Either implementation like [Arrows](https://arrow-kt.io/docs/apidocs/arrow-core-data/arrow.core/-either/).

## Description

There is simplest Either type-class implementation. It's usable just in cases when you'll mark some computation as failure-success dichotomic result.

## Download

Available through jitpack.

Add the maven repo to your root `build.gradle`

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Dependency

```groovy
implementation "com.github.SiberianPathfinder:Either:{latest-version}"
```
