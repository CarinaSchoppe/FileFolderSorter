import org.apache.commons.imaging.Imaging
import org.apache.commons.imaging.common.ImageMetadata
import org.w3c.dom.Node
import java.io.File
import kotlin.test.Test


class FileReaderTest {





private fun displayMetadata(asTree: Node?) {
    if (asTree == null) return
    val nodeList = asTree.childNodes
    for (i in 0 until nodeList.length) {
        val node = nodeList.item(i)
        if (node.nodeName == "exif:DateTimeOriginal") {
            val date = node.textContent
            val year = date.substring(0, 4)
            val month = date.substring(5, 7)
            val day = date.substring(8, 10)
            val hour = date.substring(11, 13)
            val minute = date.substring(14, 16)
            val second = date.substring(17, 19)
            val fileName = "$year-$month-$day-$hour-$minute-$second"
            println("$fileName")
        }
    }


}
    @Test
    fun testReadFile() {
        var file = File("test.jpg")
        //check if file is a folder

        var metaData: ImageMetadata = Imaging.getMetadata(file)
        metaData.items.forEach {
            println(it.toString())
        }
    }
    }
