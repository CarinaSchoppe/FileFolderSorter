package de.carina.filesorter

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.io.File
import java.net.URL

import java.util.*

class DriveSelector : Application() {
    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(javaClass.getResource("/gui/gui.fxml"))
        loader.setController(this)
        val root: Parent = loader.load() as Parent
        primaryStage!!.title = "File Sorter"
        primaryStage.isResizable = false
        primaryStage.scene = Scene(root)
        initialize()
        primaryStage.show()

    }

    @FXML
    private lateinit var deleteFolders: CheckBox

    @FXML
    private lateinit var noDrive: CheckBox

    @FXML
    private lateinit var subFolder: CheckBox

    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    @FXML
    private lateinit var driveLetter: TextField

    @FXML
    private lateinit var monthFolder: CheckBox

    @FXML
    private lateinit var startButton: Button

    @FXML
    fun onStart(event: ActionEvent) {
        if (driveLetter.text.isEmpty()) {
            var alert = Alert(Alert.AlertType.ERROR)
            alert.title = "Error"
            alert.headerText = "Drive Buchstabe oder Ordner pfad ist leer!"
            alert.showAndWait()
            return
        }
        //check if the driveLetter is a single letter from the alphabet
        if (!noDrive.isSelected && driveLetter.text.length != 1 || driveLetter.text[0].uppercaseChar() < 'A' || driveLetter.text[0].uppercaseChar() > 'Z') {
            var alert = Alert(Alert.AlertType.ERROR)
            alert.title = "Error"
            alert.headerText = "Drive Buchstabe MUSS ein Buchstabe aus dem Alphabet sein!"
            alert.showAndWait()
            return
        }
        if (noDrive.isSelected) {
            val file = File(driveLetter.text)
            if (!file.exists()) {
                var alert = Alert(Alert.AlertType.ERROR)
                alert.title = "Error"
                alert.headerText = "Ordner pfad existiert nicht!"
                alert.showAndWait()
                return
            }
            if (file.isDirectory.not()) {
                var alert = Alert(Alert.AlertType.ERROR)
                alert.title = "Error"
                alert.headerText = "Ordner pfad ist kein Ordner!"
                alert.showAndWait()
                return
            }


        }


        val worksAlert = Alert(Alert.AlertType.INFORMATION)
        worksAlert.title = "Information"
        worksAlert.headerText = "Bitte warten, der Ordner wird sortiert!"

        Thread {
            FileSorter.fileSorter = FileSorter(driveLetter.text, monthFolder.isSelected, subFolder.isSelected, noDrive.isSelected, deleteFolders.isSelected)
            FileSorter.fileSorter.addAllFiles()
        }.start()
        worksAlert.showAndWait()

    }

    @FXML
    fun initialize() {
        assert(deleteFolders != null) { "fx:id=\"deleteFolders\" was not injected: check your FXML file 'gui.fxml'." }
        assert(driveLetter != null) { "fx:id=\"driveLetter\" was not injected: check your FXML file 'gui.fxml'." }
        assert(monthFolder != null) { "fx:id=\"monthFolder\" was not injected: check your FXML file 'gui.fxml'." }
        assert(startButton != null) { "fx:id=\"startButton\" was not injected: check your FXML file 'gui.fxml'." }
        assert(subFolder != null) { "fx:id=\"subFolder\" was not injected: check your FXML file 'gui.fxml'." }
        assert(noDrive != null) { "fx:id=\"noDrive\" was not injected: check your FXML file 'gui.fxml'." }
    }

}