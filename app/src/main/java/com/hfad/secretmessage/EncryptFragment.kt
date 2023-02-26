package com.hfad.secretmessage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import android.widget.TextView


class EncryptFragment : Fragment() {

    //array list of buttons
    private val buttons = ArrayList<Button>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_encrypt, container, false)
        //toast for if the move is invalid
        val invalid = Toast.makeText(context, "INVALID MOVE", Toast.LENGTH_SHORT)

        //retrieving the message from the prior fragment
        val message = EncryptFragmentArgs.fromBundle(requireArguments()).message
        val encryptedView = view.findViewById<TextView>(R.id.encrypt_message)

        //setting the text for the current turn of the game
        encryptedView.text = "Current turn: $message"


        //adding the buttons to the array list

        buttons.add(view.findViewById<Button>(R.id.button1))
        buttons.add(view.findViewById<Button>(R.id.button2))
        buttons.add(view.findViewById<Button>(R.id.button3))
        buttons.add(view.findViewById<Button>(R.id.button4))
        buttons.add(view.findViewById<Button>(R.id.button5))
        buttons.add(view.findViewById<Button>(R.id.button6))
        buttons.add(view.findViewById<Button>(R.id.button7))
        buttons.add(view.findViewById<Button>(R.id.button8))
        buttons.add(view.findViewById<Button>(R.id.button9))
        buttons.add(view.findViewById<Button>(R.id.button10))
        buttons.add(view.findViewById<Button>(R.id.button11))
        buttons.add(view.findViewById<Button>(R.id.button12))
        buttons.add(view.findViewById<Button>(R.id.button13))
        buttons.add(view.findViewById<Button>(R.id.button14))
        buttons.add(view.findViewById<Button>(R.id.button15))
        buttons.add(view.findViewById<Button>(R.id.button16))
        buttons.add(view.findViewById<Button>(R.id.button17))
        buttons.add(view.findViewById<Button>(R.id.button18))
        buttons.add(view.findViewById<Button>(R.id.button19))
        buttons.add(view.findViewById<Button>(R.id.button20))
        buttons.add(view.findViewById<Button>(R.id.button21))
        buttons.add(view.findViewById<Button>(R.id.button22))
        buttons.add(view.findViewById<Button>(R.id.button23))
        buttons.add(view.findViewById<Button>(R.id.button24))
        buttons.add(view.findViewById<Button>(R.id.button25))
        buttons.add(view.findViewById<Button>(R.id.button26))
        buttons.add(view.findViewById<Button>(R.id.button27))
        buttons.add(view.findViewById<Button>(R.id.button28))
        buttons.add(view.findViewById<Button>(R.id.button29))
        buttons.add(view.findViewById<Button>(R.id.button30))
        buttons.add(view.findViewById<Button>(R.id.button31))
        buttons.add(view.findViewById<Button>(R.id.button32))
        buttons.add(view.findViewById<Button>(R.id.button33))
        buttons.add(view.findViewById<Button>(R.id.button34))
        buttons.add(view.findViewById<Button>(R.id.button35))
        buttons.add(view.findViewById<Button>(R.id.button36))

        //for loop to set an on click listener for each button
        for(i in 0..35)
        {
            buttons[i].setBackgroundColor(Color.WHITE)
            buttons[i].setOnClickListener(){
                if(buttons[i].text == " ")
                {
                    setMove(1,i)
                }
                buttons[i].onClick(EncryptFragment());
            }
        }



        return view


    }
    private fun Button.onClick(encryptFragment: EncryptFragment) {



        //button to reset the game if the game ends
        val reset = view?.findViewById<Button>(R.id.resetButton)

        //toasts for different situations
        val invalid = Toast.makeText(context, "INVALID MOVE", Toast.LENGTH_SHORT)
        val blue = Toast.makeText(context, "BLUE WON", Toast.LENGTH_SHORT)
        val red = Toast.makeText(context, "RED WON", Toast.LENGTH_SHORT)
        val tie = Toast.makeText(context, "TIE", Toast.LENGTH_SHORT)

        //code for if the game ends
        if (reset != null) {
            //click listener for the reset button
            reset.setOnClickListener(){
                Toast.makeText(activity, "Restarting Game", Toast.LENGTH_SHORT).show()
                val i = requireActivity().baseContext.packageManager.getLaunchIntentForPackage(requireActivity().baseContext.packageName)
            i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
          i!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         startActivity(i)
         requireActivity().finish()
            }
        }

        //assigns the text and color for buttons selected
        if(this.text == " "){
            this.text = "R"
            this.setBackgroundColor(Color.RED)
            var win = checkForWinner()
            //if statement for check or the winner
            //this code is implented in two areas based on if the computer wins for the player wins
            if(win == GameConstants.TIE){
                tie.show()
                if (reset != null) {
                    //this makes the reset button visible
                    reset.isInvisible = false
                }
            }
            if(win == GameConstants.RED_WON){
                red.show()
                if (reset != null) {
                    reset.isInvisible = false
                }
            }
            if(win == GameConstants.BLUE_WON){
                blue.show()
                if (reset != null) {
                    reset.isInvisible = false
                }

            }

            //gets the computer move
            val computer = computerMove()
            setMove(2,computer)
            buttons[computer].text = "B"
            buttons[computer].setBackgroundColor(Color.BLUE)

            win = checkForWinner()

            if(win == GameConstants.TIE){
                tie.show()
                if (reset != null) {
                    reset.isInvisible = false
                }
            }
            if(win == GameConstants.RED_WON){
                red.show()
                if (reset != null) {
                    reset.isInvisible = false
                }
            }
            if(win == GameConstants.BLUE_WON){
                blue.show()
                if (reset != null) {
                    reset.isInvisible = false
                }

            }

        }
        //shows the invalid move
        else
        {
            invalid.show()
        }
    }



    //the rest of the code is unedited
    private val board = Array(GameConstants.ROWS) { IntArray(GameConstants.COLS){0} }
    private var start = 0;

    //clears the board by going through each space of the board and clearing it

    private fun setMove(player: Int, location: Int) {

        //converts the single number location into row and column values
        var r = location % GameConstants.ROWS;
        var c = location / GameConstants.COLS;

        //sets the move based on if the player is the player (1) or computer (2)
        if(player == 1){
            board[c][r] = GameConstants.RED;
        }
        if(player == 2){

            board[c][r] = GameConstants.BLUE;
        }



        //have this call the computerMove once this has been gone through

        computerMove()
    }



    //computer move function
    fun computerMove(): Int
    {

        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                //this is used for the beginning of the game to pick a random starting location for the computer
                if(start == 0)
                {
                    var i = 0;
                    var ranRow = 0;
                    var ranCol = 0;

                    while(i == 0){
                        ranRow = (0..5).random()
                        ranCol = (0..5).random()
                        if(board[ranRow][ranCol] == 0)
                        {
                            i = 1
                        }
                    }

                    //converts the row and column values to a single number for location
                    var location = ranRow * 6;
                    location += ranCol;

                    start += 1;


                    return location;
                }

                //these functions bellow are used to determine where the computer should move
                //it works based on if there is a blue tile (represents computer) in a sorrunding space and if that space is not already occupied

                //used for the computer to move right if there is a blue tile to the left of it
                if((col != 0) && (board[row][col - 1] == GameConstants.BLUE) && (board[row][col] == 0))
                {
                    var location = row * 6;
                    location += col;
                    println(location)
                    return location;

                }

                //used for the computer to move up if there is a blue tile below it
                else if(row != 0 && board[row - 1][col] == GameConstants.BLUE && (board[row][col] == 0))
                {
                    var location = row * 6;
                    location += col;
                    println()
                    return location;
                }

                //used for the computer to move left if there is a blue tile to the right of it
                else if(col != 5 && board[row][col + 1] == GameConstants.BLUE && (board[row][col] == 0))
                {
                    var location = row * 6;
                    location += col;
                    return location;
                }

                //used for the computer to move down if there is a blue tile above it
                else if(row != 5 && board[row + 1][col] == GameConstants.BLUE && (board[row][col] == 0))
                {
                    var location = row * 6;
                    location += col;
                    return location;
                }

                //the rest of the computer moves below are diagonals

                //up right
                else if((col != 0) && (row != 0) && (board[row - 1][col - 1] == GameConstants.BLUE) && (board[row][col] == 0))
                {
                    var location = row * 6;
                    location += col;
                    return location;
                }

                //up left
                else if((col != 5) && (row != 0) && (board[row - 1][col + 1] == GameConstants.BLUE) && (board[row][col] == 0))
                {
                    var location = row * 6;
                    location += col;
                    return location;
                }

                //down right
                else if((col != 0) && (row != 5) && (board[row + 1][col - 1] == GameConstants.BLUE) && (board[row][col] == 0))
                {
                    var location = row * 6;
                    location += col;
                    return location;
                }

                //down right
                else if((col != 5) && (row != 5) && (board[row + 1][col + 1] == GameConstants.BLUE) && (board[row][col] == 0))
                {
                    var location = row * 6;
                    location += col;
                    return location;
                }
            }


        }

        //this function is used if there is no available space for the computer to move so it goes to a random spot on the board that is open
        var i = 0;
        var ranRow = 0;
        var ranCol = 0;

        while(i == 0){
            ranRow = (0..5).random()
            ranCol = (0..5).random()
            if(board[ranRow][ranCol] == 0)
            {
                i = 1
            }
        }

        var location = ranRow * 6;
        location += ranCol;

        return location;

    }







    //used to check if a player has won
    fun checkForWinner(): Int {

        //checking if the player has won
        //finds a tile that is of a certain color then checks if the next 3 tiles are of the same color either diagonally vertically or horizontally

        //checking for win horizontally
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {

                if (board[row][col] == GameConstants.RED && (col + 3 <= 5)) {
                    if ((board[row][col + 1] == GameConstants.RED)) {
                        if ((board[row][col + 2] == GameConstants.RED)) {
                            if ((board[row][col + 3] == GameConstants.RED)) {
                                return 2;
                            }
                        }

                    }
                }


            }
        }

        //checking for win vertically
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {

                if (board[row][col] == GameConstants.RED && (row + 3 <= 5)) {
                    if ((board[row + 1][col] == GameConstants.RED)) {
                        if ((board[row + 2][col] == GameConstants.RED)) {
                            if ((board[row + 3][col] == GameConstants.RED)) {
                                return 2;
                            }
                        }

                    }
                }


            }
        }

        //diagonal where the top is to the right and the bottom is to the left (/)
        //checking for win top to the right
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {

                if (board[row][col] == GameConstants.RED && (row + 3 <= 5) && (col + 3 <= 5)) {
                    if ((board[row + 1][col + 1] == GameConstants.RED)) {
                        if ((board[row + 2][col + 2] == GameConstants.RED)) {
                            if ((board[row + 3][col + 3] == GameConstants.RED)) {
                                return 2;
                            }
                        }

                    }
                }


            }
        }

        //diagonal where the top is to the left and the bottom is to the right (\)
        //checking for win top to the right
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {

                if (board[row][col] == GameConstants.RED && (row - 3 >= 0) && (col + 3 <= 5)) {
                    if ((board[row - 1][col + 1] == GameConstants.RED)) {
                        if ((board[row - 2][col + 2] == GameConstants.RED)) {
                            if ((board[row - 3][col + 3] == GameConstants.RED)) {
                                return 2;
                            }
                        }

                    }
                }


            }
        }

        //same set of check win functions just but to check if the computer wins

        //going up
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {

                if (board[row][col] == GameConstants.BLUE && (row + 3 <= 5)) {
                    if ((board[row + 1][col] == GameConstants.BLUE)) {
                        if ((board[row + 2][col] == GameConstants.BLUE)) {
                            if ((board[row + 3][col] == GameConstants.BLUE)) {
                                return 3;
                            }
                        }

                    }
                }


            }
        }

        //checking for win horizontally
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {

                if (board[row][col] == GameConstants.BLUE && (col + 3 <= 5)) {
                    if ((board[row][col + 1] == GameConstants.BLUE)) {
                        if ((board[row][col + 2] == GameConstants.BLUE)) {
                            if ((board[row][col + 3] == GameConstants.BLUE)) {
                                return 3;
                            }
                        }

                    }
                }


            }
        }

        //checking for win top to the right
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {

                if (board[row][col] == GameConstants.BLUE && (row + 3 <= 5) && (col + 3 <= 5)) {
                    if ((board[row + 1][col + 1] == GameConstants.BLUE)) {
                        if ((board[row + 2][col + 2] == GameConstants.BLUE)) {
                            if ((board[row + 3][col + 3] == GameConstants.BLUE)) {
                                return 3;
                            }
                        }

                    }
                }


            }
        }

        //checking for win top to the right
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {

                if (board[row][col] == GameConstants.BLUE && (row - 3 >= 0) && (col + 3 <= 5)) {
                    if ((board[row - 1][col + 1] == GameConstants.BLUE)) {
                        if ((board[row - 2][col + 2] == GameConstants.BLUE)) {
                            if ((board[row - 3][col + 3] == GameConstants.BLUE)) {
                                return 3;
                            }
                        }

                    }
                }
            }
        }

        //check for a tie
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                if(board[row][col] == 0)
                {
                    return 0;
                }
            }
        }

        return 1
    }

    /**
     * Print the game board
     */
    fun printBoard() {

        //allows the player to move
        println("Your move: ")
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                printCell(board[row][col]) // print each of the cells
                if (col != GameConstants.COLS - 1) {
                    print("|") // print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                println("-----------") // print horizontal partition
            }
        }

        var g = 0;

        while(g == 0) {
            println("Enter a positon on the board: ")
            val valueTemp = readLine()!!
            //i didnt want the player to enter the value 0 so i just subtracted 1 after he enters the value
            var value: Int = valueTemp.toInt() - 1;

            //converts it to a single number
            var r = value % GameConstants.ROWS;
            var c = value / GameConstants.COLS;

            //checks that the position is not take and the position is valid
            if (value + 1 < 36 && value + 1 > 0 && board[r][c] == 0) {
                setMove(1, value)
                g+=1;

            } else {
                println("The value you entered is invalid please try again")
            }
        }


        setMove(2, computerMove());
        if(checkForWinner() == 2)
        {
            println("RED WON")
            println("new board:")
            clearBoard();
        }

        //the computer's mov
        println("Computer's move: ")
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                printCell(board[row][col]) // print each of the cells
                if (col != GameConstants.COLS - 1) {
                    print("|") // print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                println("-----------") // print horizontal partition
            }
        }

        //checks if the computer has won
        if(checkForWinner() == 3)
        {
            println("BLUE WON")
            println("new board:")
            clearBoard();
        }

        //checks if the game ends in a tie
        if(checkForWinner() == 1){
            println("THE GAME HAS ENDED IN A TIE")
            println("new board:")
            clearBoard();
        }

    }

    /**
     * Print a cell with the specified "content"
     * @param content either BLUE, RED or EMPTY
     */
    fun printCell(content: Int) {
        when (content) {
            GameConstants.EMPTY -> print("   ")
            GameConstants.BLUE -> print(" B ")
            GameConstants.RED -> print(" R ")
        }
    }

    fun clearBoard() {
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                board[row][col] = GameConstants.EMPTY;
            }
        }
    }




    }






//buttons[0].setOnClickListener{
//    if(buttons[0].text == " "){
//        setMove(1,1)
//        var win = checkForWinner()
//        //if statement for check or the winner
//
//
//
//        val computer = computerMove()
//        setMove(2,computer)
//
//        //set the button for the computer
//        win = checkForWinner()
//        //if statement for check or the winner
//    }
//    else
//    {
//        invalid.show()
//    }
//}



