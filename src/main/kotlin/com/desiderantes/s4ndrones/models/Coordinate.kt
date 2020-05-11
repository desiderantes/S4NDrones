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

import kotlin.math.abs

data class Coordinate(
    val x: Int = 0,
    val y: Int = 0,
    val orientation: Orientation = Orientation.NORTH
) {
    fun iterate(direction: Direction): Coordinate {
        return when (direction) {
            Direction.FORWARD -> {
                val (newX, newY) = orientation.advance(x, y)
                copy(x = newX, y = newY)
            }
            Direction.RIGHT -> copy(orientation = orientation.next())
            Direction.LEFT -> copy(orientation = orientation.prev())
        }
    }

    fun toPrettyString(): String {
        return "($x, $y) dirección ${orientation.toPrettyString()}"
    }

    /**
     * This method assumes that a coordinate is in range if it's
     * in the square of size 2*max x 2*max (both inclusive), with (0,0) at the center.
     */
    fun inRange(max: Int): Boolean {
        return abs(x) <= max && abs(y) <= max
    }
}