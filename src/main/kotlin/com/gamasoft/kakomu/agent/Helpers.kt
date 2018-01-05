package com.gamasoft.kakomu.agent

import com.gamasoft.kakomu.model.Board
import com.gamasoft.kakomu.model.Player
import com.gamasoft.kakomu.model.Point


fun isAnEye(board: Board, point: Point, color: Player): Boolean {

    //Must be empty
    if (!board.isFree(point)) {
        return false
    }

    //All adjacent points must contain friendly stones.
    for (neighbor in point.neighbors()) {
        if (board.isOnTheGrid(neighbor)) {
            val string = board.getString(neighbor)
            if (string == null || string.color != color)
                return false
        }
    }


    // We must control 3 out of 4 corners if the point is in the middle
    // of the board; on the edge we must control all corners.
    var friendlyCorners = 0
    var offBoardCorners = 0
    val corners = listOf<Point>(
            Point(point.row - 1, point.col - 1),
            Point(point.row - 1, point.col + 1),
            Point(point.row + 1, point.col - 1),
            Point(point.row + 1, point.col + 1)
    )
    for (corner in corners) {
        if (board.isOnTheGrid(corner)) {
            val cornerColor = board.getString(corner)?.color

            if (cornerColor == color)
                friendlyCorners += 1
        } else {
            offBoardCorners += 1
        }
    }


    if (offBoardCorners > 0) {
        // Point is on the edge or corner.
        return offBoardCorners + friendlyCorners == 4
    }

    // Point is in the middle.
    return friendlyCorners >= 3

}


