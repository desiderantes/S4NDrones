/*   S4NDrones: A drone delivery scheduler and reporter.
 *
 *   Copyright (C) 2020 Mario Daniel Ruiz Saavedra <desiderantes93@gmail.com>.
 *
 *   S4NDrones is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   S4NDrones is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   You should have received a copy of the GNU General Public License
 *   along with S4NDrones.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.desiderantes.s4ndrones.services

import java.nio.file.Files
import java.nio.file.Paths
import java.text.DecimalFormat

object FileProcessor {

    const val MAX_DRONES = 20

    private val inputFormat = DecimalFormat("0".repeat(MAX_DRONES.toString().length))

    /**
     * This process ignores missing files and does NOT try to consume more files than
     * MAX_DRONES. A missing file means tha there's no output for that drone.
     * A file with invalid or empty deliveries will result on a report with no entries.
     *
     */
    fun startProcess(inputFolder: String, lineSeparator: String, outputFolder: String) {
        val dir = Paths.get(inputFolder)
        (1..MAX_DRONES).forEach {
            val droneName = inputFormat.format(it)
            val fileName = "in$droneName.txt"
            val inputFile = dir.resolve(fileName)
            if (Files.notExists(inputFile)) {
                println("Missing drone file input in folder, skipping: $fileName")
                return@forEach
            }
            val out = Files.newOutputStream(Files.createFile(Paths.get(outputFolder).resolve("out$droneName.txt")))
            DroneLogProcessor.processInput(droneName, lineSeparator, Files.newInputStream(inputFile), out)
        }
    }

}