import java.util.*

const val fullPrice = 10
const val salePrice = 8

var numPurchasedTickets = 0
var currentIncome = 0

fun main() {

    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    println("")

    val cinema = MutableList(rows) { MutableList(seats) {'S'} }

    var input = ""

    while (input != "0") {
        println("""
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
        """.trimIndent())
        input = readln()

        when (input) {
            "1" -> println("\n${cinemaSeats(cinema, seats)}")
            "2" -> println("\n${buySeat(cinema, rows, seats)}\n")
            "3" -> println("\n${cinemaStatistics(rows, seats)}\n") //TODO()
            else -> input = "0"
        }
    }
}

fun cinemaSeats(cinema:  MutableList<MutableList<Char>>, seats: Int): String {
    val seatsPrint = 1..seats

    var cinemaPrint = "Cinema:\n  ${seatsPrint.joinToString(" ")}\n"
    for (indexPlace in 0 until cinema.size) {
        cinemaPrint += "${indexPlace + 1} ${cinema[indexPlace].joinToString(" ")}\n"
    }
    return cinemaPrint
}

fun buySeat(cinema: MutableList<MutableList<Char>>, rows: Int, seats: Int): String {
    var rowSel: Int
    var seatSel: Int
    var flag = false
    var priceSeat = ""

    while (!flag) {
        println("")
        println("Enter a row number:")
        rowSel = readln().toInt()
        println("Enter a seat number in that row:")
        seatSel = readln().toInt()

        if (rowSel <= rows && seatSel <= seats) {
            if (cinema[rowSel - 1][seatSel - 1] != 'B') {
                if (rowSel > 4) {
                    priceSeat = "Ticket price: $$salePrice"
                    currentIncome += salePrice
                } else {
                    priceSeat = "Ticket price: $$fullPrice"
                    currentIncome += fullPrice
                }
                numPurchasedTickets++

                cinema[rowSel - 1][seatSel - 1] = 'B'

                flag = true
            } else {
                println("\nThat ticket has already been purchased!")
            }
        } else {
            println("\nWrong input!")
        }
    }

    return priceSeat
}

fun cinemaStatistics(rows: Int, seats: Int): String {
    val room = rows * seats
    val percent: Double = numPurchasedTickets.toDouble() / room * 100
    val formatPercent = String.format(locale = Locale.ENGLISH, format = "%.2f", percent)
    var income = fullPrice * room

    if (room > 60) {
        val frontHalf: Int = rows / 2 * seats
        val backHalf: Int = (rows - rows / 2) * seats
        income = frontHalf * fullPrice + backHalf * salePrice
    }

    return """
        Number of purchased tickets: $numPurchasedTickets
        Percentage: $formatPercent%
        Current income: $$currentIncome
        Total income: $$income
    """.trimIndent()
}