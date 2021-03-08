package com.sibpf.utils.either

/**
 * Type-class which value can be instance of [L] or [R] class.
 */
sealed class Either<L, R> {

    /**
     * Type-class represents left-side of [Either] case.
     *
     * @param value Value which [Left] contains.
     */
    data class Left<L, R>(val value: L) : Either<L, R>()

    /**
     * Type-class represents right-side of [Either] case.
     *
     * @param value Value which [Right] contains.
     */
    data class Right<L, R>(val value: R) : Either<L, R>()

    /**
     * Right-based flapMap function.
     *
     * @return self if it's [Left] or [Either] returned by [f].
     */
    fun <T> flatMap(f: (R) -> Either<L, T>): Either<L, T> = when (this) {
        is Left -> this.value.asLeft()
        is Right -> f(this.value)
    }

    /**
     * Right-based map function.
     *
     * @return self if it's [Left] or [Right] with value returned by [f].
     */
    fun <T> map(f: (R) -> T): Either<L, T> = when (this) {
        is Left -> this.value.asLeft()
        is Right -> f(this.value).asRight()
    }

    /**
     * Left-based map function.
     *
     * @return self if it's [Right] or [Left] with value returned by [f].
     */
    fun <T> mapLeft(f: (L) -> T): Either<T, R> = when (this) {
        is Left -> f(this.value).asLeft()
        is Right -> this.value.asRight()
    }

    /**
     * Bi-directional map function.
     *
     * @return Either with mapped sides. It'll be [Left] mapped by [left] function if [bimap] was called on [Left]
     * instance. Same rule for [Right], but its value maps by [right] function.
     */
    fun <A, B> bimap(left: (L) -> A, right: (R) -> B): Either<A, B> = when (this) {
        is Left -> left(this.value).asLeft()
        is Right -> right(this.value).asRight()
    }

    /**
     * Evaluates some value which depends on side.
     *
     * @return value evaluated by [ifLeft] function if [Either] is [Left] instance. In [Right] case value will be
     * evaluated by [ifRight] function.
     */
    fun <T> fold(ifLeft: (L) -> T, ifRight: (R) -> T): T = when (this) {
        is Left -> ifLeft(this.value)
        is Right -> ifRight(this.value)
    }

    /**
     * Right-based 'get' function.
     *
     * @return null if [Either] is [Left], else - [Right.value].
     */
    fun getOrNull(): R? = when (this) {
        is Left -> null
        is Right -> this.value
    }

    /**
     * Right-based 'exists' function.
     *
     * @return false if [Either] is [Left], else evaluates by [predicate].
     */
    fun exists(predicate: (R) -> Boolean): Boolean = when (this) {
        is Left -> false
        is Right -> predicate(this.value)
    }
}

/**
 * Shortcut for create [Either] instance from any value.
 * Wraps value to [Either.Left].
 *
 * @return [Either.Left] in which the value will be wrapped.
 */
fun <L, R> L.asLeft(): Either<L, R> = Either.Left(this)


/**
 * Shortcut for create [Either] instance from any value.
 * Wraps value to [Either.Right].
 *
 * @return [Either.Right] in which the value will be wrapped.
 */
fun <L, R> R.asRight(): Either<L, R> = Either.Right(this)
