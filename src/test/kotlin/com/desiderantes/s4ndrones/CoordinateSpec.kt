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

import com.desiderantes.s4ndrones.models.Coordinate
import com.desiderantes.s4ndrones.models.Direction
import com.desiderantes.s4ndrones.models.Orientation
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object CoordinateSpec : Spek({
    describe("A coordinate") {
        it("can iterate to the left") {
            val coordinate1 = Coordinate(0, 0, Orientation.NORTH)
            assertEquals(coordinate1.iterate(Direction.LEFT), Coordinate(0, 0, Orientation.WEST))
        }
        it("can iterate to the left and go forward") {
            val coordinate1 = Coordinate(0, 0, Orientation.NORTH)
            assertEquals(
                coordinate1
                    .iterate(Direction.LEFT)
                    .iterate(Direction.FORWARD), Coordinate(-1, 0, Orientation.WEST)
            )
        }

        it("can iterate to the right") {
            val coordinate1 = Coordinate(0, 0, Orientation.NORTH)
            assertEquals(coordinate1.iterate(Direction.RIGHT), Coordinate(0, 0, Orientation.EAST))
        }
        it("can iterate to the right and go forward") {
            val coordinate1 = Coordinate(0, 0, Orientation.NORTH)
            assertEquals(
                coordinate1
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.FORWARD), Coordinate(1, 0, Orientation.EAST)
            )
        }

        it("can iterate full circle, clockwise") {
            val coordinate1 = Coordinate(0, 0, Orientation.NORTH)
            assertEquals(
                coordinate1.iterate(Direction.RIGHT)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.RIGHT), coordinate1
            )
        }

        it("can iterate full circle, counter-clockwise") {
            val coordinate1 = Coordinate(0, 0, Orientation.NORTH)
            assertEquals(
                coordinate1.iterate(Direction.LEFT)
                    .iterate(Direction.LEFT)
                    .iterate(Direction.LEFT)
                    .iterate(Direction.LEFT), coordinate1
            )
        }

        it("can do a rotation from a different orientation") {
            val coordinate1 = Coordinate(0, 0, Orientation.SOUTH)
            assertEquals(
                coordinate1.iterate(Direction.RIGHT)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.RIGHT), coordinate1
            )
        }

        it("can do a square") {
            val coordinate1 = Coordinate(0, 0, Orientation.NORTH)
            assertEquals(
                coordinate1.iterate(Direction.RIGHT)
                    .iterate(Direction.FORWARD)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.FORWARD)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.FORWARD)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.FORWARD), coordinate1
            )
        }

        it("can draw an S") {
            val coordinate1 = Coordinate(1, 2, Orientation.NORTH)
            assertEquals(
                coordinate1.iterate(Direction.LEFT)
                    .iterate(Direction.FORWARD)
                    .iterate(Direction.LEFT)
                    .iterate(Direction.FORWARD)
                    .iterate(Direction.LEFT)
                    .iterate(Direction.FORWARD)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.FORWARD)
                    .iterate(Direction.RIGHT)
                    .iterate(Direction.FORWARD)
                    .iterate(Direction.RIGHT), Coordinate()
            )
        }

        it("can identify if it's in range") {
            val range = 5
            assertTrue(Coordinate().inRange(range))
            assertTrue(Coordinate(x = range).inRange(range))
            assertTrue(Coordinate(y = -range).inRange(range))
            assertFalse(Coordinate(15, 20).inRange(range))
            assertTrue(Coordinate(range, -range, Orientation.EAST).inRange(range))
        }

    }

})