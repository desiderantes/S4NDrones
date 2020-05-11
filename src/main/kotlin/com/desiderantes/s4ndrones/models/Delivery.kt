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

class Delivery(firstCoordinate: Coordinate = Coordinate()) {
    private val coordinates: MutableList<Coordinate> = arrayListOf(firstCoordinate)

    fun addMovement(direction: Direction) {
        coordinates.add(coordinates.last().iterate(direction))
    }

    val lastCoordinate: Coordinate
        get() = coordinates.last()

    fun validForRange(max: Int): Boolean {
        return coordinates.last().inRange(max)
    }

    fun toPrettyString(): String {
        return coordinates.last().toPrettyString()
    }
}