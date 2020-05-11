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

enum class Orientation {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun next(): Orientation {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun prev(): Orientation {
        return when (this) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
        }
    }

    fun advance(x: Int, y: Int): Pair<Int, Int> {
        return when (this) {
            NORTH -> x to (y + 1)
            SOUTH -> x to (y - 1)
            EAST -> (x + 1) to y
            WEST -> (x - 1) to y
        }
    }

    fun toPrettyString(): String {
        return when (this) {
            NORTH -> "Norte"
            SOUTH -> "Sur"
            EAST -> "Este"
            WEST -> "Oeste"
        }
    }
}