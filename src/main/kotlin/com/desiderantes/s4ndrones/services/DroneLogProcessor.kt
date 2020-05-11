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
package com.desiderantes.s4ndrones.services

import com.desiderantes.s4ndrones.models.Direction
import com.desiderantes.s4ndrones.models.Drone
import java.io.InputStream
import java.io.OutputStream

object DroneLogProcessor {

    private const val DEFAULT_RANGE = 10


    /**
     * Process an InputStream, generate the deliveries, and write the results to the OutputStream
     * This process assumes that a delivery is only valid if it ends in the valid range (by default, 10)
     * Otherwise it gets yanked, along with the next lines.
     *
     * The reading process ignores invalid characters in the processed lines
     *
     */
    fun processInput(name: String, lineSeparator: String, inputStream: InputStream, outputStream: OutputStream) {
        val drone = Drone(name)
        var botchedFlag = false
        inputStream
            .bufferedReader()
            .lines()
            .forEachOrdered {
                if (botchedFlag) return@forEachOrdered
                val delivery = drone.startDelivery() ?: return@forEachOrdered
                it.forEach { char ->
                    val direction = Direction.fromChar(char) ?: return@forEach
                    delivery.addMovement(direction)
                }
                if (!delivery.validForRange(DEFAULT_RANGE)) {
                    drone.botchDelivery()
                    botchedFlag = true
                }
            }
        drone.write(outputStream, lineSeparator)
    }

}