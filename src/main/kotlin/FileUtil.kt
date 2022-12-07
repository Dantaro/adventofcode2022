import java.io.File

object FileUtil {
    fun getFile(directoryName: String): File =
        File(this::class.java.classLoader.getResource("$directoryName/data.txt")!!.toURI().path)
}