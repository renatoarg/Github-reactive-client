package renatoarg.xapokotlin.utils

import java.text.SimpleDateFormat
import java.util.*

object AppUtils {
    @JvmStatic
    fun formatUpdatedAt(date: Date) : String {
        val format = SimpleDateFormat("yyyy, MMM dd")
        return "last update: " + format.format(date)
    }
    @JvmStatic
    fun formatForks(count: Int) : String {
        return "forks: $count"
    }
    @JvmStatic
    fun formatStargazers(count: Int) : String {
        return String.format("%.1fk", count.toFloat()  / 1000)
    }
}