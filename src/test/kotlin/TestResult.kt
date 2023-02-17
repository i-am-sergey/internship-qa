import org.example.internship.data.Component

data class TestResult(
    val component: Component,
    val message: String,
    val result: Boolean
) {
    override fun toString(): String {
        return "$message: $component"
    }
}