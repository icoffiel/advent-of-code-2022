package util

fun <E> Iterable<E>.replaceAll(old: E, new: E): List<E> {
    return map {
        if (it == old) {
            new
        } else {
            it
        }
    }
}