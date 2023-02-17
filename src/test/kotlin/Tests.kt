import org.example.internship.data.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

internal class Tests {
    @ParameterizedTest
    @ArgumentsSource(ComponentTestsProvider.SafeComponents::class)
    fun `test safe components`(result: TestResult) {
        assert(result.result) { result }
    }

    @ParameterizedTest
    @ArgumentsSource(ComponentTestsProvider.UnsafeComponents::class)
    fun `test unsafe components`(result: TestResult) {
        assert(result.result) { result }
    }
}
