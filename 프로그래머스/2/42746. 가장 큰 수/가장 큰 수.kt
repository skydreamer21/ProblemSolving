class Solution {
    class Number(
        val number: String
    ): Comparable<Number> {

        override fun compareTo(o: Number): Int {
            val num1 = (this.number + o.number).toInt()
            val num2 = (o.number + this.number).toInt()
            return num2 - num1
        }

    }
    fun solution(numbers: IntArray): String {
        val numArray = Array(numbers.size) {i -> Number(numbers[i].toString()) }
        numArray.sort()

        val answer = if (numArray[0].number == "0") "0"
        else {
            buildString {
                for (i in 0 until numbers.size) {
                    append(numArray[i].number)
                }
            }
        }
        return answer
    }
}