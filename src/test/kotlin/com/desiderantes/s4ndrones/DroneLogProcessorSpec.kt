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

import com.desiderantes.s4ndrones.services.DroneLogProcessor
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

object DroneLogProcessorSpec : Spek({
    describe("The drone log processor") {
        it("can process a clean input") {
            DroneLogProcessor.processInput(
                "02",
                "\n",
                this.javaClass.getResourceAsStream("/clean/in02.txt"),
                System.out
            )
        }

        it("matches on clean input") {
            val out = ByteArrayOutputStream()
            val lineSeparator = "\n"
            DroneLogProcessor.processInput(
                "04",
                lineSeparator,
                this.javaClass.getResourceAsStream("/clean/in04.txt"),
                out
            )
            val expectedOutput = arrayOf(
                "== Reporte de entregas ==",
                "(-2, 4) dirección Norte",
                "(-1, 3) dirección Norte",
                "(-2, 6) dirección Este$lineSeparator"
            ).joinToString(separator = lineSeparator)
            val realOutput = out.toString(Charsets.UTF_8.name())
            assertEquals(expectedOutput, realOutput)
        }

        it("fails to match on line separator issues") {
            val out = ByteArrayOutputStream()
            val lineSeparator = "\n"
            DroneLogProcessor.processInput(
                "04",
                lineSeparator,
                this.javaClass.getResourceAsStream("/dirty/non-valid-line-separator.txt"),
                out
            )
            val expectedOutput = arrayOf(
                "== Reporte de entregas ==",
                "(-2, 4) dirección Norte",
                "(-1, 3) dirección Norte",
                "(-2, 6) dirección Este\r\n"
            ).joinToString(separator = "\r\n")
            val realOutput = out.toString(Charsets.UTF_8.name())
            assertNotEquals(expectedOutput, realOutput)
        }

        it("tries its best on invalid files") {
            val out = ByteArrayOutputStream()
            val lineSeparator = "\n"
            DroneLogProcessor.processInput(
                "valid-drone",
                lineSeparator,
                this.javaClass.getResourceAsStream("/dirty/non-valid-file.html"),
                out
            )
            val expectedOutput = arrayOf(
                "== Reporte de entregas ==",
                "(0, 0) dirección Este",
                "(1, 0) dirección Este",
                "(2, 0) dirección Sur$lineSeparator"
            ).joinToString(separator = lineSeparator)
            val realOutput = out.toString(Charsets.UTF_8.name())
            assertEquals(expectedOutput, realOutput)
        }

        it("skips nonsensical characters, but preserves the lines") {
            val out = ByteArrayOutputStream()
            val lineSeparator = "\n"
            DroneLogProcessor.processInput(
                "chinese-chars",
                lineSeparator,
                this.javaClass.getResourceAsStream("/dirty/non-valid-chars.txt"),
                out
            )
            val expectedOutput = arrayOf(
                "== Reporte de entregas ==",
                "(0, 0) dirección Norte",
                "(0, 0) dirección Norte",
                "(0, 0) dirección Norte$lineSeparator"
            ).joinToString(separator = lineSeparator)
            val realOutput = out.toString(Charsets.UTF_8.name())
            assertEquals(expectedOutput, realOutput)
        }

        it("can process an empty file") {
            val out = ByteArrayOutputStream()
            val lineSeparator = "\n"
            DroneLogProcessor.processInput(
                "empty",
                lineSeparator,
                this.javaClass.getResourceAsStream("/dirty/empty-file.txt"),
                out
            )
            val expectedOutput = "== Reporte de entregas ==$lineSeparator"
            val realOutput = out.toString(Charsets.UTF_8.name())
            assertEquals(expectedOutput, realOutput)
        }

    }
})