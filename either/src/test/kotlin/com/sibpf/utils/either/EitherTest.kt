package com.sibpf.utils.either

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal

internal class EitherTest {

    @Test
    fun flatMap() {
        val intEither = 5.asRight<String, Int>()
        when (val floatEither = intEither.flatMap { it.toFloat().asRight() }) {
            is Either.Left -> fail()
            is Either.Right -> assertEquals(5.0f, floatEither.value)
        }
        val s = "some-error"
        when (val failEither = intEither.flatMap<Int> { s.asLeft() }) {
            is Either.Left -> assertEquals(s, failEither.value)
            is Either.Right -> fail()
        }
    }

    @Test
    fun map() {
        val intEither = 5.asRight<String, Int>()
        when (val floatEither = intEither.map { it.toFloat() }) {
            is Either.Left -> fail()
            is Either.Right -> assertEquals(5.0f, floatEither.value)
        }
    }

    @Test
    fun mapLeft() {
        val failEither = "some-error".asLeft<String, Int>()
        when (val mappedFailEither = failEither.mapLeft { it.replace("-", "") }) {
            is Either.Left -> assertEquals("someerror", mappedFailEither.value)
            is Either.Right -> fail()
        }
    }

    @Test
    fun bimap() {
        val either1 = "5".asLeft<String, Float>()
        when (val mapped = either1.bimap({ it.toInt() }, { it.toBigDecimal() })) {
            is Either.Left -> assertEquals(5, mapped.value)
            is Either.Right -> fail()
        }
        val either2 = "5".asRight<Float, String>()
        when (val mapped = either2.bimap({ it.toInt() }, { it.toBigDecimal() })) {
            is Either.Left -> fail()
            is Either.Right -> assertEquals(BigDecimal.valueOf(5), mapped.value)
        }
    }

    @Test
    fun fold() {
        val either1 = "5".asLeft<String, Float>()
        val folded1 = either1.fold({ true }) { false }
        assertTrue(folded1)
        val either2 = 10f.asRight<String, Float>()
        val folded2 = either2.fold({ false }) { true }
        assertTrue(folded2)
    }

    @Test
    fun getOrNull() {
        val either1 = "5".asLeft<String, Float>()
        val value1 = either1.getOrNull()
        assertEquals(value1, null)
        val either2 = 10f.asRight<String, Float>()
        val value2 = either2.getOrNull()
        assertEquals(value2, 10f)
    }

    @Test
    fun exists() {
        val either1 = "5".asLeft<String, Float>()
        val value1 = either1.exists { it == 5f }
        assertEquals(value1, false)
        val either2 = 10f.asRight<String, Float>()
        val value2 = either2.exists { it == 10f }
        assertEquals(value2, true)
        val value3 = either2.exists { it == 12f }
        assertEquals(value3, false)
    }
}