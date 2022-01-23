package tictactoe

import java.lang.Exception

const val BORDER = "---------"

enum class GameStatus {
    Playing,
    Draw,
    WinX,
    WinO
}

enum class PlayerType {
    PlayerX,
    PlayerO
}

fun main() {
    val board = prepareBoard()
    var currentPlayer = PlayerType.PlayerX
    var gameStatus: GameStatus = GameStatus.Playing
    drawBoard(board)
    do {
        val coordinates = parseCoordinates(board)
        board[coordinates[0]][coordinates[1]] = getCurrentPlayer(currentPlayer)
        drawBoard(board)
        gameStatus = checkWin(board, currentPlayer)
        currentPlayer = if (currentPlayer == PlayerType.PlayerX) PlayerType.PlayerO else PlayerType.PlayerX
    } while (gameStatus == GameStatus.Playing)

    println(
        when (gameStatus) {
            GameStatus.WinX -> "X wins"
            GameStatus.WinO -> "O wins"
            else -> "Draw"
        }
    )
}

private fun getCurrentPlayer(currentPlayer: PlayerType) = if (currentPlayer == PlayerType.PlayerX) "X" else "O"

private fun drawBoard(board: MutableList<MutableList<String>>) {
    println(BORDER)
    for (row in board) {
        println("| ${row[0]} ${row[1]} ${row[2]} |")
    }
    println(BORDER)
}

private fun checkWin(board: MutableList<MutableList<String>>, currentPlayer: PlayerType): GameStatus {
    var x: Int = 0
    var o: Int = 0
    for (row in board) {
        for (ch in row) {
            if (ch == "X") {
                x++
            } else if (ch == "O") {
                o++
            }
        }
    }

    val currentPlayerFigure = getCurrentPlayer(currentPlayer)

    if (
    // check horizon
        board[0][0] == currentPlayerFigure && board[0][1] == currentPlayerFigure && board[0][2] == currentPlayerFigure ||
        board[1][0] == currentPlayerFigure && board[1][1] == currentPlayerFigure && board[1][2] == currentPlayerFigure ||
        board[2][0] == currentPlayerFigure && board[2][1] == currentPlayerFigure && board[2][2] == currentPlayerFigure ||

        // check vertices
        board[0][0] == currentPlayerFigure && board[1][0] == currentPlayerFigure && board[2][0] == currentPlayerFigure ||
        board[0][1] == currentPlayerFigure && board[1][1] == currentPlayerFigure && board[2][1] == currentPlayerFigure ||
        board[0][2] == currentPlayerFigure && board[1][2] == currentPlayerFigure && board[2][2] == currentPlayerFigure ||

        // check diagonals
        board[0][0] == currentPlayerFigure && board[1][1] == currentPlayerFigure && board[2][2] == currentPlayerFigure ||
        board[2][0] == currentPlayerFigure && board[1][1] == currentPlayerFigure && board[0][2] == currentPlayerFigure
    ) {
        return if (currentPlayer == PlayerType.PlayerX) GameStatus.WinX else GameStatus.WinO
    } else if (x + o == 9) {
        return GameStatus.Draw
    }

    return GameStatus.Playing
}

private fun prepareBoard(): MutableList<MutableList<String>> {
    return mutableListOf(mutableListOf(" ", " ", " "), mutableListOf(" ", " ", " "), mutableListOf(" ", " ", " "))
}

private fun parseCoordinates(board: MutableList<MutableList<String>>): List<Int> {
    var x: Int = -1
    var y: Int = -1
    do {
        println("Enter the coordinates:")
        try {
            val input: MutableList<Int> = readLine()!!.split(" ").map { it.toInt() }.toMutableList()
            val tempX = input.first() - 1
            val tempY = input.last() - 1
            if (board[tempX][tempY] == "X" || board[tempX][tempY] == "O") {
                println("This cell is occupied! Choose another one!")
            } else if (tempX !in 0..2 || tempY !in 0..2) {
                println("Coordinates should be from 1 to 3!")
            } else {
                x = tempX
                y = tempY
            }
        } catch (e: Exception) {
            println("You should enter numbers!")
        }
    } while ((x !in 0..2) && (y !in 0..2))
    return listOf(x, y)
}
