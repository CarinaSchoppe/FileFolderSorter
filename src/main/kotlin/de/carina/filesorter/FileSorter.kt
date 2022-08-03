package de.carina.filesorter

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.control.Alert
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.util.*


fun main(args: Array<String>) {
    //create new instance of FileSorter
    val driveSelector = DriveSelector()
    Application.launch(driveSelector::class.java, *args)


}

class FileSorter(private val driveLetter: Char, private val monthFolder: Boolean, private val subFolder: Boolean) {


    private var driveFolder: File = File("$driveLetter:\\")

    companion object {
        lateinit var fileSorter: FileSorter
    }


    fun addAllFiles() {
        //add all files that are not directories even in subdirectories to the fileList
        if (subFolder) {
            driveFolder.walkTopDown().forEach {
                start(it)
            }
        } else {
            driveFolder.listFiles().forEach {
                start(it)
            }
        }

        Platform.runLater {
            var alert = Alert(Alert.AlertType.INFORMATION)
            alert.title = "FileSorter"
            alert.headerText = "Files sorted"
            alert.showAndWait()
        }

    }

    private fun start(file: File) {


        //check if file is a folder
        if (file.isDirectory) return
        //check if file is an Image
        if (file.extension != "jpg" && file.extension != "jpeg" && file.extension != "png" && file.extension != "gif" && file.extension != "raw") return

        //get date from metaData

        val attr: BasicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes::class.java)
        //get the date
        //convert date: yyyy:MM:dd HH:mm:ss to Date object
        //create new DateFormat
        val creation = attr.creationTime()
        val change = attr.lastModifiedTime()
        val access = attr.lastAccessTime()


        //get the earliest of the dates
        val date = if (creation < change) {
            if (creation < access) {
                creation.toMillis()
            } else {
                access.toMillis()
            }
        } else {
            if (change < access) {
                change.toMillis()
            } else {
                access.toMillis()
            }
        }

        //convert date to Date object
        val dateObject = Date(date)

        //get month from date using calendar
        val calendar = Calendar.getInstance()
        calendar.time = dateObject
        val month = when (calendar.get(Calendar.MONTH)) {
            0 -> "Januar"
            1 -> "Februar"
            2 -> "März"
            3 -> "April"
            4 -> "Mai"
            5 -> "Juni"
            6 -> "Juli"
            7 -> "August"
            8 -> "September"
            9 -> "Oktober"
            10 -> "November"
            11 -> "Dezember"
            else -> "Error"
        }
        val year = calendar.get(Calendar.YEAR)

        println("$month $year")

        //create new folder for year
        val yearFolder = File("$driveLetter:\\$year")
        if (!yearFolder.exists()) yearFolder.mkdir()
        //create new folder for month
        val monthFolder = File("$driveLetter:\\$year\\$month")
        if (!monthFolder.exists() && this.monthFolder) monthFolder.mkdir()

        //move file to year folder
        file.renameTo(File("$driveLetter:\\$year\\${file.name}"))
        //move file to month folder
        if (this.monthFolder) file.renameTo(File("$driveLetter:\\$year\\$month\\${file.name}"))


    }
}







