package de.carina.filesorter

import javafx.application.Application
import org.apache.commons.imaging.Imaging
import org.apache.commons.imaging.common.ImageMetadata
import java.io.File
import java.text.SimpleDateFormat
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

    private var fileList: MutableList<File> = mutableListOf()

    fun addAllFiles() {
        //add all files that are not directories even in subdirectories to the fileList
        driveFolder.walkTopDown().forEach {
            if (it.isFile) {
                fileList.add(it)
            }
        }
    }

    fun start() {
        if (subFolder)
            addAllFiles()
        else
            fileList = driveFolder.listFiles().toMutableList()
        for (file in fileList) {


            //check if file is a folder
            if (file.isDirectory) continue
            //check if file is an Image
            if (file.extension != "jpg" && file.extension != "jpeg" && file.extension != "png" && file.extension != "gif" && file.extension != "raw") continue


            val metaData: ImageMetadata = Imaging.getMetadata(file)
            //get date from metaData

            for (item in metaData.items) {
                if (item.toString().startsWith("DateTimeOriginal")) {
                    //get the date
                    val dateString = item.toString().substring(item.toString().indexOf("'") + 1, item.toString().lastIndexOf("'"))
                    //convert date: yyyy:MM:dd HH:mm:ss to Date object
                    //create new DateFormat
                    val dateFormat = SimpleDateFormat("yyyy:MM:dd HH:mm:ss")
                    val date = dateFormat.parse(dateString)
                    //get month from date using calendar
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    val month = calendar.get(Calendar.MONTH)
                    val year = calendar.get(Calendar.YEAR)


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
        }
    }
}







