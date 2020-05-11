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
 *   along with Skandium.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.desiderantes.s4ndrones.controllers

import com.desiderantes.s4ndrones.services.FileProcessor
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum

object CliRunner : CliktCommand(help = "A drone delivery scheduler and reporter") {
    val input by option(help = "The input folder").default(
        System.getProperty("user.dir"),
        defaultForHelp = "Current dir"
    )
    val lineSeparator by option(help = "line separator for the output files").enum<LineSeparator>()
        .default(LineSeparator.LF, defaultForHelp = "Unix new line (\\n)")
    val output by option(help = "Output folder").default(System.getProperty("user.dir"), defaultForHelp = "Current dir")
    override fun run() {
        FileProcessor.startProcess(input, lineSeparator.realValue, output)
    }
}

enum class LineSeparator(val realValue: String) {
    LF("\n"),
    CRLF("\r\n"),
    CR("\r");

    companion object {
        fun fromString(separator: String): LineSeparator {
            return when (separator) {
                LF.realValue -> LF
                CRLF.realValue -> CRLF
                CR.realValue -> CR
                else -> throw IllegalArgumentException("Unknown line separator")
            }
        }
    }
}