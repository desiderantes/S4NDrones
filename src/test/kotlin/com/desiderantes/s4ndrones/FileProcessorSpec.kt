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
package com.desiderantes.s4ndrones

import com.desiderantes.s4ndrones.services.FileProcessor
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

object FileProcessorSpec : Spek({
    describe("File Processor") {
        it("Processes files from a folder") {
            FileProcessor.startProcess(
                Paths.get(this.javaClass.getResource("/clean").toURI()).toString(),
                "\n",
                //this outputs to the dir specified in the env var "java.io.tmpdir"
                // %TEMP% on Windows, and usually /var/tmp on Unix
                Files.createTempDirectory("s4n-spec-${LocalDate.now()}").toAbsolutePath().toString()
            )
        }
    }
})