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
package com.desiderantes.s4ndrones

import com.desiderantes.s4ndrones.controllers.CliRunner
import com.desiderantes.s4ndrones.controllers.LineSeparator
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

object CliRunnerSpec : Spek({
    describe("Cli Runner") {
        it("Properly parses commands") {
            val params = arrayOf(
                "--input=" + Paths.get(this.javaClass.getResource("/clean").toURI()).toString(),
                "--line-separator=${LineSeparator.LF}",
                //this outputs to the dir specified in the env var "java.io.tmpdir"
                // %TEMP% on Windows, and usually /var/tmp on Unix
                "--output=" + Files.createTempDirectory("s4n-cli-${LocalDate.now()}").toAbsolutePath().toString()
            )
            CliRunner.main(params)
        }
    }

})