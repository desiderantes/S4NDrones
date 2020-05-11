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
package com.desiderantes.s4ndrones.models

import java.io.OutputStream

class Drone(val name: String) {
    private val deliveries: MutableList<Delivery> = ArrayList()

    fun startDelivery(): Delivery? {
        return if (deliveries.size < MAX_DRONE_CAPACITY) {
            val delivery = currentDelivery()?.let { Delivery(it.lastCoordinate.copy()) } ?: Delivery()
            deliveries.add(delivery)
            delivery
        } else null
    }

    fun currentDelivery(): Delivery? {
        return deliveries.lastOrNull()
    }

    fun countDeliveries() = deliveries.size

    fun botchDelivery(): Boolean {
        return deliveries.remove(currentDelivery())
    }

    fun toPrettyString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendln("== Reporte de entregas ==")
        deliveries.forEach {
            stringBuilder.appendln(it.toPrettyString())
        }
        return stringBuilder.toString()
    }

    fun write(out: OutputStream, lineSeparator: String) {
        out.writer().use { writer ->
            writer.append("== Reporte de entregas ==")
            writer.append(lineSeparator)
            deliveries.forEach {
                writer.append(it.toPrettyString())
                writer.append(lineSeparator)
            }
        }
    }

    companion object {
        const val MAX_DRONE_CAPACITY = 3
    }
}