package garden.mobi.kmptemplate.domain.util

interface Mapper<in In, out Out> {
    fun In.map(): Out
}

fun <In, Out> In.map(mapper: Mapper<In, Out>) = mapper.run { map() }

fun <In, Out> Iterable<In>.map(mapper: Mapper<In, Out>) = mapNotNull { mapper.run { it.map() } }
