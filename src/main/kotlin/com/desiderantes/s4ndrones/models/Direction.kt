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

enum class Direction {
    FORWARD,
    LEFT,
    RIGHT;


    companion object {
        fun fromChar(char: Char): Direction? {
            return when (char) {
                'A', 'a' -> FORWARD
                'I', 'i' -> LEFT
                'D', 'd' -> RIGHT
                else -> null
            }
        }
    }
}

